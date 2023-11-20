package com.vinz.playerpedia.activity.user

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.vinz.data.data.source.local.datastore.NightModeViewModel
import com.vinz.data.domain.model.User
import com.vinz.data.utils.reduceFileImage
import com.vinz.data.utils.uriToFile
import com.vinz.playerpedia.R
import com.vinz.playerpedia.activity.blur.BlurActivity
import com.vinz.playerpedia.databinding.ActivityProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.io.FileOutputStream

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {

    private val profileViewModel: ProfileViewModel by viewModels()
    private val nightModeViewModel: NightModeViewModel by viewModels()
    private lateinit var binding: ActivityProfileBinding
    private var userId = 0
    private var currentImageUri: Uri? = null
    private var oldPhoto: File? = null
    private var isEdit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        isEnabledInput(false)

        val emailPreferences = getSharedPreferences("userAccount", MODE_PRIVATE)
        val email = emailPreferences.getString("email", "")

        profileViewModel.getUserByEmail(email!!).observe(this) { user ->
            if (user != null) {
                userId = user.id
                binding.userNameEdit.setText(user.name)
                binding.usernameUserEdit.setText(user.username)
                binding.emailUserEdit.setText(user.email)
                binding.passwordUserEdit.setText(user.password)
                binding.phoneUserEdit.setText(user.phone)
                Glide.with(this)
                    .load(user.image)
                    .error(R.drawable.dummy_photo)
                    .into(binding.profileImage)

                oldPhoto = if (user.image != null) {
                    user.image
                } else {
                    drawableToFile(R.drawable.dummy_photo, "dummy_photo.png")
                }
            }
        }

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

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

    private fun drawableToFile(resourceId: Int, fileName: String): File? {
        val bitmap = BitmapFactory.decodeResource(resources, resourceId)
        val file = File(externalCacheDir, fileName)
        val outStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream)
        outStream.flush()
        outStream.close()
        return file
    }

    private fun onClick() {
        binding.editProfile.setOnClickListener {
            isEdit = true
            isEnabledInput(true)
            visibilityButton(true)
        }

        binding.blurredImage.setOnClickListener {
            val intent = Intent(this, BlurActivity::class.java)
            startActivity(intent)
        }

        binding.editProfileImage.setOnClickListener {
            if (isEdit) {
                startGallery()
            }
        }

        binding.profileImage.setOnClickListener {
            if (isEdit) {
                startGallery()
            }
        }

        binding.cancelProfile.setOnClickListener {
            isEdit = false
            isEnabledInput(false)
            visibilityButton(false)
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
            val imageFile = currentImageUri?.let { uriToFile(it, this).reduceFileImage() }

            val player = (if (currentImageUri != null) imageFile else oldPhoto)?.let {
                User(
                    id = userId,
                    name = binding.userNameEdit.text.toString(),
                    username = binding.usernameUserEdit.text.toString(),
                    email = binding.emailUserEdit.text.toString(),
                    phone = binding.phoneUserEdit.text.toString(),
                    password = binding.passwordUserEdit.text.toString(),
                    image = it
                )
            }

            if (player != null) {
                profileViewModel.updateUser(player)
            }

            savedWithSharedPreferences()
            isEdit = false

            isEnabledInput(false)
            visibilityButton(false)
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(
                    this,
                    getString(R.string.request_permission_accept), Toast.LENGTH_LONG
                ).show()
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.request_permission_reject), Toast.LENGTH_LONG
                ).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            this,
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            Glide.with(this)
                .load(uri)
                .error(R.drawable.ic_author)
                .into(binding.profileImage)
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun savedWithSharedPreferences() {
        val email = binding.emailUserEdit.text.toString()

        val accountData = getSharedPreferences("userAccount", MODE_PRIVATE)
        val accountEdit = accountData.edit()
        accountEdit.putString("email", email)
        accountEdit.apply()
    }

    private fun visibilityButton(visibility: Boolean) {
        binding.cancelProfile.visibility = if (visibility) View.VISIBLE else View.GONE
        binding.saveProfile.visibility = if (visibility) View.VISIBLE else View.GONE
        binding.editProfile.visibility = if (visibility) View.GONE else View.VISIBLE
        binding.editProfileImage.visibility = if (visibility) View.VISIBLE else View.GONE
    }

    private fun isEnabledInput(enable: Boolean) {
        binding.userNameEdit.isEnabled = enable
        binding.usernameUserEdit.isEnabled = enable
        binding.emailUserEdit.isEnabled = enable
        binding.passwordUserEdit.isEnabled = enable
        binding.phoneUserEdit.isEnabled = enable
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}