package com.example.fit_nut.ui.user_information

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.example.fit_nut.ui.model.AppUser
import com.example.fit_nut.ui.regster.BaseViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.example.fit_nut.ui.user_information.UserInformationViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await
import java.lang.Exception


class UserInformationViewModel : BaseViewModel<Navigator>() {
    val gender = ObservableField<String>()
    val birth_day = ObservableField<String>()
    val birth_month = ObservableField<String>()
    val birth_year = ObservableField<String>()
    val height = ObservableField<String>()
    val wight = ObservableField<String>()
    val diseases = ObservableField<String>()

    fun start() {
        navigator?.openHome()
    }

    val db = FirebaseFirestore.getInstance()

    suspend fun getUserById(id: String): AppUser? {
        val appUser = db.collection(AppUser.COLLECTION_NAME)
            .document(id).get().await().toObject(AppUser::class.java)
        return appUser
    }

    fun updateUserData(
        userId: String
    ) {
        val user = db.collection(AppUser.COLLECTION_NAME).document(userId)

        user.update("height", height.get()?:"").addOnSuccessListener {
            start()
        }
        user.update("gender", gender.get()?:"")
        user.update("weight",wight.get()?:"")
        user.update("diseases", diseases.get()?:"")
        user.update("birthday",birth_day.get()?:"")
        user.update("birthMonth",birth_month.get()?:"")
        user.update("birthYear", birth_year.get()?:"")

    }
}

