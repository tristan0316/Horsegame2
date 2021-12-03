package com.example.horsegame2.utils

class Horse(horsename: String = "", hRatio: Double) : Thread() {
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