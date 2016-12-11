package app.banking.bankmuscat.merchant.Screens;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

import app.banking.bankmuscat.R;
import app.banking.bankmuscat.merchant.base.ActivityBase;

public class BMTransferBankresult extends ActivityBase {


    @Override
    public int GetScreenLayout() {
        return R.layout.bm_transfer_bank_result;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
}
