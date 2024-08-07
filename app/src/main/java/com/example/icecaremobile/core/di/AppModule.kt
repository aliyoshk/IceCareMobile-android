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
import com.example.icecaremobile.*

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideApplicationContext(application: IceCareMobile): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideRepositoryProvider(): IRepositoryProvider {
        return if (BuildConfig.USE_MOCK_DATA)
            MockRepository()
        else
            RemoteRepository()
    }

    @Provides
    @Singleton
    fun provideBaseUrl() = Urls.BASE_URL

    @Provides
    @Singleton
    fun provideWeatherApi(retrofit: Retrofit): IApiService {
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
    fun provideWeatherRepository(apiService: ApiService): IRepository {
        return RepositoryImpl(apiService)
    }

    @Provides
    @Singleton
    fun provideWeatherApiService(api: IApiService): ApiService {
        return ApiService(api)
    }
}