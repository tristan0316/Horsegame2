package com.example.horsegame.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HorseBet (
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "winHorse")
    var winHorse: String = "",

    @ColumnInfo(name = "betHorse")
    var betHorse: String = "",

    @ColumnInfo(name = "balance")
    var remainingAmt: Double = 0.0,

    @ColumnInfo(name = "betmoney")
    var priceAmt:  Double = 0.0
)