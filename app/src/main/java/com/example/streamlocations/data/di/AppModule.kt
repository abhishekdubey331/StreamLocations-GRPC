package com.example.streamlocations.data.di

import android.net.Uri
import com.example.streamlocations.data.api.ApiService
import com.example.streamlocations.data.location.FetchLocationUpdatesUseCase
import com.example.streamlocations.data.location.FetchLocationUpdatesUseCaseImpl
import com.example.streamlocations.data.location.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "http://10.0.2.2:9000/"

    @Provides
    @Singleton
    fun provideServerUri(): ApiService {
        return ApiService(Uri.parse(BASE_URL))
    }

    @Provides
    @Singleton
    fun provideManagedChannel(apiService: ApiService): ManagedChannel {
        return ManagedChannelBuilder.forAddress(
            apiService.serverUri.host, apiService.serverUri.port
        ).usePlaintext().build()
    }

    @Provides
    @Singleton
    fun provideLocationRepository(managedChannel: ManagedChannel): LocationRepository {
        return LocationRepository(managedChannel)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class LocationUseCaseModule {

    @Binds
    abstract fun bindFetchLocationUpdatesUseCase(
        fetchLocationUpdatesUseCaseImpl: FetchLocationUpdatesUseCaseImpl
    ): FetchLocationUpdatesUseCase
}
