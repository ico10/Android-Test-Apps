package com.sfa.android.moiveapp2.ui.lists.medialists

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sfa.android.moiveapp2.R
import com.sfa.android.moiveapp2.databinding.FragmentAddMediaToListBinding
import com.sfa.android.moiveapp2.network.userlistmodels.MediaListItem
import com.sfa.android.moiveapp2.ui.popular.PopularMediaAdapter

class AddMediaToListFragment : Fragment() {

    private lateinit var binding: FragmentAddMediaToListBinding
    private lateinit var viewModel: AddMediaToListViewModel
    private lateinit var adapterMovies: PopularMediaAdapter
    private lateinit var adapterSeries: PopularMediaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAddMediaToListBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val addMediaToListFragmentArgs by navArgs<AddMediaToListFragmentArgs>()
        val viewModelFactory = AddMediaViewModelFactory(addMediaToListFragmentArgs.listId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddMediaToListViewModel::class.java)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setAdapter()
        setObservers()
    }

    private fun setObservers() {
        viewModel.movies.observe(viewLifecycleOwner, {
            adapterMovies.submitList(it)
        })

        viewModel.series.observe(viewLifecycleOwner, {
            adapterSeries.submitList(it)
        })

        viewModel.currentMediaToAdd.observe(viewLifecycleOwner, {
            viewModel.addMediaItemsToList(it)
        })
    }

    private fun setAdapter() {
        adapterMovies = PopularMediaAdapter(PopularMediaAdapter.OnClickListener{
            Toast.makeText(context, R.string.movie_added_text,Toast.LENGTH_SHORT).show()
            it.setMediaType("movie")
            val current = MediaListItem(it.id, it.getMediaType())
            viewModel.addItemToList(current)
        })
        adapterSeries = PopularMediaAdapter(PopularMediaAdapter.OnClickListener{
            Toast.makeText(context, R.string.tv_added_text,Toast.LENGTH_SHORT).show()
            it.setMediaType("tv")
            val current = MediaListItem(it.id, it.getMediaType())
            viewModel.addItemToList(current)
        })
        binding.moviesGrid.adapter = adapterMovies
        binding.seriesGrid.adapter = adapterSeries
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.all_media_add_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.done_button -> this.findNavController()
                .navigate(AddMediaToListFragmentDirections.actionAddMediaToListFragmentToListOfItemsFragment(viewModel.listId))
        }
        return super.onOptionsItemSelected(item)
    }
}