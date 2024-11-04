package com.example.to_docompose.ui.screens.splash

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = hiltViewModel(),
    navigateToTasksList: () -> Unit,
) {
    val needToNavigateFromSplash by viewModel.needToNavigateFromSplash.collectAsStateWithLifecycle()

    if (needToNavigateFromSplash) {
        LaunchedEffect(true) {
            navigateToTasksList()
        }
        viewModel.onNavigatedFromSplash()
    }

    AnimatedSplashContent()
}
