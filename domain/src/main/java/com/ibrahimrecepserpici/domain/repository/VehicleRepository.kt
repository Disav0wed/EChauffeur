package com.ibrahimrecepserpici.domain.repository

import com.ibrahimrecepserpici.domain.entity.VehicleInfo

interface VehicleRepository {

    suspend fun getVehiclesInRegion(p1Latitude: Double,
                                    p1Longitude: Double,
                                    p2Latitude: Double,
                                    p2Longitude: Double): Result<List<VehicleInfo>>
}