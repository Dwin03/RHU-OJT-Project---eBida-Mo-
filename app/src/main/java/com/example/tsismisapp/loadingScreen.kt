package com.example.tsismisapp

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class loadingScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading_screen)

        val handler = window.decorView
        handler.postDelayed({

//            val imageView: ImageView = findViewById(R.id.loadingGif)
//            Glide.with(this)
//                .asGif()
//                .load(R.drawable.new_loadingscreen_gif)
//                .into(imageView)

            val nextPage = Intent(this@loadingScreen, termsAndAgreements::class.java)
            startActivity(nextPage)
            finish()
        }, 3000)
    }
}