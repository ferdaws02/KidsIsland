package com.example.kidsislandv1.levels.easygames


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kidsislandv1.R
import kotlinx.android.synthetic.main.activity_puzzel.view.*


class PlayerAdapter_easygames (var PlayerList_easygames:MutableList<Player_easygames>): RecyclerView.Adapter<PlayerViewHolder_easygame>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder_easygame {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.player_item_easygames ,parent,false)
        return PlayerViewHolder_easygame(view)
    }


    override fun onBindViewHolder(holder: PlayerViewHolder_easygame, position: Int) {
        val pid = PlayerList_easygames[position].pid
        val playername = PlayerList_easygames[position].name
        val score = PlayerList_easygames[position].score
        val imageplayerithem = PlayerList_easygames[position].imageplayerithem
        val imagebackgraund = PlayerList_easygames[position].imagebackgraund


        holder.pid.text = pid.toString()
        holder.player.text = playername
        holder.score.text = score.toString()
        holder.imagebackgraund.setImageResource(imagebackgraund)
        holder.imageplayerithem.setImageResource(imageplayerithem)



    }
    override fun getItemCount()=PlayerList_easygames.size

}