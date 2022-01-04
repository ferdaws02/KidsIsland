package com.example.kidsislandv1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.kidsislandv1.levels.easygames.AudioPlay
import com.example.kidsislandv1.levels.easygames.play
import kotlinx.android.synthetic.main.activity_play.*
import java.util.*

class age : AppCompatActivity() {



    lateinit var sharedPreferences: SharedPreferences
    var isRemembered4 = false




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_age)
        val EasyEn: ImageButton = findViewById(R.id.EasyEn)







        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)

        isRemembered4 = sharedPreferences.getBoolean("CHECKBOX4", false)

        if (isRemembered4) {
            val intent = Intent(this, play::class.java)
            startActivity(intent)
            finish()
        }
        //button.setOnClickListener{

//            val name: String = editTextTextPersonName.text.toString()
//            val age: Int = editTextTextPersonName2.text.toString().toInt()

        // val checked: Boolean = checkBox.isChecked


        EasyEn.setOnClickListener {


            var soudClick = R.raw.sound_button
            AudioPlay.playAudioButton(this, soudClick)


            val editor: SharedPreferences.Editor = sharedPreferences.edit()
//            editor.putString("NAME", name)
//            editor.putInt("AGE", age)
            editor.putBoolean("CHECKBOX4", true)
            editor.apply()

            setLocate("en")
            recreate()
            val intent = Intent(this, play::class.java)
            startActivity(intent)
            finish()


        }

    }
   private fun setLocate(s: String) {
        val locale = Locale(s)

        Locale.setDefault(locale)

        val config = Configuration()

        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.putString("My_Lang", s)
        editor.putBoolean("CHECKBOX", true)
        editor.apply()




    }
    }
