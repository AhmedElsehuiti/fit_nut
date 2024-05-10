package com.example.fit_nut.home.calories

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fit_nut.databinding.ItemSingleTextBinding

class SingleItemAdapter : RecyclerView.Adapter<SingleItemAdapter.SingleViewHolder>() {
    private var items: List<String> = emptyList()

    inner class SingleViewHolder(private val binding: ItemSingleTextBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: String) = with(binding) {
            textView8.text = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleViewHolder {
        return SingleViewHolder(
            ItemSingleTextBinding.inflate(
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