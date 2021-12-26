package com.example.kidsislandv1.levels.easygames


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kidsislandv1.levels.easygames.memorygame.MemoryMainActivity

@Database(entities = [Player_easygames::class], version = 1)
abstract class PlayerDataBase_easygames : RoomDatabase(){
    abstract fun DAOplayer_easygames():playerDAo_easygames


    companion object {
        @Volatile
        private var instance: PlayerDataBase_easygames ? = null

        fun getDatabase(context: Context): PlayerDataBase_easygames {
            if (instance == null) {
                synchronized(this) {
                    instance =
                        Room.databaseBuilder(context, PlayerDataBase_easygames ::class.java, "database")
                            .allowMainThreadQueries()
                            .build()


                }
            }
            return instance!!
        }


    }
}