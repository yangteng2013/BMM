package app.banking.bankmuscat.merchant.Screens;

/**
 * Created by ADMIN on 11/25/2016.
 */

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import app.banking.bankmuscat.R;
import app.banking.bankmuscat.merchant.base.ActivityBase;
import app.banking.bankmuscat.merchant.components.widgets.RobotoTextView;
import app.banking.bankmuscat.merchant.util.Contents;
import app.banking.bankmuscat.merchant.util.QRCodeEncoder;

public class GenerateQRCodeActivity extends ActivityBase{

    private String LOG_TAG = "GenerateQRCode";
    private RobotoTextView amount,txnid;
    TextView headingTextView;


    @Override
    public int GetScreenLayout() {
        return R.layout.qr;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        amount=(RobotoTextView) findViewById(R.id.amount);
        txnid=(RobotoTextView) findViewById(R.id.txnid);
        headingTextView = (TextView) findViewById(R.id.title_header);
        headingTextView.setText(getResources().getString(R.string.generate_qr));
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        String qrInputText = getIntent().getExtras().getString("ivrResponse");
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        smallerDimension = smallerDimension * 3/4;

        //Encode with a QR Code image
        QRCodeEncoder qrCodeEncoder = new QRCodeEncoder(qrInputText,
                null,
                Contents.Type.TEXT,
                BarcodeFormat.QR_CODE.toString(),
                smallerDimension);
        try {
            amount.setText(getResources().getString(R.string.ro)+getIntent().getExtras().getString("amount"));
            txnid.setText(getIntent().getExtras().getString("ivrResponse"));
            Bitmap bitmap = qrCodeEncoder.encodeAsBitmap();
            ImageView myImage = (ImageView) findViewById(R.id.imageView1);
            myImage.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
            showAlert(e.getMessage());
        }


    }



}