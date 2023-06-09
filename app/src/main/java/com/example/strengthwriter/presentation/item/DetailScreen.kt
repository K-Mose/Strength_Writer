package com.example.strengthwriter.presentation.item

import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.strengthwriter.R
import com.example.strengthwriter.data.model.Workout
import com.example.strengthwriter.navigation.Screens
import com.example.strengthwriter.presentation.calculator.CalDetail
import com.example.strengthwriter.presentation.components.*
import com.example.strengthwriter.presentation.viewmodel.DetailViewModel
import com.example.strengthwriter.ui.theme.*
import com.example.strengthwriter.utils.RequestState
import com.example.strengthwriter.utils.Units
import com.example.strengthwriter.utils.Utils.toFormattedString
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailScreen(
    screen: Screens,
    detailViewModel: DetailViewModel
) {
    val workoutPopup = rememberSaveable { mutableStateOf(false) }
    val loadPopup = rememberSaveable { mutableStateOf(false) }
    val title by detailViewModel.title
    val date by detailViewModel.date
    val unit by detailViewModel.unit
    val missionId by detailViewModel.missionId
    val workoutState by detailViewModel.workoutListState
    val workoutList = when(workoutState) {
        is RequestState.Success -> (workoutState as RequestState.Success<List<Workout>>).data
        is RequestState.Loading -> (workoutState as RequestState.Loading<List<Workout>>).data
        else -> listOf()
    }

    val loadedWorkoutState by detailViewModel.loadedWorkoutState.collectAsState()
    val loadedWorkoutList = when(loadedWorkoutState) {
        is RequestState.Success -> (loadedWorkoutState as RequestState.Success<List<Workout>>).data
        is RequestState.Loading -> (loadedWorkoutState as RequestState.Loading<List<Workout>>).data
        else -> listOf()
    }

    val openRemoveDialog = remember { mutableStateOf(false) }
    val selectedWorkoutIndex = remember { mutableStateOf(-1) }

    val context: Context = LocalContext.current

    val focusManager = LocalFocusManager.current

    val expanded = remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures { focusManager.clearFocus() }
        },
        topBar = {
            MyAppBar(
                title = stringResource(R.string.daily_mission),
                isNavigation = true,
                navigationIcon = ActionItem(
                    icon = Icons.Filled.ArrowBack,
                    onClick = screen.list
                ),
                isActionButton = true,
                actionIcon = if (missionId > 0) {
                    ActionItem(
                        icon = Icons.Default.MoreVert,
                        onClick = { expanded.value = true }
                    )
                } else {
                       ActionItem(
                           icon = Icons.Default.Check,
                           onClick = {
                               if (detailViewModel.validateInputData()) {
                                   detailViewModel.addMission()
                                   screen.list
                               } else {
                                   displayToast(context)
                               }
                           }
                       )
               },
                iconBody = {
                    if (missionId > 0) {
                        DetailMenu(
                            expanded = expanded.value,
                            onDismissRequest = { expanded.value = false },
                            updateAction = {
                                if (detailViewModel.validateInputData()) {
                                    detailViewModel.updateMission()
                                    screen.list()
                                }  else {
                                    displayToast(context)
                                }
                            },
                            convertAction = {
                                detailViewModel.convertUnit()
                            },
                            deleteAction = {
                                screen.list()
                                detailViewModel.removeDailyMission()
                            },
                        )
                    }
                }
            )
        }
    ) {
        it
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = PADDING_MEDIUM),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Heading(
                date = date,
                updateDate = detailViewModel::updateDate,
                title = title,
                updateTitle = detailViewModel::updateTitle,
                addExercise = { workoutPopup.value = true},
                loadExercise = {
                    detailViewModel.loadAllWorkout()
                    loadPopup.value = true
                }
            )
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Blue)
            ) {
                LazyColumn {
                    itemsIndexed(workoutList) { index, workout ->
                        Surface(
                            modifier = Modifier.combinedClickable(
                                onClick = {},
                                onLongClick = {
                                    selectedWorkoutIndex.value = index
                                    openRemoveDialog.value = true
                                }
                            )
                        ) {
                            WorkoutCard(workout = workout)
                        }
                    }
                }
            }
        }
        if (workoutPopup.value) {
            WorkoutPopup(
                navigateTo = { workoutPopup.value = false},
                popupReturn = { workout -> detailViewModel.addWorkout(workout)},
                unit = unit
            )
        }
        if (loadPopup.value) {
            Log.d("CALCULATOR::loadedWorkoutList", "$loadedWorkoutList")
            LoadPopup(
                workoutList = loadedWorkoutList,
                itemClickListener = { workout ->
                    detailViewModel.addWorkout(workout)
                    loadPopup.value = false
                },
                closePopup = { loadPopup.value = false}
            )
        }
    }

    DisplayAlertDialog(
        title = "Delete Workout",
        body = { Text("Are you sure remove this workout?")},
        openDialog = openRemoveDialog.value,
        closeDialog = {
            selectedWorkoutIndex.value = -1
            openRemoveDialog.value = false
        }
    ) {
        detailViewModel.removeWorkout(selectedWorkoutIndex.value)
        selectedWorkoutIndex.value = -1
        openRemoveDialog.value = false
    }
}

