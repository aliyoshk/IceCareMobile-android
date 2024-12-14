package com.example.icecaremobile.data.provider.RepositoryProvider

import com.example.icecaremobile.domain.repository.IRepository

interface IRepositoryProvider {
    fun provideRepository(): IRepository
}

