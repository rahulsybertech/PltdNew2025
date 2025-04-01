package com.syber.ssspltd.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class SharedPref {

    private static SharedPreferences mSharedPref;

    public static final String USERID               = "d_id";
    public static final String USERNAME             = "name";
    public static final String RM_ID                = "sm_id";
    public static final String USEREMAIL            = "email";
    public static final String USERMOBILE           = "mobile";
    public static final String COMP                 = "comp";
    public static final String DB                   = "db";
    public static final String FILTER_POS           = "filter_pos";
    public static final String FILTER_TRANS         = "filter_trans";
    public static final String DB_NAME              = "db_name";
    public static final String LIST_TYPE            = "list_type";
    public static final String D_ID                 = "did";
    public static final String PARTY_CODE           = "party_code";
    public static final String ACCOUND_ID           = "account_id";
    public static final String CUM_NUM              = "Com_Num";
    public static final String WALLET               = "wallet";
    public static final String USERTYPE             = "usertype";
    public static final  String offline_list        = "offline_list";
    public static final String isLogin              = "isLogin";
    public  static  final String CHECK              = "check";
    public static  final  String SELECTED           = "selected";
    public static  final  String  TYPE              = "type";
    public static  final  String DASHBOARD_TYPE     = "dash_type";
    public  static  final  String spinner_pos       = "pos";
    public  static  final  String Current_Bal       = "cur_bal";
    public  static  final  String YearFilter_pos    = "yrFilter_pos";
    public  static  final  String default_db        = "defaultdb";
    public  static  final  String selected_default_yr = "selecteddefaultyr";
    public  static  final  String Year_startYear    = "Year_startYear";
    public  static  final  String Year_endYear      = "Year_endYear";
    public  static  final  String FY_StartDate      = "FY_StartDate";
    public  static  final  String FY_EndDate        = "FY_EndDate";
    public  static  final  String ON                = "on";
    public  static  final  String SET_YEAR          = "set_year";
    public  static  final  String F_YEAR            = "f_year";
    public  static  final  String FALSE             = "false";
    public  static  final  String IMG_VIDEO         = "img_video";
    public  static  final  String BACK_BUTTON       = "back_button";
    public  static  final  String IS_ANY_CHOOSEN    = "isanychoosen";
    public  static  final  String WHERE_TO_GO       = "wheretogo";
    public  static  final  String IS_SUPPER_SELECTED= "supperselected";
    public  static  final  String IS_BACK_VISIBLE   = "isbackvisible";
    public  static  final  String HAVE_REFERAL      = "referal";
    public  static  final  String USER_TYPE         = "user_type";
    public  static  final  String KYC_TYPE_OPEN     = "kyc_type_open";
    public static final String Referal_code         ="Referal_code";
    public static final String AppVersion           ="AppVersion";
    public static final String GST_Valid            ="gst_valid";
    public static final String SELECTED_ENTRY       ="selected_entry";
    public static final String PRIVIOUS_REMARK      = "privious_remark";
    public static final String END_DATE             = "enddate";
    public static final String START_DATE           = "startdate";
    public  static  final String typeNumber         ="typeNumber";
    public  static  final String clubType           ="clubType";
    public  static  final String dashboardClubType  ="dashboardClubType";
    public  static  final String noClubType         ="noClubType";
    public  static  final String ACCCESS_TOKEN      ="ACCCESS_TOKEN";
    public  static  final String SELECTED_FY_DATA   ="selected_fy_data";



    public static void init(Context context)
    {
        if(mSharedPref == null)
            mSharedPref = context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public static String read(String key, String defValue) {
        return mSharedPref.getString(key, defValue);
    }

    public static void write(String key, String value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }


    public static boolean read(String key, boolean defValue) {
        return mSharedPref.getBoolean(key, defValue);
    }

    public static void write(String key, boolean value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putBoolean(key, value);
        prefsEditor.apply();
    }

    public static Integer read(String key, int defValue) {
        return mSharedPref.getInt(key, defValue);
    }

    public static void write(String key, Integer value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putInt(key, value).apply();
    }
    public static void clear(){
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.clear();
        editor.apply();
    }

    public void setLedgerReportResult(String selfDetails){
        SharedPreferences.Editor editor=mSharedPref.edit();
        editor.putString("acadmicData3",selfDetails);
        editor.commit();
    }

    // upgrading in future version
//    public static SharedPreferences getSharedPreferences(Context context){
//        return context.getSharedPreferences(context.getPackageName(),Context.MODE_PRIVATE);
//    }

//    public static void setPreference(Context context, String key, String val) {
//        SharedPreferences settings = SharedPref.getSharedPreferences(context);
//        SharedPreferences.Editor editor = settings.edit();
//        editor.putString(key, val);
//        editor.commit();
//    }

//    public static void setPreference(Context context, String key, Object val) {
//        SharedPreferences settings = SharedPref.getSharedPreferences(context);
//        SharedPreferences.Editor editor = settings.edit();
//        Gson gson = new Gson();
//        String json = gson.toJson(val);
//        editor.putString(key, json);
//        editor.commit();
//    }

//    public static void setPreference(Context context, String key, boolean val) {
//        SharedPreferences settings = SharedPref.getSharedPreferences(context);
//        SharedPreferences.Editor editor = settings.edit();
//        editor.putBoolean(key, val);
//        editor.commit();
//    }

//    public static String getPreference(Context context, String key) {
//        mSharedPref = SharedPref.getSharedPreferences(context);
//        return mSharedPref.getString(key, "");
//    }

//    public static boolean getPreference_boolean(Context context, String key) {
//        mSharedPref = SharedPref.getSharedPreferences(context);
//        return mSharedPref.getBoolean(key, false);
//    }

}
