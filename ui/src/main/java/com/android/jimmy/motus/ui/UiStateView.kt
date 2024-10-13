package com.android.jimmy.motus.ui

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.jimmy.motus.domain.model.Character
import com.android.jimmy.motus.ui.model.UiState

@Composable
fun UIStateView(
    state: State<UiState<List<Character>>>,
    content: @Composable () -> Unit,
) {
    when (val value = state.value) {
        is UiState.Failure -> UIFailure(value.errorMsg)

        is UiState.Loading -> UILoading()

        is UiState.Success -> content()
    }
}

@Composable
@Preview
private fun UILoading() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
@Preview
private fun UIFailure(errorMsg: String = "") {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = errorMsg,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary,
                textAlign = TextAlign.Center,
            )
        }
    }
}