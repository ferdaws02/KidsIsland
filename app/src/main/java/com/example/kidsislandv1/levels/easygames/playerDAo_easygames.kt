package com.example.kidsislandv1.levels.easygames


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface playerDAo_easygames {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(Player_easygames : Player_easygames)
    @Query("UPDATE Player Set score=:nscore where pid=:npid")
    fun update(nscore:String,npid:Int)

    @Query("UPDATE Player Set diamond=:ndiamond where name=:name")
    fun updatediamond(ndiamond :String,name:String)


    @Query("SELECT * FROM Player order by score desc ")
    fun getAllPlayer(): MutableList<Player_easygames>
    @Query("SELECT * FROM player WHERE  name LIKE '%' || :name || '%'")
    fun searchByName (name:String): Boolean


    @Query("SELECT score FROM Player where name LIKE '%' || :name || '%'")
    fun getPlayerbyName(name: String): Int

    @Query("SELECT diamond FROM Player where name LIKE '%' || :name || '%'")
    fun getPlayerdiamondbyName(name: String): Int




}