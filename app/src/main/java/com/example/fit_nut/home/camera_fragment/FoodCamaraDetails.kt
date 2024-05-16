package com.example.fit_nut.home.camera_fragment

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.fit_nut.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class FoodCamaraDetails : BottomSheetDialogFragment(){
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_food_camara_details, container, false)
    }



}