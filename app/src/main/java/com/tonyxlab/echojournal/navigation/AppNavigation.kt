package com.tonyxlab.echojournal.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.tonyxlab.echojournal.presentation.core.utils.spacing
import com.tonyxlab.echojournal.presentation.screens.editor.EditorScreen
import com.tonyxlab.echojournal.presentation.screens.editor.EditorScreenRoot
import com.tonyxlab.echojournal.presentation.screens.home.HomeScreenRoot
import com.tonyxlab.echojournal.presentation.screens.home.HomeViewModel
import com.tonyxlab.echojournal.utils.Constants
import kotlinx.serialization.Serializable
import timber.log.Timber

fun NavGraphBuilder.appDestinations(
    navController: NavController,
    isDataLoaded: () -> Unit,
    isLaunchedFromWidget: Boolean
) {

    composable<HomeRouteObject> {backStackEntry ->

        val parentEntry = remember(backStackEntry){
            navController.getBackStackEntry<HomeRouteObject>()
        }

        val viewModel: HomeViewModel = hiltViewModel(parentEntry)
        HomeScreenRoot(
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.spaceMedium),
            isDataLoaded = isDataLoaded,
            isLaunchedFromWidget = isLaunchedFromWidget,
            viewModel = viewModel,
            navigateToEditorScreen = {

                navController.navigate(EditorRouteObject(audioFilePath = it))
            },
            navigateToSettingScreen = { navController.navigate(SettingsRouteObject) })
    }

    composable<EditorRouteObject> { navBackStackEntry ->

        val args = navBackStackEntry.toRoute<EditorRouteObject>()


        EditorScreenRoot(
            echoId = args.id,
            audioFilePath = args.audioFilePath,
            navigateBack = { navController.navigate(HomeRouteObject) },
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.spaceMedium),
        )


    }

}

@Serializable
data object HomeRouteObject

@Serializable
data object SettingsRouteObject

@Serializable
data class EditorRouteObject(val id: Long = Constants.UNDEFINED_ECHO_ID, val audioFilePath: String)



