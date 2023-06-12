package com.autonture.originsocialrutravel.Utilis.Classes

data class Place(
    var id:Int? = null,
    var title:String? = null,
    var address:String? = null,
    var townsRefID:Int? = null,
    val rating:Int? = null,
    var Photos: List<Photo>
):java.io.Serializable

