package com.ibrahimrecepserpici.data.dto

import com.ibrahimrecepserpici.domain.entity.Coordinate
import com.ibrahimrecepserpici.domain.entity.VehicleInfo


// Data Transfer Object for Vehicle Information
data class VehicleDto(
    val coordinate: Coordinate,
    val fleetType: String,
    val heading: Double,
    val id: Int
)


fun VehicleDto.toVehicleInfo(): VehicleInfo{
    return VehicleInfo(coordinate,fleetType,heading,id)
}
