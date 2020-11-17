package com.example.orbitmvi.persistence

import android.content.Context
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import com.example.orbitmvi.di.LOGIN_QUALIFIER
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DataStorageManagerImpl : DataStorageManager, KoinComponent {
    private val context by inject<Context>(LOGIN_QUALIFIER)
    private val SETTINGS_FLAG = "settings"

    private val dataManager by lazy {
        context.createDataStore(name = SETTINGS_FLAG)
    }

    override suspend fun <T> saveData(flag: String, value: T) {
        val key = preferencesKey<Any>(flag)
        dataManager.edit { settings ->
            val currentCounterValue = settings[key] ?: 0
            settings[key] = value as Any
        }
    }

    override suspend fun <T> getData(flag: String): Flow<T?>? {
        val key = preferencesKey<Any>(flag)
        return dataManager.data.map {
            it[key]
        } as? Flow<T?>
    }
}
interface DataStorageManager {
    suspend fun <T> saveData(flag: String, value: T)
    suspend fun <T> getData(flag: String): Flow<T?>?

}