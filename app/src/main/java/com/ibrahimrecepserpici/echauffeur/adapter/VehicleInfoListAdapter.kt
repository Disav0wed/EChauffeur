package com.ibrahimrecepserpici.echauffeur.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ibrahimrecepserpici.domain.entity.VehicleInfo
import com.ibrahimrecepserpici.echauffeur.R

class VehicleInfoListAdapter : RecyclerView.Adapter<VehicleInfoListAdapter.VehicleInfoViewHolder>() {

    private var vehicleInfoList: MutableList<VehicleInfo> = mutableListOf<VehicleInfo>()
    var itemClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleInfoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vehicle_list_rv_item, parent, false)
        return VehicleInfoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VehicleInfoViewHolder, position: Int) {
        val vehicleInfo = vehicleInfoList[position]

            holder.tvFleetType.text = vehicleInfo.fleetType
            holder.tvVehicleId.text = vehicleInfo.id.toString()

    }

    override fun getItemCount(): Int {
        return vehicleInfoList.size
    }


    @SuppressLint("NotifyDataSetChanged")
    fun setList(vehicleInfoList: List<VehicleInfo>){
        this.vehicleInfoList.clear()
        this.vehicleInfoList.addAll(vehicleInfoList)
        this.notifyDataSetChanged()
    }

    inner class VehicleInfoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvFleetType: TextView = itemView.findViewById(R.id.tvFleetType)
        var tvVehicleId: TextView = itemView.findViewById(R.id.tvVehicleId)
        var btnGoToLocation: ImageButton = itemView.findViewById(R.id.btnLocate)

        init {
            btnGoToLocation.setOnClickListener {
                itemClickListener?.onRecyclerViewItemClick(adapterPosition)
            }
        }

    }
}


