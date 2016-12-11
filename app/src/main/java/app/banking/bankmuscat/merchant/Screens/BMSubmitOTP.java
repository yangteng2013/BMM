package app.banking.bankmuscat.merchant.Screens;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.UUID;

import app.banking.bankmuscat.R;
import app.banking.bankmuscat.merchant.api.APIManager;
import app.banking.bankmuscat.merchant.base.ActivityBase;
import app.banking.bankmuscat.merchant.common.Wallet;
import app.banking.bankmuscat.merchant.entity.instrument.User;
import app.banking.bankmuscat.merchant.interfaces.ILoader;
import app.banking.bankmuscat.merchant.logger.CLog;
import app.banking.bankmuscat.merchant.util.constant.ErrorAndPopupCodes;


//import com.google.i18n.phonenumbers.NumberParseException;
//import com.google.i18n.phonenumbers.PhoneNumberUtil;
//import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
//import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class BMSubmitOTP extends ActivityBase implements ILoader {

    private static final String TAG = "BMSubmitOtp";
    private final APIManager apiManager = APIManager.createInstance(null);
    private Wallet.Data appData = Wallet.Data.getInstance();

    public ImageView btnBack, header_home, header_menu_button;
    TextView headingTextView;
    public EditText edtotp1, edtotp2, edtotp3, edtotp4;
    Button next, cancel;
    RelativeLayout pageScroll, top_header;
    int mode = NO_TYPE;
    String newMobileNumber = "";
    String newEmail = "";
    String pin = "";
    Button mobedtresendotp;
    boolean isSaved = false;
    String nid = "";//, pin;
    User user;

    String amount, description;
    // BMLoadSVNAmount.TransactionData transactionData;

    @Override
    public int GetScreenLayout() {
        return R.layout.bm_submit_otp;
    }

    @Override
    public boolean isHeaderNeeded() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mode = getIntent().getExtras().getInt("Mode");
        String Title = getIntent().getExtras().getString("Title");

        if (mode == REGISTER_USER) {
            user = appData.getUser();
            //   newMobileNumber = getIntent().getExtras().getString("Number");
            //   newEmail = getIntent().getExtras().getString("Email");
        } else if (mode == CHANGE_NUMBER)
            newMobileNumber = getIntent().getExtras().getString("NewNumber");
        else if (mode == CHANGE_EMAIL)
            newEmail = getIntent().getExtras().getString("NewEmail");



        else if (mode == FORGOT_PIN || mode == CHANGE_DEVICE_FORGOTPINFINAL) {
            RemoveHeaderButtons();
            nid = getIntent().getExtras().getString("Nid");
            newMobileNumber = getIntent().getExtras().getString("Number");
            //pin = getIntent().getExtras().getString("Pin");
        } else if (mode == LOAD_SVA) {
            amount = getIntent().getExtras().getString("Amount");
            description = getIntent().getExtras().getString("Description");
            isSaved = getIntent().getExtras().getBoolean("IsSaved");
            //transactionData = (BMLoadSVNAmount.TransactionData) getIntent().getExtras().get("TransactionData");
        } else if (mode == CHANGE_EMAILORNUMBER) {
            newMobileNumber = getIntent().getExtras().getString("Number");

            newEmail = getIntent().getExtras().getString("Email");
        } else if (mode == CHANGE_DEVICE) {
            RemoveHeaderButtons();
            newMobileNumber = getIntent().getExtras().getString("Number");
            pin = getIntent().getExtras().getString("Pin");
        } else if (mode == CHANGE_DEVICE_FORGOTPIN) {
            RemoveHeaderButtons();
            newMobileNumber = getIntent().getExtras().getString("Number");
            nid = getIntent().getExtras().getString("Nid");
        }


        // setContentView(R.layout.bm_submit_otp);

        if (mode == REGISTER_USER) {
            GenerateOtp(user.mobileNumber, user.emailId, appData.getUniqueId());
        } else if (mode != FORGOT_PIN && mode != LOAD_SVA && mode != CHANGE_EMAILORNUMBER && mode != CHANGE_DEVICE && mode != CHANGE_DEVICE_FORGOTPIN) {
            GenerateOtp(appData.getUserData().mobileNumber, appData.getUserData().emailId, appData.getUniqueId());
        }


        /*if(mode == FORGOT_PIN){
            GenerateOtp(newMobileNumber, newEmail, appData.getUniqueId());}
        else
        {
           GenerateOtp(appData.getUserData().mobileNumber,appData.getUserData().emailId, appData.getUniqueId());
        }*/

        // GenerateOtp(appData.getUserData().mobileNumber,appData.getUserData().emailId, appData.getUniqueId());

        pageScroll = (RelativeLayout) findViewById(R.id.mob_page_scroll_rel_layout);
        top_header = (RelativeLayout) findViewById(R.id.header_layout);
        if (mode == REGISTER_USER) {
            pageScroll.setVisibility(View.VISIBLE);
            top_header.setVisibility(View.GONE);
        } else {
            pageScroll.setVisibility(View.GONE);
            top_header.setVisibility(View.VISIBLE);
        }

        mobedtresendotp = (Button) findViewById(R.id.mobedtresendotp);

        btnBack = (ImageView) findViewById(R.id.header_back_button);
        next = (Button) findViewById(R.id.mob_buttonnext);
        cancel = (Button) findViewById(R.id.mob_buttoncancel);
        edtotp1 = (EditText) findViewById(R.id.edtotp1);
        edtotp2 = (EditText) findViewById(R.id.edtotp2);
        edtotp3 = (EditText) findViewById(R.id.edtotp3);
        edtotp4 = (EditText) findViewById(R.id.edtotp4);

        setTextFocus(edtotp1, edtotp2);
        setTextFocus(edtotp2, edtotp3);
        setTextFocus(edtotp3, edtotp4);

        setDeleteFocus(edtotp4, edtotp3);
        setDeleteFocus(edtotp3, edtotp2);
        setDeleteFocus(edtotp2, edtotp1);


        header_home = (ImageView) findViewById(R.id.header_home);
        header_menu_button = (ImageView) findViewById(R.id.header_menu_button);

        headingTextView = (TextView) findViewById(R.id.title_header);
        headingTextView.setText(Title);

        edtotp1.requestFocus();
        getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        mobedtresendotp.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ClearOTP();
                EnableOrDisableOTP(true);
                if (mode != REGISTER_USER)
                    GenerateOtp(appData.getUserData().mobileNumber, appData.getUserData().emailId, appData.getUniqueId());
                else
                    PerformDedupe(appData.getUser().emailId, appData.getUser().mobileNumber);

            }
        });

        next.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                nextAction();
            }
        });
        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (mode == CHANGE_EMAIL || mode == CHANGE_NUMBER || mode == CHANGE_EMAILORNUMBER)
                    ShowProfile();

                else if (mode == REGISTER_USER)
                    ShowLogin();
                else {
                    	CloseWin();
                }

            }
        });


        edtotp4.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {

                nextAction();
                return false;
            }

        });

        if (mode == REGISTER_USER)
            btnBack.setVisibility(View.INVISIBLE);
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

        String otp = "";
        /*String mobileNumber = "";
        String email = "";*/

        otp = edtotp1.getText().toString() + edtotp2.getText().toString()
                + edtotp3.getText().toString() + edtotp4.getText().toString();
        if (otp.trim().isEmpty()) {

            showAlert("Please enter the otp");
        } else if (edtotp1.getText().toString().trim().length() == 0) {
            edtotp1.requestFocus();
            showAlert("Please enter a valid otp");
        } else if (edtotp2.getText().toString().trim().length() == 0) {
            edtotp2.requestFocus();
            showAlert("Please enter a valid otp");
        } else if (edtotp3.getText().toString().trim().length() == 0) {
            edtotp3.requestFocus();
            showAlert("Please enter a valid otp");
        } else if (edtotp4.getText().toString().trim().length() == 0) {
            edtotp4.requestFocus();
            showAlert("Please enter a valid otp");
        } else if (otp.trim().length() != 4) {

            showAlert("Please enter a valid otp");
        } else {
            if (!verifyInternetStatus()) {
                return;
            } else {


               /* TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
                String imei = telephonyManager.getDeviceId();
                VerifyOTP(null != imei ? imei : mobileNumber, email, otp);*/

                //VerifyOTP(appData.getMsisdn(), appData.getEmail(), otp);
                if (mode == FORGOT_PIN || mode == CHANGE_DEVICE_FORGOTPINFINAL) {
                    //  ForgotPINReset(appData.getUniqueId(), newMobileNumber, pin, otp);

                   /* Intent i = new Intent(getApplicationContext(), BMResetPin.class);
                    i.putExtra("Mode", FORGOT_PIN);
                    i.putExtra("Nid", nid);
                    i.putExtra("Number", newMobileNumber);
                    i.putExtra("Otp", otp);
                   CloseWin();
                    startActivity(i);*/

                    GenericValidateOtp(newMobileNumber, otp);

                } /*else if (mode == LOAD_SVA) {
                    ConfirmLoadSVA(otp);
                }*/ else if (mode == CHANGE_EMAILORNUMBER) {

                    String userNumber = appData.getUserData().mobileNumber;
                   /* TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
                    String imei = telephonyManager.getDeviceId();

                    if(userNumber.compareTo(newMobileNumber) != 0)
                    ChangeMSISDN(null != imei ? imei : userNumber, otp, newMobileNumber);
                    else
                        ChangeEmail(null != imei ? imei : userNumber, otp, newEmail);*/

                    if (userNumber.compareTo(newMobileNumber) != 0) {
                        if (otp.compareToIgnoreCase("1234") == 0) {
                            Intent i = new Intent(getApplicationContext(), BMEnterSecret.class);
                            i.putExtra("Mode", CHANGE_EMAILORNUMBER);
                            i.putExtra("NewNumber", newMobileNumber);
                            CloseWin();
                            startActivity(i);
                        } else {
                            showAlert("Invalid OTP");
                        }

                        /////wt ChangeMSISDN(newMobileNumber, otp, newMobileNumber);
                    } else
                        ChangeEmail(userNumber, otp, newEmail);
                } else if (mode == REGISTER_USER) {
                    VerifyOTP(user.mobileNumber, user.emailId, otp);
                } else if (mode == CHANGE_DEVICE || mode == CHANGE_DEVICE_FORGOTPIN) {
                    ValidateOTPChangeDevice(newMobileNumber, otp);
                } else
                    VerifyOTP(appData.getUserData().mobileNumber, appData.getUserData().emailId, otp);

					/*Intent i = new Intent(getApplicationContext(), BMSetPin.class);
                    startActivity(i);*/
            }
        }
    }

    private void ClearOTP() {
        edtotp1.setText("");
        edtotp2.setText("");
        edtotp3.setText("");
        edtotp4.setText("");
        edtotp1.requestFocus();

    }

    private void EnableOrDisableOTP(boolean enabled) {
        edtotp1.setEnabled(enabled);
        edtotp2.setEnabled(enabled);
        edtotp3.setEnabled(enabled);
        edtotp4.setEnabled(enabled);

        RelativeLayout lytotpbuttons = (RelativeLayout) findViewById(R.id.lytotpbuttons);
        TextView txtotpheader = (TextView) findViewById(R.id.txtotpheader);

        if (enabled) {
            lytotpbuttons.setVisibility(View.VISIBLE);
            txtotpheader.setText(getResources().getString(R.string.otpheader));
            edtotp1.requestFocus();
        } else {
            lytotpbuttons.setVisibility(View.GONE);
            txtotpheader.setText(getResources().getString(R.string.resendotpmessage));
            mobedtresendotp.requestFocus();
        }


    }


    private void VerifyOTP(String mobileNumber, String emailId, String otp) {
        if (!verifyInternetStatus()) {
            return;
        }
        showLoader();
		/*DbManager dbManager = DbManager.createAuto(BMLogin.this);
		dbManager.setImei(imei);
		dbManager.setMsisdn(msisdn);*/

        Wallet.Data.syncRead(getApplicationContext());

        try {

            //	final UUID resultId = apiManager.BMCustomerRegister("frstnm","lstnm","tem1p@gmail.com","91666666","05-10-2016","N","5454875458","458745","male","india","bleh576");

            final UUID resultId = apiManager.BMVerifyOTP(mobileNumber, emailId, otp);// "1234");//for demo otp);

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


                                ClearOTP();

                                /*Intent i = new Intent(getApplicationContext(), BMChangesMade.class);
                                if(mode ==CHANGE_EMAIL) {
                                    i = new Intent(getApplicationContext(), BMChangesMade.class);
                                    appData.setEmail(newEmail);
                                }
                                else if(mode ==CHANGE_NUMBER) {
                                    i = new Intent(getApplicationContext(), BMChangesMade.class);
                                    appData.setEmail(newEmail);
                                }
                                else if(mode ==REGISTER_USER)
                                    i = new Intent(getApplicationContext(), BMSetPin.class);
                                else if(mode ==FORGOT_PIN)
                                    i = new Intent(getApplicationContext(), BMChangePin.class);
                                CloseWin();
                                startActivity(i);*/

                                if (mode == REGISTER_USER) {
                                    //Intent i = new Intent(getApplicationContext(), BMSetPin.class);
                                   /* Intent i = new Intent(getApplicationContext(), BMSecurityQuestion.class);
                                    CloseWin();
                                    startActivity(i);*/


                                    CustomerRegister(user.firstName, user.lastName, user.emailId, user.mobileNumber, user.dob, "N", user.nid, "0", user.gender, "Oman", user.reference);


                                } else if (mode == CHANGE_NUMBER) {

                                    Intent i = new Intent(getApplicationContext(), BMEnterSecret.class);
                                    i.putExtra("Mode", CHANGE_NUMBER);
                                    i.putExtra("NewNumber", newMobileNumber);
                                    CloseWin();
                                    startActivity(i);
                                }
                               /* else if(mode == FORGOT_PIN) {

                                    Intent i = new Intent(getApplicationContext(), BMEnterSecret.class);
                                    i.putExtra("Mode", FORGOT_PIN);
                                    i.putExtra("NID", nid);

                                    CloseWin();
                                    startActivity(i);
                                } */


                                /* else   if(mode == LOAD_SVA) {

                                     LoadSVA();
                                 }*/
                                else
                                    ChangeProfileDetails();


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
                               /* hideLoader();
                                CLog.w(TAG, "in error : " + data.getError());
                                showAlert(data.getResponseData().txnMessage);*/


                               /* if(mode ==REGISTER_USER) {
                                    Intent i = new Intent(getApplicationContext(), BMSetPin.class);
                                    CloseWin();
                                    startActivity(i);
                                }

                               else if(mode == CHANGE_NUMBER) {

                                    Intent i = new Intent(getApplicationContext(), BMEnterSecret.class);
                                    i.putExtra("Mode", CHANGE_NUMBER);
                                    i.putExtra("NewNumber", newMobileNumber);
                                    CloseWin();
                                    startActivity(i);
                                }
                                else if(mode == FORGOT_PIN) {

                                    Intent i = new Intent(getApplicationContext(), BMResetPin.class);
                                    i.putExtra("Mode", FORGOT_PIN);
                                    i.putExtra("NID", nid);
                                    CloseWin();
                                    startActivity(i);
                                }else   if(mode == LOAD_SVA) {
                                    Intent i = new Intent(getApplicationContext(), BMLoadSVNResult.class);
                                    i.putExtra("Amount", nid);
                                    i.putExtra("TransactionID", nid);
                                    i.putExtra("Date", nid);
                                    CloseWin();
                                    startActivity(i);
                                }
                                else
                                    ChangeProfileDetails();


                               *//* Intent i = new Intent(getApplicationContext(), BMChangesMade.class);
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

                                //    EnableOrDisableOTP(false);
                                hideLoader();
                                ClearOTP();
                                CLog.w(TAG, "out error : " + data.getError());
                                showAlert(data.getResponseData().txnMessage);
                            }
                        } else {
                            //  EnableOrDisableOTP(false);
                            hideLoader();
                            ClearOTP();
                            CLog.w(TAG, "out error : " + data.getError());
                            showAlert(data.getResponseData().txnMessage);


                        }
                    } catch (Exception e) {

                        EnableOrDisableOTP(false);
                        hideLoader();
                        ClearOTP();
                        new AlertDialog.Builder(BMSubmitOTP.this)
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


    private void GenericValidateOtp(String msisdn, String otp) {
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

            final UUID resultId = apiManager.BMGenericValidateOtp(msisdn, otp);

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

                                Intent i = new Intent(getApplicationContext(), BMResetPin.class);
                                i.putExtra("Mode", FORGOT_PIN);
                                i.putExtra("Nid", nid);
                                i.putExtra("Number", newMobileNumber);
                                CloseWin();
                                startActivity(i);


                            } else {

                                // EnableOrDisableOTP(false);
                                hideLoader();
                                ClearOTP();
                                CLog.w(TAG, "in error : " + data.getError());
                                showAlert(data.getResponseData().txnMessage);


                            }
                        } else {
                            // EnableOrDisableOTP(false);
                            hideLoader();
                            ClearOTP();
                            CLog.w(TAG, "out error : " + data.getError());
                            showAlert(data.getResponseData().txnMessage);

                        }
                    } catch (Exception e) {

                        EnableOrDisableOTP(false);
                        hideLoader();
                        ClearOTP();
                        new AlertDialog.Builder(BMSubmitOTP.this)
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
            final UUID resultId = apiManager.BMChangeProfileDetails(data.image, data.uniqueId, newEmail, data.mobileNumber, data.dateOfBirth, data.firstName, data.firstName);

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
                                if (mode == CHANGE_EMAIL) {
                                    i = new Intent(getApplicationContext(), BMChangesMade.class);
                                    i.putExtra("Title", getResources().getString(R.string.changeemail));
                                    i.putExtra("SubtTitle", getResources().getString(R.string.changeemaildisclaimer));
                                    appData.setEmail(newEmail);
                                    appData.getUserData().emailId = newEmail;
                                } else if (mode == CHANGE_NUMBER) {
                                    i = new Intent(getApplicationContext(), BMChangesMade.class);
                                    appData.setMsisdn(newMobileNumber);
                                    appData.getUserData().mobileNumber = newMobileNumber;
                                    i.putExtra("Title", getResources().getString(R.string.changemobile));
                                    i.putExtra("SubtTitle", getResources().getString(R.string.changemobiledisclaimer));
                                } else if (mode == REGISTER_USER)
                                    i = new Intent(getApplicationContext(), BMSetPin.class);
                                else if (mode == FORGOT_PIN) {
                                    i = new Intent(getApplicationContext(), BMResetPin.class);
                                    i.putExtra("NID", nid);
                                }
                                CloseWin();
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
                                CLog.w(TAG, "in error : " + data.getError());
                                showAlert(data.getResponseData().txnMessage);

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
                            }
                        } else {
                            hideLoader();
                            CLog.w(TAG, "out error : " + data.getError());
                            showAlert(data.getResponseData().txnMessage);
                          /*  Intent i = new Intent(getApplicationContext(), BMResetPin.class);
                            startActivity(i);*/
                            //hideLoader();
                        }
                    } catch (Exception e) {

                        new AlertDialog.Builder(BMSubmitOTP.this)
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


    /*private void LoadSVA() {
        if (!verifyInternetStatus()) {
            return;
        }
        showLoader();
		*//*DbManager dbManager = DbManager.createAuto(BMLogin.this);
		dbManager.setImei(imei);
		dbManager.setMsisdn(msisdn);*//*

        Wallet.Data.syncRead(getApplicationContext());

        try {

            String[] separated = appData.getSelectedCardCheckout().getCardNumber().trim().split(" ");

            String card;
            if(separated.length > 1)
             card = separated[0]+separated[1]+separated[2]+separated[3];

            else
            card = appData.getSelectedCardCheckout().getCardNumber().trim();


            final UUID resultId = apiManager.BM_Load_SVA(card , amount);

            final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

                @Override
                public void handleMessage(APIManager.SOAPOperationData data) {
                    try {

                        if (data.getId() == resultId) {
                            apiManager.removeListener(this);
                            hideLoader();

                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy  hh:mm aa", Locale.getDefault());
                            String strDate = sdf.format(c.getTime());

                            if (!data.hasError()) {
                                CLog.i(TAG, "success");
                                System.out.println("Service Id............." + data.getServiceId());
                                appData.setServiceId(data.getServiceId());



                                Intent i = new Intent(getApplicationContext(), BMLoadSVNResult.class);
                                i.putExtra("Amount", amount);
                                i.putExtra("TransactionID", data.getTransactionId());//appData.getUserData().transactionId);
                                i.putExtra("Date", strDate);
                                CloseWin();
                                startActivity(i);


                            } else {
							*//*if ("225".equals(data.getError())) {

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
							}*//*
                                hideLoader();
                                CLog.w(TAG, "in error : " + data.getError());
                                showAlert(data.getResponseData().txnMessage);

                                //////bypass

                                Intent i = new Intent(getApplicationContext(), BMLoadSVNResult.class);
                                i.putExtra("Amount", amount);
                                i.putExtra("TransactionID", appData.getUserData().transactionId);
                                i.putExtra("Date", strDate);
                                CloseWin();
                                startActivity(i);

                                //////bypass


                            }
                        } else {
                            hideLoader();
                            CLog.w(TAG, "out error : " + data.getError());
                            showAlert(data.getResponseData().txnMessage);
                            *//*Intent i = new Intent(getApplicationContext(), BMLoadSVNResult.class);
                            i.putExtra("Amount", amount);
                            i.putExtra("TransactionID", appData.getUserData().transactionId);
                            i.putExtra("Date", "");
                            startActivity(i);*//*
                        }
                    } catch (Exception e) {

                        new AlertDialog.Builder(BMSubmitOTP.this)
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
    }*/


    /*private void ConfirmLoadSVA(String otp) {
        if (!verifyInternetStatus()) {
            return;
        }
        showLoader();
		*//*DbManager dbManager = DbManager.createAuto(BMLogin.this);
		dbManager.setImei(imei);
		dbManager.setMsisdn(msisdn);*//*

        Wallet.Data.syncRead(getApplicationContext());

        try {

            //  String otp = edtotp1.getText().toString().trim() + edtotp2.getText().toString().trim() + edtotp3.getText().toString().trim();

            *//*String[] separated = appData.getSelectedCardCheckout().getCardNumber().trim().split(" ");

            String card;
            if(separated.length > 1)
                card = separated[0]+separated[1]+separated[2]+separated[3];

            else
                card = appData.getSelectedCardCheckout().getCardNumber().trim();*//*


            final UUID resultId = apiManager.BM_LOAD_MONEY_CONFIRM(appData.getUserData().transactionId, appData.getUserData().mobileNumber, amount, otp);

            final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

                @Override
                public void handleMessage(APIManager.SOAPOperationData data) {
                    try {

                        if (data.getId() == resultId) {
                            apiManager.removeListener(this);
                            hideLoader();

                            Calendar c = Calendar.getInstance();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy  hh:mm aa", Locale.getDefault());
                            String strDate = sdf.format(c.getTime());

                            if (!data.hasError()) {
                                CLog.i(TAG, "success");
                                System.out.println("Service Id............." + data.getServiceId());
                                appData.setServiceId(data.getServiceId());


                                Intent i = new Intent(getApplicationContext(), BMLoadSVNResult.class);
                                i.putExtra("Amount", amount);
                                i.putExtra("TransactionID", data.getTransactionId());//appData.getUserData().transactionId);
                                i.putExtra("Date", strDate);
                                i.putExtra("IsSaved", isSaved);
                                CloseWin();
                                startActivity(i);


                            } else {
							*//*if ("225".equals(data.getError())) {

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
							}*//*
                                // EnableOrDisableOTP(false);
                                hideLoader();
                                ClearOTP();
                                CLog.w(TAG, "in error : " + data.getError());
                                showAlert(data.getResponseData().txnMessage);

                                //////bypass

                                *//*Intent i = new Intent(getApplicationContext(), BMLoadSVNResult.class);
                                i.putExtra("Amount", amount);
                                i.putExtra("TransactionID", appData.getUserData().transactionId);
                                i.putExtra("Date", strDate);
                                CloseWin();
                                startActivity(i);*//*

                                //////bypass


                            }
                        } else {
                            // EnableOrDisableOTP(false);
                            hideLoader();
                            ClearOTP();
                            CLog.w(TAG, "out error : " + data.getError());
                            showAlert(data.getResponseData().txnMessage);
                            *//*Intent i = new Intent(getApplicationContext(), BMLoadSVNResult.class);
                            i.putExtra("Amount", amount);
                            i.putExtra("TransactionID", appData.getUserData().transactionId);
                            i.putExtra("Date", "");
                            startActivity(i);*//*
                        }
                    } catch (Exception e) {
                        EnableOrDisableOTP(false);
                        hideLoader();
                        ClearOTP();
                        new AlertDialog.Builder(BMSubmitOTP.this)
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
*/


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        hideSoftKeyboard();
        return false;
    }

    void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtotp1.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
      /*  hideSoftKeyboard();
        NavUtils.navigateUpTo(BMSubmitOTP.this, NavUtils.getParentActivityIntent(BMSubmitOTP.this));*/

        CloseWin();
    }

    // boolean otpGenerated = false;

    private void GenerateOtp(String mobileNumber, final String emailId, final String uniqueId) {
        if (!verifyInternetStatus()) {
            return;
        }
        showLoader();
		/*DbManager dbManager = DbManager.createAuto(BMLogin.this);
		dbManager.setImei(imei);
		dbManager.setMsisdn(msisdn);*/

        Wallet.Data.syncRead(getApplicationContext());

        try {

            final UUID resultId = apiManager.BMGenerateOtp(mobileNumber, emailId, uniqueId);

            final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

                @Override
                public void handleMessage(APIManager.SOAPOperationData data) {
                    try {

                        if (data.getId() == resultId) {
                            apiManager.removeListener(this);
                            hideLoader();

                            if (!data.hasError()) {
                                CLog.i(TAG, "Generate Otp success");

                                appData.setServiceId(data.getServiceId());    //abc Resetting service id
                                hideLoader();
                              //  otpGenerated = true;
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

                        new AlertDialog.Builder(BMSubmitOTP.this)
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





    /*private void ForgotPINReset(String uniqueId, String msisdn, String pin, String otp) {
        if (!verifyInternetStatus()) {
            return;
        }
        showLoader();
		*//*DbManager dbManager = DbManager.createAuto(BMLogin.this);
		dbManager.setImei(imei);
		dbManager.setMsisdn(msisdn);*//*

        Wallet.Data.syncRead(getApplicationContext());

        try {

            //	final UUID resultId = apiManager.BMCustomerRegister("frstnm","lstnm","tem1p@gmail.com","91666666","05-10-2016","N","5454875458","458745","male","india","bleh576");

            final UUID resultId = apiManager.BMForgotPINReset( uniqueId,  msisdn,  pin,  otp);

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

                                *//*Intent i = new Intent(getApplicationContext(), BMChangesMade.class);
                                if(mode ==CHANGE_EMAIL) {
                                    i = new Intent(getApplicationContext(), BMChangesMade.class);
                                    appData.setEmail(newEmail);
                                }
                                else if(mode ==CHANGE_NUMBER) {
                                    i = new Intent(getApplicationContext(), BMChangesMade.class);
                                    appData.setEmail(newEmail);
                                }
                                else if(mode ==REGISTER_USER)
                                    i = new Intent(getApplicationContext(), BMSetPin.class);
                                else if(mode ==FORGOT_PIN)
                                    i = new Intent(getApplicationContext(), BMChangePin.class);
                                CloseWin();
                                startActivity(i);*//*

                                if(mode ==REGISTER_USER) {
                                    Intent i = new Intent(getApplicationContext(), BMSetPin.class);
                                    CloseWin();
                                    startActivity(i);
                                }
                                else  if(mode == CHANGE_NUMBER) {

                                    Intent i = new Intent(getApplicationContext(), BMEnterSecret.class);
                                    i.putExtra("Mode", CHANGE_NUMBER);
                                    i.putExtra("NewNumber", newMobileNumber);
                                    CloseWin();
                                    startActivity(i);
                                }
                                else if(mode == FORGOT_PIN) {

                                    Intent i = new Intent(getApplicationContext(), BMResetPin.class);
                                    i.putExtra("Mode", FORGOT_PIN);
                                    i.putExtra("NID", nid);
                                    CloseWin();
                                    startActivity(i);
                                } else   if(mode == LOAD_SVA) {

                                    LoadSVA();
                                }
                                else
                                    ChangeProfileDetails();



                            } else {
							*//*if ("225".equals(data.getError())) {

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
							}*//*
                               *//* hideLoader();
                                CLog.w(TAG, "in error : " + data.getError());
                                showAlert(data.getResponseData().txnMessage);*//*


                                if(mode ==REGISTER_USER) {
                                    Intent i = new Intent(getApplicationContext(), BMSetPin.class);
                                    CloseWin();
                                    startActivity(i);
                                }

                                else if(mode == CHANGE_NUMBER) {

                                    Intent i = new Intent(getApplicationContext(), BMEnterSecret.class);
                                    i.putExtra("Mode", CHANGE_NUMBER);
                                    i.putExtra("NewNumber", newMobileNumber);
                                    CloseWin();
                                    startActivity(i);
                                }
                                else if(mode == FORGOT_PIN) {

                                    Intent i = new Intent(getApplicationContext(), BMResetPin.class);
                                    i.putExtra("Mode", FORGOT_PIN);
                                    i.putExtra("NID", nid);
                                    CloseWin();
                                    startActivity(i);
                                }else   if(mode == LOAD_SVA) {
                                    Intent i = new Intent(getApplicationContext(), BMLoadSVNResult.class);
                                    i.putExtra("Amount", nid);
                                    i.putExtra("TransactionID", nid);
                                    i.putExtra("Date", nid);
                                    CloseWin();
                                    startActivity(i);
                                }
                                else
                                    ChangeProfileDetails();


                               *//* Intent i = new Intent(getApplicationContext(), BMChangesMade.class);
                                 if(mode ==CHANGE_EMAIL) {
                                     i = new Intent(getApplicationContext(), BMChangesMade.class);
                                     appData.setEmail(newEmail);
                                 }
                                else if(mode ==REGISTER_USER)
                                 i = new Intent(getApplicationContext(), BMSetPin.class);
                                else if(mode ==FORGOT_PIN)
                                    i = new Intent(getApplicationContext(), BMChangePin.class);
                               CloseWin();
                                startActivity(i);*//*
                            }
                        } else {
                            hideLoader();
                            CLog.w(TAG, "out error : " + data.getError());
                            showAlert(data.getResponseData().txnMessage);
                            *//*Intent i = new Intent(getApplicationContext(), BMSetPin.class);
                            startActivity(i);*//*
                            hideLoader();
                        }
                    } catch (Exception e) {

                        new AlertDialog.Builder(BMSubmitOTP.this)
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
    }*/


    private void ChangeMSISDN(String msisdn, String otp, String mobileNumber) {
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

                                appData.setMsisdn(newMobileNumber);
                                appData.getUserData().mobileNumber = newMobileNumber;
                                i.putExtra("Title", getResources().getString(R.string.changemobile));
                                i.putExtra("SubtTitle", getResources().getString(R.string.changemobiledisclaimer));

                                CloseWin();
                                startActivity(i);


                            } else {

                                // EnableOrDisableOTP(false);
                                hideLoader();
                                ClearOTP();
                                CLog.w(TAG, "in error : " + data.getError());
                                showAlert(data.getResponseData().txnMessage);

                            }
                        } else {
                            // EnableOrDisableOTP(false);
                            hideLoader();
                            ClearOTP();
                            CLog.w(TAG, "out error : " + data.getError());
                            showAlert(data.getResponseData().txnMessage);

                        }
                    } catch (Exception e) {
                        EnableOrDisableOTP(false);
                        hideLoader();
                        ClearOTP();
                        new AlertDialog.Builder(BMSubmitOTP.this)
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


    private void ChangeEmail(String msisdn, String otp, String email) {
        if (!verifyInternetStatus()) {
            return;
        }
        showLoader();
		/*DbManager dbManager = DbManager.createAuto(BMLogin.this);
		dbManager.setImei(imei);
		dbManager.setMsisdn(msisdn);*/

        Wallet.Data.syncRead(getApplicationContext());

        try {

            final UUID resultId = apiManager.BMChangeEmail(appData.getUserData().uniqueId, msisdn, otp, email);

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

                                i.putExtra("Title", getResources().getString(R.string.changeemail));
                                i.putExtra("SubtTitle", getResources().getString(R.string.changeemaildisclaimer));
                                appData.setEmail(newEmail);
                                appData.getUserData().emailId = newEmail;

                                CloseWin();
                                startActivity(i);


                            } else {

                                EnableOrDisableOTP(false);
                                hideLoader();
                                ClearOTP();
                                CLog.w(TAG, "in error : " + data.getError());
                                showAlert(data.getResponseData().txnMessage);

                            }
                        } else {
                            EnableOrDisableOTP(false);
                            hideLoader();
                            ClearOTP();
                            CLog.w(TAG, "out error : " + data.getError());
                            showAlert(data.getResponseData().txnMessage);

                        }
                    } catch (Exception e) {
                        EnableOrDisableOTP(false);
                        hideLoader();
                        ClearOTP();
                        new AlertDialog.Builder(BMSubmitOTP.this)
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


    private boolean
    CustomerRegister(final String firstname, String lastname, final String emailId, final String mobileNumber, final String dob, String kyc, String nid, String idNumber, String gender, String country, String reference) {
        if (!verifyInternetStatus()) {
            return false;
        }
        showLoader();
		/*DbManager dbManager = DbManager.createAuto(BMLogin.this);
		dbManager.setImei(imei);
		dbManager.setMsisdn(msisdn);*/

        Wallet.Data.syncRead(getApplicationContext());

        try {

            //	final UUID resultId = apiManager.BMCustomerRegister("frstnm","lstnm","tem1p@gmail.com","91666666","05-10-2016","N","5454875458","458745","male","india","bleh576");

            final UUID resultId = apiManager.BMCustomerRegister(firstname, lastname, emailId, mobileNumber, dob, kyc, nid, idNumber, gender, country, reference);

            final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

                @Override
                public void handleMessage(APIManager.SOAPOperationData data) {
                    try {

                        if (data.getId() == resultId) {
                            apiManager.removeListener(this);
                            hideLoader();

                            if (!data.hasError()) {
                                CLog.i(TAG, "Customer registration success");
                                System.out.println("Service Id............." + data.getServiceId());
                                //  status = true;
                                appData.setServiceId(data.getServiceId());
                                appData.setUniqueId(data.getUniqueId());
                                appData.setEmail(emailId);
                                appData.setMsisdn(mobileNumber);
                                appData.setUserName(firstname);

                                appData.setUserData(data.getResponseData());///store profile?

                                appData.getUserData().mobileNumber = mobileNumber;
                                appData.getUserData().firstName = firstname;
                                appData.getUserData().emailId = emailId;
                                appData.getUserData().dateOfBirth = dob;
                                appData.getUserData().image = "";

                                // GenerateOtp(mobileNumber, emailId, appData.getUniqueId());

                               /* Intent i = new Intent(getApplicationContext(), BMSubmitOTP.class);
                                i.putExtra("Mode", REGISTER_USER);
                                i.putExtra("Title", getResources().getString(R.string.submitotp));*/
                                // i.putExtra("Number", mobileNumber);
                                //  i.putExtra("Email", emailId);



                                /*Intent i = new Intent(getApplicationContext(), BMSecurityQuestion.class);
                                i.putExtra("Mode", REGISTER_USER);
                                i.putExtra("Title", getResources().getString(R.string.submitotp));*/

                                Intent i = new Intent(getApplicationContext(), BMSetPin.class);


                                CloseWin();
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
                                //  status = false;
                                showAlert(data.getResponseData().txnMessage);
                            }
                        } else {
                            hideLoader();
                            //  status = false;
                            CLog.w(TAG, "out error : " + data.getError());
                            showAlert(data.getResponseData().txnMessage);
                        }
                    } catch (Exception e) {

                        // status = false;

                        new AlertDialog.Builder(BMSubmitOTP.this)
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
        return true;
    }


    private void ValidateOTPChangeDevice(String msisdn, String otp) {
        if (!verifyInternetStatus()) {
            return;
        }
        showLoader();
		/*DbManager dbManager = DbManager.createAuto(BMLogin.this);
		dbManager.setImei(imei);
		dbManager.setMsisdn(msisdn);*/

        Wallet.Data.syncRead(getApplicationContext());

        try {


            final UUID resultId = apiManager.BM_ValidateOTPChangeDevice(msisdn, otp);

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
                                appData.setUserData(data.getResponseData());

                                Intent i = null;
                                if (mode == CHANGE_DEVICE) {
                                    i = new Intent(getApplicationContext(), BMEnterSecret.class);
                                    i.putExtra("Mode", CHANGE_DEVICE);
                                    i.putExtra("Number", newMobileNumber);
                                    i.putExtra("Pin", pin);
                                    // i.putExtra("Question", appData.getUserData().securityQuestion);
                                } else if (mode == CHANGE_DEVICE_FORGOTPIN) {

                                    i = new Intent(getApplicationContext(), BMEnterSecret.class);
                                    i.putExtra("Mode", CHANGE_DEVICE_FORGOTPIN);
                                    i.putExtra("Nid", nid);
                                    i.putExtra("Number", newMobileNumber);


                                }


                                if (i != null) {
                                    CloseWin();
                                    startActivity(i);
                                }


                            } else {

                                hideLoader();
                                ClearOTP();
                                CLog.w(TAG, "in error : " + data.getError());
                                showAlert(data.getResponseData().txnMessage);


                            }
                        } else {
                            // EnableOrDisableOTP(false);
                            hideLoader();
                            ClearOTP();
                            CLog.w(TAG, "out error : " + data.getError());
                            showAlert(data.getResponseData().txnMessage);

                        }
                    } catch (Exception e) {
                        EnableOrDisableOTP(false);
                        hideLoader();
                        ClearOTP();
                        new AlertDialog.Builder(BMSubmitOTP.this)
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


    private boolean PerformDedupe(final String emailId, final String mobileNumber) {

        if (!verifyInternetStatus()) {
            return false;
        }
        showLoader();

        try {

            final UUID resultId = apiManager.BMPerformDedupe(emailId, mobileNumber);

            final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

                @Override
                public void handleMessage(APIManager.SOAPOperationData data) {
                    try {

                        if (data.getId() == resultId) {
                            apiManager.removeListener(this);


                            if (!data.hasError()) {
                                CLog.i(TAG, "Perform Dedupe success");
                                System.out.println("Service Id............." + data.getServiceId());

                                hideLoader();
                            } else {

                                showAlert("Please try again");

                            }
                        } else {
                            showAlert("Please try again");
                        }
                    } catch (Exception e) {


                        new AlertDialog.Builder(BMSubmitOTP.this)
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

        return true;
    }

}
