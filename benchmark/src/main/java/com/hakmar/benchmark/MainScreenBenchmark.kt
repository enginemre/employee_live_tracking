@file:OptIn(ExperimentalMetricApi::class)

package com.hakmar.benchmark

import androidx.benchmark.macro.ExperimentalMetricApi
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.PowerCategory
import androidx.benchmark.macro.PowerCategoryDisplayLevel
import androidx.benchmark.macro.PowerMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This is an example startup benchmark.
 *
 * It navigates to the device's home screen, and launches the default activity.
 *
 * Before running this benchmark:
 * 1) switch your app's active build variant in the Studio (affects Studio runs only)
 * 2) add `<profileable android:shell="true" />` to your app's manifest, within the `<application>` tag
 *
 * Run this benchmark from Studio to see startup measurements, and captured system traces
 * for investigating your app's performance.
 */
@RunWith(AndroidJUnit4::class)
class MainScreenBenchmark {


    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun mesaureStartupTimeForMainScreen() = benchmarkRule.measureRepeated(
        packageName = "com.hakmar.employeelivetracking",
        metrics = listOf(StartupTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD
    ) {
        pressHome()
        startActivityAndWait()
    }

    @Test
    fun mesaureFrameForMainScreen() = benchmarkRule.measureRepeated(
        packageName = "com.hakmar.employeelivetracking",
        metrics = listOf(FrameTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD
    ) {
        pressHome()
        startActivityAndWait()
    }

    @Test
    fun mesaurePowerMetricForMainScreen() = benchmarkRule.measureRepeated(
        packageName = "com.hakmar.employeelivetracking",
        metrics = listOf(
            PowerMetric(
                PowerMetric.Power(
                    hashMapOf(
                        PowerCategory.GPS to PowerCategoryDisplayLevel.TOTAL,
                        PowerCategory.NETWORK to PowerCategoryDisplayLevel.TOTAL,
                        PowerCategory.CPU to PowerCategoryDisplayLevel.TOTAL,
                        PowerCategory.GPU to PowerCategoryDisplayLevel.TOTAL,
                    )
                )
            )
        ),
        iterations = 5,
        startupMode = StartupMode.COLD
    ) {
        pressHome()
        startActivityAndWait()
    }

}