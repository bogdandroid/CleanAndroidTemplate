package com.example.highlevelarch.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.highlevelarch.domain.model.Post

/**
 * POST LIST SCREEN WITH SEARCH
 * Pure UI layer observing the ViewModel.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostListScreen(viewModel: PostViewModel) {
    val state by viewModel.uiState.collectAsState()
    val query by viewModel.searchQuery.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Search Posts (Debounced)") })
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            
            // --- THE SEARCH BAR ---
            OutlinedTextField(
                value = query,
                onValueChange = { viewModel.onQueryChanged(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text("Type to search...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true
            )

            // --- THE DATA DISPLAY ---
            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                when (val current = state) {
                    is PostUiState.Loading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                    is PostUiState.Error -> Text("Error: ${current.message}", color = MaterialTheme.colorScheme.error, modifier = Modifier.align(Alignment.Center))
                    is PostUiState.Success -> PostList(current.posts)
                    else -> {} 
                }
            }
        }
    }
}

@Composable
private fun PostList(posts: List<Post>) {
    if (posts.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No results found.", style = MaterialTheme.typography.bodyLarge)
        }
    } else {
        LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            items(posts) { post -> PostItem(post) }
        }
    }
}

@Composable
private fun PostItem(post: Post) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = post.title, style = MaterialTheme.typography.titleMedium, maxLines = 1, overflow = TextOverflow.Ellipsis)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = post.body, style = MaterialTheme.typography.bodyMedium, maxLines = 3, overflow = TextOverflow.Ellipsis)
        }
    }
}
