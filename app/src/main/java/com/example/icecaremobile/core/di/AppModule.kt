package com.example.icecaremobile.core.di

import android.content.Context
import com.example.icecaremobile.BuildConfig
import com.example.icecaremobile.IceCareMobile
import com.example.icecaremobile.core.utils.Urls
import com.example.icecaremobile.data.provider.MockRepositoryProvider.MockRepository
import com.example.icecaremobile.data.provider.RemoteRepositoryProvider.RemoteRepository
import com.example.icecaremobile.data.provider.RepositoryProvider.IRepositoryProvider
import com.example.icecaremobile.data.remote.dataSource.IApiService
import com.example.icecaremobile.data.remote.implementation.ApiService
import com.example.icecaremobile.data.remote.repository.RepositoryImpl
import com.example.icecaremobile.domain.repository.IRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.example.icecaremobile.data.local.auth.AuthManagerImpl
import com.example.icecaremobile.domain.auth.AuthManager
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context = context

    @Provides
    @Singleton
    fun provideRepositoryProvider(): IRepositoryProvider {
        val isDebug = BuildConfig.DEBUG
        val useMockData = BuildConfig.USE_MOCK_DATA
        return if (isDebug && useMockData) {
            MockRepository()
        } else {
            RemoteRepository() // Assuming RemoteRepository needs Context
        }
    }

    @Provides
    @Singleton
    fun provideBaseUrl() = Urls.BASE_URL

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): IApiService {
        return retrofit.create(IApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideRepository(apiService: ApiService): IRepository {
        return RepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideApiService(api: IApiService): ApiService {
        return ApiService(api)
    }

    @Provides
    @Singleton
    fun provideAuthManager(@ApplicationContext context: Context): AuthManager {
        return AuthManagerImpl(context)
    }
}

