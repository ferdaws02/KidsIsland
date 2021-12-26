package com.example.kidsislandv1.levels.easygames


import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kidsislandv1.R

class PlayerViewHolder_easygame(itemView: View): RecyclerView.ViewHolder(itemView) {
    val player: TextView
    val score: TextView
    var pid: TextView
    var imageplayerithem : ImageView
    var imagebackgraund :ImageView

    init {
        player = itemView.findViewById(R.id.nameplayer)
        score = itemView.findViewById(R.id.scoreplayer)
        pid = itemView.findViewById(R.id.idplayer)
        imageplayerithem = itemView.findViewById(R.id.imageplayerithem)
        imagebackgraund = itemView.findViewById(R.id.imagebackgraundplayerithem)
    }
}