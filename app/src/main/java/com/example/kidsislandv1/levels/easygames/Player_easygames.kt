package com.example.kidsislandv1.levels.easygames


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.kidsislandv1.R

@Entity(tableName = "Player")

data class Player_easygames (
    @PrimaryKey(autoGenerate = false)
    val pid:Int,
    val name:String,
    val score:Int,
    val imageplayerithem: Int,
    val imagebackgraund :Int,
    val diamond :Int
)

