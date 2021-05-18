package com.sfa.android.concentration4.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.sfa.android.concentration4.R
import com.sfa.android.concentration4.databinding.FragmentGameBinding
import com.sfa.android.concentration4.game.adapter.CardAdapter
import com.sfa.android.concentration4.game.adapter.CardListener
import com.sfa.android.concentration4.game.adapter.model.CardList

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private lateinit var viewModel: GameViewModel
    private lateinit var adapter: CardAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)

        val gameFragmentArgs by navArgs<GameFragmentArgs>()

        val viewModelFactory = GameViewModelFactory(gameFragmentArgs.numberOfCards)
        viewModel = ViewModelProvider(this, viewModelFactory).get(GameViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getListFromSharedViewModel(CardList.initCardsList(viewModel.numberOfCards))
        setupRecyclerView()
        setupAdditionalObservers()
    }

    private fun setupRecyclerView() {
        binding.cardsList.layoutManager = GridLayoutManager(activity, 3)

        adapter = CardAdapter(CardListener { cardId ->
            viewModel.onSleepNightClicked(cardId)
        })
        binding.cardsList.adapter = adapter
    }

    private fun setupAdditionalObservers() {
        viewModel.eventNavigateToGameWin.observe(viewLifecycleOwner, {
            if (it) {
                binding.root.findNavController().navigate(
                    GameFragmentDirections.actionGameFragmentToGameWinFragment(viewModel.getCurrentAttempts())
                )
                viewModel.doneNavigation()
            }
        })

        viewModel.hasListChanged.observe(viewLifecycleOwner, {
            if (it) {
                adapter.submitList(viewModel.getCurrentListOfCards())
                adapter.notifyDataSetChanged()
                viewModel.doneChangingList()
            }
        })
    }
}
