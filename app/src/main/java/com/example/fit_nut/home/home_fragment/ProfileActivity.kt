package com.example.fit_nut.home.home_fragment

import com.example.fit_nut.ui.user_information.UserInformationViewModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.fit_nut.R
import com.example.fit_nut.ui.model.AppUser

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.runBlocking

class ProfileActivity : AppCompatActivity() {
    private lateinit var userInformationViewModel: UserInformationViewModel
    lateinit var age :TextView
    lateinit var gender1 :TextView
    lateinit var userName:TextView
    lateinit var auth: FirebaseAuth
    lateinit var appUser: AppUser
    lateinit var  wight:TextView
    lateinit var height :TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userInformationViewModel = UserInformationViewModel()
        setContentView(R.layout.activity_profile)
        userName = findViewById(R.id.user1)
        age = findViewById(R.id.age)
        gender1 = findViewById(R.id.gender1)
        wight = findViewById(R.id.wight1)
        height=findViewById(R.id.height2)


        val currentUser = Firebase.auth.currentUser?.uid ?: ""

        runBlocking {
            appUser =  userInformationViewModel.getUserById(currentUser) ?: AppUser()
        }
        val currentYear = 2024
        val birthYear = appUser.birthYear.toIntOrNull() ?: 0
        val ageValue = currentYear - birthYear
        age.text = ageValue.toString()
        userName.text = appUser.userName
        gender1.text = appUser.gender
        wight.text = appUser.weight
        height.text = appUser.height

    }



}