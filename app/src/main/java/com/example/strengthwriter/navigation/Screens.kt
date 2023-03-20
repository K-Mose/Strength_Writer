package com.example.strengthwriter.navigation

import androidx.navigation.NavHostController
import androidx.navigation.navArgument

class Screens(private val navController: NavHostController) {
    val list: () -> Unit = {
        navController.navigate(route = "list")
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
}