package com.absensi.app.data.respone

import com.google.gson.annotations.SerializedName

data class MessageResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: String? = null
)