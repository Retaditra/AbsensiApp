package com.absensi.app.absent

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.absensi.app.R
import com.absensi.app.data.ApiConfig
import com.absensi.app.data.respone.AbsentRequest
import com.absensi.app.data.respone.IzinRequest
import com.absensi.app.data.respone.MessageResponse
import com.absensi.app.utils.parseErrorMsg
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AbsentViewModel(application: Application) : AndroidViewModel(application) {

    fun absent(
        token: String,
        request: (AbsentRequest),
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit,
        loading: (Boolean) -> Unit
    ) {
        loading(true)
        val apiService = ApiConfig().getApi()
        val call = apiService.absent("Bearer $token", request)

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

    fun izin(
        token: String,
        request: (IzinRequest),
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit,
        loading: (Boolean) -> Unit
    ) {
        loading(true)
        val apiService = ApiConfig().getApi()
        val call = apiService.izin("Bearer $token", request)

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
}