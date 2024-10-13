package com.android.jimmy.motus.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

private const val splashDelay: Long = 3000

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    valid: Boolean?,
    onSplashEndedValid: () -> Unit,
    onSplashEndedInvalid: () -> Unit
) {
    val currentValid = rememberUpdatedState(newValue = valid)

    LaunchedEffect(key1 = null) {
        delay(splashDelay)
        if (currentValid.value == true) {
            onSplashEndedValid()
        } else {
            onSplashEndedInvalid()
        }
    }
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}
