package app.banking.bankmuscat.merchant.Screens;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.UUID;

import app.banking.bankmuscat.R;
import app.banking.bankmuscat.merchant.api.APIManager;
import app.banking.bankmuscat.merchant.base.ActivityBase;
import app.banking.bankmuscat.merchant.common.Wallet;
import app.banking.bankmuscat.merchant.components.widgets.RobotoTextView;
import app.banking.bankmuscat.merchant.db.DbManager;
import app.banking.bankmuscat.merchant.interfaces.ILoader;
import app.banking.bankmuscat.merchant.logger.CLog;
import app.banking.bankmuscat.merchant.util.constant.ErrorAndPopupCodes;

public class BMForgotPin extends ActivityBase implements ILoader {

	private static final String TAG = "BMForgotPin";
	private final APIManager apiManager = APIManager.createInstance(null);
	private Wallet.Data appData = Wallet.Data.getInstance();
	Integer qid1,qid2;
	public ImageView btnBack, header_home, header_menu_button;
	TextView headingTextView;
	Button next, cancel;
	String mobileNumber;
	public EditText mobedtanswer,mobedtanswer2;
	RobotoTextView ques1,ques2;


	@Override
	public int GetScreenLayout() {
		return R.layout.bm_forgot_pin;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		RemoveHeaderButtons();
		ques1=(RobotoTextView) findViewById(R.id.spn_ques);
		ques2=(RobotoTextView) findViewById(R.id.spn_ques2);
		mobedtanswer = (EditText) findViewById(R.id.mobedtanswer);
		mobedtanswer2 = (EditText) findViewById(R.id.mobedtanswer2);
		mobileNumber = getIntent().getExtras().getString("Number");
		btnBack = (ImageView) findViewById(R.id.header_back_button);
		next = (Button) findViewById(R.id.mob_buttonnext);
		cancel = (Button) findViewById(R.id.mob_buttoncancel);
		header_home = (ImageView) findViewById(R.id.header_home);
		header_menu_button = (ImageView) findViewById(R.id.header_menu_button);
		headingTextView = (TextView) findViewById(R.id.title_header);
		headingTextView.setText(getResources().getString(R.string.forgot_pin_text));
		getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				nextAction();
			}
		});
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Logout();
			}
		});
		loadQuestions();
		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Logout();

			}

		});
	}

	@Override
	public void onBackPressed() {
		Logout();
	}

	void CloseWin() {
		this.finish();
	}
	void nextAction() {

		if (mobedtanswer.getText().toString().trim().isEmpty() || mobedtanswer2.getText().toString().trim().isEmpty()) {

			showAlert("Please enter answers to both the questions");
		}else{
			String answer1= mobedtanswer.getText().toString();
			String answer2= mobedtanswer2.getText().toString();
			Validate();
		}

	}
	void ValidateSecond(){

		if (!verifyInternetStatus()) {
			return;
		}
		showLoader();
		DbManager dbManager = DbManager.createAuto(BMForgotPin.this);
		Wallet.Data.syncRead(getApplicationContext());

		try {
			final UUID resultId = apiManager.ValidateAnswer(getIntent().getExtras().getString("Number"),qid2.toString(),mobedtanswer2.getText().toString());

			final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

				@Override
				public void handleMessage(APIManager.SOAPOperationData data) {
					try {

						if (data.getId() == resultId) {
							if("VALANSRESP".equalsIgnoreCase((String)data.getResponseDataObj().get("TYPE")) && "200".equalsIgnoreCase((String)data.getResponseDataObj().get("TXNSTATUS"))) {
								apiManager.removeListener(this);
								hideLoader();
								System.out.println(data.getResponseDataObj().get("TRID").toString()+":::"+data.getResponseDataObj().get("MESSAGE").toString());
								Intent i = new Intent(getBaseContext(),BMResetPin.class);
								i.putExtra("Number",getIntent().getExtras().getString("Number"));
								startActivity(i);
							}else
							{
								apiManager.removeListener(this);
								hideLoader();
								showAlert("Please Enter the correct answers");
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						hideLoader();
						new AlertDialog.Builder(BMForgotPin.this)
								.setTitle("Error")
								.setMessage(ErrorAndPopupCodes.No_Response.getTag())
								.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {

										ErrorAction();
									}
								})
								.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog, int which) {

									}
								})
								.show();

					}
				}
			};
			apiManager.addListener(l);
		} catch (Exception e) {
			hideLoader();
		}

	}
	void Validate(){

		if (!verifyInternetStatus()) {
			return;
		}
		showLoader();
		DbManager dbManager = DbManager.createAuto(BMForgotPin.this);
		Wallet.Data.syncRead(getApplicationContext());

		try {
			final UUID resultId = apiManager.ValidateAnswer(getIntent().getExtras().getString("Number"),qid1.toString(),mobedtanswer.getText().toString());

			final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

				@Override
				public void handleMessage(APIManager.SOAPOperationData data) {
					try {

						if (data.getId() == resultId) {
							if("VALANSRESP".equalsIgnoreCase((String)data.getResponseDataObj().get("TYPE")) && "200".equalsIgnoreCase((String)data.getResponseDataObj().get("TXNSTATUS"))) {
								apiManager.removeListener(this);
								hideLoader();
								System.out.println(data.getResponseDataObj().get("TRID").toString()+":::"+data.getResponseDataObj().get("MESSAGE").toString());
								ValidateSecond();
							}else
							{
								apiManager.removeListener(this);
								hideLoader();
								showAlert("Please Enter the correct answers");
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						hideLoader();
						new AlertDialog.Builder(BMForgotPin.this)
								.setTitle("Error")
								.setMessage(ErrorAndPopupCodes.No_Response.getTag())
								.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {

										ErrorAction();
									}
								})
								.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog, int which) {

									}
								})
								.show();
					}
				}
			};
			apiManager.addListener(l);
		} catch (Exception e) {
			hideLoader();
		}
	}

	void loadQuestions(){
		if (!verifyInternetStatus()) {
			return;
		}
		showLoader();
		DbManager dbManager = DbManager.createAuto(BMForgotPin.this);

		dbManager.setMsisdn(getIntent().getExtras().getString("Number"));

		Wallet.Data.syncRead(getApplicationContext());

		try {
			final UUID resultId = apiManager.BMGetSecurityQuestions(getIntent().getExtras().getString("Number"));

			final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

				@Override
				public void handleMessage(APIManager.SOAPOperationData data) {
					try {

						if (data.getId() == resultId) {
							if("GETANSRESP".equalsIgnoreCase((String)data.getResponseDataObj().get("TYPE")) && "200".equalsIgnoreCase((String)data.getResponseDataObj().get("TXNSTATUS"))) {
								apiManager.removeListener(this);
								hideLoader();
								CLog.i(TAG, "User login success");
								String questions = (String) data.getResponseDataObj().get("DATA");
								org.json.JSONObject qobject = new org.json.JSONObject(questions);
								org.json.JSONArray qarray = (org.json.JSONArray) qobject.get("QUESTION");
								org.json.JSONObject firstq = (org.json.JSONObject) qarray.get(0);
								ques1.setText((String)firstq.get("QUESTION"));
								qid1=(Integer)firstq.get("QNSCODE");
								org.json.JSONObject secondq = (org.json.JSONObject) qarray.get(1);
								ques2.setText((String)secondq.get("QUESTION"));
								qid2=(Integer)secondq.get("QNSCODE");


							}else
							{
								apiManager.removeListener(this);
								hideLoader();
								CLog.i(TAG, "User login failure");
								CloseWin();
								showAlert((String)data.getResponseDataObj().get("MESSAGE"));

							}
						}
					} catch (Exception e) {
						e.printStackTrace();
						hideLoader();
						new AlertDialog.Builder(BMForgotPin.this)
								.setTitle("Error")
								.setMessage(ErrorAndPopupCodes.No_Response.getTag())
								.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog, int which) {

										ErrorAction();
									}
								})
								.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

									@Override
									public void onClick(DialogInterface dialog, int which) {

									}
								})
								.show();

					}
				}
			};
			apiManager.addListener(l);
		} catch (Exception e) {
			hideLoader();
		}
	}
}
