package com.example.horsegame2

import android.app.Application
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.horsegame.database.AppDatabase
import com.example.horsegame.database.HorseBetDao
import com.example.horsegame.utils.Game
import com.example.horsegame2.utils.Horse
import java.text.DecimalFormat


class GameViewModel(val database:HorseBetDao, application: Application) : AndroidViewModel(application){

    //horse ratio
    var h1Ratio = MutableLiveData<Double>()
    var h2Ratio = MutableLiveData<Double>()
    var h3Ratio = MutableLiveData<Double>()
    var h4Ratio = MutableLiveData<Double>()

    //current balance
    var balance = MutableLiveData<Double>()

    //exchange rate
    var exrate = MutableLiveData<Double>()

    //bet amount
    var betmoney: Int? = null

    //bet horse
    var bethorsename: String? = null

    //balance change
    var earn: Int? = 0



    lateinit var game1: Game




    init {
        h1Ratio.value = 2.0
        h2Ratio.value = 2.0
        h3Ratio.value = 2.0
        h4Ratio.value = 2.0
        balance.value=10000.0

        var h1 = h1Ratio.value?.let { Horse("H1", it) }
        var h2 = h2Ratio.value?.let { Horse("H2", it) }
        var h3 = h3Ratio.value?.let { Horse("H3", it) }
        var h4 = h4Ratio.value?.let { Horse("H4", it) }

        game1 = Game(h1!!, h2!!, h3!!, h4!!)

    }

    fun startGame() {
        game1.race()
        h1Ratio.value = game1.h1.ratio
        h2Ratio.value = game1.h2.ratio
        h3Ratio.value = game1.h3.ratio
        h4Ratio.value = game1.h4.ratio
    }
}