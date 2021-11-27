package com.ibrahimrecepserpici.data.api

import com.ibrahimrecepserpici.data.dto.VehicleInfoListDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MyTaxiApi {

    @GET(".")
    fun getVehicleDataInRegion(
        @Query("p1Lat") p1Latitude: Double,
        @Query("p1Lon") p1Longitude: Double,
        @Query("p2Lat") p2Latitude: Double,
        @Query("p2Lon") p2Longitude: Double)
    : Call<VehicleInfoListDto>
}