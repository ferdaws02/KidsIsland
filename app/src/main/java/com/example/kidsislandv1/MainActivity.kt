package com.example.kidsislandv1

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var Arabe : ImageButton
    lateinit var Français : ImageButton
    lateinit var English : ImageButton



    lateinit var sharedPreferences: SharedPreferences
    var isRemembered = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocate()
        setContentView(R.layout.activity_main)



//        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)





        Arabe=findViewById(R.id.ArabicBtn)
        Français=findViewById(R.id.françaisBtn)
        English=findViewById(R.id.EnglishBtn)


        Arabe.setOnClickListener {
            var mediaPlayer = MediaPlayer.create(this, R.raw.sound_button)

            mediaPlayer?.start()
            setLocate("ar")
            recreate()
            finish()
            startActivity(Intent(this, aboutAppEn::class.java))
        }
        Français.setOnClickListener {
            var mediaPlayer = MediaPlayer.create(this, R.raw.sound_button)

            mediaPlayer?.start()
            setLocate("fr")
            recreate()
            finish()
            startActivity(Intent(this, aboutAppEn::class.java))
        }
        English.setOnClickListener {
            var mediaPlayer = MediaPlayer.create(this, R.raw.sound_button)

            mediaPlayer?.start()
            setLocate("en")
            recreate()
            finish()
            startActivity(Intent(this, aboutAppEn::class.java))
        }
        //finish()

    }



    private fun setLocate(s: String) {
        val locale = Locale(s)

        Locale.setDefault(locale)

        val config = Configuration()

        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        isRemembered=sharedPreferences.getBoolean("CHECKBOX",false)

        if (isRemembered){
            val intent = Intent(this,aboutAppEn::class.java)
            startActivity(intent)
            finish()
        }
        val editor = sharedPreferences.edit()
        editor.putString("My_Lang", s)
        editor.putBoolean("CHECKBOX", true)
        editor.apply()




    }
    private fun loadLocate() {
        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", "")
        if (language != null) {
            setLocate(language)
        }
    }
}