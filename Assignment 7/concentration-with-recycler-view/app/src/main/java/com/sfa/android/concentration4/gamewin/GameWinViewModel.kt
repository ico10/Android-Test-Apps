package com.sfa.android.concentration4.gamewin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameWinViewModel(attempts: Int) : ViewModel() {

    private val _attempts = MutableLiveData<Int>()
    val attempts: LiveData<Int>
        get() = _attempts

    private val _eventPlayAgain = MutableLiveData<Boolean>()
    val eventPlayAgain: LiveData<Boolean>
        get() = _eventPlayAgain

    private val _eventFinish = MutableLiveData<Boolean>()
    val eventFinish: LiveData<Boolean>
        get() = _eventFinish

    init {
        _attempts.value = attempts
    }

    fun onPlayAgain() {
        _eventPlayAgain.value = true
    }

    fun onPlayAgainComplete() {
        _eventPlayAgain.value = false
    }

    fun onFinish() {
        _eventFinish.value = true
    }
}
