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
import android.widget.TextView;

import java.util.UUID;

import app.banking.bankmuscat.R;
import app.banking.bankmuscat.merchant.api.APIManager;
import app.banking.bankmuscat.merchant.base.ActivityBase;
import app.banking.bankmuscat.merchant.common.Wallet;
import app.banking.bankmuscat.merchant.entity.instrument.SecurityQuestions;
import app.banking.bankmuscat.merchant.interfaces.ILoader;
import app.banking.bankmuscat.merchant.logger.CLog;
import app.banking.bankmuscat.merchant.util.constant.ErrorAndPopupCodes;


//import com.google.i18n.phonenumbers.NumberParseException;
//import com.google.i18n.phonenumbers.PhoneNumberUtil;
//import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
//import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class BMEnterSecret extends ActivityBase implements ILoader {

    private static final String TAG = "BMRegisterUser";
    private final APIManager apiManager = APIManager.createInstance(null);
    private Wallet.Data appData = Wallet.Data.getInstance();

    public ImageView btnBack, header_home, header_menu_button;
    TextView headingTextView, mobtxtquestion;
    public EditText edtsecret;

    Button next, cancel;


    int mode = NO_TYPE;
    String mobileNumber = "";
    String newEmail = "";
    String nid = "";
    String pin = "";
    String savedQuestion = "";

    @Override
    public int GetScreenLayout() {
        return R.layout.bm_enter_sectret;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mode = getIntent().getExtras().getInt("Mode");
        if (mode == CHANGE_NUMBER)
            mobileNumber = getIntent().getExtras().getString("NewNumber");
        else if (mode == CHANGE_EMAIL)
            newEmail = getIntent().getExtras().getString("NewEmail");


        else if (mode == CHANGE_EMAILORNUMBER)
            mobileNumber = getIntent().getExtras().getString("NewNumber");


        else if (mode == FORGOT_PIN) {
            RemoveHeaderButtons();
            mobileNumber = getIntent().getExtras().getString("Number");
            nid = getIntent().getExtras().getString("Nid");
        }

        else if (mode == CHANGE_DEVICE) {
            //savedQuestion = getIntent().getExtras().getString("Question");
            RemoveHeaderButtons();
mobileNumber = getIntent().getExtras().getString("Number");
            pin = getIntent().getExtras().getString("Pin");
            question = new SecurityQuestions();
            question.securityQuestion = appData.getUserData().securityQuestion;
            question.securityQuestionId = appData.getUserData().securityQuestionId;
        }

        else if (mode == CHANGE_DEVICE_FORGOTPIN) {
            RemoveHeaderButtons();
            //savedQuestion = getIntent().getExtras().getString("Question");
            mobileNumber = getIntent().getExtras().getString("Number");
            nid = getIntent().getExtras().getString("Nid");
            question = new SecurityQuestions();
            question.securityQuestion = appData.getUserData().securityQuestion;
            question.securityQuestionId = appData.getUserData().securityQuestionId;
        }

        //setContentView(R.layout.bm_enter_sectret);
        btnBack = (ImageView) findViewById(R.id.header_back_button);
        next = (Button) findViewById(R.id.mob_buttonnext);
        cancel = (Button) findViewById(R.id.mob_buttoncancel);
        edtsecret = (EditText) findViewById(R.id.edtsecret);
        mobtxtquestion = (TextView) findViewById(R.id.mobtxtquestion);
//mobtxtquestion.setText("What is your favorite color??");

        if(mode == CHANGE_DEVICE || mode == CHANGE_DEVICE_FORGOTPIN)
        SetQuestion();
        else
            LoadQuestion();

        header_home = (ImageView) findViewById(R.id.header_home);
        header_menu_button = (ImageView) findViewById(R.id.header_menu_button);

        headingTextView = (TextView) findViewById(R.id.title_header);
        headingTextView.setText(getResources().getString(R.string.answerquestion));

        edtsecret.requestFocus();
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
        /*edtpin1.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {

				edtpin2.requestFocus();
				getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);

				return false;
			}

		});*/


        btnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                hideSoftKeyboard();
				/*NavUtils.navigateUpTo(BMEnterSecret.this, NavUtils
						.getParentActivityIntent(BMEnterSecret.this));*/
                CloseWin();

            }

        });

    }

    void CloseWin() {
/*hideSoftKeyboard();
				NavUtils.navigateUpTo(BMEnterEmail.this, NavUtils
						.getParentActivityIntent(BMEnterEmail.this));*/
        this.finish();
    }

    void nextAction() {


        if (edtsecret.getText().toString().trim().isEmpty()) {

            showAlert("Please enter your secret answer");
        } else {


            if (!verifyInternetStatus()) {
                return;
            } else {

                TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
                String imei = telephonyManager.getDeviceId();

                question.answer = edtsecret.getText().toString().trim();

                if (mode == FORGOT_PIN)
                    ForgotPinInit(mobileNumber, question.securityQuestion, question.answer);
                else if (mode == CHANGE_DEVICE) {


                    VerifySecurityAnswer(mobileNumber, pin, null != imei ? imei : mobileNumber, appData.getUniqueId(), question.securityQuestionId, question.answer);
                }

                else if (mode == CHANGE_DEVICE_FORGOTPIN) {


                    VerifySecurityAnswerFogotPin(mobileNumber, nid, null != imei ? imei : mobileNumber, appData.getUniqueId(), question.securityQuestionId, question.answer);
                }


///wt
                else if (mode == CHANGE_EMAILORNUMBER) {

                        ValidateSecurityAnswer();
                    }



                else

                    ValidateSecurityAnswer();


            }
        }

    }

    SecurityQuestions question;

    private void LoadQuestion() {

    }

    private void ValidateSecurityAnswer() {
        if (!verifyInternetStatus()) {
            return;
        }
        showLoader();


        try {

            final UUID resultId = apiManager.BMValidateSecurityAnswer(appData.getUniqueId(), question.securityQuestionId, question.answer);

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

                                if (mode == CHANGE_EMAILORNUMBER) {

                                    ChangeMSISDN(mobileNumber, "1234", mobileNumber);
                                }
                                else
                                ChangeProfileDetails();

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

                        new AlertDialog.Builder(BMEnterSecret.this)
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


    private void VerifySecurityAnswer(String msisdn, String pin, String imei, String uniqueId, String securityQuestionId, String securityAnswer) {
        if (!verifyInternetStatus()) {
            return;
        }
        showLoader();


        try {

            final UUID resultId = apiManager.BM_VerifySecurityAnswer( msisdn,  pin,  imei,  uniqueId,  securityQuestionId,  securityAnswer);

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
                                appData.setUserData(data.getResponseData());
                                Intent i = new Intent(getApplicationContext(), BMHome.class);
                                i.putExtra("ProfileData", appData.getUserData());
                                i.putExtra("Mode", false);
                                startActivity(i);

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

                        new AlertDialog.Builder(BMEnterSecret.this)
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


    private void VerifySecurityAnswerFogotPin(final String msisdn, final String nid, String imei, String uniqueId, String securityQuestionId, String securityAnswer) {
        if (!verifyInternetStatus()) {
            return;
        }
        showLoader();

        try {

            final UUID resultId = apiManager.BM_VerifySecurityAnswerForgotPIN( msisdn,  nid,  imei,  uniqueId,  securityQuestionId,  securityAnswer);

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
                          //      appData.setUserData(data.getResponseData());
                                Intent i = new Intent(getApplicationContext(), BMSubmitOTP.class);
                                i.putExtra("Mode", CHANGE_DEVICE_FORGOTPINFINAL);
                                i.putExtra("Number", msisdn);
                                i.putExtra("Nid", nid);
                                startActivity(i);

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

                        new AlertDialog.Builder(BMEnterSecret.this)
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

    private void SetQuestion() {
        mobtxtquestion.setText(question.securityQuestion);
    }



    private void ChangeProfileDetails() {
        if (!verifyInternetStatus()) {
            return;
        }
        showLoader();
		/*DbManager dbManager = DbManager.createAuto(BMLogin.this);
		dbManager.setImei(imei);
		dbManager.setMsisdn(msisdn);*/

        Wallet.Data.syncRead(getApplicationContext());

        try {

            APIManager.ResponseData data = appData.getUserData();
            String a = data.firstName;
            //	final UUID resultId = apiManager.BMCustomerRegister("frstnm","lstnm","tem1p@gmail.com","91666666","05-10-2016","N","5454875458","458745","male","india","bleh576");
            // String image, String uniqueId, String emailId, String mobileNumber, String dateOfBirth, String firstName, String lastName
            final UUID resultId = apiManager.BMChangeProfileDetails(data.image, data.uniqueId, data.emailId, mobileNumber, data.dateOfBirth, data.firstName, data.firstName);

            final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

                @Override
                public void handleMessage(APIManager.SOAPOperationData data) {
                    try {

                        if (data.getId() == resultId) {
                            apiManager.removeListener(this);
                            hideLoader();

                            if (!data.hasError()) {
                                CLog.i(TAG, "success");
                                System.out.println("Service Id............." + data.getServiceId());
                                appData.setServiceId(data.getServiceId());

                                Intent i = new Intent(getApplicationContext(), BMChangesMade.class);
                                i.putExtra("Title", getResources().getString(R.string.changesmade));
                                i.putExtra("SubtTitle", "");
                                if (mode == CHANGE_NUMBER) {
                                    i = new Intent(getApplicationContext(), BMChangesMade.class);
                                    i.putExtra("Title", getResources().getString(R.string.changemobile));
                                    i.putExtra("SubtTitle", getResources().getString(R.string.changemobiledisclaimer));
                                    appData.setMsisdn(mobileNumber);
                                    appData.getUserData().mobileNumber = mobileNumber;

                                }
                                CloseWin();
                                startActivity(i);


                            } else {

							/*	Intent i = new Intent(getApplicationContext(), BMChangesMade.class);
								CloseWin();
								startActivity(i);*/

							/*if ("225".equals(data.getError())) {

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
							}

							else {
								hideLoader();
								CLog.w(TAG, "in error : " + data.getError());
								showAlert(data.getResponseData().txnMessage);
							}*/
								/*hideLoader();
								CLog.w(TAG, "in error : " + data.getError());
								showAlert(data.getResponseData().txnMessage);*/

                               /* Intent i = new Intent(getApplicationContext(), BMChangesMade.class);
                                 if(mode ==CHANGE_EMAIL) {
                                     i = new Intent(getApplicationContext(), BMChangesMade.class);
                                     appData.setEmail(newEmail);
                                 }
                                else if(mode ==REGISTER_USER)
                                 i = new Intent(getApplicationContext(), BMSetPin.class);
                                else if(mode ==FORGOT_PIN)
                                    i = new Intent(getApplicationContext(), BMChangePin.class);
                               CloseWin();
                                startActivity(i);*/
                                hideLoader();
                                showAlert(data.getResponseData().txnMessage);
                            }
                        } else {
                            hideLoader();
                            CLog.w(TAG, "out error : " + data.getError());
                            showAlert(data.getResponseData().txnMessage);
							/*Intent i = new Intent(getApplicationContext(), BMChangesMade.class);

							CloseWin();
							startActivity(i);*/
                            //hideLoader();
                        }
                    } catch (Exception e) {

                        new AlertDialog.Builder(BMEnterSecret.this)
                                .setTitle("Error")
                                .setMessage(ErrorAndPopupCodes.No_Response.getTag())
                                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        //ErrorAction();
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


    private void ForgotPinInit(final String mobileNumber, final String question, final String answer) {
        if (!verifyInternetStatus()) {
            return;
        }
        showLoader();
		/*DbManager dbManager = DbManager.createAuto(BMLogin.this);
		dbManager.setImei(imei);
		dbManager.setMsisdn(msisdn);*/

        Wallet.Data.syncRead(getApplicationContext());

        try {

            final UUID resultId = null;//apiManager.BMForgotPinInit(mobileNumber, question, answer);

            final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

                @Override
                public void handleMessage(APIManager.SOAPOperationData data) {
                    try {

                        if (data.getId() == resultId) {
                            apiManager.removeListener(this);
                            hideLoader();

                            if (!data.hasError()) {
                                appData.setUserData(data.getResponseData());
                                appData.setUniqueId(data.getUniqueId());
                                hideLoader();
                                Intent i = new Intent(getApplicationContext(), BMResetPin.class);
                                i.putExtra("Nid", nid);
                                i.putExtra("Number", mobileNumber);
                                startActivity(i);
                            } else {
							/*if ("225".equals(data.getError())) {

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
							}

							else {
								hideLoader();
								CLog.w(TAG, "in error : " + data.getError());
								showAlert(data.getResponseData().txnMessage);
							}*/
                                hideLoader();
                                showAlert(data.getResponseData().txnMessage);
                            }
                        } else {
                            hideLoader();
                            CLog.w(TAG, "out error : " + data.getError());
                            showAlert(data.getResponseData().txnMessage);
                        }
                    } catch (Exception e) {

                        new AlertDialog.Builder(BMEnterSecret.this)
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
        imm.hideSoftInputFromWindow(edtsecret.getWindowToken(), 0);
    }

	/*@Override
	public void onBackPressed() {
		hideSoftKeyboard();
		NavUtils.navigateUpTo(BMEnterSecret.this, NavUtils.getParentActivityIntent(BMEnterSecret.this));
	}*/


    ////wt

    private void ChangeMSISDN(String msisdn, String otp, final String mobileNumber) {
        if (!verifyInternetStatus()) {
            return;
        }
        showLoader();
		/*DbManager dbManager = DbManager.createAuto(BMLogin.this);
		dbManager.setImei(imei);
		dbManager.setMsisdn(msisdn);*/

        Wallet.Data.syncRead(getApplicationContext());

        try {


            final UUID resultId = apiManager.BMChangeMSISDN(appData.getUserData().uniqueId, msisdn, otp, mobileNumber);

            final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

                @Override
                public void handleMessage(APIManager.SOAPOperationData data) {
                    try {

                        if (data.getId() == resultId) {
                            apiManager.removeListener(this);
                            hideLoader();


                            if (!data.hasError()) {
                                CLog.i(TAG, "success");
                                System.out.println("Service Id............." + data.getServiceId());
                                appData.setServiceId(data.getServiceId());

                                Intent i = new Intent(getApplicationContext(), BMChangesMade.class);

                                appData.setMsisdn(mobileNumber);
                                appData.getUserData().mobileNumber = mobileNumber;
                                i.putExtra("Title", getResources().getString(R.string.changemobile));
                                i.putExtra("SubtTitle", getResources().getString(R.string.changemobiledisclaimer));

                                CloseWin();
                                startActivity(i);


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

                        new AlertDialog.Builder(BMEnterSecret.this)
                                .setTitle("Error")
                                .setMessage(ErrorAndPopupCodes.No_Response.getTag())
                                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        //ErrorAction();
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Logout();
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
}
