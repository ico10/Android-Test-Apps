package com.sfa.android.movieapp.create_movie

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.sfa.android.movieapp.MainActivity
import com.sfa.android.movieapp.R
import com.sfa.android.movieapp.databinding.FragmentCreateMovieBinding
import com.sfa.android.movieapp.model.Movie
import com.sfa.android.movieapp.model.MovieList
import java.util.*

class CreateMovieFragment : Fragment(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: FragmentCreateMovieBinding
    private lateinit var viewModel: CreateMovieViewModel
    private lateinit var viewModelFactory: CreateMovieViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_create_movie, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).title = getString(R.string.create_movies_label)

        val createMovieFragmentArgs by navArgs<CreateMovieFragmentArgs>()
        viewModelFactory = CreateMovieViewModelFactory(
            createMovieFragmentArgs.fromMovie,
            createMovieFragmentArgs.movieTag,
            createMovieFragmentArgs.movieTitle,
            createMovieFragmentArgs.movieYear
        )
        viewModel = ViewModelProvider(this, viewModelFactory).get(CreateMovieViewModel::class.java)


        setupSpinner()
        setupDatePicker()

        setViewListeners()
        setObservers()

        checkIfFromEditView()
    }

    private fun checkIfFromEditView() {
        if (viewModel.isFromEdit) {
            (requireActivity() as MainActivity).title = getString(R.string.edit_movie_text)
            binding.createMovieButton.text = getString(R.string.edit_movie_text)
            binding.createMovieImage.setImageResource(viewModel.editMovieTag)
            binding.movieTitle.setText(viewModel.editMovieTitle)
            binding.movieReleaseDate.text = viewModel.editMovieYear
            binding.createMovieImage.isClickable = false
            binding.movieTitle.isEnabled = false
            binding.movieReleaseDate.isClickable = false
        }
    }

    private fun validateFields() {
        if (binding.createMovieImage.drawable != null) {
            viewModel.imageValid = true
        }
        if (binding.movieTitle.text.length >= TITLE_VAL) {
            viewModel.titleValid = true
        }
        if (binding.movieReleaseDate.text != YEAR_VAL) {
            viewModel.yearValid = true
        }
        if (binding.shortDescription.text.length >= SHORT_VAL) {
            viewModel.shortDescriptionValid = true
        }
        if (binding.movieGanre.selectedItem.toString() != GENRE_EQUAL) {
            viewModel.genreValid = true
        }
        if (binding.longDescription.text.length >= LONG_VAL) {
            viewModel.longDescriptionValid = true
        }
        setupToastMessages()
    }

    private fun setupDatePicker() {
        val cal = Calendar.getInstance()
        val dateSelectedListener =
            OnDateSetListener { view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                cal[Calendar.YEAR] = year
                cal[Calendar.MONTH] = monthOfYear + 1
                cal[Calendar.DAY_OF_MONTH] = dayOfMonth
                binding.movieReleaseDate.text = year.toString()
            }

        val year = cal[Calendar.YEAR]
        val month = cal[Calendar.MONTH]
        val day = cal[Calendar.DAY_OF_MONTH]

        val dp = this.context?.let { DatePickerDialog(it, dateSelectedListener, year, month, day) }

        (dp?.datePicker as ViewGroup).findViewById<View>(
            resources.getIdentifier("day", "id", "android")
        ).visibility = View.GONE
        (dp.datePicker as ViewGroup).findViewById<View>(
            resources.getIdentifier("month", "id", "android")
        ).visibility = View.GONE

        binding.movieReleaseDate.setOnClickListener {
            dp.show()
        }
    }

    private fun setupSpinner() {
        this.context?.let {
            ArrayAdapter.createFromResource(
                it,
                R.array.genre_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.movieGanre.adapter = adapter
            }
        }
    }

    private fun setObservers() {
        viewModel.eventMoviePosterPressed.observe(viewLifecycleOwner, {
            if (it) {
                binding.createMovieImage.setImageResource(viewModel.getDrawableImage())
                binding.createMovieImage.setBackgroundColor(resources.getColor(R.color.white))
                viewModel.pressMoviePosterEnd()
            }
        })

        viewModel.eventCreateMovieButtonPressed.observe(viewLifecycleOwner, {
            if (it) {
                if (viewModel.isFromEdit.not()) {
                    MovieList.addMovie(
                        Movie(
                            viewModel.currentImageResource,
                            binding.movieTitle.text.toString(),
                            binding.movieReleaseDate.text.toString(),
                            binding.shortDescription.text.toString(),
                            binding.movieGanre.selectedItem.toString(),
                            binding.longDescription.text.toString()
                        )
                    )
                } else {
                    setMovieDetailsFromEditedView()
                }
                binding.root.findNavController()
                    .navigate(R.id.action_createMovieFragment_to_movieListFragment)
                viewModel.pressCreateMovieButtonEnd()
            }
        })
    }

    private fun setMovieDetailsFromEditedView() {
        MovieList.getList().forEach { currentMovie ->
            if (currentMovie.imageDrawable == viewModel.editMovieTag) {
                if (binding.shortDescription.text.toString() != "") {
                    currentMovie.shortDescription =
                        binding.shortDescription.text.toString()
                }

                if (currentMovie.genre != GENRE_EQUAL) {
                    currentMovie.genre = binding.movieGanre.selectedItem.toString()
                }

                if (binding.longDescription.text.toString() != "") {
                    currentMovie.longDescription =
                        binding.longDescription.text.toString()
                }
            }
        }
    }

    private fun setupToastMessages() {
        if (viewModel.imageValid.not()) {
            Toast.makeText(context, R.string.image_not_valid, Toast.LENGTH_LONG).show()
        }
        if (viewModel.titleValid.not()) {
            Toast.makeText(context, R.string.title_not_valid, Toast.LENGTH_LONG).show()
        }
        if (viewModel.yearValid.not()) {
            Toast.makeText(context, R.string.year_not_valid, Toast.LENGTH_LONG).show()
        }
        if (viewModel.shortDescriptionValid.not()) {
            Toast.makeText(context, R.string.short_description_not_valid, Toast.LENGTH_LONG).show()
        }
        if (viewModel.genreValid.not()) {
            Toast.makeText(context, R.string.genre_not_valid, Toast.LENGTH_LONG).show()
        }
        if (viewModel.longDescriptionValid.not()) {
            Toast.makeText(context, R.string.long_description_not_valid, Toast.LENGTH_LONG).show()
        }
    }

    private fun setViewListeners() {
        binding.createMovieImage.setOnClickListener {
            viewModel.pressMoviePoster()
        }

        binding.createMovieButton.setOnClickListener {
            validateFields()
            viewModel.pressCreateMovieButton()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("Not yet implemented")
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    companion object {
        const val TITLE_VAL = 3
        const val YEAR_VAL = "Year"
        const val SHORT_VAL = 3
        const val LONG_VAL = 10
        const val GENRE_EQUAL = "Genre"
    }
}
