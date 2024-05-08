package com.example.fit_nut.home.calories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fit_nut.R

class FoodDailyCal(private val items: List<ItemFood>) : RecyclerView.Adapter<FoodDailyCal.SettingAdapter>() {

    inner class SettingAdapter(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodName: TextView = itemView.findViewById(R.id.txt_name)
        val totalCal: TextView = itemView.findViewById(R.id.txt_cal)
        val totalCalTake: TextView = itemView.findViewById(R.id.txt_cal_number_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingAdapter {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_food, parent, false)
        return SettingAdapter(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: SettingAdapter, position: Int) {
        val item = items[position]
        holder.foodName.text = item.foodTitle
        holder.totalCal.text = item.totalCal
        holder.totalCalTake.text = item.totalCalTake
    }
}