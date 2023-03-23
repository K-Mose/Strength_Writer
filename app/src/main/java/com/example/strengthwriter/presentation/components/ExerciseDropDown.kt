package com.example.strengthwriter.presentation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.strengthwriter.R
import com.example.strengthwriter.ui.theme.Typography
import com.example.strengthwriter.utils.Exercise

@Composable
fun ExerciseDropDown(
    exercise: Exercise,
    onExerciseSelected: (Exercise) -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    val angle: Float by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .clickable { expanded = true}
            .border(
                width = 1.dp,
                color = MaterialTheme.colors.onSurface.copy(
                    alpha = ContentAlpha.disabled
                ),
                shape = MaterialTheme.shapes.small
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier.weight(8f),
            text = exercise.getName(),
            style = MaterialTheme.typography.h5
        )
        IconButton(
            modifier = Modifier
                .alpha(ContentAlpha.medium)
                .rotate(degrees = angle)
                .weight(1f),
            onClick = { expanded = true }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = stringResource(R.string.drop_down_arrow)
            )
        }
        DropdownMenu(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(onClick = {
                expanded = false
                onExerciseSelected(Exercise.SHOULDER_PRESS)
            }) {
                ExerciseItem(exercise = Exercise.SHOULDER_PRESS)
            }
            DropdownMenuItem(onClick = {
                expanded = false
                onExerciseSelected(Exercise.DEAD_LIFT)
            }) {
                ExerciseItem(exercise = Exercise.DEAD_LIFT)
            }
            DropdownMenuItem(onClick = {
                expanded = false
                onExerciseSelected(Exercise.BACK_SQUAT)
            }) {
                ExerciseItem(exercise = Exercise.BACK_SQUAT)
            }
            DropdownMenuItem(onClick = {
                expanded = false
                onExerciseSelected(Exercise.FRONT_SQUAT)
            }) {
                ExerciseItem(exercise = Exercise.FRONT_SQUAT)
            }
        }
    }
}

@Composable
fun ExerciseItem(exercise: Exercise) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.padding(start = 12.dp),
            text = exercise.getName(),
            style = Typography.subtitle2,
            color = MaterialTheme.colors.onSurface
        )
    }
}

@Preview
@Composable
fun DropDownPreview() {
    ExerciseDropDown(
        exercise = Exercise.DEAD_LIFT,
        onExerciseSelected = {}
    )
}