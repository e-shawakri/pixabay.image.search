package com.shawakri.pixabayimagesearch.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.pixabayimagesearch.R
import com.example.pixabayimagesearch.databinding.FragmentImagesBinding
import com.shawakri.pixabayimagesearch.data.PixaBayPhoto
import com.shawakri.pixabayimagesearch.ui.adapter.PagingFooterAdapter
import com.shawakri.pixabayimagesearch.ui.adapter.PixaBayPhotoAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint()
class ImagesFragment : Fragment(R.layout.fragment_images), PixaBayPhotoAdapter.onItemClickListener {


    private val viewModel by viewModels<ImagesViewModel>()

    private var _binding: FragmentImagesBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentImagesBinding.bind(view)

        val adapter = PixaBayPhotoAdapter(this)

        binding.apply {
            imageRecyclerView.setHasFixedSize(true)
            imageRecyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = PagingFooterAdapter { adapter.retry() },
                footer = PagingFooterAdapter { adapter.retry() }
            )

            retryButton.setOnClickListener {
                adapter.retry()
            }
        }

        viewModel.photos.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
        }

        adapter.addLoadStateListener { loadState ->

            binding.apply {
                imageProgressBar.isVisible = loadState.source.refresh is LoadState.Loading
                imageRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                errorLL.isVisible = loadState.source.refresh is LoadState.Error

                if ( loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1 ) {
                    imageRecyclerView.isVisible = false
                    nothingToShowLL.isVisible = true
                } else {
                    nothingToShowLL.isVisible = false
                }

            }

        }
        setHasOptionsMenu(true)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.searchItem)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null){
                    binding.imageRecyclerView.scrollToPosition(0)
                    viewModel.searchPhotos(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?) = true

        })
    }

    override fun onItemClick(photo: PixaBayPhoto) {
        val action = ImagesFragmentDirections.actionImagesFragmentToDetailsFragment2(photo)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}