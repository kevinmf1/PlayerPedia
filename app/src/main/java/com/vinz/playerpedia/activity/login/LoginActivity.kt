package com.vinz.playerpedia.activity.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.vinz.playerpedia.R
import com.vinz.playerpedia.activity.home.HomeViewModel
import com.vinz.playerpedia.activity.home.MainActivity
import com.vinz.playerpedia.activity.register.RegisterActivity
import com.vinz.playerpedia.core.di.UserViewModelFactory
import com.vinz.playerpedia.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        val sharedPreferences = getSharedPreferences("isUserLogin", MODE_PRIVATE)
        val isUserLogin = sharedPreferences.getBoolean("isUserLogin", false)
        if (isUserLogin) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }

        onClick()
    }

    private fun onClick() {
        binding.btnLogin.setOnClickListener {
            if (validationInput()) {
                validationAccount(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString()
                )
            }
        }
        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validationAccount(email: String, password: String) {
        val factory = UserViewModelFactory.getInstance(this)
        loginViewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]

        loginViewModel.getUserByEmailAndPassword(email, password).observe(this) { user ->
            if (user != null) {
                val sharedPreferences = getSharedPreferences("isUserLogin", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putBoolean("isUserLogin", true)
                editor.apply()

                val accountData = getSharedPreferences("userAccount", MODE_PRIVATE)
                val accountEdit = accountData.edit()
                accountEdit.putString("email", email)
                accountEdit.apply()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finishAffinity()
            } else {
                Toast.makeText(this, "Email atau password salah", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validationInput(): Boolean {
        var countError = 0
        if (binding.etEmail.text.toString().isEmpty()) {
            binding.etEmail.error = "Email tidak boleh kosong"
            countError++
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString())
                .matches()
        ) {
            binding.etEmail.error = "Email tidak valid"
            countError++
        }

        if (binding.etPassword.text.toString().isEmpty()) {
            binding.etPassword.error = "Password tidak boleh kosong"
            countError++
        }
        return countError <= 0
    }
}