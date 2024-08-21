package com.example.gooutfit_android.ui.onboarding.screens

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.gooutfit_android.R
import com.example.gooutfit_android.ui.auth.login.LoginActivity

class ThirdScreen : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_third_screen, container, false)

        val finish = view.findViewById<Button>(R.id.finish)

        finish.setOnClickListener{
            startLoginActivity()
            onBoardingisFinished()
        }

        return view
    }

    private fun startLoginActivity(){
        val intent = Intent(activity, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun onBoardingisFinished(){
        val sharedPreferences = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("finished", false)
        editor.apply()
    }

}