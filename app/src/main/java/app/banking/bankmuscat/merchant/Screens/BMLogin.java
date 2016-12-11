package app.banking.bankmuscat.merchant.Screens;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.UUID;

import app.banking.bankmuscat.R;
import app.banking.bankmuscat.merchant.api.APIManager;
import app.banking.bankmuscat.merchant.base.ActivityBase;
import app.banking.bankmuscat.merchant.common.Wallet;
import app.banking.bankmuscat.merchant.db.DbManager;
import app.banking.bankmuscat.merchant.interfaces.ILoader;
import app.banking.bankmuscat.merchant.logger.CLog;
import app.banking.bankmuscat.merchant.util.constant.ErrorAndPopupCodes;


//import com.google.i18n.phonenumbers.NumberParseException;
//import com.google.i18n.phonenumbers.PhoneNumberUtil;
//import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
//import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class BMLogin extends ActivityBase implements ILoader {

    private static final String TAG = "BMLogin";
    private final APIManager apiManager = APIManager.createInstance(null);
    private Wallet.Data appData = Wallet.Data.getInstance();

    public ImageView btnBack, header_home, header_menu_button;
    TextView headingTextView;
    public EditText edtMobileNumber, edtpin3, edtpin1, edtpin2, edtpin4;
    public TextView txtForget,show;
    Button login,forgot;

    @Override
    public int GetScreenLayout() {
        return R.layout.bm_login;
    }

    @Override
    public boolean isHeaderNeeded() {
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //setContentView(R.layout.bm_login);
        ///// getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        btnBack = (ImageView) findViewById(R.id.header_back_button);
        login = (Button) findViewById(R.id.mob_buttonlogin);
        show = (TextView) findViewById(R.id.showpin);
        show.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowPin();
            }
        });
