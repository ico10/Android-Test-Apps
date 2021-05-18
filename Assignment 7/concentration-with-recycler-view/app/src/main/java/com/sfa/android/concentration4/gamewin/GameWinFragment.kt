package com.sfa.android.concentration4.gamewin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.sfa.android.concentration4.R
import com.sfa.android.concentration4.databinding.FragmentGameWinBinding

class GameWinFragment : Fragment() {

    private lateinit var binding: FragmentGameWinBinding
    private lateinit var viewModel: GameWinViewModel
    private lateinit var viewModelFactory: GameWinViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game_win, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gameWinFragmentArgs by navArgs<GameWinFragmentArgs>()

        viewModelFactory = GameWinViewModelFactory(gameWinFragmentArgs.attempts)
        viewModel = ViewModelProvider(this, viewModelFactory).get(GameWinViewModel::class.java)

        setButtonsListeners()
        gameWinObservers()
    }

    private fun setButtonsListeners() {
        binding.restartButton.setOnClickListener {
            viewModel.onPlayAgain()
        }
        binding.quitButton.setOnClickListener {
            viewModel.onFinish()
        }
    }

    private fun gameWinObservers() {
        viewModel.attempts.observe(viewLifecycleOwner, { currentAttempts ->
            binding.numAttemptsView.text = getString(R.string.number_attempts_text, currentAttempts)
        })

        viewModel.eventPlayAgain.observe(viewLifecycleOwner, { playAgain ->
            if (playAgain) {
                binding.root.findNavController()
                    .navigate(GameWinFragmentDirections.actionGameWinFragmentToTitleFragment())
                viewModel.onPlayAgainComplete()
            }
        })

        viewModel.eventFinish.observe(viewLifecycleOwner, { finish ->
            if (finish) {
                activity?.finish()
            }
        })
    }
}
