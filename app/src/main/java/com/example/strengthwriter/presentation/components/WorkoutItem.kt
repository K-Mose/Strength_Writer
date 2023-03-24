package com.example.strengthwriter.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.strengthwriter.data.model.Sets
import com.example.strengthwriter.data.model.Workout
import com.example.strengthwriter.ui.theme.*
import com.example.strengthwriter.utils.Utils.removeDecimal


@Composable
fun WorkoutItem(
    workout: Workout
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(PADDING_SMALL)
    ) {
        val scrollState = rememberScrollState()
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
//                modifier = Modifier.weight(1f),
                text = workout.name.getName(),
                fontSize = MaterialTheme.typography.body1.fontSize,
                fontWeight = FontWeight.Bold
            )
            Text(
//                modifier = Modifier.weight(1f),
                text = workout.memo.let {
                    if (it.isNotEmpty()) " - $it"
                    else it
                },
                fontSize = 10.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(
                    state = scrollState
                )
        ) {
            repeat(workout.sets.size) { idx ->
                SetsItem(idx = (idx + 1), sets = workout.sets[idx])
                Spacer(modifier = Modifier.width(SPACER_WIDTH))
            }
        }
    }
}

@Composable
private fun SetsItem(
    idx: Int,
    sets: Sets
) {
    Box(
        modifier = Modifier
            .border(
                shape = RoundedCornerShape(size = CORNER_SMALL),
                width = 1.dp,
                color = Color.LightGray,
            )
            .padding(all = PADDING_SMALL)
    ) {
        Row {
            Text(
                fontSize = MaterialTheme.typography.caption.fontSize,
                text = "${idx}. "
            )
            Text(
                text = "${sets.repetition} rep / ${sets.weight.removeDecimal()} ${sets.unit.name.lowercase()}",
                fontSize = MaterialTheme.typography.subtitle2.fontSize,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}