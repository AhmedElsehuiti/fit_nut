package com.example.fit_nut.ui.regster

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
@BindingAdapter("app:error")
fun setError(textInputLayout: TextInputLayout,error:String?){
    textInputLayout.error = error
}