package app.banking.bankmuscat.merchant.Screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import app.banking.bankmuscat.R;
import app.banking.bankmuscat.merchant.base.ActivityBase;
import app.banking.bankmuscat.merchant.components.widgets.RobotoButton;
import app.banking.bankmuscat.merchant.components.widgets.RobotoEditText;
import app.banking.bankmuscat.merchant.components.widgets.RobotoTextView;

public class BMCollectPayment extends ActivityBase {


    private RobotoButton generate;
    private EditText amount,invoice;
    TextView headingTextView;
    @Override
    public int GetScreenLayout() {
        return R.layout.bmcollect_payment;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        headingTextView = (TextView) findViewById(R.id.title_header);
        headingTextView.setText(getResources().getString(R.string.collect_payment));
        amount=(EditText) findViewById(R.id.amount);
        invoice=(EditText) findViewById(R.id.invoice);
        generate =(RobotoButton) findViewById(R.id.generate);
        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(amount.getText().toString()==null)
                {
                   showAlert(getResources().getString(R.string.invalidwamt));
                }
                else if(amount.getText().toString().length()==0)
                {
                    showAlert(getResources().getString(R.string.invalidwamt));
                }
                else if(Integer.parseInt(amount.getText().toString())==0)
                {
                    showAlert(getResources().getString(R.string.invalidwamt));
                }
                else
                {
                    /*Intent i = new Intent(getBaseContext(),BMEnterPin.class);
                    i.putExtra("Mode",COLLECT_PAYMENT);
                    i.putExtra("amount", amount.getText().toString());
                    i.putExtra("invoice", invoice.getText().toString());
                    startActivity(i);*/

                    Intent i = new Intent(getBaseContext(), GenerateQRCodeActivity.class);
                    i.putExtra("amount",amount.getText().toString());
                    i.putExtra("invoice",invoice.getText().toString());
                    i.putExtra("txnid","TXNTST864826428482");
                    i.putExtra("ivrResponse",appData.getMsisdn()+":"+amount.getText().toString()+":"+invoice.getText().toString() );
                    finish();
                    startActivity(i);
                }
            }
        });
    }
}
