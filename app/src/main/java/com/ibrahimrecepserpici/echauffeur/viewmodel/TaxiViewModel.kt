package com.ibrahimrecepserpici.echauffeur.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ibrahimrecepserpici.domain.entity.Coordinate
import com.ibrahimrecepserpici.domain.entity.VehicleInfo
import com.ibrahimrecepserpici.domain.usecase.vehicle.IVehicleInfosUseCase
import com.ibrahimrecepserpici.echauffeur.enums.FragmentType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class TaxiViewModel @Inject constructor(private val vehicleInfoUseCase: IVehicleInfosUseCase) :
    ViewModel() {

    /**
     * Holds & emits the vehicle info list which fetched from api
     */
    var vehicleInfoLiveData = MutableLiveData<List<VehicleInfo>>()

    /**
     * Holds & emits the number of which vehicle is selected
     */
    var selectedVehicleLiveData = MutableLiveData<Int>()

    /**
     * A callback to trigger activity to change fragments
     */
    var navigateToFragment: ((FragmentType)->Unit)? = null

    /**
     * Fetches the vehicle information from given area and emits the result by using MutableLiveData(vehicleInfoLiveData)
     *
     * @param point1 corner of the rectangular area to be searched
     * @param point2 diagonal corner of the rectangular area to be searched
     */
    fun fetchVehicleInformationInArea(point1: Coordinate, point2: Coordinate){
        CoroutineScope(Dispatchers.IO).launch {
            kotlin.runCatching {
                val vehicleInfoResult = vehicleInfoUseCase.getVehicleInfosInRegion(point1,point2)
                vehicleInfoResult
                    .onSuccess {
                    vehicleInfoLiveData.postValue(it)
                }
                    .onFailure {
                    Log.e("Error",it.stackTraceToString())
                }

            }
        }
    }
}