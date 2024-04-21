package com.example.localimage

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.localimage.ui.fragments.LoginFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, LoginFragment())
            .addToBackStack(null)
            .commit()
    }
}