package com.example.horsegame.utils

import androidx.lifecycle.MutableLiveData
import com.example.horsegame2.GameFragment
import com.example.horsegame2.GameViewModel
import com.example.horsegame2.utils.GameListener
import com.example.horsegame2.utils.Horse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class Game constructor(gameListener: GameListener, var horse1: Horse, var horse2: Horse, var horse3: Horse, var horse4:Horse){
    var h1 :Horse = horse1
    var h2 :Horse = horse2
    var h3 :Horse = horse3
    var h4 :Horse = horse4
    var horses: List<Horse> = listOf(h1,h2,h3,h4)
    val gameListener = gameListener

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

        CoroutineScope(Dispatchers.Main).launch {
            //Four horses run concurrently
            val job1 = CoroutineScope(Dispatchers.IO).launch {
                h1.run()
            }
            val job2 = CoroutineScope(Dispatchers.IO).launch {
                h2.run()
            }
            val job3 = CoroutineScope(Dispatchers.IO).launch {
                h3.run()
            }
            val job4 = CoroutineScope(Dispatchers.IO).launch {
                h4.run()
            }
            job1.join()
            job2.join()
            job3.join()
            job4.join()
            adjustrate()
        }

    }

    fun adjustrate() {
        horses= horses.sortedBy { it.len }
        println(horses.get(0).horsename+" wins, taking " + horses.get(0).len+" seconds")
        horses.get(0).win()
        horses.get(1).lose()
        horses.get(2).lose()
        horses.get(3).lose()
        gameListener.GameEnd()
    }

    fun win_horse():Horse{
        return horses.get(0)
    }




}

