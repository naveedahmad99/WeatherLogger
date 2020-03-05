package com.nido.weatherlogger

import android.app.Application
import android.os.Build
import androidx.work.*
import androidx.work.PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS
import androidx.work.PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS
import com.nido.weatherlogger.background.RefreshForecastDataWorker
import com.nido.weatherlogger.koin.appModules
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.util.concurrent.TimeUnit.MINUTES

class WeatherLoggerApp : Application() {

    private val applicationScope = CoroutineScope((Dispatchers.Default))

    override fun onCreate() {
        super.onCreate()
        delayedInit()

        // Start Koin
        startKoin{
            androidContext(this@WeatherLoggerApp)
            modules(appModules)
        }
    }

    private fun delayedInit() = applicationScope.launch {
        setupRecurringWork()
    }

    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .setRequiresDeviceIdle(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            .build()

        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshForecastDataWorker>(
            MIN_PERIODIC_INTERVAL_MILLIS, MINUTES,
            MIN_PERIODIC_FLEX_MILLIS, MINUTES
        ).setConstraints(constraints).build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            RefreshForecastDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest
        )
    }
}
