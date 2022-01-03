package com.example.kidsislandv1

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.kidsislandv1.levels.easygames.AudioPlay
import com.example.kidsislandv1.levels.easygames.play
import kotlinx.android.synthetic.main.activity_play.*

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


            val intent = Intent(this, play::class.java)
            startActivity(intent)
            finish()


        }


//    override fun onBackPressed (){
//        super.onBackPressed()
//        var intent = Intent(this, aboutAppEn::class.java)
//        startActivity(intent)
//
//    }
    }
}