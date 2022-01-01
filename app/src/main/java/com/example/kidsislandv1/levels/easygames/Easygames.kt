package com.example.kidsislandv1.levels.easygames

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.kidsislandv1.R
import com.example.kidsislandv1.levels.easygames.memorygame.MemoryLevel
import com.example.kidsislandv1.levels.easygames.memorygame.MemoryMainActivity
import com.example.kidsislandv1.levels.easygames.puzzelgame.MainpuzzelActivity
import kotlinx.android.synthetic.main.activity_easygames.*

class Easygames : AppCompatActivity() {





    lateinit var Database_easygames : PlayerDataBase_easygames






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easygames)


        val back:ImageView = findViewById(R.id.back)

        val intro :ImageView =findViewById(R.id.puzzelin)
        val memory :ImageView =findViewById(R.id.memory)

        loadData()
back.setOnClickListener { onBackPressed () }

        val sharedPreferences = getSharedPreferences( "sharedPrefs", Context.MODE_PRIVATE)
        val savedString = sharedPreferences.getString( "STRING_KEY",  null)


        var selectavatarbutton : ImageView = findViewById(R.id.idbtnselectavatar)

        var drawableRessource = when (savedString){

            "avatar1"->R.drawable.avatar1
            "avatar2"->R.drawable.avatar2
            "avatar3"->R.drawable.avatar3
            "avatar4"->R.drawable.avatar4
            "avatar5"->R.drawable.avatar5
            else ->R.drawable.avatar6
        }
        selectavatarbutton.setImageResource(drawableRessource)
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