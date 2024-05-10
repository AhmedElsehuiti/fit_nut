package com.example.fit_nut.home.calories

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.fit_nut.R
import com.example.fit_nut.databinding.ItemFoodBinding
import com.example.fit_nut.util.collapseWithTransition
import com.example.fit_nut.util.expandWithTransition

class FoodDailyCalAadpter(private val items: MutableList<ItemFood>) :
    RecyclerView.Adapter<FoodDailyCalAadpter.SettingAdapter>() {
    private val singleItemAdapter by lazy { SingleItemAdapter() }
    private var expandItemPosition: Int = RecyclerView.NO_POSITION

    inner class SettingAdapter(private val binding: ItemFoodBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemFood) = with(binding) {
            val isExpand = position == expandItemPosition
            txtName.text = item.foodTitle
            txtCalNumberText.text = item.totalCalTake
            totalsCal.text = item.totalCal
             rvSingleTxt.visibility=View.VISIBLE // todo
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
                if (isExpand){
                    expandWithTransition(rvSingleTxt.parent as ViewGroup)
                }
                else {
                    collapseWithTransition(rvSingleTxt.parent as ViewGroup)
                }
                rvSingleTxt.adapter = singleItemAdapter.apply {
                    setList(
                        listOf(
                            " item.foodTitle.orEmpty()",
                            " item.foodTitle.orEmpty()",
                            " item.foodTitle.orEmpty()",
                            " item.foodTitle.orEmpty()",
                            " item.foodTitle.orEmpty()",
                            " item.foodTitle.orEmpty()",
                            " item.foodTitle.orEmpty()",
                            " item.foodTitle.orEmpty()",
                            " item.foodTitle.orEmpty()",
                            " item.foodTitle.orEmpty()",
                            " item.foodTitle.orEmpty()",
                            " item.foodTitle.orEmpty()",
                            " item.foodTitle.orEmpty()",
                            " item.foodTitle.orEmpty()",
                            " item.foodTitle.orEmpty()",
                        )
                    )
                }
            }



        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingAdapter {
        return SettingAdapter(
            ItemFoodBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: SettingAdapter, position: Int) {
        holder.bind(items[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: MutableList<ItemFood>) {
        list.clear()
        list.addAll(items)
        notifyDataSetChanged()
    }
}