package com.ibrahimrecepserpici.echauffeur.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.ibrahimrecepserpici.domain.entity.Coordinate
import com.ibrahimrecepserpici.domain.entity.VehicleInfo
import com.ibrahimrecepserpici.domain.usecase.vehicle.VehicleInfosUseCase
import com.ibrahimrecepserpici.echauffeur.enums.FragmentType
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.util.concurrent.CountDownLatch

class TaxiViewModelTest{

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    private val vehicleInfosUseCase = mock<VehicleInfosUseCase>()

    var requestLatch = CountDownLatch(1)
    var responseLatch = CountDownLatch(1)

    private val taxiViewModel by lazy {
        TaxiViewModel(vehicleInfosUseCase)
    }

    @Before
    fun init(){
        requestLatch = CountDownLatch(1)
        responseLatch = CountDownLatch(1)
    }

    @Test
    fun testVehicleInfoObserved(){

        var result : List<VehicleInfo>? = null
        CoroutineScope(Dispatchers.IO).launch {
            whenever(
                vehicleInfosUseCase.getVehicleInfosInRegion(
                    Coordinate(0.0,0.0),
                    Coordinate(0.0,0.0)
                )
            ).thenReturn(Result.success(mutableListOf(VehicleInfo(Coordinate(0.0,0.0),"TAXI",0.0,2451))))
            requestLatch.countDown()
        }
        requestLatch.await()
        taxiViewModel.fetchVehicleInformationInArea(
            Coordinate(0.0,0.0),
            Coordinate(0.0,0.0)
        )

        taxiViewModel.vehicleInfoLiveData.observeForever(Observer {
            result = it
            responseLatch.countDown()
        })
        responseLatch.await()

        assert(taxiViewModel.vehicleInfoLiveData.hasObservers())
        assert(result != null)
    }

    @Test
    fun testSelectedVehicleObserved(){

        var result : Int? = null

        taxiViewModel.selectedVehicleLiveData.postValue(3)

        taxiViewModel.selectedVehicleLiveData.observeForever(Observer {
            result = it
            responseLatch.countDown()
        })
        responseLatch.await()
        assert(taxiViewModel.selectedVehicleLiveData.hasObservers())
        assert(result != null)

    }

    @Test
    fun testNavigateToFragmentCallBack(){

        var result : FragmentType? = null

        taxiViewModel.navigateToFragment = {

            result = it
            responseLatch.countDown()
        }

        taxiViewModel.navigateToFragment!!.invoke(FragmentType.MAP)
        responseLatch.await()

        assert(result != null)
        assert(result!! == FragmentType.MAP)
    }

}