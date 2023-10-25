package com.vinz.playerpedia.activity.edit

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
import com.bumptech.glide.Glide
import com.vinz.playerpedia.R
import com.vinz.playerpedia.activity.home.MainActivity
import com.vinz.playerpedia.core.di.PlayerViewModelFactory
import com.vinz.playerpedia.core.domain.model.Player
import com.vinz.playerpedia.core.utils.reduceFileImage
import com.vinz.playerpedia.core.utils.uriToFile
import com.vinz.playerpedia.databinding.ActivityEditBinding
import java.io.File

class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding
    private lateinit var editViewModel: EditViewModel
    private var currentImageUri: Uri? = null
    private var oldPhoto: File? = null
    private var playerId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit)

        val player = intent.getParcelableExtra<Player>("player") as Player
        playerId = player.playerId

        Glide.with(this)
            .load(player.playerImage)
            .into(binding.playerImagePreview)

        oldPhoto = player.playerImage
        binding.playerImageEdit.setText("Gambar berhasil dipilih")

        binding.playerNameEdit.setText(player.playerName)
        binding.playerClubEdit.setText(player.playerClub)
        binding.playerPositionEdit.setText(player.playerPosition)
        binding.playerNationallyEdit.setText(player.playerNationality)
        binding.playerDescEdit.setText(player.playerDescription)

        val factory = PlayerViewModelFactory.getInstance(this)
        editViewModel = ViewModelProvider(this, factory)[EditViewModel::class.java]

        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.savePlayer.setOnClickListener {
            if (validationInput()) {
                updatePlayerToDatabase()
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

    private fun updatePlayerToDatabase() {
        val imageFile = currentImageUri?.let { uriToFile(it, this).reduceFileImage() }

        val player = (if (currentImageUri != null) imageFile else oldPhoto)?.let {
            Player(
                playerId = playerId,
                playerName = binding.playerNameEdit.text.toString(),
                playerClub = binding.playerClubEdit.text.toString(),
                playerPosition = binding.playerPositionEdit.text.toString(),
                playerNationality = binding.playerNationallyEdit.text.toString(),
                playerDescription = binding.playerDescEdit.text.toString(),
                playerImage = it
            )
        }

        if (player != null) {
            editViewModel.updatePlayer(player)
        }
        Toast.makeText(this@EditActivity, "Data pemain berhasil diedit", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this@EditActivity, MainActivity::class.java)).apply {
            finishAffinity()
        }
    }

    private fun validationInput(): Boolean {
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

        return countError == 0
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }

}