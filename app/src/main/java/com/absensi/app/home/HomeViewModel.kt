package com.absensi.app.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import com.absensi.app.R
import com.absensi.app.data.ApiConfig
import com.absensi.app.data.Pertemuan
import com.absensi.app.data.respone.PertemuanResponse
import com.absensi.app.data.respone.ProfileData
import com.absensi.app.data.respone.ProfileResponse
import com.absensi.app.utils.DataMapper
import com.absensi.app.utils.UserProfilePreferences
import com.absensi.app.utils.parseError
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    fun userProfile(
        token: String,
        onSuccess: (ProfileData) -> Unit,
        onFailure: (String) -> Unit,
        loading: (Boolean) -> Unit
    ) {
        loading(true)
        val call = ApiConfig().getApi().getProfile("Bearer $token")
        call.enqueue(object : Callback<ProfileResponse> {
            override fun onResponse(
                call: Call<ProfileResponse>, response: Response<ProfileResponse>
            ) {
                if (response.isSuccessful) {
                    val id = response.body()?.data
                    if (id != null) {
                        val data = response.body()?.data
                        UserProfilePreferences.init(getApplication())
                        UserProfilePreferences.saveUserProfile(
                            data?.id.toString(),
                            data?.nama.toString(),
                            data?.email.toString(),
                            data?.nim.toString(),
                            data?.semester.toString(),
                            data?.prodi.toString(),
                            data?.fakultas.toString()
                        )
                        data?.let { onSuccess(it) }
                        loading(false)
                    } else {
                        val error = response.errorBody()?.string()
                        onFailure(parseError(error))
                        loading(false)
                    }
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                onFailure(getApplication<Application>().getString(R.string.failure))
                loading(false)
            }
        })
    }

    fun getToday(
        token: String,
        onSuccess: (List<Pertemuan>) -> Unit,
        onFailure: (String) -> Unit,
        loading: (Boolean) -> Unit
    ) {
        loading(true)
        val call = ApiConfig().getApi().getToday("Bearer $token")
        call.enqueue(object : Callback<PertemuanResponse> {
            override fun onResponse(
                call: Call<PertemuanResponse>, response: Response<PertemuanResponse>
            ) {
                loading(false)
                if (response.isSuccessful) {
                    val error = response.message()
                    Log.d("ERROR", error.toString())
                    val item = response.body()?.data
                    if (item != null) {
                        val data = DataMapper().responseToPertemuan(item)
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
}