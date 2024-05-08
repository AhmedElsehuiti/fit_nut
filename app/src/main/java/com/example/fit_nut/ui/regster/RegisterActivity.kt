package com.example.fit_nut.ui.regster

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.fit_nut.R
import com.example.fit_nut.databinding.ActivityRegisterBinding
import com.example.fit_nut.home.HomeActivity
import com.example.fit_nut.ui.regster.BaseActivity
import com.example.fit_nut.ui.user_information.UserInformationActivity

class RegisterActivity :  BaseActivity<ActivityRegisterBinding, RegisterViewModel>(),Navigator
       {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.vm = viewModel
        viewModel.navigator = this

    }
           override fun getLayoutId(): Int {
               return R.layout.activity_register
           }

           override fun initViewModel(): RegisterViewModel {
               return ViewModelProvider(this)[RegisterViewModel::class.java]
           }

           override fun openHomeScreen() {
               val intent = Intent(this , UserInformationActivity::class.java)
               startActivity(intent)
           }
       }