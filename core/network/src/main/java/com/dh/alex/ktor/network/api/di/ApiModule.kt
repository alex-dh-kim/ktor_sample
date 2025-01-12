package com.dh.alex.ktor.network.api.di

import com.dh.alex.ktor.network.api.ApiService
import com.dh.alex.ktor.network.api.ApiServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class ApiModule {
    @Singleton
    @Binds
    abstract fun bindApiService(api: ApiServiceImpl): ApiService
}
