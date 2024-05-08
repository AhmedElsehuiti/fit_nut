package com.example.fit_nut.home.home_fragment

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContentProviderCompat.requireContext
import com.example.fit_nut.R
import com.example.fit_nut.home.HomeActivity
import com.example.fit_nut.ui.model.AppUser
import com.example.fit_nut.ui.user_information.UserInformationViewModel
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking
import kotlin.math.roundToInt

class WeightBMI : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()

    lateinit var weightInputEditText: TextInputEditText
    lateinit var calories : AppCompatButton
    lateinit var cancel :TextView
    lateinit var normalWeight : TextView
    lateinit var normalHigherWeightRange : TextView
    lateinit var normalLowerWeightRange : TextView
    lateinit var saveData : AppCompatButton
    private lateinit var userInformationViewModel: UserInformationViewModel
    lateinit var appUser: AppUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userInformationViewModel = UserInformationViewModel()
        setContentView(R.layout.activity_weight_bmi)
        initViews()
        val currentUser = Firebase.auth.currentUser?.uid ?: ""
        runBlocking {
            appUser =  userInformationViewModel.getUserById(currentUser) ?: AppUser()
        }
        weightInputEditText.setText(appUser.weight)
        onItemClicked()
    }

    fun onItemClicked(){
        saveData.setOnClickListener {
            updateWeightValues()
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        cancel.setOnClickListener {
            finish()
        }
        calories.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)

            startActivity(intent)
        }
    }

    fun initViews(){
        saveData = findViewById(R.id.save)
        normalWeight = findViewById(R.id.normal_weight)
        weightInputEditText = findViewById(R.id.ed_text)
        normalHigherWeightRange = findViewById(R.id.normal_higher_weight_range)
        normalLowerWeightRange = findViewById(R.id.normal_low_weight_range)
        cancel = findViewById(R.id.cancel)
        calories = findViewById(R.id.calories)

        weightInputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                updateWeightValues()
            }
        })
    }

    fun calculateBMI(weight: Double, height: Double): Double {
        return weight / (height * height)
    }

    fun updateWeightValues() {
        val weight = weightInputEditText.text.toString().toDoubleOrNull() ?: 0.0
        val height = appUser.height.toDoubleOrNull() ?: 0.0
        val bmi = calculateBMI(weight, height)
        val normalWeightValue = (22.5 * (height * height))/10000
        val normalHigherWeightRangeValue = (24.9 * (height * height))/10000
        val normalLowerWeightRangeValue = (18.5 * (height * height))/10000

        normalWeight.text = "${normalWeightValue.roundToInt()}"
        normalHigherWeightRange.text = "${normalHigherWeightRangeValue.roundToInt()}"
        normalLowerWeightRange.text = "${normalLowerWeightRangeValue.roundToInt()}"
        appUser.weight = weight.toString() // Update appUser.weight
        val currentUser = Firebase.auth.currentUser?.uid ?: ""
        db.collection("users").document(currentUser).update("weight", appUser.weight)
            .addOnSuccessListener {
                // Update successful
            }
            .addOnFailureListener { e ->
                // Handle failure
                Log.w(TAG, "Error updating document", e)
            }
    }

}
