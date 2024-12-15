package com.example.icecaremobile.core.di

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.icecaremobile.BuildConfig
import com.example.icecaremobile.data.local.auth.AuthManagerImpl
import com.example.icecaremobile.data.local.dataSource.AuthInterceptor
import com.example.icecaremobile.data.local.dataSource.TokenManager
import com.example.icecaremobile.data.provider.RemoteRepositoryProvider.RemoteRepository
import com.example.icecaremobile.data.provider.RepositoryProvider.IRepositoryProvider
import com.example.icecaremobile.data.remote.dataSource.IApiService
import com.example.icecaremobile.data.remote.implementation.ApiService
import com.example.icecaremobile.data.remote.repository.RepositoryImpl
import com.example.icecaremobile.domain.auth.AuthManager
import com.example.icecaremobile.domain.repository.IRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context = context

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        Log.d("App Module Execution", "provideSharedPreferences ${context.getSharedPreferences("token_prefs", Context.MODE_PRIVATE)} ")
        return context.getSharedPreferences("token_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideTokenManager(sharedPreferences: SharedPreferences): TokenManager {
        Log.d("App Module Execution", "provideTokenManager $sharedPreferences")
        return TokenManager(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenManager: TokenManager): AuthInterceptor {
        Log.d("App Module Execution", "provideAuthInterceptor $tokenManager")
        return AuthInterceptor(tokenManager)
    }

    @Provides
    @Singleton
    fun provideRepositoryProvider(apiService: ApiService, tokenManager: TokenManager): IRepositoryProvider {
        val isDebug = BuildConfig.DEBUG
        val useMockData = BuildConfig.USE_MOCK_DATA
        return if (isDebug && useMockData) {
            //MockRepository()
            RemoteRepository(apiService, tokenManager)
        } else {
            RemoteRepository(apiService, tokenManager) // Assuming RemoteRepository needs Context
        }
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): IApiService {
        Log.d("App Module Execution", "provideApi $retrofit")
        return retrofit.create(IApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        Log.d("App Module Execution", "provideOkHttpClient $authInterceptor")
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        Log.d("App Module Execution", "provideRetrofit $okHttpClient")
        return Retrofit.Builder()
            .baseUrl("https://ice-care-mobile-backend-d5fwe4asg9cwbnbd.southafricanorth-01.azurewebsites.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideRepository(apiService: ApiService, tokenManager: TokenManager): IRepository {
        Log.d("App Module Execution", "provideRepository $apiService $tokenManager")
        return RepositoryImpl(apiService, tokenManager)
    }

    @Provides
    @Singleton
    fun provideApiService(api: IApiService): ApiService {
        Log.d("App Module Execution", "provideApiService $api")
        return ApiService(api)
    }

    @Provides
    @Singleton
    fun provideAuthManager(@ApplicationContext context: Context): AuthManager {
        Log.d("App Module Execution", "provideAuthManager $context")
        return AuthManagerImpl(context)
    }
}

