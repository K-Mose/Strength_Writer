package com.example.strengthwriter.presentation.item

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.strengthwriter.R
import com.example.strengthwriter.data.model.Workout
import com.example.strengthwriter.navigation.Screens
import com.example.strengthwriter.presentation.calculator.CalDetail
import com.example.strengthwriter.presentation.components.ActionItem
import com.example.strengthwriter.presentation.components.MyAppBar
import com.example.strengthwriter.presentation.components.WorkoutCard
import com.example.strengthwriter.presentation.viewmodel.DetailViewModel
import com.example.strengthwriter.ui.theme.PADDING_MEDIUM
import com.example.strengthwriter.ui.theme.PADDING_SMALL
import com.example.strengthwriter.utils.Exercise
import com.example.strengthwriter.utils.RequestState
import com.example.strengthwriter.utils.Utils.toFormattedString
import java.util.*

@Composable
fun DetailScreen(
    navController: NavHostController,
) {
    Text(text = stringResource(R.string.detail_screen))
}