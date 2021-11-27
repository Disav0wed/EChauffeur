package com.ibrahimrecepserpici.data.di

import com.ibrahimrecepserpici.domain.repository.VehicleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.ibrahimrecepserpici.data.repositoryimpl.VehicleRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindVehicleRepository(vehicleRepositoryImpl: VehicleRepositoryImpl): VehicleRepository
}