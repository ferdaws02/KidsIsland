package com.example.kidsislandv1.levels.easygames

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

import com.example.kidsislandv1.age
import kotlinx.android.synthetic.main.coming_soon_dialog.view.*
import kotlinx.android.synthetic.main.lose_layout_dialog_easygames.*
import kotlinx.android.synthetic.main.lose_layout_dialog_easygames.view.*
import kotlinx.android.synthetic.main.rink_layout_dialog.view.*


import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kidsislandv1.R
import kotlinx.android.synthetic.main.activity_play.*
import kotlinx.android.synthetic.main.settings_layout_dialog.*
import kotlinx.android.synthetic.main.settings_layout_dialog.view.*

const val PREF_NAME = "LOGIN_PREF"
const val IMAGE = "IMAGE"

const val IS_REMEMBRED = "IS_REMEMBRED"
class play : AppCompatActivity() {


    lateinit var bntsplit :ImageView
    lateinit var bntshop  :ImageView
    lateinit var bntgift :ImageView
    lateinit var bntsetting :ImageView
    lateinit var scorehome :TextView
    lateinit var diamondhome :TextView




    lateinit var bntdatabase : ImageView
    var counter: Int = 0

    var mMediaPlayer: MediaPlayer? = null


    lateinit var PlayerAdapter: PlayerAdapter_easygames
    lateinit var playerList_easygames: MutableList<Player_easygames>
    lateinit var Database_easygames : PlayerDataBase_easygames
    lateinit var recylcerPlayer: RecyclerView
    lateinit var recylcerPlayerAdapter: PlayerAdapter_easygames


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)


         var soudClick = R.raw.sound_button
            var musicHome =  R.raw.sound_home_jumping_around



        Database_easygames = PlayerDataBase_easygames.getDatabase(this)
        playerList_easygames=ArrayList()
        PlayerAdapter = PlayerAdapter_easygames(playerList_easygames)

        bntdatabase = findViewById(R.id.databasebnt)


        bntsplit  = findViewById(R.id.idimagespin)
        bntshop  = findViewById(R.id.idimageshop)
        bntgift = findViewById(R.id.idimagegift)
        bntsetting = findViewById(R.id.idbuttonsetting)
        scorehome  = findViewById(R.id.idtextViewscorehome)
        diamondhome= findViewById(R.id.idtextViewdiamondhome)



        loadData ()


        playSound()



         bntsplit.setOnClickListener {

             //AudioPlay.lastResource.
             AudioPlay.playAudioButton(this, soudClick)



             val view = View.inflate(this@play, R.layout.coming_soon_dialog, null)

             val builder = AlertDialog.Builder(this@play)
             builder.setView(view)

             val dialog = builder.create()
             dialog.show()
             dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
             dialog.setCancelable(false)


             view.buttonidcancelcoming.setOnClickListener{
                 AudioPlay.playAudioButton(this, soudClick)
                // AudioPlay.continuePlaying(this, musicHome )
                 dialog.dismiss()}

        }

         bntsetting.setOnClickListener {

             AudioPlay.playAudioButton(this, soudClick)

             val view = View.inflate(this@play, R.layout.settings_layout_dialog, null)

             val builder = AlertDialog.Builder(this@play)
             builder.setView(view)

           //  AudioPlay.continuePlaying(this, musicHome)

             val dialog = builder.create()
             dialog.show()
             dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
             dialog.setCancelable(false)


             view.buttonidcancelsetting.setOnClickListener{
                 AudioPlay.playAudioButton(this, soudClick)
                 dialog.dismiss()}


             view.idOksetting.setOnClickListener{
                 AudioPlay.playAudioButton(this, soudClick)


             }

        }

         bntshop.setOnClickListener {
             AudioPlay.playAudioButton(this, soudClick)

             val view = View.inflate(this@play, R.layout.win_diamond_layout_dialog, null)

             val builder = AlertDialog.Builder(this@play)
             builder.setView(view)

             //  AudioPlay.continuePlaying(this, musicHome)

             val dialog = builder.create()
             dialog.show()
             dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
             dialog.setCancelable(false)


             view.buttonidcancelloserink.setOnClickListener{
                 AudioPlay.playAudioButton(this, soudClick)
                 dialog.dismiss()}





        }
         bntgift.setOnClickListener {
             AudioPlay.playAudioButton(this, soudClick)


           val view = View.inflate(this@play, R.layout.coming_soon_dialog, null)

           val builder = AlertDialog.Builder(this@play)
           builder.setView(view)

           val dialog = builder.create()
           dialog.show()
           dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
           dialog.setCancelable(false)


           view.buttonidcancelcoming.setOnClickListener{
               AudioPlay.playAudioButton(this, soudClick)
               dialog.dismiss()}

        }
        bntdatabase.setOnClickListener {


            AudioPlay.playAudioButton(this, soudClick)

        val view = View.inflate(this, R.layout.rink_layout_dialog, null)

        val builder = AlertDialog.Builder(this)
        builder.setView(view)

        val dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)

