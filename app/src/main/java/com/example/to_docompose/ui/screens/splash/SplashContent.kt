package com.example.to_docompose.ui.screens.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.to_docompose.R
import com.example.to_docompose.ui.theme.LocalCustomColorsPalette
import com.example.to_docompose.ui.theme.ToDoComposeTheme

@Composable
fun SplashContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalCustomColorsPalette.current.splashBackgroundColor),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_logo_background),
            contentDescription = stringResource(R.string.to_do_logo),
            tint = LocalCustomColorsPalette.current.splashContentColor,
        )
        Icon(
            painter = painterResource(R.drawable.ic_logo_foreground),
            contentDescription = stringResource(R.string.to_do_logo),
            tint = LocalCustomColorsPalette.current.splashBackgroundColor,
        )
    }
}

@Composable
@Preview
private fun SplashContentPreview() {
    ToDoComposeTheme(darkTheme = false) {
        SplashContent()
    }
}
