package com.example.gooutfit_android.ui.recommendation.questionere

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.gooutfit_android.databinding.FragmentFourthQuestionBinding
import com.example.gooutfit_android.ui.recommendation.viewmodel.RecViewModel

class FourthQuestionFragment : Fragment() {

    private var _binding: FragmentFourthQuestionBinding? = null
    private val binding get() = _binding!!

    private lateinit var recViewModel: RecViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFourthQuestionBinding.inflate(inflater, container, false)

        recViewModel = ViewModelProvider(requireActivity()).get(RecViewModel::class.java)

        binding.radioGroup.setOnCheckedChangeListener { _, _ ->
            setChoice()
        }

        return binding.root
    }

    private fun setChoice() {
        binding.apply {
            when (radioGroup.checkedRadioButtonId) {
                item1.id -> {
                    recViewModel.setFashionStyle("casual")
                }
                item2.id -> {
                    recViewModel.setFashionStyle("streetstyle")
                }
                item3.id -> {
                    recViewModel.setFashionStyle("young")
                }
                item4.id -> {
                    recViewModel.setFashionStyle("formal")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

}