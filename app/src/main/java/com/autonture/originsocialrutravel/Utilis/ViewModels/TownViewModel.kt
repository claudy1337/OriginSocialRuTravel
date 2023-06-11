package com.autonture.originsocialrutravel.Utilis.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.autonture.originsocialrutravel.Utilis.Classes.Town
import com.autonture.originsocialrutravel.Utilis.ConnectionService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class TownViewModel : ViewModel() {

    private var mTowns = MutableLiveData<List<Town>>()
    val towns = mTowns
    private var mError = MutableLiveData<String>()
    val error = mError
    private var mTown = MutableLiveData<Town>()
    val town = mTown


    private fun getTownsObservable(): Observable<Town> {
        return ConnectionService().service().getTowns()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { towns ->
                mTowns.value = towns
                Observable.fromIterable(towns)
                    .subscribeOn(Schedulers.io())
            }
    }
    private fun getPhotosObservable(town: Town): Observable<Town> {
        return ConnectionService().service().getPhotos(town.id!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { photos ->
                town.Photos = photos
                town
            }
            .subscribeOn(Schedulers.io())
    }
    fun getTowns() {
        val subscribeBy = getTownsObservable()
            .flatMap { post -> getPhotosObservable(post) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { town -> mTown.value = town },
                onError = { e -> mError.value = e.localizedMessage }
            )
    }
}