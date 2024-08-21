package com.example.gooutfit_android.ui.recommendation.viewmodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class RecRequest {
    @SerializedName("gender")
    @Expose
    var gender: String? = null

    @SerializedName("weather")
    @Expose
    var weather: String? = null

    @SerializedName("situation")
    @Expose
    var situation: String? = null

    @SerializedName("fashion_style")
    @Expose
    var fashion_style: String? = null
}