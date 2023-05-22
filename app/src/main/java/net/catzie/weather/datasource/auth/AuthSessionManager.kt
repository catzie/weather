package net.catzie.weather.datasource.auth

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys


class AuthSessionManager(private val context: Context) {

    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private val encryptedPreferences: SharedPreferences

    init {

        encryptedPreferences = EncryptedSharedPreferences.create(
            ENC_SHARED_PREFS_NAME,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun saveUserId(userId: String) {
        encryptedPreferences.edit().apply {
            putString(KEY_USER_ID, userId)
        }.apply()
    }

    fun loadUserId(): String? {
        return encryptedPreferences.getString(KEY_USER_ID, null)
    }

    companion object {
        private const val KEY_USER_ID = "KEY_USER_ID"
        private const val ENC_SHARED_PREFS_NAME = "ENC_SHARED_PREFS_NAME"
    }

}