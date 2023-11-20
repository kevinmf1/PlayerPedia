package com.vinz.playerpedia.activity.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.vinz.playerpedia.R
import com.vinz.data.domain.model.PlayerRemote
import com.vinz.playerpedia.databinding.ActivityDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val player = intent.getParcelableExtra<PlayerRemote>("player") as PlayerRemote

        Glide.with(this)
            .load(player.photo)
            .into(binding.ivPlayer)

        binding.tvPlayerName.text = player.name
        binding.tvPlayerClub.text = player.club
        binding.tvRating.text = player.rate.toString()
        binding.tvPlayerActive.text = if (player.activePlayer == "NO") getString(R.string.retired_player) else getString(R.string.active_player)
        binding.tvPlayerPosition.text = player.position
        binding.tvGoalAssist.text = getString(R.string.goals_assists, player.allGoalUntilNow, player.allAssistUntilNow)
        binding.tvPlayerDescription.text = player.description

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.btnSearchGoogle.setOnClickListener {
            val playerName = player.name

            val searchQuery = "https://www.google.com/search?q=${playerName}"

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(searchQuery))

            if (intent.resolveActivity(this.packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(this, getString(R.string.error_no_browser), Toast.LENGTH_SHORT).show()
            }
        }
    }
}