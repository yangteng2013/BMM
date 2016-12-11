package app.banking.bankmuscat.merchant.Screens;

import android.os.Bundle;
import android.view.WindowManager;

import app.banking.bankmuscat.merchant.base.ActivityBase;

public class BMError extends ActivityBase {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public int GetScreenLayout() {
        return 0;
    }

}
