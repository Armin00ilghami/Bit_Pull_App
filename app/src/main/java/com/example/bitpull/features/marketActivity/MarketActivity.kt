package com.example.bitpull.features.marketActivity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.example.bitpull.apiManager.ApiManager
import com.example.bitpull.apiManager.model.CoinAboutData
import com.example.bitpull.apiManager.model.CoinAboutItem
import com.example.bitpull.apiManager.model.CoinsData
import com.example.bitpull.databinding.ActivityMarketBinding
import com.example.bitpull.features.coinActivity.CoinActivity

class MarketActivity : AppCompatActivity(), MarketAdapter.RecyclerCallback {
    lateinit var binding: ActivityMarketBinding
    lateinit var adapter: MarketAdapter
    lateinit var dataNews: ArrayList<Pair<String, String>>
    lateinit var aboutDataMap: MutableMap<String, CoinAboutItem>
    val apiManager = ApiManager()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMarketBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.layoutToolbar.toolbar.title = "BitPull Market"

        firstRunShowDialogWelcome()

        binding.layoutWatchlist.btnShowMore.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.livecoinwatch.com/"))
            startActivity(intent)
        }
        binding.swipeRefreshMain.setOnRefreshListener {

            initUi()

            Handler(Looper.getMainLooper()).postDelayed({
                binding.swipeRefreshMain.isRefreshing = false
            }, 1500)

        }

        getAboutDataFromAssets()

    }
    override fun onResume() {
        super.onResume()

        initUi()

    }

    private fun initUi() {

        getNewsFromApi()
        getTopCoinsFromApi()

    }

    private fun getNewsFromApi() {

        apiManager.getNews(object : ApiManager.ApiCallback<ArrayList<Pair<String, String>>> {

            override fun onSuccess(data: ArrayList<Pair<String, String>>) {
                dataNews = data
                refreshNews()
            }

            override fun onError(errorMessage: String) {
                Toast.makeText(this@MarketActivity, "error => " + errorMessage, Toast.LENGTH_SHORT)
                    .show()
            }

        })

    }
    private fun refreshNews() {

        val randomAccess = (0..49).random()
        binding.layoutNews.txtNews.text = dataNews[randomAccess].first
        binding.layoutNews.imgNews.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(dataNews[randomAccess].second))
            startActivity(intent)
        }
        binding.layoutNews.txtNews.setOnClickListener {
            refreshNews()
        }

    }

    private fun cleanData( data: List<CoinsData.Data> ) : List<CoinsData.Data>{
        val newData = mutableListOf<CoinsData.Data>()
       // val newData = data.toMutableList()
        data.forEach {
            if ( it.rAW !=null || it.dISPLAY != null ){
                newData.add(it)
            }
        }
        return newData
    }

    private fun getTopCoinsFromApi() {

        apiManager.getCoinsList(object : ApiManager.ApiCallback<List<CoinsData.Data>> {
            override fun onSuccess(data: List<CoinsData.Data>) {

                showDataInRecycler(cleanData(data))

            }

            override fun onError(errorMessage: String) {
                Toast.makeText(this@MarketActivity, "error => " + errorMessage, Toast.LENGTH_SHORT)
                    .show()
                Log.v("testLog", errorMessage)
            }
        })

    }
    private fun showDataInRecycler(data: List<CoinsData.Data>) {

        adapter = MarketAdapter(ArrayList(data), this)
        binding.layoutWatchlist.recyclerMain.adapter = adapter
        binding.layoutWatchlist.recyclerMain.layoutManager = LinearLayoutManager(this)

    }
    override fun onCoinItemClicked(dataCoin: CoinsData.Data) {
        val intent = Intent(this, CoinActivity::class.java)

        val bundle = Bundle()
        bundle.putParcelable("bundle1", dataCoin)
        bundle.putParcelable("bundle2", aboutDataMap[dataCoin.coinInfo.name])

        intent.putExtra("bundle", bundle)
        startActivity(intent)
    }

    private fun getAboutDataFromAssets() {

        val fileInString = applicationContext.assets
            .open("currencyinfo.json")
            .bufferedReader()
            .use { it.readText() }

        aboutDataMap = mutableMapOf<String, CoinAboutItem>()

        val gson = Gson()
        val dataAboutAll = gson.fromJson(fileInString, CoinAboutData::class.java)

        dataAboutAll.forEach {
            aboutDataMap[it.currencyName] = CoinAboutItem(
                it.info.web,
                it.info.github,
                it.info.twt,
                it.info.desc,
                it.info.reddit
            )
        }

    }

    private fun firstRunShowDialogWelcome() {

        val shared = getSharedPreferences("mainSharedPref.xml", Context.MODE_PRIVATE)
        val isFirstRun = shared.getBoolean("isFirstRun", true)
        if (isFirstRun) {

            val dialog = AlertDialog.Builder(this)
            dialog.setMessage(" WELCOME TO MARKET :) ")
            dialog.show()
            shared.edit().putBoolean("isFirstRun" , false).apply()
        }

    }

}