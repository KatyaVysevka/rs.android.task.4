//package com.example.workingwithstorage.livedata
//
//import android.content.SharedPreferences
//import androidx.lifecycle.MutableLiveData
//import java.util.*
//
//class LivePreference <T> constructor(private val updates: Observable<String>,
//                                     private val preferences: SharedPreferences,
//                                     private val key: String,
//                                     private val defaultValue: T) : MutableLiveData<T>() {
//
//    private var disposable: Disposable? = null
//
//    override fun onActive() {
//        super.onActive()
//        value = (preferences.all[key] as T) ?: defaultValue
//
//        disposable = updates.filter { t -> t == key }.subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread()).subscribeWith(object: DisposableObserver<String>() {
//                override fun onComplete() {
//
//                }
//
//                override fun onNext(t: String) {
//                    postValue((preferences.all[t] as T) ?: defaultValue)
//                }
//
//                override fun onError(e: Throwable) {
//
//                }
//            })
//    }
//
//    override fun onInactive() {
//        super.onInactive()
//        disposable?.dispose()
//    }
//}