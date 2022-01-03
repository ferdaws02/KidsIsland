package com.example.kidsislandv1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2


class aboutAppEn : AppCompatActivity() {



    lateinit var sharedPreferences: SharedPreferences
    var isRemembered = false





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





        sharedPreferences = getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)

        isRemembered=sharedPreferences.getBoolean("CHECKBOX",false)

        if (isRemembered){
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        continuebtn.setOnClickListener{


            var mediaPlayer = MediaPlayer.create(this, R.raw.sound_button)
            mediaPlayer?.start()

            val editor: SharedPreferences.Editor = sharedPreferences. edit()

            editor.putBoolean("CHECKBOX", true)
            editor.apply()
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()



        }
    }






    }
