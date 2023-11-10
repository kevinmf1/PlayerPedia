package com.vinz.playerpedia.activity.user

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.vinz.playerpedia.core.data.source.local.datastore.NightModePreferences
import com.vinz.playerpedia.core.data.source.local.datastore.NightModeViewModel
import com.vinz.playerpedia.core.data.source.local.datastore.NightModeViewModelFactory
import com.vinz.playerpedia.core.data.source.local.datastore.dataStore
import com.vinz.playerpedia.core.di.UserViewModelFactory
import com.vinz.playerpedia.core.domain.model.User
import com.vinz.playerpedia.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var nightModeViewModel: NightModeViewModel
    private lateinit var binding: ActivityProfileBinding
    private var userId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        val pref = NightModePreferences.getInstance(application.dataStore)
        nightModeViewModel = ViewModelProvider(this, NightModeViewModelFactory(pref))[NightModeViewModel::class.java]
        nightModeViewModel.getNightModeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.nightModeSwitch.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.nightModeSwitch.isChecked = false
            }
        }

        onClick()
    }

    private fun onClick() {
        binding.editProfile.setOnClickListener {
            isEnabledInput(true)
            visibilityButton(true, true, false)
        }

        binding.cancelProfile.setOnClickListener {
            isEnabledInput(false)
            visibilityButton(false, false, true)
        }

        binding.nightModeSwitch.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            nightModeViewModel.saveNightModeSettings(isChecked)
        }

        binding.changeLanguage.setOnClickListener {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
        }

        binding.backHome.setOnClickListener {
            onBackPressed()
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