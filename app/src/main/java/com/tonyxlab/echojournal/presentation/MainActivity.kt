@file:RequiresApi(Build.VERSION_CODES.O)

package com.tonyxlab.echojournal.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.tonyxlab.echojournal.navigation.HomeRouteObject
import com.tonyxlab.echojournal.navigation.appDestinations
import com.tonyxlab.echojournal.presentation.core.utils.spacing
import com.tonyxlab.echojournal.presentation.theme.EchoJournalTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var keepSplashScreen = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        savedInstanceState?.let {
            keepSplashScreen = it.getBoolean(SPLASH_SCREEN_KEY)
        }
        installSplashScreen().apply {
            setKeepOnScreenCondition { keepSplashScreen }
        }
        enableEdgeToEdge()

        setContent {
            EchoJournalTheme {
                val navController = rememberNavController()
                val horizontalPadding = MaterialTheme.spacing.spaceMedium

                NavHost(
                    navController = navController,
                    startDestination = HomeRouteObject,
                ) {
                    appDestinations(
                        modifier = Modifier.padding(horizontal = horizontalPadding),
                        navController = navController,
                        isDataLoaded = { keepSplashScreen = false },
                        isLaunchedFromWidget = false,
                    )
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(SPLASH_SCREEN_KEY, keepSplashScreen)
    }

    companion object {
        private const val SPLASH_SCREEN_KEY = "splash_screen_key"
    }
}
