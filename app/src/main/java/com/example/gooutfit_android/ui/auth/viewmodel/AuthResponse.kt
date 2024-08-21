package com.example.gooutfit_android.ui.auth.viewmodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AuthResponse {
    @SerializedName("data")
    @Expose
    var data: User? = null

    class User{
        @SerializedName("email")
        @Expose
        var email: String? = null

        @SerializedName("idToken")
        @Expose
        var idToken: String? = null
    }
}

class RegisterResponse {
    @SerializedName("data")
    @Expose
    var message: String? = null
}