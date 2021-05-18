package com.sfa.android.concentration2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isInvisible
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.sfa.android.concentration2.databinding.FragmentGameBinding
import java.util.*
import kotlin.collections.HashMap
import kotlin.concurrent.schedule

class GameFragment : Fragment() {

    private lateinit var binding: FragmentGameBinding
    private var pairsOfViews: HashMap<ImageView, Int> = HashMap(12)
    private lateinit var listOfPictures: ArrayList<Int>
    private lateinit var allViews: List<ImageView>
    private lateinit var firstClicked: ImageView
    private var foundFirst: Boolean = false
    private var matches = 6
    private val listOfKeys = listOf(
        KEY_BOX_ONE, KEY_BOX_TWO, KEY_BOX_THREE, KEY_BOX_FOUR, KEY_BOX_FIVE, KEY_BOX_SIX,
        KEY_BOX_SEVEN, KEY_BOX_EIGHT, KEY_BOX_NINE, KEY_BOX_TEN, KEY_BOX_ELEVEN, KEY_BOX_TWELVE
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            allViews = listOf(
                boxOne, boxTwo, boxThree, boxFour, boxFive, boxSix,
                boxSeven, boxEight, boxNine, boxTen, boxEleven, boxTwelve
            )
        }

        savedInstanceState?.let {
            listOfKeys.forEachIndexed { index, item ->
                if (savedInstanceState.getBoolean(item)) {
                    allViews[index].isInvisible = true
                }
            }
            attempts = savedInstanceState.getInt(KEY_ATTEMPTS)
            matches = savedInstanceState.getInt(KEY_MATCHES)
            listOfPictures = savedInstanceState.getIntegerArrayList(KEY_PICTURES) as ArrayList<Int>
        } else {
            shufflePictures()
            attempts = 0
        }

        setPictures()
        setListeners()
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
        ).shuffled() as ArrayList<Int>
    }

    private fun setPictures() {


        allViews.forEachIndexed { index, imageView ->
            pairsOfViews[imageView] = listOfPictures[index]
        }
    }

    private fun setListeners() {
        pairsOfViews.keys.forEach {
            it.setOnClickListener { view ->
                changePicture(view as ImageView)
            }
        }
    }

    private fun changePicture(view: ImageView) {
        view.isClickable = false
        val resourcePicture = pairsOfViews[view]
        resourcePicture?.let { setPictureOfView(it, view) }

        if (foundFirst.not()) {
            firstClicked = view
            foundFirst = true
        } else {
            changeViewVisibility(view)
            foundFirst = false
        }
    }

    private fun changeViewVisibility(view: ImageView) {
        attempts++
        if (firstClicked.drawable.toBitmap().sameAs(view.drawable.toBitmap())) {
            Timer().schedule(500) {
                firstClicked.visibility = View.INVISIBLE
                view.visibility = View.INVISIBLE
            }
            matches--
            navigateApplicationIfAllMatched()
        } else {
            Timer().schedule(200) {
                firstClicked.setImageResource(R.mipmap.back)
                view.setImageResource(R.mipmap.back)
            }
        }
        firstClicked.isClickable = true
        view.isClickable = true
    }

    private fun navigateApplicationIfAllMatched() {
        if (matches == 0) {
            binding.root.findNavController().navigate(R.id.action_gameFragment_to_gameWinFragment)
        }
    }

    private fun setPictureOfView(resourcePicture: Int, view: ImageView) {
        binding.apply {
            when (view) {
                boxOne -> boxOne.setImageResource(resourcePicture)
                boxTwo -> boxTwo.setImageResource(resourcePicture)
                boxThree -> boxThree.setImageResource(resourcePicture)
                boxFour -> boxFour.setImageResource(resourcePicture)
                boxFive -> boxFive.setImageResource(resourcePicture)
                boxSix -> boxSix.setImageResource(resourcePicture)
                boxSeven -> boxSeven.setImageResource(resourcePicture)
                boxEight -> boxEight.setImageResource(resourcePicture)
                boxNine -> boxNine.setImageResource(resourcePicture)
                boxTen -> boxTen.setImageResource(resourcePicture)
                boxEleven -> boxEleven.setImageResource(resourcePicture)
                boxTwelve -> boxTwelve.setImageResource(resourcePicture)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        listOfKeys.forEachIndexed { index, item ->
            outState.putBoolean(item, allViews[index].isInvisible)

        }
        outState.putInt(KEY_MATCHES, matches)
        outState.putInt(KEY_ATTEMPTS, attempts)
        outState.putIntegerArrayList(KEY_PICTURES, listOfPictures)
    }

    companion object {
        private const val KEY_BOX_ONE = "key_box_one"
        private const val KEY_BOX_TWO = "key_box_two"
        private const val KEY_BOX_THREE = "key_box_three"
        private const val KEY_BOX_FOUR = "key_box_four"
        private const val KEY_BOX_FIVE = "key_box_five"
        private const val KEY_BOX_SIX = "key_box_six"
        private const val KEY_BOX_SEVEN = "key_box_seven"
        private const val KEY_BOX_EIGHT = "key_box_eight"
        private const val KEY_BOX_NINE = "key_box_nine"
        private const val KEY_BOX_TEN = "key_box_ten"
        private const val KEY_BOX_ELEVEN = "key_box_eleven"
        private const val KEY_BOX_TWELVE = "key_box_twelve"
        private const val KEY_MATCHES = "key_matches"
        private const val KEY_ATTEMPTS = "key_attempts"
        private const val KEY_PICTURES = "key_pictures"

        private var attempts = 0
        fun getAttempts(): Int = attempts
    }
}
