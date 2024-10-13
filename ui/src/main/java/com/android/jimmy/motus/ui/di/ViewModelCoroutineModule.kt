package com.android.jimmy.motus.ui.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.Dispatchers

@Qualifier
annotation class ViewModelCoroutineContext

@Module
@InstallIn(SingletonComponent::class)
class CoroutineModule {

    @Provides
    @ViewModelCoroutineContext
    fun provideViewModelCoroutineContext(): CoroutineContext =
        Dispatchers.Main + CoroutineName("MainViewModel")
}
