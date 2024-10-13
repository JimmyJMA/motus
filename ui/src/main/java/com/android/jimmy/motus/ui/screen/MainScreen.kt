package com.android.jimmy.motus.ui.screen

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.jimmy.motus.domain.model.Character
import com.android.jimmy.motus.domain.model.Status
import com.android.jimmy.motus.ui.MainViewModel
import com.android.jimmy.motus.ui.R
import com.android.jimmy.motus.ui.UIStateView
import com.android.jimmy.motus.ui.model.UiCurrentGameState
import com.android.jimmy.motus.ui.theme.MotusTheme
import java.util.regex.Pattern

@Composable
fun MainScreen(context: Context, mainViewModel: MainViewModel) {
    val state = remember { mainViewModel.state }.collectAsStateWithLifecycle()
    val listWordTried = remember { mainViewModel.listWordTried }.collectAsStateWithLifecycle()
    val uiCurrentGameState = remember {
        mainViewModel.uiCurrentGameState
    }.collectAsStateWithLifecycle()
    val currentWord = remember { mainViewModel.currentWordStr }.collectAsStateWithLifecycle()
    val moveRemaining = remember { mainViewModel.moveRemaining }.collectAsStateWithLifecycle()
    val freeMusic: MediaPlayer = remember { MediaPlayer.create(context, R.raw.free) }

    UIStateView(state) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Column(
                Modifier
                    .padding(top = 24.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            ) {
                Text(text = context.getString(R.string.move_remaining, moveRemaining.value))
            }

            Column(Modifier.align(alignment = Alignment.CenterHorizontally)) {
                listWordTried.value.forEach {
                    UiCharacters(characters = it)
                }
            }

            if (uiCurrentGameState.value == UiCurrentGameState.NONE) {
                UiTextView(
                    Modifier.align(alignment = Alignment.CenterHorizontally),
                    context.getString(R.string.button_try_word)
                ) {
                    mainViewModel.validate(it)
                }
            }

            UiGameStateView(
                uiCurrentGameState = uiCurrentGameState.value,
                newGame = { mainViewModel.newGame() },
                winString = context.getString(R.string.game_win),
                looseString = context.getString(R.string.game_loose, currentWord.value),
                newGameString = context.getString(R.string.button_game_new)
            )

            UiInfoView(
                mediaPlayer = freeMusic,
                colorCodeString = context.getString(R.string.color_code),
                characterCorrectString = context.getString(R.string.character_correct),
                characterWrongPositionString = context.getString(R.string.character_wrong_position),
                characterWrongString = context.getString(R.string.character_wrong),
                characterNoneString = context.getString(R.string.character_none)
            )
        }
    }
}

@Composable
fun UiGameStateView(
    uiCurrentGameState: UiCurrentGameState,
    newGame: () -> Unit,
    winString: String,
    looseString: String,
    newGameString: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(16.dp)
    ) {
        when (uiCurrentGameState) {
            UiCurrentGameState.LOOSE -> UiInfoGameStateView(
                text = looseString,
                textButton = newGameString,
                color = Color(0xFFC20505),
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            ) { newGame() }

            UiCurrentGameState.WIN -> UiInfoGameStateView(
                text = winString,
                textButton = newGameString,
                color = Color(0xFF087E08),
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            ) { newGame() }

            UiCurrentGameState.STARTED, UiCurrentGameState.NONE -> {}
        }
    }
}

@Composable
fun UiTextView(modifier: Modifier, buttonTryWordString: String, onClick: (String) -> Unit) {
    val pattern = Pattern.compile("^[A-Za-z]+$")
    var textFieldValue by remember { mutableStateOf(TextFieldValue(text = "")) }
    var buttonEnabled by remember { mutableStateOf(false) }
    val maxChar = 6
    Row(modifier = modifier.padding(top = 24.dp)) {
        TextField(
            placeholder = { Text("Saisir un mot") },
            value = textFieldValue,
            onValueChange = { newValue ->
                val matcher = pattern.matcher(newValue.text)
                if (matcher.matches() || newValue.text.isEmpty()) {
                    if (newValue.text.length <= maxChar) {
                        textFieldValue = newValue
                    }
                    buttonEnabled = textFieldValue.text.length == maxChar
                }
            },
            modifier = Modifier.width(200.dp),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                capitalization = KeyboardCapitalization.Characters
            )
        )
        Button(
            onClick = {
                onClick(textFieldValue.text)
                textFieldValue = TextFieldValue(text = "")
                buttonEnabled = false
            },
            modifier = Modifier
                .align(alignment = Alignment.CenterVertically)
                .padding(start = 8.dp),
            enabled = buttonEnabled
        ) {
            Text(text = buttonTryWordString)
        }
    }
}

@Composable
fun UiCharacter(character: Character, position: Int = 0) {
    Box(
        modifier = Modifier
            .height(50.dp)
            .width(50.dp)
            .background(color = getColorcharacter(character.status))
            .border(width = 1.dp, color = Color.White)
    ) {
        if (character.status != Status.NONE || position == 0) {
            Text(
                text = character.char.toString(),
                modifier = Modifier.align(alignment = Alignment.Center),
                style = TextStyle(
                    fontSize = 24.sp,
                    color = Color.Black
                ),
                color = if (character.status.equals(Status.CORRECT) || character.status.equals(
                        Status.WRONG
                    )
                ) {
                    Color.White
                } else {
                    Color.Black
                }
            )
        }
    }
}

