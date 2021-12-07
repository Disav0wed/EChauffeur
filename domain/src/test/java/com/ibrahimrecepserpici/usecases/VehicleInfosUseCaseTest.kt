package com.ibrahimrecepserpici.usecases

import com.ibrahimrecepserpici.domain.entity.Coordinate
import com.ibrahimrecepserpici.domain.entity.VehicleInfo
import com.ibrahimrecepserpici.domain.repository.VehicleRepository
import com.ibrahimrecepserpici.domain.usecase.vehicle.VehicleInfosUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyDouble
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import java.io.IOException

@ExperimentalCoroutinesApi
class VehicleInfosUseCaseTest {

    private val vehicleInfoRepository = mock(VehicleRepository::class.java)

    private val testDispatcher = StandardTestDispatcher()
    private val testCoroutineScope = TestScope(testDispatcher)


    private val getVehicleInfosUseCase by lazy {
        VehicleInfosUseCase(vehicleInfoRepository)
    }

    @Before
    fun init(){
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanUp(){
        Dispatchers.resetMain()
    }

    @Test
    fun testGetVehicleInfoCompleted(){

        var result:List<VehicleInfo>? = null

        testCoroutineScope.runTest {
            whenever(
                vehicleInfoRepository.getVehiclesInRegion(
                    anyDouble(),
                    anyDouble(),
                    anyDouble(),
                    anyDouble()
                )
            ).thenReturn(Result.success(emptyList()))

            result = getVehicleInfosUseCase.getVehicleInfosInRegion(Coordinate(0.0,0.0),Coordinate(0.0,0.0)).getOrNull()
        }

        assert(result != null)
    }

    @Test
    fun testGetVehicleInfoError(){

        var result : Result<List<VehicleInfo>>? = null

        testCoroutineScope.runTest {
            whenever(
                vehicleInfoRepository.getVehiclesInRegion(
                    anyDouble(),
                    anyDouble(),
                    anyDouble(),
                    anyDouble()
                )
            ).thenReturn(Result.failure(IOException()))

            result = getVehicleInfosUseCase.getVehicleInfosInRegion(Coordinate(0.0,0.0),Coordinate(0.0,0.0))
        }

        assert(result!!.isFailure)
    }


    @Test
    fun testGetVehicleInfoResponse(){

        val testInfo = VehicleInfo(Coordinate(0.0,0.0),"TAXI",0.0,3761)
        var result:List<VehicleInfo>? = null
        testCoroutineScope.runTest {

            whenever(
                vehicleInfoRepository.getVehiclesInRegion(
                    anyDouble(),
                    anyDouble(),
                    anyDouble(),
                    anyDouble()
                )
            ).thenReturn(Result.success(mutableListOf(testInfo)))
            result = getVehicleInfosUseCase.getVehicleInfosInRegion(Coordinate(0.0,0.0),Coordinate(0.0,0.0)).getOrNull()

        }
        assert(result != null)
        assert(result!!.size == 1)
        assert(result!![0] == testInfo)
    }

}