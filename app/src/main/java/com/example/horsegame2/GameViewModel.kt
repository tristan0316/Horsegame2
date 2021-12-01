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
import java.text.DecimalFormat


class GameViewModel(val database:HorseBetDao, application: Application) : AndroidViewModel(application){

    var h1Ratio = MutableLiveData<Double>()
    var h2Ratio = MutableLiveData<Double>()
    var h3Ratio = MutableLiveData<Double>()
    var h4Ratio = MutableLiveData<Double>()
    var balance = MutableLiveData<Double>()

    lateinit var game1: Game


    inner class Horse(horsename: String = "", hRatio: Double) : Thread() {
        var ratio: Double = hRatio
        var mile: Int = 0
        var len: Long = 0
        var horsename: String = ""

        init {
            this.ratio = 2.0
            this.horsename = horsename
            println("Introducing:" + this.horsename)
        }


        fun win() = if (this.ratio - 0.1 >= 2) {
            this.ratio = this.ratio - 0.1
        } else {
        }

        fun lose() = if (this.ratio + 0.1 <= 5) {
            this.ratio = this.ratio + 0.1
        } else {
        }

        override fun run() {
            this.mile = 0
            this.len = 0

            var start: Long = System.currentTimeMillis()
            while (mile < 20) {
                sleep((0..1000).random().toLong())
                mile++
                println("${horsename} ran ${mile} kms")

            }
            len = System.currentTimeMillis()
            len = len - start
            println("${horsename} finished in ${len} seconds")
        }
    }


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