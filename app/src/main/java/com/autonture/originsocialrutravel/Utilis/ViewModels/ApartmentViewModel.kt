package com.autonture.originsocialrutravel.Utilis.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.autonture.originsocialrutravel.Utilis.Classes.Apartment
import com.autonture.originsocialrutravel.Utilis.ConnectionService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class ApartmentViewModel: ViewModel() {

    private var mApartments = MutableLiveData<List<Apartment>>()
    val posts = mApartments
    private var mError = MutableLiveData<String>()
    val error = mError
    private var mApartment = MutableLiveData<Apartment>()
    val post = mApartment

    private fun getPostsObservable(): Observable<Apartment> {
        return ConnectionService().service().getApartments()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { posts ->
                mApartments.value = posts
                Observable.fromIterable(posts)
                    .subscribeOn(Schedulers.io())
            }
    }

    private fun getCommentsObservable(post: Apartment): Observable<Apartment> {
        return ConnectionService().service().getPhotosApartments(post.id!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { comments ->
                post.Photos = comments
                post
            }
            .subscribeOn(Schedulers.io())
    }

    fun getTowns() {
        val subscribeBy = getPostsObservable()
            .flatMap { post -> getCommentsObservable(post) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { post -> mApartment.value = post },
                onError = { e -> mError.value = e.localizedMessage }
            )
    }
}