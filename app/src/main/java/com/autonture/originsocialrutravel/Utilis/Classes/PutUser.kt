package com.autonture.originsocialrutravel.Utilis.Classes

data class PutUser (
    val name:String,
    val surname:String,
    val login:String,
    val password:String,
    val email:String,
    val countOfTravels:Int? = null,
    val rating:Double? = null,
    val townsRefID:Int? = null,
)