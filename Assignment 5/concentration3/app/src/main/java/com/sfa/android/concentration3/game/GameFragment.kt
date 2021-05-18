package com.sfa.android.concentration3.game

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.sfa.android.concentration3.R
import com.sfa.android.concentration3.databinding.FragmentGameBinding
import java.util.*
import kotlin.concurrent.schedule

const val DELAY_TIME = 200L

class GameFragment : Fragment() {

    private lateinit var viewModel: GameViewModel
    private lateinit var binding: FragmentGameBinding
    private lateinit var allViews: List<ImageView>
    private lateinit var firstClicked: ImageView
    private var foundFirst: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        binding.gameViewModel = viewModel
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            allViews = listOf(
                boxOne, boxTwo, boxThree, boxFour, boxFive, boxSix,
                boxSeven, boxEight, boxNine, boxTen, boxEleven, boxTwelve
            )
            CardTags.values()
                .forEachIndexed { index, cardTags -> allViews[index].tag = cardTags.tag }
        }
        setBoxVisibility()
        setListeners()
    }

    private fun setListeners() {
        allViews.forEachIndexed { index, it ->
            it.setOnClickListener { view ->
                (view as ImageView).setImageResource(viewModel.listOfPictures[index])
                changePicture(view)
            }
        }
    }

    private fun changePicture(view: ImageView) {
        view.isClickable = false
        if (foundFirst.not()) {
            firstClicked = view
            foundFirst = true
        } else {
            changeViewVisibility(view)
            foundFirst = false
        }
    }

    private fun changeViewVisibility(view: ImageView) {
        viewModel.attempted()
        if (firstClicked.drawable.toBitmap().sameAs(view.drawable.toBitmap())) {
            Timer().schedule(DELAY_TIME) {
                firstClicked.visibility = View.INVISIBLE
                view.visibility = View.INVISIBLE
                viewModel.addInvisibleView(firstClicked.tag.toString())
                viewModel.addInvisibleView(view.tag.toString())
            }
            viewModel.matched()
            navigateApplicationIfAllMatched()
        } else {
            Timer().schedule(DELAY_TIME) {
                firstClicked.setImageResource(R.mipmap.back)
                view.setImageResource(R.mipmap.back)
            }
        }
        firstClicked.isClickable = true
        view.isClickable = true
    }

    private fun navigateApplicationIfAllMatched() {
        viewModel.eventGameFinish.observe(viewLifecycleOwner, { isFinished ->
            if (isFinished) {
                val currentScore = viewModel.attempts.value ?: 0
                val action =
                    GameFragmentDirections.actionGameFragmentToGameWinFragment(currentScore)
                binding.root.findNavController().navigate(action)
                viewModel.onGameFinishComplete()
            }
        })
    }

    private fun setBoxVisibility() {
        viewModel.invisibilityViews.forEach {
            binding.apply {
                when (it) {
                    CardTags.ONE.tag -> boxOne.visibility = View.INVISIBLE
                    CardTags.TWO.tag -> boxTwo.visibility = View.INVISIBLE
                    CardTags.THREE.tag -> boxThree.visibility = View.INVISIBLE
                    CardTags.FOUR.tag -> boxFour.visibility = View.INVISIBLE
                    CardTags.FIVE.tag -> boxFive.visibility = View.INVISIBLE
                    CardTags.SIX.tag -> boxSix.visibility = View.INVISIBLE
                    CardTags.SEVEN.tag -> boxSeven.visibility = View.INVISIBLE
                    CardTags.EIGHT.tag -> boxEight.visibility = View.INVISIBLE
                    CardTags.NINE.tag -> boxNine.visibility = View.INVISIBLE
                    CardTags.TEN.tag -> boxTen.visibility = View.INVISIBLE
                    CardTags.ELEVEN.tag -> boxEleven.visibility = View.INVISIBLE
                    CardTags.TWELVE.tag -> boxTwelve.visibility = View.INVISIBLE
                }
            }
        }
    }
}
