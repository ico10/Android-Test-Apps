package com.sfa.android.concentration4.game.adapter

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sfa.android.concentration4.databinding.ListItemCardBinding
import com.sfa.android.concentration4.game.adapter.model.Card

class CardAdapter(private val clickListener: CardListener) :
        ListAdapter<Card, CardAdapter.ViewHolder>(
                CardDiffCallback()
        ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder.from(parent)


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(clickListener, getItem(position))
    }

    class ViewHolder private constructor(private val binding: ListItemCardBinding) :
            RecyclerView.ViewHolder(
                    binding.root
            ) {

        fun bind(clickListener: CardListener, item: Card) {
            binding.card = item
            binding.clickListener = clickListener

            setViews(item)
        }

        private fun setViews(item: Card) {
            binding.root.visibility = View.GONE
            if (item.isGone.not()) {
                binding.imageView.setImageResource(item.picture)
                binding.root.visibility = View.VISIBLE
                val handler = Handler()
                if (item.isClicked) {
                    checkIfCardsMatch(item, handler)
                } else {
                    binding.imageView.setImageResource(item.backPicture)
                }
            }
        }

        private fun checkIfCardsMatch(item: Card, handler: Handler) {
            if (item.isMatched) {
                handler.postDelayed({
                    binding.root.visibility = View.GONE
                }, 500)
                item.isGone = true
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemCardBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    class CardDiffCallback : DiffUtil.ItemCallback<Card>() {
        override fun areItemsTheSame(oldItem: Card, newItem: Card): Boolean {
            return oldItem.cardId == newItem.cardId
        }

        override fun areContentsTheSame(oldItem: Card, newItem: Card): Boolean {
            return oldItem == newItem
        }
    }
}

class CardListener(val clickListener: (picture: Int) -> Unit) {
    fun onClick(card: Card) = clickListener(card.cardId)
}
