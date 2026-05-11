package com.example.costtracker.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.costtracker.ui.screen.add.AddItemScreen
import com.example.costtracker.ui.screen.edit.EditItemScreen
import com.example.costtracker.ui.screen.list.ItemListScreen

sealed class Screen(val route: String) {
    data object ItemList : Screen("item_list")
    data object AddItem : Screen("add_item")
    data object EditItem : Screen("edit_item/{itemId}") {
        fun createRoute(itemId: Long) = "edit_item/$itemId"
    }
}

@Composable
fun AppNavGraph(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.ItemList.route
    ) {
        composable(Screen.ItemList.route) {
            ItemListScreen(
                onAddClick = {
                    navController.navigate(Screen.AddItem.route)
                },
                onItemClick = { id ->
                    navController.navigate(Screen.EditItem.createRoute(id))
                }
            )
        }

        composable(Screen.AddItem.route) {
            AddItemScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.EditItem.route,
            arguments = listOf(
                navArgument("itemId") { type = NavType.LongType }
            )
        ) {
            EditItemScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
