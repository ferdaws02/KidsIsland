package com.example.kidsislandv1.levels.easygames

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kidsislandv1.R


class Database_easygames : AppCompatActivity() {


    lateinit var recylcerPlayer: RecyclerView
    lateinit var recylcerPlayerAdapter: PlayerAdapter_easygames
    lateinit var Playerlist_easygames: MutableList<Player_easygames>
    lateinit var Database_easygames : PlayerDataBase_easygames
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rink_layout_dialog)



        recylcerPlayer=findViewById(R.id.recycleViewdialogbox)
        Playerlist_easygames = ArrayList()
        Database_easygames =  PlayerDataBase_easygames.getDatabase(this)

        Playerlist_easygames = Database_easygames.DAOplayer_easygames().getAllPlayer()

        recylcerPlayerAdapter = PlayerAdapter_easygames(Playerlist_easygames)

        recylcerPlayer.adapter = recylcerPlayerAdapter

        recylcerPlayer.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)




    }

}
