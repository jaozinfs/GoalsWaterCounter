package com.example.orbitmvi.domain

import kotlinx.coroutines.flow.Flow


interface UserRepository {
    suspend fun setDailyWaterCount(count: Int)
    suspend fun getCurrentDailyWaterCount(): Flow<Pair<Int, Int>>
    suspend fun setCurrentCupsDaily(count: Int)
    suspend fun getCurrentCupsDaily(): Flow<Int>
    suspend fun clearCurrentCups()
}
