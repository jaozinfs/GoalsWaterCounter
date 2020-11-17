package com.example.orbitmvi.di

import android.content.Context
import com.example.orbitmvi.domain.UserRepository
import com.example.orbitmvi.persistence.DataStorageManager
import com.example.orbitmvi.persistence.DataStorageManagerImpl
import com.example.orbitmvi.persistence.repositories.UserRepositoryLocalImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.StringQualifier
import org.koin.core.qualifier.named
import org.koin.dsl.module

val LOGIN_QUALIFIER = StringQualifier("koin_login_qualifier")

val appModules = module {
    single<Context>(qualifier = LOGIN_QUALIFIER) { androidApplication() }
    single<DataStorageManager> { DataStorageManagerImpl() }
    single<UserRepository> { UserRepositoryLocalImpl(get()) }
}