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
import android.widget.TextView;

import java.util.UUID;

import app.banking.bankmuscat.R;
import app.banking.bankmuscat.merchant.api.APIManager;
import app.banking.bankmuscat.merchant.base.ActivityBase;
import app.banking.bankmuscat.merchant.common.Wallet;
import app.banking.bankmuscat.merchant.interfaces.ILoader;
import app.banking.bankmuscat.merchant.logger.CLog;
import app.banking.bankmuscat.merchant.util.CommonUtils;
import app.banking.bankmuscat.merchant.util.constant.ErrorAndPopupCodes;


//import com.google.i18n.phonenumbers.NumberParseException;
//import com.google.i18n.phonenumbers.PhoneNumberUtil;
//import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
//import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class BMQRScan extends ActivityBase implements ILoader {

    private static final String TAG = "BMQRScan";
    private final APIManager apiManager = APIManager.createInstance(null);
    private Wallet.Data appData = Wallet.Data.getInstance();

    public ImageView btnBack, header_home, header_menu_button;
    TextView headingTextView;
    public EditText edt_code, edt_amount;

    Button next, cancel;

    @Override
    public int GetScreenLayout() {
        return R.layout.bm_qr_scan;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.bm_qr_scan);
        btnBack = (ImageView) findViewById(R.id.header_back_button);
        next = (Button) findViewById(R.id.mob_buttonnext);
        cancel = (Button) findViewById(R.id.mob_buttoncancel);
        edt_code = (EditText) findViewById(R.id.edt_code);

        edt_amount = (EditText) findViewById(R.id.edt_amount);
        CommonUtils.AdjustAmount(edt_amount);

        header_home = (ImageView) findViewById(R.id.header_home);
        header_menu_button = (ImageView) findViewById(R.id.header_menu_button);

        headingTextView = (TextView) findViewById(R.id.title_header);
        headingTextView.setText(getResources().getString(R.string.home_qrscan_for_merchant_pay));

        edt_code.requestFocus();
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
/*hideSoftKeyboard();
                NavUtils.navigateUpTo(BMEnterEmail.this, NavUtils
						.getParentActivityIntent(BMEnterEmail.this));*/
        this.finish();
    }

    void nextAction() {

String name = edt_code.getText().toString().trim();
        String amount = edt_amount.getText().toString().trim();
        if (name.isEmpty()) {

            showAlert("Please enter merchant code");
        }

        if (amount.isEmpty()) {

            showAlert("Please enter amount");
        }
        else {


            if (!verifyInternetStatus()) {
                return;
            } else {

                ScanQR();
            }
        }

    }

    private void ScanQR() {
        Intent i = new Intent(getApplicationContext(),
                BMQRScanDetails.class);
       // i.putExtra("Registration", "Registration");
        CloseWin();
        startActivity(i);
    }

    private void ChangeProfileDetails(final String name) {
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
            final UUID resultId = apiManager.BMChangeProfileDetails(data.image, data.uniqueId, data.emailId, data.mobileNumber, data.dateOfBirth, name, name);

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
                                appData.getUserData().firstName = name;
                                showAlert(data.getResponseData().txnMessage,"", true);



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

                        new AlertDialog.Builder(BMQRScan.this)
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




    public void OnDialogClose() {

            CloseWin();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        hideSoftKeyboard();
        return false;
    }

    void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edt_code.getWindowToken(), 0);
    }

	/*@Override
	public void onBackPressed() {
		hideSoftKeyboard();
		NavUtils.navigateUpTo(BMEnterEmail.this, NavUtils.getParentActivityIntent(BMEnterEmail.this));
	}*/
}
