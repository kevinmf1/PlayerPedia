package com.vinz.playerpedia.activity.register

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vinz.playerpedia.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

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
        return if (binding.etName.text.toString().isEmpty()) {
            binding.etName.error = "Nama tidak boleh kosong"
            false
        } else if (binding.etEmail.text.toString().isEmpty()) {
            binding.etEmail.error = "Email tidak boleh kosong"
            false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.text.toString()).matches()) {
            binding.etEmail.error = "Email tidak valid"
            false
        } else if (binding.etUsername.text.toString().isEmpty()) {
            binding.etUsername.error = "Username tidak boleh kosong"
            false
        } else if (binding.etUsername.text.toString().length < 6) {
            binding.etUsername.error = "Username minimal 6 karakter"
            false
        } else if (binding.etUsername.text.toString().contains(" ")) {
            binding.etUsername.error = "Username tidak boleh mengandung spasi"
            false
        } else if (binding.etPassword.text.toString().isEmpty()) {
            binding.etPassword.error = "Password tidak boleh kosong"
            false
        } else if (binding.etRetypePassword.text.toString().isEmpty()) {
            binding.etRetypePassword.error = "Konfirmasi password tidak boleh kosong"
            false
        } else if (binding.etPassword.text.toString() != binding.etRetypePassword.text.toString()) {
            binding.etRetypePassword.error = "Password tidak sama"
            false
        } else {
            true
        }
    }

    private fun createAccount() {

    }
}