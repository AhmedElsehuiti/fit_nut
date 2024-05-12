package com.example.fit_nut.home.diet_frgment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fit_nut.R
import com.example.fit_nut.databinding.ItemFoodBinding
import com.example.fit_nut.databinding.ItemFoodDietBinding
import com.example.fit_nut.util.collapseWithTransition
import com.example.fit_nut.util.expandWithTransition


class FoodDietAdapter(private var itemsDiet:MutableList<ItemDiet>) : RecyclerView.Adapter<FoodDietAdapter.SettingAdapter>() {
    private val singleItem2Adapter by lazy { SingleItemDietAdapter() }
    private var expandItemPosition: Int = RecyclerView.NO_POSITION
    inner class SettingAdapter(private val binding:ItemFoodDietBinding)
        :RecyclerView.ViewHolder(binding.root){


       fun bind(item:ItemDiet)=with(binding){
           val isExpand = position == expandItemPosition

           foodTitle.text =item.mealsName
           mealGm.text = item.mealsTotalCal
           rvSingleTxt.visibility = View.VISIBLE
           val drawableResId = if (isExpand) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
           txtAddFood.setCompoundDrawablesWithIntrinsicBounds(0, 0, drawableResId, 0)
           txtAddFood.setOnClickListener {
               val previceExpandPoistion = expandItemPosition
               expandItemPosition = if (position == expandItemPosition) {
                   RecyclerView.NO_POSITION
               } else position
               notifyItemChanged(previceExpandPoistion)
               notifyItemChanged(expandItemPosition)
           }
           rvSingleTxt.apply {
               if (isExpand) {
                   expandWithTransition(rvSingleTxt.parent as ViewGroup)
               }
               else {
                   collapseWithTransition(rvSingleTxt.parent as ViewGroup)
               }
               rvSingleTxt.adapter =singleItem2Adapter.apply {
                   setList(
                       listOf(
                           "0", "0", "0",
                       )
                   )
               }
           }



       }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingAdapter {
        return SettingAdapter(
            ItemFoodDietBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )


    }

    override fun getItemCount(): Int {
        return itemsDiet.size
    }

    override fun onBindViewHolder(holder: SettingAdapter, position: Int) {
     holder.bind(itemsDiet[position])

    }
    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: MutableList<ItemDiet>) {
        list.clear()
        list.addAll(itemsDiet)
        notifyDataSetChanged()
    }
}


