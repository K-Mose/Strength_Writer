package com.example.strengthwriter.presentation.item

import android.app.DatePickerDialog
import android.icu.util.Calendar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.strengthwriter.R
import com.example.strengthwriter.data.model.Workout
import com.example.strengthwriter.navigation.Screens
import com.example.strengthwriter.presentation.calculator.CalDetail
import com.example.strengthwriter.presentation.components.ActionItem
import com.example.strengthwriter.presentation.components.MyAppBar
import com.example.strengthwriter.presentation.components.WorkoutCard
import com.example.strengthwriter.presentation.components.WorkoutItem
import com.example.strengthwriter.presentation.viewmodel.DetailViewModel
import com.example.strengthwriter.ui.theme.PADDING_MEDIUM
import com.example.strengthwriter.ui.theme.PADDING_SMALL
import com.example.strengthwriter.ui.theme.SPACER_LARGE_WIDTH
import com.example.strengthwriter.utils.RequestState
import com.example.strengthwriter.utils.Utils.toFormattedString
import java.util.*

@Composable
fun DetailScreen(
    screen: Screens,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    val workoutPopup = rememberSaveable { mutableStateOf(false) }
    val title by detailViewModel.title
    val date by detailViewModel.date
    val workoutState by detailViewModel.workoutListState
    val workoutList = when(workoutState) {
        is RequestState.Success -> (workoutState as RequestState.Success<List<Workout>>).data
        is RequestState.Loading -> (workoutState as RequestState.Loading<List<Workout>>).data
        else -> listOf()
    }
    Scaffold(
        topBar = {
            MyAppBar(
                title = stringResource(R.string.daily_mission),
                isNavigation = true,
                navigationIcon = ActionItem(
                    icon = Icons.Filled.ArrowBack,
                    onClick = screen.list
                ),
                isActionButton = true,
                actionIcon = ActionItem(
                    icon = Icons.Filled.Check,
                    onClick = {
                        detailViewModel.addMission()
                        screen.list()
                    }
                )
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
                loadExercise = { }
            )
            Surface(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Blue)
            ) {
                LazyColumn {
                    itemsIndexed(workoutList) { index, workout ->
                        WorkoutCard(workout = workout)
                    }
                }
            }
        }
        if (workoutPopup.value) {
            WorkoutPopup(
                navigateTo = { workoutPopup.value = false},
                popupReturn = { workout -> detailViewModel.addWorkout(workout)}
            )
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
    popupReturn: (Workout) -> Unit
) {
    Popup(
        properties = PopupProperties(
            focusable = true,
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            clippingEnabled = false
        ),
        onDismissRequest = { navigateTo()}
    ) {
        CalDetail(
            navigateTo = navigateTo,
            popupReturn = popupReturn,
            isPopup = true
        )
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
        LazyColumn {
            items(workoutList) { workout ->
                Surface(
                    onClick = {
                        itemClickListener(workout)
                    }
                ) {
                    WorkoutItem(workout = workout)
                }
            }
        }
    }
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