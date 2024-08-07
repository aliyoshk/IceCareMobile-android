package com.example.icecaremobile.data.provider.RemoteRepositoryProvider

import com.example.icecaremobile.core.utils.Urls
import com.example.icecaremobile.data.provider.RepositoryProvider.IRepositoryProvider
import com.example.icecaremobile.data.remote.dataSource.IApiService
import com.example.icecaremobile.data.remote.implementation.ApiService
import com.example.icecaremobile.data.remote.repository.RepositoryImpl
import com.example.icecaremobile.domain.repository.IRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteRepository @Inject constructor() : IRepositoryProvider
{

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Urls.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api: IApiService = retrofit.create(IApiService::class.java)
    private val apiService = ApiService(api)

    override fun provideRepository(): IRepository {
        return RepositoryImpl(apiService)
    }
}