package com.sfa.android.currentbitcointracker.ui.savedrates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sfa.android.currentbitcointracker.databinding.ItemCurrencyBinding
import com.sfa.android.currentbitcointracker.network.DatabaseCurrency
import com.sfa.android.currentbitcointracker.ui.formatCurrencyTextFromDatabase

class ArchiveAdapter(private val buttonClickListener: ButtonClickListener): ListAdapter<DatabaseCurrency, ArchiveAdapter.MediaListViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaListViewHolder {
        return MediaListViewHolder(ItemCurrencyBinding.inflate(LayoutInflater.from(parent.context)), buttonClickListener)
    }

    override fun onBindViewHolder(holder: MediaListViewHolder, position: Int) {
        val mediaList = getItem(position)
        holder.bind(mediaList)
    }

    class MediaListViewHolder(
        private var binding: ItemCurrencyBinding,
        private val buttonClickListener: ButtonClickListener
    ):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(databaseCurrency: DatabaseCurrency) {
            binding.currencyBox.text = formatCurrencyTextFromDatabase(databaseCurrency)
            binding.deleteButton.setOnClickListener {
                buttonClickListener.onClick(databaseCurrency)
            }
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<DatabaseCurrency>() {
        override fun areItemsTheSame(oldItem: DatabaseCurrency, newItem: DatabaseCurrency): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: DatabaseCurrency, newItem: DatabaseCurrency): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class ButtonClickListener(val clickListener: (databaseCurrency: DatabaseCurrency) -> Unit) {
        fun onClick(databaseCurrency: DatabaseCurrency) = clickListener(databaseCurrency)
    }
}
