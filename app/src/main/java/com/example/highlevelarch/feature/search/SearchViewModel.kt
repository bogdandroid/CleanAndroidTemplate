package com.example.highlevelarch.feature.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

sealed class SearchUiState {
    object Idle : SearchUiState()
    object Loading : SearchUiState()
    data class Success(val results: List<BookDto>) : SearchUiState()
    data class Error(val message: String) : SearchUiState()
}

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    val uiState: StateFlow<SearchUiState> = _searchQuery
        .debounce(500L)
        .distinctUntilChanged()
        .transformLatest { query ->
            if (query.isBlank()) {
                emit(SearchUiState.Idle)
            } else {
                emit(SearchUiState.Loading)
                try {
                    val results = searchRepository.searchBooks(query)
                    emit(SearchUiState.Success(results))
                } catch (e: Exception) {
                    emit(SearchUiState.Error(e.message ?: "An error occurred"))
                }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = SearchUiState.Idle
        )

    fun onQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }
}
