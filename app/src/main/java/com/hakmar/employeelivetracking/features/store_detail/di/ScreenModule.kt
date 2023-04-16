package com.hakmar.employeelivetracking.features.store_detail.di

import cafe.adriel.voyager.hilt.ScreenModelFactory
import cafe.adriel.voyager.hilt.ScreenModelFactoryKey
import com.hakmar.employeelivetracking.features.store_detail.ui.viewmodel.StoreDetailScreenModel
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
    @ScreenModelFactoryKey(StoreDetailScreenModel.Factory::class)
    abstract fun bindHiltDetailsScreenModelFactory(
        hiltDetailsScreenModelFactory: StoreDetailScreenModel.Factory
    ): ScreenModelFactory
}
