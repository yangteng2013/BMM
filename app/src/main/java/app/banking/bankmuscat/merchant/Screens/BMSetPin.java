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
import android.widget.CheckBox;
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
//import com.google.i18n.phonenumbers.PhoneNumberUtil;
//import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
//import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class BMSetPin extends ActivityBase implements ILoader {

    private static final String TAG = "BMSetPin";
    private final APIManager apiManager = APIManager.createInstance(null);
    private Wallet.Data appData = Wallet.Data.getInstance();

    public ImageView btnBack, header_home, header_menu_button;
    TextView headingTextView;
    public EditText edtpin1, edtpin2, edtpin3, edtpin4, edtpin5, edtrepin1, edtrepin2, edtrepin3, edtrepin4, edtrepin5;
    Button next, cancel;
    RelativeLayout mob_pin;

    ImageView mobbtninfo;
    CheckBox mobchksetpin;

    String pin;

    @Override
    public int GetScreenLayout() {
        return R.layout.bm_set_pin;
    }

    @Override
    public boolean isHeaderNeeded() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.bm_set_pin);
        btnBack = (ImageView) findViewById(R.id.header_back_button);
        mobchksetpin = (CheckBox) findViewById(R.id.mobchksetpin);
        cancel = (Button) findViewById(R.id.mob_buttoncancel);
        next = (Button) findViewById(R.id.mob_buttonnext);
        edtpin1 = (EditText) findViewById(R.id.edtpin1);
        edtpin2 = (EditText) findViewById(R.id.edtpin2);
        edtpin3 = (EditText) findViewById(R.id.edtpin3);
        edtpin4 = (EditText) findViewById(R.id.edtpin4);
        edtpin5 = (EditText) findViewById(R.id.edtpin5);

        edtrepin1 = (EditText) findViewById(R.id.edtrepin1);
        edtrepin2 = (EditText) findViewById(R.id.edtrepin2);
        edtrepin3 = (EditText) findViewById(R.id.edtrepin3);
        edtrepin4 = (EditText) findViewById(R.id.edtrepin4);
        edtrepin5 = (EditText) findViewById(R.id.edtrepin5);

        getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        setTextFocus(edtpin1, edtpin2);
        setTextFocus(edtpin2, edtpin3);
        setTextFocus(edtpin3, edtpin4);
        setTextFocus(edtpin4, edtpin5);

        setTextFocus(edtpin5, edtrepin1);
        setTextFocus(edtrepin1, edtrepin2);
        setTextFocus(edtrepin2, edtrepin3);
        setTextFocus(edtrepin3, edtrepin4);
        setTextFocus(edtrepin4, edtrepin5);

        setDeleteFocus(edtpin5, edtpin4);
        setDeleteFocus(edtpin4, edtpin3);
        setDeleteFocus(edtpin3, edtpin2);
        setDeleteFocus(edtpin2, edtpin1);
        setDeleteFocus(edtrepin5, edtrepin4);
        setDeleteFocus(edtrepin4, edtrepin3);
        setDeleteFocus(edtrepin3, edtrepin2);
        setDeleteFocus(edtrepin2, edtrepin1);

        header_home = (ImageView) findViewById(R.id.header_home);
        header_menu_button = (ImageView) findViewById(R.id.header_menu_button);

        headingTextView = (TextView) findViewById(R.id.title_header);
        headingTextView.setText(getResources().getString(R.string.enterpin));

        edtpin1.requestFocus();
        getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        mobbtninfo = (ImageView) findViewById(R.id.mobbtninfo);
        mobbtninfo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                showInfo(getResources().getString(R.string.pinrule));
            }
        });

        mob_pin = (RelativeLayout) findViewById(R.id.mob_pin);
        mob_pin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                showInfo(getResources().getString(R.string.pinrule));
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
                //ExitApplication();
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

		});

		edtpin2.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {

				edtpin3.requestFocus();
				getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);

				return false;
			}

		});

		edtpin3.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {

				edtpin4.requestFocus();
				getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);

				return false;
			}

		});

		edtpin4.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {

				edtrepin1.requestFocus();
				getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);

				return false;
			}

		});

		edtrepin1.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {

				edtrepin2.requestFocus();
				getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);

				return false;
			}

		});

		edtrepin2.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {

				edtrepin3.requestFocus();
				getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);

				return false;
			}

		});

		edtrepin3.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {

				edtrepin4.requestFocus();
				getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);

				return false;
			}

		});*/

        edtrepin5.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {

                nextAction();

                return false;
            }

        });


        btnBack.setVisibility(View.INVISIBLE);
        btnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                CloseWin();
               /* hideSoftKeyboard();
                NavUtils.navigateUpTo(BMSetPin.this, NavUtils
                        .getParentActivityIntent(BMSetPin.this));*/

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


		 /*if (edtpin1.getText().toString().trim().isEmpty()) {

			showAlert("Please enter the otp");
		}


		else {*/


        if (!verifyInternetStatus()) {
            return;
        } else {

					/*TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
                    String imei = telephonyManager.getDeviceId();
					BMRegisterUser.this.validateMSISDN(mobileNumber,
							null != imei ? imei : mobileNumber);*/

            Validation();

					/*Intent i = new Intent(getApplicationContext(), BMSecurityQuestion.class);
					startActivity(i);*/
        }
        //	}

    }



    private void Validation() {


        // String pin = null;
        pin = edtpin1.getText().toString() + edtpin2.getText().toString()
                + edtpin3.getText().toString() + edtpin4.getText().toString() + edtpin5.getText().toString();

        if (
                (edtpin1.getText().toString().trim().isEmpty())
                        || (edtpin2.getText().toString().trim().isEmpty())
                        || (edtpin3.getText().toString().trim().isEmpty())
                        || (edtpin4.getText().toString().trim().isEmpty())
                        || (edtpin5.getText().toString().trim().isEmpty())
                        || (edtrepin1.getText().toString().trim().isEmpty())
                        || (edtrepin2.getText().toString().trim().isEmpty())
                        || (edtrepin3.getText().toString().trim().isEmpty())
                        || (edtrepin4.getText().toString().trim().isEmpty())
                        || (edtrepin5.getText().toString().trim().isEmpty())) {
            showAlert("Please enter the Wallet PIN");

        } else if (edtpin1.getText().toString().trim()
                .equalsIgnoreCase(edtrepin1.getText().toString().trim())
                && edtpin2.getText().toString().trim()
                .equalsIgnoreCase(edtrepin2.getText().toString().trim())
                && edtpin3.getText().toString().trim()
                .equalsIgnoreCase(edtrepin3.getText().toString().trim())
                && edtpin4.getText().toString().trim()
                .equalsIgnoreCase(edtrepin4.getText().toString().trim())
                && edtpin5.getText().toString().trim()
                .equalsIgnoreCase(edtrepin5.getText().toString().trim())) {
            if (!IsSequentialOrRepetitive(edtpin1.getText().toString().trim(), edtpin2.getText().toString().trim(), edtpin3.getText().toString().trim(), edtpin4.getText().toString().trim())) {

                if (mobchksetpin.isChecked()) {
                    pin = edtpin1.getText().toString() + edtpin2.getText().toString()
                            + edtpin3.getText().toString()
                            + edtpin4.getText().toString() + edtpin5.getText().toString();
                    if (!verifyInternetStatus()) {
                        return;
                    } else {
                        BMSetPin.this.setPin(pin);
                    }
                } else {
                    showAlert("Please accept to Bank Muscat Terms & Conditions to continue.");
                }
            } else {

                showAlert("Wallet PIN can not be sequential or repetitive.");
            }

        } else {

            showAlert("Wallet PIN and Re-enter Wallet PIN does not match! Please try again.");
            edtpin1.setText(null);
            edtpin2.setText(null);
            edtpin3.setText(null);
            edtpin4.setText(null);
            edtpin5.setText(null);
            edtrepin1.setText(null);
            edtrepin2.setText(null);
            edtrepin3.setText(null);
            edtrepin4.setText(null);
            edtrepin5.setText(null);
            edtpin1.requestFocus();
        }
    }


    private void setPin(final String pin) {
        showLoader();
        try {
            final UUID resultId = apiManager.BMSetPin(appData.getUniqueId(), pin);
            final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

                @Override
                public void handleMessage(APIManager.SOAPOperationData data) {
                    try {

                        if (data.getId() == resultId) {
                            apiManager.removeListener(this);
                            hideLoader();

                            if (!data.hasError()) {
                                CLog.i(TAG, "SetPin  success");
                               /* DbManager dbManager = DbManager
                                        .createAuto(BMSetPin.this);
                                dbManager
                                        .setAppStatus(Wallet.Status.REGISTRAION_COMPLETED);*/

                                appData.setServiceId(data.getServiceId());
                                /*Intent i = new Intent(getApplicationContext(),
                                        BMSecurityQuestion.class);
                                i.putExtra("pin", pin);
                                CloseWin();
                                startActivity(i);*/
                                hideLoader();

                                appData.getUser().pin = pin;

Intent i = new Intent(getApplicationContext(), BMSecurityQuestion.class);
                                i.putExtra("Mode", REGISTER_USER);
                                i.putExtra("Title", getResources().getString(R.string.submitotp));


                                CloseWin();
                                startActivity(i);
                               // Login();
                            } else {
                                hideLoader();

                                CLog.w(TAG, "error : " + data.getError());
                                showAlert(data.getResponseData().txnMessage);

                                edtpin1.getText().clear();
                                edtpin2.getText().clear();
                                edtpin3.getText().clear();
                                edtpin4.getText().clear();
                                edtpin5.getText().clear();
                                edtrepin1.getText().clear();
                                edtrepin2.getText().clear();
                                edtrepin3.getText().clear();
                                edtrepin4.getText().clear();
                                edtrepin5.getText().clear();
                                edtpin1.requestFocus();

                                setTextFocus(edtpin1, edtpin2);
                                setTextFocus(edtpin2, edtpin3);
                                setTextFocus(edtpin3, edtpin4);
                                setTextFocus(edtpin4, edtpin5);
                                hideLoader();

                            }
                            hideLoader();
                           // CLog.w(TAG, "error : " + data.getError());
                          //  showAlert(data.getResponseData().txnMessage);
                        }
                    } catch (Exception e) {

                        new AlertDialog.Builder(BMSetPin.this)
                                .setTitle("Error")
                                .setMessage(ErrorAndPopupCodes.No_Response.getTag())
                                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // continue with delete
                                        Validation();
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
            showAlert(e.getMessage());
            hideLoader();
        }
    }

    private void Login() {


        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();

        LoginUser(appData.getMsisdn(), pin, null != imei ? imei : appData.getMsisdn(), "aaaaaa");

    }

    private void LoginUser(String msisdn, String pin, String imei, String rnsId) {
        if (!verifyInternetStatus()) {
            return;
        }
        showLoader();
        DbManager dbManager = DbManager.createAuto(BMSetPin.this);
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

                        new AlertDialog.Builder(BMSetPin.this)
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

	/*@Override
	public void onClick(View v) {

		switch (v.getId()) {
			case R.id.header_back_button:

				hideSoftKeyboard();
				NavUtils.navigateUpTo(RegistrationPin.this,
						NavUtils.getParentActivityIntent(RegistrationPin.this));
				break;

			case R.id.btnCancelPin:

				ExitApplication();
				break;

			case R.id.btnConfirmPin:

				Validation();
				break;
			default:
				break;
		}
	}*/


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
       /* hideSoftKeyboard();
        NavUtils.navigateUpTo(BMSetPin.this, NavUtils.getParentActivityIntent(BMSetPin.this));*/

        CloseWin();
    }
}
