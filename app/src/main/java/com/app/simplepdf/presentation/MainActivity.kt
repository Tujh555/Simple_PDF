package com.app.simplepdf.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.app.simplepdf.R
import com.app.simplepdf.appComponent
import com.app.simplepdf.databinding.ActivityMainBinding
import com.app.simplepdf.permissions.PermissionsManager
import com.app.simplepdf.presentation.mainScreen.MainFragment
import java.lang.ref.WeakReference
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MainFragment())
            .addToBackStack(null)
            .commit()
    }
}