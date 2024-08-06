package com.absensi.app.history

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.absensi.app.R
import com.absensi.app.data.ApiConfig
import com.absensi.app.data.Pertemuan
import com.absensi.app.data.respone.AbsentRequest
import com.absensi.app.data.respone.MessageResponse
import com.absensi.app.data.respone.PertemuanResponse
import com.absensi.app.utils.DataMapper
import com.absensi.app.utils.parseError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    fun getHistory(
        token: String,
        onSuccess: (List<Pertemuan>) -> Unit,
        onFailure: (String) -> Unit,
        loading: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            loading(true)
            try {
                withContext(Dispatchers.IO) {
                    val call = ApiConfig().getApi().getHistory("Bearer $token")
                    call.enqueue(object : Callback<PertemuanResponse> {
                        override fun onResponse(
                            call: Call<PertemuanResponse>, response: Response<PertemuanResponse>
                        ) {
                            loading(false)
                            if (response.isSuccessful) {
                                val item = response.body()?.data
                                if (item != null) {
                                    val data = DataMapper().responseToMeet(item)
                                    onSuccess(data)
                                }
                            } else {
                                val error = response.errorBody()?.string()
                                onFailure(parseError(error))
                                loading(false)
                            }
                        }

                        override fun onFailure(call: Call<PertemuanResponse>, t: Throwable) {
                            onFailure(getApplication<Application>().getString(R.string.failure))
                            loading(false)
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

    fun absent(
        token: String,
        id: String,
        kode: String,
        message: (String) -> Unit,
        loading: (Boolean) -> Unit
    ) {
        viewModelScope.launch {
            loading(true)
            try {
                withContext(Dispatchers.IO) {
                    val request = AbsentRequest(id, kode)
                    val apiService = ApiConfig().getApi()
                    val call = apiService.absent("Bearer $token", request)

                    call.enqueue(object : Callback<MessageResponse> {
                        override fun onResponse(
                            call: Call<MessageResponse>,
                            response: Response<MessageResponse>
                        ) {
                            if (response.isSuccessful) {
                                val data = response.body()?.message
                                if (data != null) {
                                    message(data)
                                    loading(false)
                                }
                            } else {
                                val error = response.errorBody()?.string()
                                message(parseError(error))
                                loading(false)
                            }
                        }

                        override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                            message(getApplication<Application>().getString(R.string.failure))
                            loading(false)
                        }
                    })
                }
            } catch (e: Exception) {
                message(getApplication<Application>().getString(R.string.failure))
            } finally {
                loading(false)
            }
        }
    }
}