package com.absensi.app.data.respone

import com.google.gson.annotations.SerializedName

data class UpdateRequest(
    @SerializedName("current_password")
    val current_password: String,
    @SerializedName("new_password")
    val new_password: String,
    @SerializedName("new_password_confirmation")
    val new_password_confirmation: String,
)