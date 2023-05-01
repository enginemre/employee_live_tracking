package com.hakmar.employeelivetracking.features.store_detail_tasks.di

import cafe.adriel.voyager.hilt.ScreenModelFactory
import cafe.adriel.voyager.hilt.ScreenModelFactoryKey
import com.hakmar.employeelivetracking.features.store_detail_tasks.ui.viewmodel.StoreInsideScreenModel
import com.hakmar.employeelivetracking.features.store_detail_tasks.ui.viewmodel.StoreOutsideScreenModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(ActivityComponent::class)
abstract class ScreenModule {
    @Binds
    @IntoMap
    @ScreenModelFactoryKey(StoreOutsideScreenModel.Factory::class)
    abstract fun bindHiltOutsideScreenModelFactory(
        hiltDetailsScreenModelFactory: StoreOutsideScreenModel.Factory
    ): ScreenModelFactory

    @Binds
    @IntoMap
    @ScreenModelFactoryKey(StoreInsideScreenModel.Factory::class)
    abstract fun bindHiltInsideScreenModelFactory(
        hiltDetailsScreenModelFactory: StoreInsideScreenModel.Factory
    ): ScreenModelFactory
}