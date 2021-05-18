package com.sfa.android.moiveapp2.ui.lists

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sfa.android.moiveapp2.R
import com.sfa.android.moiveapp2.databinding.FragmentAddListBinding

class AddListFragment : Fragment() {

    private lateinit var binding: FragmentAddListBinding
    private lateinit var viewModel: AddListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentAddListBinding.inflate(inflater)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(AddListViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setClickListeners()
        setObservers()
    }

    private fun setClickListeners() {
        binding.createListButton.setOnClickListener {
            if (binding.listName.text.toString().isNotEmpty()) {
                viewModel.pressCreateButton(binding.listName.text.trim().toString())
                activity?.let { it1 -> hideSoftKeyboard(it1) }
            } else {
                Toast.makeText(context, R.string.name_not_present, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setObservers() {
        viewModel.success.observe(viewLifecycleOwner, {
            if (it.success) {
                this.findNavController().navigate(AddListFragmentDirections.actionAddListFragmentToAddMediaToListFragment(it.id))
                viewModel.resetSuccess()
            }
        })
    }

    private fun hideSoftKeyboard(activity: Activity) {
        if (activity.currentFocus == null){
            return
        }
        val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity.currentFocus?.windowToken, 0)
    }
}