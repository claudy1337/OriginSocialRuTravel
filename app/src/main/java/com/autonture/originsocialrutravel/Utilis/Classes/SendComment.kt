package com.autonture.originsocialrutravel.Utilis.Classes

data class SendComment (
    var text:String? = null,
    var date:String? = null,
    var usersRefId:Int? = null,
    var flatsRefId:Int? = null,
    var cafesRefId:Int? = null,
    var sightsRefId:Int? = null
)