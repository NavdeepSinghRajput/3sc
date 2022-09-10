package com.task.a3sctask

import android.content.Intent
import android.os.Bundle
import com.task.a3sctask.home.HomeActivity

/**
 * @author Navdeep Singh
 * @since 07.09.2021
 */
class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        startActivity(Intent(baseContext,HomeActivity::class.java))
    }
}