package com.shawakri.pixabayimagesearch.ui

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.shawakri.pixabayimagesearch.data.PixaBayRepo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImagesViewModel @Inject constructor(
    private val repo: PixaBayRepo,
    private val currentState: SavedStateHandle
    ): ViewModel() {

        companion object {
            const val CURRENT_QUERY = "current_query"
            const val DEFAULT_QUERY = "fruits"
        }

    private val currentQuery = currentState.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    val photos = currentQuery.switchMap { query ->
        repo.getSearchResults(query).cachedIn(viewModelScope)
    }

    fun searchPhotos(query: String){
        currentQuery.value = query
    }
}