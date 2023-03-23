package com.example.strengthwriter.presentation.components

import androidx.compose.material.DrawerValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.res.stringResource
import com.example.strengthwriter.R
import com.example.strengthwriter.navigation.Screens
import kotlinx.coroutines.launch

@Composable
fun HomeScaffold(
    screen: Screens,
    title: String = stringResource(id = R.string.app_name),
    content: @Composable () -> Unit,
    fabClickListener: (Int) -> Unit
 ) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scaffoldState = rememberScaffoldState(drawerState = drawerState)
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            MyAppBar(
                title = title
            ) {
                coroutineScope.launch {
                    scaffoldState.drawerState.open()
                }
            }
        },
        drawerContent = {
            DrawerContent(
                itemClickListener = screen.drawerSelector
            ) {
                coroutineScope.launch {
                    scaffoldState.drawerState.close()
                }
            }
        },
        content = {
            it
            content()
        },
        floatingActionButton = {
            Fab(navigationToNewWorkout = fabClickListener)
        }
    )
}