package app.banking.bankmuscat.merchant.Screens;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import app.banking.bankmuscat.R;


//import com.google.i18n.phonenumbers.NumberParseException;
//import com.google.i18n.phonenumbers.PhoneNumberUtil;
//import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
//import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class BMLoadImage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bm_load_image);

        int mode =  getIntent().getExtras().getInt("Mode");

        RelativeLayout lytGallery, lytCamera, LytRemove;
        TextView txtcancel= (TextView) findViewById(R.id.txtcancel);
        txtcancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
               CloseWin();
            }
        });


        lytGallery = (RelativeLayout) findViewById(R.id.lytgallery);
        lytGallery.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                nextAction(1);
            }
        });

        lytCamera = (RelativeLayout) findViewById(R.id.lytcamera);
        lytCamera.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                nextAction(2);
            }
        });

        LytRemove = (RelativeLayout) findViewById(R.id.lytremove);
        LytRemove.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                nextAction(3);
            }
        });

        if( mode == 10)
        {
            LytRemove.setVisibility(View.GONE);
        }

        ImageView imgGallery, imgCamera, imgRemove;


        imgGallery = (ImageView) findViewById(R.id.imggallery);
        imgGallery.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                nextAction(1);
            }
        });

        imgCamera = (ImageView) findViewById(R.id.imgcamera);
        imgCamera.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                nextAction(2);
            }
        });

        imgRemove = (ImageView) findViewById(R.id.imgremove);
        imgRemove.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                nextAction(3);
            }
        });

    }

    void CloseWin() {
/*hideSoftKeyboard();
				NavUtils.navigateUpTo(BMForgotPin.this, NavUtils
						.getParentActivityIntent(BMForgotPin.this));*/
        this.finish();
    }

    void nextAction(int mode) {


       // getIntent().putExtra("Selection", mode);
        this.setResult(RESULT_OK, getIntent().putExtra("Selection", mode));
        this.finish();
    }

}
