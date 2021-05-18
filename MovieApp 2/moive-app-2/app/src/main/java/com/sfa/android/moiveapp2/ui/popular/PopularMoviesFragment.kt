package com.sfa.android.moiveapp2.ui.popular

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.navigation.fragment.findNavController
import com.sfa.android.moiveapp2.R

class PopularMoviesFragment: PopularMedia() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
    }

    private fun setObservers() {
        mediaViewModel.movies.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        mediaViewModel.navigateToSelectedMedia.observe(viewLifecycleOwner, {
            if (it != null){
                it.setMediaType(MediaType.MOVIE.type)
                this.findNavController().navigate(PopularMoviesFragmentDirections
                    .actionNavigationMovieToDetailsFragment(it))
                mediaViewModel.resetCurrentlyClickedMedia()
            }
        })
    }
}
