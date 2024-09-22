package com.example.homeworkattractions.di

import com.example.homeworkattractions.data.APIRepository
import com.example.homeworkattractions.data.ApiClient
import com.example.homeworkattractions.data.ApiRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApiClient(): ApiClient {
        return ApiClient()
    }

    @Provides
    @Singleton
    fun provideApiRemoteDataSource(apiClient: ApiClient): ApiRemoteDataSource {
        return ApiRemoteDataSource(apiClient)
    }

    @Provides
    @Singleton
    fun provideAPIRepository(apiRemoteDataSource: ApiRemoteDataSource): APIRepository {
        return APIRepository(apiRemoteDataSource)
    }
}
