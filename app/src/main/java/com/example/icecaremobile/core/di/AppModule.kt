package com.example.icecaremobile.core.di

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.icecaremobile.BuildConfig
import com.example.icecaremobile.core.utils.Urls
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
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
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
        return context.getSharedPreferences("token_prefs", Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideTokenManager(sharedPreferences: SharedPreferences): TokenManager {
        return TokenManager(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(tokenManager: TokenManager): AuthInterceptor {
        Log.d("AuthInterceptor", tokenManager.toString())
        return AuthInterceptor(tokenManager)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor): OkHttpClient {
        // Create logging interceptor for debugging
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor) // Logs request and response bodies
            .addInterceptor(authInterceptor)    // Handles authentication
            .addNetworkInterceptor(object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    val request: Request = chain.request().newBuilder()
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Connection", "close")
                        .build()
                    return chain.proceed(request)
                }
            })
            .build()
    }

    @Provides
    @Singleton
    fun provideRepositoryProvider(tokenManager: TokenManager): IRepositoryProvider {
        val isDebug = BuildConfig.DEBUG
        val useMockData = BuildConfig.USE_MOCK_DATA
        return if (isDebug && useMockData) {
            //MockRepository()
            RemoteRepository(tokenManager)
        } else {
            RemoteRepository(tokenManager) // Assuming RemoteRepository needs Context
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
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(provideBaseUrl())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideRepository(apiService: ApiService, tokenManager: TokenManager): IRepository {
        return RepositoryImpl(apiService, tokenManager)
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

