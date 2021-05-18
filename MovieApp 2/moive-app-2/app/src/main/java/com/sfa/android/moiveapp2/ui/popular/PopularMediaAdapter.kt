package com.sfa.android.moiveapp2.ui.popular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sfa.android.moiveapp2.databinding.GridViewItemBinding

import com.sfa.android.moiveapp2.network.Media

class PopularMediaAdapter(private val onClickListener: OnClickListener) : ListAdapter<Media, PopularMediaAdapter.MediaViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        return MediaViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        val media = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(media)
        }
        holder.bind(media)
    }

    class MediaViewHolder(private var binding: GridViewItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(media: Media) {
            binding.media = media
            binding.textView.text = media.getMediaTitle()
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Media>() {
        override fun areItemsTheSame(oldItem: Media, newItem: Media): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Media, newItem: Media): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class OnClickListener(val clickListener: (media: Media) -> Unit) {
        fun onClick(media: Media) = clickListener(media)
    }
}
