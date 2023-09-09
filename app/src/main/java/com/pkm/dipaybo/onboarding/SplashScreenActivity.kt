package com.pkm.dipaybo.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.core.content.ContextCompat
import com.pkm.dipaybo.MainActivity
import com.pkm.dipaybo.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        window.statusBarColor = ContextCompat.getColor(this, R.color.blue_dark)
        supportActionBar?.hide()
        Handler().postDelayed({
            val i = Intent(this@SplashScreenActivity, MainActivity::class.java)
            startActivity(i)
            finish()
        }, SPLASH_TIME_OUT.toLong())
    }
    companion object {
        private const val SPLASH_TIME_OUT = 1500
    }
}