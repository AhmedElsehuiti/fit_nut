package com.example.fit_nut.ui.model

data class AppUser (
    var id:String?=null,
    var userName :String?=null,
    var fullName:String?=null,
    var email :String?=null,
    var gender : String = "",
    var birthday : String = "",
    var birthMonth : String = "",
    var birthYear : String = "",
    var height : String = "",
    var weight : String = "",
    var diseases : String = "",
)
{companion object{
    const val COLLECTION_NAME ="users"}
}