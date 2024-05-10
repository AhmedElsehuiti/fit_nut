package com.example.fit_nut.home.diet_frgment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import com.example.fit_nut.R
import com.example.fit_nut.home.home_fragment.WeightBMI
import com.example.fit_nut.ui.model.AppUser
import com.example.fit_nut.ui.user_information.UserInformationViewModel
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.runBlocking

class DietFragment : Fragment() {
    lateinit var getDiet: AppCompatButton
    private lateinit var userInformationViewModel: UserInformationViewModel
    private lateinit var appUser: AppUser
    private lateinit var diet_Priority : AutoCompleteTextView
    private lateinit var activityLevel : AutoCompleteTextView
    private lateinit var chooseYourCategory: AutoCompleteTextView
    private lateinit var diet_cal : TextView
    private val activityRates = mapOf("High" to 1.7, "Medium" to 1.55, "Low" to 1.2)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_diet,container,false)
        userInformationViewModel = UserInformationViewModel()
        diet_Priority = view.findViewById(R.id.dietAutoCompleteTextView)
        activityLevel = view.findViewById(R.id.activityLevelAutoCompleteTextView)
        chooseYourCategory = view.findViewById(R.id.chooseYourCategoryAutoCompleteTextView)
        getDiet = view.findViewById(R.id.getDiet)
        diet_cal = view.findViewById(R.id.diet_cal)
        val priorities = arrayOf("High", "Medium", "Low")
        val categories = arrayOf("Bulking", "Diet", "Strength", "Fitness")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, priorities)
        val adapter2 = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, categories)
        val currentUser = Firebase.auth.currentUser?.uid ?: ""
        runBlocking {
            appUser =  userInformationViewModel.getUserById(currentUser) ?: AppUser()
        }
        updateDietCal()

        diet_Priority.setAdapter(adapter)
        activityLevel.setAdapter(adapter)
        chooseYourCategory.setAdapter(adapter2)

        diet_Priority.setOnItemClickListener { _, _, position, _ ->
            updateDietCal()
        }

        activityLevel.setOnItemClickListener { _, _, position, _ ->
            updateDietCal()
        }
        onItemClick()

        return view
    }

    private fun updateDietCal() {
        val priority = diet_Priority.text.toString()
        val activity = activityLevel.text.toString()
        var caloriesModifier = when (priority) {
            "High" -> -1000
            "Medium" -> -500
            "Low" -> -250
            else -> 0
        }
        val activityRate = activityRates[activity] ?: 1.0
        val currentYear = 2024
        val birthYear = appUser.birthYear.toIntOrNull() ?: 0
        val ageValue = currentYear - birthYear
        val weight =  appUser.weight.toDoubleOrNull() ?: 0.0
        val height = appUser.height.toDoubleOrNull() ?: 0.0
        val bmr = (10 * weight) + (6.25 * height) - (5 * ageValue) + 5
        val dailyCalories = (bmr * activityRate) + caloriesModifier
        diet_cal.text = dailyCalories.toString()
    }
    fun onItemClick(){
        getDiet.setOnClickListener {
            val intent = Intent(activity,FoodDiet::class.java)
            startActivity(intent)
        }
    }
}