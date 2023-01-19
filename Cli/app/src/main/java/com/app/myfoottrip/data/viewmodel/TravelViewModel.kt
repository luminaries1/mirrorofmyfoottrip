package com.app.myfoottrip.data.dto.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.myfoottrip.data.dto.Board
import com.app.myfoottrip.data.dto.Travel
import com.app.myfoottrip.data.repository.TravelRepository
import com.app.myfoottrip.util.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TravelViewModel : ViewModel() {
    private val travelRepository = TravelRepository()
    
    // 선택된 지역 리스트
    private var locationList = arrayListOf<String>()

    //여정 조회 값
    private val _travelData = MutableLiveData<Travel>()
    val travelData: LiveData<Travel>
        get() = _travelData

    //유저별 여정 조회 값
    val travelUserData: LiveData<NetworkResult<ArrayList<Travel>>>
        get() = travelRepository.travelListResponseLiveData

    //여정 기록 state
    private val _travelResponseStatus = MutableLiveData(false)
    val travelResponseStatus: LiveData<Boolean>
        get() = _travelResponseStatus

    fun setLocationList(list : ArrayList<String>){
        locationList = list
    }

    //유저별 여정 확인
    fun getUserTravel(userId : Int){
        viewModelScope.launch {
            TravelRepository().getUserTravel(userId)
        }
    }

    //여정 조회
    fun getTravel(travelId : Int){
        var travelData : Travel?
        viewModelScope.launch {
            travelData = TravelRepository().getTravel(travelId)
            if(travelData != null){
                _travelData.postValue(travelData)
            }
        }
    }

    //여정 추가
    fun makeTravel(travel: Travel){
        viewModelScope.launch {
            TravelRepository().sendTravel(travel)
        }
    }

    //여정 수정

}