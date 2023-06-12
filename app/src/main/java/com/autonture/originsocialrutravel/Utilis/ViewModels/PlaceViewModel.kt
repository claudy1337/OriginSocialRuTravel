package com.autonture.originsocialrutravel.Utilis.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.autonture.originsocialrutravel.Utilis.Classes.Place
import com.autonture.originsocialrutravel.Utilis.Classes.Town
import com.autonture.originsocialrutravel.Utilis.ConnectionService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class PlaceViewModel : ViewModel() {

    private var mPlaces = MutableLiveData<List<Place>>()
    val places = mPlaces
    private var mError = MutableLiveData<String>()
    val error = mError
    private var mPlace = MutableLiveData<Place>()
    val place = mPlace


    private fun getPlaceObservable(): Observable<Place> {
        return ConnectionService().service().getPlaces()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { places ->
                mPlaces.value = places
                Observable.fromIterable(places)
                    .subscribeOn(Schedulers.io())
            }
    }
    private fun getPhotosObservable(place: Place): Observable<Place> {
        return ConnectionService().service().getPlacePhotos(place.id!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { photos ->
                place.Photos = photos
                place
            }
            .subscribeOn(Schedulers.io())
    }
    fun getPlaces() {
        val subscribeBy = getPlaceObservable()
            .flatMap { post -> getPhotosObservable(post) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { place -> mPlace.value = place },
                onError = { e -> mError.value = e.localizedMessage }
            )
    }
}