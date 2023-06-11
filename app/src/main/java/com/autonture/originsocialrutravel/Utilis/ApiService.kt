package com.autonture.originsocialrutravel.Utilis

import com.autonture.originsocialrutravel.Utilis.Classes.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    @POST("user/Users/")
    fun createUser(@Body user: User): Call<Void>

    @GET("user/Users/UserByLogin{login}")
    fun getLogin(@Path("login") login: String): Call<User>

}