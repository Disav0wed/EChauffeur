package com.ibrahimrecepserpici.echauffeur.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibrahimrecepserpici.domain.entity.Coordinate
import com.ibrahimrecepserpici.domain.entity.VehicleInfo
import com.ibrahimrecepserpici.domain.usecase.vehicle.IGetVehicleInfosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class TaxiViewModel @Inject constructor(private val getVehicleInfoUseCase: IGetVehicleInfosUseCase) :
    ViewModel() {

         fun getVehicleInformations(point1: Coordinate, point2: Coordinate) : Deferred<Result<List<VehicleInfo>>>{
            return GlobalScope.async {
                kotlin.runCatching {
                    getVehicleInfoUseCase.getVehicleInfosInRegion(point1,point2)
                }
            }
        }



}