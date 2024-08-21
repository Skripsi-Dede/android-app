package com.example.gooutfit_android.ui.auth.viewmodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class AuthRequest {
    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("password")
    @Expose
    var password: String? = null
}

class AuthRegister{
    @SerializedName("email")
    @Expose
    var email: String? = null

    @SerializedName("password")
    @Expose
    var password: String? = null

    @SerializedName("username")
    @Expose
    var username: String? = null
}