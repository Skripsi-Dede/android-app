package com.example.gooutfit_android.ui.auth.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPreferences = application.getSharedPreferences("login_pref", Context.MODE_PRIVATE)

    private val _idToken = MutableLiveData<String>()
    val idToken: LiveData<String> = _idToken
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _loginStatus = MutableLiveData<Boolean>()
    val loginStatus: LiveData<Boolean> get() = _loginStatus

    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    user?.getIdToken(true)?.addOnCompleteListener { tokenTask ->
                        if (tokenTask.isSuccessful) {
                            val idToken = tokenTask.result?.token
                            idToken?.let {
                                _idToken.value = it
                                saveTokenToSharedPreferences(it)
                                _loginStatus.value = true
                            }
                        } else {
                            Log.e("AuthViewModel", "getIdToken: ${tokenTask.exception?.message}")
                            _loginStatus.value = false
                        }
                    }
                } else {
                    Log.e("AuthViewModel", "signInWithEmail: ${task.exception?.message}")
                    _loginStatus.value = false
                }
            }
    }

    private fun saveTokenToSharedPreferences(token: String?) {
        // Inisialisasi Shared Preferences
        val editor = sharedPreferences.edit()

        // Menyimpan token ke Shared Preferences
        editor.putString("token", token)
        editor.apply()
    }
}