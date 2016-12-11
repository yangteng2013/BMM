package app.banking.bankmuscat.merchant.Screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import app.banking.bankmuscat.R;
import app.banking.bankmuscat.merchant.base.ActivityBase;
import app.banking.bankmuscat.merchant.components.widgets.RobotoButton;
import app.banking.bankmuscat.merchant.components.widgets.RobotoEditText;
import app.banking.bankmuscat.merchant.components.widgets.RobotoTextView;
import app.banking.bankmuscat.merchant.entity.instrument.Transaction;

public class BMAccountsDetail extends ActivityBase {
    private RobotoButton next,cancel;
    private RobotoEditText amount,reason;

    @Override
    public int GetScreenLayout() {
        return R.layout.bm_accounts_details;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);*/
        amount = (RobotoEditText) findViewById(R.id.amount);
        reason = (RobotoEditText) findViewById(R.id.reason);
        next= (RobotoButton) findViewById(R.id.mob_buttonnext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextAction();
            }
        });
        cancel = (RobotoButton) findViewById(R.id.mob_buttoncancel);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                CloseWin();
            }
        });
    }

    void CloseWin()
    {
        this.finish();
    }

    void nextAction()
    {
        if(amount.getText().toString()==null)
        {
            showAlert("Please Enter a valid amount !!");
        }
        else if(amount.getText().toString().length()==0)
        {
            showAlert("Please Enter a valid amount !!");
        }
        else if(Integer.parseInt(amount.getText().toString())==0)
        {
            showAlert("Please Enter a valid amount !!");
        }
        else {
            Transaction txn = new Transaction();
            txn.setAmount(amount.getText().toString());
            txn.setDescription(reason.getText()!=null?reason.getText().toString():"Transfer to Account");
            appData.setCurrentTransaction(txn);
            Intent i = new Intent(getBaseContext(), BMAccountsTransferConfirm.class);
            startActivity(i);
        }
    }
}
