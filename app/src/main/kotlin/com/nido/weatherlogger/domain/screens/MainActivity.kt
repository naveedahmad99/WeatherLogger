package com.nido.weatherlogger.domain.screens

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.nido.weatherlogger.R

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TIME_INTERVAL = 1000 // # milliseconds, desired time passed between two back presses.
    }
    private var mBackPressed: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis()) {
            finish()
            return
        } else
            Toast.makeText(baseContext, "Tap again to exit", Toast.LENGTH_LONG).show()

        mBackPressed = System.currentTimeMillis()
    }
}
