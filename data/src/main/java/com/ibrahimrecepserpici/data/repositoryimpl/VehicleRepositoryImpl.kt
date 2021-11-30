package com.ibrahimrecepserpici.data.repositoryimpl

import android.util.Log
import com.ibrahimrecepserpici.data.api.MyTaxiApi
import com.ibrahimrecepserpici.data.dto.VehicleDto
import com.ibrahimrecepserpici.data.dto.toVehicleInfo
import com.ibrahimrecepserpici.domain.entity.VehicleInfo
import com.ibrahimrecepserpici.domain.repository.VehicleRepository
import kotlinx.coroutines.*
import java.io.IOException
import java.lang.Exception
import javax.inject.Inject

class VehicleRepositoryImpl @Inject constructor(private val  api: MyTaxiApi): VehicleRepository{

    override suspend fun getVehiclesInRegion(p1Latitude: Double,
                                             p1Longitude: Double,
                                             p2Latitude: Double,
                                             p2Longitude: Double): Result<List<VehicleInfo>> {

        var result:Result<List<VehicleInfo>> = Result.failure(NullPointerException("Result not initialized yet!"))

        val deferredResult = CoroutineScope(Dispatchers.IO).async {
            runCatching{
                api.getVehicleDataInRegion(p1Latitude, p1Longitude, p2Latitude, p2Longitude).execute().body()
            }.onSuccess {

                result = if (it != null) {
                    Result.success(it.poiList.map { vehicleDto -> vehicleDto.toVehicleInfo() })
                }else{
                    // Throwing IO exception if VehicleInfoListDto null
                    Result.failure(IOException())
                }

            }.onFailure { result = Result.failure(it) }
        }

        deferredResult.await()

        return result //api.getVehicleDataInRegion(p1Latitude, p1Longitude, p2Latitude, p2Longitude).execute().body()!!.poiList.map { vehicleDto -> vehicleDto.toVehicleInfo() }

    }

}