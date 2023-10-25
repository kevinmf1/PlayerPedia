package com.vinz.playerpedia.activity.user

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.vinz.playerpedia.R
import com.vinz.playerpedia.core.di.UserViewModelFactory
import com.vinz.playerpedia.core.domain.model.User
import com.vinz.playerpedia.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var binding: ActivityProfileBinding
    private var userId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        isEnabledInput(false)

        val emailPreferences = getSharedPreferences("userAccount", MODE_PRIVATE)
        val email = emailPreferences.getString("email", "")
        Log.d("email", email.toString())
        val factory = UserViewModelFactory.getInstance(this)
        profileViewModel = ViewModelProvider(this, factory)[ProfileViewModel::class.java]

        profileViewModel.getUserByEmail(email!!).observe(this) { user ->
            if (user != null) {
                userId = user.id
                binding.userNameEdit.setText(user.name)
                binding.usernameUserEdit.setText(user.username)
                binding.emailUserEdit.setText(user.email)
                binding.passwordUserEdit.setText(user.password)
                binding.phoneUserEdit.setText(user.phone)
            }
        }

        binding.editProfile.setOnClickListener {
            isEnabledInput(true)
            visibilityButton(true, true, false)
        }

        binding.cancelProfile.setOnClickListener {
            isEnabledInput(false)
            visibilityButton(false, false, true)
        }

        binding.saveProfile.setOnClickListener {
            savedToDatabase(
                User(
                    id = userId,
                    name = binding.userNameEdit.text.toString(),
                    username = binding.usernameUserEdit.text.toString(),
                    email = binding.emailUserEdit.text.toString(),
                    phone = binding.phoneUserEdit.text.toString(),
                    password = binding.passwordUserEdit.text.toString()
                )
            )

            savedWithSharedPreferences()

            isEnabledInput(false)
            visibilityButton(false, false, true)
        }

        binding.backHome.setOnClickListener {
            onBackPressed()
        }
    }

    private fun savedToDatabase(user: User) {
        profileViewModel.updateUser(user)
    }

    private fun savedWithSharedPreferences() {
        val email = binding.emailUserEdit.text.toString()

        val accountData = getSharedPreferences("userAccount", MODE_PRIVATE)
        val accountEdit = accountData.edit()
        accountEdit.putString("email", email)
        accountEdit.apply()
    }

    private fun visibilityButton(btnCancel: Boolean, btnChecklist: Boolean, btnEdit: Boolean) {
        binding.cancelProfile.visibility = if (btnCancel) View.VISIBLE else View.GONE
        binding.saveProfile.visibility = if (btnChecklist) View.VISIBLE else View.GONE
        binding.editProfile.visibility = if (btnEdit) View.VISIBLE else View.GONE
    }

    private fun isEnabledInput(enable: Boolean) {
        binding.userNameEdit.isEnabled = enable
        binding.usernameUserEdit.isEnabled = enable
        binding.emailUserEdit.isEnabled = enable
        binding.passwordUserEdit.isEnabled = enable
        binding.phoneUserEdit.isEnabled = enable
    }
}