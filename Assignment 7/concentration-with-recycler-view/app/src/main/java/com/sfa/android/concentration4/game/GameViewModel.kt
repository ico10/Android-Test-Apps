package com.sfa.android.concentration4.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sfa.android.concentration4.game.adapter.model.Card

class GameViewModel(val numberOfCards: Int) : ViewModel() {

    private var listOfCards = listOf<Card>()

    private var firstCard: Card? = null
    private var foundFirstCard: Boolean = false

    private var _hasListChanged = MutableLiveData<Boolean>().apply { value = false }
    val hasListChanged: LiveData<Boolean>
        get() = _hasListChanged

    private val _eventNavigateToGameWin = MutableLiveData<Boolean>().apply { value = false }
    val eventNavigateToGameWin: LiveData<Boolean>
        get() = _eventNavigateToGameWin

    private var attempts = 0
    private var matches = numberOfCards.div(2)

    fun getCurrentListOfCards(): List<Card> = listOfCards

    fun getCurrentAttempts(): Int = attempts

    fun doneChangingList() {
        _hasListChanged.value = false
    }

    fun doneNavigation() {
        _eventNavigateToGameWin.value = false
    }

    fun getListFromSharedViewModel(cards: List<Card>) {
        listOfCards = cards
        _hasListChanged.value = true
    }

    fun onSleepNightClicked(cardId: Int) {
        listOfCards.forEach {
            if (it.cardId == cardId) {
                setAreCardsMatching(it)
            }
        }
        _hasListChanged.value = true
    }

    private fun setAreCardsMatching(card: Card) {
        card.isClicked = true
        if (foundFirstCard.not()) {
            foundFirstCard = true
            firstCard = card
        } else {
            checkIfBothCardsMatch(card)
            attempts++
        }
        _hasListChanged.value = true
    }

    private fun checkIfBothCardsMatch(card: Card) {
        if (firstCard?.picture == card.picture) {
            setFieldsIfCardsMatch(card)
        } else {
            firstCard?.isNotMatched = true
            card.isNotMatched = true
            firstCard?.isClicked = false
            card.isClicked = false
        }
        foundFirstCard = false
    }

    private fun setFieldsIfCardsMatch(card: Card) {
        calculateMatches()
        firstCard?.isMatched = true
        card.isMatched = true
    }

    private fun calculateMatches() {
        matches--
        if (matches == 0) {
            _eventNavigateToGameWin.value = true
        }
    }
}
