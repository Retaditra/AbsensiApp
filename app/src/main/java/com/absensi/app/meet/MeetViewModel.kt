package com.absensi.app.meet

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.absensi.app.R
import com.absensi.app.data.ApiConfig
import com.absensi.app.data.Pertemuan
import com.absensi.app.data.respone.PertemuanResponse
import com.absensi.app.utils.DataMapper
import com.absensi.app.utils.parseErrorMsg
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MeetViewModel(application: Application) : AndroidViewModel(application) {

    fun getMeet(
        token: String,
        id: String,
        onSuccess: (List<Pertemuan>) -> Unit,
        onFailure: (String) -> Unit,
        loading: (Boolean) -> Unit
    ) {
        loading(true)
        val apiService = ApiConfig().getApi()
        val call = apiService.getMeet("Bearer $token", id)

        call.enqueue(object : Callback<PertemuanResponse> {
            override fun onResponse(
                call: Call<PertemuanResponse>,
                response: Response<PertemuanResponse>
            ) {
                if (response.isSuccessful) {
                    val error = response.message()
                    Log.d("ERROR", error.toString())
                    val item = response.body()?.data
                    if (item != null) {
                        val data = DataMapper().responseToMeet(item)
                        onSuccess(data)
                    }
                    loading(false)
                } else {
                    val error = response.errorBody()?.string()
                    onFailure(parseErrorMsg(error))
                    loading(false)
                }
            }

            override fun onFailure(call: Call<PertemuanResponse>, t: Throwable) {
                onFailure(getApplication<Application>().getString(R.string.failure))
                loading(false)
            }
        })
    }
}