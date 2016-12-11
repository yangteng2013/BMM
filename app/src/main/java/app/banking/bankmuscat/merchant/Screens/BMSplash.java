package app.banking.bankmuscat.merchant.Screens;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import app.banking.bankmuscat.R;
import app.banking.bankmuscat.merchant.common.Wallet;
import app.banking.bankmuscat.merchant.db.DbManager;
import app.banking.bankmuscat.merchant.logger.CLog;

import static app.banking.bankmuscat.merchant.base.ActivityBase.BM_DATA;
import static app.banking.bankmuscat.merchant.base.ActivityBase.BM_LANGUAGE;
import static app.banking.bankmuscat.merchant.base.ActivityBase.BM_MOBILE;
import static app.banking.bankmuscat.merchant.base.ActivityBase.LANGUAGE_NONE;
import static app.banking.bankmuscat.merchant.base.ActivityBase.USER_RESTART;


public class BMSplash extends AppCompatActivity {

    protected Wallet.Data appData = Wallet.Data.getInstance();

    private boolean locationIsAssets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // init wallet

        Wallet.App.onBegin(BMSplash.this);
        //addShortcut();
        //


        System.out.println("Root Directory............................." + Environment.getRootDirectory().toString());


        setContentView(R.layout.bm_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        SharedPreferences settings = getSharedPreferences("conf", 0);
        locationIsAssets = settings.getBoolean("assets", true);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int d = metrics.densityDpi;
        System.out.println("Density........................" + d);


        // Service for File Observation Servide
        //startService(new Intent(this, FileAccessService.class));

        new Handler().postDelayed(new Runnable() {

			/*
             * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

            @SuppressWarnings("unused")
            @Override
            public void run() {
                //
                Intent i = null;


                DbManager dbManager = DbManager.createAuto(BMSplash.this);
                int appStatus = dbManager.getAppStatus();

                CLog.i("app status : ", appStatus + "");
                appData.setAppMode(Wallet.Mode.NORMAL);
				/*if (Wallet.Status.REGISTRAION_COMPLETED <= appStatus) {
					// skip registration
					i = new Intent(BMSplash.this, HomeScreen.class);
				} else if (Wallet.Status.PROFILE_SUCCESS <= appStatus) {
					i = new Intent(BMSplash.this, RegistrationPin.class);
				} else {
					// This method will be executed once the timer is over
					// Start your app main activity
					i = new Intent(BMSplash.this,
							TermsAndConditionsActivity.class);
				}abc*/

                String num = BMReadData(BM_MOBILE,"");


                /////
                if(BMReadData(BM_LANGUAGE, LANGUAGE_NONE).compareToIgnoreCase(LANGUAGE_NONE) == 0) {
                    i = new Intent(BMSplash.this,
                            BMLanguage.class);
                    i.putExtra("Mode", 0);
                }

                else if (num.length() != 0) {

                    i = new Intent(getApplicationContext(), BMEnterPin.class);
                    i.putExtra("Mode", USER_RESTART);

                }

                else {
                    i = new Intent(BMSplash.this,
                            BMLogin.class);
                }

               /* i = new Intent(BMSplash.this,
                        BMLanguage.class);
                i.putExtra("Mode", 0);*/


                //////
                startActivity(i);

                finish();

            }
        }, Wallet.Data.SPLASH_SCREEN_TIME_OUT);


    }

    public String BMReadData(String key, String defaultValue){
        try{
            SharedPreferences settings = this.getSharedPreferences(BM_DATA, Context.MODE_PRIVATE);
            return settings.getString(key, defaultValue);
        }
        catch(Exception e){}
        return defaultValue;
    }

}