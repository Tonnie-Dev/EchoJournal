package com.tonyxlab.echojournal.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tonyxlab.echojournal.presentation.screens.editor.EditorScreen
import com.tonyxlab.echojournal.presentation.screens.entry.HomeScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.appDestinations(navController: NavController) {

    composable<HomeScreenObject> {

        HomeScreen(
            onClickEcho = {
                navController.navigate(route = SaveScreenObject(it))
            },
            navigateToSaveScreen = { navController.navigate(route = SaveScreenObject()) },
        )
    }

    composable<SaveScreenObject> {

        EditorScreen(
            onPresBack = { navController.popBackStack() },
            onCancelEditor = { navController.navigateUp() },
            onSaveEditor = {navController.navigate(route = HomeScreenObject) }
        )

    }

}

@Serializable
data object HomeScreenObject

@Serializable
data class SaveScreenObject(val id: String? = null)