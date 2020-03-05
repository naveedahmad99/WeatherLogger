package com.nido.weatherlogger.background

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.nido.weatherlogger.ServiceLocator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RefreshForecastDataWorker(appContext: Context, params: WorkerParameters):
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshForecastDataWorker"
    }

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            ServiceLocator.provideForecastRepository(applicationContext).getForecastData()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}
