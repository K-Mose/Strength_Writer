package com.example.strengthwriter.presentation.components

import androidx.compose.material.*
import com.example.strengthwriter.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.strengthwriter.ui.theme.AppBarColor

@Composable
fun MyAppBar(
   title: String,
   isNavigation: Boolean = false,
   navigationIcon: ActionItem? = null,
   isActionButton: Boolean = false,
   actionIcon: ActionItem? = null,
   iconBody: @Composable () -> Unit = {}
) {
   TopAppBar(
      backgroundColor = AppBarColor,
      title = { Text(text = title)},
      contentColor = Color.White,
      navigationIcon = {
         if (isNavigation) {
            IconButton(onClick = { navigationIcon!!.onClick() }) {
               Icon(
                  imageVector = navigationIcon!!.icon!!,
                  contentDescription = stringResource(R.string.menu_icon)
               )
            }
         }
      },
      actions = {
         if (isActionButton) {
            IconButton(onClick = { actionIcon!!.onClick() }) {
               Icon(
                  imageVector = actionIcon!!.icon,
                  contentDescription = stringResource(R.string.topbar_action),
                  tint = Color.White
               )
               iconBody()
            }
         }
      }
   )
}