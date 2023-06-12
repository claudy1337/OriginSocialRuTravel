package com.autonture.originsocialrutravel.Utilis.ViewModels

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.autonture.originsocialrutravel.Utilis.Classes.Comments
import com.autonture.originsocialrutravel.Utilis.ConnectionService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class CommentViewModel : ViewModel() {

    private var mComments = MutableLiveData<List<Comments>>()
    val comments = mComments
    private var mError = MutableLiveData<String>()
    val error = mError
    private var mComm = MutableLiveData<Comments>()
    val comm = mComm

    private fun getPostsObservable(id:Int): Observable<Comments> {
        return ConnectionService().service().getCommentFlatId(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { comments ->
                mComments.value = comments
                Observable.fromIterable(comments)
                    .subscribeOn(Schedulers.io())
            }
    }

    private fun getCommentsObservable(comm: Comments): Observable<Comments> {
        return ConnectionService().service().getUserByPost(comm.usersRefId!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { user ->
                comm.User = user
                comm
            }
            .subscribeOn(Schedulers.io())
    }

    @SuppressLint("CheckResult")
    fun getComments(id:Int) {
        getPostsObservable(id)
            .flatMap { post -> getCommentsObservable(post) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { post -> mComm.value = post },
                onError = { e -> mError.value = e.localizedMessage }
            )
    }
}