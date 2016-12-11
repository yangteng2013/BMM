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

import java.util.UUID;

import app.banking.bankmuscat.R;
import app.banking.bankmuscat.merchant.api.APIManager;
import app.banking.bankmuscat.merchant.base.ActivityBase;
import app.banking.bankmuscat.merchant.common.Wallet;
import app.banking.bankmuscat.merchant.interfaces.ILoader;
import app.banking.bankmuscat.merchant.logger.CLog;
import app.banking.bankmuscat.merchant.util.constant.ErrorAndPopupCodes;


//import com.google.i18n.phonenumbers.NumberParseException;
//import com.google.i18n.phonenumbers.PhoneNumberUtil;
//import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
//import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class BMResetPin extends ActivityBase implements ILoader {

    private static final String TAG = "BMReSetPin";
    private final APIManager apiManager = APIManager.createInstance(null);
    private Wallet.Data appData = Wallet.Data.getInstance();

    public ImageView btnBack, header_home, header_menu_button;
    TextView headingTextView;
    public EditText edtpin1, edtpin2, edtpin3, edtpin4, /*edtpin5,*/ edtrepin1, edtrepin2, edtrepin3, edtrepin4/*, edtrepin5*/;
    Button confirm, cancel;

    RelativeLayout mob_pin;

    ImageView mobbtninfo;
    //String otp, number, nid = "";

    String  number, nid = "";

    @Override
    public int GetScreenLayout() {
        return R.layout.bm_reset_pin;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RemoveHeaderButtons();

        nid = getIntent().getExtras().getString("Nid");
        number = getIntent().getExtras().getString("Number");
       // otp = getIntent().getExtras().getString("Otp");

        //setContentView(R.layout.bm_reset_pin);
        btnBack = (ImageView) findViewById(R.id.header_back_button);
        confirm = (Button) findViewById(R.id.mob_buttonconfirm);
        cancel = (Button) findViewById(R.id.mob_buttoncancel);
        edtpin1 = (EditText) findViewById(R.id.edtpin1);
        edtpin2 = (EditText) findViewById(R.id.edtpin2);
        edtpin3 = (EditText) findViewById(R.id.edtpin3);
        edtpin4 = (EditText) findViewById(R.id.edtpin4);/*
        edtpin5 = (EditText) findViewById(R.id.edtpin5);*/

        edtrepin1 = (EditText) findViewById(R.id.edtrepin1);
        edtrepin2 = (EditText) findViewById(R.id.edtrepin2);
        edtrepin3 = (EditText) findViewById(R.id.edtrepin3);
        edtrepin4 = (EditText) findViewById(R.id.edtrepin4);/*
        edtrepin5 = (EditText) findViewById(R.id.edtrepin5);*/

        setTextFocus(edtpin1, edtpin2);
        setTextFocus(edtpin2, edtpin3);
        setTextFocus(edtpin3, edtpin4);/*
        setTextFocus(edtpin4, edtpin5);*/
/*
        setTextFocus(edtpin5, edtrepin1);*/
        setTextFocus(edtrepin1, edtrepin2);
        setTextFocus(edtrepin2, edtrepin3);
        setTextFocus(edtrepin3, edtrepin4);/*
        setTextFocus(edtrepin4, edtrepin5);*/
/*
        setDeleteFocus(edtpin5, edtpin4);*/
        setDeleteFocus(edtpin4, edtpin3);
        setDeleteFocus(edtpin3, edtpin2);
        setDeleteFocus(edtpin2, edtpin1);/*
        setDeleteFocus(edtrepin5, edtrepin4);*/
        setDeleteFocus(edtrepin4, edtrepin3);
        setDeleteFocus(edtrepin3, edtrepin2);
        setDeleteFocus(edtrepin2, edtrepin1);

        header_home = (ImageView) findViewById(R.id.header_home);
        header_menu_button = (ImageView) findViewById(R.id.header_menu_button);

        headingTextView = (TextView) findViewById(R.id.title_header);
        headingTextView.setText(getResources().getString(R.string.resetpin));

        edtpin1.requestFocus();
        getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        confirm.setOnClickListener(new OnClickListener() {

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

        edtrepin4.setOnEditorActionListener(new OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {

                nextAction();

                return false;
            }

        });


        btnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                hideSoftKeyboard();
               /* NavUtils.navigateUpTo(BMResetPin.this, NavUtils
                        .getParentActivityIntent(BMResetPin.this));*/
                CloseWin();

            }

        });

    }

    void nextAction() {


		 /*if (edtpin1.getText().toString().trim().isEmpty()) {

			showAlert("Please enter the otp");
		}


		else {*/


        if (!verifyInternetStatus()) {
            return;
        } else {


            Validation();

					/*Intent i = new Intent(getApplicationContext(), BMSecurityQuestion.class);
                    startActivity(i);*/
        }
        //	}

    }



    private void Validation() {
        String pin = null;
        pin = edtpin1.getText().toString() + edtpin2.getText().toString()
                + edtpin3.getText().toString() + edtpin4.getText().toString() /*+ edtpin5.getText().toString()*/;

        if (
                (edtpin1.getText().toString().trim().isEmpty())
                        || (edtpin2.getText().toString().trim().isEmpty())
                        || (edtpin3.getText().toString().trim().isEmpty())
                        || (edtpin4.getText().toString().trim().isEmpty())
                        || (edtrepin1.getText().toString().trim().isEmpty())
                        || (edtrepin2.getText().toString().trim().isEmpty())
                        || (edtrepin3.getText().toString().trim().isEmpty())
                        || (edtrepin4.getText().toString().trim().isEmpty())) {
            showAlert("Please enter the Pin Number");

        } else if (edtpin1.getText().toString().trim()
                .equalsIgnoreCase(edtrepin1.getText().toString().trim())
                && edtpin2.getText().toString().trim()
                .equalsIgnoreCase(edtrepin2.getText().toString().trim())
                && edtpin3.getText().toString().trim()
                .equalsIgnoreCase(edtrepin3.getText().toString().trim())
                && edtpin4.getText().toString().trim()
                .equalsIgnoreCase(edtrepin4.getText().toString().trim())) {

            pin = edtpin1.getText().toString() + edtpin2.getText().toString()
                    + edtpin3.getText().toString()
                    + edtpin4.getText().toString() /*+ edtpin5.getText().toString()*/;
            if (!verifyInternetStatus()) {
                return;
            } else {
                BMResetPin.this.ForgotPINReset(appData.getUniqueId(),number,pin);//, otp);

               /* Intent i = new Intent(getApplicationContext(), BMSubmitOTP.class);
                i.putExtra("Mode", FORGOT_PIN);
                i.putExtra("Nid", nid);
                i.putExtra("Number", number);
                i.putExtra("Pin", pin);
                CloseWin();
                startActivity(i);*/
            }

        } else {

            showAlert("PIN and Re-enter PIN does not match! Please try again.");
            edtpin1.setText(null);
            edtpin2.setText(null);
            edtpin3.setText(null);
            edtpin4.setText(null);
            edtrepin1.setText(null);
            edtrepin2.setText(null);
            edtrepin3.setText(null);
            edtrepin4.setText(null);
            edtpin1.requestFocus();
        }
    }


    private void ForgotPINReset(String uniqueId, String msisdn, String pin){//}, String otp) {
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

            final UUID resultId = apiManager.ResetPin(msisdn, pin,pin);//, otp);

            final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

                @Override
                public void handleMessage(APIManager.SOAPOperationData data) {
                    try {

                        if ("RRPNRESP".equalsIgnoreCase((String)data.getResponseDataObj().get("TYPE")) && "200".equalsIgnoreCase((String)data.getResponseDataObj().get("TXNSTATUS"))) {
                            apiManager.removeListener(this);
                            hideLoader();
                            Intent i = new Intent(getApplicationContext(),BMChangesMade.class);
                            i.putExtra("Title", getResources().getString(R.string.changepin));
                            i.putExtra("SubtTitle", getResources().getString(R.string.changepindisclaimer));
                            CloseWin();
                            startActivity(i);
                            hideLoader();

                        } else {
                            hideLoader();
                            CLog.w(TAG, "out error : " + data.getError());
                            showAlert((String)data.getResponseDataObj().get("MESSAGE"));

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            apiManager.addListener(l);
        } catch (Exception e) {
            hideLoader();
        }
    }


/*    public void OnDialogClose() {
        CloseWin();
      //  ShowLogin();
    }*/


    private void SetPin(String pin) {
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

                           /* if (!data.hasError()) {
                                CLog.i(TAG, "SetPin  success");
                                DbManager dbManager = DbManager
                                        .createAuto(BMSetPin.this);
                                dbManager
                                        .setAppStatus(Wallet.Status.REGISTRAION_COMPLETED);

                                appData.setServiceId(data.getServiceId());
                                Intent i = new Intent(getApplicationContext(),
                                        BMSecurityQuestion.class);
                                startActivity(i);
                                hideLoader();
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

                            }*/

                            CloseWin();
                        }
                    } catch (Exception e) {

                        new AlertDialog.Builder(BMResetPin.this)
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


    void CloseWin() {
/*hideSoftKeyboard();
				NavUtils.navigateUpTo(BMEnterEmail.this, NavUtils
						.getParentActivityIntent(BMEnterEmail.this));*/
        this.finish();
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
        hideSoftKeyboard();
        // NavUtils.navigateUpTo(BMResetPin.this, NavUtils.getParentActivityIntent(BMResetPin.this));
        CloseWin();
    }
}
