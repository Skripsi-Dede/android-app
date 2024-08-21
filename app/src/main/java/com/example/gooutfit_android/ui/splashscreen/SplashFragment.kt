package com.example.gooutfit_android.ui.splashscreen

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import com.example.gooutfit_android.R
import com.example.gooutfit_android.ui.auth.login.LoginActivity
import com.example.gooutfit_android.ui.auth.viewmodel.AuthResponse
import com.example.gooutfit_android.ui.auth.viewmodel.AuthViewModel
import com.example.gooutfit_android.ui.dashboard.DashboardActivity
import com.example.gooutfit_android.ui.recommendation.RecommendationActivity
import com.example.gooutfit_android.ui.recommendation.viewmodel.RecViewModel
import com.google.android.play.integrity.internal.t
import com.google.firebase.auth.FirebaseAuth

class SplashFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private var authResponse = AuthResponse()
    private lateinit var authViewModel: AuthViewModel
    private lateinit var viewModel: RecViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        auth = FirebaseAuth.getInstance()
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)
        viewModel = ViewModelProvider(this).get(RecViewModel::class.java)

        val view = inflater.inflate(R.layout.fragment_splash, container, false)

        // Mendapatkan pengguna saat ini dari FirebaseAuth
        val currentUser = auth.currentUser
        val token = getTokenFromSharedPreferences(requireContext())

        if (currentUser != null && token != null) {
            // Pengguna sudah login, lanjutkan ke halaman utama
            Log.e("Splash", "User logged in: ${currentUser.email}")
            startActivity()
        } else {
            // Pengguna belum login, arahkan ke halaman login
            Log.e("Splash", "User not logged in")
            Handler().postDelayed({
                findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
            }, 3000)
        }

        return view
    }

    private fun getTokenFromSharedPreferences(context: Context): String? {
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPreferences.getString("id_token", null)
    }

    private fun startActivity(){
        viewModel.randomDataToRepository()
        viewModel.footwearList.observe(viewLifecycleOwner) { footwearList ->
            viewModel.topwearList.observe(viewLifecycleOwner) { topwearList ->
                viewModel.bottomwearList.observe(viewLifecycleOwner) { bottomwearList ->
                    // Create an intent object
                    val intent = Intent(activity, DashboardActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }

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
//        val intent = Intent(activity, DashboardActivity::class.java).also {
//            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        }
//        startActivity(intent)
    }
}