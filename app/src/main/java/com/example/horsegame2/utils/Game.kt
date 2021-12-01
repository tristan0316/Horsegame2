package com.example.horsegame.utils

import com.example.horsegame2.GameFragment
import com.example.horsegame2.GameViewModel


class Game constructor(var horse1: GameViewModel.Horse, var horse2: GameViewModel.Horse, var horse3: GameViewModel.Horse, var horse4:GameViewModel.Horse){
    var h1 :GameViewModel.Horse = horse1
    var h2 :GameViewModel.Horse = horse2
    var h3 :GameViewModel.Horse = horse3
    var h4 :GameViewModel.Horse = horse4
    var horses: List<GameViewModel.Horse> = listOf(h1,h2,h3,h4)

    init{
        this.h1=horse1
        this.h2=horse2
        this.h3=horse3
        this.h4=horse4

        println("MainActivity.Horse 1 is " + this.h1)
        println("MainActivity.Horse 2 is " + this.h2)
        println("MainActivity.Horse 3 is " + this.h3)
        println("MainActivity.Horse 4 is " + this.h4)

        this.horses = listOf(h1,h2,h3,h4)
    }

    fun race() {
        h1.start()
        h2.start()
        h3.start()
        h4.start()
        h1.join()
        h2.join()
        h3.join()
        h4.join()
        winner()
    }

    fun winner() {

        horses= horses.sortedBy { it.len }
        println(horses.get(0).horsename+" wins, taking " + horses.get(0).len+" seconds")
        horses.get(0).win()
        horses.get(1).lose()
        horses.get(2).lose()
        horses.get(3).lose()

        println(horses.get(0).horsename+" ratio : " + horses.get(0).ratio)
        println(horses.get(1).horsename+" ratio : " + horses.get(1).ratio)
        println(horses.get(2).horsename+" ratio : " + horses.get(2).ratio)
        println(horses.get(3).horsename+" ratio : " + horses.get(3).ratio)
    }




}

