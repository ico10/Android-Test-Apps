package com.sfa.android.concentration4.game.adapter.model

import androidx.annotation.DrawableRes
import com.sfa.android.concentration4.R

data class Card(
        var cardId: Int = 0,
        @DrawableRes var picture: Int = 0,
        val backPicture: Int = R.drawable.back,
        var isClicked: Boolean = false,
        var isMatched: Boolean = false,
        var isGone: Boolean = false,
        var isNotMatched: Boolean = false
)
