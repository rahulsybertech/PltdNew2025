package com.syber.ssspltd.Utils;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.telephony.TelephonyManager;
import android.util.Log;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.Timer;
import java.util.TimerTask;

public class AppController extends Application {

    public static AppController controller;

    private static final String TAG = "AppController";
    private Timer mActivityTransitionTimer;
    private TimerTask mActivityTransitionTimerTask;
    public boolean wasInBackground;
    private final long MAX_ACTIVITY_TRANSITION_TIME_MS = 3 * 60 * 1000;
    public  static Context mContext ;
    @SuppressLint("MissingSuperCall")
    @Override
    public void onCreate() {
        //Fabric.with(this, new Crashlytics());
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);
        controller = this;
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

    }


//    public static FusedLocationProviderClient getFusedProvider() {
//        return LocationServices.getFusedLocationProviderClient(controller);
//    }

//    public static String getMyMAC() {
//        WifiManager wifiManager = (WifiManager) controller.getSystemService(Context.WIFI_SERVICE);
//        if (wifiManager != null) {
//            WifiInfo wInfo = wifiManager.getConnectionInfo();
//            return wInfo.getMacAddress();
//        }
//        return "NA";
//    }

    @SuppressLint("MissingPermission")
    public static String getIMEI() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) controller.getSystemService(Context.TELEPHONY_SERVICE);
            if (android.os.Build.VERSION.SDK_INT >= 26) {
                return telephonyManager.getImei();
            } else {
                return telephonyManager.getDeviceId();
            }
        } catch (Exception e) {
            Log.e("Ec", e + "");
        }
        return "NA";
    }


    public static synchronized AppController getInstance() {
        return controller;
    }


}
