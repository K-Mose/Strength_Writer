package com.example.strengthwriter.presentation.item

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.strengthwriter.R
import com.example.strengthwriter.presentation.viewmodel.DetailViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun DetailScreen(
    navController: NavHostController,
) {
    Text(text = stringResource(R.string.detail_screen))
}