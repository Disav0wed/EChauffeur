package com.ibrahimrecepserpici.usecases

import com.ibrahimrecepserpici.domain.entity.Coordinate
import com.ibrahimrecepserpici.domain.entity.VehicleInfo
import com.ibrahimrecepserpici.domain.repository.VehicleRepository
import com.ibrahimrecepserpici.domain.usecase.vehicle.VehicleInfosUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers.anyDouble
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import java.io.IOException
import java.util.concurrent.CountDownLatch

@RunWith(JUnit4::class)
class VehicleInfosUseCaseTest {

    private val vehicleInfoRepository = mock<VehicleRepository>()

    var requestLatch = CountDownLatch(1)
    var responseLatch = CountDownLatch(1)

    private val getVehicleInfosUseCase by lazy {
        VehicleInfosUseCase(vehicleInfoRepository)
    }


    @Before
    fun init(){
        requestLatch = CountDownLatch(1)
        responseLatch = CountDownLatch(1)
    }


    @Test
    fun testGetVehicleInfoCompleted(){

        CoroutineScope(Dispatchers.IO).launch {
            whenever(
                vehicleInfoRepository.getVehiclesInRegion(
                    anyDouble(),
                    anyDouble(),
                    anyDouble(),
                    anyDouble()
                )
            ).thenReturn(kotlin.Result.success(emptyList()))
            requestLatch.countDown()
        }
        requestLatch.await()

        var result:List<VehicleInfo>? = null
        CoroutineScope(Dispatchers.IO).launch {
             result = getVehicleInfosUseCase.getVehicleInfosInRegion(Coordinate(0.0,0.0),Coordinate(0.0,0.0)).getOrNull()
            responseLatch.countDown()
        }
        responseLatch.await()
        assert(result != null)
    }

    @Test
    fun testGetVehicleInfoError(){

        var result : Result<List<VehicleInfo>>? = null

        CoroutineScope(Dispatchers.IO).launch {
            whenever(
                vehicleInfoRepository.getVehiclesInRegion(
                    anyDouble(),
                    anyDouble(),
                    anyDouble(),
                    anyDouble()
                )
            ).thenReturn(Result.failure(IOException()))
            requestLatch.countDown()
        }
        requestLatch.await()

        CoroutineScope(Dispatchers.IO).launch {
            result = getVehicleInfosUseCase.getVehicleInfosInRegion(Coordinate(0.0,0.0),Coordinate(0.0,0.0))
            responseLatch.countDown()
        }
        responseLatch.await()
        assert(result!!.isFailure)
    }

}