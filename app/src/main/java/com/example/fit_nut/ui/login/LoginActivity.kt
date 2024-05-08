package com.example.fit_nut.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.fit_nut.R
import com.example.fit_nut.databinding.ActivityLoginBinding
import com.example.fit_nut.home.HomeActivity
import com.example.fit_nut.ui.model.AppUser
import com.example.fit_nut.ui.regster.BaseActivity
import com.example.fit_nut.ui.regster.RegisterActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class LoginActivity:BaseActivity<ActivityLoginBinding, LoginViewModel>(), Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.vm= viewModel
        viewModel.navigator = this

    }



    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initViewModel(): LoginViewModel {
        return ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun openHomeScreen() {
        val intent = Intent(this , HomeActivity::class.java)
        startActivity(intent)
    }

    override fun openRegisterationScreen() {
        val intent = Intent(this , RegisterActivity::class.java)
        startActivity(intent)
    }


}