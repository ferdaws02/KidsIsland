package com.example.kidsislandv1

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2


class aboutAppEn : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_app_en)
        val continuebtn:ImageButton=findViewById(R.id.btnContinu)
        val viewpage: ViewPager2 = findViewById(R.id.viewPage)
        val fragments:ArrayList<Fragment> = arrayListOf(
        firstfragAboutUs(),
            secondFragmentAboutUs()
        )
        val adapter = ViewPagerAdapter(fragments, this)
        viewpage.adapter= adapter
        continuebtn.setOnClickListener {
            var mediaPlayer = MediaPlayer.create(this, R.raw.sound_button)
            mediaPlayer?.start()
            finish()
            startActivity(Intent(this,LoginActivity::class.java))  }
    }
}