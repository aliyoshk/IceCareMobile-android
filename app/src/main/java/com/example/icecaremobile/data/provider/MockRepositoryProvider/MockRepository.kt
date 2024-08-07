package com.example.icecaremobile.data.provider.MockRepositoryProvider

import com.example.icecaremobile.data.mock.MockService
import com.example.icecaremobile.data.provider.RepositoryProvider.IRepositoryProvider
import com.example.icecaremobile.domain.repository.IRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockRepository @Inject constructor() : IRepositoryProvider
{
    override fun provideRepository(): IRepository
    {
        return MockService()
    }
}