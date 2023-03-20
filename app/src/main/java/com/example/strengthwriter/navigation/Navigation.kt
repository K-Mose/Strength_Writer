package com.example.strengthwriter.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.strengthwriter.presentation.item.DetailScreen
import com.example.strengthwriter.presentation.list.ListScreen
import com.example.strengthwriter.utils.Constants.LIST_SCREEN
import com.example.strengthwriter.utils.Constants.DETAIL_SCREEN

@Composable
fun Navigation(
    navController: NavHostController
) {
    val screen = remember(navController) {
        Screens(navController = navController)
    }
    NavHost(
        navController = navController,
        startDestination = LIST_SCREEN
    ) {
        composable(route = LIST_SCREEN) {
            ListScreen { id ->
                screen.detail(id)
            }
        }

        composable(
            route = DETAIL_SCREEN,
            arguments = listOf(navArgument("id") {
                type = NavType.IntType
            })
        ) { backstackEntry ->
            DetailScreen(
                navController = navController,
            )
        }
    }
}