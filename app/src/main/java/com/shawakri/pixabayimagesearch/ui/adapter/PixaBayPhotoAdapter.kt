package com.shawakri.pixabayimagesearch.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pixabayimagesearch.databinding.ImageContainerBinding
import com.shawakri.pixabayimagesearch.data.PixaBayPhoto

class PixaBayPhotoAdapter(private val listener: onItemClickListener) :
    PagingDataAdapter<PixaBayPhoto, PixaBayPhotoAdapter.PhotoViewHolder>(COMPARTOR) {

    inner class PhotoViewHolder(val binding: ImageContainerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.previewImage.setOnClickListener {
                val position = bindingAdapterPosition

                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    if (item != null) {
                        listener.onItemClick(item)
                    }
                }
            }
        }
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item = getItem(position)

        if (item != null) {
            holder.binding.photo = item
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val binding =
            ImageContainerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoViewHolder(binding)
    }

    companion object {
        private val COMPARTOR = object : DiffUtil.ItemCallback<PixaBayPhoto>() {
            override fun areItemsTheSame(oldItem: PixaBayPhoto, newItem: PixaBayPhoto) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: PixaBayPhoto, newItem: PixaBayPhoto) =
                oldItem == newItem
        }
    }

    interface onItemClickListener {
        fun onItemClick(photo: PixaBayPhoto)
    }

}