package com.example.to_docompose.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    companion object {
        private const val SPLASH_DURATION: Long = 3_000
    }

    private val _needToNavigateFromSplash: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val needToNavigateFromSplash: StateFlow<Boolean> = _needToNavigateFromSplash

    init {
        startSplashTimer()
    }

    private fun startSplashTimer() {
        viewModelScope.launch {
            delay(SPLASH_DURATION)
            _needToNavigateFromSplash.value = true
        }
    }

    fun onNavigatedFromSplash() {
        _needToNavigateFromSplash.value = false
    }
}
