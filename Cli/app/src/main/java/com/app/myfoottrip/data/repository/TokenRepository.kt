package com.app.myfoottrip.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.myfoottrip.Application
import com.app.myfoottrip.data.dto.Token
import com.app.myfoottrip.data.dto.User
import com.app.myfoottrip.network.api.TokenApi
import com.app.myfoottrip.util.NetworkResult

private const val TAG = "TokenRepository_싸피"

class TokenRepository {
    private val tokenApi = Application.retrofit.create(TokenApi::class.java)

    // 헤더를 포함한 Retrofit
    private val headerTokenApi = Application.headerRetrofit.create(TokenApi::class.java)

    // 액세스 토큰이 만료되었을 때 리프레시 토큰을 통해서 액세스토큰을 재발급 요청
    private val _getAccessTokenByRefreshTokenResponseLiveData =
        MutableLiveData<NetworkResult<Token>>()
    val getAccessTokenByRefreshTokenResponseLiveData: LiveData<NetworkResult<Token>>
        get() = _getAccessTokenByRefreshTokenResponseLiveData


    // 액세스 토큰을 통해서 유저 데이터를 요청
    private val _getUserDataByAccessTokenResponseLiveData = MutableLiveData<NetworkResult<User>>()
    val getUserDataByAccessTokenResponseLiveData: LiveData<NetworkResult<User>>
        get() = _getUserDataByAccessTokenResponseLiveData

    suspend fun getAccessTokenByRefreshToken(refreshToken: Token) {
        val response = tokenApi.refreshTokenAvailableCheck(refreshToken)

        _getAccessTokenByRefreshTokenResponseLiveData.postValue(NetworkResult.Loading())

        if (response.isSuccessful && response.body() != null) {
            _getAccessTokenByRefreshTokenResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            _getAccessTokenByRefreshTokenResponseLiveData.postValue(
                NetworkResult.Error(
                    response.errorBody()!!.string()
                )
            )
        } else {
            _getAccessTokenByRefreshTokenResponseLiveData.postValue(
                NetworkResult.Error(
                    response.headers().toString()
                )
            )
        }
    } // End of getAccessTokenByRefreshToken

    // AccessToken 헤더를 사용해서 유저 정보가져오기
    suspend fun getUserDataByAccessToken() {
        val response = headerTokenApi.getUserDataByAccessToken()
        
        _getUserDataByAccessTokenResponseLiveData.postValue(NetworkResult.Loading())

        if (response.isSuccessful && response.body() != null) {
            _getUserDataByAccessTokenResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if (response.errorBody() != null) {
            _getUserDataByAccessTokenResponseLiveData.postValue(
                NetworkResult.Error(
                    response.errorBody()!!.string()
                )
            )
        } else {
            _getUserDataByAccessTokenResponseLiveData.postValue(
                NetworkResult.Error(
                    response.headers().toString()
                )
            )
        }

    } // End of getUserDataByAccessToken

} // End of TokenRepository class