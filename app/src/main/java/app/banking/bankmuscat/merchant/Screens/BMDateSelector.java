package app.banking.bankmuscat.merchant.Screens;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import app.banking.bankmuscat.R;
import app.banking.bankmuscat.merchant.api.APIManager;
import app.banking.bankmuscat.merchant.base.ActivityBase;
import app.banking.bankmuscat.merchant.common.Wallet;
import app.banking.bankmuscat.merchant.interfaces.ILoader;


/**
 * Created by ADMIN on 10/19/2016.
 */

public class BMDateSelector  extends ActivityBase implements ILoader {

    private final APIManager apiManager = APIManager.createInstance(null);
    private Wallet.Data appData = Wallet.Data.getInstance();

    @Override
    public int GetScreenLayout() {
        return R.layout.date_selector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.date_selector);

        ImageView img = (ImageView) findViewById(R.id.imageView12);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                System.out.println("Clicked");
                ShowDatePicker("1");

            }
        });

        ImageView img2 = (ImageView) findViewById(R.id.imageView18);
        img2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                System.out.println("Clicked");
                ShowDatePicker("2");

            }
        });

        /*RobotoButton rb = (RobotoButton ) findViewById(R.id.mob_buttonconfirm);
        rb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                System.out.println("Clicked");
                Intent i = new Intent(getApplicationContext(), BMTransactions.class);
                startActivity(i);

            }
        });*/
    }





    void ShowDatePicker(final String option) {
        DatePickerDialog toDatePickerDialog;

        final SimpleDateFormat dateFormatter;
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            TextView v1;
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if(option.equalsIgnoreCase("1")) {
                    v1 = (TextView) findViewById(R.id.startDate);
                    v1.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                    System.out.println("selected date: " + dayOfMonth + "/" + monthOfYear + "/" + year);
                }else{
                    v1 = (TextView) findViewById(R.id.endDate);
                    v1.setText(dayOfMonth + "/" + monthOfYear + "/" + year);
                    System.out.println("selected date: " + dayOfMonth + "/" + monthOfYear + "/" + year);
                }
            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        fromDatePickerDialog.show();
        fromDatePickerDialog.setCanceledOnTouchOutside(false);

    }


}
