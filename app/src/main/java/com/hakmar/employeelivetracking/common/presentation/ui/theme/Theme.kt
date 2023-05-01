package com.hakmar.employeelivetracking.common.presentation.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Green40,
    secondary = Green40,
    background = DefaultBackground,
    onPrimary = Natural100,
    onSecondary = Natural100,
    surface = White,
    onSurface = Natural80,
)

private val LightColorScheme = lightColorScheme(
    primary = Green40,
    secondary = Green40,
    background = DefaultBackground,
    onPrimary = Natural100,
    onSecondary = Natural100,
    surface = White,
    onSurface = Natural80,
    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun EmployeeLiveTrackingTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        else -> LightColorScheme
    }
    val view = LocalView.current
    val currentWindow = (view.context as? Activity)?.window
        ?: throw Exception("Not in an activity - unable to get Window reference")
    if (!view.isInEditMode) {
        SideEffect {

            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(currentWindow, view).isAppearanceLightStatusBars =
                darkTheme
        }
    }
    CompositionLocalProvider(
        LocalSpacing provides Spacing(),
        LocalColors provides AppColors(),
        LocalOverscrollConfiguration provides null,

    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = InterTypography,
            content = content
        )
    }

}