//
            recylcerPlayer=dialog.findViewById<View>(R.id.recycleViewdialogbox)as RecyclerView
            playerList_easygames = ArrayList()
            Database_easygames =  PlayerDataBase_easygames.getDatabase(this )

            playerList_easygames = Database_easygames.DAOplayer_easygames().getAllPlayer()

            recylcerPlayerAdapter = PlayerAdapter_easygames(playerList_easygames)

            recylcerPlayer.adapter = recylcerPlayerAdapter

            recylcerPlayer.layoutManager = LinearLayoutManager(this , LinearLayoutManager.VERTICAL ,false)


                view.buttonidcancelloserink.setOnClickListener{
                AudioPlay.playAudioButton(this, soudClick)
                dialog.dismiss()}





//            AudioPlay.playAudioButton(this, soudClick)
//            val intent =Intent(this,com.example.kidsislandv1.levels.easygames.Database_easygames::class.java)
//            startActivity(intent)



        }


             val selectavatarbutton : ImageView

        selectavatarbutton = findViewById(R.id.idbtnselectavatar)
        selectavatarbutton.setOnClickListener {
            AudioPlay.playAudioButton(this, soudClick)

            val view = View.inflate(this@play, R.layout.lose_layout_dialog_easygames, null)

            val builder = AlertDialog.Builder(this@play)
            builder.setView(view)

            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.setCancelable(false)


            view.idavatar1.setOnClickListener {
                AudioPlay.playAudioButton(this, soudClick)
                Database_easygames= PlayerDataBase_easygames.getDatabase(this)
                var score= Database_easygames.DAOplayer_easygames().getPlayerbyName("avatar1").toString()
                saveDataNS("avatar1",score)
                selectavatarbutton.setImageResource(R.drawable.avatar1)
                dialog.dismiss()


            }
            view.idavatar2.setOnClickListener {
                AudioPlay.playAudioButton(this, soudClick)
                Database_easygames= PlayerDataBase_easygames.getDatabase(this)
                var score= Database_easygames.DAOplayer_easygames().getPlayerbyName("avatar2").toString()
                saveDataNS("avatar2",score)
                selectavatarbutton.setImageResource(R.drawable.avatar2)
                dialog.dismiss()

            }
            view.idavatar3.setOnClickListener {
                AudioPlay.playAudioButton(this, soudClick)
                Database_easygames= PlayerDataBase_easygames.getDatabase(this)
                var score= Database_easygames.DAOplayer_easygames().getPlayerbyName("avatar3").toString()
                saveDataNS("avatar3",score)
                selectavatarbutton.setImageResource(R.drawable.avatar3)
                dialog.dismiss()

            }
            view.idavatar4.setOnClickListener {
                AudioPlay.playAudioButton(this, soudClick)
                Database_easygames= PlayerDataBase_easygames.getDatabase(this)
                var score= Database_easygames.DAOplayer_easygames().getPlayerbyName("avatar4").toString()
                saveDataNS("avatar4",score)
                selectavatarbutton.setImageResource(R.drawable.avatar4)

                dialog.dismiss()

            }
            view.idavatar5.setOnClickListener {
                AudioPlay.playAudioButton(this, soudClick)
                Database_easygames= PlayerDataBase_easygames.getDatabase(this)
                var score= Database_easygames.DAOplayer_easygames().getPlayerbyName("avatar5").toString()
                saveDataNS("avatar5",score)
                selectavatarbutton.setImageResource(R.drawable.avatar5)


                dialog.dismiss()

            }
            view.idavatar6.setOnClickListener {
                AudioPlay.playAudioButton(this, soudClick)
                Database_easygames= PlayerDataBase_easygames.getDatabase(this)
                var score= Database_easygames.DAOplayer_easygames().getPlayerbyName("avatar6").toString()
                saveDataNS("avatar6",score)
                selectavatarbutton.setImageResource(R.drawable.avatar6)

                dialog.dismiss()


            }

            view.buttonidcancellose.setOnClickListener{
                AudioPlay.playAudioButton(this, soudClick)
                dialog.dismiss()}

        }

        val sharedPreferences = getSharedPreferences( "sharedPrefs", Context.MODE_PRIVATE)
        val profileName=sharedPreferences.getString( "STRING_KEY",  null).toString()

        println("************************ta3tina il NAME yehdik*********  "+profileName)
        Database_easygames.DAOplayer_easygames().getAllPlayer()

        scorehome.setText(Database_easygames.DAOplayer_easygames().getPlayerbyName(profileName).toString())
        diamondhome.setText(Database_easygames.DAOplayer_easygames().getPlayerdiamondbyName(profileName).toString())


