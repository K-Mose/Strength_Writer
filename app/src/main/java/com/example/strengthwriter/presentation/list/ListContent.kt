package com.example.strengthwriter.presentation.list

import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.strengthwriter.data.model.DailyMission
import com.example.strengthwriter.data.model.Sets
import com.example.strengthwriter.data.model.Workout
import com.example.strengthwriter.presentation.components.WorkoutItem
import com.example.strengthwriter.ui.theme.*
import com.example.strengthwriter.utils.Utils.removeDecimal

@Composable
fun ListContent(
    missions: List<DailyMission>
) {
    LazyColumn(
        modifier = Modifier.padding(all = PADDING_SMALL),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(all = PADDING_EXTRA_LARGE)
    ) {
        items(
            items = missions,
            key = {
                it.id
            }
        ) { mission ->
            ContentItem(mission)
            Spacer(modifier = Modifier.padding(all = PADDING_SMALL))
        }
    }
}

@Composable
private fun ContentItem(
    mission: DailyMission
) {
    val workouts: List<Workout> = mission.workout!!
    Card(
        modifier = Modifier
            .fillMaxWidth(),
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