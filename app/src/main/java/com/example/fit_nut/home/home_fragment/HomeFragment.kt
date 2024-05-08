package com.example.fit_nut.home.home_fragment
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.fit_nut.R
import com.example.fit_nut.ui.model.AppUser
import com.example.fit_nut.ui.user_information.UserInformationViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.runBlocking
import kotlin.math.roundToInt

class HomeFragment : Fragment(), SensorEventListener ,OnAddWaterListener {
    private lateinit var userInformationViewModel: UserInformationViewModel


    private lateinit var textView1: TextView
    private lateinit var textView2: TextView
    private lateinit var image: ImageButton
    private lateinit var weightUpdate:TextView
    lateinit var cardView : CardView
    lateinit var cardView1: CardView
    private val REQUEST_ACTIVITY_RECOGNITION_PERMISSION = 123
    private lateinit var image2: ImageButton
    private var counter=0f
    lateinit var appUser: AppUser
    private lateinit var imageSetting: CircleImageView
    private lateinit var textView: TextView
    private lateinit var textViewKm: TextView
    private lateinit var progressCircular: CircularProgressBar
    private lateinit var progressCircularKM: CircularProgressBar
    private lateinit var progressCircularTotalCalories: CircularProgressBar
    private lateinit var totalCalories: TextView
    private lateinit var sensorManger: SensorManager
    private var running = false
    private var totalSteps = 0f
    private var previousTotalSteps = 0f
    private val caloriesPerStep = 0.05 // Calories burned per step (e.g., 0.05 calories per step)
    private val distancePerStep = 0.762 // Distance covered per step in meters (e.g., 0.762 meters per step)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        requestActivityRecognitionPermission()
        userInformationViewModel = UserInformationViewModel()
        weightUpdate =view.findViewById(R.id.wight_update)

