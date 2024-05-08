package com.example.fit_nut.ui.login

import android.util.Log
import androidx.databinding.ObservableField
import com.example.fit_nut.ui.model.AppUser
import com.example.fit_nut.ui.regster.BaseViewModel
import com.example.fit_nut.ui.database.signIn
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class LoginViewModel:BaseViewModel<Navigator>() {
    val email = ObservableField<String>()
    val password  = ObservableField<String>()
    val emailError = ObservableField<String>()
    val passwordError  = ObservableField<String>()
    private val firebaseAuth =Firebase.auth

    fun login(){
        if (validate()){
         authWithFirebaseAuth()
        }
    }

    fun authWithFirebaseAuth(){
        showLading.value = true
        firebaseAuth.signInWithEmailAndPassword(email.get()!!,password.get()!!)
            .addOnCompleteListener{task->
                showLading.value = false
                if (!task.isSuccessful){
                    messageLiveData.value =task.exception?.localizedMessage
                    Log.e("firebase","failed"+task.exception?.localizedMessage)

                }
                else{
                   // messageLiveData.value = "Successful registration"
                    Log.e("firebase","success registration")
                  //  navigator?.openHomeScreen()
                    checkUserFromFireStore(task.result.user?.uid)
                }
            }

    }

    private fun checkUserFromFireStore(uid:String?) {
        showLading.value=true
        signIn(uid!!, OnSuccessListener {docSnapshot->
            showLading.value = false
            val user= docSnapshot.toObject(AppUser::class.java)
            if (user==null ){
                messageLiveData.value="Invalid email or password"
                return@OnSuccessListener
            }
            navigator?.openHomeScreen()

        }) {
            showLading.value = false
            messageLiveData.value = it.localizedMessage
        }

    }
    fun validate():Boolean{
        var valid = true

        if (email.get().isNullOrBlank()){
            emailError.set("This field is required")
            valid= false

        }else{
            emailError.set(null)
        }
        if (password.get().isNullOrBlank()){
            passwordError.set("This field is required")
            valid= false

        }else{
            passwordError.set(null)
        }
        return valid
    }
    fun openRegister(){
        navigator?.openRegisterationScreen()

    }
}