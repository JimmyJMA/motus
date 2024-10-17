package com.android.jimmy.motus.domain

import com.android.jimmy.motus.domain.mapper.CharacterMapper
import com.android.jimmy.motus.domain.model.Character
import com.android.jimmy.motus.domain.model.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterMapperTest: BaseUnitTest() {

    private lateinit var characterMapper: CharacterMapper

    @Before
    fun setup() {
        characterMapper = CharacterMapper()
    }

    @Test
    fun `map to success when state is success`() {
        // region given
        val expectedStateSuccessString = State.Success("TEST")
        val expectedStateListCharacter = State.Success(
            listOf(
                Character('T', Status.NONE),
                Character('E', Status.NONE),
                Character('S', Status.NONE),
                Character('T', Status.NONE)
            )
        )

        // endregion
        // when
        val result = characterMapper.toCharacterMapper(expectedStateSuccessString)

        // then
        assertEquals(expectedStateListCharacter, result)
    }

    @Test
    fun `map to failure when state is success and data is null`() {
        // region given
        val expectedStateSuccessString = State.Success("")
        val expectedStateListCharacter: State.Failure<List<Character>> = State.Failure("Code erreur 4 - Une erreur est survenue")

        // endregion
        // when
        val result = characterMapper.toCharacterMapper(expectedStateSuccessString)

        // then
        assertEquals(expectedStateListCharacter, result)
    }
}