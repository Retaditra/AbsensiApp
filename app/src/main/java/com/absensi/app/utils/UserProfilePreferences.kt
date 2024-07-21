package com.kta.app.utils

import android.content.Context
import android.content.SharedPreferences

object UserProfilePreferences {
    private const val PREF_NAME = "user_profile_pref"
    private const val KEY_ID = "user_id"
    private const val KEY_NAME = "user_name"
    private const val KEY_PHONE = "user_phone"
    private const val KEY_DATE_BIRTH = "user_date_birth"
    private const val KEY_BIRTHPLACE = "user_birthplace"
    private const val KEY_POSITION = "user_position"
    private const val KEY_IMAGE_PROFILE = "user_image_profile"

    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveUserProfile(
        id: String,
        name: String,
        phone: String,
        dateBirth: String,
        birthplace: String,
        position: String,
        imageProfile: String
    ) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_ID, id)
        editor.putString(KEY_NAME, name)
        editor.putString(KEY_PHONE, phone)
        editor.putString(KEY_DATE_BIRTH, dateBirth)
        editor.putString(KEY_BIRTHPLACE, birthplace)
        editor.putString(KEY_POSITION, position)
        editor.putString(KEY_IMAGE_PROFILE, imageProfile)
        editor.apply()
    }

    fun getUserProfile(): UserProfile {
        return UserProfile(
            sharedPreferences.getString(KEY_ID, ""),
            sharedPreferences.getString(KEY_NAME, ""),
            sharedPreferences.getString(KEY_PHONE, ""),
            sharedPreferences.getString(KEY_DATE_BIRTH, ""),
            sharedPreferences.getString(KEY_BIRTHPLACE, ""),
            sharedPreferences.getString(KEY_POSITION, ""),
            sharedPreferences.getString(KEY_IMAGE_PROFILE, "")
        )
    }

    fun removeUserProfile() {
        val editor = sharedPreferences.edit()
        editor.remove(KEY_ID)
        editor.remove(KEY_NAME)
        editor.remove(KEY_PHONE)
        editor.remove(KEY_DATE_BIRTH)
        editor.remove(KEY_BIRTHPLACE)
        editor.remove(KEY_POSITION)
        editor.remove(KEY_IMAGE_PROFILE)
        editor.apply()
    }
}
