package com.ibrahimrecepserpici.echauffeur.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ibrahimrecepserpici.domain.entity.VehicleInfo
import com.ibrahimrecepserpici.echauffeur.R
import com.ibrahimrecepserpici.echauffeur.activity.IMainActivity
import com.ibrahimrecepserpici.echauffeur.viewmodel.TaxiViewModel

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var map : GoogleMap
    lateinit var taxiViewModel: TaxiViewModel
    private var vehicleInfoList: MutableList<VehicleInfo> = mutableListOf<VehicleInfo>()
    lateinit var iMainActivity: IMainActivity<TaxiViewModel>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        iMainActivity = context as IMainActivity<TaxiViewModel>
        taxiViewModel = iMainActivity.viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!this::map.isInitialized){
            initMap()
        }
    }


    fun initMap(){
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.gMapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Coordinates for  Hamburg
        val hamburgCoord = LatLng(53.551086, 9.993682)
        map.moveCamera(CameraUpdateFactory.newLatLng(hamburgCoord))

        // Observe vehicle info list changes & update recyclerview accordingly
        taxiViewModel.vehicleInfoLiveData.observe(this, Observer {
            for ( vehicleInfo: VehicleInfo in  it){
                map.addMarker(MarkerOptions()
                    .position(LatLng(vehicleInfo.coordinate.latitude,vehicleInfo.coordinate.longitude))
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.car_white_64x30))
                    .title(vehicleInfo.fleetType)
                    .snippet("ID : "+vehicleInfo.id)
                    .rotation(vehicleInfo.heading.toFloat())
                    .anchor(0.5f,0.5f))
            }

            this.vehicleInfoList.clear()
            this.vehicleInfoList.addAll(it)
        })

        taxiViewModel.selectedVehicleLiveData.observe(this, Observer {

            var vehicleInfo = vehicleInfoList[it]
            map.moveCamera(CameraUpdateFactory.newLatLng(LatLng(vehicleInfo.coordinate.latitude,vehicleInfo.coordinate.longitude)))

        })
    }


}