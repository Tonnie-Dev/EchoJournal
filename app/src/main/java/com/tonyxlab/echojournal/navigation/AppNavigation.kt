package com.tonyxlab.echojournal.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tonyxlab.echojournal.presentation.screens.editor.EditorScreen
import com.tonyxlab.echojournal.presentation.screens.home.HomeScreenRoot
import kotlinx.serialization.Serializable

fun NavGraphBuilder.appDestinations(
    navController: NavController, isDataLoaded: () -> Unit, isLaunchedFromWidget: Boolean
) {

    composable<HomeRouteObject> {

        HomeScreenRoot(
            isDataLoaded = isDataLoaded,
            isLaunchedFromWidget = isLaunchedFromWidget,
            navigateToEditorScreen = { navController.navigate(EditorRouteObject(audioFilePath = it, id = "ss")) },
            navigateToSettingScreen = { navController.navigate(SettingsRouteObject) })
    }

    composable<EditorRouteObject> {

        EditorScreen(
            onPresBack = { navController.popBackStack() },
            onCancelEditor = { navController.navigateUp() },
            onSaveEditor = { })

    }

}

@Serializable
data object HomeRouteObject

@Serializable
data object SettingsRouteObject

@Serializable
data class EditorRouteObject(val id: String? = null, val audioFilePath: String)



