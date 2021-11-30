package com.ibrahimrecepserpici.domain.usecase.vehicle

import com.ibrahimrecepserpici.domain.entity.Coordinate
import com.ibrahimrecepserpici.domain.entity.VehicleInfo
import com.ibrahimrecepserpici.domain.repository.VehicleRepository
import javax.inject.Inject

class VehicleInfosUseCase @Inject constructor(private val repository: VehicleRepository): IVehicleInfosUseCase{
    override suspend fun getVehicleInfosInRegion(
        p1: Coordinate,
        p2: Coordinate
    ): Result<List<VehicleInfo>> {
        return repository.getVehiclesInRegion(p1.latitude,p1.longitude,p2.latitude,p2.longitude)
    }

}