package com.syber.ssspltd.ui.theme

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
// ✅ Proper top-level declaration
private val Context._dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_theme_prefs")
val Context.dataStore: DataStore<Preferences>
    get() = _dataStore

object ThemeDataStore {

    private val THEME_KEY = stringPreferencesKey("current_theme")

    suspend fun saveTheme(context: Context, theme: AppThemeType) {
        context.dataStore.edit { prefs ->
            prefs[THEME_KEY] = theme.name
        }
    }

    suspend fun getTheme(context: Context): AppThemeType {
        val themeName = context.dataStore.data
            .map { it[THEME_KEY] ?: AppThemeType.DEFAULT.name }
            .first()

        return AppThemeType.valueOf(themeName)
    }
}