println("************************ta3tina il score yehdik*********  "+Database_easygames.DAOplayer_easygames().getPlayerbyName(profileName).toString())
        val play : ImageButton =findViewById(R.id.Play)
        play.setOnClickListener {
            AudioPlay.playAudioButton(this, soudClick)


                if (profileName == "avatar1") {



                   if (Database_easygames.DAOplayer_easygames().searchByName("avatar1")){
                       var player1=Player_easygames(1,"avatar1",Database_easygames.DAOplayer_easygames().getPlayerbyName("avatar1"),R.drawable.avatar1,R.drawable.purple_backgraundithem,Database_easygames.DAOplayer_easygames().getPlayerdiamondbyName("avatar1"))
                       Database_easygames.DAOplayer_easygames().insert(player1)
                       playerList_easygames.add(player1)
                       PlayerAdapter.PlayerList_easygames = playerList_easygames
                       PlayerAdapter.notifyDataSetChanged()


                   }else{

                    var player1=Player_easygames(1,"avatar1",0,R.drawable.avatar1,R.drawable.purple_backgraundithem,0)


                       Database_easygames.DAOplayer_easygames().insert(player1)
                    playerList_easygames.add(player1)
                    PlayerAdapter.PlayerList_easygames = playerList_easygames
                    PlayerAdapter.notifyDataSetChanged()}
                }

                else if (profileName == "avatar2") {
                    if (Database_easygames.DAOplayer_easygames().searchByName("avatar2")){
                        var player2=Player_easygames(2,"avatar2",Database_easygames.DAOplayer_easygames().getPlayerbyName("avatar2"),R.drawable.avatar2,R.drawable.bluegraundithem,Database_easygames.DAOplayer_easygames().getPlayerdiamondbyName("avatar2"))
                        Database_easygames.DAOplayer_easygames().insert(player2)
                        playerList_easygames.add(player2)
                        PlayerAdapter.PlayerList_easygames = playerList_easygames
                        PlayerAdapter.notifyDataSetChanged()
                    }else{

                        var player2=Player_easygames(2,"avatar2",0,R.drawable.avatar2,R.drawable.bluegraundithem,0)


                        Database_easygames.DAOplayer_easygames().insert(player2)
                        playerList_easygames.add(player2)
                        PlayerAdapter.PlayerList_easygames = playerList_easygames
                        PlayerAdapter.notifyDataSetChanged()}

                } else if (profileName == "avatar3") {
                    if (Database_easygames.DAOplayer_easygames().searchByName("avatar3")){
                        var player3=Player_easygames(3,"avatar3",Database_easygames.DAOplayer_easygames().getPlayerbyName("avatar3"),R.drawable.avatar3,R.drawable.pinkbackgraundithem,Database_easygames.DAOplayer_easygames().getPlayerdiamondbyName("avatar3"))
                        Database_easygames.DAOplayer_easygames().insert(player3)
                        playerList_easygames.add(player3)
                        PlayerAdapter.PlayerList_easygames = playerList_easygames
                        PlayerAdapter.notifyDataSetChanged()
                    }else{

                        var player3=Player_easygames(3,"avatar3",0,R.drawable.avatar3,R.drawable.pinkbackgraundithem,0)


                        Database_easygames.DAOplayer_easygames().insert(player3)
                        playerList_easygames.add(player3)
                        PlayerAdapter.PlayerList_easygames = playerList_easygames
                        PlayerAdapter.notifyDataSetChanged()
                    }
                } else if (profileName == "avatar4") {
                    if (Database_easygames.DAOplayer_easygames().searchByName("avatar4")){
                        var player4=Player_easygames(4,"avatar4",Database_easygames.DAOplayer_easygames().getPlayerbyName("avatar4"),R.drawable.avatar4,R.drawable.greybackgraundithem,Database_easygames.DAOplayer_easygames().getPlayerdiamondbyName("avatar4"))
                        Database_easygames.DAOplayer_easygames().insert(player4)
                        playerList_easygames.add(player4)
                        PlayerAdapter.PlayerList_easygames = playerList_easygames
                        PlayerAdapter.notifyDataSetChanged()
                    }else{

                        var player4=Player_easygames(4,"avatar4",0,R.drawable.avatar4,R.drawable.greybackgraundithem,0)


                        Database_easygames.DAOplayer_easygames().insert(player4)
                        playerList_easygames.add(player4)
                        PlayerAdapter.PlayerList_easygames = playerList_easygames
                        PlayerAdapter.notifyDataSetChanged()}

                } else if (profileName == "avatar5") {
                    if (Database_easygames.DAOplayer_easygames().searchByName("avatar5")){
                        var player5=Player_easygames(5,"avatar5",Database_easygames.DAOplayer_easygames().getPlayerbyName("avatar5"),R.drawable.avatar5,R.drawable.yellowbackgraundithem,Database_easygames.DAOplayer_easygames().getPlayerdiamondbyName("avatar5"))
                        Database_easygames.DAOplayer_easygames().insert(player5)
                        playerList_easygames.add(player5)
                        PlayerAdapter.PlayerList_easygames = playerList_easygames
                        PlayerAdapter.notifyDataSetChanged()
                    }else{

                        var player5=Player_easygames(5,"avatar5",0,R.drawable.avatar5,R.drawable.yellowbackgraundithem,0)


                        Database_easygames.DAOplayer_easygames().insert(player5)
                        playerList_easygames.add(player5)
                        PlayerAdapter.PlayerList_easygames = playerList_easygames
                        PlayerAdapter.notifyDataSetChanged()}
                } else {
                    if (Database_easygames.DAOplayer_easygames().searchByName("avatarr6")){
                        var player6=Player_easygames(6,"avatar6",Database_easygames.DAOplayer_easygames().getPlayerbyName("avatar6"),R.drawable.avatar6,R.drawable.blackbackgraundithem,Database_easygames.DAOplayer_easygames().getPlayerdiamondbyName("avatar6"))
                        Database_easygames.DAOplayer_easygames().insert(player6)
                        playerList_easygames.add(player6)
                        PlayerAdapter.PlayerList_easygames = playerList_easygames
                        PlayerAdapter.notifyDataSetChanged()
                    }else{

                        var player6=Player_easygames(6,"avatar6",0,R.drawable.avatar6,R.drawable.blackbackgraundithem,3)


                        Database_easygames.DAOplayer_easygames().insert(player6)
                        playerList_easygames.add(player6)
                        PlayerAdapter.PlayerList_easygames = playerList_easygames
                        PlayerAdapter.notifyDataSetChanged()}
                }



            var intent = Intent(this, Easygames::class.java)
            intent.putExtra("playername", profileName)
            stopSound()
            finish()
            startActivity(intent)


        }

        }


    // 1. Plays the water sound
    fun playSound() {

        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.sound_home_jumping_around)
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


    private fun saveDataNS(savetext:String,savescore:String){



        val sharedPreferences =getSharedPreferences( "sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString("STRING_KEY", savetext)
            putString("STRING_SCORE", savescore)
        }.apply()

    }










    private fun saveData(savetext:String){



        val sharedPreferences =getSharedPreferences( "sharedPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putString("STRING_KEY", savetext)

        }.apply()

    }


    private fun loadData() {

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





    }


}
