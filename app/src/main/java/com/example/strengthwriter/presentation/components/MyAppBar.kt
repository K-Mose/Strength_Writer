package com.example.strengthwriter.presentation.components

import androidx.compose.material.*
import com.example.strengthwriter.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.strengthwriter.ui.theme.AppBarColor

@Composable
fun MyAppBar(
   title: String,
   menuClick: () -> Unit
) {
   TopAppBar(
      backgroundColor = AppBarColor,
      title = { Text(text = title)},
      contentColor = Color.White,
      navigationIcon = {
         IconButton(onClick = {
            menuClick()
         }) {
            Icon(
               imageVector = Icons.Default.Menu,
               contentDescription = stringResource(R.string.menu_icon)
            )
         }
      }
   )
}