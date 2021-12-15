package com.shawakri.pixabayimagesearch.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pixabayimagesearch.databinding.PagingFooterBinding

class PagingFooterAdapter(private val retry: () -> Unit) : LoadStateAdapter<PagingFooterAdapter.LoadViewHolder>() {

    inner class LoadViewHolder(private val binding: PagingFooterBinding) :
        RecyclerView.ViewHolder(binding.root){

        init {
            binding.reconnectButton.setOnClickListener {
                retry.invoke()
            }
        }
            fun bind (loadState: LoadState){
                binding.apply {
                    loadingLottie.isVisible = loadState is LoadState.Loading
                    errorTV.isVisible = loadState !is LoadState.Loading
                    reconnectButton.isVisible = loadState !is LoadState.Loading

                }
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadViewHolder {
        val binding =
            PagingFooterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return LoadViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LoadViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }


}