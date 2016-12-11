package app.banking.bankmuscat.merchant.Screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.UUID;

import app.banking.bankmuscat.R;
import app.banking.bankmuscat.merchant.api.APIManager;
import app.banking.bankmuscat.merchant.base.ActivityBase;
import app.banking.bankmuscat.merchant.components.widgets.RobotoButton;
import app.banking.bankmuscat.merchant.components.widgets.RobotoEditText;
import app.banking.bankmuscat.merchant.entity.instrument.Transaction;

/**
 * Created by ADMIN on 11/26/2016.
 */

public class BMMerchantM2M extends ActivityBase {

    private RobotoEditText mobile,amount;
    private RobotoButton next,cancel;
    TextView headingTextView;

    @Override
    public int GetScreenLayout() {
        return R.layout.bm_m2m;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        amount=(RobotoEditText) findViewById(R.id.amount);
        mobile=(RobotoEditText) findViewById(R.id.mobile);
        next=(RobotoButton) findViewById(R.id.mob_buttonnext);
        headingTextView = (TextView) findViewById(R.id.title_header);
        headingTextView.setText(getResources().getString(R.string.menu_m2m));
        cancel=(RobotoButton) findViewById(R.id.mob_buttoncancel);
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

    void CloseWin() {

        this.finish();
    }

    void nextAction() {

        Transaction transactionData = new Transaction();
        amount=(RobotoEditText) findViewById(R.id.amount);
        if(amount.getText()==null)
        {
            showAlert(getResources().getString(R.string.enteramount));
        }else if(mobile.getText()==null)
        {
            showAlert(getResources().getString(R.string.enter_mobile_num));
        }
        else {
            String amt = amount.getText().toString();
            transactionData.setPayeeMobileNumber(mobile.getText().toString());
            transactionData.setEmail("");
            transactionData.setAmount(amt);
            appData.setCurrentTransaction(transactionData);
        }
        int amountValue = 0;
        int balanceValue = 0;

        try {
            amountValue = NumberFormat.getInstance().parse(transactionData.amount.toString()).intValue();
            balanceValue = NumberFormat.getInstance().parse(appData.getUserData().balance.toString()).intValue() - 1; ///to be corrected
        } catch (Exception e) {
            String msg = e.getMessage();
        }
            if (transactionData.getPayeeMobileNumber().toString().trim().length() == 0) {
                mobile.requestFocus();
                showAlert(getResources().getString(R.string.invalidmoblen));
            } else if (transactionData.getPayeeMobileNumber().toString().trim().length() < 8) {
                mobile.requestFocus();
                showAlert(getResources().getString(R.string.invalidmoblen));
            }

            else if (transactionData.getPayeeMobileNumber().substring(0, 1).equalsIgnoreCase("0") || transactionData.getPayeeMobileNumber().substring(0, 1).equalsIgnoreCase("2")) {//else if (!mobileNumber.substring(0, 1).equalsIgnoreCase("9") && !mobileNumber.substring(0, 1).equalsIgnoreCase("7")) {
                mobile.requestFocus();
                showAlert(getResources().getString(R.string.invalidmobstr));
            }
            else if(transactionData.amount.toString().length()==0){
                amount.requestFocus();

                showAlert(getResources().getString(R.string.invalidamt));
            }

           else if (transactionData.getPayeeMobileNumber().toString().trim().compareToIgnoreCase(appData.getMsisdn()) == 0) {
                mobile.requestFocus();
                showAlert(getResources().getString(R.string.own_mob));
            }else if (transactionData.amount.toString().trim().compareToIgnoreCase("0.00") == 0) {

                amount.requestFocus();

                showAlert(getResources().getString(R.string.invalidmobstr));
            }else if (Integer.parseInt(transactionData.amount.toString()) > appData.getSvnBalance()) {

                amount.requestFocus();
                showAlert(getResources().getString(R.string.insuff_bal));
            } else if (Integer.parseInt(transactionData.amount.toString()) == 0) {

                amount.requestFocus();
                showAlert(getResources().getString(R.string.invalidmobstr));
            }else if (transactionData.amount.toString().trim().length() == 0) {

                amount.requestFocus();

                showAlert(getResources().getString(R.string.invalidmobstr));
            } else {
                GetDetails(transactionData.getPayeeMobileNumber(),transactionData.amount.toString());

            }

    }

    void SendMoney(String amount,String mobile)
    {
        Intent i = new Intent(getApplicationContext(), BMM2MConfirmation.class);
        i.putExtra("amount",amount);
        i.putExtra("mobile",mobile);
        startActivity(i);
    }

    void GetDetails(final String msisdn, final String amount){

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
                            SendMoney(amount,msisdn);
                            hideLoader();
                        }else{
                            showAlert("Merchant is not registered");
                                                 apiManager.removeListener(this);
                            hideLoader();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        apiManager.removeListener(this);
                        SendMoney(amount,msisdn);
                        hideLoader();
                    }
                }
            };
            apiManager.addListener(l);
        } catch (Exception e) {
            hideLoader();
            SendMoney(amount,msisdn);
        }

    }




}
