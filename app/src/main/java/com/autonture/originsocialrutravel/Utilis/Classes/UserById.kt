package com.autonture.originsocialrutravel.Utilis.Classes

data class UserById (
    val id:Int? = null,
    val name:String,
    val surname:String,
    val email:String,
    val countOfTravels:Int? = null,
    val rating:Double? = null,
    val townsRefID:Int? = null,
)
