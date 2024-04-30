package com.example.moremovies.repository

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

private const val SHARED_PREF = "sharedPref"
private const val IS_FIRST_LAUNCH = "is_first_launch"

@Singleton
class SharedPreferencesRepository @Inject constructor(@ApplicationContext context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)

    fun saveAuthState(isAuthenticated: Boolean) {
        prefs.edit().putBoolean(IS_FIRST_LAUNCH, isAuthenticated).apply()
    }

    fun getAuthState(): Boolean {
        return prefs.getBoolean(IS_FIRST_LAUNCH, false)
    }
}