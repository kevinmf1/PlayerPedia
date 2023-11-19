package com.vinz.playerpedia.activity.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vinz.playerpedia.R
import com.vinz.playerpedia.activity.detail.DetailActivity
import com.vinz.playerpedia.activity.login.LoginActivity
import com.vinz.playerpedia.activity.user.ProfileActivity
import com.vinz.core.domain.model.PlayerRemote
import com.vinz.core.utils.ResultWrapper
import com.vinz.playerpedia.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var playerAdapter: com.vinz.core.ui.PlayerAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding
    private val homeViewModel: HomeViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.rvPlayers
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        homeViewModel.player.observe(this) { player ->
            if (player != null) {
                when (player) {
                    is ResultWrapper.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is ResultWrapper.Success -> {
                        binding.progressBar.visibility = View.GONE
                        visibilityEmptyData(false)

                        player.payload.let { data ->
                            if (data != null) {
                                playerAdapter = com.vinz.core.ui.PlayerAdapter(data)
                            }
                        }

                        recyclerView.adapter = playerAdapter

                        playerAdapter.setOnItemClickCallback(object :
                            com.vinz.core.ui.PlayerAdapter.OnItemClickCallback {
                            override fun onItemClicked(data: PlayerRemote) {
                                navigateToAnotherActivity(
                                    data,
                                    DetailActivity::class.java
                                )
                            }
                        })
                    }

                    is ResultWrapper.Error -> {
                        Log.d("Error", player.message.toString()  + " " + player.exception.toString())
                        binding.progressBar.visibility = View.GONE
                        visibilityEmptyData(true)
                    }

                    is ResultWrapper.Empty -> {
                        Toast.makeText(this, getString(R.string.empty_data), Toast.LENGTH_SHORT).show()
                        binding.progressBar.visibility = View.GONE
                        visibilityEmptyData(true)
                    }

                }
            }
        }

        binding.btnExitApp.setOnClickListener {
            dialogLogout()
        }

        binding.btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun dialogLogout() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.logout_title))
        builder.setMessage(getString(R.string.logout_desc))
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            val sharedPreferences = getSharedPreferences("isUserLogin", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("isUserLogin", false)
            editor.apply()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
        builder.setNegativeButton(getString(R.string.no)) { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }

    private fun visibilityEmptyData(boolean: Boolean) {
        binding.emptyData.visibility = if (boolean) View.VISIBLE else View.GONE
        binding.emptyText.visibility = if (boolean) View.VISIBLE else View.GONE
        binding.rvPlayers.visibility = if (boolean) View.GONE else View.VISIBLE
    }

    private fun navigateToAnotherActivity(player: PlayerRemote, activity: Class<*>) {
        val intent = Intent(this, activity)
        intent.putExtra("player", player)
        startActivity(intent)
    }

}