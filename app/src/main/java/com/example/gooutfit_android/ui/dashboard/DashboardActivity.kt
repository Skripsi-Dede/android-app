package com.example.gooutfit_android.ui.dashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.gooutfit_android.R
import com.example.gooutfit_android.databinding.ActivityDashboardBinding
import com.example.gooutfit_android.ui.dashboard.history.HistoryFragment
import com.example.gooutfit_android.ui.dashboard.home.HomeFragment
import com.example.gooutfit_android.ui.dashboard.setting.SettingFragment
import com.google.firebase.auth.FirebaseAuth

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startHomeFragment()

        binding.bottomNavView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> startHomeFragment()
                R.id.history -> adapterFragment(HistoryFragment())
                R.id.settings -> adapterFragment(SettingFragment())
            }
            true
        }

    }

    private fun adapterFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    private fun startHomeFragment(){
        val observationBundle = intent.getBundleExtra("observationBundle")
        val topwearList = observationBundle?.getStringArrayList("topwearList")
        Log.d("Dashboard", "$topwearList")
        val bottomwearList = observationBundle?.getStringArrayList("bottomwearList")
        Log.d("Dashboard", "$bottomwearList")
        val footwearList = observationBundle?.getStringArrayList("footwearList")
        Log.d("Dashboard", "$footwearList")
        val bundle = Bundle().apply {
            putStringArrayList("topwearList", topwearList)
            putStringArrayList("bottomwearList", bottomwearList)
            putStringArrayList("footwearList", footwearList)
        }
        val fragment = HomeFragment()
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .commit()
    }

}