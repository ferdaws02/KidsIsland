package com.example.kidsislandv1.levels.easygames.memorygame

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.kidsislandv1.R
import com.example.kidsislandv1.levels.easygames.AudioPlay
import com.example.kidsislandv1.levels.easygames.Easygames

class MemoryLevel : AppCompatActivity() {

    var mMediaPlayer: MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_level)

        var textid: TextView = findViewById(R.id.idtextplayer)
        var textscore: TextView = findViewById(R.id.idtextscore)

        val playernamememorygameActivity = intent.getStringExtra("profileNameEasygames")
        textid.setText(playernamememorygameActivity)

        var buttonlevelopen1 : ImageView = findViewById(R.id.idlevelopen1)

        buttonlevelopen1.setOnClickListener {

            var soudClick = R.raw.sound_button
            AudioPlay.playAudioButton(this, soudClick)
            var intent = Intent(this, MemoryMainActivity::class.java)
            intent.putExtra("profileNameEasygames", playernamememorygameActivity)
            finish()
            startActivity(intent)


        }




    }




    // 1. Plays the water sound
    fun playSound() {

        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.sound_music_puzzel_game)
            mMediaPlayer!!.isLooping = true
            mMediaPlayer!!.start()
        } else mMediaPlayer!!.start()
    }

    // 2. Pause playback
    fun pauseSound(view: View) {
        if (mMediaPlayer != null && mMediaPlayer!!.isPlaying) mMediaPlayer!!.pause()
    }

    // 3. {optional} Stops playback
    fun stopSound() {
        if (mMediaPlayer != null) {
            mMediaPlayer!!.stop()
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }

    // 4. Closes the MediaPlayer when the app is closed
    override fun onStop() {
        super.onStop()
        if (mMediaPlayer != null) {
            mMediaPlayer!!.release()
            mMediaPlayer = null
        }
    }




    override fun onBackPressed (){
        super.onBackPressed()
        stopSound()
        var intent = Intent(this, Easygames::class.java)
        startActivity(intent)

    }
}