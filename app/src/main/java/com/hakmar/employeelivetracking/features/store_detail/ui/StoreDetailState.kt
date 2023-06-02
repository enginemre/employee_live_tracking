package com.hakmar.employeelivetracking.features.store_detail.ui

import com.hakmar.employeelivetracking.common.domain.model.Store
import com.hakmar.employeelivetracking.common.presentation.graphs.StoreDetailDestination
import com.hakmar.employeelivetracking.features.store_detail.domain.model.TaskModel
import com.hakmar.employeelivetracking.util.TimerState
import kotlin.time.Duration.Companion.minutes

data class StoreDetailState(
    var isLoading: Boolean = false,
    var seconds: String = "00",
    var minutes: String = "00",
    var hours: String = "00",
    var isPlaying: TimerState = TimerState.Idle,
    var initialTime: Int = 1,
    var maxValueOfTime: Int = 45.minutes.inWholeSeconds.toInt(),
    var taskList: List<TaskModel> = listOf<TaskModel>(
        TaskModel(
            name = "Çelik Kasa Sayım",
            isCompleted = false,
            imageUrl = "https://cdn-icons-png.flaticon.com/512/2676/2676632.png",
            infoText = "Bu görevde yapmanız gereken şeyler şunlardır öncelikle kasanın yanına git",
            route = StoreDetailDestination.SteelCaseAmounts.base
        ),
        TaskModel(
            name = "Mağaza Önü Kontrol",
            isCompleted = false,
            imageUrl = "https://cdn-icons-png.flaticon.com/512/609/609752.png",
            infoText = "Bu görevde yapmanız gereken şeyler şunlardır öncelikle mağazanın önüne git",
            route = StoreDetailDestination.StoreOutside.base
        ),
        TaskModel(
            name = "Mağaza İçi Kontrol",
            isCompleted = false,
            imageUrl = "https://cdn-icons-png.flaticon.com/512/3306/3306049.png",
            infoText = "Bu görevde yapmanız gereken şeyler şunlardır öncelikle mağazanın önüne git",
            route = StoreDetailDestination.StoreInside.base

        ),
        TaskModel(
            name = "Z Raporları Kontrol",
            isCompleted = false,
            imageUrl = "https://cdn-icons-png.flaticon.com/512/9342/9342023.png",
            infoText = "Bu görevde yapmanız gereken şeyler şunlardır öncelikle mağazanın önüne git",
            route = StoreDetailDestination.PosAmounts.base
        ),
    ),
    var store: Store? = null
)
