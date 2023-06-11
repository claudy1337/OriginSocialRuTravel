package com.autonture.originsocialrutravel.Utilis.Classes

data class Town(
    var id:Int? = null,
    var name:String? = null,
    var rating: Float? = null,
    var description :String? = null,
    var Photos: List<Photo>
):java.io.Serializable
