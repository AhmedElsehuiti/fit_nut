package com.example.fit_nut.ui.regster

import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.fit_nut.ui.regster.BaseViewModel

open abstract class BaseActivity<DB : ViewDataBinding, VM: BaseViewModel<*>>
    :AppCompatActivity(){
    lateinit var viewModel: VM
    lateinit var viewDataBinding: DB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        viewModel = initViewModel()
        subscribeToLiveData()
    }
    abstract fun getLayoutId(): Int
    abstract fun initViewModel(): VM
    fun subscribeToLiveData(){
        viewModel.messageLiveData.observe(this) { message ->
            showDialog(message,"ok")


        }
        viewModel.showLading.observe(this) { show ->
             if (show)
                 showLoading()
            else hideLoading()
        }
    }
    var alertDialog : AlertDialog?= null
    fun showDialog(message:String,
                   posActionName:String?=null,
                   posAction:DialogInterface.OnClickListener?=null,
                   negActionName:String?=null,
                   negAction:DialogInterface.OnClickListener?=null,
                   cancelable:Boolean=true
    ){
        val defAction = DialogInterface.OnClickListener { dialog, p1 -> dialog?.dismiss() }
        val builder= AlertDialog.Builder(this)
             .setMessage(message)
        if (posActionName!=null)
            builder.setPositiveButton(posActionName,
            posAction ?: defAction
        )
        if (negActionName!=null)
            builder.setNegativeButton(negActionName,negAction?:defAction)
        builder.setCancelable(cancelable)
      val alertDialog=   builder.show()
    }
    fun hideAlertDialog(){
      alertDialog?.dismiss()
        alertDialog=null
    }
    var progressDialog : ProgressDialog?=null
    fun showLoading(){
        progressDialog= ProgressDialog(this)
        progressDialog?.setMessage("Loading....")
        progressDialog?.setCancelable(false)
        progressDialog?.show()
    }
    fun hideLoading(){
        progressDialog?.dismiss()
        progressDialog =null
    }
}