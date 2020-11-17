package com.example.orbitmvi.persistence.repositories

import android.content.Context
import android.os.storage.StorageManager
import android.util.Log
import androidx.datastore.preferences.createDataStore
import androidx.datastore.preferences.edit
import androidx.datastore.preferences.preferencesKey
import com.example.orbitmvi.di.LOGIN_QUALIFIER
import com.example.orbitmvi.domain.UserRepository
import com.example.orbitmvi.persistence.DataStorageManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.lang.NullPointerException

class UserRepositoryLocalImpl(
    private val dataStorageManager: DataStorageManager
) : UserRepository, KoinComponent {
    private val USER_WATER_COUNT = "args_user_water_count"
    private val USER_WATER_CURRENT = "args_user_water_current"

    private val context by inject<Context>(LOGIN_QUALIFIER)
    private val SETTINGS_FLAG = "settings"

    private val dataManager by lazy {
        context.createDataStore(name = SETTINGS_FLAG)
    }

    override suspend fun setDailyWaterCount(count: Int) {
        val key = preferencesKey<Int>(USER_WATER_COUNT)
        dataManager.edit { settings ->
            settings[key] = count
        }
    }

    override suspend fun getCurrentDailyWaterCount(): Flow<Pair<Int, Int>> {
        val keyCount = preferencesKey<Int>(USER_WATER_COUNT)
        val keyCurrent = preferencesKey<Int>(USER_WATER_CURRENT)
        return dataManager.data.map {
           Pair( it[keyCount] ?: 0,  it[keyCurrent] ?: 0)
        }
    }

    override suspend fun setCurrentCupsDaily(count: Int) {
        val key = preferencesKey<Int>(USER_WATER_CURRENT)
        dataManager.edit { settings ->
            settings[key] = count
        }
    }

    override suspend fun getCurrentCupsDaily(): Flow<Int> {
        val key = preferencesKey<Int>(USER_WATER_CURRENT)
        return dataManager.data.map {
            it[key] ?: 0
        }
    }

    override suspend fun clearCurrentCups() {
        Log.d("Teste", "Run hsss")
        val keyCount = preferencesKey<Int>(USER_WATER_CURRENT)
        dataManager.edit { settings ->
            settings[keyCount] = 0
        }
    }

}