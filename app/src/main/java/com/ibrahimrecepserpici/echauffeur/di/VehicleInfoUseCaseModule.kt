package com.ibrahimrecepserpici.echauffeur.di

import com.ibrahimrecepserpici.domain.usecase.vehicle.VehicleInfosUseCase
import com.ibrahimrecepserpici.domain.usecase.vehicle.IVehicleInfosUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class VehicleInfoUseCaseModule {

    @Binds
    @ViewModelScoped
    abstract fun provideVehicleInfoUseCase(vehicleInfosUseCase: VehicleInfosUseCase) : IVehicleInfosUseCase
}