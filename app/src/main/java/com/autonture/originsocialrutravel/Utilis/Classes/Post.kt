package com.autonture.originsocialrutravel.Utilis.Classes

data class Post(
    var id:Int? = null,
    var title: String? = null,
    var text: String? = null,
    var usersId:Int? = null,
    var date: String? = null,
    var Photos: List<Photo>,
    var User: User
):java.io.Serializable
