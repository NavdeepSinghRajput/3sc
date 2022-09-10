package com.task.a3sctask

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * @author Navdeep Singh
 * @since 07.09.2021
 */
abstract class BaseActivity : AppCompatActivity() {

    fun showToast(message: String) {
        Toast.makeText(baseContext, message, Toast.LENGTH_SHORT).show()
    }


}