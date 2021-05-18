package com.sfa.android.concentration3.game

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sfa.android.concentration3.R

class GameViewModel : ViewModel() {

    lateinit var listOfPictures: List<Int>

    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: LiveData<Boolean>
        get() = _eventGameFinish

    private var _invisibilityViews: MutableList<String> = mutableListOf()
    val invisibilityViews: List<String>
        get() = _invisibilityViews

    private val _attempts = MutableLiveData<Int>()
    val attempts: LiveData<Int>
        get() = _attempts

    private var matches = 6

    init {
        _attempts.value = 0
        shufflePictures()
    }

    private fun shufflePictures() {
        listOfPictures = listOf(
            R.mipmap.apple,
            R.mipmap.apple,
            R.mipmap.banana,
            R.mipmap.banana,
            R.mipmap.kiwi,
            R.mipmap.kiwi,
            R.mipmap.cherry,
            R.mipmap.cherry,
            R.mipmap.grape,
            R.mipmap.grape,
            R.mipmap.pear,
            R.mipmap.pear
        ).shuffled()
    }

    fun matched() {
        matches--
        if (matches == 0) {
            _eventGameFinish.value = true
        }
    }

    fun attempted() {
        _attempts.value = _attempts.value?.plus(1)
    }

    fun onGameFinishComplete() {
        _eventGameFinish.value = false
    }

    fun addInvisibleView(view: String) {
        _invisibilityViews.add(view)
    }
}
