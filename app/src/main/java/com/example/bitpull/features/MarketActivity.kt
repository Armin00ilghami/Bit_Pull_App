package com.example.bitpull.features

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bitpull.databinding.ActivityMarketBinding


class MarketActivity : AppCompatActivity() {
    lateinit var binding: ActivityMarketBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMarketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.layoutToolbar.toolbar.title = "Market"


    }
}