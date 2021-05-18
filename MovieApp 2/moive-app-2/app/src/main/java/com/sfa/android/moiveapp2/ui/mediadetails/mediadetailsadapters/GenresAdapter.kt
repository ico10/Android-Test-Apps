package com.sfa.android.moiveapp2.ui.mediadetails.mediadetailsadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sfa.android.moiveapp2.databinding.ItemGenreBinding
import com.sfa.android.moiveapp2.network.moviedetailsmodel.Genre

class GenresAdapter: ListAdapter<Genre, GenresAdapter.GenreViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder(ItemGenreBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        val genre = getItem(position)
        holder.bind(genre)
    }

    class GenreViewHolder(private var binding: ItemGenreBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(genre: Genre) {
            binding.genreType.text = genre.name
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Genre>() {
        override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
