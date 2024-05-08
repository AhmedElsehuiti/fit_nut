package com.example.fit_nut

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.fit_nut.screen.FirstScreenFragment
import com.example.fit_nut.screen.FourthScreenFragment
import com.example.fit_nut.screen.SecondScreenFragment
import com.example.fit_nut.screen.ThirdScreenFragment
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator

class OnBoardingFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_on_boarding, container, false)
        val fragmentList = arrayListOf<Fragment>(
            FirstScreenFragment() ,
            SecondScreenFragment(),
            ThirdScreenFragment(),
            FourthScreenFragment()
        )
        val adapter =ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        val viewpager= view.findViewById<ViewPager2>(R.id.view_pager)
        viewpager.adapter =adapter
        val indicator = view.findViewById<DotsIndicator>(R.id.dots_indicator)
        indicator.attachTo(viewpager)
        return view
    }


}