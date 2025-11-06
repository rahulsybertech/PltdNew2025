package com.syber.ssspltd.data.model

data class AppConfigResponse(
    val data: List<AppConfigItem>,
    val message: String,
    val success: Boolean,
    val error: Boolean,
    val responsecode: String
)

data class AppConfigItem(
    val id: String? = null,
    val appName: String? = null,
    val type: String? = null,
    val dataValue: String? = null,
    val image: String? = null,

    val homeScreenBackGroundTop: String? = null,
    val appVerson: String? = null,
    val appLogo: String? = null,
    val loginScreenImage: String? = null,
    val buttonColor2LogIn: String? = null,
    val appIconClubTypeSilver: String? = null,
    val appIconClubTypeDiamond: String? = null,
    val homeScreenBackGroundCenter: String? = null,
    val buttonColor1LogIn: String? = null,
    val selectUserScreenImage: String? = null,
    val homeScreenOffersImage: String? = null,
    val splashScreen: String? = null,
    val appThemeColor: String? = null,
    val appIconClubTypeGold: String? = null,
    val appIconClubTypeNormal: String? = null,
    val buttonColor1SubmitButton: String? = null
)
