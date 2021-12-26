package com.example.kidsislandv1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock.sleep

import android.view.animation.AnimationUtils
import android.view.animation.AnimationUtils.loadAnimation
import android.widget.ImageView
import androidx.core.os.HandlerCompat.postDelayed


class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val islandimage :ImageView=findViewById(R.id.island)
        val waterAnimation= AnimationUtils.loadAnimation(this,R.anim.slidewater)
        val topAnimation= AnimationUtils.loadAnimation(this,R.anim.slide)

        islandimage.startAnimation(topAnimation)
        Handler().postDelayed({
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        },2000)

        val waterimg:ImageView=findViewById(R.id.water)
        waterimg.startAnimation(waterAnimation)
        Handler().postDelayed({
            finish()
        },3000)
        val nameapp:ImageView=findViewById(R.id.nameapp)
        nameapp.startAnimation(waterAnimation)
        Handler().postDelayed({
            finish()
        },3000)
    }
}