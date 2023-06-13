package com.autonture.originsocialrutravel.Utilis.Classes

data class SendPhoto (
    var name        : String? = null,
    var photo       : String? = null,
    var usersRefId  : String? = null,
    var flatsRefId  : String? = null,
    var cafesRefId  : String? = null,
    var sightsRefId : String? = null,
    var townsRefId  : Int?    = null,
    var postsRefId  : String? = null
)