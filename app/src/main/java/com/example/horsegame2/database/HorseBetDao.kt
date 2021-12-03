package com.example.horsegame.database

import androidx.room.*
import com.example.horsegame2.utils.Horse

@Dao
interface HorseBetDao {

    @Query("SELECT * FROM horsebet")
    suspend fun getAll(): List<HorseBet>

    @Query("INSERT OR REPLACE INTO horsebet(id, winHorse, betHorse, balance,betmoney) VALUES(:id, :winHorse,:betHorse, :balance, :betmoney)")
    suspend fun insertData(id:Int, winHorse:String, betHorse:String, balance:Double,betmoney:Double)

    @Update
    fun update(banking: HorseBet?): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg record: HorseBet)

    @Query("DELETE FROM horsebet WHERE id = :id")
    suspend fun deleteById(id: Int)
}