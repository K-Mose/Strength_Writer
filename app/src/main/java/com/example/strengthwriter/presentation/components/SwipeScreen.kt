package com.example.strengthwriter.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.strengthwriter.R
import com.example.strengthwriter.ui.theme.PADDING_LARGE
import com.example.strengthwriter.utils.ContentItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeScreen(
    onSwipeToDelete: () -> Unit,
    content: @Composable () -> Unit
) {

    // swipe to delete
    // setup dismissState
    var dismissState = rememberDismissState()
    val dismissDirection = dismissState.dismissDirection
    var isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)
    // delete action
    if (isDismissed && dismissDirection == DismissDirection.EndToStart) {
        onSwipeToDelete()
    }

    // dismiss animation
    val degrees by animateFloatAsState(
        targetValue = if (dismissState.targetValue == DismissValue.Default) 0f else -45f
    )

    var itemAppeared by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(key1 = true) {
        itemAppeared = true
    }

    AnimatedVisibility(
        visible = itemAppeared && !isDismissed,
        enter = expandVertically(
            animationSpec = tween(
                durationMillis = 300
            )
        ),
        exit = shrinkVertically(
            animationSpec = tween(
                durationMillis = 300
            )
        )
    ) {
        SwipeToDismiss(
            modifier = Modifier.fillMaxWidth(),
            state = dismissState,
            directions = setOf(DismissDirection.EndToStart),
            dismissThresholds = { FractionalThreshold(0.2f) },
            background = { RedBackground(degrees = degrees) },
            dismissContent = {
                Surface(modifier = Modifier
                    .fillMaxWidth()
                ) {
                    content()
                }
            }
        )
    }
}



@Composable
fun RedBackground(degrees: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(if (degrees == 0f) Color.White else Color(0xFFFF4646))
            .padding(PADDING_LARGE),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            modifier = Modifier.rotate(degrees = degrees),
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.delete_icon),
            tint = Color.White
        )
    }
}