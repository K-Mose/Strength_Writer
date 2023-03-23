package com.example.strengthwriter.presentation.calculator

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.strengthwriter.R
import com.example.strengthwriter.data.model.Sets
import com.example.strengthwriter.navigation.Screens
import com.example.strengthwriter.presentation.components.ExerciseDropDown
import com.example.strengthwriter.presentation.viewmodel.CalViewModel
import com.example.strengthwriter.presentation.viewmodel._Sets
import com.example.strengthwriter.ui.theme.PADDING_EXTRA_LARGE
import com.example.strengthwriter.ui.theme.PADDING_MEDIUM
import com.example.strengthwriter.ui.theme.SPACER_WIDTH
import com.example.strengthwriter.utils.Exercise
import com.example.strengthwriter.utils.RequestState
import com.example.strengthwriter.utils.Unit.LBS
import com.example.strengthwriter.utils.Utils.removeDecimal

@Preview
@Composable
private fun CalDetailPreview() {
//    CalDetail()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,
    ) {
    DetailItem(
        sets = getEmptySets(),
        index = 0,
        updateReps = { _, _ -> },
        updateWeight = { _, _ -> },
        updateRatio = { _, _ -> },
        removeItem = { _ -> },
    )
    DetailItem(
        sets = getEmptySets(),
        index = 0,
        updateReps = { _, _ -> },
        updateWeight = { _, _ -> },
        updateRatio = { _, _ -> },
        removeItem = { _ -> },
    )
    }
}

@Composable
fun CalDetail(
        screen: Screens? = null,
        calViewModel: CalViewModel = hiltViewModel()
) {
    val setsState: RequestState<List<_Sets>> by calViewModel.setsState.collectAsState()
    val name by calViewModel.workoutName
    val exercise by calViewModel.exercise

    val setsList: List<_Sets> = when {
        setsState is RequestState.Success -> (setsState as RequestState.Success<List<_Sets>>).data
        setsState is RequestState.Loading -> (setsState as RequestState.Loading<List<_Sets>>).data
        else -> emptyList<_Sets>()
    }

    Calculator(
        workoutName = name,
        setWorkoutName = {
            calViewModel.setWorkoutName(it)
        },
        exercise = exercise,
        onExerciseSelected = { calViewModel.exercise.value = it},
        setsList = setsList,
        updateReps = { index, reps ->
            calViewModel.updateReps(index, reps)
        },
        updateWeight = { index, weight ->
            calViewModel.updateWeight(index, weight)
        },
        updateRatio = { index, ratio ->
            calViewModel.updateRatio(index, ratio)
        },
        addItem = {
            calViewModel.addSets(getEmptySets())
        },
        addExercise = {
            calViewModel.saveExercise()
        },
        removeItem = { index ->
            calViewModel.removeSets(index)
        }
    )
}

@Composable
fun Calculator(
    workoutName: String,
    setWorkoutName: (String) -> Unit,
    exercise: Exercise,
    onExerciseSelected: (Exercise) -> Unit,
    setsList: List<_Sets>,
    updateReps: (Int, String) -> Unit,
    updateWeight: (Int, String) -> Unit,
    updateRatio: (Int, String) -> Unit,
    addItem: () -> Unit,
    removeItem: (Int) -> Unit,
    addExercise: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = PADDING_EXTRA_LARGE)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Workout",
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            )

            OutlinedButton(onClick = { addExercise() }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(id = R.string.add_workout))
                Text(text = "ADD EXERCISE")
            }
        }

        Row (
            verticalAlignment = Alignment.CenterVertically
        ) {
            val text = remember { mutableStateOf("") }
            Box(modifier = Modifier.weight(4f)) {
                ExerciseDropDown(
                    exercise = exercise,
                    onExerciseSelected = { onExerciseSelected(it)}
                )
            }
            Box(
                modifier = Modifier
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                IconButton(
                    modifier = Modifier
                        .border(width = 1.dp, color = Color.Red, shape = RoundedCornerShape(100f)),
                    onClick = { addItem() }
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(id = R.string.add_sets))
                }
            }
        }
        Surface(
            modifier = Modifier.fillMaxSize()
        ) {
            DetailList(
                setsList = setsList,
                updateReps = updateReps,
                updateWeight = updateWeight,
                updateRatio = updateRatio,
                removeItem = removeItem
            )
        }
    }
}

@Composable
private fun DetailList(
    setsList: List<_Sets> = listOf(),
    updateReps: (Int, String) -> Unit,
    updateWeight: (Int, String) -> Unit,
    updateRatio: (Int, String) -> Unit,
    removeItem: (Int) -> Unit,
) {
    Log.d("CALCULATOR::DetailList", "$setsList")
    LazyColumn {
        itemsIndexed(setsList) {index, sets ->
            DetailItem(
                index = index,
                sets = sets,
                updateReps = updateReps,
                updateWeight = updateWeight,
                updateRatio = updateRatio,
                removeItem = removeItem
            )
        }
    }
}

@Composable
private fun DetailItem(
    index: Int,
    sets: _Sets,
    updateReps: (Int, String) -> Unit,
    updateWeight: (Int, String) -> Unit,
    updateRatio: (Int, String) -> Unit,
    removeItem: (Int) -> Unit,
) {
    Log.d("CALCULATOR::DetailItem", "$sets / $index")
    val reps = sets.repetition
    val weight = sets.weight
    val ratio = sets.ratio
    Card() {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .width(100.dp)
                    .background(Color.White),
                value = when (reps) {
                    "0" -> ""
                    else -> reps
                },
                onValueChange = { reps -> updateReps(index, reps) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                label = { Text(
                    text = "Reps",
                    fontSize = MaterialTheme.typography.caption.fontSize
                )}
            )
            Spacer(modifier = Modifier.width(SPACER_WIDTH))
            OutlinedTextField(
                modifier = Modifier
                    .width(100.dp),
                value = when (weight) {
                    "0.0" -> ""
                    else -> weight
                },
                onValueChange = { weight -> updateWeight(index, weight)},
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                label = {
                    Text(
                        text = "Weight",
                        fontSize = MaterialTheme.typography.caption.fontSize
                    )
                }
            )
            Spacer(modifier = Modifier.width(SPACER_WIDTH))
            OutlinedTextField(
                modifier = Modifier
                    .width(100.dp),
                value = when (ratio) {
                    "0" -> ""
                    else -> ratio
                },
                onValueChange = { ratio -> updateRatio(index, ratio)},
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal
                ),
                label = {
                    Text(
                        text = "Ratio",
                        fontSize = MaterialTheme.typography.caption.fontSize
                    )
                },
            )
            Spacer(modifier = Modifier.width(SPACER_WIDTH))
            Surface(
                modifier = Modifier
                    .size(24.dp)
                    .clickable { removeItem(index) }
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remove Item"
                )
            }
        }
    }

}

fun getEmptySets(): _Sets = _Sets(
    weight = "",
    ratio = "",
    unit = LBS,
    repetition = ""
)