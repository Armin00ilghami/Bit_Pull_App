package com.example.bitpull.features

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bitpull.databinding.ActivityCoinBinding

class CoinActivity : AppCompatActivity() {
    lateinit var binding : ActivityCoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCoinBinding.inflate(layoutInflater)
        setContentView( binding.root )
    }
}