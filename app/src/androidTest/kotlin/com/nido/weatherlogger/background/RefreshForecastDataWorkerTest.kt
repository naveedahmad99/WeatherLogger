package com.nido.weatherlogger.background

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.ListenableWorker
import androidx.work.testing.TestListenableWorkerBuilder
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RefreshForecastDataWorkerTest {

    private lateinit var context: Context

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
    }

    @Test
    fun testMyWork_returnsSuccess() {
        // Get the ListenableWorker
        val worker = TestListenableWorkerBuilder<RefreshForecastDataWorker>(context).build()
        // Run the worker synchronously
        val result = worker.startWork().get()
        MatcherAssert.assertThat(result, CoreMatchers.`is`(ListenableWorker.Result.success()))
    }

//    @Test
//    fun testMyWork_returnsError() {
//        // Get the ListenableWorker
//        val worker = TestListenableWorkerBuilder<RefreshForecastDataWorker>(context).build()
//        // Run the worker synchronously
//        val result = worker.startWork().get()
//        MatcherAssert.assertThat(result, CoreMatchers.`is`(ListenableWorker.Result.failure()))
//    }
}
