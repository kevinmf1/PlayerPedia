package com.vinz.playerpedia.activity.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.vinz.playerpedia.R
import com.vinz.playerpedia.core.domain.model.Player
import com.vinz.playerpedia.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)

        val player = intent.getParcelableExtra<Player>("player") as Player

        Glide.with(this)
            .load(player.playerImage)
            .into(binding.ivPlayer)

        binding.tvPlayerName.text = player.playerName
        binding.tvPlayerClub.text = player.playerClub
        binding.tvPlayerPosition.text = player.playerPosition
        binding.tvPlayerNationally.text = player.playerNationality
        binding.tvPlayerDescription.text = player.playerDescription

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnSearchGoogle.setOnClickListener {
            val playerName = player.playerName

            val searchQuery = "https://www.google.com/search?q=${playerName}"

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(searchQuery))

            if (intent.resolveActivity(this.packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, "Tidak ada browser yang tersedia.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}