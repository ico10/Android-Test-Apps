package com.sfa.android.currentbitcointracker.ui.savedrates

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.sfa.android.currentbitcointracker.R
import com.sfa.android.currentbitcointracker.databinding.FragmentArchiveBinding
import com.sfa.android.currentbitcointracker.network.BitcoinDatabase

class ArchiveFragment : Fragment() {

    private lateinit var binding: FragmentArchiveBinding
    private lateinit var viewModel: ArchiveViewModel
    private lateinit var adapter: ArchiveAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentArchiveBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application
        val dataSource = BitcoinDatabase.getInstance(application).bitcoinDatabaseDao

        val archiveViewModelFactory = ArchiveViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, archiveViewModelFactory).get(ArchiveViewModel::class.java)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapters()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.archivedCurrencies.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
    }

    private fun setupAdapters() {
        adapter = ArchiveAdapter(ArchiveAdapter.ButtonClickListener{
            viewModel.onDeleteButtonPressed(it)
            Toast.makeText(context, getText(R.string.currency_deleted_text), Toast.LENGTH_SHORT).show()
        }).also { binding.currencyList.adapter = it }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.archive_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete_all_action){
            viewModel.deleteAllRecords()
        }
        return super.onOptionsItemSelected(item)
    }
}
