package com.absensi.app.utils

import android.content.Context
import android.content.SharedPreferences

object UserProfilePreferences {
    private const val PREF_NAME = "user_profile_pref"
    private const val KEY_ID = "user_id"
    private const val KEY_EMAIL = "user_email"
    private const val KEY_NAME = "user_name"
    private const val KEY_NIM = "user_nim"
    private const val KEY_SEMESTER = "user_semester"
    private const val KEY_PRODI = "user_prodi"
    private const val KEY_FAKULTAS = "user_fakultas"

    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun saveUserProfile(
        id: String,
        name: String,
        email: String,
        nim: String,
        semester: String,
        prodi: String,
        fakultas: String
    ) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_ID, id)
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_NAME, name)
        editor.putString(KEY_NIM, nim)
        editor.putString(KEY_SEMESTER, semester)
        editor.putString(KEY_PRODI, prodi)
        editor.putString(KEY_FAKULTAS, fakultas)
        editor.apply()
    }

    fun getUserProfile(): UserProfile {
        return UserProfile(
            sharedPreferences.getString(KEY_ID, ""),
            sharedPreferences.getString(KEY_EMAIL, ""),
            sharedPreferences.getString(KEY_NAME, ""),
            sharedPreferences.getString(KEY_NIM, ""),
            sharedPreferences.getString(KEY_SEMESTER, ""),
            sharedPreferences.getString(KEY_PRODI, ""),
            sharedPreferences.getString(KEY_FAKULTAS, "")
        )
    }

    fun removeUserProfile() {
        val editor = sharedPreferences.edit()
        editor.remove(KEY_ID)
        editor.remove(KEY_EMAIL)
        editor.remove(KEY_NAME)
        editor.remove(KEY_NIM)
        editor.remove(KEY_SEMESTER)
        editor.remove(KEY_PRODI)
        editor.remove(KEY_FAKULTAS)
        editor.apply()
    }
}
