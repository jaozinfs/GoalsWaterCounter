package com.example.orbitmvi.worker

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.orbitmvi.domain.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ClearCupsWorker(
    ctx: Context,
    params: WorkerParameters
) : CoroutineWorker(ctx, params), KoinComponent {
    companion object {
        const val WORK_TAG = "ClearCupsJobs"
    }

    private val userRepository: UserRepository by inject()


    override suspend fun doWork(): Result {
        userRepository.clearCurrentCups()
        return Result.success()
    }
}