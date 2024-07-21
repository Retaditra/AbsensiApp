package com.absensi.app.utils

object ButtonUtils {

//    fun setButtonTextAndStyle(button: Button, schedule: Schedule) {
//        val text: String
//        val textSize: Float
//        val textColor: Int
//
//        when {
//            schedule.status == "0" && schedule.status_absent == "0" -> {
//                text = "Absen"
//                textSize = 12f
//                textColor = ContextCompat.getColor(button.context, R.color.white)
//            }
//            schedule.status == "1" && schedule.status_absent == "0" -> {
//                text = "Absen"
//                textSize = 12f
//                textColor = ContextCompat.getColor(button.context, R.color.white)
//            }
//            schedule.status == "1" && schedule.status_absent == "1" -> {
//                text = "Hadir"
//                textSize = 12f
//                textColor = ContextCompat.getColor(button.context, R.color.white)
//            }
//            schedule.status == "2" && schedule.status_absent == "1" -> {
//                text = "Hadir"
//                textSize = 12f
//                textColor = ContextCompat.getColor(button.context, R.color.white)
//            }
//            schedule.status == "2" && schedule.status_absent == "0" -> {
//                text = "Tidak Hadir"
//                textSize = 11f
//                textColor = ContextCompat.getColor(button.context, R.color.red)
//            }
//            else -> {
//                text = "Absen"
//                textSize = 12f
//                textColor = ContextCompat.getColor(button.context, R.color.white)
//            }
//        }
//
//        button.text = text
//        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize)
//        button.setTextColor(textColor)
//    }
//
//
//    fun setButtonBackgroundColor(context: Context, button: Button, schedule: Schedule) {
//        button.setBackgroundColor(getButtonBackgroundColor(context, schedule))
//    }
//
//    fun setButtonStatus(button: Button, schedule: Schedule) {
//        button.isEnabled = schedule.status == "1" && schedule.status_absent == "0"
//    }
//
//    private fun getButtonBackgroundColor(context: Context, schedule: Schedule): Int {
//        return when {
//            schedule.status == "0" && schedule.status_absent == "0" ->
//                ContextCompat.getColor(context, R.color.silver_200)
//
//            schedule.status == "1" && schedule.status_absent == "0" ->
//                ContextCompat.getColor(context, R.color.red)
//
//            schedule.status == "1" && schedule.status_absent == "1" ->
//                ContextCompat.getColor(context, R.color.green)
//
//            schedule.status == "2" && schedule.status_absent == "1" ->
//                ContextCompat.getColor(context, R.color.green)
//
//            schedule.status == "2" && schedule.status_absent == "0" ->
//                ContextCompat.getColor(context, R.color.silver_200)
//
//            else -> ContextCompat.getColor(context, R.color.silver_200)
//        }
//    }
}
