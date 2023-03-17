package com.challenge.nycs_challengejpmc.di

import com.challenge.nycs_challengejpmc.rest.SchoolsRepository
import com.challenge.nycs_challengejpmc.rest.SchoolsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun providesRepositoryImpl(
        schoolsRepositoryImpl: SchoolsRepositoryImpl
    ): SchoolsRepository
}