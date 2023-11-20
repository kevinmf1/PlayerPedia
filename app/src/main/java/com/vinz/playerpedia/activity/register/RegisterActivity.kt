package com.vinz.playerpedia.activity.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.vinz.playerpedia.R
import com.vinz.playerpedia.activity.home.MainActivity
import com.vinz.data.domain.model.User
import com.vinz.playerpedia.databinding.ActivityRegisterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            if (validateForm()) {
                createAccount()
            }
        }
    }

    private fun validateForm(): Boolean {
        var countError = 0

        if (binding.etName.text.toString().isEmpty()) {
            binding.etName.error = getString(R.string.error_empty_name)
            countError++
        }

        if (binding.etEmail.text.toString().isEmpty()) {
            binding.etEmail.error = getString(R.string.error_empty_email)
            countError++
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString())
                .matches()
        ) {
            binding.etEmail.error = getString(R.string.error_email_not_valid)
            countError++
        }

        if (binding.etUsername.text.toString().isEmpty()) {
            binding.etUsername.error = getString(R.string.error_empty_username)
            countError++
        } else if (binding.etUsername.text.toString().length < 6) {
            binding.etUsername.error = getString(R.string.error_username_length)
            countError++
        } else if (binding.etUsername.text.toString().contains(" ")) {
            binding.etUsername.error = getString(R.string.error_username_space)
            countError++
        }

        if (binding.etPassword.text.toString().isEmpty()) {
            binding.etPassword.error = getString(R.string.error_empty_password)
            countError++
        }

        if (binding.etRetypePassword.text.toString().isEmpty()) {
            binding.etRetypePassword.error = getString(R.string.error_empty_confirmation_password)
            countError++
        } else if (binding.etPassword.text.toString() != binding.etRetypePassword.text.toString()) {
            binding.etRetypePassword.error = getString(R.string.error_password_not_same)
            countError++
        }

        return countError <= 0
    }

    private fun createAccount() {
        registerViewModel.getUserByEmail(binding.etEmail.text.toString()).observe(this) { user ->
            if (user != null) {
                binding.etEmail.error = getString(R.string.error_email_already_taken)
                binding.etEmail.requestFocus()
                Toast.makeText(
                    this,
                    getString(R.string.try_another_email),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(this, getString(R.string.successfully_register), Toast.LENGTH_SHORT).show()
                val name = binding.etName.text.toString()
                val email = binding.etEmail.text.toString()
                val username = binding.etUsername.text.toString()
                val password = binding.etPassword.text.toString()
                val phoneNumber = binding.etPhoneNumber.text.toString()

                val userCreate = User(
                    id = 0,
                    name = name,
                    username = username,
                    email = email,
                    phone = phoneNumber,
                    password = password
                )

                val sharedPreferences = getSharedPreferences("isUserLogin", MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putBoolean("isUserLogin", true)
                editor.apply()

                val accountData = getSharedPreferences("userAccount", MODE_PRIVATE)
                val accountEdit = accountData.edit()
                accountEdit.putString("email", email)
                accountEdit.apply()

                registerViewModel.insertUser(userCreate)
                startActivity(Intent(this, MainActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                })
            }
        }
    }
}