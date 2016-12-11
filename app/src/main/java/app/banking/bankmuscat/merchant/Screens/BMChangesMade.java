package app.banking.bankmuscat.merchant.Screens;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
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

public class BMChangesMade extends ActivityBase implements ILoader {

	private static final String TAG = "BMRegisterUser";
	private final APIManager apiManager = APIManager.createInstance(null);
	private Wallet.Data appData = Wallet.Data.getInstance();

	public ImageView btnBack, header_home, header_menu_button;
	TextView headingTextView, mobtxttitle, mobtxtsubtitle;

	String title;
	Button ok;

	@Override
	public int GetScreenLayout() {
		return R.layout.bm_changes_made;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.bm_changes_made);



		title = getIntent().getExtras().getString("Title");
		String subTitle = getIntent().getExtras().getString("SubtTitle");

		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		btnBack = (ImageView) findViewById(R.id.header_back_button);
		ok = (Button) findViewById(R.id.mob_buttonok);

		if(title.compareToIgnoreCase(getResources().getString(R.string.changepin)) == 0) {
			RemoveHeaderButtons();
			btnBack.setVisibility(View.INVISIBLE);
		}

		mobtxttitle = (TextView) findViewById(R.id.mobtxttitle);
		mobtxtsubtitle = (TextView) findViewById(R.id.mobtxtsubtitle);

		mobtxttitle.setText(title);
		mobtxtsubtitle.setText(subTitle);

		header_home = (ImageView) findViewById(R.id.header_home);
		header_menu_button = (ImageView) findViewById(R.id.header_menu_button);

		headingTextView = (TextView) findViewById(R.id.title_header);
		headingTextView.setText(getResources().getString(R.string.success));

		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				nextAction();
			}
		});

		/*edtpin1.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView arg0, int arg1, KeyEvent arg2) {

				edtpin2.requestFocus();
				getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);

				return false;
			}

		});*/




		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
			/*	hideSoftKeyboard();
				NavUtils.navigateUpTo(BMChangesMade.this, NavUtils
						.getParentActivityIntent(BMChangesMade.this));*/

				ShowProfile();
				//CloseWin();

			}

		});

	}

	void CloseWin() {

		this.finish();
	}

	void nextAction() {


		/* if (mobedtanswer2.getText().toString().trim().isEmpty()) {

			showAlert("Please enter answer");
		}


		else {*/


				if (!verifyInternetStatus()) {
					return;
				} else {

					/*TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
					String imei = telephonyManager.getDeviceId();
					BMRegisterUser.this.validateMSISDN(mobileNumber,
							null != imei ? imei : mobileNumber);*/

					if(title.compareToIgnoreCase(getResources().getString(R.string.changepin)) == 0)
						Logout();
					else
					{
						ShowProfile();
						//this.finish();
						}

					//Intent i = new Intent(getApplicationContext(), BMProfile.class);
					//startActivity(i);
				}
		//	}

	}

	


	

	@Override	
	public boolean onTouchEvent(MotionEvent event) {
		hideSoftKeyboard();
		return false;
	}

	void hideSoftKeyboard() {

	}

/*	@Override
	public void onBackPressed() {
		hideSoftKeyboard();
		NavUtils.navigateUpTo(BMChangesMade.this, NavUtils.getParentActivityIntent(BMChangesMade.this));
	}*/
}
