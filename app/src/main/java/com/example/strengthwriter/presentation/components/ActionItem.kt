package com.example.strengthwriter.presentation.components

import androidx.compose.ui.graphics.vector.ImageVector

data class ActionItem(
    val icon: ImageVector,
    val onClick: () -> Unit
)
