package com.example.horsegame.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class HorseBet {
    @PrimaryKey(autoGenerate = true)
    var id = 0

    @ColumnInfo(name = "playerName")
    var playerName = ""

    @ColumnInfo(name = "result")
    var result = ""

    @ColumnInfo(name = "winHorse")
    var winHorse = ""

    @ColumnInfo(name = "betHorse")
    var betHorse = ""

    @ColumnInfo(name = "remainingAmt")
    var remainingAmt = 0.0

    @ColumnInfo(name = "priceAmt")
    var priceAmt = 0.0
}