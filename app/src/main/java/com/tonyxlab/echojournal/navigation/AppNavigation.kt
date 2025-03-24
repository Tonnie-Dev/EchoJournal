package com.tonyxlab.echojournal.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.tonyxlab.echojournal.presentation.screens.editor.EditorScreen
import com.tonyxlab.echojournal.presentation.screens.editor.EditorScreenRoot
import com.tonyxlab.echojournal.presentation.screens.home.HomeScreenRoot
import com.tonyxlab.echojournal.utils.Constants
import kotlinx.serialization.Serializable

fun NavGraphBuilder.appDestinations(
    navController: NavController, isDataLoaded: () -> Unit, isLaunchedFromWidget: Boolean
) {

    composable<HomeRouteObject> {

        HomeScreenRoot(
            isDataLoaded = isDataLoaded,
            isLaunchedFromWidget = isLaunchedFromWidget,
            navigateToEditorScreen = { navController.navigate(EditorRouteObject(audioFilePath = it)) },
            navigateToSettingScreen = { navController.navigate(SettingsRouteObject) })
    }

    composable<EditorRouteObject> { navBackStackEntry ->

        val args = navBackStackEntry.toRoute<EditorRouteObject>()
        EditorScreenRoot(
            echoId = args.id,
            audioFilePath = args.audioFilePath,
            navigateBack = { navController.popBackStack() })


    }

}

@Serializable
data object HomeRouteObject

@Serializable
data object SettingsRouteObject

@Serializable
data class EditorRouteObject(val id: Long = Constants.UNDEFINED_ECHO_ID, val audioFilePath: String)



