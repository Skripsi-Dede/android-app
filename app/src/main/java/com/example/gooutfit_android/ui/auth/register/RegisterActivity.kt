package com.example.gooutfit_android.ui.auth.register

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.gooutfit_android.R
import com.example.gooutfit_android.databinding.ActivityRegisterBinding
import com.example.gooutfit_android.ui.auth.login.LoginActivity
import com.example.gooutfit_android.ui.auth.viewmodel.*
import com.example.gooutfit_android.ui.dashboard.DashboardActivity
import com.google.firebase.auth.FirebaseAuth
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@SuppressLint("CheckResult")
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //        Validation
        val usernameStream = RxTextView.textChanges(binding.etUsername)
            .skipInitialValue()
            .map { name ->
                name.isEmpty()
            }
        usernameStream.subscribe {
            showNameExistAlert(it)
        }

        val emailStream = RxTextView.textChanges(binding.etEmail)
            .skipInitialValue()
            .map { email ->
                !Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        emailStream.subscribe {
            showEmailValidAlert(it)
        }

        val passwordStream = RxTextView.textChanges(binding.etPassword)
            .skipInitialValue()
            .map { password ->
               password.length < 8
            }
        passwordStream.subscribe {
            showPasswordMinimalAlert(it, "Password")
        }

        //        Button Enable True or False
        val invalidFieldsStream = Observable.combineLatest(
            usernameStream,
            emailStream,
            passwordStream,
            {
                usernameInvalid: Boolean, emailInvalid: Boolean, passwordInvalid: Boolean ->
                    !usernameInvalid && !emailInvalid && !passwordInvalid
            })
        invalidFieldsStream.subscribe { isValid ->
            if(isValid) {
                binding.btnRegister.isEnabled = true
                binding.btnRegister.backgroundTintList = ContextCompat.getColorStateList(this, R.color.custom_primary_1)
            } else {
                binding.btnRegister.isEnabled = false
                binding.btnRegister.backgroundTintList = ContextCompat.getColorStateList(this, R.color.custom_stroke)
            }
        }

        //        Auth
        auth = FirebaseAuth.getInstance()

        //        Button Click
        binding.tvHaveAccount.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        binding.btnRegister.setOnClickListener {
            register()
            Log.e("btn", "btn")
//            val username = binding.etUsername.text.toString().trim()
//            val email = binding.etEmail.text.toString().trim()
//            val password = binding.etPassword.text.toString().trim()
//            registerUser(email, password)
        }
    }

    private fun showNameExistAlert(isNotValid: Boolean){
        binding.etUsername.error = if (isNotValid) "Enter your username" else null
    }

    private fun showPasswordMinimalAlert(isNotValid: Boolean, text: String){
        binding.etPassword.error = if(isNotValid) "Password must be at least 8 character" else null
    }

    private fun showEmailValidAlert(isNotValid: Boolean){
        binding.etEmail.error = if (isNotValid) "Email is not valid" else null
    }

    private fun registerUser(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    startActivity(Intent(this, LoginActivity::class.java))
                    Toast.makeText(this, "Register Success", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun register(){
        val authRequest = AuthRegister()
        val uEmail = binding.etEmail.text.toString().trim()
        val uPassword = binding.etPassword.text.toString().trim()
        val uUsername = binding.etUsername.text.toString().trim()
        authRequest.email = uEmail
        authRequest.password = uPassword
        authRequest.username = uUsername

        val client = ApiAuthConfig.getApiService()
        client.register(authRequest).enqueue(object : Callback<RegisterResponse> {
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                t.message?.let { Log.e("Error", it) }
            }

            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                val user = response.body()
                user?.message?.let { Log.e("token", it) }
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                startActivity(intent)
                Toast.makeText(this@RegisterActivity, "Register Success", Toast.LENGTH_SHORT).show()
            }
        })
    }
}