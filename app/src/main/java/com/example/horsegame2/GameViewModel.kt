package com.example.horsegame2

import MyAPIService
import android.app.Application
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.horsegame.database.AppDatabase
import com.example.horsegame.database.HorseBet
import com.example.horsegame.database.HorseBetDao
import com.example.horsegame.utils.Game
import com.example.horsegame2.jaon.Currency
import com.example.horsegame2.utils.GameListener
import com.example.horsegame2.utils.Horse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.DecimalFormat


class GameViewModel(val database:HorseBetDao, application: Application) : AndroidViewModel(application), GameListener {



    var progress1 = MutableLiveData<Int>()
    var progress2 = MutableLiveData<Int>()
    var progress3 = MutableLiveData<Int>()
    var progress4 = MutableLiveData<Int>()


    //horse ratio
    var h1Ratio = MutableLiveData<Double>()
    var h2Ratio = MutableLiveData<Double>()
    var h3Ratio = MutableLiveData<Double>()
    var h4Ratio = MutableLiveData<Double>()

    //current balance
    var balance = MutableLiveData<Double>()

    //bet amount
    var betmoney: Double = 10.0

    //bet horse
    var bethorsename: String? = null

    //balance change
    var earn: Int? = 0

    // dataID
    var dataid =0

    //exchange rate
    var exrate = MutableLiveData<Double>()



    lateinit var game1: Game



    fun fetch_exchangeRate(){
        CoroutineScope(Dispatchers.IO).launch {

            //PS: Import right packages in this class (EX: Many packages have Callback class)
            val apiService = RetrofitManager.client.create(MyAPIService::class.java)
            apiService.getExchangeRate().enqueue(object: Callback<Currency>{
                override fun onResponse(call: Call<Currency>, response: Response<Currency>) {

                    //PS: Update LiveData in the Main thread
                    CoroutineScope(Dispatchers.Main).launch {
                       exrate.value = response.body()!!.USDTWD!!.Exrate
                        Log.v("Game", "Network access successfully")
                    }
                }
                override fun onFailure(call: Call<Currency>, t: Throwable) {
                    Log.v("Game", "Network access failed")
                }
            })
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

        game1 = Game(this, h1!!, h2!!, h3!!, h4!!)

        fetch_exchangeRate()

    }


    fun startGame() {
        game1.race()
    }

    override fun GameEnd() {
        h1Ratio.value = game1.h1.ratio
        h2Ratio.value = game1.h2.ratio
        h3Ratio.value = game1.h3.ratio
        h4Ratio.value = game1.h4.ratio
        calculate_result()

        balance.value?.let {
            insert_bet(dataid,game1.win_horse().horsename,bethorsename.toString(),
                it,betmoney)
        }
        Log.e("Kenny", "finish")
        Log.e("Kenny",bethorsename.toString() )
        Log.e("Kenny",game1.win_horse().horsename.toString())

    }

   fun calculate_result(){
       if( bethorsename.equals(game1.win_horse().horsename)){


           Log.e("Kenny","insert succeed"+ exrate.value.toString())

           balance.value= balance.value?.plus(betmoney*game1.win_horse().ratio* exrate.value!!)


          //balance.value= betmoney?.times(game1.win_horse().ratio)?.let { balance.value?.plus(it) }
       }
       else{

           Log.e("Kenny","insert succeed"+ exrate.value.toString())

           balance.value= balance.value?.minus(betmoney* exrate.value!!)
         // balance.value= betmoney?.let { balance.value?.minus(it) }
       }
   }

    fun reset (){
        h1Ratio.value = 2.0
        h2Ratio.value = 2.0
        h3Ratio.value = 2.0
        h4Ratio.value = 2.0
        balance.value=10000.0

        var h1 = h1Ratio.value?.let { Horse("H1", it) }
        var h2 = h2Ratio.value?.let { Horse("H2", it) }
        var h3 = h3Ratio.value?.let { Horse("H3", it) }
        var h4 = h4Ratio.value?.let { Horse("H4", it) }

        game1 = Game(this, h1!!, h2!!, h3!!, h4!!)
    }

   fun insert_bet(dataid:Int,winhorse:String,bethorse:String,balancee:Double,betmoneyy:Double){
        CoroutineScope(Dispatchers.IO).launch {
            val newData = HorseBet(dataid,game1.win_horse().horsename!!, bethorsename!!, balance.value!!, betmoney)
            database.insertAll(newData)
            Log.e("Kenny","insert succeed"+ newData.toString())
        }
        this.dataid++
    }
}