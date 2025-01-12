package com.dh.alex.ktor.data.repository.di

import com.dh.alex.ktor.data.repository.GithubRepository
import com.dh.alex.ktor.data.repository.GithubRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindGithubRepository(repository: GithubRepositoryImpl): GithubRepository
}
