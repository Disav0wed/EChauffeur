package com.ibrahimrecepserpici.echauffeur.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.ibrahimrecepserpici.domain.entity.Coordinate
import com.ibrahimrecepserpici.echauffeur.R
import com.ibrahimrecepserpici.echauffeur.databinding.ActivityMainBinding
import com.ibrahimrecepserpici.echauffeur.enums.FragmentType
import com.ibrahimrecepserpici.echauffeur.viewmodel.TaxiViewModel
import dagger.hilt.android.AndroidEntryPoint




@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

    val viewModel: TaxiViewModel by viewModels()
    private lateinit var navController : NavController
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.fetchVehicleInformationInArea(
            Coordinate(53.694865, 9.757589),
            Coordinate(53.394655, 10.099891)
        )

        navController = findNavController(R.id.nav_host_fragment)
        initBottomNavMenu(navController)

        viewModel.navigateToFragment = {
            navigateToFragment(it)
        }
    }

    private fun initBottomNavMenu(navController: NavController) {
        binding.bottomNav.setupWithNavController(navController)
        binding.bottomNav.setOnItemSelectedListener { menuItem ->
            when(menuItem.itemId){
                FragmentType.LIST.getMenuItemId() -> navController.navigate(FragmentType.LIST.getFragmentNavigationAction())
                FragmentType.MAP.getMenuItemId() -> navController.navigate(FragmentType.MAP.getFragmentNavigationAction())
            }
            true
        }
    }

    private fun navigateToFragment(fragmentType: FragmentType){
        binding.bottomNav.selectedItemId = fragmentType.getMenuItemId()
        navController.navigate(fragmentType.getFragmentNavigationAction())
    }

}