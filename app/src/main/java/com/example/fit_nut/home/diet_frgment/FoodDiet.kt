package com.example.fit_nut.home.diet_frgment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.fit_nut.R
import com.example.fit_nut.home.calories.FoodDailyCalAadpter
import com.example.fit_nut.home.calories.ItemFood

class FoodDiet : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FoodDietAdapter
    private var foodDetails=ArrayList<ItemDiet>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_diet)
        recyclerView = findViewById(R.id.recyclerView)
        adapter = FoodDietAdapter(foodDetails)
        recyclerView.adapter = adapter
        foodDiet()
    }
    private fun foodDiet() {
        val item1 = ItemDiet("Breakfast", "0.0")
        val item2 = ItemDiet("Dinner", "0.0")
        val item3 = ItemDiet("Lunch", "0.0")
        val item4 = ItemDiet("snacks", "0.0")
        val item5 = ItemDiet("light meal", "0.0")
        foodDetails.add(item1)
        foodDetails.add(item2)
        foodDetails.add(item3)
        foodDetails.add(item4)
        foodDetails.add(item5)
        adapter.notifyDataSetChanged()
    }

}