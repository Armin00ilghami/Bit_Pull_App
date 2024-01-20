package com.example.bitpull.features

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bitpull.apiManager.BASE_URL_TWITTER
import com.example.bitpull.apiManager.model.CoinAboutItem
import com.example.bitpull.apiManager.model.CoinsData
import com.example.bitpull.databinding.ActivityCoinBinding

class CoinActivity : AppCompatActivity() {
    lateinit var binding : ActivityCoinBinding
    lateinit var dataThisCoin:CoinsData.Data
    lateinit var dataThisCoinAbout : CoinAboutItem

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinBinding.inflate(layoutInflater)
        setContentView( binding.root )

        val fromIntent =  intent.getBundleExtra( "bundle" )!!
        dataThisCoin = fromIntent.getParcelable<CoinsData.Data>("bundle1")!!
        dataThisCoinAbout = fromIntent.getParcelable<CoinAboutItem>("bundle2")!!

        binding.layoutToolbar.toolbar.title = dataThisCoin.coinInfo.fullName
        initUi()

    }

    private fun initUi() {
        initChartUi()
        initStatisticsUi()
        initAboutUi()
    }

    private fun initAboutUi() {
        binding.layoutAbout.txtWebsite.text = dataThisCoinAbout.coinWebsite
        binding.layoutAbout.txtGithub.text = dataThisCoinAbout.coinGithub
        binding.layoutAbout.txtReddit.text = dataThisCoinAbout.coinReddit
        binding.layoutAbout.txtTwitter.text ="@" + dataThisCoinAbout.coinTwitter
        binding.layoutAbout.txtAboutCoin.text = dataThisCoinAbout.coinDesc

        binding.layoutAbout.txtWebsite.setOnClickListener {
            openWebsiteDataCoin( dataThisCoinAbout.coinWebsite!!)
        }
        binding.layoutAbout.txtGithub.setOnClickListener {
            openWebsiteDataCoin(  dataThisCoinAbout.coinGithub!!)
        }
        binding.layoutAbout.txtReddit.setOnClickListener {
             openWebsiteDataCoin(dataThisCoinAbout.coinReddit !!)
        }
        binding.layoutAbout.txtTwitter.setOnClickListener {
             openWebsiteDataCoin( BASE_URL_TWITTER + dataThisCoinAbout.coinTwitter!!)
        }

    }

    private fun openWebsiteDataCoin(url : String){
        val intent = Intent(Intent.ACTION_VIEW , Uri.parse(url))
        startActivity(intent)

    }

    private fun initStatisticsUi() {
        binding.layoutStatistics.tvOpenAmount.text = dataThisCoin.dISPLAY.uSD.oPEN24HOUR
        binding.layoutStatistics.tvTodaysHighAmount.text =  dataThisCoin.dISPLAY.uSD.hIGH24HOUR
        binding.layoutStatistics.tvTodayLowAmount.text =  dataThisCoin.dISPLAY.uSD.lOW24HOUR
        binding.layoutStatistics.tvAvgMarketCapAmount.text = dataThisCoin.dISPLAY.uSD.cHANGE24HOUR
        binding.layoutStatistics.tvAlgorithm.text = dataThisCoin.dISPLAY.uSD.vOLUME24HOUR
        binding.layoutStatistics.tvAvgVolumeLabel.text = dataThisCoin.dISPLAY.uSD.tOTALVOLUME24H
        binding.layoutStatistics.tvAvgMarketCapAmount.text = dataThisCoin.dISPLAY.uSD.mKTCAP
        binding.layoutStatistics.tvSupplyNumber.text = dataThisCoin.dISPLAY.uSD.sUPPLY

    }

    private fun initChartUi() {

    }
}