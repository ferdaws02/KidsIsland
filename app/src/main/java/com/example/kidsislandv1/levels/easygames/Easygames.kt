package com.example.kidsislandv1.levels.easygames

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.kidsislandv1.R
import com.example.kidsislandv1.levels.easygames.memorygame.MemoryLevel
import com.example.kidsislandv1.levels.easygames.memorygame.MemoryMainActivity
import com.example.kidsislandv1.levels.easygames.puzzelgame.MainpuzzelActivity

class Easygames : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easygames)


        val intro :ImageView =findViewById(R.id.puzzelin)
        val memory :ImageView =findViewById(R.id.memory)
        val profileNameEasygames =intent.getStringExtra("playername")


        intro.setOnClickListener {

            var soudClick = R.raw.sound_button
            AudioPlay.playAudioButton(this, soudClick)

            var intent = Intent(this, MainpuzzelActivity::class.java)
            intent.putExtra("profileNameEasygames", profileNameEasygames)
            finish()
            startActivity(intent)

        }
        memory.setOnClickListener {
            var soudClick = R.raw.sound_button
            AudioPlay.playAudioButton(this, soudClick)
            var intent = Intent(this, MemoryLevel::class.java)
            intent.putExtra("profileNameEasygames", profileNameEasygames)
            finish()
            startActivity(intent)

        }

    }

     override fun onBackPressed (){
         super.onBackPressed()
       var intent = Intent(this, play::class.java)
       startActivity(intent)

    }
}