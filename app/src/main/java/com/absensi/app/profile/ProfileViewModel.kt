package com.absensi.app.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.absensi.app.R
import com.absensi.app.data.ApiConfig
import com.absensi.app.data.Matkul
import com.absensi.app.data.respone.MatkulResponse
import com.absensi.app.data.respone.MessageResponse
import com.absensi.app.data.respone.UpdateRequest
import com.absensi.app.utils.DataMapper
import com.absensi.app.utils.parseError
import com.absensi.app.utils.parseErrorMsg
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    fun getMatkulProfile(
        token: String,
        onSuccess: (List<Matkul>) -> Unit,
        onFailure: (String) -> Unit,
        loading: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            loading(true)
            try {
                withContext(Dispatchers.IO) {
                    val call = ApiConfig().getApi().getAllMatkul("Bearer $token")
                    call.enqueue(object : Callback<MatkulResponse> {
                        override fun onResponse(
                            call: Call<MatkulResponse>, response: Response<MatkulResponse>
                        ) {
                            loading(false)
                            if (response.isSuccessful) {
                                val item = response.body()?.data
                                if (item != null) {
                                    val data = DataMapper().responseToMatkul(item)
                                    onSuccess(data)
                                }
                            } else {
                                val error = response.errorBody()?.string()
                                onFailure(parseError(error.toString()))
                            }
                        }
                        override fun onFailure(call: Call<MatkulResponse>, t: Throwable) {
                            onFailure(getApplication<Application>().getString(R.string.failure))
                        }
                    })
                }
            } catch (e: Exception) {
                onFailure(getApplication<Application>().getString(R.string.failure))
            } finally {
                loading(false)
            }
        }
    }

    fun updateProfile(
        token: String,
        request: (UpdateRequest),
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit,
        loading: (Boolean) -> Unit
    ) {
        loading(true)
        val apiService = ApiConfig().getApi()
        val call = apiService.getUpdate("Bearer $token", request)

        call.enqueue(object : Callback<MessageResponse> {
            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {
                if (response.isSuccessful) {
                    val message = response.body()?.message
                    message?.let { onSuccess(it) }
                    loading(false)
                } else {
                    val error = response.errorBody()?.string()
                    onFailure(parseErrorMsg(error))
                    loading(false)
                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                onFailure(getApplication<Application>().getString(R.string.failure))
                loading(false)
            }
        })
    }

    fun logout(
        token: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit,
        loading: (Boolean) -> Unit
    ) {
        loading(true)
        val apiService = ApiConfig().getApi()
        val call = apiService.logout("Bearer $token")

        call.enqueue(object : Callback<MessageResponse> {
            override fun onResponse(
                call: Call<MessageResponse>,
                response: Response<MessageResponse>
            ) {
                if (response.isSuccessful) {
                    val message = response.body()?.message
                    message?.let { onSuccess(it) }
                    loading(false)
                } else {
                    val error = response.errorBody()?.string()
                    onFailure(parseError(error.toString()))
                    loading(false)
                }
            }

            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                onFailure(getApplication<Application>().getString(R.string.failure))
                loading(false)
            }
        })
    }
}