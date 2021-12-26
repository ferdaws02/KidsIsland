package com.example.kidsislandv1

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.kidsislandv1.levels.easygames.AudioPlay
import com.example.kidsislandv1.levels.easygames.play

class age : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_age)
        val EasyEn :ImageButton =findViewById(R.id.EasyEn)
        EasyEn.setOnClickListener {


            var soudClick = R.raw.sound_button
            AudioPlay.playAudioButton(this, soudClick)


            finish()
            startActivity(Intent(this, play::class.java)) }
    }


//    override fun onBackPressed (){
//        super.onBackPressed()
//        var intent = Intent(this, aboutAppEn::class.java)
//        startActivity(intent)
//
//    }
}