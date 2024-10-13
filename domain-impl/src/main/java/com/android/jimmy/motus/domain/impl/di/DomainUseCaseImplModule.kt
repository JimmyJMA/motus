package com.android.jimmy.motus.domain.impl.di

import com.android.jimmy.motus.domain.impl.FindWordUseCaseImpl
import com.android.jimmy.motus.domain.impl.GetRandomWordUseCaseImpl
import com.android.jimmy.motus.domain.usecase.FindWordUseCase
import com.android.jimmy.motus.domain.usecase.GetRandomWordUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface DomainUseCaseImplModule {

    @Binds
    fun bindsGetWordsUseCase(getRandomWordUseCaseImpl: GetRandomWordUseCaseImpl): GetRandomWordUseCase

    @Binds
    fun bindsFindWordUseCase(findWordUseCaseImpl: FindWordUseCaseImpl): FindWordUseCase

}