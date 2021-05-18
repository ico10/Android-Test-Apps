package com.sfa.android.movieapp.intro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.sfa.android.movieapp.MainActivity
import com.sfa.android.movieapp.R
import com.sfa.android.movieapp.databinding.FragmentIntroBinding

class IntroFragment : Fragment() {

    private lateinit var binding: FragmentIntroBinding
    private lateinit var viewModel: IntroViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_intro, container, false)
        viewModel = ViewModelProvider(this).get(IntroViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).title = getString(R.string.intro_label_text)
        setIntroButtonListeners()
        buttonObservers()
    }

    private fun setIntroButtonListeners() {
        binding.showMoviesButton.setOnClickListener {
            viewModel.pressShowButton()
        }

        binding.addMovieButton.setOnClickListener {
            viewModel.pressAddMovieButton()
        }
    }

    private fun buttonObservers() {
        viewModel.showButtonPressed.observe(viewLifecycleOwner, {
            if (it) {
                binding.root.findNavController()
                    .navigate(R.id.action_introFragment_to_movieListFragment)
                viewModel.pressShowButtonEnd()
            }
        })

        viewModel.addMovieButtonPressed.observe(viewLifecycleOwner, {
            if (it) {
                binding.root.findNavController()
                    .navigate(R.id.action_introFragment_to_createMovieFragment)
                viewModel.pressAddMovieButtonEnd()
            }
        })
    }
}
