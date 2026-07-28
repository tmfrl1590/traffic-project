package com.system.traffic.admob.di
import com.system.traffic.BuildConfig
import com.traffic.design.component.AdConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object AdModule {
    @Provides
    @Singleton
    fun provideAdConfig(): AdConfig {
        return AdConfig(
            adUnitId = BuildConfig.AD_UNIT_ID
        )
    }
}