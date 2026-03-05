package com.example.highlevelarch.feature.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
fun SearchScreen(viewModel: SearchViewModel = koinViewModel()) {
    val query by viewModel.searchQuery.collectAsStateWithLifecycle()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = query,
            onValueChange = viewModel::onQueryChanged,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Search Books") },
            singleLine = true
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        when (val state = uiState) {
            is SearchUiState.Idle -> {
                Text("Type an author or book name to start searching...")
            }
            is SearchUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }
            is SearchUiState.Error -> {
                Text(text = "Error: ${state.message}", color = MaterialTheme.colorScheme.error)
            }
            is SearchUiState.Success -> {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.results) { book ->
                        BookItem(book = book)
                    }
                }
            }
        }
    }
}

@Composable
fun BookItem(book: BookDto) {
    Card(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = book.title, style = MaterialTheme.typography.titleMedium)
            val authors = book.authorName?.joinToString(", ") ?: "Unknown Author"
            Text(text = "By: $authors", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
