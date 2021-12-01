package com.ibrahimrecepserpici.echauffeur.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
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
import com.ibrahimrecepserpici.echauffeur.databinding.FragmentMapBinding
import com.ibrahimrecepserpici.echauffeur.viewmodel.TaxiViewModel

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var map : GoogleMap
    private lateinit var mapFragment : SupportMapFragment
    private val taxiViewModel: TaxiViewModel by activityViewModels()
    private var vehicleInfoList: MutableList<VehicleInfo> = mutableListOf()
    private lateinit var binding: FragmentMapBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMapBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (!this::map.isInitialized){
            initMap()
        }
    }

    private fun initMap(){
        mapFragment = childFragmentManager
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

            val vehicleInfo = vehicleInfoList[it]
            map.moveCamera(CameraUpdateFactory.newLatLng(LatLng(vehicleInfo.coordinate.latitude,vehicleInfo.coordinate.longitude)))

        })
    }


    override fun onStart() {
        super.onStart()
        mapFragment.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapFragment.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapFragment.onDestroy()
    }

    override fun onPause() {
        mapFragment.onPause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapFragment.onResume()
    }


}