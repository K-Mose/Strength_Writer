package com.example.strengthwriter.ui.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val Colors.fabBackgroundColor: Color
    @Composable
    get() = if(isLight) Color.Black else Color.LightGray

val Colors.fabIconColor: Color
    @Composable
    get() = if(isLight) Color.White else Color.Black