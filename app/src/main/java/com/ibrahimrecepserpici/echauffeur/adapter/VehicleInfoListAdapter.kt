package com.ibrahimrecepserpici.echauffeur.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ibrahimrecepserpici.domain.entity.VehicleInfo
import com.ibrahimrecepserpici.echauffeur.databinding.VehicleListRvItemBinding

class VehicleInfoListAdapter : RecyclerView.Adapter<VehicleInfoListAdapter.VehicleInfoViewHolder>() {

    private var vehicleInfoList: MutableList<VehicleInfo> = mutableListOf()
    var itemClickListener: ItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleInfoViewHolder {
        val binding = VehicleListRvItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VehicleInfoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VehicleInfoViewHolder, position: Int) {
        val vehicleInfo = vehicleInfoList[position]
            holder.binding.tvFleetType.text = vehicleInfo.fleetType
            holder.binding.tvVehicleId.text = vehicleInfo.id.toString()
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

    inner class VehicleInfoViewHolder(var binding: VehicleListRvItemBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnLocate.setOnClickListener {
                itemClickListener?.onRecyclerViewItemClick(adapterPosition)
            }
        }

    }
}


