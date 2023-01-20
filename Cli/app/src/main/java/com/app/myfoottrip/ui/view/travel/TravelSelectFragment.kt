package com.app.myfoottrip.ui.view.travel

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.app.myfoottrip.R
import com.app.myfoottrip.data.dto.Travel
import com.app.myfoottrip.data.dto.viewmodel.TravelViewModel
import com.app.myfoottrip.databinding.FragmentTravelSelectBinding
import com.app.myfoottrip.ui.adapter.TravelAdapter
import com.app.myfoottrip.ui.base.BaseFragment
import com.app.myfoottrip.util.NetworkResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "TravelSelectFragment_싸피"
class TravelSelectFragment : BaseFragment<FragmentTravelSelectBinding>(
    FragmentTravelSelectBinding::bind, R.layout.fragment_travel_select
) {
    private var type = 0 // 0 : 여정 기록, 1 : 마이페이지, 2 : 게시글 작성

    private val travelViewModel by activityViewModels<TravelViewModel>()
    private lateinit var travelAdapter: TravelAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //type 받는 코드
        type = requireArguments().getInt("type")
        Log.d(TAG, "onViewCreated: type : $type")
        initialize()
    }

    private fun initialize(){
        initObseve()
        setData()
        if(type == 0){ //여정 선택 부분
            binding.tvTravelTitle.text = "여정을 선택해주세요"
            binding.btnSave.visibility = View.VISIBLE
            binding.btnSave.text = "+ 여정 새로 만들기"
        }else if(type == 1){ //여정 관리 부분
            binding.tvTravelTitle.text = "나의 여정"
            binding.btnSave.visibility = View.GONE
        }else{ //게시글

        }
        initAdapter()
        setListener()

    }

    private fun initAdapter(){
        travelAdapter = TravelAdapter(arrayListOf(), type)
        binding.rvTravel.adapter = travelAdapter

        travelAdapter.setItemClickListener(object : TravelAdapter.ItemClickListener{
            override fun onAllClick(position: Int, travelDto: Travel) {
                //TODO : 여정 확인 페이지로 이동
            }

            override fun onChipClick(type: Int, position: Int, travelDto: Travel) {
                if(type == 0){ //여정 선택
                    changeSelected(position)
                }else{ //여정 삭제
                    //TODO : 여정 삭제 dialog
                }
            }

        })
    }

    private fun setListener(){
        binding.apply {
            ivBack.setOnClickListener{
                travelViewModel.getUserData()
//                Log.d(TAG, "data: ${travelViewModel.travelUserData.value} ")
//                findNavController().popBackStack()
            }
            btnSave.setOnClickListener{
                //TODO : select 된 상태이면 -> 하는거
                findNavController().navigate(R.id.action_travelSelectFragment_to_travelLocationSelectFragment)
            }
        }
    }
    
    private fun initObseve(){
        travelViewModel.travelUserData.observe(viewLifecycleOwner) {
            Log.d(TAG, "initObseve: ")
            when (it) {
                is NetworkResult.Success -> {
                    Log.d(TAG, "initObseve: Success=====")
                    if(it.data != null){
                        Log.d(TAG, "initObseve: ${it.data}")
                        val boardList = it.data
                        travelAdapter.setList(boardList)
                        travelAdapter.notifyDataSetChanged()
                    }
                }
                is NetworkResult.Error -> {
                    Log.d(TAG, "userTravel 체크 Error: ${it.data}")
                }
                is NetworkResult.Loading -> {
                    //TODO: 로딩바
                }
            }
        }
    }


    private fun setData(){ //TODO : DB에서 값 가져와서 넣기
        CoroutineScope(Dispatchers.IO).launch {
            travelViewModel.getUserTravel(1)
        }
    }

    private fun changeSelected(position: Int){
        travelAdapter.apply { 
            var lastPos = getSelected()
            if(position == lastPos){ //선택 해제
                setSelected(-1)
                binding.btnSave.text = "+ 여정 새로 만들기"
            }else{ //선택
                setSelected(position)
                binding.btnSave.text = "선택 완료"
            }
            notifyItemChanged(lastPos) // 선택 해제
            notifyItemChanged(position) // 선택
        }
    }
}