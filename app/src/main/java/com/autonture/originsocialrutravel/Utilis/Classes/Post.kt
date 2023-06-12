package com.autonture.originsocialrutravel.Utilis.Classes

data class Post(
    var id:Int? = null,
    var title: String? = null,
    var text: String? = null,
    var idUser:Int? = null,
    var date: String? = null,
    var Photos: List<Photo>,
    var User: UserById
):java.io.Serializable
