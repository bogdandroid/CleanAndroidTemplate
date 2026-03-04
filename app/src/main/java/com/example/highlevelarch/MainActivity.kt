package com.example.highlevelarch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.highlevelarch.ui.PostListScreen
import com.example.highlevelarch.ui.PostViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * MAIN ACTIVITY
 * Entry point for the UI. Hilt handles injecting the ViewModel automatically.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: PostViewModel = hiltViewModel()
            PostListScreen(viewModel)
        }
    }
}
