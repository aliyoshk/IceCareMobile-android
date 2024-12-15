package com.example.icecaremobile.data.provider.RemoteRepositoryProvider

import com.example.icecaremobile.data.local.dataSource.TokenManager
import com.example.icecaremobile.data.provider.RepositoryProvider.IRepositoryProvider
import com.example.icecaremobile.data.remote.implementation.ApiService
import com.example.icecaremobile.data.remote.repository.RepositoryImpl
import com.example.icecaremobile.domain.repository.IRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteRepository @Inject constructor(
    private val apiService: ApiService,
    private val tokenManager: TokenManager
) : IRepositoryProvider {

    override fun provideRepository(): IRepository {
        return RepositoryImpl(apiService, tokenManager)
    }
}

