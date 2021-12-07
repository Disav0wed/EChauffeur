package com.ibrahimrecepserpici.echauffeur.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ibrahimrecepserpici.echauffeur.adapter.ItemClickListener
import com.ibrahimrecepserpici.echauffeur.adapter.VehicleInfoListAdapter
import com.ibrahimrecepserpici.echauffeur.databinding.FragmentListBinding
import com.ibrahimrecepserpici.echauffeur.enums.FragmentType
import com.ibrahimrecepserpici.echauffeur.viewmodel.TaxiViewModel

class ListFragment : Fragment(), ItemClickListener {

    private lateinit var vehicleInfoListAdapter: VehicleInfoListAdapter
    private val taxiViewModel: TaxiViewModel by activityViewModels()
    private  var binding: FragmentListBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentListBinding.inflate(inflater)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(activity);
        val divider = DividerItemDecoration(activity,DividerItemDecoration.VERTICAL)
        vehicleInfoListAdapter = VehicleInfoListAdapter()
        vehicleInfoListAdapter.itemClickListener = this
        binding!!.rvVehicleInfo.layoutManager = layoutManager
        binding!!.rvVehicleInfo.adapter = vehicleInfoListAdapter
        binding!!.rvVehicleInfo.addItemDecoration(divider)

        // Observe vehicle info list changes & update recyclerview accordingly
        taxiViewModel.vehicleInfoLiveData.observe(this, Observer {
            vehicleInfoListAdapter.setList(it)
        })

    }

    override fun onRecyclerViewItemClick(position: Int) {
        taxiViewModel.selectedVehicleLiveData.postValue(position)
        taxiViewModel.navigateToFragment?.let { it(FragmentType.MAP) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.rvVehicleInfo?.adapter = null
        binding = null
    }
}