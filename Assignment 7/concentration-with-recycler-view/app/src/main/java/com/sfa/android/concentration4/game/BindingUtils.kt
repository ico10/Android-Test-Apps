package com.sfa.android.concentration4.game

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.sfa.android.concentration4.game.adapter.model.Card

@BindingAdapter("cardPicture")
fun ImageView.setCardPicture(item: Card?) {
    isClickable = true
    if (item?.isClicked == true) {
        visibility = View.VISIBLE
        setImageResource(item.picture)
    } else {
        isClickable = false
        visibility = View.VISIBLE
        item?.backPicture?.let { setImageResource(it) }
    }
}
