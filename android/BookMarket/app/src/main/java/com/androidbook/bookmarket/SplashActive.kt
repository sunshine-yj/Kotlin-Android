package com.androidbook.bookmarket

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    val SPLASH_VIEW_TIME: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.splash)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imgLogo: ImageView = findViewById(R.id.imgLogo)
        val imgMan: ImageView = findViewById(R.id.imgMan)

        imgLogo.setBackgroundResource(R.drawable.splash_ani)
        imgMan.setBackgroundResource(R.drawable.splash_ani_man)

        val animLogo = imgLogo.getBackground() as AnimationDrawable
        animLogo.start()

        val animMan = imgMan.getBackground() as AnimationDrawable
        animMan.start()

        CoroutineScope(Dispatchers.Main).launch {
            delay(SPLASH_VIEW_TIME)
            val intent  = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}