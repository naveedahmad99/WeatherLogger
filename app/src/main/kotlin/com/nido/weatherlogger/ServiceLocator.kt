package com.nido.weatherlogger

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.nido.weatherlogger.data.ForecastsRepository
import com.nido.weatherlogger.data.IForecastsRepository
import com.nido.weatherlogger.data.source.local.ForecastsLocalDataSource
import com.nido.weatherlogger.data.source.local.room.utils.AppDatabase
import com.nido.weatherlogger.data.source.remote.ForecastsRemoteDataSource

/**
 * The service locator pattern involves making a singleton class called
 * a [ServiceLocator] whose purpose is to store and provide dependencies.
 * The service locator does this both for the regular code and for test
 * code. Whenever you need a dependency, instead of constructing it
 * yourself you create a service locator and then you get the dependencies
 * from the service locator. For the regular app code meeting everything
 * in the main source set, but for the tests you update the service
 * locator to provide testable versions of the dependencies.
 *
 * The benefit of using a service locator over set of dependency injection,
 * is that to set a certain dependency injection, you need to set the
 * correct dependency each time that can make an instance of a class. Now,
 * with the service locator you set all the correct dependencies in the
 * beginning in the service locator and then you can be sure that those
 * dependencies are going to be used everywhere.
 */
object ServiceLocator {

    private var db: AppDatabase? = null

    private val lock = Any()

    @Volatile
    var forecastsRepository: IForecastsRepository? = null
        @VisibleForTesting set

    /**
     * Function to provide a [ForecastsRepository] instance.
     */
    fun provideForecastRepository(context: Context): IForecastsRepository {
        synchronized(this) {
            return forecastsRepository ?: createForecastRepository(context)
        }
    }

    private fun createForecastRepository(context: Context): ForecastsRepository {
        db = AppDatabase.getInstance(context)
        val newRepository = ForecastsRepository(
            ForecastsRemoteDataSource(),
            ForecastsLocalDataSource(db!!.forecastDataDao(), db!!.cityForecastDataDao())
        )
        forecastsRepository = newRepository
        return newRepository
    }

    @VisibleForTesting
    fun resetRepository() {
        synchronized(lock) {
            db?.apply {
                clearAllTables()
                close()
            }
            db = null
            forecastsRepository = null
        }
    }
}
