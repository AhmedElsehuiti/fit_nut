package com.example.fit_nut.home.diet_frgment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fit_nut.databinding.ItemSingleText2Binding


class SingleItemDietAdapter :  RecyclerView.Adapter<SingleItemDietAdapter.SingleViewHolder>()
{
    private var items: List<String> = emptyList()
    inner class SingleViewHolder(private val binding: ItemSingleText2Binding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) = with(binding) {
            foodName.text=item
            foodCal.text =item
            foodGm.text=item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleViewHolder {
        return SingleViewHolder(
            ItemSingleText2Binding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
         }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: SingleViewHolder, position: Int) {
        holder.bind(items[position])
    }
    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<String>) {
        items = list
        notifyDataSetChanged()
    }
}