package com.vinz.playerpedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView
import com.vinz.dataapp.R
import com.vinz.domain.model.PlayerRemote

class PlayerAdapter(
    private val playerList: List<PlayerRemote>
) :
    RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: PlayerRemote)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_player, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val player = playerList[position]
        holder.playerName.text = player.name
        holder.playerDescription.text = player.description
        Glide.with(holder.itemView.context)
            .load(player.photo)
            .into(holder.imagePlayer)

        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(playerList[position])
        }
    }

    override fun getItemCount(): Int = playerList.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val playerName: MaterialTextView = view.findViewById(R.id.tv_item_name)
        val playerDescription: MaterialTextView = view.findViewById(R.id.tv_item_description)
        val imagePlayer: ShapeableImageView = view.findViewById(R.id.img_item_photo)
    }
}