package com.vinz.playerpedia.activity.addplayer

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.vinz.playerpedia.R
import com.vinz.playerpedia.activity.home.MainActivity
import com.vinz.playerpedia.core.di.PlayerViewModelFactory
import com.vinz.playerpedia.core.domain.model.Player
import com.vinz.playerpedia.core.utils.reduceFileImage
import com.vinz.playerpedia.core.utils.uriToFile
import com.vinz.playerpedia.databinding.ActivityAddBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddBinding
    private var currentImageUri: Uri? = null
    private lateinit var addViewModel: AddViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add)

        val factory = PlayerViewModelFactory.getInstance(this)
        addViewModel = ViewModelProvider(this, factory)[AddViewModel::class.java]

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.savePlayer.setOnClickListener {
            if(validationInput()) {
                addPlayerToDatabase()
            }
        }

        binding.playerImageEdit.setOnClickListener {
            startGallery()
        }

    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(this, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Permission request denied", Toast.LENGTH_LONG).show()
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
            binding.playerImageEdit.setText("Gambar berhasil dipilih")
            binding.playerImagePreview.setImageURI(uri)
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun addPlayerToDatabase() {
        val imageFile = currentImageUri?.let { uriToFile(it, this).reduceFileImage() }

        val player = imageFile?.let {
            Player(
                playerId = 0,
                playerName = binding.playerNameEdit.text.toString(),
                playerClub = binding.playerClubEdit.text.toString(),
                playerPosition = binding.playerPositionEdit.text.toString(),
                playerNationality = binding.playerNationallyEdit.text.toString(),
                playerDescription = binding.playerDescEdit.text.toString(),
                playerImage = it
            )
        }

        addViewModel.addPlayer(player!!)
        Toast.makeText(this@AddActivity, "Data pemain berhasil ditambahkan", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this@AddActivity, MainActivity::class.java)).apply {
            finishAffinity()
        }
    }

    private fun validationInput() : Boolean {
        var countError = 0

        if (binding.playerNameEdit.text.isNullOrEmpty()) {
            binding.playerNameEdit.error = "Nama tidak boleh kosong"
            countError++
        }
        if (binding.playerClubEdit.text.isNullOrEmpty()) {
            binding.playerClubEdit.error = "Club tidak boleh kosong"
            countError++
        }
        if (binding.playerPositionEdit.text.isNullOrEmpty()) {
            binding.playerPositionEdit.error = "Posisi tidak boleh kosong"
            countError++
        }
        if (binding.playerNationallyEdit.text.isNullOrEmpty()) {
            binding.playerNationallyEdit.error = "Kewarganegaraan tidak boleh kosong"
            countError++
        }
        if (binding.playerDescEdit.text.isNullOrEmpty()) {
            binding.playerDescEdit.error = "Deskripsi tidak boleh kosong"
            countError++
        }
        if (binding.playerImageEdit.text.isNullOrEmpty()) {
            binding.playerImageEdit.error = "Gambar tidak boleh kosong"
            countError++
        }

        return countError == 0
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}