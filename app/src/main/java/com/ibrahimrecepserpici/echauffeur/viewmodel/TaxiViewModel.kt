package com.ibrahimrecepserpici.echauffeur.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ibrahimrecepserpici.domain.entity.Coordinate
import com.ibrahimrecepserpici.domain.usecase.vehicle.IGetVehicleInfosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaxiViewModel @Inject constructor(private val getVehicleInfoUseCase: IGetVehicleInfosUseCase) :
    ViewModel() {

         fun getVehicleInformations(point1: Coordinate, point2: Coordinate){
            GlobalScope.launch(Dispatchers.IO) {
                val infos = getVehicleInfoUseCase.getVehicleInfosInRegion(point1,point2)
                Log.e("TEST",infos.get(0).fleetType)
            }
        }

}