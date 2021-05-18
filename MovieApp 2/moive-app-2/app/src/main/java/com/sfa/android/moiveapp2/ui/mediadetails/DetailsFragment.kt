package com.sfa.android.moiveapp2.ui.mediadetails

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sfa.android.moiveapp2.R
import com.sfa.android.moiveapp2.databinding.FragmentDetailsBinding
import com.sfa.android.moiveapp2.ui.mediadetails.mediadetailsadapters.GenresAdapter
import com.sfa.android.moiveapp2.ui.mediadetails.mediadetailsadapters.ReviewAdapter
import com.sfa.android.moiveapp2.ui.mediadetails.mediadetailsadapters.VideoListAdapter

open class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private lateinit var viewModel: DetailsViewModel
    private lateinit var adapterGenres: GenresAdapter
    private lateinit var adapterVideo: VideoListAdapter
    private lateinit var adapterReview: ReviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val detailsFragmentArgs by navArgs<DetailsFragmentArgs>()
        val viewModelFactory = DetailsViewModelFactory(detailsFragmentArgs.selectedMedia)
        viewModel = ViewModelProvider(this, viewModelFactory).get(DetailsViewModel::class.java)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
        setupAdapter()
    }

    private fun setObservers() {
        viewModel.currentMedia.observe(viewLifecycleOwner, {
            binding.apply {
                mediaTitle.text = it.getMediaTitle()
                releaseDate.text = getString(R.string.release_date_text, it.getMediaReleaseDate())
                audio.text = getString(R.string.audio_text, it.originalLanguage)
                ratingBar.numStars = MAX_NUM_STARTS
                ratingBar.rating = it.voteAverage.toFloat()
                setupImageWithGlide(movieDetailsPoster, it.posterFile)
                adapterGenres.submitList(it.genres)
                adapterVideo.submitList(it.videos?.results)
                overviewText.text = it.overview
            }
        })

        viewModel.currentMediaReviews.observe(viewLifecycleOwner, {
            adapterReview.submitList(it)
        })
    }

    private fun setupAdapter() {
        adapterGenres = GenresAdapter()
        binding.genreGrid.adapter = adapterGenres

        adapterVideo = VideoListAdapter()
        binding.trailers.adapter = adapterVideo

        adapterReview = ReviewAdapter()
        binding.reviewsList.adapter = adapterReview
    }

    private fun setupImageWithGlide(movieDetailsPoster: ImageView, posterFile: String) {
        val image = BASE_IMAGE_URL + posterFile
        val imgUri = image.toUri().buildUpon().scheme("https").build()
        Glide.with(movieDetailsPoster.context)
            .load(imgUri)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
            )
            .into(movieDetailsPoster)
    }

    companion object {
        const val BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w500"
        const val MAX_NUM_STARTS = 10
    }
}
