package com.tonyxlab.echojournal.presentation.navigation

import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.tonyxlab.echojournal.presentation.home.HomeScreen
import com.tonyxlab.echojournal.presentation.home.HomeViewModel
import com.tonyxlab.echojournal.presentation.home.SaveScreen
import kotlinx.serialization.Serializable

fun NavGraphBuilder.appDestinations(navController: NavController) {

    navigation<NestedScreens>(startDestination = HomeScreenObject) {


        composable<HomeScreenObject> { backStackEntry ->

            val parentEntry =
                remember(backStackEntry) { navController.getBackStackEntry<NestedScreens>() }

            val viewModel: HomeViewModel = hiltViewModel(parentEntry)

            HomeScreen(

                onClickEcho = {

                    navController.navigate(route = SaveScreenObject(it))
                },
                viewModel = viewModel
            )
        }
    }


     composable<SaveScreenObject> {backstackEntry ->

         val parentEntry = remember(backstackEntry) {

             navController.getBackStackEntry<NestedScreens>()
         }

         val viewModel:HomeViewModel = hiltViewModel(parentEntry)

         SaveScreen(viewModel = viewModel)

     }

}

@Serializable
data object NestedScreens

@Serializable
data object HomeScreenObject

@Serializable
data class SaveScreenObject(val id: String? = null)