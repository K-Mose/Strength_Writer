package com.example.strengthwriter.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.strengthwriter.presentation.calculator.CalDetail
import com.example.strengthwriter.presentation.calculator.Calculator
import com.example.strengthwriter.presentation.item.DetailScreen
import com.example.strengthwriter.presentation.list.ListScreen
import com.example.strengthwriter.presentation.viewmodel.DetailViewModel
import com.example.strengthwriter.utils.Constants.CALCULATOR_SCREEN
import com.example.strengthwriter.utils.Constants.CAL_DETAIL_SCREEN
import com.example.strengthwriter.utils.Constants.LIST_SCREEN
import com.example.strengthwriter.utils.Constants.DETAIL_SCREEN

@Composable
fun Navigation(
    navController: NavHostController,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    val screen = remember(navController) {
        Screens(navController = navController)
    }
    NavHost(
        navController = navController,
        startDestination = LIST_SCREEN
    ) {
        composable(route = LIST_SCREEN) {
            ListScreen(screen = screen) { id ->
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
                screen = screen
            )
        }

        composable(
            route = CALCULATOR_SCREEN
        ) {
            Calculator(
                screen = screen
            )
        }

        composable(
            route = CAL_DETAIL_SCREEN,
            arguments = listOf(navArgument("id") {
                type = NavType.IntType
            })
        ) { backstackEntry ->
            val id = backstackEntry.arguments?.getInt("id")

            CalDetail(
                navigateTo = screen.calculator
            )
        }
    }
}