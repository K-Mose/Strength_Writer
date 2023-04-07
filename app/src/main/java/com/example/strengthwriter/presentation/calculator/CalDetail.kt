package com.example.strengthwriter.presentation.calculator

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.strengthwriter.R
import com.example.strengthwriter.data.model.Workout
import com.example.strengthwriter.presentation.components.DisplayAlertDialog
import com.example.strengthwriter.presentation.components.ExerciseDropDown
import com.example.strengthwriter.presentation.item.CloseBox
import com.example.strengthwriter.presentation.viewmodel.CalViewModel
import com.example.strengthwriter.presentation.viewmodel._Sets
import com.example.strengthwriter.ui.theme.*
import com.example.strengthwriter.utils.Exercise
import com.example.strengthwriter.utils.RequestState
import com.example.strengthwriter.utils.Units
import com.example.strengthwriter.utils.Units.LBS
import kotlinx.coroutines.launch

@Preview
@Composable
private fun CalDetailPreview() {
//    CalDetail()
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,
    ) {
    DetailItem(
        sets = getEmptySets(LBS),
        index = 0,
        updateReps = { _, _ -> },
        updateWeight = { _, _ -> },
        updateRatio = { _, _ -> },
        removeItem = { _ -> },
        focusList = listOf()
    )
    DetailItem(
        sets = getEmptySets(LBS),
        index = 0,
        updateReps = { _, _ -> },
        updateWeight = { _, _ -> },
        updateRatio = { _, _ -> },
        removeItem = { _ -> },
        focusList = listOf()
    )
    }
}

