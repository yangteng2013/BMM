package app.banking.bankmuscat.merchant.Screens;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
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
//import com.google.i18n.phonenumbeTypers.PhoneNumberUtil;
//import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
//import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class BMEnterPin extends ActivityBase implements ILoader {


    private static final String TAG = "BMEnterPin";
    private final APIManager apiManager = APIManager.createInstance(null);
    private Wallet.Data appData = Wallet.Data.getInstance();

    public ImageView btnBack, header_home, header_menu_button;
    TextView headingTextView;
    public EditText edtpin1, edtpin2, edtpin3, edtpin4;
    Button next, cancel, mobbtnlogout;

    int mode = NO_TYPE;
    String newMobileNumber = "";
    String newEmail = "";
    String nid = "";

    int position = 0;

    String amount,mobile,mobile2,acctno,bankid, description;

    @Override
    public int GetScreenLayout() {
        return R.layout.bm_enter_pin;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mode = getIntent().getExtras().getInt("Mode");

        btnBack = (ImageView) findViewById(R.id.header_back_button);
        RelativeLayout mob_logout = (RelativeLayout) findViewById(R.id.mob_logout);
        mob_logout.setVisibility(View.INVISIBLE);

        if (mode == USER_INACTIVITY || mode == USER_RESTART) {
            if (apiManager != null)
                apiManager.setCheckingPinStatus(true);
            RemoveHeaderButtons();
            btnBack.setVisibility(View.INVISIBLE);
            mob_logout.setVisibility(View.VISIBLE);
        }

        if (mode == CHANGE_NUMBER)
            newMobileNumber = getIntent().getExtras().getString("NewNumber");
        else if (mode == CHANGE_EMAIL)
            newEmail = getIntent().getExtras().getString("NewEmail");

        else if (mode == FORGOT_PIN)
            nid = getIntent().getExtras().getString("Nid");
        else if (mode == SEND_MONEY) {
            amount = getIntent().getExtras().getString("amount");
            mobile = getIntent().getExtras().getString("mobile");
        }
        else if (mode == SEND_BANK) {
            amount = getIntent().getExtras().getString("amount");
            mobile = getIntent().getExtras().getString("mobile");
            mobile2 = getIntent().getExtras().getString("mobile2");
            acctno = getIntent().getExtras().getString("acctno");
            bankid = getIntent().getExtras().getString("bankid");
        }
        else if (mode == COLLECT_PAYMENT) {
            amount = getIntent().getExtras().getString("Amount");
        }
        else if (mode == DELETE_CARD) {
            position = getIntent().getExtras().getInt("Position");
            amount = getIntent().getExtras().getString("Amount");
            description = getIntent().getExtras().getString("Description");

        } else if (mode == DELETE_ACCOUNT)
            position = getIntent().getExtras().getInt("Position");

        else if (mode == CHANGE_EMAILORNUMBER) {
            newMobileNumber = getIntent().getExtras().getString("Number");

            newEmail = getIntent().getExtras().getString("Email");
        }

        //setContentView(R.layout.bm_enter_pin);

        mobbtnlogout = (Button) findViewById(R.id.mobbtnlogout);
        cancel = (Button) findViewById(R.id.mob_buttoncancel);
        next = (Button) findViewById(R.id.mob_buttonnext);
        edtpin1 = (EditText) findViewById(R.id.edtpin1);
        edtpin2 = (EditText) findViewById(R.id.edtpin2);
        edtpin3 = (EditText) findViewById(R.id.edtpin3);
        edtpin4 = (EditText) findViewById(R.id.edtpin4);


        setTextFocus(edtpin1, edtpin2);
        setTextFocus(edtpin2, edtpin3);
        setTextFocus(edtpin3, edtpin4);

        setDeleteFocus(edtpin4, edtpin3);
        setDeleteFocus(edtpin3, edtpin2);
        setDeleteFocus(edtpin2, edtpin1);


        header_home = (ImageView) findViewById(R.id.header_home);
        header_menu_button = (ImageView) findViewById(R.id.header_menu_button);

        headingTextView = (TextView) findViewById(R.id.title_header);
        headingTextView.setText(getResources().getString(R.string.enter_pin));

        edtpin1.requestFocus();
        getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mobbtnlogout.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Logout();
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

                else if (mode == UNLOAD_SVA)
                    ShowHome();//ShowTransferToBankList();

                else if (mode == USER_INACTIVITY || mode == USER_RESTART)
                    Logout();

                else if (mode == REQUEST_ACCEPT)
                    ShowHome();


                else if (mode == SEND_BANK)
                    ShowHome();

                else
                    CloseWin();

            }
        });


        edtpin4.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {

                nextAction();

                return false;
            }

        });


        btnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                /*hideSoftKeyboard();
                NavUtils.navigateUpTo(BMEnterPin.this, NavUtils
						.getParentActivityIntent(BMEnterPin.this));*/

                CloseWin();

            }

        });

    }


    void CloseWin() {

        this.finish();
    }

    String pin;

    void SendMoney(String pin){


        if (!verifyInternetStatus()) {
            return;
        }
        showLoader();

        try {
            System.out.println("ENTER PIN SENDING   ::::: "+appData.getMsisdn()+":::::"+mobile);
            final UUID resultId = apiManager.BMC2C(amount,appData.getMsisdn(),mobile,pin);

            final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

                @Override
                public void handleMessage(APIManager.SOAPOperationData data) {
                    try {
                        System.out.println("DATA:::::: "+data.getResponseDataObj().toString());

                        System.out.println(!data.hasError());
                        if ("RMRESP".equalsIgnoreCase((String)data.getResponseDataObj().get("TYPE")) && "200".equalsIgnoreCase((String)data.getResponseDataObj().get("TXNSTATUS"))) {
                            apiManager.removeListener(this);
                            hideLoader();
                            finish();
                            Forward((String)data.getResponseDataObj().get("TXNID"));

                        }
                        else {
                            apiManager.removeListener(this);
                            hideLoader();
                            showAlert((String)data.getResponseDataObj().get("MESSAGE"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        new AlertDialog.Builder(BMEnterPin.this)
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

    void SendBank(){
        if (!verifyInternetStatus()) {
            return;
        }
        showLoader();

        try {
            System.out.println("ENTER PIN SENDING   ::::: "+appData.getMsisdn()+":::::"+mobile);
            final UUID resultId = apiManager.BMC2C(amount,appData.getMsisdn(),mobile,pin);

            final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

                @Override
                public void handleMessage(APIManager.SOAPOperationData data) {
                    try {
                        System.out.println("DATA:::::: "+data.getResponseDataObj().toString());

                        System.out.println(!data.hasError());
                        if ("RMRESP".equalsIgnoreCase((String)data.getResponseDataObj().get("TYPE")) && "200".equalsIgnoreCase((String)data.getResponseDataObj().get("TXNSTATUS"))) {
                            apiManager.removeListener(this);
                            hideLoader();
                            finish();
                            Intent i = new Intent(getBaseContext(),BMTransferBankresult.class);/*
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);*/
                            startActivity(i);
                        }
                        else {
                            apiManager.removeListener(this);
                            hideLoader();
                            showAlert((String)data.getResponseDataObj().get("MESSAGE"));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        new AlertDialog.Builder(BMEnterPin.this)
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

    void Forward(String txnid)
    {

        Intent i = new Intent(this,BMM2Mresult.class);/*
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);*/
        i.putExtra("amount",amount);
        i.putExtra("mobile",mobile);
        i.putExtra("txnid",txnid);
        finish();
        startActivity(i);
    }



    void nextAction() {

        pin = null;
    /*	pin = edtpin1.getText().toString() + edtpin2.getText().toString()
				+ edtpin3.getText().toString() + edtpin4.getText().toString()+ edtpin5.getText().toString();
*/
        if (
                (edtpin1.getText().toString().trim().isEmpty())
                        || (edtpin2.getText().toString().trim().isEmpty())
                        || (edtpin3.getText().toString().trim().isEmpty())
                        || (edtpin4.getText().toString().trim().isEmpty())
                ) {
            showAlert(getResources().getString(R.string.enteryourwalletpin));
            ClearPin();

        } else {

            pin = edtpin1.getText().toString() + edtpin2.getText().toString()
                    + edtpin3.getText().toString()
                    + edtpin4.getText().toString();


            if (!verifyInternetStatus()) {
                return;
            } else {

                 if (mode == CHANGE_EMAILORNUMBER)
                    UpdateProfileDedupe(newMobileNumber, pin, newEmail);
                else if (mode == USER_INACTIVITY || mode == USER_RESTART) {
                    CheckPin();
                }
                else if (mode == COLLECT_PAYMENT) {
                    CollectPayment();
                }
                else if (mode == SEND_MONEY) {
                    SendMoney(pin);
                }
                else if (mode == SEND_BANK) {
                    SendBank();
                }


                else {

                    Intent i = new Intent(getApplicationContext(), BMSubmitOTP.class);
                    i.putExtra("Mode", CHANGE_EMAIL);
                    i.putExtra("NewEmail", newEmail);
                    i.putExtra("Title", getResources().getString(R.string.changeemailtitle));
                    CloseWin();
                    startActivity(i);
                }
            }
        }

    }


    private void UpdateProfileDedupe(final String msisdn, String pin, final String emailid) {
        showLoader();
        try {

            final UUID resultId;

            resultId = apiManager.BMUpdateProfileDedupe(appData.getUniqueId(), msisdn, pin, emailid);

            final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

                @Override
                public void handleMessage(APIManager.SOAPOperationData data) {
                    try {

                        if (data.getId() == resultId) {
                            apiManager.removeListener(this);
                            ClearPin();
                            hideLoader();

                            if (!data.hasError()) {
                                hideLoader();
                                Intent i = new Intent(getApplicationContext(), BMSubmitOTP.class);
                                i.putExtra("Mode", CHANGE_EMAILORNUMBER);
                                i.putExtra("Email", emailid);
                                i.putExtra("Number", msisdn);

                                CloseWin();
                                startActivity(i);

                            } else if ("601".equals(data.getError())) {


                                hideLoader();
                                showAlert(getResources().getString(R.string.userblockedmessage));
                                //  ForgotPinAction();
                            } else if ("862".equals(data.getError())) {


                                hideLoader();
                                showAlert(getResources().getString(R.string.userblockedmessage));
                                //  ForgotPinAction();
                            } else {
                                hideLoader();
                                CLog.w(TAG, "error : " + data.getError());

                                showAlert(data.getResponseData().txnMessage);

                            }
                        }
                    } catch (Exception e) {

                        new AlertDialog.Builder(BMEnterPin.this)
                                .setTitle("Error")
                                .setMessage(ErrorAndPopupCodes.No_Response.getTag())
                                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

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
            showAlert(e.getMessage());
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
        imm.hideSoftInputFromWindow(edtpin1.getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        if (mode == USER_INACTIVITY || mode == USER_RESTART) {
            ShowLogin();
        }
		/*hideSoftKeyboard();
		NavUtils.navigateUpTo(BMEnterPin.this, NavUtils.getParentActivityIntent(BMEnterPin.this));*/
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
								i.putExtra("Mode", CHANGE_EMAIL);
								i.putExtra("NewEmail", newEmail);
								CloseWin();
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

						new AlertDialog.Builder(BMEnterPin.this)
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


    private void CheckPin() {
        //	isActive = false;

        String mobilenumber = BMReadData(BM_MOBILE, "");

        pin = edtpin1.getText().toString() + edtpin2.getText().toString()
                + edtpin3.getText().toString()
                + edtpin4.getText().toString();

        /*if (true){//pin.compareToIgnoreCase("36985") == 0) {
            Intent dat = new Intent();
            setResult(RESULT_OK, dat);
            //	apiManager.setCheckingPinStatus(false);
            finish();
        } else {
            Intent dat = new Intent();
            setResult(RESULT_CANCELED, dat);
            //	apiManager.setCheckingPinStatus(false);
            finish();
        }*/

/*        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();*/

        if (mode == USER_RESTART) {
            Logout();

        } else if (mode == USER_INACTIVITY) {
            if (appData.getMsisdn()!= null) {
                LoginUser(appData.getMsisdn(), pin);
                Intent i = new Intent(this,BMHome.class);
                startActivity(i);
            }
        }
    }

    private void LoginUser(final String msisdn, final String pin) {
        if (!verifyInternetStatus()) {
            return;
        }
        showLoader();
        DbManager dbManager = DbManager.createAuto(BMEnterPin.this);

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
                        new AlertDialog.Builder(BMEnterPin.this)
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


    public void OnDialogClose(String message) {

        if (message.compareToIgnoreCase(getResources().getString(R.string.userblockedmessage)) == 0) {
            ForgotPinAction();
        }else if(message.compareToIgnoreCase("Initiator MPIN is incorrect")!=0){
            CloseWin();
        }else{

        }

    }


    void ForgotPinAction() {

        String mobileNumber = BMReadData(BM_MOBILE, "");

        ClearPin();
        Intent i = new Intent(getApplicationContext(), BMForgotPin.class);
        // i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("Number", mobileNumber);
        startActivity(i);

    }

    private void CollectPayment() {
       /* if (!verifyInternetStatus()) {
            return;
        }
        showLoader();

        Wallet.Data.syncRead(getApplicationContext());
        System.out.println("AMOUNT::: "+getIntent().getExtras().getString("amount"));
        try {
            final UUID resultId = apiManager.BMCollectPayment(getIntent().getExtras().getString("amount"));

            final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

                @Override
                public void handleMessage(APIManager.SOAPOperationData data) {
                    try {
                        System.out.println("TXNSTATUS::::::"+data.getResponseDataObj().get("TXNSTATUS"));
                        if (data.getId() == resultId) {
                            if("200".equalsIgnoreCase((String)data.getResponseDataObj().get("TXNSTATUS"))) {
                                apiManager.removeListener(this);
                                hideLoader();
                                CLog.i(TAG, "User login success");*/
                                Intent i = new Intent(getBaseContext(), GenerateQRCodeActivity.class);
                                i.putExtra("amount",getIntent().getExtras().getString("amount"));
                                i.putExtra("invoice",getIntent().getExtras().getString("invoice"));
                                i.putExtra("txnid","TXNTST864826428482");
                                i.putExtra("ivrResponse",getIntent().getExtras().getString("amount")+":"+appData.getUniqueId()+":"+getIntent().getExtras().getString("invoice") );
                                CloseWin();
                                startActivity(i);
                               /*
                            }else
                            {
                                apiManager.removeListener(this);
                                hideLoader();
                                CLog.i(TAG, "QR gen failure");
                                showAlert((String)data.getResponseDataObj().get("MESSAGE"));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        new AlertDialog.Builder(BMEnterPin.this)
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
        }*/
    }











    private void ClearPin() {
        edtpin1.setText("");
        edtpin2.setText("");
        edtpin3.setText("");
        edtpin4.setText("");
        edtpin1.requestFocus();
    }

    public void OnDialogClose() {


        if (reloadintent != null) {
            CloseWin();
            startActivity(reloadintent);
        }


    }

    Intent reloadintent = null;

   /* private void DeleteStoredCard(int position, String pin) {
        showLoader();
        try {
            final UUID resultId = apiManager.BMDeleteCard(appData.getUniqueId(), appData.getWalletCardList().get(position).paymentInstrumentId, pin);
            final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

                @Override
                public void handleMessage(APIManager.SOAPOperationData data) {
                    try {

                        if (data.getId() == resultId) {
                            apiManager.removeListener(this);
                            hideLoader();
                            ClearPin();

                            if (!data.hasError()) {
                                hideLoader();


                                reloadintent = new Intent(getApplicationContext(), BMLoadSVNCards.class);
                                reloadintent.putExtra("Amount", amount);
                                reloadintent.putExtra("Description", description);

                                //	startActivity(reloadintent);

                                showAlert("Card deleted successfully");

                            } else if ("601".equals(data.getError())) {


                                hideLoader();
                                showAlert(getResources().getString(R.string.userblockedmessage));
                                //  ForgotPinAction();
                            } else if ("862".equals(data.getError())) {


                                hideLoader();
                                showAlert(getResources().getString(R.string.userblockedmessage));
                                //  ForgotPinAction();
                            } else {
                                hideLoader();
                                CLog.w(TAG, "error : " + data.getError());
                                ClearPin();
                                showAlert(data.getResponseData().txnMessage);


                            }
                        }
                    } catch (Exception e) {
                        ClearPin();
                        new AlertDialog.Builder(BMEnterPin.this)
                                .setTitle("Error")
                                .setMessage(ErrorAndPopupCodes.No_Response.getTag())
                                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

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
            showAlert(e.getMessage());
            hideLoader();
        }
    }*/




}
