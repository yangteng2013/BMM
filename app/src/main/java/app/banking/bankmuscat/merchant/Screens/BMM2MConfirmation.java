package app.banking.bankmuscat.merchant.Screens;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.UUID;

import app.banking.bankmuscat.R;
import app.banking.bankmuscat.merchant.api.APIManager;
import app.banking.bankmuscat.merchant.base.ActivityBase;
import app.banking.bankmuscat.merchant.components.widgets.RobotoButton;
import app.banking.bankmuscat.merchant.components.widgets.RobotoEditText;
import app.banking.bankmuscat.merchant.components.widgets.RobotoTextView;
import app.banking.bankmuscat.merchant.entity.instrument.Transaction;
import app.banking.bankmuscat.merchant.util.constant.ErrorAndPopupCodes;

/**
 * Created by ADMIN on 11/26/2016.
 */

public class BMM2MConfirmation extends ActivityBase {


    private RobotoButton next,cancel;
    private String amount,mobile;
    private RobotoTextView amt,payee,mmobile,mcode;
    TextView headingTextView;

    @Override
    public int GetScreenLayout() {
        return R.layout.bm_m2m_confirm;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        headingTextView = (TextView) findViewById(R.id.title_header);
        headingTextView.setText(getResources().getString(R.string.menu_m2m));
        amount = getIntent().getExtras().getString("amount");
        mobile = getIntent().getExtras().getString("mobile");
        amt = (RobotoTextView) findViewById(R.id.amount);
        mmobile = (RobotoTextView) findViewById(R.id.mmobile);
        mcode = (RobotoTextView) findViewById(R.id.mcode);
        next = (RobotoButton) findViewById(R.id.mob_buttonnext);
        cancel = (RobotoButton) findViewById(R.id.mob_buttoncancel);
        amt.setText(getResources().getString(R.string.ro)+" "+amount);
        payee=(RobotoTextView) findViewById(R.id.payee);

        GetDetails(getIntent().getExtras().getString("mobile"));
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextAction();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CloseWin();
            }
        });
    }


    void nextAction()
    {
        Intent i = new Intent(this,BMEnterPin.class);
        i.putExtra("Mode",SEND_MONEY);
        i.putExtra("amount",getIntent().getExtras().getString("amount"));
        i.putExtra("mobile",getIntent().getExtras().getString("mobile"));
        CloseWin();
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
    }

    void CloseWin() {

        this.finish();
    }

    void GetDetails(String msisdn){

        if (!verifyInternetStatus()) {
            return;
        }
        showLoader();

        try {

            final UUID resultId = apiManager.GetUserInfo(msisdn);

            final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

                @Override
                public void handleMessage(APIManager.SOAPOperationData data) {
                    try {
                        if("RADRESP".equalsIgnoreCase(data.getResponseDataObj().getString("TYPE"))&&"200".equalsIgnoreCase(data.getResponseDataObj().getString("TXNSTATUS"))) {
                            apiManager.removeListener(this);
                            String message = data.getResponseDataObj().getString("MESSAGE");
                            payee.setText(payee.getText().toString().replaceAll("#payee",message.substring(message.indexOf(":")+2).split(" ")[0]));
                            mcode.setText(getResources().getString(R.string.merchant_code)+" : "+data.getResponseDataObj().getString("MERCHANTCODE") );
                            mmobile.setText(getResources().getString(R.string.mobile)+": "+mobile);
                            hideLoader();
                        }else{
                            showAlert(data.getResponseDataObj().getString("MESSAGE"));
                            payee.setText(mobile);
                            mcode.setText("");
                            mmobile.setText("");
                            apiManager.removeListener(this);
                            hideLoader();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        apiManager.removeListener(this);
                        hideLoader();
                        payee.setText(mobile);
                        mcode.setText("");
                        mmobile.setText("");
                    }
                }
            };
            apiManager.addListener(l);
        } catch (Exception e) {
            hideLoader();
        }

    }




}
