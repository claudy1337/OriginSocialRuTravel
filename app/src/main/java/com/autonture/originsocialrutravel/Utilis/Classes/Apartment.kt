package com.autonture.originsocialrutravel.Utilis.Classes

data class Apartment(
    var id:Int? = null,
    var townsRefID:Int? = null,
    var countOfRooms:Int? = null,
    var floor:Int? = null,
    var pricePerMonth:Int? = null,
    var ownersPhone:String? = null,
    var rating: Float? = null,
    var address :String? = null,
    var Photos: List<Photo>
):java.io.Serializable
