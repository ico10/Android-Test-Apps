package com.sfa.android.moiveapp2.ui.popular

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController

class PopularSeriesFragment: PopularMedia() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
    }

    private fun setObservers() {
        mediaViewModel.series.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        mediaViewModel.navigateToSelectedMedia.observe(viewLifecycleOwner, {
            if (it != null){
                it.setMediaType(MediaType.SERIES.type)
                this.findNavController().navigate(PopularSeriesFragmentDirections
                    .actionNavigationSeriesToDetailsFragment(it))
                mediaViewModel.resetCurrentlyClickedMedia()
            }
        })
    }
}
