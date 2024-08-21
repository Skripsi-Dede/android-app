package com.example.gooutfit_android.ui.recommendation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.gooutfit_android.R
import com.example.gooutfit_android.databinding.ActivityFindOutfitBinding
import com.example.gooutfit_android.ui.onboarding.ViewPagerAdapter
import com.example.gooutfit_android.ui.recommendation.questionere.FirstQuestionFragment
import com.example.gooutfit_android.ui.recommendation.questionere.FourthQuestionFragment
import com.example.gooutfit_android.ui.recommendation.questionere.SecondQuestionFragment
import com.example.gooutfit_android.ui.recommendation.questionere.ThirdQuestionFragment
import com.example.gooutfit_android.ui.recommendation.viewmodel.RecViewModel

class FindOutfitActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFindOutfitBinding

    private lateinit var viewModel: RecViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindOutfitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(RecViewModel::class.java)

        val onBoardingPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                setNextButton(position)
            }
        }

        val fragmentList = arrayListOf(
            FirstQuestionFragment(),
            SecondQuestionFragment(),
            ThirdQuestionFragment(),
            FourthQuestionFragment()
        )

        val adapter = ViewPagerAdapter(
            fragmentList,
            supportFragmentManager,
            lifecycle
        )

        binding.apply {
            questionViewpager.adapter = adapter
            questionViewpager.registerOnPageChangeCallback(onBoardingPageChangeCallback)
            questionViewpager.isUserInputEnabled = false

            nextBtn.setOnClickListener {
                setProgressBar(true)
                binding.questionViewpager.currentItem += 1
            }

            backBtn.setOnClickListener {
                setProgressBar(false)
                binding.questionViewpager.currentItem -= 1
            }

            finishBtn.setOnClickListener {
                viewModel.saveDataToRepository()
                viewModel.footwearList.observe(this@FindOutfitActivity) { footwearList ->
                    viewModel.topwearList.observe(this@FindOutfitActivity) { topwearList ->
                        viewModel.bottomwearList.observe(this@FindOutfitActivity) { bottomwearList ->
                            // Create an intent object
                            val intent = Intent(this@FindOutfitActivity, RecommendationActivity::class.java)

                            // Create a bundle to hold the observations
                            val bundle = Bundle()

                            // Add the footwearList as an extra to the bundle
                            bundle.putStringArrayList("footwearList", ArrayList(footwearList))

                            // Add the topwearList as an extra to the bundle
                            bundle.putStringArrayList("topwearList", ArrayList(topwearList))

                            // Add the bottomwearList as an extra to the bundle
                            bundle.putStringArrayList("bottomwearList", ArrayList(bottomwearList))

                            // Add the bundle as an extra to the intent
                            intent.putExtra("observationBundle", bundle)

                            // Start the RecommendationActivity with the intent
                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }

    private fun setNextButton(position: Int) {
        when (position) {
            0 -> {
                binding.backBtn.visibility = View.INVISIBLE
                binding.finishBtn.visibility = View.GONE
                binding.nextBtn.visibility = View.VISIBLE
            }
            3 -> {
                binding.backBtn.visibility = View.VISIBLE
                binding.finishBtn.visibility = View.VISIBLE
                binding.nextBtn.visibility = View.GONE
            }
            else -> {
                binding.backBtn.visibility = View.VISIBLE
                binding.finishBtn.visibility = View.GONE
                binding.nextBtn.visibility = View.VISIBLE
            }
        }
    }

    private fun setProgressBar(add: Boolean) {
        binding.apply {
            if (add) {
                progressBar.progress = progressBar.progress + 1
            } else {
                progressBar.progress = progressBar.progress - 1
            }
            tvRemainingquestion.text = getString(R.string.number_of_questions, binding.progressBar.progress)
        }
    }

    override fun onBackPressed() {
        if (binding.questionViewpager.currentItem - 1 < 0) {
        } else {
            binding.questionViewpager.currentItem -= 1
            setProgressBar(false)
        }
    }

}