package com.sfa.android.colormyviews

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.sfa.android.colormyviews.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setListeners()
    }

    private fun setListeners() {
        val clickableViews: List<View> =
                listOf(binding.boxOneText, binding.boxTwoText, binding.boxThreeText,
                        binding.boxFourText, binding.boxFiveText, binding.constraintLayout,
                        binding.redButton, binding.yellowButton, binding.greenButton)

        for (item in clickableViews) {
            item.setOnClickListener { makeColored(it) }
        }
    }

    private fun makeColored(view: View) {
        when (view) {

            binding.boxOneText -> binding.boxOneText.setBackgroundColor(Color.DKGRAY)
            binding.boxTwoText -> binding.boxTwoText.setBackgroundColor(Color.GRAY)

            binding.boxThreeText -> binding.boxThreeText.setBackgroundResource(android.R.color.holo_green_light)
            binding.boxFourText -> binding.boxFourText.setBackgroundResource(android.R.color.holo_green_dark)
            binding.boxFiveText -> binding.boxFiveText.setBackgroundResource(android.R.color.holo_green_light)

            binding.redButton -> binding.boxThreeText.setBackgroundResource(R.color.my_red)
            binding.yellowButton -> binding.boxFourText.setBackgroundResource(R.color.my_yellow)
            binding.greenButton -> binding.boxFiveText.setBackgroundResource(R.color.my_green)

            else -> view.setBackgroundColor(Color.LTGRAY)
        }
    }
}
