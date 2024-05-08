package com.example.fit_nut.home.calories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.fit_nut.R
import com.example.fit_nut.ui.model.AppUser
import com.example.fit_nut.ui.user_information.UserInformationViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.runBlocking

class CaloriesFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter:FoodDailyCal
    private var foodDetails=ArrayList<ItemFood>()
    private lateinit var totalCal : TextView
    private lateinit var userInformationViewModel: UserInformationViewModel
    private lateinit var appUser: AppUser

    private val  activityRate = 1.55
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_calories,container,false)
        userInformationViewModel = UserInformationViewModel()
        initViews(view)
        val currentUser = Firebase.auth.currentUser?.uid ?: ""
        runBlocking {
            appUser =  userInformationViewModel.getUserById(currentUser) ?: AppUser()
        }
        val currentYear = 2024
        val birthYear = appUser.birthYear.toIntOrNull() ?: 0
        val ageValue = currentYear - birthYear
        val weight =  appUser.weight.toDoubleOrNull() ?: 0.0
        val height = appUser.height.toDoubleOrNull() ?: 0.0
        val bmr = (10 * weight) + (6.25 * height) - (5 *ageValue ) + 5
        val dailyCalories = bmr * activityRate
        totalCal.text = dailyCalories.toString()
        return view
    }
    private fun initViews(view: View) {
        totalCal = view.findViewById(R.id.total_cal)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rv_break_fast)
        adapter = FoodDailyCal(foodDetails)
        recyclerView.adapter = adapter
        dailyFood()  // انقل هذه الدالة لتأتي بعد تعيين ال adapter

    }
    private fun dailyFood() {
        val item1 = ItemFood("Breakfast", "0.0", "0.0")
        val item2 = ItemFood("Dinner", "0.0", "0.0")
        val item3 = ItemFood("Lunch", "0.0", "0.0")
        val item4 = ItemFood("light meal", "0.0", "0.0")
        foodDetails.add(item1)
        foodDetails.add(item2)
        foodDetails.add(item3)
        foodDetails.add(item4)

    }

}