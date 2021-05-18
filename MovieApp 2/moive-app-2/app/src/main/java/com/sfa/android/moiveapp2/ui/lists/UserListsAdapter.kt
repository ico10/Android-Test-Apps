package com.sfa.android.moiveapp2.ui.lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sfa.android.moiveapp2.databinding.ItemUserListBinding
import com.sfa.android.moiveapp2.network.userlistmodels.MediaList

class UserListsAdapter(private val onClickListener: OnClickListener, private val buttonClickListener: ButtonClickListener): ListAdapter<MediaList, UserListsAdapter.MediaListViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaListViewHolder {
        return MediaListViewHolder(ItemUserListBinding.inflate(LayoutInflater.from(parent.context)), buttonClickListener)
    }

    override fun onBindViewHolder(holder: MediaListViewHolder, position: Int) {
        val mediaList = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(mediaList)
        }
        holder.bind(mediaList)
    }

    class MediaListViewHolder(
        private var binding: ItemUserListBinding,
        private val buttonClickListener: ButtonClickListener
    ):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(mediaList: MediaList) {
            binding.deleteListButton.setOnClickListener {
                buttonClickListener.onClick(mediaList)
            }
            binding.mediaList = mediaList
            binding.mediaListName.text = mediaList.name
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<MediaList>() {
        override fun areItemsTheSame(oldItem: MediaList, newItem: MediaList): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: MediaList, newItem: MediaList): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class OnClickListener(val clickListener: (mediaList: MediaList) -> Unit) {
        fun onClick(mediaList: MediaList) = clickListener(mediaList)
    }

    class ButtonClickListener(val clickListener: (mediaList: MediaList) -> Unit) {
        fun onClick(mediaList: MediaList) = clickListener(mediaList)
    }
}