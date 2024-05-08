package com.example.fit_nut.ui.regster

import android.util.Log
import androidx.databinding.ObservableField
import com.example.fit_nut.ui.database.addUserToFireStore
import com.example.fit_nut.ui.database.getCollection
import com.example.fit_nut.ui.model.AppUser
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest

class RegisterViewModel : BaseViewModel<Navigator>() {
    val fullName = ObservableField<String>()
    val userName = ObservableField<String>()
    val email = ObservableField<String>()
    val password = ObservableField<String>()
    val emailError = ObservableField<String>()
    val passwordError = ObservableField<String>()
    val fullNameError = ObservableField<String>()
    val userNameError = ObservableField<String>()
    val auth = Firebase.auth


    fun createAccount() {

        if (validate()) {
            addAccountToFirebase(

            )
        }
    }

    fun addAccountToFirebase() {
        showLading.value = true
        auth.createUserWithEmailAndPassword(email.get() ?: "", password.get()?:"")
            .addOnCompleteListener { task ->
                showLading.value = false
                if (!task.isSuccessful) {
                    messageLiveData.value = task.exception?.localizedMessage
                    Log.e("firebase", "failed" + task.exception?.localizedMessage)
                } else {
                    sendEmailVerification()
//                   messageLiveData.value = "Successful registration"
//                   Log.e("firebase","success registration")
                    createFireStoreUser(
                        userName = userName.get() ?:"",
                        name = fullName.get() ?:"",
                        emaiil = email.get()?:"",
                        userId = task.result.user?.uid ?:""
                    )

                }
            }

    }

    fun createFireStoreUser(
        name: String,
        userId: String,
        emaiil: String,
        userName: String
    ) {
        val collectionRef = getCollection(AppUser.COLLECTION_NAME)
        val appUser = AppUser(
            userName = userName,
            fullName = name,
            email = emaiil,
            id = userId
        )
        collectionRef.document(userId).set(appUser).addOnSuccessListener {
            navigator?.openHomeScreen()
        }
    }


//    fun createFirestoreUser(uid: String?) {
//        showLading.value = true
//        val user = AppUser(
//            id = uid,
//            fullName = fullName.get(),
//            userName = userName.get(),
//            email = email.get()
//        )
//        addUserToFireStore(user,
//            OnSuccessListener {
//                val currentUser = auth.currentUser
//                val profileUpdates = userProfileChangeRequest {
//                    displayName = userName.get()
//                }
//                currentUser?.updateProfile(profileUpdates)?.addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        showLading.value = false
//                        navigator?.openHomeScreen()
//                    } else {
//                        showLading.value = false
//                        messageLiveData.value =
//                            "Failed to update display name: ${task.exception?.localizedMessage}"
//                    }
//                }
//
//            }, OnFailureListener {
//                showLading.value = false
//                messageLiveData.value = it.localizedMessage
//
//            })
//
//
//    }


    fun validate(): Boolean {
        var valid = true
        if (fullName.get().isNullOrBlank()) {
            fullNameError.set("This field is required")
            valid = false

        } else {
            fullNameError.set(null)
        }

        if (userName.get().isNullOrBlank()) {
            userNameError.set("This field is required")
            valid = false

        } else {
            userNameError.set(null)
        }
        if (email.get().isNullOrBlank()) {
            emailError.set("This field is required")
            valid = false

        } else {
            emailError.set(null)
        }
        if (password.get().isNullOrBlank()) {
            passwordError.set("This field is required")
            valid = false

        } else {
            passwordError.set(null)
        }
        return valid
    }

    private fun sendEmailVerification() {
        val user = auth.currentUser
        user?.sendEmailVerification()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("EmailVerification", "Email sent.")

                } else {
                    Log.e("EmailVerification", "Failed to send email: ${task.exception?.message}")

                }
            }
    }
}