package com.sfa.android.concentration

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.sfa.android.concentration.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.HashMap
import kotlin.concurrent.schedule
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firstClicked: ImageView
    private lateinit var secondClicked: ImageView
    private var pairsOfViews: HashMap<ImageView, Int> = HashMap(12)
    private var foundFirst: Boolean = false
    private var foundSecond: Boolean = false
    private var matches = 6

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setPictures()
        setListeners()
    }

    private fun setPictures() {
        val listOfPictures: List<Int> =
            listOf(
                R.drawable.apple,
                R.drawable.apple,
                R.drawable.banana,
                R.drawable.banana,
                R.drawable.kiwi,
                R.drawable.kiwi,
                R.drawable.cherry,
                R.drawable.cherry,
                R.drawable.grape,
                R.drawable.grape,
                R.drawable.pear,
                R.drawable.pear
            ).shuffled()

        binding.run {
            val backViews: List<ImageView> = listOf(
                firstBox, secondBox, thirdBox, fourthBox, fifthBox,
                sixthBox, seventhBox, eightBox, ninthBox, tenthBox, eleventhBox, twelfthBox
            )

            backViews.forEachIndexed { index, imageView ->
                pairsOfViews[imageView] = listOfPictures[index]
            }
        }

    }

    private fun setListeners() {
        val clickableViews: MutableSet<ImageView> = pairsOfViews.keys

        clickableViews.forEach {
            it.setOnClickListener { view ->
                changePicture(view as ImageView)
            }
        }
    }

    private fun changePicture(view: ImageView) {
        view.isClickable = false
        val resourcePicture = pairsOfViews[view]
        resourcePicture?.let { setPictureOfView(it, view) }

        if (!foundFirst) {
            firstClicked = view
            foundFirst = true
        } else if (foundFirst && !foundSecond) {
            secondClicked = view
            foundSecond = true
            closeApplicationIfAllMatched()
        } else {
            changeViewVisibility()
            firstClicked = view
            foundSecond = false
        }
    }

    private fun closeApplicationIfAllMatched() {
        Timer().schedule(500) {
            if (matches == 1) {
                exitProcess(0)
            }
        }
    }

    private fun changeViewVisibility() {
        if (firstClicked.drawable.bounds == secondClicked.drawable.bounds) {
            firstClicked.visibility = View.INVISIBLE
            secondClicked.visibility = View.INVISIBLE
            firstClicked.isClickable = true
            secondClicked.isClickable = true
            matches--

        } else {
            firstClicked.setImageResource(R.drawable.back)
            secondClicked.setImageResource(R.drawable.back)
            firstClicked.isClickable = true
            secondClicked.isClickable = true
        }
    }

    private fun setPictureOfView(resourcePicture: Int, view: ImageView) {
        binding.apply {
            when (view) {
                firstBox -> firstBox.setImageResource(resourcePicture)
                secondBox -> secondBox.setImageResource(resourcePicture)
                thirdBox -> thirdBox.setImageResource(resourcePicture)
                fourthBox -> fourthBox.setImageResource(resourcePicture)
                fifthBox -> fifthBox.setImageResource(resourcePicture)
                sixthBox -> sixthBox.setImageResource(resourcePicture)
                seventhBox -> seventhBox.setImageResource(resourcePicture)
                eightBox -> eightBox.setImageResource(resourcePicture)
                ninthBox -> ninthBox.setImageResource(resourcePicture)
                tenthBox -> tenthBox.setImageResource(resourcePicture)
                eleventhBox -> eleventhBox.setImageResource(resourcePicture)
                twelfthBox -> twelfthBox.setImageResource(resourcePicture)
            }
        }
    }
}

