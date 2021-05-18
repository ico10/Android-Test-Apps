package com.sfa.android.moiveapp2.ui.lists.medialists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sfa.android.moiveapp2.databinding.GridViewListItemBinding
import com.sfa.android.moiveapp2.network.Media

class ListOfItemsAdapter(private val onClickListener: OnClickListener, private val buttonClickListener: ButtonClickListener) : ListAdapter<Media, ListOfItemsAdapter.MediaViewHolder>(
    DiffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        return MediaViewHolder(GridViewListItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        val media = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(media)
        }
        holder.bind(media, buttonClickListener)
    }

    class MediaViewHolder(private var binding: GridViewListItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(media: Media, buttonClickListener: ButtonClickListener) {
            binding.media = media
            binding.textView.text = media.getMediaTitle()

            binding.deleteButton.setOnClickListener {
                buttonClickListener.onClick(media)
            }
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

    class ButtonClickListener(val clickListener: (media: Media) -> Unit) {
        fun onClick(media: Media) = clickListener(media)
    }
}