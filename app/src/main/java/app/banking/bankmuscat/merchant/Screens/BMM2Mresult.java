package app.banking.bankmuscat.merchant.Screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import app.banking.bankmuscat.R;
import app.banking.bankmuscat.merchant.base.ActivityBase;
import app.banking.bankmuscat.merchant.components.widgets.RobotoTextView;

public class BMM2Mresult extends ActivityBase {

    private TextView amount,payee,txnid,txndate;
    private ImageView backbutton;
    TextView headingTextView;
    @Override
    public void onBackPressed() {
    }
    @Override
    public int GetScreenLayout() {
        return R.layout.bm_m2m_result;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        headingTextView = (TextView) findViewById(R.id.title_header);
        headingTextView.setText(getResources().getString(R.string.menu_m2m));
        backbutton=(ImageView) findViewById(R.id.header_menu_button);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),BMHome.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
        payee=(TextView) findViewById(R.id.payee);
        amount=(TextView) findViewById(R.id.amount);
        txnid=(TextView) findViewById(R.id.txnid);
        txndate=(TextView) findViewById(R.id.txndate);
        payee.setText(getIntent().getExtras().getString("mobile"));
        amount.setText(getResources().getString(R.string.ro)+" "+getIntent().getExtras().getString("amount"));
        txnid.setText(getResources().getString(R.string.transactionid)+" : "+getIntent().getExtras().getString("txnid"));
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:aa");
        Calendar cal = Calendar.getInstance();
        txndate.setText(getResources().getString(R.string.transaction_date)+" : "+dateFormat.format(cal.getTime()));
    }
}
