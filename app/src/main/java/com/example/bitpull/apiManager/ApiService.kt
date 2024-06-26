package com.example.bitpull.apiManager

import com.example.bitpull.apiManager.model.ChartData
import com.example.bitpull.apiManager.model.CoinsData
import com.example.bitpull.apiManager.model.NewsData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @Headers(API_KEY)
    @GET("v2/news/")
    fun getTopNews(
        @Query("sortOrder") sortOrder: String = "popular"
    ) :Call<NewsData>

    @Headers(API_KEY)
    @GET("top/totalvolfull")
    fun getTopCoins(
        @Query("tsym") to_symbol :String = "USD" ,
        @Query("limit") limit_data :Int = 20
    ) :Call<CoinsData>

    @Headers(API_KEY)
    @GET("{period}")
    fun getChartData(
        @Path("period") period: String ,
        @Query("fsym") fromSymbol :String ,
        @Query("limit") llimit:Int ,
        @Query("aggregate") aggregate:Int   ,
        @Query("tsym") toSymbol :String = "USD"
        ) :Call<ChartData>
}