package com.example.penjualan_desianasf

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.penjualan_desianasf.room.TbBarang
import kotlinx.android.synthetic.main.activity_adapter_barang.view.*

class AdapterBarang (private val barang :ArrayList<TbBarang>,private val listener:OnAdapterListener) :
    RecyclerView.Adapter<AdapterBarang.BrgViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrgViewHolder {
        return BrgViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.activity_adapter_barang,parent,false)
        )
    }

    override fun onBindViewHolder(holder: BrgViewHolder, position: Int) {
        val brg = barang[position]
        holder.view.tvharga.text = brg.hrg_brg.toString()
        holder.view.tvNama.text = brg.nm_brg
        holder.view.tvNama.setOnClickListener {
            listener.OnClick(brg)
        }
        holder.view.icEdit.setOnClickListener {
           listener.OnUpdate(brg)
        }
        holder.view.icDelete.setOnClickListener {
            listener.OnDelete(brg)
        }
    }

    override fun getItemCount() = barang.size

    class BrgViewHolder (val view: View): RecyclerView.ViewHolder(view)

    fun setData(list: List<TbBarang>){
        barang.clear()
        barang.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun OnClick(barang: TbBarang)
        fun OnUpdate(barang: TbBarang)
        fun OnDelete(barang: TbBarang)
    }

}