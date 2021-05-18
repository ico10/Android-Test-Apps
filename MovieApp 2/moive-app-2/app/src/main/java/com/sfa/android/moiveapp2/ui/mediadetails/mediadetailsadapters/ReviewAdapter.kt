package com.sfa.android.moiveapp2.ui.mediadetails.mediadetailsadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sfa.android.moiveapp2.databinding.ItemReviewBinding
import com.sfa.android.moiveapp2.network.moviedetailsmodel.Review

class ReviewAdapter: ListAdapter<Review, ReviewAdapter.ReviewViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        return ReviewViewHolder(ItemReviewBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }

    class ReviewViewHolder(private var binding: ItemReviewBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(review: Review) {
            binding.reviewer.text = review.author
            binding.reviewText.text = review.content
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Review>() {
        override fun areItemsTheSame(oldItem: Review, newItem: Review): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Review, newItem: Review): Boolean {
            return oldItem.id == newItem.id
        }
    }
}