        val currentUser = Firebase.auth.currentUser?.uid ?: ""
        runBlocking {
            appUser =  userInformationViewModel.getUserById(currentUser) ?: AppUser()
        }
        weightUpdate.text = appUser.weight
        // Observe the LiveData in the fragment
        ImageLiveData.observe(viewLifecycleOwner) { uri ->
            Glide.with(this).load(uri).into(imageSetting)
        }
        initViews(view)
        loadData()
        resetSteps()
        setupWaterCounter()
        onItemClicked()
        sensorManger = requireContext().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        return view
    }
    override fun onSensorChanged(event: SensorEvent?) {
        if (running) {
            totalSteps = event!!.values[0]
            val currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()
            textView.text = "$currentSteps"
            progressCircular.setProgressWithAnimation(currentSteps.toFloat())
            val calories = (currentSteps / 10) * 10 * caloriesPerStep // Calories burned for every 10 steps
            val distance = (currentSteps / 10) * 10 * distancePerStep // Distance covered for every 10 steps
            val distanceKm = distance / 1000.0 // Convert distance from meters to kilometers
            val roundedDistanceKm = String.format("%.2f", distanceKm) // Round distance to two decimal places
            totalCalories.text = calories.toString()
            textViewKm.text = roundedDistanceKm.toString()
            progressCircularKM.setProgressWithAnimation(currentSteps.toFloat())
            progressCircularTotalCalories.setProgressWithAnimation(currentSteps.toFloat())
        }

    }
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
        // Can be left empty
    }
    override fun onResume() {
        super.onResume()
        ImageLiveData.value?.let { uri ->
            Glide.with(this).load(uri).into(imageSetting)
        }
        running = true
        val stepSensor = sensorManger?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        if (stepSensor == null) {
            Toast.makeText(context, "No sensor detected on this device", Toast.LENGTH_SHORT).show()
        } else {
            sensorManger?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_UI)
        }

    }
    private fun initViews(view: View) {
        progressCircular = view.findViewById(R.id.progress_circular2)
        progressCircularKM =  view.findViewById(R.id.progress_circular_km)
        progressCircularTotalCalories = view.findViewById(R.id.circularProgressBarTotalCal)
        textView = view.findViewById(R.id.textView)
        totalCalories=view.findViewById(R.id.totalCalories)
        textView1 = view.findViewById(R.id.textView5)
        textView2 = view.findViewById(R.id.textView11)
        image=view.findViewById(R.id.imageButton)
        image2 = view.findViewById(R.id.imageButton2)
        textViewKm = view.findViewById(R.id.textViewKM)
        imageSetting = view.findViewById(R.id.profile)
        cardView = view.findViewById(R.id.card2)
        cardView1 = view.findViewById(R.id.card3)




        // Your initialization code here
    }
    private fun resetSteps() {
        textView.setOnClickListener {
            Toast.makeText(context, "Long tap to reset steps", Toast.LENGTH_SHORT).show()
        }
        textView.setOnLongClickListener {
            previousTotalSteps = totalSteps
            textView.text = "0"
            saveData()
            true
        }
    }
    private fun saveData() {
        val sharedPreferences = activity?.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences?.edit()
        editor?.putFloat("key1", previousTotalSteps)
        editor?.apply()
    }
    private fun loadData() {
        val sharedPreferences = activity?.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val savedNumber = sharedPreferences?.getFloat("key1", 0f) ?: 0f
        previousTotalSteps = savedNumber
    }
    private fun saveCounterValue(counter: Float) {
        val sharedPreferences = requireActivity().getSharedPreferences("WaterCounterPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("counterKey", counter)
        editor.apply()
    }

    private fun loadCounterValue(): Float {
        val sharedPreferences = requireActivity().getSharedPreferences("WaterCounterPrefs", Context.MODE_PRIVATE)
        return sharedPreferences.getFloat("counterKey", 0.0f) // Default value is 0.0
    }
    private fun setupWaterCounter() {
        // Load saved counter value
        counter = loadCounterValue()

        // Update the TextView immediately with the loaded value
        val roundedCounterInit = ((counter * 100).roundToInt()) / 100.0
        textView1.text = roundedCounterInit.toString()


        image.setOnClickListener {
            updateWaterAmount(0.24f)
        }


        image2.setOnClickListener {
            updateWaterAmount(-0.24f)
        }
    }
    private fun onItemClicked(){
        imageSetting.setOnClickListener {
            val intent = Intent(activity, SettingActivity::class.java)
            startActivity(intent)
        }
        cardView.setOnClickListener {
            showAddBottomSheet()

        }
        cardView1.setOnClickListener {
            val intent = Intent(activity, WeightBMI::class.java)
            startActivity(intent)
        }
    }
    private fun showAddBottomSheet() {
        val addBottomSheet = AddWater()
        addBottomSheet.setOnAddWaterListener(this)

        addBottomSheet.show(childFragmentManager,"")
    }

    override fun onWaterAdded(amount: Float) {
        // Update textView1 with the added water amount
        updateWaterAmount(amount)

    }
    private fun updateWaterAmount(amount: Float) {
        val currentAmount = textView1.text.toString().toFloat()
        val newAmount = currentAmount + amount
        val roundedAmount = (newAmount * 100).roundToInt() / 100.0
        val formattedRoundedAmount = String.format("%.2f", roundedAmount)
        if (roundedAmount >= 0 && roundedAmount <= 7) {
            textView1.text = formattedRoundedAmount
            saveCounterValue(roundedAmount.toFloat())
        } else {
            Toast.makeText(requireContext(), "Exceeded the allowed value", Toast.LENGTH_SHORT).show()
        }
    }
    // طلب إذن نشاط المستخدم البدني
    private fun requestActivityRecognitionPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACTIVITY_RECOGNITION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // إذا لم يكن لديك الإذن، فقم بطلبه
            requestPermissions(
                arrayOf(Manifest.permission.ACTIVITY_RECOGNITION),
                REQUEST_ACTIVITY_RECOGNITION_PERMISSION
            )
        }
    }

    // معالجة نتيجة طلب الإذن
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_ACTIVITY_RECOGNITION_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // تم منح الإذن بنجاح
                    Toast.makeText(
                        requireContext(),
                        "Activity Recognition Permission Granted",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    // تم رفض الإذن
                    Toast.makeText(
                        requireContext(),
                        "Activity Recognition Permission Denied",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

}