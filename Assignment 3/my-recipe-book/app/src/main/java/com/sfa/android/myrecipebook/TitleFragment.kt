package com.sfa.android.myrecipebook

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.sfa.android.myrecipebook.databinding.FragmentTitleBinding

class TitleFragment : Fragment() {

    private lateinit var binding: FragmentTitleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= DataBindingUtil.inflate(
            inflater, R.layout.fragment_title,
            container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setRecipeListeners()
    }

    private fun setRecipeListeners() {
        binding.run {
            val listOfRecipes = listOf(recipeOne, recipeTwo, recipeThree, recipeFour, recipeFive)

            listOfRecipes.forEach {
                it.setOnClickListener { view ->
                    chooseRecipe(view)
                }
            }
        }
    }

    private fun chooseRecipe(view: View?) {
        binding.apply {
            when (view) {
                recipeOne -> recipeOne.findNavController()
                    .navigate(R.id.action_titleFragment_to_recipeOneFragment)
                recipeTwo -> recipeTwo.findNavController()
                    .navigate(R.id.action_titleFragment_to_recipeTwoFragment)
                recipeThree -> recipeThree.findNavController()
                    .navigate(R.id.action_titleFragment_to_recipeThreeFragment)
                recipeFour -> recipeFour.findNavController()
                    .navigate(R.id.action_titleFragment_to_recipeFourFragment)
                recipeFive -> recipeFive.findNavController()
                    .navigate(R.id.action_titleFragment_to_recipeFiveFragment)
            }
        }
    }
}
