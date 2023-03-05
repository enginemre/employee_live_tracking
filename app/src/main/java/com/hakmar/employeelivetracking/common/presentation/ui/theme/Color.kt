@file:Suppress("unused")

package com.hakmar.employeelivetracking.common.presentation.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color


val Green40 = Color(0xff6EDD8A)
val Gray40 = Color(0xffE1FAEB)
val DefaultBackground = Color(0xffE5E5E5)
val White = Color(0xffffffff)


val Natural40 = Color(0xffD0D6DB)
val Natural50 = Color(0xffB8C2C8)
val Natural60 = Color(0xffB8C2C8)
val Natural70 = Color(0xff8696A3)
val Natural80 = Color(0xff6F7374)
val Natural90 = Color(0xff3D464A)
val Natural100 = Color(0xff00214d)
val Natural110 = Color(0xff0d1c2e)

val Warning = Color(0xffF2C94C)
val WarningSurface = Color(0xffFCF4DB)
val WarningPressed = Color(0xff796426)

val Info = Color(0xff0C61F7)
val InfoPressed = Color(0xff06307C)
val InfoSurface = Color(0xffCEDFFD)

data class AppColors(
    val primary: Color = Green40,
    val secondary: Color = Green40,
    val background: Color = DefaultBackground,
    val onPrimary: Color = Natural100,
    val onSecondary: Color = Natural100,
    val surface: Color = White,
    val onSurface: Color = Natural80,
)

val LocalColors = compositionLocalOf { AppColors() }


val MaterialTheme.colors: AppColors
    @Composable
    @ReadOnlyComposable
    get() = LocalColors.current

