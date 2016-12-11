package app.banking.bankmuscat.merchant.Screens;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.UUID;

import app.banking.bankmuscat.R;
import app.banking.bankmuscat.merchant.api.APIManager;
import app.banking.bankmuscat.merchant.base.ActivityBase;
import app.banking.bankmuscat.merchant.common.Wallet;
import app.banking.bankmuscat.merchant.db.DbManager;
import app.banking.bankmuscat.merchant.logger.CLog;
import app.banking.bankmuscat.merchant.util.constant.ErrorAndPopupCodes;

//import com.google.i18n.phonenumbers.NumberParseException;
//import com.google.i18n.phonenumbers.PhoneNumberUtil;
//import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
//import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class BMLanguage extends ActivityBase {

    private static final String TAG = "BMRegisterUser";
    private final APIManager apiManager = APIManager.createInstance(null);
    private Wallet.Data appData = Wallet.Data.getInstance();

    //public ImageView btnBack, header_home, header_menu_button;
    TextView headingTextView;
    public TextView edtenglish, edtarabic;
    Button confirm;

    RelativeLayout pageScroll, top_header;

    int mode = NO_TYPE;

    EditText tsttt, tsttt1;


    boolean isLanguageselected = false;

    @Override
    public int GetScreenLayout() {
        return R.layout.bm_language;
    }

    @Override
    public boolean isHeaderNeeded() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.bm_language);

        mode = getIntent().getExtras().getInt("Mode");

        top_header = (RelativeLayout) findViewById(R.id.header_layout);

        if (mode == CHANGE_LANGUAGE) {

            top_header.setVisibility(View.VISIBLE);
        } else {

            top_header.setVisibility(View.GONE);
        }


        confirm = (Button) findViewById(R.id.mob_buttonconfirm);
        edtenglish = (TextView) findViewById(R.id.edtenglish);
        edtarabic = (TextView) findViewById(R.id.edtarabic);


        headingTextView = (TextView) findViewById(R.id.title_header);
        headingTextView.setText(getResources().getString(R.string.chooselanguage));

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        edtenglish.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View arg0) {
                LoadEnglish();
              //  edtenglish.setSelected(true);
              //  edtarabic.setSelected(false);
                isLanguageselected = true;

            }
        });

        edtarabic.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
              //  edtenglish.setSelected(false);
              //  edtarabic.setSelected(true);
                LoadArabic();
                isLanguageselected = true;
            }
        });

        confirm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                nextAction();
                CloseWin();
            }
        });

        HideSoftKeyboard();
    }

    String selectedLanguage = LANGUAGE_NONE;

    void LoadEnglish() {

        selectedLanguage = LANGUAGE_ENGLISH;

        /*edtenglish.setBackgroundColor(this.getResources().getColor(R.color.grey));
        edtarabic.setBackgroundColor(this.getResources().getColor(R.color.transparent));*/

        edtenglish.setBackground(this.getResources().getDrawable(R.drawable.shadowedred));
        edtenglish.setTextColor(this.getResources().getColor(R.color.white));
        edtarabic.setBackground(this.getResources().getDrawable(R.drawable.shadowedwhite));
        edtarabic.setTextColor(this.getResources().getColor(R.color.AppthemeTextColor));
    }

    void LoadArabic() {

        selectedLanguage = LANGUAGE_ARABIC;

       /* edtenglish.setBackgroundColor(this.getResources().getColor(R.color.transparent));
        edtarabic.setBackgroundColor(this.getResources().getColor(R.color.grey));*/

        edtenglish.setBackground(this.getResources().getDrawable(R.drawable.shadowedwhite));
        edtenglish.setTextColor(this.getResources().getColor(R.color.AppthemeTextColor));
        edtarabic.setBackground(this.getResources().getDrawable(R.drawable.shadowedred));
        edtarabic.setTextColor(this.getResources().getColor(R.color.white));
    }

    void nextAction() {


        if (isLanguageselected) {

            edtenglish.setBackgroundColor(this.getResources().getColor(R.color.transparent));
            edtarabic.setBackgroundColor(this.getResources().getColor(R.color.transparent));

            BMWriteData(BM_LANGUAGE, selectedLanguage);

            if (mode == CHANGE_LANGUAGE) {

             ChangeLanguage();

            } else {


                CloseWin();
                ShowHome();


            }

        } else {
            showAlert("Please select a language");
        }

    }


    void CloseWin() {
/*hideSoftKeyboard();
                NavUtils.navigateUpTo(BMEnterEmail.this, NavUtils
						.getParentActivityIntent(BMEnterEmail.this));*/
        this.finish();
    }


    void ChangeLanguage(){

        if (!verifyInternetStatus()) {
            return;
        }
//        showLoader();
        DbManager dbManager = DbManager.createAuto(BMLanguage.this);

        dbManager.setMsisdn(appData.getMsisdn());
        Wallet.Data.syncRead(getApplicationContext());

        try {
            final UUID resultId = apiManager.SetLanguage(appData.getMsisdn(),selectedLanguage);

            final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

                @Override
                public void handleMessage(APIManager.SOAPOperationData data) {
                    try {

                        if (data.getId() == resultId) {
                            if("RSETLANGREQ".equalsIgnoreCase((String)data.getResponseDataObj().get("TYPE")) && "200".equalsIgnoreCase((String)data.getResponseDataObj().get("TXNSTATUS"))) {
                                apiManager.removeListener(this);

                               /* hideLoader();*/
                                CLog.i(TAG, "User login success");
                                /**/
                                Logout();
                            }
                            else{
                                apiManager.removeListener(this);
                                /*hideLoader();*/
                                CLog.i(TAG, "User login failure");
                                showAlert((String)data.getResponseDataObj().get("MESSAGE"));
                            }
                            Intent i = new Intent(getBaseContext(), BMHome.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        hideLoader();
                        new AlertDialog.Builder(BMLanguage.this)
                                .setTitle("Error")
                                .setMessage(ErrorAndPopupCodes.No_Response.getTag())
                                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        ErrorAction();
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .show();

                    }
                }
            };
            apiManager.addListener(l);
        } catch (Exception e) {
            e.printStackTrace();
            hideLoader();
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        hideSoftKeyboard();
        return false;
    }

    void hideSoftKeyboard() {

    }

    @Override
    public void onBackPressed() {
	/*	hideSoftKeyboard();
		NavUtils.navigateUpTo(BMLanguage.this, NavUtils.getParentActivityIntent(BMLanguage.this));*/

        CloseWin();
        //ExitApplication();
    }
}





/*
package app.banking.bankmuscat.wallet.Screens;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Locale;

import app.banking.bankmuscat.R;
import app.banking.bankmuscat.wallet.api.APIManager;
import app.banking.bankmuscat.wallet.base.ActivityBase;
import app.banking.bankmuscat.wallet.common.Wallet;

//import com.google.i18n.phonenumbers.NumberParseException;
//import com.google.i18n.phonenumbers.PhoneNumberUtil;
//import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
//import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class BMLanguage extends ActivityBase {

    private static final String TAG = "BMRegisterUser";
    private final APIManager apiManager = APIManager.createInstance(null);
    private Wallet.Data appData = Wallet.Data.getInstance();

    //public ImageView btnBack, header_home, header_menu_button;
    TextView headingTextView;
    public TextView edtenglish, edtarabic;
    Button confirm;

    RelativeLayout pageScroll, top_header;

    int mode = NO_TYPE;

    EditText tsttt, tsttt1;


    boolean isLanguageselected = false;

    @Override
    public int GetScreenLayout() {
        return R.layout.bm_language;
    }

    @Override
    public boolean isHeaderNeeded() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.bm_language);

        mode = getIntent().getExtras().getInt("Mode");

        top_header = (RelativeLayout) findViewById(R.id.header_layout);

        if (mode == CHANGE_LANGUAGE) {

            top_header.setVisibility(View.VISIBLE);
        } else {

            top_header.setVisibility(View.GONE);
        }

        //btnBack = (ImageView) findViewById(R.id.header_back_button);
        confirm = (Button) findViewById(R.id.mob_buttonconfirm);
        edtenglish = (TextView) findViewById(R.id.edtenglish);
        edtarabic = (TextView) findViewById(R.id.edtarabic);


        //header_home = (ImageView) findViewById(header_home);
        //header_menu_button = (ImageView) findViewById(header_menu_button);

        headingTextView = (TextView) findViewById(R.id.title_header);
        headingTextView.setText(getResources().getString(R.string.chooselanguage));

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        edtenglish.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View arg0) {
                LoadEnglish();
                isLanguageselected = true;

            }
        });

        edtarabic.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                LoadArabic();
                	isLanguageselected = true;
            }
        });

        confirm.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                nextAction();
            }
        });

		*/
/*btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				hideSoftKeyboard();
				NavUtils.navigateUpTo(BMLanguage.this, NavUtils
						.getParentActivityIntent(BMLanguage.this));

			}

		});*//*



        HideSoftKeyboard();
    }

    String selectedLanguage = LANGUAGE_NONE;

    void LoadEnglish() {
        */
/*Intent i = new Intent(getApplicationContext(), BMTransferToBankList.class);
		startActivity(i);*//*


        Configuration configuration = getResources().getConfiguration();
        configuration.setLayoutDirection(new Locale("en"));
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());

        Locale locale = new Locale("en_US");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
       this.getResources().updateConfiguration(config, null);

        //nextAction();
        selectedLanguage = LANGUAGE_ENGLISH;
        BMWriteData(BM_LANGUAGE, LANGUAGE_ENGLISH);
        edtenglish.setBackgroundColor(this.getResources().getColor(R.color.grey));
        edtarabic.setBackgroundColor(this.getResources().getColor(R.color.transparent));
    }

    void LoadArabic() {

        Configuration configuration = getResources().getConfiguration();
        configuration.setLayoutDirection(new Locale("ar"));
        getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());

        Locale locale = new Locale("ar");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        this.getResources().updateConfiguration(config, null);

        selectedLanguage = LANGUAGE_ARABIC;
        BMWriteData(BM_LANGUAGE, LANGUAGE_ARABIC);

        edtenglish.setBackgroundColor(this.getResources().getColor(R.color.transparent));
        edtarabic.setBackgroundColor(this.getResources().getColor(R.color.grey));
    }

    void nextAction() {


        if (isLanguageselected) {


		*/
/*edtenglish.setBackgroundColor(this.getResources().getColor(R.color.transparent));
		Intent i = new Intent(getApplicationContext(), BMLogin.class);
		CloseWin();
		startActivity(i);
*//*


            edtenglish.setBackgroundColor(this.getResources().getColor(R.color.transparent));
            edtarabic.setBackgroundColor(this.getResources().getColor(R.color.transparent));

        //    BMWriteData(BM_LANGUAGE, LANGUAGE_ENGLISH);

            if (mode == CHANGE_LANGUAGE) {

                CloseWin();

            } else {


                Intent i = new Intent(getApplicationContext(), BMLogin.class);
                CloseWin();
                startActivity(i);

            }

        } else {
            showAlert("Please select a language");
        }

    }


    void CloseWin() {
*/
/*hideSoftKeyboard();
                NavUtils.navigateUpTo(BMEnterEmail.this, NavUtils
						.getParentActivityIntent(BMEnterEmail.this));*//*

        this.finish();
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        hideSoftKeyboard();
        return false;
    }

    void hideSoftKeyboard() {

    }

    @Override
    public void onBackPressed() {
	*/
/*	hideSoftKeyboard();
		NavUtils.navigateUpTo(BMLanguage.this, NavUtils.getParentActivityIntent(BMLanguage.this));*//*


        CloseWin();
        //ExitApplication();
    }
}
*/