@Composable
fun CalDetail(
    navigateTo: () -> Unit,
    isPopup: Boolean = false,
    popupReturn: (Workout) -> Unit = {},
    unit: Units = Units.LBS,
    calViewModel: CalViewModel = hiltViewModel()
) {
    val setsState: RequestState<List<_Sets>> by calViewModel.setsState.collectAsState()
    val memo by calViewModel.workoutMemo
    val exercise by calViewModel.exercise
    LaunchedEffect(key1 = true) {
        calViewModel.initList()
    }
    val setsList: List<_Sets> = when (setsState) {
        is RequestState.Success -> (setsState as RequestState.Success<List<_Sets>>).data
        is RequestState.Loading -> (setsState as RequestState.Loading<List<_Sets>>).data
        else -> emptyList<_Sets>()
    }

    val focusList = calViewModel.focusList
    
    val openDialog = remember { mutableStateOf(false) }

    Calculator(
        workoutMemo = memo,
        navigateTo = navigateTo,
        setWorkoutMemo = {
            calViewModel.setWorkoutMemo(it)
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
            calViewModel.addFocusListItem()
            calViewModel.addSets(getEmptySets(unit = unit))
        },
        addExercise = {
            if (!isPopup)
                calViewModel.saveExercise()
            else
                popupReturn(calViewModel.getWorkout())
            navigateTo()
        },
        removeItem = { index ->
            calViewModel.removeFocusListItem()
            calViewModel.removeSets(index)
        },
        focusList = focusList,
        openDialog = { openDialog.value = true}
    )
    val oneRm = remember { mutableStateOf("")}
    DisplayAlertDialog(
        title = "Calculate ${unit.name}",
        body = {
            Row {
                OutlinedTextField(
                    value = oneRm.value,
                    onValueChange = { oneRm.value = it},
                    label = {
                        Text(text = "1RM")
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )
            }
        },
        openDialog = openDialog.value,
        closeDialog = { openDialog.value = false },
        confirmText = "Calc",
        confirmDialog = {
            calViewModel.calculateSets(oneRm = oneRm.value.toIntOrNull() ?: 1)
        }
    )
}

@Composable
fun Calculator(
    workoutMemo: String,
    navigateTo: () -> Unit,
    setWorkoutMemo: (String) -> Unit,
    exercise: Exercise,
    onExerciseSelected: (Exercise) -> Unit,
    setsList: List<_Sets>,
    updateReps: (Int, String) -> Unit,
    updateWeight: (Int, String) -> Unit,
    updateRatio: (Int, String) -> Unit,
    addItem: () -> Unit,
    removeItem: (Int) -> Unit,
    addExercise: () -> Unit,
    focusList: List<FocusRequester>,
    openDialog: () -> Unit
) {
    val focusManager = LocalFocusManager.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(
                horizontal = PADDING_EXTRA_LARGE,
                vertical = PADDING_SMALL
            )
            .pointerInput(Unit) {
                detectTapGestures {
                    focusManager.clearFocus()
                }
            }
    ) {
        CloseBox { navigateTo()}
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

            OutlinedButton(onClick = {
                addExercise()
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(id = R.string.add_workout))
                Text(text = "ADD EXERCISE")
            }
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(vertical = PADDING_SMALL)
        ) {
            OutlinedTextField(
                value = workoutMemo,
                onValueChange = { setWorkoutMemo(it)},
                label = {
                    Text(text = "Memo")
                }
            )
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
        if (setsList.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ICONS_SIZE_LARGE),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = {
                    openDialog()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_calculate_24),
                        contentDescription = stringResource(R.string.calculator_icon)
                    )
                }
            }
        }
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 1.dp)
        ) {
            DetailList(
                setsList = setsList,
                updateReps = updateReps,
                updateWeight = updateWeight,
                updateRatio = updateRatio,
                removeItem = removeItem,
                focusManager = focusManager,
                focusList = focusList
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
    focusManager:FocusManager,
    focusList: List<FocusRequester>
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
                removeItem = removeItem,
                focusList = focusList,
                focusManager = focusManager,
                isLast = (index+1) == setsList.size
            )
        }
    }
    Log.d("CALCULATOR::DetailList", "${focusList.size} $focusList")
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalFoundationApi::class)
@Composable
private fun DetailItem(
    index: Int,
    sets: _Sets,
    updateReps: (Int, String) -> Unit,
    updateWeight: (Int, String) -> Unit,
    updateRatio: (Int, String) -> Unit,
    removeItem: (Int) -> Unit,
    focusList:List<FocusRequester>,
    focusManager: FocusManager = LocalFocusManager.current,
    isLast: Boolean = false
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Log.d("CALCULATOR::DetailItem", "$sets / $index")
    val reps = sets.repetition
    val weight = sets.weight
    val ratio = sets.ratio
    val weightFocus = FocusRequester()
    val ratioFocus = FocusRequester()

    val coroutineScope = rememberCoroutineScope()
    val bringIntoViewRequester = remember { BringIntoViewRequester()}

    Card(
//        modifier = Modifier.bringIntoViewRequester(bringIntoViewRequester)
    ) {
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
                    .background(Color.White)
                    .focusRequester(focusList[index])
                    .focusProperties {
                        next = weightFocus
                    }
                    .onFocusEvent { focusState ->
                        if (focusState.isFocused)
                            coroutineScope.launch {
                                bringIntoViewRequester.bringIntoView()
                            }
                    }
                    .bringIntoViewRequester(bringIntoViewRequester)
                ,
                value = when (reps) {
                    "0" -> ""
                    else -> reps
                },
                onValueChange = { reps -> updateReps(index, reps) },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(onNext = {
                    focusManager.moveFocus(FocusDirection.Next)
                }),
                label = { Text(
                    text = "Reps",
                    fontSize = MaterialTheme.typography.caption.fontSize
                )}
            )
            Spacer(modifier = Modifier.width(SPACER_SMALL_WIDTH))
            OutlinedTextField(
                modifier = Modifier
                    .width(100.dp)
                    .focusRequester(weightFocus)
                    .focusProperties {
                        next = ratioFocus
                    }
                    .onFocusEvent { focusState ->
                        if (focusState.isFocused)
                            coroutineScope.launch {
                                bringIntoViewRequester.bringIntoView()
                            }
                    }
                    .bringIntoViewRequester(bringIntoViewRequester)
                ,
                value = when (weight) {
                    "0.0" -> ""
                    else -> weight
                },
                onValueChange = { weight -> updateWeight(index, weight)},
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                label = {
                    Text(
                        text = "Weight",
                        fontSize = MaterialTheme.typography.caption.fontSize
                    )
                }
            )
            Spacer(modifier = Modifier.width(SPACER_SMALL_WIDTH))
            OutlinedTextField(
                modifier = Modifier
                    .width(100.dp)
                    .focusRequester(ratioFocus)
                    .focusProperties {
                        next = focusList[if (!isLast) index + 1 else index]
                    }
                    .onFocusEvent { focusState ->
                        if (focusState.isFocused)
                            coroutineScope.launch {
                                bringIntoViewRequester.bringIntoView()
                            }
                    }
                    .bringIntoViewRequester(bringIntoViewRequester)
                ,
                value = when (ratio) {
                    "0" -> ""
                    else -> ratio
                },
                onValueChange = { ratio -> updateRatio(index, ratio)},
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = if(isLast) ImeAction.Done else ImeAction.Next
                ),
                keyboardActions = KeyboardActions (
                        onDone = {
                            focusManager.clearFocus()
                            keyboardController?.hide()
                        }
                    ),
                label = {
                    Text(
                        text = "Ratio",
                        fontSize = MaterialTheme.typography.caption.fontSize
                    )
                },
            )
            Spacer(modifier = Modifier.width(SPACER_SMALL_WIDTH))
            Surface(
                modifier = Modifier
                    .size(ICONS_SIZE_MEDIUM)
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

fun getEmptySets(unit: Units): _Sets = _Sets(
    weight = "",
    ratio = "",
    units = unit,
    repetition = ""
)