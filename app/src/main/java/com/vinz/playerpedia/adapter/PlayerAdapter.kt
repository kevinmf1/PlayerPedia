package com.vinz.playerpedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.vinz.playerpedia.core.domain.model.Player
import com.vinz.playerpedia.databinding.ItemPlayerBinding
import java.io.File

class PlayerAdapter(private val playerList: List<Player>) :
    RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Player)
        fun onEditClicked(data: Player)
        fun onDeleteClicked(data: Player)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPlayerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val player = playerList[position]
        holder.bind(player)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(player)
        }
        holder.binding.btnEditPlayer.setOnClickListener {
            onItemClickCallback.onEditClicked(player)
        }
        holder.binding.btnDeletePlayer.setOnClickListener {
            onItemClickCallback.onDeleteClicked(player)
        }
    }

    class ViewHolder(val binding: ItemPlayerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(player: Player) {
            binding.player = player
            binding.executePendingBindings()
        }
    }

    override fun getItemCount(): Int = playerList.size

    companion object {

        @JvmStatic
        @BindingAdapter("setPhoto")
        fun setPhoto(imgPhoto: ShapeableImageView, img: File) {
            Glide.with(imgPhoto)
                .load(img)
                .into(imgPhoto)
        }
    }

}