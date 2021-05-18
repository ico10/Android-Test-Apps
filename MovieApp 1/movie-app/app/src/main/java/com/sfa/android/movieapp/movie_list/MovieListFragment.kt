package com.sfa.android.movieapp.movie_list

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.sfa.android.movieapp.MainActivity
import com.sfa.android.movieapp.R
import com.sfa.android.movieapp.databinding.FragmentMovieListBinding
import com.sfa.android.movieapp.model.MovieList

class MovieListFragment : Fragment() {

    private lateinit var binding: FragmentMovieListBinding
    private lateinit var viewModel: MovieListViewModel
    private lateinit var movieBoxViewList: List<ImageView>

    private lateinit var currentlyClickedView: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as MainActivity).title = getString(R.string.movies_label)
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movie_list, container, false)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(MovieListViewModel::class.java)

        addViewsToList()
        setAddButtonListeners()
        buttonObservers()
        menuObservers()
        populateMovieBox()
    }

    private fun populateMovieBox() {
        var emptySlots = 0
        MovieList.getList().forEachIndexed { index, movie ->
            emptySlots++
            movieBoxViewList[index].setImageResource(movie.imageDrawable)
            movieBoxViewList[index].setBackgroundColor(resources.getColor(R.color.white))
            movieBoxViewList[index].setPadding(0, 0, 0, 0)
            movieBoxViewList[index].tag = movie.imageDrawable
        }
        while (emptySlots < MAX_NUMBER_OF_MOVIES) {
            movieBoxViewList[emptySlots].tag = R.drawable.add_icon
            emptySlots++
        }
    }

    private fun addViewsToList() {
        binding.run {
            movieBoxViewList = listOf(
                movieBoxOne,
                movieBoxTwo,
                movieBoxThree,
                movieBoxFour,
                movieBoxFive,
                movieBoxSix
            )
        }
    }

    private fun setAddButtonListeners() {
        movieBoxViewList.forEach { movie ->
            movie.setOnClickListener {
                currentlyClickedView = movie
                if (movie.tag == R.drawable.add_icon) {
                    viewModel.addMovieClick()
                } else {
                    viewModel.clickOnMovie()
                }
            }
        }
    }

    private fun buttonObservers() {
        viewModel.eventAddMoviePressed.observe(viewLifecycleOwner, {
            if (it) {
                binding.root.findNavController()
                    .navigate(R.id.action_movieListFragment_to_createMovieFragment)
                viewModel.addMovieClickEnd()
            }
        })
        viewModel.isMoviePopulated.observe(viewLifecycleOwner, {
            if (it) {
                navigateWithArguments()
                viewModel.clickOnMovieEnd()
            }
        })
    }

    private fun navigateWithArguments() {
        MovieList.getList().forEach {
            if (it.imageDrawable == currentlyClickedView.tag) {
                binding.root.findNavController()
                    .navigate(
                        MovieListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(
                            it.imageDrawable,
                            it.title,
                            it.year,
                            it.shortDescription,
                            it.genre,
                            it.longDescription
                        )
                    )
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.movie_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_create_room) {
            binding.root.findNavController()
                .navigate(R.id.action_movieListFragment_to_createMovieFragment)
        } else {
            if (MovieList.getList().isEmpty().not()) {
                when (item.itemId) {
                    R.id.action_sort_alphabetically -> {
                        MovieList.sortMovieListByTitle()
                        viewModel.actionSort()
                    }
                    R.id.action_sort_by_release_date -> {
                        MovieList.sortMovieListByReleaseDate()
                        viewModel.actionSort()
                    }
                    R.id.action_filter_action,
                    R.id.action_filter_horror,
                    R.id.action_filter_comedy,
                    R.id.action_filter_fantasy,
                    R.id.action_filter_western,
                    R.id.action_reset_filter -> {
                        viewModel.addFilter(item.title.toString())
                    }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun menuObservers() {
        viewModel.isSorted.observe(viewLifecycleOwner, {
            if (it) {
                populateMovieBox()
                viewModel.actionSortEnd()
            }
        })

        viewModel.isFilteredBy.observe(viewLifecycleOwner, {
            when (it) {
                getString(R.string.filter_action) -> {
                    MovieList.getList().forEachIndexed { index, imageView ->
                        if (imageView.genre != getString(R.string.filter_action)) {
                            movieBoxViewList[index].visibility = View.INVISIBLE
                        }
                    }
                }
                getString(R.string.filter_horror) -> {
                    MovieList.getList().forEachIndexed { index, imageView ->
                        if (imageView.genre != getString(R.string.filter_horror)) {
                            movieBoxViewList[index].visibility = View.INVISIBLE
                        }
                    }
                }
                getString(R.string.filter_comedy) -> {
                    MovieList.getList().forEachIndexed { index, imageView ->
                        if (imageView.genre != getString(R.string.filter_comedy)) {
                            movieBoxViewList[index].visibility = View.INVISIBLE
                        }
                    }
                }
                getString(R.string.filter_fantasy) -> {
                    MovieList.getList().forEachIndexed { index, imageView ->
                        if (imageView.genre != getString(R.string.filter_fantasy)) {
                            movieBoxViewList[index].visibility = View.INVISIBLE
                        }
                    }
                }
                getString(R.string.filter_western) -> {
                    MovieList.getList().forEachIndexed { index, imageView ->
                        if (imageView.genre != getString(R.string.filter_western)) {
                            movieBoxViewList[index].visibility = View.INVISIBLE
                        }
                    }
                }
                else -> {
                    setEveryMovieBoxVisible()
                }
            }
        })
    }

    private fun setEveryMovieBoxVisible() {
        movieBoxViewList.forEach { imageView ->
            imageView.visibility = View.VISIBLE
        }
    }

    companion object {
        const val MAX_NUMBER_OF_MOVIES = 6
    }
}
