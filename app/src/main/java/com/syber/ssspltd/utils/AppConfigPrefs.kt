package com.syber.ssspltd.utils

import androidx.datastore.preferences.core.stringPreferencesKey

object AppConfigPrefs {
    val appThemeColor = stringPreferencesKey("app_theme_color")
    val appVersion = stringPreferencesKey("app_version")
    val appLogo = stringPreferencesKey("app_logo")
    val loginScreenImage = stringPreferencesKey("loginScreenImage")
    val buttonColor2LogIn = stringPreferencesKey("buttonColor2LogIn")
    val buttonColor1LogIn = stringPreferencesKey("buttonColor1LogIn")
    val appIconClubTypeSilver = stringPreferencesKey("appIconClubTypeSilver")
    val appIconClubTypeDiamond = stringPreferencesKey("appIconClubTypeDiamond")
    val buttonColor1SubmitButton = stringPreferencesKey("buttonColor1SubmitButton")


    // ... other keys
}
