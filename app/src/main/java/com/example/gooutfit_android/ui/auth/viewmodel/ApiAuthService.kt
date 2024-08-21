package com.example.gooutfit_android.ui.auth.viewmodel

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiAuthService {
    @POST("accounts:signInWithPassword?key=AIzaSyABI1UlI8YrA1TzK1nUHfe2ALDb-axjPCk")
    fun login(
        @Body authRequest: AuthRequest
    ): Call<AuthResponse>

    @POST("accounts:signUp?key=AIzaSyABI1UlI8YrA1TzK1nUHfe2ALDb-axjPCk")
    fun register(
        @Body authRegister: AuthRegister
    ): Call<RegisterResponse>
}