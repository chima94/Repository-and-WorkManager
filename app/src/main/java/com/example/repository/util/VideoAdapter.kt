package com.example.repository.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.repository.databinding.DevbyteItemBinding
import com.example.repository.domain.DevByteVideo


class VideoAdapter(private val onclickListener : OnclickListener) : ListAdapter<DevByteVideo, VideoAdapter.VideoHolder>(DiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
       return VideoHolder.from(parent)
    }

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {
        val devByteVideo = getItem(position)
        holder.itemView.setOnClickListener {
            onclickListener.onClick(devByteVideo)
        }
        holder.bind(devByteVideo)

    }


    class VideoHolder private constructor(private var binding : DevbyteItemBinding) : RecyclerView.ViewHolder(binding.root){

        companion object {
            fun from(parent: ViewGroup): VideoHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val viewDataBinding = DevbyteItemBinding.inflate(layoutInflater, parent, false)
                return VideoHolder(viewDataBinding)
            }
        }
            fun bind(devByteVideo: DevByteVideo){
                binding.video = devByteVideo
                binding.executePendingBindings()
            }
    }

    class OnclickListener(val clickListener : (devByVideo : DevByteVideo) ->Unit){
        fun onClick(devByteVideo: DevByteVideo) = clickListener(devByteVideo)
    }


}

class DiffCallback :DiffUtil.ItemCallback<DevByteVideo>(){
    override fun areItemsTheSame(oldItem: DevByteVideo, newItem: DevByteVideo): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: DevByteVideo, newItem: DevByteVideo): Boolean {
        return oldItem.title == newItem.title
    }

}