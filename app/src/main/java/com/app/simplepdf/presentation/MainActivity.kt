package com.app.simplepdf.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.app.simplepdf.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
    }
}