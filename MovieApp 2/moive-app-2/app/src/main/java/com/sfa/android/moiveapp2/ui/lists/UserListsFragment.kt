package com.sfa.android.moiveapp2.ui.lists

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sfa.android.moiveapp2.R
import com.sfa.android.moiveapp2.databinding.FragmentUserListsBinding

class UserListsFragment : Fragment() {

    private lateinit var binding: FragmentUserListsBinding
    private lateinit var viewModel: UserListsViewModel
    private lateinit var adapterUserLists: UserListsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentUserListsBinding.inflate(inflater)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(UserListsViewModel::class.java)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
        setAdapters()
    }

    private fun setObservers() {
        viewModel.mediaLists.observe(viewLifecycleOwner, {
            adapterUserLists.submitList(it)
        })

        viewModel.navigateToMediaListItems.observe(viewLifecycleOwner, {
            if (it != DEFAULT_ID) {
                this.findNavController().navigate(
                    UserListsFragmentDirections.actionNavigationListToListOfItemsFragment(it)
                )
                viewModel.onMediaListPressedCompleted()
            }
        })

        viewModel.success.observe(viewLifecycleOwner, {
            if (it.success){
                viewModel.getAllMediaLists()
            }
        })
    }

    private fun setAdapters() {
        adapterUserLists = UserListsAdapter(UserListsAdapter.OnClickListener {
            viewModel.onMediaListPressed(it.id)
        }, UserListsAdapter.ButtonClickListener {
            viewModel.deleteList(it.id)
        })
        binding.mediaLists.adapter = adapterUserLists
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.all_list_view_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_list -> this.findNavController()
                .navigate(R.id.action_navigation_list_to_addListFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    companion object{
        const val DEFAULT_ID = 0L
    }
}
