package com.example.horsegame2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.horsegame.database.AppDatabase
import com.example.horsegame.database.HorseBet
import com.example.horsegame2.databinding.FragmentBetHistoryBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BetHistoryFragment : Fragment() {

    private lateinit var binding: FragmentBetHistoryBinding

    private lateinit var datalist: List<HorseBet>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        //Get all data
        lateinit var datalist: MutableList<HorseBet>
        val application = requireNotNull(this.activity).application
        val database = AppDatabase.getInstance(application).horseBetDao

        CoroutineScope(Dispatchers.Main).launch {
            var job1 = CoroutineScope(Dispatchers.IO).launch {
                datalist = database.getAll()
            }
            job1.join()
            binding.recyclerview.adapter = HistoryRecordAdapter(datalist, database, application)
        }

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_bet_history,
            container,
            false
        )

        //New a recyclerview's layoutManager as LinearLayoutManager
        val layoutManager = LinearLayoutManager(this.activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL


        //Set recyclerview's layoutManager and adapter
        binding.recyclerview.layoutManager = layoutManager




        return binding.root
    }
}


