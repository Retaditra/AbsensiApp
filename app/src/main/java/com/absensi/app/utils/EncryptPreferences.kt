package com.kta.app.utils

import android.content.Context
import android.content.SharedPreferences
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class EncryptPreferences(context: Context) {

    private val sharedPreferences = createPreferences(context)
    private fun createPreferences(context: Context): SharedPreferences {
        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            MasterKey.DEFAULT_MASTER_KEY_ALIAS,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        ).run {
            setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            setKeySize(MasterKey.DEFAULT_AES_GCM_MASTER_KEY_SIZE)
            build()
        }

        val masterKey = MasterKey.Builder(context)
            .setKeyGenParameterSpec(keyGenParameterSpec)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            "MyPrefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun getPreferences(): SharedPreferences {
        return sharedPreferences
    }

    fun savePreferences(token: String) {
        with(getPreferences().edit()) {
            putString("token", token)
            apply()
        }
    }

    fun removePreferences() {
        with(getPreferences().edit()) {
            remove("token")
            apply()
        }
    }
}
