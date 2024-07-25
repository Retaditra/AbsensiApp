package com.absensi.app.jadwal

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.absensi.app.R
import com.absensi.app.data.ApiConfig
import com.absensi.app.data.Matkul
import com.absensi.app.data.respone.MatkulResponse
import com.absensi.app.utils.DataMapper
import com.absensi.app.utils.parseError
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JadwalViewModel(application: Application) : AndroidViewModel(application) {

    fun getJadwal(
        token: String,
        onSuccess: (List<Matkul>) -> Unit,
        onFailure: (String) -> Unit,
        loading: (Boolean) -> Unit
    ) {
        loading(true)
        val apiService = ApiConfig().getApi()
        val call = apiService.getJadwal("Bearer $token")

        call.enqueue(object : Callback<MatkulResponse> {
            override fun onResponse(
                call: Call<MatkulResponse>,
                response: Response<MatkulResponse>
            ) {
                if (response.isSuccessful) {
                    val item = response.body()?.data
                    if (item != null) {
                        val data = DataMapper().responseToMatkul(item)
                        onSuccess(data)
                        loading(false)
                    }
                } else {
                    val error = response.errorBody()?.string()
                    onFailure(parseError(error.toString()))
                    loading(false)
                }
            }

            override fun onFailure(call: Call<MatkulResponse>, t: Throwable) {
                onFailure(getApplication<Application>().getString(R.string.failure))
                loading(false)
            }
        })
    }
}