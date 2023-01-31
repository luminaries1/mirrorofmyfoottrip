package com.app.myfoottrip.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.myfoottrip.data.dto.Place
import com.app.myfoottrip.data.dto.Travel
import com.app.myfoottrip.databinding.ListItmeTravelEditSaveBinding

class TravelEditSaveItemAdapter(val context: Context, private val placeList: List<Place>) :
    RecyclerView.Adapter<TravelEditSaveItemAdapter.TravelEditSaveItemHolder>() {
    private lateinit var binding: ListItmeTravelEditSaveBinding
    private val tempList : List<Place> = emptyList()

    //클릭리스너 선언
    private lateinit var itemClickListner: ItemClickListener


    inner class TravelEditSaveItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindInfo(data: Place) {
            binding.travelItemAddressTv.text = data.address
            binding.travelItemDateTv.text = data.memo
        }

    } // End of inner class

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TravelEditSaveItemHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: TravelEditSaveItemHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = placeList.size


    interface ItemClickListener {
        fun onEditButtonClick(position: Int, placeData: Place) //전체 클릭한 경우
    }

    //클릭리스너 등록 매소드
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListner = itemClickListener
    } // End of setItemClickListener
}