package com.ibrahimrecepserpici.data.repositoryimpl

import android.util.Log
import com.ibrahimrecepserpici.data.api.MyTaxiApi
import com.ibrahimrecepserpici.data.dto.VehicleDto
import com.ibrahimrecepserpici.data.dto.toVehicleInfo
import com.ibrahimrecepserpici.domain.entity.VehicleInfo
import com.ibrahimrecepserpici.domain.repository.VehicleRepository
import javax.inject.Inject

class VehicleRepositoryImpl @Inject constructor(private val  api: MyTaxiApi): VehicleRepository{

    override suspend fun getVehiclesInRegion(p1Latitude: Double,
                                             p1Longitude: Double,
                                             p2Latitude: Double,
                                             p2Longitude: Double): List<VehicleInfo> {
        return api.getVehicleDataInRegion(p1Latitude, p1Longitude, p2Latitude, p2Longitude).execute().body()!!.poiList.map { vehicleDto -> vehicleDto.toVehicleInfo() }

    }

}