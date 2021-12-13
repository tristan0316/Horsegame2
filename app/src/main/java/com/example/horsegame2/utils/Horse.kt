package com.example.horsegame2.utils

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Horse(horsename: String = "", hRatio: Double)  {
    var ratio: Double = hRatio
    var mile: Int = 0
    var len: Long = 0
    var horsename: String = ""
    var milesLivedata = MutableLiveData<Int>()

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

     fun run() {
        this.mile = 0
        this.len = 0

        var start: Long = System.currentTimeMillis()
        while (mile < 20) {
            Thread.sleep((0..2000).random().toLong())
            CoroutineScope(Dispatchers.Main).launch {
                mile = (mile)?.plus(1)
                println("${horsename} ran ${mile} miles")
                milesLivedata.value = mile
            }
        }
        len = System.currentTimeMillis()
        len = len - start
        println("${horsename} finished in ${len} seconds")
    }
}