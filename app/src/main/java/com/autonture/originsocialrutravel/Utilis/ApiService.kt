package com.autonture.originsocialrutravel.Utilis

import com.autonture.originsocialrutravel.Utilis.Classes.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("user/Users/")
    fun createUser(@Body user: User): Call<Void>

    @PUT("users/{id}")
    fun updateUser(@Path("id") userId: Int, @Body user: User): Call<User>

    @GET("user/Users/UserByLogin{login}")
    fun getLogin(@Path("login") login: String): Call<User>

    @GET("user/Users/UserById/{id}")
    fun getId(@Path("id") id: Int): Call<User>

    @GET("comment/Comments/CommentsByFlatId/{flatRefId}")
    fun getCommentFlatId(@Path("flatRefId") flatRefId: Int): io.reactivex.rxjava3.core.Observable<List<Comments>>


    @GET("comment/Comments/CommentsBySightId/{sightsRefId}")
    fun getCommentPlaceId(@Path("sightsRefId") sightsRefId: Int): io.reactivex.rxjava3.core.Observable<List<Comments>>

    @POST("comment/Comments")
    fun createComment(@Body comment: SendComment): Call<Void>

    @GET("Towns/")
    fun getTowns(): io.reactivex.rxjava3.core.Observable<List<Town>>
    @GET("Towns/{townsId}")
    fun getTownsId(@Path("townsId") townsId: Int): Call<Town>

    @GET("photo/Photos/PhotoByFlatId/{Id}")
    fun getPhotoApartmentId(@Path("Id") Id: Int): Call<Photo>

    @GET("user/Users/UserById/{id}")
    fun getUserByPost(@Path("id", encoded = false) id: Int): io.reactivex.rxjava3.core.Observable<User>


    @GET("photo/Photos/PhotoByTownId/{id}")
    fun getPhotos(@Path("id", encoded = false) id: Int): io.reactivex.rxjava3.core.Observable<List<Photo>>


    @GET("flat/Flats/{id}")
    fun getApartment(@Path("id") id: Int): Call<Apartment>
    @GET("post/Posts/PostByUserId/{id}")
    fun getPostUserId(@Path("id") id: Int): io.reactivex.rxjava3.core.Observable<List<Post>>
    @GET("photo/Photos/PhotoByPostId/{id}")
    fun getPhotoPost(@Path("id", encoded = false) id: Int): io.reactivex.rxjava3.core.Observable<List<Photo>>
    @GET("user/Users/UserById/{id}")
    fun getUserPost(@Path("id", encoded = false) id: Int): io.reactivex.rxjava3.core.Observable<User>

    @GET("sight/Sights/")
    fun getPlaces(): io.reactivex.rxjava3.core.Observable<List<Place>>

    @GET("Towns/{townsRefID}")
    fun getTownId(@Path("townsRefID") townsRefID: Int): Call<Town>

    @GET("photo/Photos/PhotoBySightId/{sightsRefId}")
    fun getPlaceImage(@Path("sightsRefId") sightsRefId: Int): Call<Photo>

    @GET("photo/Photos/PhotoBySightId/{sightsRefId}")
    fun getPlacePhotos(@Path("sightsRefId", encoded = false) sightsRefId: Int): io.reactivex.rxjava3.core.Observable<List<Photo>>

    @GET("flat/Flats/")
    fun getApartments(): io.reactivex.rxjava3.core.Observable<List<Apartment>>

    @GET("photo/Photos/PhotoByFlatId/{id}")
    fun getPhotosApartments(@Path("id", encoded = false) id: Int): io.reactivex.rxjava3.core.Observable<List<Photo>>



}