package com.example.fit_nut.screen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.viewpager2.widget.ViewPager2
import com.example.fit_nut.R
import com.example.fit_nut.ui.regster.RegisterActivity

class FirstScreenFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_first_screen, container, false)
        val next = view.findViewById<AppCompatButton>(R.id.button1)
        val viewPager =activity?.findViewById<ViewPager2>(R.id.view_pager)
        val skip = view.findViewById<TextView>(R.id.skip1)

        next.setOnClickListener {
            viewPager?.currentItem=1
        }
        skip.setOnClickListener{
            val intent = Intent(requireContext(), RegisterActivity::class.java)
            startActivity(intent)
            onBoardingIsFinished()
        }
        return view

    }
    private fun onBoardingIsFinished(){
        val sharedPreferences=requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor =sharedPreferences.edit()
        editor.putBoolean("finished",true)
        editor.apply()
    }

}