//        register = (Button) findViewById(R.id.mob_buttonregister);
        edtMobileNumber = (EditText) findViewById(R.id.mobedtMobileNumber);

        edtpin1 = (EditText) findViewById(R.id.edtpin1);
        edtpin2 = (EditText) findViewById(R.id.edtpin2);
        edtpin3 = (EditText) findViewById(R.id.edtpin3);
        edtpin4 = (EditText) findViewById(R.id.edtpin4);
        /*edtpin5 = (EditText) findViewById(R.id.edtpin5);*/

        setTextFocus(edtpin1, edtpin2);
        setTextFocus(edtpin2, edtpin3);
        setTextFocus(edtpin3, edtpin4);
       /* setTextFocus(edtpin4, edtpin5);*/

        /*setDeleteFocus(edtpin5, edtpin4);*/
        setDeleteFocus(edtpin4, edtpin3);
        setDeleteFocus(edtpin3, edtpin2);
        setDeleteFocus(edtpin2, edtpin1);


        ///testing

       /* edtMobileNumber.setText("99950800");
        edtpin1.setText("1");
        edtpin2.setText("4");
        edtpin3.setText("7");
        edtpin4.setText("8");
        edtpin5.setText("5");*/

        ///testing


        txtForget = (TextView) findViewById(R.id.mobbtnforget);

        header_home = (ImageView) findViewById(R.id.header_home);
        header_menu_button = (ImageView) findViewById(R.id.header_menu_button);

        headingTextView = (TextView) findViewById(R.id.title_header);
        headingTextView.setText(getResources().getString(R.string.login_button_text));

        edtMobileNumber.requestFocus();
        getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        try {
            getWindow().setBackgroundDrawableResource(R.drawable.loginbg);
        }
        catch (Exception e)
        {
            String s = e.getMessage();
        }

        txtForget.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ForgotPinAction();
            }
        });
        login.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                loginAction();
                // ChipinTEst();
            }
        });/*
        register.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                RegisterAction();
            }
        });*/


        edtpin4.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {

                loginAction();

                return false;
            }

        });


        btnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
               ExitApplication();

            }

        });

    }





    void RegisterAction() {
        //finish();
        ClearInputs();
        Intent i = new Intent(getApplicationContext(), BMRegisterUser.class);
        startActivity(i);
    }

    void ShowPin()
    {
        System.out.println("SHO PIN *************");
        edtpin1.setTransformationMethod(null);
        edtpin2.setTransformationMethod(null);
        edtpin3.setTransformationMethod(null);
        edtpin4.setTransformationMethod(null);
    }

    void ForgotPinAction() {
        //finish();

        String mobileNumber = edtMobileNumber.getText().toString().trim();//"12345678";

        long mobileNumToLong = 0;

        if (mobileNumber.isEmpty()) {
            edtMobileNumber.requestFocus();
            showAlert("Please enter your 8 digit mobile number");

        }

        else if (mobileNumber.substring(0,1).equalsIgnoreCase("0") || mobileNumber.substring(0, 1).equalsIgnoreCase("2")) {//else if (!mobileNumber.substring(0, 1).equalsIgnoreCase("9") && !mobileNumber.substring(0, 1).equalsIgnoreCase("7")) {
            edtMobileNumber.requestFocus();
            showAlert("Please enter a valid mobile number");
        } else {

            mobileNumToLong = Long.parseLong(mobileNumber);

            int length = String.valueOf(mobileNumToLong).length();

            if (length != 8) {
                showAlert(ErrorAndPopupCodes.Invalid_Mobile_Number.getTag());

            } else {
                System.out.println("LOAD QUESTION");
                loadQuestions(mobileNumber);
            }
        }
    }

    public void OnDialogClose(String message) {

        if (message.compareToIgnoreCase(getResources().getString(R.string.userblockedmessage)) == 0) {
            ForgotPinAction();
        }
    }

    public void ChangeDevice(String msisdn, String pin) {
        Intent i = new Intent(getApplicationContext(), BMSubmitOTP.class);
        i.putExtra("Mode", CHANGE_DEVICE);
i.putExtra("Number", msisdn);
        i.putExtra("Pin", pin);
        ClearInputs();
        startActivity(i);
    }

    void loginAction() {
        String mobileNumber = edtMobileNumber.getText().toString().trim();//"12345678";
        String pin = edtpin1.getText().toString().trim() + edtpin2.getText().toString().trim() + edtpin3.getText().toString().trim() + edtpin4.getText().toString().trim() ;//"1234";
        long mobileNumToLong = 0;
        long pinToLong = 0;
       /* TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();
        BMLogin.this.LoginUser(mobileNumber, pin,
                null != imei ? imei : mobileNumber, "29873498wuehwier");*/
        if (mobileNumber.isEmpty()) {

            showAlert("Please enter your 8 digit mobile number");
            ClearInputs();
            edtMobileNumber.requestFocus();

        } else if (isvalidMobile(mobileNumber)) {
            ClearInputs();
            showAlert(ErrorAndPopupCodes.Invalid_Mobile_Number.getTag());
            edtMobileNumber.requestFocus();
        } else if (pin.isEmpty()) {
            ClearPin();
            edtpin1.requestFocus();
            showAlert("Please enter your wallet PIN");

        } else if (inValidPin(pin)) {
            ClearPin();
            edtpin1.requestFocus();
            showAlert(ErrorAndPopupCodes.Invalid_PinInput.getTag());

        } else {
            mobileNumToLong = Long.parseLong(mobileNumber);
            pinToLong = Long.parseLong(pin);
            int length = String.valueOf(mobileNumToLong).length();
            int pinlength = String.valueOf(pinToLong).length();
            if (pinlength != 4) {
                ClearInputs();
                showAlert(ErrorAndPopupCodes.Invalid_PinInput.getTag());
                edtpin1.requestFocus();

            } else if (length != 8) {
                edtMobileNumber.setText("");
                showAlert(ErrorAndPopupCodes.Invalid_Mobile_Number.getTag());
                edtMobileNumber.requestFocus();

            } else {
                if (!verifyInternetStatus()) {
                    ClearInputs();
                    edtMobileNumber.requestFocus();
                    return;
                } else {

                    TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
                    String imei = telephonyManager.getDeviceId();
                    BMLogin.this.LoginUser(mobileNumber, pin);
                  /*  BMLogin.this.LoginUser(mobileNumber, "1357",
                            null != imei ? imei : mobileNumber, "29873498wuehwier");*/
                }
            }
        }
    }

    private boolean isvalidMobile(String mobileNumber) {
        long mobileNumToLong = 0;
        mobileNumToLong = Long.parseLong(mobileNumber);
        int length = String.valueOf(mobileNumToLong).length();
        return length != 8;
    }

    private boolean inValidPin(String pin) {
        long pinToLong = 0;
        pinToLong = Long.parseLong(pin);
        int pinlength = String.valueOf(pinToLong).length();
        return pinlength != 4;
    }

    private void ClearInputs() {
        edtMobileNumber.setText("");
        edtpin1.setText("");
        edtpin2.setText("");
        edtpin3.setText("");
        edtpin4.setText("");

        edtMobileNumber.requestFocus();
    }

    private void ClearPin() {

        edtpin1.setText("");
        edtpin2.setText("");
        edtpin3.setText("");
        edtpin4.setText("");

        edtpin1.requestFocus();
    }

    /**
     * validate msisdn api call
     */
    private void LoginUser(final String msisdn, final String pin) {
        if (!verifyInternetStatus()) {
            return;
        }
        showLoader();
        DbManager dbManager = DbManager.createAuto(BMLogin.this);

        dbManager.setMsisdn(msisdn);

        Wallet.Data.syncRead(getApplicationContext());

        try {
            final UUID resultId = apiManager.UserRequest(msisdn,pin);

            final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

                @Override
                public void handleMessage(APIManager.SOAPOperationData data) {
                    try {

                        if (data.getId() == resultId) {
                            if("USERENQRESP".equalsIgnoreCase((String)data.getResponseDataObj().get("TYPE")) && "200".equalsIgnoreCase((String)data.getResponseDataObj().get("TXNSTATUS"))) {
                                apiManager.removeListener(this);
                                hideLoader();
                                CLog.i(TAG, "User login success");
                                appData.setMsisdn(msisdn);
                                appData.setPinIntentString(pin);
                                appData.setUserName((String)data.getResponseDataObj().get("FNAME"));
                                appData.setUniqueId((String)data.getResponseDataObj().get("AGENTCODE"));
                                appData.setUniqueAcctId((String)data.getResponseDataObj().get("USERID"));
                                Intent i = new Intent(getBaseContext(), BMHome.class);
                                startActivity(i);

                            }else if("USERENQRESP".equalsIgnoreCase((String)data.getResponseDataObj().get("TYPE")) && "003590".equalsIgnoreCase((String)data.getResponseDataObj().get("TXNSTATUS")))
                            {
                                apiManager.removeListener(this);
                                hideLoader();
                                GetQuestion(msisdn);
                            }
                            else{
                                apiManager.removeListener(this);
                                hideLoader();
                                CLog.i(TAG, "User login failure");
                                showAlert((String)data.getResponseDataObj().get("MESSAGE"));
                                ClearInputs();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        hideLoader();
                        ClearInputs();
                        new AlertDialog.Builder(BMLogin.this)
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
            hideLoader();
            ClearInputs();
        }
    }


    private void GetQuestion(final String msisdn) {
        if (!verifyInternetStatus()) {
            return;
        }
        showLoader();
        DbManager dbManager = DbManager.createAuto(BMLogin.this);

        dbManager.setMsisdn(msisdn);

        Wallet.Data.syncRead(getApplicationContext());

        try {
            final UUID resultId = apiManager.BMUnansweredQn(msisdn);

            final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

                @Override
                public void handleMessage(APIManager.SOAPOperationData data) {
                    try {

                        if (data.getId() == resultId) {
                            if("UANSQNSRESP".equalsIgnoreCase((String)data.getResponseDataObj().get("TYPE")) && "200".equalsIgnoreCase((String)data.getResponseDataObj().get("TXNSTATUS"))) {
                                apiManager.removeListener(this);
                                System.out.println("HJDGHSGHS");
                                hideLoader();
                                if(data.getResponseDataObj().get("DATA").toString().equalsIgnoreCase("{}"))
                                {
                                    Intent i = new Intent(getBaseContext(), BMResetPin.class);
                                    i.putExtra("Number",msisdn);
                                    startActivity(i);
                                }else

                                {
                                    Intent i = new Intent(getBaseContext(), BMSecurityQuestion.class);
                                    i.putExtra("Number",msisdn);
                                    startActivity(i);
                                }


                            }else
                            {
                                apiManager.removeListener(this);
                                hideLoader();
                                CLog.i(TAG, "User login failure");
                                showAlert((String)data.getResponseDataObj().get("MESSAGE"));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        hideLoader();
                        ClearInputs();
                        new AlertDialog.Builder(BMLogin.this)
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
            hideLoader();
            ClearInputs();
        }
    }


    void loadQuestions(final String mobileNumber){
        if (!verifyInternetStatus()) {
            return;
        }
        showLoader();
        DbManager dbManager = DbManager.createAuto(BMLogin.this);

        dbManager.setMsisdn(mobileNumber);

        Wallet.Data.syncRead(getApplicationContext());

        try {
            final UUID resultId = apiManager.BMGetSecurityQuestions(mobileNumber);

            final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

                @Override
                public void handleMessage(APIManager.SOAPOperationData data) {
                    try {

                        if (data.getId() == resultId) {
                            if("GETANSRESP".equalsIgnoreCase((String)data.getResponseDataObj().get("TYPE")) && "200".equalsIgnoreCase((String)data.getResponseDataObj().get("TXNSTATUS"))) {
                                apiManager.removeListener(this);
                                hideLoader();
                                ClearInputs();
                                Intent i = new Intent(getApplicationContext(), BMForgotPin.class);
                                i.putExtra("Number", mobileNumber);
                                startActivity(i);

                            }else
                            {
                                apiManager.removeListener(this);
                                hideLoader();
                                showAlert("Security Questions have not been set. Please contact service center");

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        hideLoader();
                        new AlertDialog.Builder(BMLogin.this)
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
            hideLoader();
        }
    }





    @Override
    public boolean onTouchEvent(MotionEvent event) {
        hideSoftKeyboard();
        return false;
    }

    void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtMobileNumber.getWindowToken(), 0);
    }

    void CloseWin() {
/*hideSoftKeyboard();
                NavUtils.navigateUpTo(BMForgotPin.this, NavUtils
						.getParentActivityIntent(BMForgotPin.this));*/
        this.finish();
    }

    @Override
    public void onBackPressed() {

      //  CloseWin();
        ExitApplication();
    }
}
