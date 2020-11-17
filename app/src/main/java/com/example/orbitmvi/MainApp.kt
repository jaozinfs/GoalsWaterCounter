package com.example.orbitmvi

import android.app.Application
import android.util.Log
import androidx.work.*
import com.example.orbitmvi.di.appModules
import com.example.orbitmvi.worker.ClearCupsWorker
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit

class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApp)
            modules(appModules)
        }
        startWorker()
    }

    private fun startWorker(){

        val work = PeriodicWorkRequestBuilder<ClearCupsWorker>(1, TimeUnit.DAYS).build()

        WorkManager.getInstance(applicationContext)
            .enqueueUniquePeriodicWork(ClearCupsWorker.WORK_TAG, ExistingPeriodicWorkPolicy.KEEP, work)


    }
}