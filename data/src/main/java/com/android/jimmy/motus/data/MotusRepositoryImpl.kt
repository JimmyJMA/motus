package com.android.jimmy.motus.data

import com.android.jimmy.motus.data.api.toState
import com.android.jimmy.motus.domain.State
import com.android.jimmy.motus.domain.mapper.CharacterMapper
import com.android.jimmy.motus.domain.model.Character
import com.android.jimmy.motus.domain.repository.MotusRepository
import javax.inject.Inject

class MotusRepositoryImpl @Inject constructor(
    private val motusRemoteDataSource: MotusRemoteDataSource,
    private val characterMapper: CharacterMapper,
) : MotusRepository {

    override suspend fun getWord(): State<List<Character>> {
        return when (val response = motusRemoteDataSource.bindsMotusRemoteDataSource().toState()) {
            is State.Success -> {
                characterMapper.toCharacterMapper(State.Success(response.data))
            }
            is State.Failure -> {
                State.Failure("Error network")
            }
        }
    }

}