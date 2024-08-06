package com.absensi.app.utils

import android.content.Context
import android.util.TypedValue
import android.widget.Button
import androidx.core.content.ContextCompat
import com.absensi.app.R
import com.absensi.app.data.Pertemuan

object ButtonUtils {

    fun setButtonTextAndStyle(button: Button, pertemuan: Pertemuan) {
        val text: String
        val textSize: Float
        val textColor: Int

        when {
            pertemuan.kode_absensi == "0" && pertemuan.status == "0" -> {
                text = "Absen"
                textSize = 12f
                textColor = ContextCompat.getColor(button.context, R.color.white)
            }
            pertemuan.kode_absensi == "1" && pertemuan.status == "0" -> {
                text = "Absen"
                textSize = 12f
                textColor = ContextCompat.getColor(button.context, R.color.white)
            }
            pertemuan.kode_absensi == "1" && pertemuan.status == "1" -> {
                text = "Proses"
                textSize = 10f
                textColor = ContextCompat.getColor(button.context, R.color.white)
            }
            pertemuan.kode_absensi == "1" && pertemuan.status == "2" -> {
                text = "Proses"
                textSize = 10f
                textColor = ContextCompat.getColor(button.context, R.color.white)
            }
            pertemuan.kode_absensi == "0" && pertemuan.status == "1" -> {
                text = "Hadir"
                textSize = 12f
                textColor = ContextCompat.getColor(button.context, R.color.white)
            }
            pertemuan.kode_absensi == "0" && pertemuan.status == "2" -> {
                text = "Izin"
                textSize = 12f
                textColor = ContextCompat.getColor(button.context, R.color.white)
            }
            pertemuan.kode_absensi == "0" && pertemuan.status == "3" -> {
                text = "Tidak Hadir"
                textSize = 10f
                textColor = ContextCompat.getColor(button.context, R.color.white)
            }

            else -> {
                text = "Absen"
                textSize = 12f
                textColor = ContextCompat.getColor(button.context, R.color.white)
            }
        }

        button.text = text
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
        button.setTextColor(textColor)
    }


    fun setButtonBackgroundColor(context: Context, button: Button, pertemuan: Pertemuan) {
        button.setBackgroundColor(getButtonBackgroundColor(context, pertemuan))
    }

    fun setButtonStatus(button: Button, pertemuan: Pertemuan) {
        button.isEnabled = pertemuan.kode_absensi == "1" && pertemuan.status == "0"
    }

    private fun getButtonBackgroundColor(context: Context, pertemuan: Pertemuan): Int {
        return when {
            pertemuan.kode_absensi == "0" && pertemuan.status == "0" ->
                ContextCompat.getColor(context, R.color.silver_200)

            pertemuan.kode_absensi == "1" && pertemuan.status == "0" ->
                ContextCompat.getColor(context, R.color.red)

            pertemuan.kode_absensi == "1" && pertemuan.status == "1" ->
                ContextCompat.getColor(context, R.color.blue)

            pertemuan.kode_absensi == "1" && pertemuan.status == "2" ->
                ContextCompat.getColor(context, R.color.blue)

            pertemuan.kode_absensi == "0" && pertemuan.status == "1" ->
                ContextCompat.getColor(context, R.color.green)

            pertemuan.kode_absensi == "0" && pertemuan.status == "2" ->
                ContextCompat.getColor(context, R.color.gold)

            pertemuan.kode_absensi == "0" && pertemuan.status == "3" ->
                ContextCompat.getColor(context, R.color.grey_500)

            else -> ContextCompat.getColor(context, R.color.silver_200)
        }
    }
}
