package com.sfa.android.moiveapp2.ui.popular

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sfa.android.moiveapp2.R
import com.sfa.android.moiveapp2.databinding.FragmentPopularMediaBinding

open class PopularMedia: Fragment() {

    private lateinit var binding: FragmentPopularMediaBinding
    protected lateinit var mediaViewModel: PopularMediaViewModel
    protected lateinit var adapter: PopularMediaAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentPopularMediaBinding.inflate(inflater)
        binding.lifecycleOwner = this

        mediaViewModel = ViewModelProvider(this).get(PopularMediaViewModel::class.java)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapters()
    }

    private fun setupAdapters() {
        adapter = PopularMediaAdapter(PopularMediaAdapter.OnClickListener {
            mediaViewModel.setCurrentlyClickedMedia(it)
        })
        binding.mediaGrid.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.popular_media_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.sort_rating -> mediaViewModel.ratingSort()
            R.id.sort_release_date -> mediaViewModel.releaseDateSort()
            R.id.sort_title -> mediaViewModel.titleSort()
        }
        return super.onOptionsItemSelected(item)
    }
}
