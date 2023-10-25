package com.vinz.playerpedia.activity.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vinz.playerpedia.R
import com.vinz.playerpedia.activity.addplayer.AddActivity
import com.vinz.playerpedia.activity.detail.DetailActivity
import com.vinz.playerpedia.activity.edit.EditActivity
import com.vinz.playerpedia.activity.login.LoginActivity
import com.vinz.playerpedia.activity.user.ProfileActivity
import com.vinz.playerpedia.adapter.PlayerAdapter
import com.vinz.playerpedia.core.di.PlayerViewModelFactory
import com.vinz.playerpedia.core.domain.model.Player
import com.vinz.playerpedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var playerAdapter: PlayerAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var binding: ActivityMainBinding
    private lateinit var homeViewModel: HomeViewModel

    private var isLinearLayoutManager = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        recyclerView = binding.rvPlayers
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val factory = PlayerViewModelFactory.getInstance(this)
        homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        homeViewModel.player.observe(this) { player ->
            if (player.isNotEmpty()) {
                visibilityEmptyData(false)
                playerAdapter = PlayerAdapter(player)
                recyclerView.adapter = playerAdapter

                playerAdapter.setOnItemClickCallback(object : PlayerAdapter.OnItemClickCallback {
                    override fun onItemClicked(data: Player) {
                        navigateToAnotherActivity(data, DetailActivity::class.java)
                    }

                    override fun onEditClicked(data: Player) {
                        navigateToAnotherActivity(data, EditActivity::class.java)
                    }

                    override fun onDeleteClicked(data: Player) {
                        dialogDelete(data)
                    }
                })
            } else {
                visibilityEmptyData(true)
            }
        }

        binding.fabAddPlayer.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }

        binding.btnExitApp.setOnClickListener {
            dialogLogout()
        }

        binding.btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun dialogDelete(player: Player) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Hapus Player")
        builder.setMessage("Apakah anda yakin ingin menghapus player ini?")
        builder.setPositiveButton("Ya") { _, _ ->
            homeViewModel.deletePlayer(player)
        }
        builder.setNegativeButton("Tidak") { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }

    private fun dialogLogout() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Logout")
        builder.setMessage("Apakah anda yakin ingin logout?")
        builder.setPositiveButton("Ya") { _, _ ->
            val sharedPreferences = getSharedPreferences("isUserLogin", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean("isUserLogin", false)
            editor.apply()

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
        builder.setNegativeButton("Tidak") { dialog, _ ->
            dialog.cancel()
        }
        builder.show()
    }

    private fun visibilityEmptyData(boolean: Boolean) {
        binding.emptyData.visibility = if (boolean) View.VISIBLE else View.GONE
        binding.emptyText.visibility = if (boolean) View.VISIBLE else View.GONE
        binding.rvPlayers.visibility = if (boolean) View.GONE else View.VISIBLE
    }

    private fun navigateToAnotherActivity(player: Player, activity: Class<*>) {
        val intent = Intent(this, activity)
        intent.putExtra("player", player)
        startActivity(intent)
    }

}