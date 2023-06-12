package com.autonture.originsocialrutravel.Utilis.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.autonture.originsocialrutravel.Utilis.Classes.Post
import com.autonture.originsocialrutravel.Utilis.ConnectionService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.kotlin.subscribeBy
import io.reactivex.rxjava3.schedulers.Schedulers

class PostViewModel : ViewModel() {

    private var mPosts = MutableLiveData<List<Post>>()
    val posts = mPosts
    private var mError = MutableLiveData<String>()
    val error = mError
    private var mPost = MutableLiveData<Post>()
    val post = mPost

    private fun getPostsObservable(id: Int): Observable<Post> {
        return ConnectionService().service().getPostUserId(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap { posts ->
                mPosts.value = posts
                Observable.fromIterable(posts)
                    .subscribeOn(Schedulers.io())
            }
    }

    private fun getImagePostObservable(post: Post): Observable<Post>? {
        return post.id?.let {
            ConnectionService().service().getPhotoPost(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { comments ->
                    post.Photos = comments
                    post
                }
                .subscribeOn(Schedulers.io())
        }
    }
    private fun getUserPostObservable(post: Post) : Observable<Post>? {
        return post.idUser?.let{
            ConnectionService().service().getUserPost(it)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { comments ->
                    post.User = comments
                    post
                }
                .subscribeOn(Schedulers.io())
        }
    }

    fun getPosts(id: Int) {
        val subscribeBy = getPostsObservable(id)
            .flatMap { post -> getUserPostObservable(post)!!}
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy(
                onNext = { post -> mPost.value = post },
                onError = { e -> mError.value = e.localizedMessage }
            )
    }
}