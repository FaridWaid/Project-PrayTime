package com.faridwaid.praytimeislamic.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.faridwaid.praytimeislamic.MainActivity
import com.faridwaid.praytimeislamic.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        //Menyembunyikan action bar
        supportActionBar?.hide()

        //Pindah activity setelah beberapa detik
        Handler().postDelayed({
            val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)

    }
}