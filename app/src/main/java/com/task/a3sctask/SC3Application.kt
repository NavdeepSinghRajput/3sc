package com.task.a3sctask

import android.app.Application
import android.content.Context
import com.task.a3sctask.utils.NetworkLiveData
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import java.io.IOException

class SC3Application : Application() {


    var appContext: Context? = null



    override fun onCreate() {
        super.onCreate()
        appContext = getApplicationContext()
        NetworkLiveData.init(this)
/*
//        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        Completable.fromAction {
            initRxErrorHandler()
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
        applicationInjectorComponent =  Dagg
                .builder()
            .applicationDaggerModule(ApplicationDaggerModule(this))
            .build()

        //applicationInjectorComponent!! initLifecycleHandler()*/
    }



    private fun initRxErrorHandler() {
        RxJavaPlugins.setErrorHandler { e: Throwable ->
            if (e is IOException) {
                // fine, irrelevant network problem or API that throws on cancellation
                return@setErrorHandler
            }
            if (e is InterruptedException) {
                // fine, some blocking code was interrupted by a dispose call
                return@setErrorHandler
            }
            if (e is NullPointerException || e is IllegalArgumentException) {
                // that's likely a bug in the application
                Thread.currentThread().uncaughtExceptionHandler
                    .uncaughtException(Thread.currentThread(), e)
                return@setErrorHandler
            }
            if (e is IllegalStateException) {
                // that's a bug in RxJava or in a custom operator
                Thread.currentThread().uncaughtExceptionHandler
                    .uncaughtException(Thread.currentThread(), e)
                return@setErrorHandler
            }
        }
    }
}