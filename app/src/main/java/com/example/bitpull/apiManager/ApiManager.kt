package com.example.bitpull.apiManager

import com.example.bitpull.apiManager.model.CoinsData
import com.example.bitpull.apiManager.model.NewsData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://min-api.cryptocompare.com/data/"
const val BASE_URL_IMAGE = "https://www.cryptocompare.com"
const val API_KEY =
    "authorization: Apikey 98ac91fe0448e6e73045b9ac770b319602951c730179f0dee37ed6d3c58b767f"
const val BASE_URL_TWITTER = "https://twitter.com/"

class ApiManager {
    private val apiService: ApiService

    init {
        val retrofit = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    fun getNews( apiCallback :ApiCallback<ArrayList<Pair<String, String>>> ) {
        apiService.getTopNews().enqueue(object : Callback<NewsData> {

            override fun onResponse(call: Call<NewsData>, response: Response<NewsData>) {
                val data = response.body()!!

                val dataToSend: ArrayList<Pair<String, String>> = arrayListOf()
                data.data.forEach {
                    dataToSend.add( Pair(it.title , it.url) )
                }

                apiCallback.onSuccess( dataToSend )
            }

            override fun onFailure(call: Call<NewsData>, t: Throwable) {
                apiCallback.onError( t.message!! )
            }


        })
    }

    fun getCoinsList( apiCallback :ApiCallback<List<CoinsData.Data>> ) {
        apiService.getTopCoins().enqueue( object :Callback<CoinsData> {
            override fun onResponse(call: Call<CoinsData>, response: Response<CoinsData>) {
                val data = response.body()!!
                apiCallback.onSuccess(data.data)
            }

            override fun onFailure(call: Call<CoinsData>, t: Throwable) {
                apiCallback.onError(t.message!!)
            }

        })
    }

    interface ApiCallback<T> {
        fun onSuccess(data: T)
        fun onError(errorMessage: String)
    }
}