@Composable
fun UiCharacters(characters: List<Character>, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        characters.forEachIndexed { index, character ->
            UiCharacter(character, index)
        }
    }
}

@Composable
fun UiInfoView(
    modifier: Modifier = Modifier,
    mediaPlayer: MediaPlayer?,
    colorCodeString: String,
    characterCorrectString: String,
    characterWrongPositionString: String,
    characterWrongString: String,
    characterNoneString: String
) {
    var musicStarted: Boolean = remember { false }
    Column(modifier = modifier.padding(top = 24.dp)) {
        Row {
            Text(
                text = colorCodeString,
                modifier = Modifier
                    .align(alignment = Alignment.CenterVertically)
                    .padding(start = 8.dp, bottom = 8.dp)

            )
        }
        Row(
            modifier = Modifier.clickable(
                onClick = {
                    mediaPlayer?.let {
                        if (musicStarted && mediaPlayer.isPlaying) {
                            mediaPlayer.pause().also { musicStarted = false }
                        } else {
                            mediaPlayer.start().also { musicStarted = true }
                        }
                    }
                }
            )
        ) {
            UiCharacter(Character('F', Status.CORRECT))
            Text(
                text = characterCorrectString,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(alignment = Alignment.CenterVertically)
            )
        }
        Row(
            modifier = Modifier.clickable(
                onClick = {
                    mediaPlayer?.let {
                        if (musicStarted && mediaPlayer.isPlaying) {
                            mediaPlayer.pause().also { musicStarted = false }
                        } else {
                            mediaPlayer.start().also { musicStarted = true }
                        }
                    }
                }
            )
        ) {
            UiCharacter(Character('R', Status.WRONG_POSITION))
            Text(
                text = characterWrongPositionString,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(alignment = Alignment.CenterVertically)
            )
        }
        Row(
            modifier = Modifier.clickable(
                onClick = {
                    mediaPlayer?.let {
                        if (musicStarted && mediaPlayer.isPlaying) {
                            mediaPlayer.pause().also { musicStarted = false }
                        } else {
                            mediaPlayer.start().also { musicStarted = true }
                        }
                    }
                }
            )
        ) {
            UiCharacter(Character('E', Status.WRONG))
            Text(
                text = characterWrongString,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(alignment = Alignment.CenterVertically)
            )
        }
        Row(
            modifier = Modifier.clickable(
                onClick = {
                    mediaPlayer?.let {
                        if (musicStarted && mediaPlayer.isPlaying) {
                            mediaPlayer.pause().also { musicStarted = false }
                        } else {
                            mediaPlayer.start().also { musicStarted = true }
                        }
                    }
                }
            )
        ) {
            UiCharacter(Character('E', Status.NONE))
            Text(
                text = characterNoneString,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .align(alignment = Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun UiInfoGameStateView(
    text: String,
    textButton: String,
    color: Color,
    modifier: Modifier,
    reset: () -> Unit
) {
    Column(modifier = modifier) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            color = color
        )

        Button(
            onClick = { reset() },
            modifier = Modifier
                .padding(8.dp)
                .align(alignment = Alignment.CenterHorizontally)
        ) {
            Text(text = textButton)
        }
    }
}

@Composable
fun getColorcharacter(status: Status) = when (status) {
    Status.CORRECT -> Color(0xFFC20505)
    Status.WRONG_POSITION -> Color(0xFFFFDD00)
    Status.WRONG -> Color(0xFF000000)
    Status.NONE -> Color(0xFFCCCCCC)
}

@Preview(showBackground = true)
@Composable
fun UiCharacterPreview() {
    MotusTheme {
        val character = Character('F', Status.CORRECT)
        UiCharacter(character)
    }
}

@Preview(showBackground = true)
@Composable
fun UiCharactersPreview() {
    MotusTheme {
        val character1 = Character('J', Status.CORRECT)
        val character2 = Character('I', Status.WRONG)
        val character3 = Character('M', Status.CORRECT)
        val character4 = Character('M', Status.WRONG_POSITION)
        val character5 = Character('Y', Status.CORRECT)
        val character6 = Character('Y', Status.NONE)

        val characters = listOf(
            character1,
            character2,
            character3,
            character4,
            character5,
            character6
        )
        UiCharacters(characters)
    }
}

@Preview(showBackground = true)
@Composable
fun UiInfoViewPreview() {
    MotusTheme {
        UiInfoView(
            mediaPlayer = null,
            colorCodeString = "Code color:",
            characterCorrectString = "Correct",
            characterWrongPositionString = "Wrong Position",
            characterWrongString = "Wrong",
            characterNoneString = "None"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UiTextViewPreview() {
    MotusTheme {
        UiTextView(modifier = Modifier, buttonTryWordString = "Essayer ce mot") {}
    }
}

@Preview
@Composable
fun UiGameStateViewWin() {
    MotusTheme {
        UiGameStateView(UiCurrentGameState.WIN, {}, "Win", "Loose", "")
    }
}

@Preview
@Composable
fun UiGameStateViewLoose() {
    MotusTheme {
        UiGameStateView(UiCurrentGameState.LOOSE, {}, "Win", "Loose", "Toto")
    }
}
