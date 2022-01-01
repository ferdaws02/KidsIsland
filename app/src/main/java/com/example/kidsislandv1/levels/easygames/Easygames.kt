package com.example.kidsislandv1.levels.easygames

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kidsislandv1.R
import com.example.kidsislandv1.levels.easygames.memorygame.MemoryLevel
import com.example.kidsislandv1.levels.easygames.memorygame.MemoryMainActivity
import com.example.kidsislandv1.levels.easygames.puzzelgame.MainpuzzelActivity

class Easygames : AppCompatActivity() {





    lateinit var Database_easygames : PlayerDataBase_easygames






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easygames)


        val intro :ImageView =findViewById(R.id.puzzelin)
        val memory :ImageView =findViewById(R.id.memory)

        loadData()

        intro.setOnClickListener {

            var soudClick = R.raw.sound_button
            AudioPlay.playAudioButton(this, soudClick)
            var intent = Intent(this, MainpuzzelActivity::class.java)
            finish()
            startActivity(intent)

        }
        memory.setOnClickListener {
            var soudClick = R.raw.sound_button
            AudioPlay.playAudioButton(this, soudClick)
            var intent = Intent(this, MemoryLevel::class.java)
            finish()
            startActivity(intent)

        }

    }

     override fun onBackPressed (){
         super.onBackPressed()

       var intent = Intent(this, play::class.java)
       startActivity(intent)

    }


    private fun loadData() {

       var textid: TextView = findViewById(R.id.idtextplayer)
      var textscore: TextView = findViewById(R.id.idtextscore)
        val sharedPreferences = getSharedPreferences( "sharedPrefs", Context.MODE_PRIVATE)
        val savedString = sharedPreferences.getString( "STRING_KEY",  null)
        val savedScore = sharedPreferences.getString( "STRING_KEYSCORE",  null)

        textid.setText(savedString)
        textscore.setText(scorebyname (savedString.toString()))

    }


    fun scorebyname (name:String) : String {
        Database_easygames =  PlayerDataBase_easygames.getDatabase(this )
        var score = Database_easygames.DAOplayer_easygames().getPlayerbyName(name).toString()
        return score
    }




}