package com.example.fit_nut.home

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.fit_nut.R
import com.example.fit_nut.home.calories.CaloriesFragment
import com.example.fit_nut.home.camera_fragment.CameraFragment
import com.example.fit_nut.home.diet_frgment.DietFragment
import com.example.fit_nut.home.home_fragment.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

 @Suppress("UNUSED_EXPRESSION")
 class HomeActivity : AppCompatActivity()  {
     private var backPressedOnce = false
     private lateinit var bottomNavigationView: BottomNavigationView
     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_home)
         bottomNavigationView= findViewById(R.id.bottomNavigationView)
         bottomNavigationView.selectedItemId = R.id.home_nav
         bottomNavigationView.setOnItemSelectedListener OnItemSelectedListener@{
             when (it.itemId) {
                 R.id.home_nav -> {
                     pushFragment(HomeFragment())
                     true
                 }

                 R.id.diet_nav -> {
                     pushFragment(DietFragment())
                     true

                 }

                 R.id.camera_nav -> {
                     pushFragment(CameraFragment())
                     true
                 }

                 R.id.calories_nav -> {
                     pushFragment(CaloriesFragment())

                     true
                 }
                 else -> false
             }
             return@OnItemSelectedListener true
         }

        bottomNavigationView.selectedItemId = R.id.home_nav



    }
    private fun pushFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
     @SuppressLint("MissingSuperCall")
     @Deprecated("Deprecated in Java")
     override fun onBackPressed() {
         val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
         if (fragment is HomeFragment) {
             if (!backPressedOnce) {
                 backPressedOnce = true
                 Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()
                 Handler(Looper.getMainLooper()).postDelayed({
                     backPressedOnce = false
                 }, 1000)
             } else {
                 finishAffinity() // This will exit the app completely
             }
         } else {
             supportFragmentManager.beginTransaction()
                 .replace(R.id.fragment_container, HomeFragment())
                 .commit()
             bottomNavigationView.selectedItemId = R.id.home_nav

         }
     }
 }