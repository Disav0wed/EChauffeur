package com.ibrahimrecepserpici.echauffeur.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ibrahimrecepserpici.domain.entity.Coordinate
import com.ibrahimrecepserpici.echauffeur.R
import com.ibrahimrecepserpici.echauffeur.viewmodel.TaxiViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    private val taxiViewModel: TaxiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        taxiViewModel.getVehicleInformations(
            Coordinate(53.694865, 9.757589),
            Coordinate(53.394655, 10.099891)
        )




    }
}