package app.banking.bankmuscat.merchant.base;


import android.app.Application;

import java.util.Timer;
import java.util.TimerTask;

import app.banking.bankmuscat.merchant.api.APIManager;

/*
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
*/

public class BMApplication extends Application {


    private static Boolean gcmflag = false;
    private String sva_status = "";
    private static String SVA_STATUS = "0";
    private Boolean svaBalance = false;
    private static Boolean Login = true;
    private APIManager apiManager;




    public void onCreate() {
        /*try {
            TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
            Fabric.with(this, new Twitter(authConfig), new Crashlytics());
        } catch (Exception e) {
            e.printStackTrace();
        }*/


        try {
       /* SDKManager sdkManager = new SDKManager(this);
            registerActivityLifecycleCallbacks(sdkManager.getMcbpActivityLifeCycleCallbacks());*/
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            apiManager = APIManager.createInstance(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }



	/* Checks if external storage is available for read and write */


    }

    public APIManager getApiManager() {
        return apiManager;
    }

    public Boolean getSvaBalance() {
        return svaBalance;
    }

    public void setSvaBalance(Boolean svaBalance) {
        this.svaBalance = svaBalance;
    }

    public static Boolean getGcmflag() {
        return gcmflag;
    }

    public static void setGcmflag(Boolean gcmflag) {
        BMApplication.gcmflag = gcmflag;
    }

    public static String getSVA_STATUS() {
        return SVA_STATUS;
    }

    public static void setSVA_STATUS(String sVA_STATUS) {
        SVA_STATUS = sVA_STATUS;
    }

    public static Boolean getLogin() {
        return Login;
    }

    public static void setLogin(Boolean login) {
        Login = login;
    }

///in bg op


    private Timer inactiveTimer;

    private TimerTask inactiveTimerTask;
    public boolean wasInactive;
    public final long MAX_INACTIVITY_TIME = 300000;

    public void StartInactivityTimer() {
        this.inactiveTimer = new Timer();
        this.inactiveTimerTask = new TimerTask() {
            public void run() {
                BMApplication.this.wasInactive = true;
            }
        };

        this.inactiveTimer.schedule(inactiveTimerTask,
                MAX_INACTIVITY_TIME);
    }

    public void StopInactivityTimer() {
        if (this.inactiveTimerTask != null) {
            this.inactiveTimerTask.cancel();
        }

        if (this.inactiveTimer != null) {
            this.inactiveTimer.cancel();
        }

        this.wasInactive = false;
    }

}

