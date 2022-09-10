package com.task.a3sctask

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.task.a3sctask.home.HomeActivity

/**
 * @author Navdeep Singh
 * @since 07.09.2021
 */
class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        callingMainActivity()
    }

    private fun callingMainActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(baseContext, HomeActivity::class.java))
            finish()
        }, 2000)
    }
}