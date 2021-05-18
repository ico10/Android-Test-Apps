package com.sfa.android.concentration4.game.adapter.model

import com.sfa.android.concentration4.R

object CardList {

    fun initCardsList(numberOfCards: Int): List<Card> {
        val listOfCards = mutableListOf<Card>()
        val listOfDrawables = listOf(
            R.drawable.apple,
            R.drawable.apple,
            R.drawable.banana,
            R.drawable.banana,
            R.drawable.grape,
            R.drawable.grape,
            R.drawable.pear,
            R.drawable.pear,
            R.drawable.kiwi,
            R.drawable.kiwi,
            R.drawable.cherry,
            R.drawable.cherry
        )

        for (i in 0 until numberOfCards) {
            val card = Card()
            card.cardId = i
            card.picture = listOfDrawables[i]
            listOfCards.add(card)
        }
        return listOfCards.toList().shuffled()
    }
}
