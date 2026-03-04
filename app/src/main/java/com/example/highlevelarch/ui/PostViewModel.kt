package com.example.highlevelarch.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.highlevelarch.domain.model.Post
import com.example.highlevelarch.domain.usecase.GetPostsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * UI STATE DEFINITION
 */
sealed class PostUiState {
    object Idle : PostUiState()
    object Loading : PostUiState()
    data class Success(val posts: List<Post>) : PostUiState()
    data class Error(val message: String) : PostUiState()
}

/**
 * VIEWMODEL WITH SEARCH & DEBOUNCE
 * Now uses Hilt for DI and GetPostsUseCase for business logic.
 */
@HiltViewModel
@OptIn(FlowPreview::class)
class PostViewModel @Inject constructor(
    private val getPostsUseCase: GetPostsUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    // THE DEBOUNCE PIPELINE
    val uiState: StateFlow<PostUiState> = _searchQuery
        .debounce(500L) // Wait for user to stop typing
        .distinctUntilChanged()
        .flatMapLatest { query ->
            getPostsUseCase(query)
        }
        .map { result ->
            result.fold(
                onSuccess = { PostUiState.Success(it) },
                onFailure = { PostUiState.Error(it.message ?: "Unknown Error") }
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PostUiState.Loading
        )

    fun onQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }
}
