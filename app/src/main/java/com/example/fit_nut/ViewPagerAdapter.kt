package com.example.fit_nut

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

 class ViewPagerAdapter(list:ArrayList<Fragment>,FM:FragmentManager,lifecycle:Lifecycle):FragmentStateAdapter(FM,lifecycle) {
     private val fragmentList=list
     override fun getItemCount(): Int {
         return fragmentList.size
     }

     override fun createFragment(position: Int): Fragment {
         return fragmentList[position]
     }
 }