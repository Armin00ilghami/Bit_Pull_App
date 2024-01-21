package com.example.bitpull.features.coinActivity

import com.robinhood.spark.SparkAdapter
import com.example.bitpull.apiManager.model.ChartData

class ChartAdapter(
    private val historicalData :List<ChartData.Data> ,
    private val baseLine :String ?) :SparkAdapter()  {

    override fun getCount(): Int {
        return historicalData.size
    }

    override fun getItem(index: Int): ChartData.Data {
        return historicalData[index]
    }

    override fun getY(index: Int): Float {
        return historicalData[index].close.toFloat()
    }

    // i wanna make candle chart-> after getY
    override fun getX(index: Int): Float {
        return historicalData[index].open.toFloat()
    }

    override fun hasBaseLine(): Boolean {
        return true
    }
    override fun getBaseLine(): Float {
        return baseLine?.toFloat()  ?: super.getBaseLine()
    }


}