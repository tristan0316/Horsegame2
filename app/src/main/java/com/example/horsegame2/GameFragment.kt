package com.example.horsegame2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.horsegame.database.AppDatabase
import com.example.horsegame.utils.Game
import com.example.horsegame2.databinding.FragmentGameBinding
import java.lang.Exception

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

    private lateinit var binding: FragmentGameBinding
    private lateinit var viewModel: GameViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentGameBinding>(
            inflater,
            R.layout.fragment_game,
            container,
            false
            )

        //Get the activity's application
        val application = requireNotNull(this.activity).application


        //Get the database's Dao by this application
        val dataSource = AppDatabase.getInstance(application).horseBetDao

        // Create an instance of the ViewModel Factory
        val viewModelFactory = GameFragmentViewModelFactory(dataSource, application)

        // Get a reference to the ViewModel associated with this fragment.
        viewModel = ViewModelProvider(this, viewModelFactory).get(GameViewModel::class.java)


        binding.btnStartRace.setOnClickListener {

            try {
                viewModel.betmoney = binding.etBet.text.toString().trim().toInt().toDouble()
                val RadioB1 = binding.radioGroup
                val RadioBtm: RadioButton = binding.root.findViewById(RadioB1.getCheckedRadioButtonId())
                viewModel.bethorsename = RadioBtm.text.toString()

            }catch (e: Exception){
                Log.i("Game", "Please insert the money and choose one horse")
            }
          viewModel.startGame()

        }

        //reset button listener
        binding.resetbtn.setOnClickListener {
            viewModel.reset()
        }

        binding.btnHistory.setOnClickListener {
            view?.findNavController()?.navigate(R.id.action_gameFragment_to_betHistoryFragment)
        }


        viewModel.h1Ratio.observe(viewLifecycleOwner, Observer { newScore ->
            binding.tvH1Ratio.text = String.format("%.1f", newScore) })
        viewModel.h2Ratio.observe(viewLifecycleOwner, Observer { newScore ->
            binding.tvH2Ratio.text =  String.format("%.1f", newScore) })
        viewModel.h3Ratio.observe(viewLifecycleOwner, Observer { newScore ->
            binding.tvH3Ratio.text =  String.format("%.1f", newScore) })
        viewModel.h4Ratio.observe(viewLifecycleOwner, Observer { newScore ->
            binding.tvH4Ratio.text =  String.format("%.1f", newScore) })
        viewModel.balance.observe(viewLifecycleOwner, Observer { newScore ->
            binding.tvRemains.text = newScore.toString() })



        return binding.root
    }


}