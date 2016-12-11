package app.banking.bankmuscat.merchant.Screens;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;

import app.banking.bankmuscat.R;
import app.banking.bankmuscat.merchant.api.APIManager;
import app.banking.bankmuscat.merchant.base.ActivityBase;
import app.banking.bankmuscat.merchant.common.Wallet;
import app.banking.bankmuscat.merchant.components.widgets.Utils;
import app.banking.bankmuscat.merchant.entity.instrument.User;
import app.banking.bankmuscat.merchant.interfaces.ILoader;
import app.banking.bankmuscat.merchant.logger.CLog;
import app.banking.bankmuscat.merchant.util.CommonUtils;
import app.banking.bankmuscat.merchant.util.constant.ErrorAndPopupCodes;


/*import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;*/

//import com.google.i18n.phonenumbers.NumberParseException;
//import com.google.i18n.phonenumbers.PhoneNumberUtil;
//import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
//import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class BMRegisterUser extends ActivityBase implements ILoader {

    private static final String TAG = "BMRegisterUser";
    private final APIManager apiManager = APIManager.createInstance(null);
    private Wallet.Data appData = Wallet.Data.getInstance();
    private static final int RC_SIGN_IN = 007;
    public ImageView btnBack, header_home, header_menu_button, imgcalendar, imginfo;
    TextView headingTextView;
    public EditText edtFullName, edtNID, edtMobileNumber, edtDOB, edtEmail, edtRefCode;
    Button next, cancel;
    RadioGroup radgender;
    RadioButton radmale,radfemale;

    @Override
    public int GetScreenLayout() {
        return R.layout.bm_register_user;
    }

    @Override
    public boolean isHeaderNeeded() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        btnBack = (ImageView) findViewById(R.id.header_back_button);
        next = (Button) findViewById(R.id.mob_buttonnext);
        cancel = (Button) findViewById(R.id.mob_buttoncancel);

        edtFullName = (EditText) findViewById(R.id.mobedtFullname);
        edtNID = (EditText) findViewById(R.id.mobedtnid);
        edtMobileNumber = (EditText) findViewById(R.id.mobedtMobileNumber);
        edtDOB = (EditText) findViewById(R.id.mobedtdob);
        edtEmail = (EditText) findViewById(R.id.mobedtemail);
        CommonUtils.ReplaceCharacter(edtEmail, " ", "");
        edtRefCode = (EditText) findViewById(R.id.mobedtref);
        radgender = (RadioGroup) findViewById(R.id.radgender);
        imgcalendar = (ImageView) findViewById(R.id.imgcalendar);
        imginfo = (ImageView) findViewById(R.id.imginfo);
        header_home = (ImageView) findViewById(R.id.header_home);
        header_menu_button = (ImageView) findViewById(R.id.header_menu_button);
        radmale=(RadioButton) findViewById(R.id.radmale);
        radfemale=(RadioButton) findViewById(R.id.radfemale);
        headingTextView = (TextView) findViewById(R.id.title_header);
        headingTextView.setText(getResources().getString(R.string.register_button_text));
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "app.banking.bankmuscat",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());/*
                System.out.println("KeyHash: "+Base64.encodeToString(md.digest(), Base64.DEFAULT));
                showAlert("KeyHash: "+Base64.encodeToString(md.digest(), Base64.DEFAULT));*/
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        edtFullName.requestFocus();
        getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        imginfo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                showInfo(getResources().getString(R.string.dobinfo));

            }
        });

        imgcalendar.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                ShowDatePicker();

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
                CloseWin();
            }
        });

        btnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                /*hideSoftKeyboard();
                NavUtils.navigateUpTo(BMRegisterUser.this, NavUtils
                        .getParentActivityIntent(BMRegisterUser.this));*/

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

    void ShowDatePicker() {
        //   DatePickerDialog dialog =  new DatePickerDialog(this);
        DatePickerDialog toDatePickerDialog;


        //mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());

        final SimpleDateFormat dateFormatter;
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                edtDOB.setText(dateFormatter.format(newDate.getTime()));
            }

        }, newCalendar.get(Calendar.YEAR ), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        long EIGHTEEN_YEARS_MILLISECOND = 1 * 24 * 60 * 60 * 1000L *365 * 18;
        long LEAP_MILLISECOND_ADJUSTMENT =  5 * 24 * 60 * 60 * 1000L ;
        long EIGHTEEN_YEARS_MILLISECOND_ADJUSTED =EIGHTEEN_YEARS_MILLISECOND + LEAP_MILLISECOND_ADJUSTMENT;
      //  long EIGHTEEN_YEARS_MILLISECOND = 1000 * 60 * 60 * 24 * 365 * 18;
       // long EIGHTEEN_YEARS_MILLISECOND_ADJUSTED = EIGHTEEN_YEARS_MILLISECOND - 1000 * 60 * 60 * 24 * 4 ;
        fromDatePickerDialog.show();
        fromDatePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis()- EIGHTEEN_YEARS_MILLISECOND_ADJUSTED);



    }


    void nextAction() {
        String fullName = edtFullName.getText().toString().trim();
        String mobileNumber = edtMobileNumber.getText().toString().trim();
        String nid = edtNID.getText().toString().trim();
        String dob = edtDOB.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String gender = "male";
        int genderid = radgender.getCheckedRadioButtonId();
        switch (genderid) {
            case R.id.radmale:
                gender = "male";
                break;

            case R.id.radfemale:
                gender = "female";
                break;
        }

        String reference = edtRefCode.getText().toString().trim();
        if (edtRefCode.getText().toString().trim().isEmpty()) {

            reference = "Nil";
        }

      //  long mobileNumToLong = 0;


      //  mobileNumToLong = Long.parseLong(mobileNumber);
     //   int length = String.valueOf(mobileNumToLong).length();

        if (edtFullName.getText().toString().trim().isEmpty()) {

            showAlert("Please enter your full name");
            edtFullName.requestFocus();
        } else if (fullName.isEmpty()) {
            showAlert(ErrorAndPopupCodes.Invalid_Name.getTag());

        }else if (edtNID.getText().toString().trim().isEmpty()) {
            edtNID.requestFocus();
            showAlert("Please enter your NID");
        } else if (edtNID.getText().toString().trim().length() < 3) {
            edtNID.requestFocus();
            showAlert("Please enter a valid NID");
        } else if (nid.isEmpty()) {
            showAlert(ErrorAndPopupCodes.Invalid_Nid.getTag());

        }else if (mobileNumber.isEmpty()) {
            edtMobileNumber.requestFocus();
            showAlert("Please enter your 8 digit mobile number");
        } else if (mobileNumber.substring(0, 1).equalsIgnoreCase("0") || mobileNumber.substring(0, 1).equalsIgnoreCase("2")) {//else if (!mobileNumber.substring(0, 1).equalsIgnoreCase("9") && !mobileNumber.substring(0, 1).equalsIgnoreCase("7")) {
            edtMobileNumber.requestFocus();
            showAlert("Mobile number cannot be started with 0 and 2");
        }
        else if (mobileNumber.length() != 8) {
            showAlert("Please enter your 8 digit mobile number");

        }else if (edtDOB.getText().toString().trim().isEmpty()) {
            edtDOB.requestFocus();
            showAlert("Please enter your date of birth");
        } else if (!Utils.checkDobPattern(dob)) {
            edtDOB.requestFocus();
            showAlert(ErrorAndPopupCodes.Invalid_Dob.getTag());
        } else if (edtEmail.getText().toString().trim().length() > 50) {
            edtEmail.requestFocus();
            showAlert("Please enter a valid Email ID");
        } else if (!Utils.checkEmailPattern(email)) {
            edtEmail.requestFocus();
            showAlert(ErrorAndPopupCodes.Invalid_Email.getTag());
        }else if (radgender.getCheckedRadioButtonId() == -1) {
            radgender.requestFocus();
            showAlert("Please select your gender");
        } else {



                if (!verifyInternetStatus()) {
                    return;
                } else {

                    PerformDedupe(fullName, fullName, email, mobileNumber, dob, "N", nid, "0", gender, "Oman", reference);

                    //   CustomerRegister(fullName, fullName, email, mobileNumber, dob, "N", nid, "0", gender, "Oman", reference);


                    /*if(status) {
                        if(CustomerRegister(fullName, fullName, email, mobileNumber, dob, "N", nid, "0", gender, "Oman", reference))
                            GenerateOtp(mobileNumber,email, appData.getUniqueId());
                    }*/

                    /*if(status) {
                        CustomerRegister(fullName, fullName, email, mobileNumber, dob, "N", nid, "0", gender, "Oman", reference);

                    }*/

					/*Intent i = new Intent(getApplicationContext(), BMSubmitOTP.class);
                    startActivity(i);*/
                }
            }

    }

    boolean status = false;

    private boolean PerformDedupe(final String firstname, final String lastname, final String emailId, final String mobileNumber, final String dob, final String kyc, final String nid, final String idNumber, final String gender, final String country, final String reference) {

        if (!verifyInternetStatus()) {
            return false;
        }
        showLoader();
        /*DbManager dbManager = DbManager.createAuto(BMLogin.this);
        dbManager.setImei(imei);
		dbManager.setMsisdn(msisdn);*/

        Wallet.Data.syncRead(getApplicationContext());

        try {


            final UUID resultId = apiManager.BMPerformDedupe(emailId, mobileNumber);

            final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

                @Override
                public void handleMessage(APIManager.SOAPOperationData data) {
                    try {

                        if (data.getId() == resultId) {
                            apiManager.removeListener(this);
                            hideLoader();

                            if (!data.hasError()) {
                                CLog.i(TAG, "Perform Dedupe success");
                                System.out.println("Service Id............." + data.getServiceId());
                                //appData.setServiceId(data.getServiceId());
                                // appData.setUniqueId(data.getUniqueId());
                                // appData.setEmail(emailId);
                                // appData.setMsisdn(mobileNumber);
                                status = true;

                                User user =  new User();
                                user.firstName = firstname;
                                user.lastName = lastname;
                                user.mobileNumber = mobileNumber;
                                user.emailId = emailId;
                                user.dob = dob;
                                user.nid = nid;
                                user.gender = gender;
                                user.reference = reference;

                                appData.setUser(user);

                                Intent i = new Intent(getApplicationContext(), BMSubmitOTP.class);
                                i.putExtra("Mode", REGISTER_USER);
                                i.putExtra("Title", getResources().getString(R.string.submitotp));

                                CloseWin();
                                startActivity(i);
                               // CustomerRegister(firstname, lastname, emailId, mobileNumber, dob, "N", nid, "0", gender, "Oman", reference);

                                hideLoader();
                            } else if ("225".equals(data.getError())) {

                               /* //	DbManager dbManager = DbManager.createAuto(BMLogin.this);

                              //  String uniqueId = (String) data.getResponse().getProperty("uniqueId").toString().trim();
                                appData.setServiceId(data.getServiceId());
                               // appData.setValidateMsisdn(true);
                                System.out.println("SERVICE ID......." + data.getServiceId());
                              //  appData.setUniqueId(uniqueId);
                              //  CLog.i(TAG, "UniqueID  success:" + uniqueId);
                                //dbManager.setUniqueId(uniqueId);
                                Intent i = new Intent(getApplicationContext(),
                                        BMSetPin.class);
                                //i.putExtra("Registration", "Registration");
                                CloseWin();
                                startActivity(i);
                                hideLoader();*/

                                //---------

                                User user =  new User();
                                user.firstName = firstname;
                                user.lastName = lastname;
                                user.mobileNumber = mobileNumber;
                                user.emailId = emailId;
                                user.dob = dob;
                                user.nid = nid;
                                user.gender = gender;
                                user.reference = reference;

                                appData.setUser(user);

                                Intent i = new Intent(getApplicationContext(), BMSubmitOTP.class);
                                i.putExtra("Mode", REGISTER_USER);
                                i.putExtra("Title", getResources().getString(R.string.submitotp));

                                CloseWin();
                                startActivity(i);

                               //---------

                            }

                            else if ("326".equals(data.getError())) {

                                //	DbManager dbManager = DbManager.createAuto(BMLogin.this);
                                showAlert(data.getResponseData().txnMessage);
                                hideLoader();
                            }

                            else if ("252".equals(data.getError())) {

                                //	DbManager dbManager = DbManager.createAuto(BMLogin.this);
                                showAlert(data.getResponseData().txnMessage);
                                hideLoader();
                            }

                            /*else if ("260".equals(data.getError())) {

								Intent i = new Intent(getApplicationContext(),
										RegistrationPin.class);
								startActivity(i);
								hideLoader();
							}*/

                            else {
                                /*status = false;
								hideLoader();
								CLog.w(TAG, "in error : " + data.getError());
								*/

                                showAlert(data.getResponseData().txnMessage);


                                //   status = true;
                                //  CustomerRegister(firstname, lastname, emailId, mobileNumber, dob, "N", nid, "0", gender, "Oman", reference);

                            }
                        } else {
                           /* status = false;
                            hideLoader();
                            CLog.w(TAG, "out error : " + data.getError());
                            showAlert(data.getResponseData().txnMessage);*/


                            status = true;
                            CustomerRegister(firstname, lastname, emailId, mobileNumber, dob, "N", nid, "0", gender, "Oman", reference);


                        }
                    } catch (Exception e) {

                        status = false;

                        new AlertDialog.Builder(BMRegisterUser.this)
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

        return status;
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
                                status = true;
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

                                Intent i = new Intent(getApplicationContext(), BMSubmitOTP.class);
                                i.putExtra("Mode", REGISTER_USER);
                                i.putExtra("Title", getResources().getString(R.string.submitotp));
                                // i.putExtra("Number", mobileNumber);
                                //  i.putExtra("Email", emailId);
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
                                status = false;
                                showAlert(data.getResponseData().txnMessage);
                            }
                        } else {
                            hideLoader();
                            status = false;
                            CLog.w(TAG, "out error : " + data.getError());
                            showAlert(data.getResponseData().txnMessage);
                        }
                    } catch (Exception e) {

                        status = false;

                        new AlertDialog.Builder(BMRegisterUser.this)
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
        return status;
    }


    /*private void GenerateOtp(String mobileNumber, final String emailId, final String uniqueId) {
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
                                System.out.println("Service Id............." + data.getServiceId());
                                *//*appData.setServiceId(data.getServiceId());
                                appData.setUniqueId(data.getUniqueId());
                                appData.setEmail(emailId);
                                appData.setMsisdn(mobileNumber);*//*


                                Intent i = new Intent(getApplicationContext(), BMSubmitOTP.class);
                                i.putExtra("Mode", REGISTER_USER);
                                //  i.putExtra("Number", mobileNumber);
                                //  i.putExtra("Email", emailId);

                                startActivity(i);
                                hideLoader();
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
                                showAlert(data.getResponseData().txnMessage);
                            }
                        } else {
                            hideLoader();
                            CLog.w(TAG, "out error : " + data.getError());
                            showAlert(data.getResponseData().txnMessage);
                        }
                    } catch (Exception e) {

                        new AlertDialog.Builder(BMRegisterUser.this)
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
    }*/



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        hideSoftKeyboard();
        return false;
    }

    void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edtMobileNumber.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
      /*  hideSoftKeyboard();
        NavUtils.navigateUpTo(BMRegisterUser.this, NavUtils.getParentActivityIntent(BMRegisterUser.this));*/

        CloseWin();
    }





}
