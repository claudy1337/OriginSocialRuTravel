package com.autonture.originsocialrutravel.Utilis.Classes

data class User(
    val id:Int? = null,
    val name:String,
    val surname:String,
    val login:String,
    val password:String,
    val email:String,
    val countOfTravels:Int? = null,
    val rating:Double? = null,
    val townsRefID:Int? = null,
)
