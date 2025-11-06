package com.syber.ssspltd.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
private const val SKAZULE_SHARED_PREFERENCES = "SKAZULE_SHARED_PREFERENCES"
private const val KEY_TOKEN = "KEY_TOKEN"
private const val SaveUserStatus = "save_user_status"
private const val PHONENUMBER = "PHONE_NUMBER"
private const val PARTY_CODE = "party_code"
private const val IS_LOGIN = "false"
private const val START_DATE = "start_date"
private const val END_DATE = "end_date"
private const val IS_SUPPER_SELECTED = "KEY_TOKEN"
private const val WHERE_TO_GO = "KEY_TOKEN"
private const val ISANYCHOOSEN = "ISANYCHOOSEN"
private const val GROUP_CODE = "group_code"
private const val USER_ID = "user_id"
private const val SAVEUSERMSNUALLYSELECTEDYEAR = "saveuserManuallySelectedYear"
private const val ISPARTYCODE = "ISPARTYCODE"
private const val PARTY_ID = "partt_id"
private const val SELECTEDPARTYNAME = "selectedPartyName"
class AppSharedPreferences  @Inject constructor(
    @ApplicationContext private val context: Context,

) {

    private var sharedPreferences: SharedPreferences = context.getSharedPreferences(SKAZULE_SHARED_PREFERENCES, Context.MODE_PRIVATE)



    var mobileNumber: String?
        get() = sharedPreferences.getString("START_DATE", null)
        set(value) {
            sharedPreferences.putValues { it.putString("START_DATE", value) }
        }
    // set and get token
    fun saveToken(token: String) {
        sharedPreferences.putValues {
            it.putString(KEY_TOKEN, token)
        }
    }
    var token: String? = null
    get() {
        val storedValue = sharedPreferences.getString(KEY_TOKEN, "")
        token = storedValue
        return field
    }

    fun savePhoneNumber(token: String) {
        sharedPreferences.putValues {
            it.putString(PHONENUMBER, token)
        }
    }
    var phoneNumber: String? = null
        get() {
            val storedValue = sharedPreferences.getString(PHONENUMBER, "")
            phoneNumber = storedValue
            return field
        }

    fun saveUserStatus(token: String) {
        sharedPreferences.putValues {
            it.putString(SaveUserStatus, token)
        }
    }
    var userStatus: String? = null
        get() {
            val storedValue = sharedPreferences.getString(SaveUserStatus, "")
            token = storedValue
            return field
        }



    fun saveUserType(userType: String) {
        sharedPreferences.putValues {
            it.putString(ISANYCHOOSEN, userType)
        }
    }
    var userType: String? = null
        get() {
            val storedValue = sharedPreferences.getString(ISANYCHOOSEN, "")
            userType = storedValue
            return field
        }

    fun saveGroupCode(userType: String) {
        sharedPreferences.putValues {
            it.putString(GROUP_CODE, userType)
        }
    }
    var groupCode: String? = null
        get() {
            val storedValue = sharedPreferences.getString(GROUP_CODE, "")
            groupCode = storedValue
            return field
        }

    fun saveUserId(userType: String) {
        sharedPreferences.putValues {
            it.putString(USER_ID, userType)
        }
    }
    var userId: String? = null
        get() {
            val storedValue = sharedPreferences.getString(USER_ID, "")
            userId = storedValue
            return field
        }

    fun saveUserManuallySelectedYear(userType: String) {
        sharedPreferences.putValues {
            it.putString(SAVEUSERMSNUALLYSELECTEDYEAR, userType)
        }
    }
    var userManuallySelectedYear: String? = null
        get() {
            val storedValue = sharedPreferences.getString(SAVEUSERMSNUALLYSELECTEDYEAR, "")
            userManuallySelectedYear = storedValue
            return field
        }


    fun isLogin(token: String) {
        sharedPreferences.putValues {
            it.putString(IS_LOGIN, token)
        }
    }
    var login: String? = null
        get() {
            val storedValue = sharedPreferences.getString(IS_LOGIN, "")
            login = storedValue
            return field
        }




    var startDate: String? = null
        get() {
            val storedValue = sharedPreferences.getString(START_DATE, "")
            startDate = storedValue
            return field
        }
        set(value) {
            sharedPreferences.putValues {
                it.putString(START_DATE, value)
            }
        }


    var endDate: String? = null
        get() {
            val storedValue = sharedPreferences.getString(END_DATE, "")
            endDate = storedValue
            return field
        }
        set(value) {
            sharedPreferences.putValues {
                it.putString(END_DATE, value)
            }
        }



    var isSupperSelected: String? = null
        get() {
            val storedValue = sharedPreferences.getString(IS_SUPPER_SELECTED, "")
            isSupperSelected = storedValue
            return field
        }

    var isWeretogo: String? = null
        get() {
            val storedValue = sharedPreferences.getString(WHERE_TO_GO, "")
            isWeretogo = storedValue
            return field
        }


    fun savePartyCode(partyCode: String) {
        sharedPreferences.putValues {
            it.putString(ISPARTYCODE, partyCode)
        }
    }

    var isPartyCode: String? = null
        get() {
            val storedValue = sharedPreferences.getString(ISPARTYCODE, "")
            isPartyCode = storedValue
            return field
        }

    fun savePartyId(partyId: String) {
        sharedPreferences.putValues {
            it.putString(PARTY_ID, partyId)
        }
    }

    var partyId: String? = null
        get() {
            val storedValue = sharedPreferences.getString(PARTY_ID, "")
            partyId = storedValue
            return field
        }

    fun saveSelectedPartyName(partyCode: String) {
        sharedPreferences.putValues {
            it.putString(SELECTEDPARTYNAME, partyCode)
        }
    }

    var selectedPartyName: String? = null
        get() {
            val storedValue = sharedPreferences.getString(SELECTEDPARTYNAME, "")
            selectedPartyName = storedValue
            return field
        }




  /*  fun saveIsSpotifyConected(state: String) {
        sharedPreferences.putValues {
            it.putString(IS_SPOTIFY_CONECTED, state)
        }
    }
    var isSpotifyConected: String? = null
        get() {
            val storedValue = sharedPreferences.getString(IS_SPOTIFY_CONECTED, "")
            tokenSpotify = storedValue
            return field
        }


    fun saveRadioState(state: String) {
        sharedPreferences.putValues {
            it.putString(KEY_RADIO_STATE, state)
        }
    }
    var state: String? = null
        get() {
            val storedValue = sharedPreferences.getString(KEY_RADIO_STATE, "")
            state = storedValue
            return field
        }

    // set and get token
    fun saveToken(token: String) {
        sharedPreferences.putValues {
            it.putString(KEY_TOKEN, token)
        }
    }
    var token: String? = null
        get() {
            val storedValue = sharedPreferences.getString(KEY_TOKEN, "")
            token = storedValue
            return field
        }

    //set and get fcm token
    fun saveFcmToken(fcmToken: String){
        sharedPreferences.putValues {
            it.putString(KEY_FCM_TOKEN, fcmToken)
        }
    }

    var fcmToken: String? = null
        get() {
            val storedValue = sharedPreferences.getString(KEY_FCM_TOKEN, "")
            fcmToken = storedValue
            return field
        }


    //set cartid
    fun saveCartId(id: String){
        sharedPreferences.putValues {
            it.putString(CART_ID, id)
        }
    }

    var cartId: String? = null
        get() {
            val storedValue = sharedPreferences.getString(CART_ID, "")
            cartId = storedValue
            return field
        }

    //set cartid
    fun saveCheckOutUrl(id: String){
        sharedPreferences.putValues {
            it.putString(CHECKOUTURL, id)
        }
    }

    var checkOutUrlnew: String? = null
        get() {
            val storedValue = sharedPreferences.getString(CHECKOUTURL, "")
            checkOutUrlnew = storedValue
            return field
        }

    // set and get email
    fun saveEmail(email: String) {
        sharedPreferences.putValues {
            it.putString(KEY_EMAIL, email)
        }
    }
    var email: String? = null
        get() {
            val storedValue = sharedPreferences.getString(KEY_EMAIL, "")
            email = storedValue
            return field
        }

    // set and get email
    fun saveIntroScreen(introScreen: String) {
        sharedPreferences.putValues {
            it.putString(KEY_INTRO_SCREEEN, introScreen)
        }
    }
    var introScreen: String? = null
        get() {
            val storedValue = sharedPreferences.getString(KEY_INTRO_SCREEEN, "")
            introScreen = storedValue
            return field
        }

    fun saveMyFirebaseId(userId: String){
        sharedPreferences.putValues {
            it.putString(KEY_MY_FIREBASE_ID, userId)
        }
    }

    var myFirebaseId: String? = null
        get() {
            val storedValue = sharedPreferences.getString(KEY_MY_FIREBASE_ID, "")
            myFirebaseId = storedValue
            return field
        }

    //work chat list save
    fun saveMyWorkChatList(userId: String){
        sharedPreferences.putValues {
            it.putString(KEY_MY_WORK_CHAT_LIST, userId)
        }
    }

    var MyWorkChatList: String? = null
        get() {
            val storedValue = sharedPreferences.getString(KEY_MY_WORK_CHAT_LIST, "")
            MyWorkChatList = storedValue
            return field
        }

    // set and get name
    fun saveName(name: String) {
        sharedPreferences.putValues {
            it.putString(KEY_NAME, name)
        }
    }
    var price: String? = null
        get() {
            val storedValue = sharedPreferences.getString(KEY_PRICE, "")
            price = storedValue
            return field
        }
    // set and get name
    fun savePrice(name: String) {
        sharedPreferences.putValues {
            it.putString(KEY_PRICE, name)
        }
    }


    var htmlText: String? = null
        get() {
            val storedValue = sharedPreferences.getString(KEY_HTML, "")
            htmlText = storedValue
            return field
        }
    // set and get name
    fun saveHtmlText(name: String) {
        sharedPreferences.putValues {
            it.putString(KEY_HTML, name)
        }
    }
    var name: String? = null
        get() {
            val storedValue = sharedPreferences.getString(KEY_NAME, "")
            name = storedValue
            return field
        }

    // set and get mobile
    fun saveMobileNo(mobile: String) {
        sharedPreferences.putValues {
            it.putString(KEY_MOBILE, mobile)
        }
    }
    var mobile: String? = null
        get() {
            val storedValue = sharedPreferences.getString(KEY_MOBILE, "")
            mobile = storedValue
            return field
        }

    // set and get company name
    fun saveCompanyName(companyName: String) {
        sharedPreferences.putValues {
            it.putString(KEY_COMPANY_NAME, companyName)
        }
    }
    var companyName: String? = null
        get() {
            val storedValue = sharedPreferences.getString(KEY_COMPANY_NAME, "")
            companyName = storedValue
            return field
        }

    // set and get employer_id
    fun saveEmployer_Id(employer_id: String) {
        sharedPreferences.putValues {
            it.putString(KEY_EMPLOYER_ID, employer_id)
        }
    }
    var employer_id: String? = null
        get() {
            val storedValue = sharedPreferences.getString(KEY_EMPLOYER_ID, "")
            employer_id = storedValue
            return field
        }




    fun saveUserId(user_id: String) {
        sharedPreferences.putValues {
            it.putString(KEY_USER_ID, user_id)
        }
    }
    var user_id: String? = null
        get() {
            val storedValue = sharedPreferences.getString(KEY_USER_ID, "")
            user_id = storedValue
            return field
        }

    // set and get company_id
    fun saveCompany_Id(company_id: String) {
        sharedPreferences.putValues {
            it.putString(KEY_COMPANY_ID, company_id)
        }
    }
    var company_id: String? = null
        get() {
            val storedValue = sharedPreferences.getString(KEY_COMPANY_ID, "")
            company_id = storedValue
            return field
        }
    // set and get industry_id
    fun saveIndustry_Id(industry_id: String) {
        sharedPreferences.putValues {
            it.putString(KEY_INDUSTRY_ID, industry_id)
        }
    }
    var industry_id: String? = null
        get() {
            val storedValue = sharedPreferences.getString(KEY_INDUSTRY_ID, "")
            industry_id = storedValue
            return field
        }

    // set profile_pic
    fun saveProfile_pic(profile_pic: String) {
        sharedPreferences.putValues {
            it.putString(KEY_PROFILE_PIC, profile_pic)
        }
    }
    var profile_pic: String? = null
        get() {
            val storedValue = sharedPreferences.getString(KEY_PROFILE_PIC, "")
            profile_pic = storedValue
            return field
        }

    // set and get remember me
    fun saveRemember_me(remember_me: String) {
        sharedPreferences.putValues {
            it.putString(KEY_REMEMBER_ME, remember_me)
        }
    }
    var remember_me: String? = null
        get() {
            val storedValue = sharedPreferences.getString(KEY_REMEMBER_ME, "")
            remember_me = storedValue
            return field
        }


    // set and get remember me pass
    fun saveSound(remember_me_pass: String) {
        sharedPreferences.putValues {
            it.putString(KEY_REMEMBER_ME_PASS, remember_me_pass)
        }
    }
    var sound: String? = null
        get() {
            val storedValue = sharedPreferences.getString(KEY_REMEMBER_ME_PASS, "")
            sound = storedValue
            return field
        }
*/
    /**
     * Extension function for shared preferences
     */
    private fun SharedPreferences.putValues(func:(SharedPreferences.Editor)-> Unit) {
        val editor: SharedPreferences.Editor = this.edit()
        func(editor)
        editor.apply()
    }

    private fun setString(key: String?, value: String?) {
        if (key != null && value != null) {
            try {
                if (_pref != null) {
                    val editor = _pref!!.edit()
                    editor.putString(key, value)
                    editor.commit()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Unable to set " + key + "= " + value + "in shared preference", e)
            }

        }
    }

    private fun getString(key: String?, defaultValue: String): String? {
        return if (_pref != null && key != null && _pref!!.contains(key)) {
            _pref!!.getString(key, defaultValue)
        } else defaultValue
    }

    companion object{
        val TAG = AppSharedPreferences::class.java.name
        private var _pref: SharedPreferences? = null
        private var _instance: AppSharedPreferences? = null
        private val PRIVATE_MODE = 0
        val SHARED_PREF_NAME = "RMC_"

        @SuppressLint("WrongConstant")
        fun getInstance(context: Context): AppSharedPreferences {
            if (_pref == null) {
                _pref = context
                    .getSharedPreferences(SHARED_PREF_NAME, PRIVATE_MODE)
            }
            if (_instance == null)  {
                _instance = AppSharedPreferences(context)
            }
            return _instance as AppSharedPreferences
        }
    }


    fun clearToken() {
        sharedPreferences.putValues {
            it.remove(KEY_TOKEN)
        }
    }




}