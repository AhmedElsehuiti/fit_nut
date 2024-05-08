package com.example.fit_nut.screen

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.navigation.fragment.findNavController
import com.example.fit_nut.R

class FourthScreenFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_fourth_screen, container, false)
        val finish=view.findViewById<AppCompatButton>(R.id.button4)
        finish.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardingFragment_to_homeActivity)
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