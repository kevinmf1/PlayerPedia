package com.vinz.playerpedia.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vinz.playerpedia.R
import com.vinz.playerpedia.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}