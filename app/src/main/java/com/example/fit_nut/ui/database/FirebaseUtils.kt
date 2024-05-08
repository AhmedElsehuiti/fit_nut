package com.example.fit_nut.ui.database

import com.example.fit_nut.ui.model.AppUser
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.firestore
fun getCollection(collection:String): CollectionReference {
    val db = Firebase.firestore
    return db.collection(AppUser.COLLECTION_NAME)


}
fun addUserToFireStore(user:AppUser,
                       onSuccessListener:OnSuccessListener<Void>,
                       onFailureListener:OnFailureListener){

    val userCollection= getCollection(AppUser.COLLECTION_NAME)
    val userDoc =  userCollection.document(user.id!!)
    userDoc.set(user)
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
}
fun signIn(uid:String, onSuccessListener
:OnSuccessListener<DocumentSnapshot>, onFailureListener:OnFailureListener){
    val collectionRef = getCollection(AppUser.COLLECTION_NAME)

    collectionRef.document(uid)
        .get()
        .addOnSuccessListener (onSuccessListener)
        .addOnFailureListener(onFailureListener)
}