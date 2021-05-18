package com.sfa.android.moiveapp2.ui.mediadetails.mediadetailsadapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sfa.android.moiveapp2.databinding.ItemVideoBinding
import com.sfa.android.moiveapp2.network.moviedetailsmodel.Video

private const val YOUTUBE_URL = "https://www.youtube.com/watch?v="

class VideoListAdapter : ListAdapter<Video, VideoListAdapter.VideoViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder(ItemVideoBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = getItem(position)
        holder.itemView.setOnClickListener {
            val openVideoPage = Intent(Intent.ACTION_VIEW)
            openVideoPage.data = Uri.parse(YOUTUBE_URL + video.key)
            startActivity(it.context, openVideoPage, null)
        }
        holder.bind(video)
    }

    class VideoViewHolder(private var binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(video: Video) {
            binding.video = video
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Video>() {
        override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
