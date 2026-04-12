package com.example.tsismisapp

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class exitPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exit_page)

        val exitApp = findViewById<Button>(R.id.exitBtn)
        exitApp.setOnClickListener { v ->
            finishAffinity()
            finish()
            System.exit(0)
        }
    }
}