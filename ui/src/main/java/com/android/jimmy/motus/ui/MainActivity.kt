package com.android.jimmy.motus.ui

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.android.jimmy.motus.ui.model.UiState
import com.android.jimmy.motus.ui.screen.MainScreenCompose
import com.android.jimmy.motus.ui.screen.SplashScreen
import com.android.jimmy.motus.ui.theme.MotusTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@SuppressLint("SourceLockedOrientationActivity")
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val context = LocalContext.current
            val viewModel = hiltViewModel<MainViewModel>()

            MotusTheme(darkTheme = false) {
                NavHost(navController = navController, startDestination = "splash") {
                    composable(route = "splash") {
                        SplashScreen(
                            splashDelay = splashDelay,
                            valid = when (viewModel.state.collectAsStateWithLifecycle().value) {
                                is UiState.Success -> true
                                is UiState.Failure, is UiState.Loading -> false
                            },
                            onSplashEndedValid = {
                                navController.navigate("main") {
                                    popUpTo("splash") { inclusive = true }
                                }
                            },
                            onSplashEndedInvalid = {
                                navController.navigate("main") {
                                    popUpTo("splash") { inclusive = true }
                                }
                            }
                        )
                    }
                    composable(route = "main") {
                        MainScreenCompose(context, viewModel)
                    }
                }
            }
        }
    }

    companion object {
        private const val splashDelay: Long = 3000
    }
}
