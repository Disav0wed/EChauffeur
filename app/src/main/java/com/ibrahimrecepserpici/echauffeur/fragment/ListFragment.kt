package com.ibrahimrecepserpici.echauffeur.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ibrahimrecepserpici.echauffeur.R
import com.ibrahimrecepserpici.echauffeur.activity.IMainActivity
import com.ibrahimrecepserpici.echauffeur.adapter.ItemClickListener
import com.ibrahimrecepserpici.echauffeur.adapter.VehicleInfoListAdapter
import com.ibrahimrecepserpici.echauffeur.viewmodel.TaxiViewModel
import java.lang.ClassCastException

class ListFragment : Fragment(), ItemClickListener {

    lateinit var rvVehicleInfos: RecyclerView
    lateinit var vehicleInfoListAdapter: VehicleInfoListAdapter
    lateinit var taxiViewModel: TaxiViewModel
    lateinit var iMainActivity: IMainActivity<TaxiViewModel>

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {

            iMainActivity = context as IMainActivity<TaxiViewModel>

            taxiViewModel = iMainActivity.viewModel
        }catch (castException:ClassCastException){
            Log.e("ListFragment","Activity does not implement the interface")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvVehicleInfos = view.findViewById(R.id.rv_vehicle_info)

        val layoutManager = LinearLayoutManager(activity);
        val divider = DividerItemDecoration(activity,DividerItemDecoration.VERTICAL)
        vehicleInfoListAdapter = VehicleInfoListAdapter()
        vehicleInfoListAdapter.itemClickListener = this
        rvVehicleInfos.layoutManager = layoutManager
        rvVehicleInfos.adapter = vehicleInfoListAdapter
        rvVehicleInfos.addItemDecoration(divider)

        // Observe vehicle info list changes & update recyclerview accordingly
        taxiViewModel.vehicleInfoLiveData.observe(this, Observer {
            vehicleInfoListAdapter.setList(it)
        })

    }

    override fun onRecyclerViewItemClick(position: Int) {
        taxiViewModel.selectedVehicleLiveData.postValue(position)
        iMainActivity.navigateToMapFragment()
    }
}