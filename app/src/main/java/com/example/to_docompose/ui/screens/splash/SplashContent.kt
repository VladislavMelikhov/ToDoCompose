package com.example.to_docompose.ui.screens.splash

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.to_docompose.R
import com.example.to_docompose.ui.theme.LocalCustomColorsPalette
import com.example.to_docompose.ui.theme.ToDoComposeTheme

private const val ANIMATION_DURATION = 700

@Composable
fun AnimatedSplashContent() {
    var startAnimation by remember { mutableStateOf(false) }

    val logoOffset by animateDpAsState(
        targetValue = if (startAnimation) 0.dp else 100.dp,
        animationSpec = tween(ANIMATION_DURATION),
        label = "Logo offset animation",
    )

    val logoAlpha by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(ANIMATION_DURATION),
        label = "Logo alpha animation",
    )

    LaunchedEffect(true) {
        startAnimation = true
    }

    SplashContent(
        logoOffset = logoOffset,
        logoAlpha = logoAlpha,
    )
}

@Composable
fun SplashContent(
    logoOffset: Dp = 0.dp,
    logoAlpha: Float = 1f,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalCustomColorsPalette.current.splashBackgroundColor),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .offset { IntOffset(x = 0, y = logoOffset.roundToPx()) }
                .alpha(alpha = logoAlpha)
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
}

@Composable
@Preview
private fun SplashContentPreview() {
    ToDoComposeTheme(darkTheme = false) {
        SplashContent()
    }
}
