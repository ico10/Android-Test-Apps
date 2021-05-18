package com.sfa.android.concentration4.game_intro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.sfa.android.concentration4.R
import com.sfa.android.concentration4.databinding.FragmentTitleBinding

class GameIntroFragment : Fragment() {

    private lateinit var binding: FragmentTitleBinding
    private lateinit var viewModel: GameIntroViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_title, container, false)
        viewModel = ViewModelProvider(this).get(GameIntroViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinner()
        setObservers()
    }

    private fun setObservers() {
        viewModel.numberOfCards.observe(viewLifecycleOwner, {
            it?.let {
                if (it != 0) {
                    binding.root.findNavController()
                        .navigate(GameIntroFragmentDirections.actionTitleFragmentToGameFragment(it))
                }
                viewModel.resetNumberOfCards()
            }
        })
    }

    private fun setupSpinner() {
        this.context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item,
                viewModel.getArrayNumberOfCards()
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinner.adapter = adapter
            }
        }
        binding.spinner.onItemSelectedListener = getItemListener()
    }

    private fun getItemListener(): OnItemSelectedListener = object : OnItemSelectedListener {
        override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View,
                position: Int,
                id: Long
        ) {
            viewModel.setNumberOfCards(parentView?.getItemAtPosition(position) as Int)
        }

        override fun onNothingSelected(parentView: AdapterView<*>?) {
            TODO()
        }
    }

}
