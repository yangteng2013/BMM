package app.banking.bankmuscat.merchant.Screens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import app.banking.bankmuscat.R;
import app.banking.bankmuscat.merchant.api.APIManager;
import app.banking.bankmuscat.merchant.base.ActivityBase;
import app.banking.bankmuscat.merchant.common.Wallet;
import app.banking.bankmuscat.merchant.interfaces.ILoader;


//import com.google.i18n.phonenumbers.NumberParseException;
//import com.google.i18n.phonenumbers.PhoneNumberUtil;
//import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
//import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class BMQRScanDetails extends ActivityBase implements ILoader {

	private static final String TAG = "BMQRScanDetails";
	private final APIManager apiManager = APIManager.createInstance(null);
	private Wallet.Data appData = Wallet.Data.getInstance();


	public ImageView btnBack, header_home, header_menu_button;
	TextView header_title;
	TextView txt_store, txt_total;
	Button next, cancel;

//	String transactionID, amount, date;

	@Override
	public int GetScreenLayout() {
		return R.layout.bm_qr_scan_details;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/*amount = getIntent().getExtras().getString("Amount");
		transactionID = getIntent().getExtras().getString("TransactionID");
		date = getIntent().getExtras().getString("Date");*/

		//setContentView(R.layout.bm_qr_scan_details);
		btnBack = (ImageView) findViewById(R.id.header_back_button);
		next = (Button) findViewById(R.id.mob_buttonnext);
		cancel = (Button) findViewById(R.id.mob_buttoncancel);

		txt_store = (TextView) findViewById(R.id.txt_store);
		txt_total = (TextView) findViewById(R.id.txt_total);

	//	txt_total.setText(appData.getCurrentTransaction().getAmount());

		header_home = (ImageView) findViewById(R.id.header_home);
		header_menu_button = (ImageView) findViewById(R.id.header_menu_button);
		header_title = (TextView) findViewById(R.id.title_header);

		header_title.setText(getResources().getString(R.string.home_qrscan_for_merchant_pay));

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				CloseWin();
			}
		});

		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				NextAction();
			}
		});

		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				CloseWin();

			}

		});

	}

	void CloseWin() {
/*hideSoftKeyboard();
				NavUtils.navigateUpTo(BMEnterEmail.this, NavUtils
						.getParentActivityIntent(BMEnterEmail.this));*/
		this.finish();
	}

	void NextAction() {
		Intent i = new Intent(getApplicationContext(),
				BMQRScanResult.class);
		// i.putExtra("Registration", "Registration");
		CloseWin();
		startActivity(i);
	}


	void SelectDebit() {

	}



	@Override
	public boolean onTouchEvent(MotionEvent event) {
		hideSoftKeyboard();
		return false;
	}

	void hideSoftKeyboard() {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

	}

	@Override
	public void onBackPressed() {
		/*hideSoftKeyboard();
		NavUtils.navigateUpTo(BMLoadSVNResult.this, NavUtils.getParentActivityIntent(BMLoadSVNResult.this));*/
	}
}
