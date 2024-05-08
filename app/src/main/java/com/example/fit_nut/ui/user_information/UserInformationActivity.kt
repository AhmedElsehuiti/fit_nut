package com.example.fit_nut.ui.user_information

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Spinner

import androidx.lifecycle.ViewModelProvider
import com.example.fit_nut.R
import com.example.fit_nut.databinding.ActivityUserInformationBinding
import com.example.fit_nut.home.HomeActivity
import com.example.fit_nut.ui.regster.BaseActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.runBlocking


class UserInformationActivity : BaseActivity<ActivityUserInformationBinding,UserInformationViewModel>(),Navigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.vm = viewModel
        viewModel.navigator =this
        val currentUser = Firebase.auth.currentUser?.uid ?: ""

        viewDataBinding.start.setOnClickListener{
            viewModel.updateUserData(currentUser)
        }

        val genderOptions = arrayOf("Male", "Female")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, genderOptions)
        viewDataBinding.genderAutoCompleteTextView.setAdapter(adapter)
    }

    override fun getLayoutId(): Int {
      return  R.layout.activity_user_information
    }

    override fun initViewModel(): UserInformationViewModel {
        return ViewModelProvider(this)[UserInformationViewModel::class.java]
    }

    override fun openHome() {
        val intent = Intent(this , HomeActivity::class.java)
        startActivity(intent)

    }


}






