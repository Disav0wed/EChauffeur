package com.ibrahimrecepserpici.domain.usecase.vehicle

import com.ibrahimrecepserpici.domain.entity.Coordinate
import com.ibrahimrecepserpici.domain.entity.VehicleInfo

interface IVehicleInfosUseCase {
    suspend fun getVehicleInfosInRegion(p1: Coordinate, p2: Coordinate): Result<List<VehicleInfo>>
}