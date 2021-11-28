package com.ibrahimrecepserpici.echauffeur.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.ibrahimrecepserpici.domain.entity.Coordinate
import com.ibrahimrecepserpici.echauffeur.R
import com.ibrahimrecepserpici.echauffeur.viewmodel.TaxiViewModel
import dagger.hilt.android.AndroidEntryPoint




@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    private val taxiViewModel: TaxiViewModel by viewModels()
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        taxiViewModel.getVehicleInformations(
            Coordinate(53.694865, 9.757589),
            Coordinate(53.394655, 10.099891)
        )




        navController = findNavController(R.id.nav_host_fragment)
        initBottomNavMenu(navController)

    }

    private fun initBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNav.setupWithNavController(navController)

        bottomNav.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                R.id.bottom_nav_menu_item_list_fragment -> navController.navigate(R.id.action_global_listFragment)
                R.id.bottom_nav_menu_item_map_fragment -> navController.navigate(R.id.action_global_mapFragment)
            }
            true
        }
    }






}