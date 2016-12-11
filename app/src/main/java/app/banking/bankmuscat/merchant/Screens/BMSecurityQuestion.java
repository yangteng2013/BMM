package app.banking.bankmuscat.merchant.Screens;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.UUID;

import app.banking.bankmuscat.R;
import app.banking.bankmuscat.merchant.api.APIManager;
import app.banking.bankmuscat.merchant.base.ActivityBase;
import app.banking.bankmuscat.merchant.common.Wallet;
import app.banking.bankmuscat.merchant.components.widgets.RobotoTextView;
import app.banking.bankmuscat.merchant.db.DbManager;
import app.banking.bankmuscat.merchant.entity.instrument.SecurityQuestions;
import app.banking.bankmuscat.merchant.interfaces.ILoader;
import app.banking.bankmuscat.merchant.logger.CLog;
import app.banking.bankmuscat.merchant.util.constant.ErrorAndPopupCodes;


//import com.google.i18n.phonenumbers.NumberParseException;
//import com.google.i18n.phonenumbers.PhoneNumberUtil;
//import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
//import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class BMSecurityQuestion extends ActivityBase implements ILoader {

    private static final String TAG = "BMRegisterUser";
    private final APIManager apiManager = APIManager.createInstance(null);
    private Wallet.Data appData = Wallet.Data.getInstance();
    RobotoTextView ques1,ques2,ques3;
    public ImageView btnBack, header_home, header_menu_button, spinner_arrow;
    TextView headingTextView;
    public EditText mobedtanswer,mobedtanswer2;
    /*Spinner ques;*/
    Button next, cancel;
    private String question = "{\\\"QUESTION\\\":[{\\\"LANGCODE\\\":1,\\\"QNSCODE\\\":101,\\\"QUESTION\\\":\\\"What is your favourite colour\\\",\\\"ANSWER\\\":\\\"###1###\\\",\\\"USERID\\\":\\\"###UID###\\\"},\n" +
            "{\\\"LANGCODE\\\":1,\\\"QNSCODE\\\":102,\\\"QUESTION\\\":\\\"What is your first school\\\",\\\"ANSWER\\\":\\\"###2###\\\",\\\"USERID\\\":\\\"###UID###\\\"}]}";
    //private String pin;

    @Override
    public int GetScreenLayout() {
        return R.layout.bm_security_question;
    }

    @Override
    public boolean isHeaderNeeded() {
        return false;
    }

    ArrayList<SecurityQuestions> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Question:::"+question);
        btnBack = (ImageView) findViewById(R.id.header_back_button);
        next = (Button) findViewById(R.id.mob_buttonnext);
        cancel = (Button) findViewById(R.id.mob_buttoncancel);
        mobedtanswer = (EditText) findViewById(R.id.mobedtanswer);
        mobedtanswer2 = (EditText) findViewById(R.id.mobedtanswer2);

        /*ques = (Spinner) findViewById(R.id.spn_ques);*/
        ques1=(RobotoTextView) findViewById(R.id.spn_ques);
        ques2=(RobotoTextView) findViewById(R.id.spn_ques2);

        ques1.setText("What is your favourite colour ?");
        ques2.setText("What is your first school ?");



       /* LoadQuestions();*/


        header_home = (ImageView) findViewById(R.id.header_home);
        header_menu_button = (ImageView) findViewById(R.id.header_menu_button);

        headingTextView = (TextView) findViewById(R.id.title_header);
        headingTextView.setText(getResources().getString(R.string.topbar_security_question));

        mobedtanswer.requestFocus();
        getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        next.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                nextAction();
            }
        });
        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                CloseWin();
            }
        });
        btnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                CloseWin();

            }

        });

    }

    void CloseWin() {
        this.finish();
    }

    void nextAction() {


        if (mobedtanswer.getText().toString().trim().isEmpty() || mobedtanswer2.getText().toString().trim().isEmpty()) {

            showAlert("Please enter answers to both the questions");
        } else if (mobedtanswer.getText().toString().trim().length() < 3 || mobedtanswer2.getText().toString().trim().length() < 3) {

            showAlert("Answers should have atleast atleast 3 characters");
        } else {
            if (!verifyInternetStatus()) {
                return;
            } else {
                String answer1= mobedtanswer.getText().toString();
                String answer2= mobedtanswer2.getText().toString();
                question=question.replaceAll("###1###",answer1);
                question=question.replaceAll("###2###",answer2);
                question=question.replaceAll("###UID###",appData.getUniqueAcctId());
                SetQuestion(getIntent().getExtras().getString("Number"),question);

            }
        }
    }


    private void SetQuestion(final String msisdn,final String question) {
        if (!verifyInternetStatus()) {
            return;
        }
        showLoader();
        DbManager dbManager = DbManager.createAuto(BMSecurityQuestion.this);

        dbManager.setMsisdn(msisdn);

        Wallet.Data.syncRead(getApplicationContext());

        try {
            final UUID resultId = apiManager.BMSetSecurityQuestions(msisdn,question);

            final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

                @Override
                public void handleMessage(APIManager.SOAPOperationData data) {
                    try {
                                    apiManager.removeListener(this);
                                    Intent i = new Intent(getBaseContext(), BMResetPin.class);
                                    i.putExtra("Number",msisdn);
                                     startActivity(i);
                    } catch (Exception e) {
                        e.printStackTrace();
                        hideLoader();
                        new AlertDialog.Builder(BMSecurityQuestion.this)
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



    private void LoadQuestions() {
        if (!verifyInternetStatus()) {
            return;
        }
        showLoader();


        try {

            final UUID resultId = apiManager.BMGetAllSecurityQuestions();

            final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

                @Override
                public void handleMessage(APIManager.SOAPOperationData data) {
                    try {

                        if (data.getId() == resultId) {
                            apiManager.removeListener(this);
                            hideLoader();


                            if (!data.hasError()) {
                                CLog.i(TAG, "User login success");
                                System.out.println("Service Id............." + data.getServiceId());
                                //appData.setServiceId(data.getServiceId());

                                questions = data.getResponseData().securityQuestionDetailList;

                                SetQuestions();

                                hideLoader();
                            } else {
                                hideLoader();
                                CLog.w(TAG, "in error : " + data.getError());
                                showAlert(data.getResponseData().txnMessage);
                            }

                        } else {
                            hideLoader();
                            CLog.w(TAG, "out error : " + data.getError());
                            showAlert(data.getResponseData().txnMessage);
                        }
                    } catch (Exception e) {

                        new AlertDialog.Builder(BMSecurityQuestion.this)
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

    private void AddSecurityAnswer(String questionID, String answer) {
        if (!verifyInternetStatus()) {
            return;
        }
        showLoader();


        try {


            final UUID resultId = apiManager.BMAddSecurityAnswer(appData.getUniqueId(), questionID, answer);

            final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

                @Override
                public void handleMessage(APIManager.SOAPOperationData data) {
                    try {

                        if (data.getId() == resultId) {
                            apiManager.removeListener(this);



                            if (!data.hasError()) {
                                CLog.i(TAG, "User login success");
                                System.out.println("Service Id............." + data.getServiceId());
                                appData.setServiceId(data.getServiceId());
                                Login();


                            } else {
                                hideLoader();
                                CLog.w(TAG, "in error : " + data.getError());
                                showAlert(data.getResponseData().txnMessage);
                            }

                        } else {
                            hideLoader();
                            CLog.w(TAG, "out error : " + data.getError());
                            showAlert(data.getResponseData().txnMessage);
                        }
                    } catch (Exception e) {
hideLoader();
                        new AlertDialog.Builder(BMSecurityQuestion.this)
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

   /* private void Login() {


        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();

        LoginUser(appData.getMsisdn(), pin, null != imei ? imei : appData.getMsisdn(), "aaaaaa");

    }*/

    private void Login() {


        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();

        LoginUser(appData.getMsisdn(), appData.getUser().pin, null != imei ? imei : appData.getMsisdn(), "aaaaaa");

    }

    private void LoginUser(String msisdn, String pin, String imei, String rnsId) {
        if (!verifyInternetStatus()) {
            return;
        }
        showLoader();
        DbManager dbManager = DbManager.createAuto(BMSecurityQuestion.this);
        dbManager.setImei(imei);
        dbManager.setMsisdn(msisdn);

        Wallet.Data.syncRead(getApplicationContext());

        try {

            final UUID resultId = apiManager.BMLogin(msisdn, pin, imei, rnsId);

            final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

                @Override
                public void handleMessage(APIManager.SOAPOperationData data) {
                    try {

                        if (data.getId() == resultId) {
                            apiManager.removeListener(this);
                            hideLoader();


                            if (!data.hasError()) {
                                CLog.i(TAG, "User login success");
                                System.out.println("Service Id............." + data.getServiceId());
                                appData.setServiceId(data.getServiceId());
                                appData.setUniqueId(data.getUniqueId());
                                appData.setUserData(data.getResponseData());

                                Intent i = new Intent(getApplicationContext(), BMHome.class);
                                i.putExtra("Mode", true);
                                CloseWin();
                                hideLoader();
                                startActivity(i);


                               /* Intent i = new Intent(getApplicationContext(), BMHome.class);
                                i.putExtra("ProfileData", appData.getUserData());
                                i.putExtra("Mode", false);
                                startActivity(i);*/
                                hideLoader();
                            } /*else {
                            if ("225".equals(data.getError())) {

								DbManager dbManager = DbManager.createAuto(BMLogin.this);

								String uniqueId = (String) data.getResponse().getProperty("uniqueId").toString().trim();
								appData.setServiceId(data.getServiceId());
								appData.setValidateMsisdn(true);
								System.out.println("SERVICE ID......." + data.getServiceId());
								appData.setUniqueId(uniqueId);
								CLog.i(TAG, "UniqueID  success:" + uniqueId);
								dbManager.setUniqueId(uniqueId);
								Intent i = new Intent(getApplicationContext(),
										RegistrationForgetPin.class);
								i.putExtra("Registration", "Registration");
								startActivity(i);
								hideLoader();
							} else if ("260".equals(data.getError())) {

								Intent i = new Intent(getApplicationContext(),
										RegistrationPin.class);
								startActivity(i);
								hideLoader();
							}*/ else {
                                hideLoader();
                                CLog.w(TAG, "in error : " + data.getError());
                                showAlert(data.getResponseData().txnMessage);
                            }
                            //      showAlert(data.getResponseData().txnMessage);
                            //  }
                        } else {
                            hideLoader();
                            CLog.w(TAG, "out error : " + data.getError());
                            showAlert(data.getResponseData().txnMessage);
                        }
                    } catch (Exception e) {

                        new AlertDialog.Builder(BMSecurityQuestion.this)
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

    private void SetQuestions() {
        String spinnerArray[] = {};

        ArrayList<String> spinnerArray1 = new ArrayList<>();

        for (int i = 0; i < questions.size(); i++) {

            // spinnerArray[i]= questions.get(i).securityQuestion;
            spinnerArray1.add(questions.get(i).securityQuestion);
        }

        BMSpinnerAdapter spinnerArrayAdapter = new BMSpinnerAdapter(this, android.R.layout.simple_spinner_item, spinnerArray1);
        // BMSpinnerAdapter spinnerArrayAdapter = new BMSpinnerAdapter(this, R.layout.bm_spinner_bg, spinnerArray1);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
/*        ques.setAdapter(spinnerArrayAdapter);

        spinner_arrow.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ques.performClick();
            }
        });*/
    }




    @Override
    public boolean onTouchEvent(MotionEvent event) {
        hideSoftKeyboard();
        return false;
    }

    void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mobedtanswer.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
       /* hideSoftKeyboard();
        NavUtils.navigateUpTo(BMSecurityQuestion.this, NavUtils.getParentActivityIntent(BMSecurityQuestion.this));*/
    }


}
