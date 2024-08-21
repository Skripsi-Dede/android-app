package com.example.gooutfit_android.ui.recommendation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.gooutfit_android.ui.auth.viewmodel.ApiAuthConfig
import com.example.gooutfit_android.ui.auth.viewmodel.AuthResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class RecViewModel(application: Application) : AndroidViewModel(application)  {

    private val _genderLiveData = MutableLiveData<String>()
    val genderLiveData: LiveData<String> = _genderLiveData

    private val _weatherLiveData = MutableLiveData<String>()
    val weatherLiveData: LiveData<String> = _weatherLiveData

    private val _situationLiveData = MutableLiveData<String>()
    val situationLiveData: LiveData<String> = _situationLiveData

    private val _fashionStyleLiveData = MutableLiveData<String>()
    val fashionStyleLiveData: LiveData<String> = _fashionStyleLiveData

    private val _footwearList: MutableLiveData<List<String?>> = MutableLiveData()
    val footwearList: LiveData<List<String?>> = _footwearList

    private val _topwearList: MutableLiveData<List<String?>> = MutableLiveData()
    val topwearList: LiveData<List<String?>> = _topwearList

    private val _bottomwearList: MutableLiveData<List<String?>> = MutableLiveData()
    val bottomwearList: LiveData<List<String?>> = _bottomwearList

    fun setGender(value: String) {
        _genderLiveData.value = value
    }

    fun setWeather(value: String) {
        _weatherLiveData.value = value
    }

    fun setSituation(value: String) {
        _situationLiveData.value = value
    }

    fun setFashionStyle(value: String) {
        _fashionStyleLiveData.value = value
    }

    fun randomDataToRepository() {
        val randomRequest = RecRequest().apply {
            gender = "male"
            weather = "cold"
            situation = "formal"
            fashion_style = "formal"
        }

        Log.e("SendDataRandom", randomRequest.toString().trim())
        sendData(randomRequest)
    }

    fun saveDataToRepository() {
        val recRequest = RecRequest().apply {
            gender = genderLiveData.value
            weather = weatherLiveData.value
            situation = situationLiveData.value
            fashion_style = fashionStyleLiveData.value
        }

        Log.e("SendData", recRequest.toString().trim())
        sendData(recRequest)
    }

    private fun sendData(recRequest: RecRequest) {
        val client = ApiRecConfig.getApiRecService()
        client.recommend(recRequest).enqueue(object : Callback<RecResponse> {
            override fun onFailure(call: Call<RecResponse>, t: Throwable) {
                Log.e("RecViewModel", "API call failed: ${t.message.toString()}")
            }

            override fun onResponse(call: Call<RecResponse>, response: Response<RecResponse>) {
                if (response.isSuccessful) {
                    val recResponse = response.body()
                    if (recResponse != null) {
                        // The API call was successful and the response data is available
                        Log.d("RecViewModel", "Response: $recResponse")
                        parseData(recResponse)
                    } else {
                        Log.e("RecViewModel", "Response body is null")
                    }
                } else {
                    Log.e("RecViewModel", "API call failed with status code ${response.code()}")
                }
            }
        })
    }

    private fun parseData(recResponse: RecResponse) {
        val footwearList = mutableListOf<String>()
        recResponse.footwear?.forEach { footwearGroup ->
            footwearGroup?.forEach { footwearUrl ->
                footwearUrl?.let { footwearList.add(it) }
            }
        }

        val topwearList = mutableListOf<String>()
        recResponse.upperwear?.forEach { topwearGroup ->
            topwearGroup?.forEach { topwearUrl ->
                topwearUrl?.let { topwearList.add(it) }
            }
        }

        val bottomwearList = mutableListOf<String>()
        recResponse.bottomwear?.forEach { bottomwearGroup ->
            bottomwearGroup?.forEach { bottomwearUrl ->
                bottomwearUrl?.let { bottomwearList.add(it) }
            }
        }

        _footwearList.value = footwearList
        Log.d("RecViewModel", "Footwear List: $footwearList")
        _topwearList.value = topwearList
        Log.d("RecViewModel", "Topwear List: $topwearList")
        _bottomwearList.value = bottomwearList
        Log.d("RecViewModel", "Bottomwear List: $bottomwearList")
    }
}
