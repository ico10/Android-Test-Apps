package com.sfa.android.currentbitcointracker.ui.rates

import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.sfa.android.currentbitcointracker.R
import com.sfa.android.currentbitcointracker.databinding.FragmentRatesBinding
import com.sfa.android.currentbitcointracker.network.BitcoinDatabase
import com.sfa.android.currentbitcointracker.ui.formatCurrencyText

class RatesFragment : Fragment() {

    private lateinit var binding: FragmentRatesBinding
    private lateinit var viewModel: RatesViewModel
    private val handler = Handler()
    private lateinit var runnable: Runnable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRatesBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application
        val dataSource = BitcoinDatabase.getInstance(application).bitcoinDatabaseDao

        val ratesViewModelFactory = RatesViewModelFactory(dataSource)
        viewModel = ViewModelProvider(this, ratesViewModelFactory).get(RatesViewModel::class.java)

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListeners()
        setObservers()
        setupTimer()
    }

    private fun setupTimer() {
        runnable = object : Runnable {
            override fun run() {
                viewModel.refreshBitcoinIndex()
                handler.postDelayed(this, MIN_TO_MILLISECOND)
            }
        }
        handler.postDelayed(runnable, 100)
    }

    private fun setOnClickListeners() {
        val toast = Toast.makeText(context, getText(R.string.currency_archived_text), Toast.LENGTH_SHORT)
        binding.usdAddButton.setOnClickListener{
            viewModel.onAddToArchivePressed(viewModel.getCurrencyPressed(CurrencyCode.USD.code))
            toast.show()
        }
        binding.eurAddButton.setOnClickListener{
            viewModel.onAddToArchivePressed(viewModel.getCurrencyPressed(CurrencyCode.EUR.code))
            toast.show()
        }
        binding.gbpAddButton.setOnClickListener{
            viewModel.onAddToArchivePressed(viewModel.getCurrencyPressed(CurrencyCode.GBP.code))
            toast.show()
        }
    }

    private fun setObservers() {
        viewModel.time.observe(viewLifecycleOwner, { time ->
            binding.time.text = time
        })

        viewModel.usd.observe(viewLifecycleOwner, {
            binding.usdBox.text = formatCurrencyText(it)
        })

        viewModel.eur.observe(viewLifecycleOwner, {
            binding.eurBox.text = formatCurrencyText(it)
        })

        viewModel.gbp.observe(viewLifecycleOwner, {
            binding.gbpBox.text = formatCurrencyText(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.rates_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_archive){
            this.findNavController().navigate(R.id.action_ratesFragment_to_savedRatesFragment)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(runnable)
    }

    companion object{
        private const val MIN_TO_MILLISECOND = 60000L
    }
}
