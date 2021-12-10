package com.example.horsegame2

import android.app.Application
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.horsegame.database.AppDatabase
import com.example.horsegame.database.HorseBet
import com.example.horsegame.database.HorseBetDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//Data list as the argument
class HistoryRecordAdapter(private var data: MutableList<HorseBet>, private val database: HorseBetDao, private val application: Application): RecyclerView.Adapter<HistoryRecordAdapter.ViewHolder>() {


    //Get the database's Dao by this application
    val dataSource = AppDatabase.getInstance(application).horseBetDao

    //The number of the data rows
    override fun getItemCount(): Int {
        return data.size
    }

    //Inflate the each row by item_view.xml
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_view, parent, false)
        return ViewHolder (view)
    }

    //Bind the data values with views in item_view.xml
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        bind(holder, item)
    }

    //Assign value to each view's text
    private fun bind(holder: HistoryRecordAdapter.ViewHolder, item: HorseBet) {
        holder.id.text = item.id.toString()
        holder.id.setOnClickListener { view ->
            CoroutineScope(Dispatchers.Main).launch {
                val job = CoroutineScope(Dispatchers.IO).launch {
                    dataSource.deleteById(item.id)
                    data.remove(item)

                }
                job.join()
                notifyItemRemoved(item.id)
                notifyDataSetChanged()
//                view.findNavController().navigate(R.id.action_global_betHistoryFragment)
            }
        }
        holder.winhorse.text = item.winHorse.toString()
        holder.betHorse.text = item.betHorse.toString()
        holder.balance.text = item.remainingAmt.toString()
        holder.betsize.text=item.priceAmt.toString()
    }

    //A class with all the views (Six TextViews in this example) in a row
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val id: TextView = itemView.findViewById(R.id.tv_id)
        val winhorse: TextView = itemView.findViewById(R.id.tv_winhorse)
        val betHorse: TextView = itemView.findViewById(R.id.tv_betHorse)
        val balance: TextView = itemView.findViewById(R.id.tv_balance)
        val betsize: TextView = itemView.findViewById(R.id.tv_betsize)


//        private fun ViewHolder.bind(item: Record) {
//            id.text = item.id.toString()
//            bethorse.text = item.Bethorse
//            betmoney.text = item.Betmoney.toString()
//            winner.text = item.Winner
//            earn.text = item.Earn.toString()
//            capital.text = item.Captial.toString()
//        }
    }
}


