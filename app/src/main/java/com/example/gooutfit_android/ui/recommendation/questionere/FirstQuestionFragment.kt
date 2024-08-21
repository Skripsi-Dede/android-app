package com.example.gooutfit_android.ui.recommendation.questionere

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.gooutfit_android.databinding.FragmentFirstQuestionBinding
import com.example.gooutfit_android.ui.recommendation.viewmodel.RecViewModel

class FirstQuestionFragment : Fragment() {

    private var _binding: FragmentFirstQuestionBinding? = null
    private val binding get() = _binding!!

    private lateinit var recViewModel: RecViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstQuestionBinding.inflate(inflater, container, false)

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
                    recViewModel.setGender("male")
                }
                item2.id -> {
                    recViewModel.setGender("female")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.root.requestLayout()
    }

}