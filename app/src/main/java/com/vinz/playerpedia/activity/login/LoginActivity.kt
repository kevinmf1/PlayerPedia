package com.vinz.playerpedia.activity.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.vinz.playerpedia.R
import com.vinz.playerpedia.activity.home.MainActivity
import com.vinz.playerpedia.activity.register.RegisterActivity
import com.vinz.playerpedia.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                Toast.makeText(this, getString(R.string.wrong_data), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validationInput(): Boolean {
        var countError = 0
        if (binding.etEmail.text.toString().isEmpty()) {
            binding.etEmail.error = getString(R.string.error_empty_email)
            countError++
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString())
                .matches()
        ) {
            binding.etEmail.error = getString(R.string.error_email_not_valid)
            countError++
        }

        if (binding.etPassword.text.toString().isEmpty()) {
            binding.etPassword.error = getString(R.string.error_empty_password)
            countError++
        }
        return countError <= 0
    }
}