@Composable
fun DetailMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    updateAction: () -> Unit,
    convertAction: () -> Unit,
    deleteAction: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismissRequest() }
    ) {
        DropdownMenuItem(onClick = {
            onDismissRequest()
            updateAction()
        }) {
            Text(text = "Save")
        }
        DropdownMenuItem(onClick = {
            onDismissRequest()
            convertAction()
        }) {
            Text(text = "Convert Unit")
        }
        DropdownMenuItem(onClick = {
            onDismissRequest()
            deleteAction()
        }) {
            Text(text = "Delete")
        }
    }
}

@Composable
fun Heading(
    date: String,
    updateDate: (String) -> Unit,
    title: String,
    updateTitle: (String) -> Unit,
    addExercise: () -> Unit,
    loadExercise: () -> Unit
) {
    val context = LocalContext.current

    val calendar = Calendar.getInstance()
    val y = calendar.get(Calendar.YEAR)
    val m = calendar.get(Calendar.MONTH)
    val d = calendar.get(Calendar.DAY_OF_MONTH)

    LaunchedEffect(true) {
        calendar.time = Date() // 현재 날짜로 설정?
        updateDate(calendar.time.toFormattedString())
    }

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, day ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, day)
            updateDate(calendar.time.toFormattedString())
        }, y, m, d,

    )

    Column {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
            value = title,
            onValueChange = {
                updateTitle(it)
            },
            label = {
                Text(text = stringResource(id = R.string.title))
            }
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = PADDING_SMALL)
                .background(Color.White),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(onClick = { datePickerDialog.show() }) {
                Text(
                    text = date,
                    fontSize = 24.sp,
                    color = Color.Black
                )
            }

            Row(
                modifier = Modifier.padding(horizontal = PADDING_MEDIUM),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = MaterialTheme.shapes.small,
                    elevation = 5.dp,
                ) {
                    IconButton(
                        modifier = Modifier
                            .size(36.dp)
                            .background(color = Color.White),
                        onClick = {
                            Log.d("CALCULATOR::DetailsScreen", "LOAD EXERCISE")
                            loadExercise()
                        }
                    ) {
                        Icon(painter = painterResource(id = R.drawable.ic_get_24), contentDescription = "Load Workout Data")
                    }
                }
                Spacer(modifier = Modifier.width(width = SPACER_LARGE_WIDTH))
                Surface(
                    shape = MaterialTheme.shapes.small,
                    elevation = 5.dp,
                ) {
                    IconButton(
                        modifier = Modifier
                            .size(36.dp)
                            .background(color = Color.White),
                        onClick = {
                            addExercise()
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = stringResource(id = R.string.add_workout))
                    }
                }
            }
        }
    }
}

@Composable
fun WorkoutPopup(
    navigateTo: () -> Unit,
    popupReturn: (Workout) -> Unit,
    unit: Units
) {
    Popup(
        properties = PopupProperties(
            focusable = true,
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        onDismissRequest = { navigateTo()}
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(all = PADDING_MEDIUM)
        ) {
            CalDetail(
                navigateTo = navigateTo,
                popupReturn = popupReturn,
                isPopup = true,
                unit = unit
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoadPopup(
    workoutList: List<Workout>,
    itemClickListener: (Workout) -> Unit,
    closePopup: () -> Unit
) {
    Popup(
        properties = PopupProperties(
            focusable = true,
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        ),
        onDismissRequest = { closePopup() }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(all = PADDING_MEDIUM)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                item {
                    CloseBox { closePopup()}
                }
                items(workoutList) { workout ->
                    Surface(
                        onClick = {
                            itemClickListener(workout)
                        }
                    ) {
                        WorkoutCard(workout = workout)
                    }
                }
            }
        }
    }
}

@Composable
fun CloseBox(
    closePopup: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(BOX_SMALL_HEIGHT)
    )
    {
        Surface(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(ICONS_SIZE_LARGE)
                .clickable {
                    closePopup()
                }
        ) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(id = R.string.close_icon)
            )
        }
    }
}

fun displayToast(context: Context) {
    Toast.makeText(
        context,
        "Some Field is Empty",
        Toast.LENGTH_SHORT
    ).show()
}

@Preview
@Composable
fun HeadingPreview() {
    Heading(
        title = "title",
        updateTitle = {},
        date = "20220301",
        updateDate = {},
        addExercise = {},
        loadExercise = {}
    )
}

