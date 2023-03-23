package com.example.strengthwriter.navigation

import androidx.navigation.NavHostController
import androidx.navigation.navArgument
import com.example.strengthwriter.utils.Constants.CALCULATOR_SCREEN
import com.example.strengthwriter.utils.Constants.CAL_DETAIL_SCREEN
import com.example.strengthwriter.utils.Constants.LIST_SCREEN

class Screens(private val navController: NavHostController) {
    val list: () -> Unit = {
        navController.navigate(route = "list/{action}") {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }

    val detail: (Int) -> Unit = { id ->
        navController.navigate(route = "detail/${id}") {
            popUpTo("list") {
                navArgument("id") {
                    inclusive = true
                }
            }
        }
    }

    val calculator: () -> Unit = {
        navController.navigate(route = CALCULATOR_SCREEN) {
            // clear backstack - https://medium.com/@banmarkovic/jetpack-compose-clear-back-stack-popbackstack-inclusive-explained-14ee73a29df5
            /*navController.popBackStack(
                route = LIST_SCREEN,
                inclusive = true
            )
            // or
            popUpTo(navController.graph.id) {
                inclusive = true
            }*/
            popUpTo(LIST_SCREEN) {
                // list screen을 popup에 포함할지여부
                inclusive = false
            }
        }
    }

    val calDetail: (Int) -> Unit = { id ->
        navController.navigate(route = "cal_detail/${id}") {
            popUpTo(CALCULATOR_SCREEN) {
                inclusive = false
            }
        }
    }

    val drawerSelector: (String) -> Unit = { route ->
        when (route) {
            "list" -> list()
            "calculator" -> calculator()
            else -> list()
        }
    }
}