package com.task.a3sctask

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author Navdeep Singh
 * @since 07.09.2021
 */
abstract class BaseActivity : AppCompatActivity() {
    private var compositeDisposable: CompositeDisposable? = null

    protected fun addToDisposablePool(disposable: Disposable): Boolean {
        var rez = false
        if (compositeDisposable != null) {
            rez = compositeDisposable!!.add(disposable)
        } else {
            disposable.dispose()
        }
        if (!rez) {
            Log.w("SubscribeOutStartStop", disposable.toString())
        }
        return rez
    }

    override fun onStart() {
        super.onStart()
        compositeDisposable = CompositeDisposable()
    }

    override fun onStop() {
        super.onStop()
        if (compositeDisposable != null) {
            compositeDisposable!!.dispose()
        }
    }

    protected fun cancelAllRequests() {
        if (compositeDisposable != null) {
            compositeDisposable!!.clear()
        }
    }

    protected val isStarted: Boolean
        get() = compositeDisposable != null && !compositeDisposable!!.isDisposed
}