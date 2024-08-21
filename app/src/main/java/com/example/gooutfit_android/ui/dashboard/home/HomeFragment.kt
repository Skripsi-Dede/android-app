package com.example.gooutfit_android.ui.dashboard.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gooutfit_android.R
import com.example.gooutfit_android.databinding.FragmentFirstQuestionBinding
import com.example.gooutfit_android.databinding.FragmentHomeBinding
import com.example.gooutfit_android.ui.recommendation.FindOutfitActivity
import com.example.gooutfit_android.ui.recommendation.RecycleViewBottomAdapter
import com.example.gooutfit_android.ui.recommendation.RecycleViewFootAdapter
import com.example.gooutfit_android.ui.recommendation.RecycleViewTopAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.btnFindOutfit.setOnClickListener {
            val intent = Intent(activity, FindOutfitActivity::class.java)
            startActivity(intent)
        }

        val topLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val bottomLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val footLayoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        val footwearList = arguments?.getStringArrayList("footwearList")
        Log.d("Home", "$footwearList")
        val topwearList = arguments?.getStringArrayList("topwearList")
        val bottomwearList = arguments?.getStringArrayList("bottomwearList")

        val topAdapter = topwearList?.let { RecycleViewTopAdapter(it) }
        binding.rvTopsRecommendation.adapter = topAdapter
        binding.rvTopsRecommendation.layoutManager = topLayoutManager

        val bottomAdapter = bottomwearList?.let { RecycleViewBottomAdapter(it) }
        binding.rvBottomsRecommendation.adapter = bottomAdapter
        binding.rvBottomsRecommendation.layoutManager = bottomLayoutManager

        val footAdapter = footwearList?.let { RecycleViewFootAdapter(it) }
        binding.rvFootwearRecommendation.adapter = footAdapter
        binding.rvFootwearRecommendation.layoutManager = footLayoutManager

        return binding.root
    }

}