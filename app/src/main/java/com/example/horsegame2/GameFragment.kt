package com.example.horsegame2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.horsegame.database.AppDatabase
import com.example.horsegame.utils.Game
import com.example.horsegame2.databinding.FragmentGameBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [GameFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GameFragment : Fragment() {

    lateinit var viewModel: GameViewModel
    lateinit var binding: FragmentGameBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        binding = DataBindingUtil.inflate<FragmentGameBinding>(
            inflater,
            R.layout.fragment_game, container, false
        )
        

        val application = requireNotNull(this.activity).application
        val dataSource = AppDatabase.getInstance(application).horseBetDao
        val viewModelFactory = GameFragmentViewModelFactory(dataSource, application)


        binding.btnStartRace.setOnClickListener {
          viewModel.startGame()
        }


        viewModel.h1Ratio.observe(viewLifecycleOwner, Observer { newScore ->
            binding.tvH1Ratio.text = newScore.toString() })
        viewModel.h2Ratio.observe(viewLifecycleOwner, Observer { newScore ->
            binding.tvH2Ratio.text = newScore.toString() })
        viewModel.h3Ratio.observe(viewLifecycleOwner, Observer { newScore ->
            binding.tvH3Ratio.text = newScore.toString() })
        viewModel.h4Ratio.observe(viewLifecycleOwner, Observer { newScore ->
            binding.tvH4Ratio.text = newScore.toString() })
        viewModel.balance.observe(viewLifecycleOwner, Observer { newScore ->
            binding.tvRemains.text = newScore.toString() })



        return binding.root
    }


}