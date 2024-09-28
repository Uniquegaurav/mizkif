package com.example.mymiso.di

import android.content.Context
import com.example.mymiso.data.repository.DeliveryPartnerLocationRepositoryImpl
import com.example.mymiso.data.repository.LocationTrackerRepositoryImpl
import com.example.mymiso.domain.repository.DeliveryPartnerLocationRepository
import com.example.mymiso.domain.repository.LocationServiceRepository
import com.example.mymiso.domain.repository.LocationTrackerRepository
import com.example.mymiso.domain.use_cases.ObserveLocationUpdateUseCase
import com.example.mymiso.domain.use_cases.StartLocationTrackingUseCase
import com.example.mymiso.domain.use_cases.StopLocationTrackingUseCase
import com.example.mymiso.domain.use_cases.TrackDeliveryPartnerLocationUseCase
import com.example.mymiso.framework.location.FusedLocationService
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TrackingModule {

    @Singleton
    @Provides
    fun provideFusedLocationClient(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }

    @Singleton
    @Provides
    fun provideLocationServiceRepository(fusedLocationProviderClient: FusedLocationProviderClient): LocationServiceRepository {
        return FusedLocationService(fusedLocationProviderClient)
    }

    @Singleton
    @Provides
    fun provideLocationTracker(locationServiceRepository: LocationServiceRepository): LocationTrackerRepository {
        return LocationTrackerRepositoryImpl(locationServiceRepository)
    }

    @Singleton
    @Provides
    fun provideStartLocationTrackingUseCase(locationTrackerRepository: LocationTrackerRepository): StartLocationTrackingUseCase {
        return StartLocationTrackingUseCase(locationTrackerRepository)
    }

    @Singleton
    @Provides
    fun provideStopLocationTrackingUseCase(locationTrackerRepository: LocationTrackerRepository): StopLocationTrackingUseCase {
        return StopLocationTrackingUseCase(locationTrackerRepository)
    }

    @Singleton
    @Provides
    fun provideObserveLocationUpdatesUseCase(locationTrackerRepository: LocationTrackerRepository): ObserveLocationUpdateUseCase {
        return ObserveLocationUpdateUseCase(locationTrackerRepository)
    }

    @Singleton
    @Provides
    fun provideDeliveryPartnerLocationRepository(): DeliveryPartnerLocationRepository {
        return DeliveryPartnerLocationRepositoryImpl()
    }

    @Singleton
    @Provides
    fun provideTrackDeliveryPartnerLocationUseCase(repository: DeliveryPartnerLocationRepository): TrackDeliveryPartnerLocationUseCase {
        return TrackDeliveryPartnerLocationUseCase(repository)
    }


}