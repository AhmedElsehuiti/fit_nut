package com.example.fit_nut.ui.regster

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel<N>: ViewModel() {
    val messageLiveData = MutableLiveData<String>()
    val showLading = MutableLiveData<Boolean>()
    var navigator:N?=null
}