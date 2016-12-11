package app.banking.bankmuscat.merchant.Screens;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

public class BMChangePin extends ActivityBase implements ILoader {

    private static final String TAG = "BMRegisterUser";
    private final APIManager apiManager = APIManager.createInstance(null);
    private Wallet.Data appData = Wallet.Data.getInstance();

    public ImageView btnBack, header_home, header_menu_button;
    TextView headingTextView;
    public EditText edtoldpin1, edtoldpin2, edtoldpin3, edtoldpin4, edtoldpin5, edtpin1, edtpin2, edtpin3, edtpin4, edtpin5, edtrepin1, edtrepin2, edtrepin3, edtrepin4, edtrepin5;
    Button confirm, cancel;
    RelativeLayout mob_pin;

    ImageView mobbtninfo;

    @Override
    public int GetScreenLayout() {
        return R.layout.bm_change_pin;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setContentView(R.layout.bm_change_pin);
        btnBack = (ImageView) findViewById(R.id.header_back_button);
        confirm = (Button) findViewById(R.id.mob_buttonconfirm);
        cancel = (Button) findViewById(R.id.mob_buttoncancel);

        edtoldpin1 = (EditText) findViewById(R.id.edtoldpin1);
        edtoldpin2 = (EditText) findViewById(R.id.edtoldpin2);
        edtoldpin3 = (EditText) findViewById(R.id.edtoldpin3);
        edtoldpin4 = (EditText) findViewById(R.id.edtoldpin4);
        /*edtoldpin5 = (EditText) findViewById(R.id.edtoldpin5);*/

        edtpin1 = (EditText) findViewById(R.id.edtpin1);
        edtpin2 = (EditText) findViewById(R.id.edtpin2);
        edtpin3 = (EditText) findViewById(R.id.edtpin3);
        edtpin4 = (EditText) findViewById(R.id.edtpin4);
        /*edtpin5 = (EditText) findViewById(R.id.edtpin5);*/

        edtrepin1 = (EditText) findViewById(R.id.edtrepin1);
        edtrepin2 = (EditText) findViewById(R.id.edtrepin2);
        edtrepin3 = (EditText) findViewById(R.id.edtrepin3);
        edtrepin4 = (EditText) findViewById(R.id.edtrepin4);
        /*edtrepin5 = (EditText) findViewById(R.id.edtrepin5);*/

        setTextFocus(edtoldpin1, edtoldpin2);
        setTextFocus(edtoldpin2, edtoldpin3);
        setTextFocus(edtoldpin3, edtoldpin4);
        /*setTextFocus(edtoldpin4, edtoldpin5);*/

        setTextFocus(edtoldpin4, edtpin1);
        setTextFocus(edtpin1, edtpin2);
        setTextFocus(edtpin2, edtpin3);
        setTextFocus(edtpin3, edtpin4);
        /*setTextFocus(edtpin4, edtpin5);*/

        setTextFocus(edtpin4, edtrepin1);
        setTextFocus(edtrepin1, edtrepin2);
        setTextFocus(edtrepin2, edtrepin3);
        setTextFocus(edtrepin3, edtrepin4);
        /*setTextFocus(edtrepin4, edtrepin5);*/

        /*setDeleteFocus(edtoldpin5, edtoldpin4);*/
        setDeleteFocus(edtoldpin4, edtoldpin3);
        setDeleteFocus(edtoldpin3, edtoldpin2);
        setDeleteFocus(edtoldpin2, edtoldpin1);
        /*setDeleteFocus(edtpin5, edtpin4);*/
        setDeleteFocus(edtpin4, edtpin3);
        setDeleteFocus(edtpin3, edtpin2);
        setDeleteFocus(edtpin2, edtpin1);
       /* setDeleteFocus(edtrepin5, edtrepin4);*/
        setDeleteFocus(edtrepin4, edtrepin3);
        setDeleteFocus(edtrepin3, edtrepin2);
        setDeleteFocus(edtrepin2, edtrepin1);

        header_home = (ImageView) findViewById(R.id.header_home);
        header_menu_button = (ImageView) findViewById(R.id.header_menu_button);

        headingTextView = (TextView) findViewById(R.id.title_header);
        headingTextView.setText(getResources().getString(R.string.menu_change_pin));

        edtoldpin1.requestFocus();
        getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        /*mobbtninfo = (ImageView) findViewById(R.id.mobbtninfo);*/
        /*mobbtninfo.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                showInfo(getResources().getString(R.string.pinrule));
            }
        });*/

        mob_pin = (RelativeLayout) findViewById(R.id.mob_pin);
        mob_pin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                showInfo(getResources().getString(R.string.pinrule));
            }
        });

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


        btnBack.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

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


		 /*if (edtpin1.getText().toString().trim().isEmpty()) {

			showAlert("Please enter the otp");
		}


		else {*/


        if (!verifyInternetStatus()) {
            return;
        } else {


            Validation();
                    /*this.finish();
                    Intent i = new Intent(getApplicationContext(), BMChangesMade.class);
					startActivity(i);*/
        }
        //	}

    }


    private void Validation() {

        String oldPin = null;
        oldPin = edtoldpin1.getText().toString() + edtoldpin2.getText().toString()
                + edtoldpin3.getText().toString() + edtoldpin4.getText().toString()/* + edtoldpin5.getText().toString()*/;

        String pin = null;
        pin = edtpin1.getText().toString() + edtpin2.getText().toString()
                + edtpin3.getText().toString() + edtpin4.getText().toString() /*+ edtpin5.getText().toString()*/;

        if (
                (edtoldpin1.getText().toString().trim().isEmpty())
                        || (edtoldpin2.getText().toString().trim().isEmpty())
                        || (edtoldpin3.getText().toString().trim().isEmpty())
                        || (edtoldpin4.getText().toString().trim().isEmpty())
                       /* || (edtoldpin5.getText().toString().trim().isEmpty())*/
                        || (edtpin1.getText().toString().trim().isEmpty())
                        || (edtpin2.getText().toString().trim().isEmpty())
                        || (edtpin3.getText().toString().trim().isEmpty())
                        || (edtpin4.getText().toString().trim().isEmpty())
                        /*|| (edtpin5.getText().toString().trim().isEmpty())*/
                        || (edtrepin1.getText().toString().trim().isEmpty())
                        || (edtrepin2.getText().toString().trim().isEmpty())
                        || (edtrepin3.getText().toString().trim().isEmpty())
                        || (edtrepin4.getText().toString().trim().isEmpty())
                        /*|| (edtrepin5.getText().toString().trim().isEmpty())*/) {
            showAlert("Please enter the Wallet PIN");

        } else if (edtpin1.getText().toString().trim()
                .equalsIgnoreCase(edtrepin1.getText().toString().trim())
                && edtpin2.getText().toString().trim()
                .equalsIgnoreCase(edtrepin2.getText().toString().trim())
                && edtpin3.getText().toString().trim()
                .equalsIgnoreCase(edtrepin3.getText().toString().trim())
                && edtpin4.getText().toString().trim()
                .equalsIgnoreCase(edtrepin4.getText().toString().trim())
                /*&& edtpin5.getText().toString().trim()
                .equalsIgnoreCase(edtrepin5.getText().toString().trim())*/) {

            if (!IsSequentialOrRepetitive(edtpin1.getText().toString().trim(), edtpin2.getText().toString().trim(), edtpin3.getText().toString().trim(), edtpin4.getText().toString().trim())) {
                pin = edtpin1.getText().toString() + edtpin2.getText().toString()
                        + edtpin3.getText().toString()
                        + edtpin4.getText().toString() /*+ edtpin5.getText().toString()*/;
                if (!verifyInternetStatus()) {
                    return;
                } else {

                    if (oldPin.compareToIgnoreCase(pin) == 0)
                        showAlert(getResources().getString(R.string.samepinerror));
                    else
                        BMChangePin.this.ChangePin(oldPin, pin);
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
            /*edtpin5.setText(null);*/
            edtrepin1.setText(null);
            edtrepin2.setText(null);
            edtrepin3.setText(null);
            edtrepin4.setText(null);
            /*edtrepin5.setText(null);*/
            edtpin1.requestFocus();
        }
    }




    private void ChangePin(String oldPin, final String pin) {
        showLoader();
        try {
            final UUID resultId = apiManager.UpdatePin(appData.getMsisdn(),oldPin, pin);
            final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

                @Override
                public void handleMessage(APIManager.SOAPOperationData data) {
                    try {

                        if (data.getId() == resultId) {
                            apiManager.removeListener(this);
                            hideLoader();

                                if("200".equalsIgnoreCase((String)data.getResponseDataObj().get("TXNSTATUS"))) {
                                    appData.setServiceId(data.getServiceId());
                                    appData.setPinIntentString(pin);
                                    Intent i = new Intent(getApplicationContext(),
                                            BMChangesMade.class);
                                    i.putExtra("Title", getResources().getString(R.string.changepin));
                                    i.putExtra("SubtTitle", getResources().getString(R.string.changepindisclaimer));
                                    CloseWin();
                                    ClearPin();
                                    startActivity(i);
                                    hideLoader();
                                }
                                else{
                                    showAlert((String)data.getResponseDataObj().get("MESSAGE"));
                                }

                        }
                    } catch (Exception e) {
                        hideLoader();
                        e.printStackTrace();
                        new AlertDialog.Builder(BMChangePin.this)
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

    public void OnDialogClose(String message) {

        if (message.compareToIgnoreCase(getResources().getString(R.string.userblockedmessage)) == 0) {
            ForgotPinAction();
        }
    }

    void ForgotPinAction() {

        String mobileNumber = BMReadData(BM_MOBILE, "");
        ClearPin();
        Intent i = new Intent(getApplicationContext(), BMForgotPin.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("Number", mobileNumber);
        startActivity(i);

    }

    private void ClearPin() {
        edtoldpin1.setText("");
        edtoldpin2.setText("");
        edtoldpin3.setText("");
        edtoldpin4.setText("");
        /*edtoldpin5.setText("");*/
        edtpin1.setText("");
        edtpin2.setText("");
        edtpin3.setText("");
        edtpin4.setText("");
        /*edtpin5.setText("");*/
        edtrepin1.setText("");
        edtrepin2.setText("");
        edtrepin3.setText("");
        edtrepin4.setText("");
//        edtrepin5.setText("");
        edtoldpin1.requestFocus();
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

	/*@Override
    public void onBackPressed() {
		hideSoftKeyboard();
		NavUtils.navigateUpTo(BMChangePin.this, NavUtils.getParentActivityIntent(BMChangePin.this));
	}*/
}
