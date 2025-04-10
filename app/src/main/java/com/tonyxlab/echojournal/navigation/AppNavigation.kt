@file:RequiresApi(Build.VERSION_CODES.O)

package com.tonyxlab.echojournal.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.tonyxlab.echojournal.presentation.screens.editor.EditorScreenRoot
import com.tonyxlab.echojournal.presentation.screens.home.HomeScreenRoot
import com.tonyxlab.echojournal.presentation.screens.home.HomeViewModel
import com.tonyxlab.echojournal.presentation.screens.settings.SettingsScreenRoot
import com.tonyxlab.echojournal.utils.Constants
import kotlinx.serialization.Serializable

fun NavGraphBuilder.appDestinations(
    navController: NavController,
    isDataLoaded: () -> Unit,
    isLaunchedFromWidget: Boolean,
    modifier: Modifier = Modifier,
) {
    composable<HomeRouteObject> { backStackEntry ->

        val parentEntry =
            remember(backStackEntry) {
                navController.getBackStackEntry<HomeRouteObject>()
            }

        val viewModel: HomeViewModel = hiltViewModel(parentEntry)

        HomeScreenRoot(
            modifier = modifier,
            isDataLoaded = isDataLoaded,
            isLaunchedFromWidget = isLaunchedFromWidget,
            viewModel = viewModel,
            navigateToEditorScreen = { arg1, arg2 ->
                navController.navigate(EditorRouteObject(audioFilePath = arg1))
            },
            navigateToSettingScreen = { navController.navigate(SettingsRouteObject) },
        )
    }

    composable<EditorRouteObject> { navBackStackEntry ->

        val args = navBackStackEntry.toRoute<EditorRouteObject>()

        EditorScreenRoot(
            modifier = modifier,
            echoId = args.id,
            audioFilePath = args.audioFilePath,
            navigateBack = { navController.navigate(HomeRouteObject) },
        )
    }

    composable<SettingsRouteObject> {
        SettingsScreenRoot(
            modifier = modifier,
            navigateToHome = { navController.navigate(HomeRouteObject) },
        )
    }
}

@Serializable
data object HomeRouteObject

@Serializable
data object SettingsRouteObject

@Serializable
data class EditorRouteObject(val id: Long = Constants.UNDEFINED_ECHO_ID, val audioFilePath: String)
