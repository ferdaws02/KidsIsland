package com.example.kidsislandv1.levels.easygames


import androidx.room.Entity
import androidx.room.PrimaryKey

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

