package com.vinz.playerpedia.activity.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.vinz.playerpedia.R
import com.vinz.playerpedia.activity.register.RegisterActivity
import com.vinz.playerpedia.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onClick()
    }

    private fun onClick() {
        binding.btnLogin.setOnClickListener {
            if (validationInput()) {

            }
        }
        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validationAccount(email: String, password: String) : Boolean {

    }

    private fun validationInput() : Boolean {
        var countError = 0
        if (binding.etEmail.text.toString().isEmpty()) {
            binding.etEmail.error = "Email tidak boleh kosong"
            countError++
        }
        if (binding.etPassword.text.toString().isEmpty()) {
            binding.etPassword.error = "Password tidak boleh kosong"
            countError++
        }
        return countError <= 0
    }
}