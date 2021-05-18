package com.sfa.android.movieapp.movie_descriptions

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.sfa.android.movieapp.MainActivity
import com.sfa.android.movieapp.R
import com.sfa.android.movieapp.databinding.FragmentMovieDetailsBinding

class MovieDetailsFragment : Fragment() {

    private lateinit var binding: FragmentMovieDetailsBinding
    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var viewModelFactory: MovieDetailsViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as MainActivity).title = getString(R.string.movie_details_label)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_movie_details, container, false)

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val movieDetailsFragmentArgs by navArgs<MovieDetailsFragmentArgs>()

        viewModelFactory = MovieDetailsViewModelFactory(
            movieDetailsFragmentArgs.imageDrawable,
            movieDetailsFragmentArgs.title,
            movieDetailsFragmentArgs.releaseYear,
            movieDetailsFragmentArgs.shortDescription,
            movieDetailsFragmentArgs.genre,
            movieDetailsFragmentArgs.longDescription
        )


        viewModel = ViewModelProvider(this, viewModelFactory).get(MovieDetailsViewModel::class.java)

        setTextViews()
    }

    private fun setTextViews() {
        binding.apply {
            movieDetailsImage.setImageResource(viewModel.modelImageDrawable)
            movieDetailsTitle.text = getString(R.string.title_format, viewModel.modelTitle)
            movieDetailsYear.text = getString(R.string.year_format, viewModel.modelReleaseYear)
            movieDetailsShortDescription.text =
                getString(R.string.short_format, viewModel.modelShortDescription)
            movieDetailsGenre.text = getString(R.string.genre_format, viewModel.modelGenre)
            movieDetailsLongDescription.text = getString(R.string.long_format)
            movieDetailsLongDescriptionText.text = viewModel.modelLongDescription
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.edit_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_edit_movie) {
            val movieDetailsFragmentArgs by navArgs<MovieDetailsFragmentArgs>()
            binding.root.findNavController()
                .navigate(
                    MovieDetailsFragmentDirections.actionMovieDetailsFragmentToCreateMovieFragment(
                        true,
                        movieDetailsFragmentArgs.imageDrawable,
                        movieDetailsFragmentArgs.title,
                        movieDetailsFragmentArgs.releaseYear
                    )
                )
        } else {
            binding.root.findNavController().navigateUp()
        }
        super.onOptionsItemSelected(item)
        return true
    }
}
