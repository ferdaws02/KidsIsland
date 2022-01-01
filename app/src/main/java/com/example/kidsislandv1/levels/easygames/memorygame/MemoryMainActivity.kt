package com.example.kidsislandv1.levels.easygames.memorygame


import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.kidsislandv1.R
import com.example.kidsislandv1.R.drawable.*
import com.example.kidsislandv1.levels.easygames.*
import kotlinx.android.synthetic.main.activity_memory_level.*
import kotlinx.android.synthetic.main.win_diamond_layout_dialog.view.*

import kotlinx.android.synthetic.main.win_layout_dialog.view.*

class MemoryMainActivity : AppCompatActivity() {


    lateinit var PlayerAdapter_easygames : PlayerAdapter_easygames
    lateinit var dataBase_easygames : PlayerDataBase_easygames
    lateinit var PlayerList_easygames: MutableList<Player_easygames>
    var mMediaPlayer: MediaPlayer? = null

    lateinit var Database_easygames : PlayerDataBase_easygames



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_memory_main)


        var soudClick = R.raw.sound_button
        dataBase_easygames = PlayerDataBase_easygames.getDatabase(this)





        val sharedPreferences = getSharedPreferences( "sharedPrefs", Context.MODE_PRIVATE)
        val savedString = sharedPreferences.getString( "STRING_KEY",  null)
        val playernamememorygameActivity= savedString.toString()



        playSound()


        val images: MutableList<Int> =
            mutableListOf(lion, giraffe,
                horse,
                monkey,
                pig,
                frog,
                lion,
                giraffe,
                horse,
                monkey,
                pig,
                frog)


        val button1: Button = findViewById(R.id.button1)
        val button2: Button = findViewById(R.id.button2)
        val button3: Button = findViewById(R.id.button3)
        val button4: Button = findViewById(R.id.button4)
        val button5: Button = findViewById(R.id.button5)
        val button6: Button = findViewById(R.id.button6)
        val button7: Button = findViewById(R.id.button7)
        val button8: Button = findViewById(R.id.button8)
        val button9: Button = findViewById(R.id.button9)
        val button10: Button = findViewById(R.id.button10)
        val button11: Button = findViewById(R.id.button11)
        val button12: Button = findViewById(R.id.button12)


        val buttons =
            arrayOf(button1, button2, button3, button4, button5, button6, button7, button8,
                button9, button10, button11, button12)


        val cardBack = code
        var clicked = 0
        var turnOver = false
        var lastClicked = -1


        images.shuffle()
        for (i in 0..11) {

            buttons[i].setBackgroundResource(cardBack)
            buttons[i].text = "cardBack"
            buttons[i].textSize = 0.0F


            buttons[i].setOnClickListener {

                AudioPlay.playAudioButton(this, soudClick)


                if (buttons[i].text == "cardBack" && !turnOver) {


                    buttons[i].setBackgroundResource(images[i])
                    buttons[i].setText(images[i])
                    if (clicked == 0) {
                        lastClicked = i
                    }
                    clicked++
                } else if (buttons[i].text !in "cardBack") {

                    buttons[i].setBackgroundResource(cardBack)
                    buttons[i].text = "cardBack"
                    clicked--
                }

                if (clicked == 2) {
                    turnOver = true
                    if (buttons[i].text == buttons[lastClicked].text) {




                        buttons[i].isClickable = false
                        buttons[lastClicked].isClickable = false
                        turnOver = false
                        clicked = 0
                    }
                } else if (clicked == 0) {



                    turnOver = false
                }

                if (buttons[0].text != "cardBack"
                    && buttons[1].text != "cardBack"
                    && buttons[2].text != "cardBack"
                    && buttons[3].text != "cardBack"
                    && buttons[4].text != "cardBack"
                    && buttons[5].text != "cardBack"
                    && buttons[6].text != "cardBack"
                    && buttons[7].text != "cardBack"
                    && buttons[8].text != "cardBack"
                    && buttons[9].text != "cardBack"
                    && buttons[10].text != "cardBack"
                    && buttons[11].text != "cardBack"
                ) {

                   checkGameOver()
                }


            }


        }


    }

    fun checkGameOver() {

             win ()

            val view = View.inflate(this@MemoryMainActivity, R.layout.win_layout_dialog, null)
            val builder = AlertDialog.Builder(this@MemoryMainActivity)
            builder.setView(view)

            val dialog = builder.create()
            dialog.show()
            dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.setCancelable(false)



        view.buttonidcancelwin.setOnClickListener {
            var soudClick = R.raw.sound_button
            AudioPlay.playAudioButton(this, soudClick)

            dialog.cancel()



            val sharedPreferences = getSharedPreferences( "sharedPrefs", Context.MODE_PRIVATE)
            val savedString = sharedPreferences.getString( "STRING_KEY",  null)
            var textidfromintent = savedString





            if ( dataBase_easygames.DAOplayer_easygames().getPlayerbyName(textidfromintent.toString()).mod(100)==0){


                var diamondactueldeplayer1 = dataBase_easygames.DAOplayer_easygames().getPlayerdiamondbyName(textidfromintent.toString())
                    var newdiamondPlayer1 =  diamondactueldeplayer1 + 1
                    dataBase_easygames.DAOplayer_easygames().updatediamond(newdiamondPlayer1.toString(),1)

                val view = View.inflate(this@MemoryMainActivity, R.layout.win_diamond_layout_dialog, null)

                val builder = AlertDialog.Builder(this@MemoryMainActivity)
                builder.setView(view)

                val dialog = builder.create()
                dialog.show()
                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
                dialog.setCancelable(false)

                view.buttonidcancelwindiamond.setOnClickListener{
                    var soudClick = R.raw.sound_button
                    AudioPlay.playAudioButton(this, soudClick)

                    dialog.cancel()}

            }


        }




        view.Restartbtndia.setOnClickListener {
                var soudClick = R.raw.sound_button
                AudioPlay.playAudioButton(this, soudClick)

                startActivity(intent)
                dialog.cancel()
            }


                var soundWin = R.raw.sound_win
                AudioPlay.playAudio(this, soundWin)

        }



    fun win () {


        var player1= Player_easygames(1,"avatar1",0,0,0,0)
        var player2=Player_easygames(2,"avatar2",0,0,0,0)
        var player3=Player_easygames(3,"avatar3",0,0,0,0)
        var player4=Player_easygames(4,"avatar4",0,0,0,0)
        var player5=Player_easygames(5,"avatar5",0,0,0,0)
        var player6=Player_easygames(6,"avatar6",0,0,0,0)

        dataBase_easygames = PlayerDataBase_easygames.getDatabase(this)
        PlayerList_easygames=ArrayList()
        PlayerAdapter_easygames = PlayerAdapter_easygames(PlayerList_easygames)




        val sharedPreferences = getSharedPreferences( "sharedPrefs", Context.MODE_PRIVATE)
        val textidfromintent  = sharedPreferences.getString( "STRING_KEY",  null)




        val playernamememorygameActivity=textidfromintent.toString()


        if ( textidfromintent == "avatar1") {


            var scoreactueldeplayer1 = dataBase_easygames.DAOplayer_easygames().getPlayerbyName("avatar1")
          var newScorePlayer1 =  scoreactueldeplayer1 + 10



            dataBase_easygames.DAOplayer_easygames().update(newScorePlayer1.toString(),1)


            PlayerList_easygames.add(player1)
            PlayerAdapter_easygames.PlayerList_easygames = PlayerList_easygames
            PlayerAdapter_easygames.notifyDataSetChanged()

        }

        else if ( textidfromintent == "avatar2") {


            var scoreactueldeplayer2 = dataBase_easygames.DAOplayer_easygames().getPlayerbyName("avatar2")
            var newScorePlayer2 =  scoreactueldeplayer2 + 10



            dataBase_easygames.DAOplayer_easygames().update(newScorePlayer2.toString(),2)
            PlayerList_easygames.add(player2)
            PlayerAdapter_easygames.PlayerList_easygames = PlayerList_easygames
            PlayerAdapter_easygames.notifyDataSetChanged()

        } else if ( textidfromintent == "avatar3") {

            var scoreactueldeplayer3 = dataBase_easygames.DAOplayer_easygames().getPlayerbyName("avatar3")
            var newScorePlayer3 =  scoreactueldeplayer3 + 10




            dataBase_easygames.DAOplayer_easygames().update(newScorePlayer3.toString(),3)
            PlayerList_easygames.add(player3)
            PlayerAdapter_easygames.PlayerList_easygames = PlayerList_easygames
            PlayerAdapter_easygames.notifyDataSetChanged()

        } else if ( textidfromintent == "avatar4") {


            var  scoreactueldeplayer4 = dataBase_easygames.DAOplayer_easygames().getPlayerbyName("avatar4")
            var newScorePlayer4 =  scoreactueldeplayer4 + 10




            dataBase_easygames.DAOplayer_easygames().update(newScorePlayer4.toString(),4)
            PlayerList_easygames.add(player4)
            PlayerAdapter_easygames.PlayerList_easygames = PlayerList_easygames
            PlayerAdapter_easygames.notifyDataSetChanged()

        } else if ( textidfromintent == "avatar5") {

            var scoreactueldeplayer5 = dataBase_easygames.DAOplayer_easygames().getPlayerbyName("avatar5")
            var newScorePlayer5 =  scoreactueldeplayer5 + 10



            dataBase_easygames.DAOplayer_easygames().update(newScorePlayer5.toString(),5)
            PlayerList_easygames.add(player5)
            PlayerAdapter_easygames.PlayerList_easygames = PlayerList_easygames
            PlayerAdapter_easygames.notifyDataSetChanged()

        } else {

            var  scoreactueldeplayer6 = dataBase_easygames.DAOplayer_easygames().getPlayerbyName("avatar6")
            var newScorePlayer6 =  scoreactueldeplayer6 + 10



            dataBase_easygames.DAOplayer_easygames().update(newScorePlayer6.toString(),6)
            PlayerList_easygames.add(player6)
            PlayerAdapter_easygames.PlayerList_easygames = PlayerList_easygames
            PlayerAdapter_easygames.notifyDataSetChanged()

        }
    }



    override fun onBackPressed (){
        super.onBackPressed()
        stopSound()
        var intent = Intent(this, MemoryLevel::class.java)
        startActivity(intent)

    }


    // 1. Plays the water sound
    fun playSound() {

        if (mMediaPlayer == null) {
            mMediaPlayer = MediaPlayer.create(this, R.raw.sound_memory_just_kidding)
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


    fun scorebyname (name:String) : String {
        dataBase_easygames = PlayerDataBase_easygames.getDatabase(this)

        var score = Database_easygames.DAOplayer_easygames().getPlayerbyName(name).toString()
        return score
    }


}



