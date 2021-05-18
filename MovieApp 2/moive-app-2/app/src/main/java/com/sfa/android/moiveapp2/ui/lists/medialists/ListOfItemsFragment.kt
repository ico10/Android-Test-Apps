package com.sfa.android.moiveapp2.ui.lists.medialists

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.sfa.android.moiveapp2.R
import com.sfa.android.moiveapp2.databinding.FragmentListOfItemsBinding
import com.sfa.android.moiveapp2.network.userlistmodels.MediaListItem


class ListOfItemsFragment : Fragment() {

    private lateinit var binding: FragmentListOfItemsBinding
    private lateinit var viewModel: ListOfItemsViewModel
    private lateinit var adapter: ListOfItemsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentListOfItemsBinding.inflate(inflater)

        val listOfItemsFragmentArgs by navArgs<ListOfItemsFragmentArgs>()
        val viewModelFactory = ListOfItemsFactory(listOfItemsFragmentArgs.listId)
        viewModel = ViewModelProvider(this, viewModelFactory).get(ListOfItemsViewModel::class.java)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
        setAdapters()
    }

    private fun setObservers() {
        viewModel.media.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        viewModel.currentMediaToDelete.observe(viewLifecycleOwner, {
            viewModel.deleteMediaItemsFromList(it)
        })

        viewModel.navigateToAddMedia.observe(viewLifecycleOwner, {
            if (it != DEFAULT_ID) {
                this.findNavController().navigate(
                    ListOfItemsFragmentDirections.actionListOfItemsFragmentToAddMediaToListFragment(
                        it
                    )
                )
                viewModel.addMediaToListPressedCompleted()
            }
        })

        viewModel.success.observe(viewLifecycleOwner, {
            if (it.success) {
                viewModel.getSingleMediaList()
            }
        })

        viewModel.deleteListSuccess.observe(viewLifecycleOwner, {
            if (it.success) {
                this.findNavController().navigate(R.id.navigation_list)
            }
        })
    }

    private fun setAdapters() {
        adapter = ListOfItemsAdapter(ListOfItemsAdapter.OnClickListener {
            this.findNavController().navigate(
                ListOfItemsFragmentDirections.actionListOfItemsFragmentToDetailsFragment(it)
            )
        }, ListOfItemsAdapter.ButtonClickListener {
            val current = MediaListItem(it.id, it.getMediaType())
            viewModel.addItemToList(current)
        })
        binding.mediaGrid.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.single_list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_item_to_list -> viewModel.addMediaToListPressed()
            R.id.delete_menu_item -> setupDialogAlert()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupDialogAlert() {
        val dialogClickListener =
            DialogInterface.OnClickListener { dialog, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        viewModel.deleteListPressed()
                    }
                    DialogInterface.BUTTON_NEGATIVE -> {
                    }
                }
            }

        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
            .setNegativeButton("No", dialogClickListener).show()
    }

    companion object {
        const val DEFAULT_ID = 0L
    }
}