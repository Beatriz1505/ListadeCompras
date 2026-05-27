package com.mcr.listacompras.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val MCRColorScheme = darkColorScheme(
    primary        = Color(0xFFCC0000),   // Vermelho MCR
    onPrimary      = Color.White,
    secondary      = Color(0xFF8B0000),   // Vermelho escuro
    onSecondary    = Color.White,
    background     = Color(0xFF0D0D0D),   // Quase preto
    onBackground   = Color(0xFFEEEEEE),
    surface        = Color(0xFF1A1A1A),   // Preto suave
    onSurface      = Color(0xFFEEEEEE),
    surfaceVariant = Color(0xFF2D2D2D),
    onSurfaceVariant = Color(0xFFAAAAAA),
    error          = Color(0xFFFF4444),
    onError        = Color.White
)

@Composable
fun MCRTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = MCRColorScheme,
        typography = MCRTypography,
        content = content
    )
}