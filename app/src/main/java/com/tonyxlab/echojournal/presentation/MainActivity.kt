package com.tonyxlab.echojournal.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.tonyxlab.echojournal.presentation.navigation.NestedScreens
import com.tonyxlab.echojournal.presentation.navigation.appDestinations
import com.tonyxlab.echojournal.presentation.ui.theme.EchoJournalTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainDummyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen().apply {

            setKeepOnScreenCondition { !viewModel.isReady.value }
        }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            EchoJournalTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = NestedScreens) {

                    appDestinations(navController = navController)
                }
            }
        }
    }
}

