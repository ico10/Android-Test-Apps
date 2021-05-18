package com.sfa.android.concentration4.game_intro

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameIntroViewModel : ViewModel() {

    private val _numberOfCards = MutableLiveData<Int>()
    val numberOfCards: LiveData<Int>
        get() = _numberOfCards

    fun setNumberOfCards(number: Int) {
        _numberOfCards.value = number
    }

    fun resetNumberOfCards() {
        _numberOfCards.value = null
    }

    fun getArrayNumberOfCards(): ArrayList<Int> {
        val tempList = mutableListOf<Int>()
        for (i in 0..12 step 2) {
            tempList.add(i)
        }
        return tempList as ArrayList<Int>
    }
}
