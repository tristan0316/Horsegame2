package com.example.horsegame.database

import androidx.room.*

@Dao
interface HorseBetDao {

    @Query("SELECT * FROM `horseBet`")
    fun getAll(): MutableList<HorseBet>

    @Insert
    fun insert(banking: HorseBet?)

    @Update
    fun update(banking: HorseBet?): Int
}