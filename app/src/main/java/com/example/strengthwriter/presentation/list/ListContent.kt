package com.example.strengthwriter.presentation.list

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.strengthwriter.data.model.DailyMission
import com.example.strengthwriter.data.model.Workout
import com.example.strengthwriter.presentation.components.DisplayAlertDialog
import com.example.strengthwriter.presentation.components.WorkoutItem
import com.example.strengthwriter.ui.theme.*

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun ListContent(
    missions: List<DailyMission>,
    removeDailyMission: (DailyMission) -> Unit
) {
    val openRemoveDialog = remember { mutableStateOf(false) }
    val selectedMission = remember { mutableStateOf<DailyMission?>(null) }
    LazyColumn(
        modifier = Modifier.padding(all = PADDING_SMALL),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(vertical = PADDING_EXTRA_LARGE)
    ) {
        items(
            items = missions,
            key = {
                it.id
            }
        ) { mission ->
            Surface(
                modifier = Modifier.combinedClickable(
                    onClick = {},
                    onLongClick = {
                        openRemoveDialog.value = true
                        selectedMission.value = mission
                    }
                )
            ) {
                ContentItem(mission)
            }
        }
    }

    DisplayAlertDialog(
        title = "Delete Mission",
        body = { Text(text = "Are you sure delete this Mission?")},
        openDialog = openRemoveDialog.value,
        closeDialog = { openRemoveDialog.value= false }
    ) {
        removeDailyMission(selectedMission.value!!)
        selectedMission.value = null
        openRemoveDialog.value = false
    }
}

@Composable
private fun ContentItem(
    mission: DailyMission
) {
    val workouts: List<Workout> = mission.workout!!
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = PADDING_SMALL),
        elevation = CARD_ELEVATION_5,
        backgroundColor = Color.LightGray
    ) {
        Surface(
            shape = RoundedCornerShape(size = CORNER_SMALL)
        ) {
            Column() {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        modifier = Modifier.padding(all = PADDING_MEDIUM),
                        text = "${mission.title}",
                        fontSize = MaterialTheme.typography.h5.fontSize,
                    )
                    Text(
                        modifier = Modifier.padding(all = PADDING_MEDIUM),
                        text = "Date : ${mission.date}"
                    )
                }
                Divider(
                    Modifier
                        .fillMaxWidth(0.6f)
                        .padding(horizontal = PADDING_SMALL)
                )
                repeat(workouts.size) { idx ->
                    WorkoutItem(
                        workout = workouts[idx]
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun ListPreview() {
}
