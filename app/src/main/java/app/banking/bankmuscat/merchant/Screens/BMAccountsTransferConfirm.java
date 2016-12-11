package app.banking.bankmuscat.merchant.Screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import app.banking.bankmuscat.*;
import app.banking.bankmuscat.merchant.base.ActivityBase;
import app.banking.bankmuscat.merchant.components.widgets.RobotoButton;
import app.banking.bankmuscat.merchant.components.widgets.RobotoTextView;

public class BMAccountsTransferConfirm extends ActivityBase {
    private RobotoButton cancel,next;
    private RobotoTextView amount,reason;
    @Override
    public int GetScreenLayout() {
        return R.layout.bm_accounts_confirm;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        amount =(RobotoTextView) findViewById(R.id.title3);
        reason =(RobotoTextView) findViewById(R.id.reason);
        amount.setText("OMR "+appData.getCurrentTransaction().getAmount());
        reason.setText(appData.getCurrentTransaction().getDescription());
        cancel = (RobotoButton) findViewById(R.id.mob_buttoncancel);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                CloseWin();
            }
        });
        next = (RobotoButton) findViewById(R.id.mob_buttonnext);
        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                nextAction();
            }
        });
    }


    void CloseWin()
    {
        this.finish();
    }

    void nextAction()
    {
        Intent i = new Intent(this,BMEnterPin.class);
        i.putExtra("Mode",SEND_BANK);
        startActivity(i);
    }
}
