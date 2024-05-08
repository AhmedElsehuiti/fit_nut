package com.example.fit_nut.home.home_fragment

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.fit_nut.R
import com.example.fit_nut.home.NotificationActivity
import com.example.fit_nut.ui.model.AppUser
import com.example.fit_nut.ui.user_information.UserInformationViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.runBlocking

class SettingActivity : AppCompatActivity() {
    private lateinit var userInformationViewModel: UserInformationViewModel

    lateinit var appUser: AppUser
    lateinit var userFullName :TextView


    private val PICK_IMAGE_REQUEST = 1
    private val PERMISSION_REQUEST_CODE = 2
    private lateinit var imageView: CircleImageView
    private var imageUri: Uri? = null
    private lateinit var profile :AppCompatButton
    private lateinit var profile1 :AppCompatButton
    private lateinit var profile2 :AppCompatButton
    private lateinit var profile3 :AppCompatButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userInformationViewModel = UserInformationViewModel()
        setContentView(R.layout.activity_setting)
        userFullName =findViewById(R.id.user_fullName)
        val currentUser = Firebase.auth.currentUser?.uid ?: ""
        runBlocking {
            appUser =  userInformationViewModel.getUserById(currentUser) ?: AppUser()
        }
        userFullName.text = appUser.fullName
        initViews()
        onItemClicked()


        // Load image URI from SharedPreferences if not in savedInstanceState
        imageUri = savedInstanceState?.getParcelable("imageUri") ?: loadImageUri()
        imageUri?.let { uri ->
            Glide.with(this).load(uri).into(imageView)
        }
    }

    private fun checkPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            this, Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_REQUEST_CODE
        )
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            imageUri = data.data
            imageUri?.let { uri ->
                Glide.with(this).load(uri).into(imageView)
                saveImageUri(uri.toString())  // Save URI to SharedPreferences
            }
        }
    }

    private fun saveImageUri(imageUri: String) {
        val sharedPref = getSharedPreferences("AppSettings", MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("imageUri", imageUri)
            apply()
            ImageLiveData.postValue(Uri.parse(imageUri))
        }
    }

    private fun loadImageUri(): Uri? {
        val sharedPref = getSharedPreferences("AppSettings", MODE_PRIVATE)
        val uriString = sharedPref.getString("imageUri", null)
        return uriString?.let { Uri.parse(it) }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        imageUri?.let { uri ->
            outState.putParcelable("imageUri", uri)
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        imageUri = savedInstanceState.getParcelable("imageUri") ?: loadImageUri()
        imageUri?.let { uri ->
            Glide.with(this).load(uri).into(imageView)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery()
            }
        }
    }
  private  fun onItemClicked(){
      imageView.setOnClickListener {
          if (checkPermission()) {
              openGallery()
          } else {
              requestPermission()
          }
      }
        profile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
      profile1.setOnClickListener {
          val intent = Intent(this, NotificationActivity::class.java)
          startActivity(intent)
      }
      profile2.setOnClickListener {
          val intent = Intent(this, GoalsActivity::class.java)
          startActivity(intent)
      }
      profile3.setOnClickListener {
          val intent = Intent(this, UserSettingActivity::class.java)
          startActivity(intent)
      }
        }
    private fun initViews(){
        imageView = findViewById(R.id.imageView)
        profile = findViewById(R.id.profile2)
        profile1 = findViewById(R.id.profile3)
        profile2 = findViewById(R.id.profile4)
        profile3 = findViewById(R.id.profile5)
    }
    }