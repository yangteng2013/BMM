package app.banking.bankmuscat.merchant.Screens;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.nfc.NfcAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.apache.http.HttpConnection;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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

import static app.banking.bankmuscat.merchant.Screens.BMProfile.getRoundedCroppedBitmap;

//import com.comviva.wallet.OldScreens.BleWrapper;


public class BMHome extends ActivityBase implements OnClickListener,ILoader {

	private CharSequence mTitle;

	private static Boolean tag = true;
	private static Boolean appstarted = true;
	private final APIManager apiManager = APIManager.createInstance(null);
	private static String unReadtext ="";
	private SharedPreferences sharedPreferences;
	private SharedPreferences socialHandleSharedPreferences;
	UnreadNotification unReadNotification = null;

	// HCE Variable Starts
	private HttpConnection background;
	private SharedPreferences cardPresence;
	private ProgressDialog connection = null;
	private ImageView notification, back, home, favorite,splitbill;
	private TextView unReadCount, balance;


	private LinearLayout userbalance_layout,collect,bank;
	//private TextView nfcText;
	public static final String KEY_SOCIAL = "SocialHandle";
	//private BleWrapper ble = null;

	ListView list;
	private RelativeLayout mywallet, favoritetransaction, loadmoney, sendmoney, paybill, tranfertobank, requestmoney,qrscan;
ImageView img_profilepic;

	private NfcAdapter mNfcAdapter;
	public static boolean isBLEnabled = true;

	public static boolean isBLEnabled() {
		return isBLEnabled;
	}
	public static void setBLEnabled(boolean isBLEnabled) {
		BMHome.isBLEnabled = isBLEnabled;
	}
	public static Boolean getStarted() {
		return appstarted;
	}
	public static void setStarted(Boolean started) {
		BMHome.appstarted = started;
	}

	@Override
	public int GetScreenLayout() {
		return R.layout.bm_home;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);


		/*UserQuestions(appData.getMsisdn());*/
		initLayout();
		onNotificationClicked();
		getSocialHandleStatus();
		balance = (RobotoTextView) findViewById(R.id.txt_userbalanceamnt);
		GetBalance();
		collect=(LinearLayout) findViewById(R.id.collect_layout);
		collect.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),BMCollectPayment.class);
				startActivity(i);
			}
		});
		bank=(LinearLayout) findViewById(R.id.bank);
		bank.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(),BMmove2bank.class);
				startActivity(i);
			}
		});
		mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
		connection = new ProgressDialog(this);
		if (getStarted()) {
			SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(BMHome.this);
			Editor edit = sp.edit();
			edit.putBoolean("CHECKBOX6", true);
			edit.commit();
		}

		/*img_profilepic = (ImageView) findViewById(R.id.img_profilepic);

		img_profilepic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ShowProfile();
			}

		});*/
		
/*		mywallet = (RelativeLayout) findViewById(R.id.mywallet);
		favoritetransaction = (RelativeLayout) findViewById(R.id.favoritetransaction);*/

		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		/*nfcText = (TextView) findViewById(R.id.nfc_textView3);
		if (mNfcAdapter == null) {
			nfcText.setText("NFC OFF");
		} else if (!mNfcAdapter.isEnabled()) {

			nfcText.setText("NFC OFF");
		} else {

			nfcText.setText("NFC ON");
		}*/
		/*ButtonNfc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}

		});
		scanQR.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getApplicationContext(),ScanQrTabView.class);
				startActivity(intent);
			}
		});
		transfer.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(getApplicationContext(), TransferHome_New.class);
				startActivity(intent);
			}
		});*/


	}




	private void onNotificationClicked() {



	}

	boolean isFirtstTime =  false;

	@Override
	protected void onResume() {
		super.onResume();
		GetBalance();
	}

	private void getSocialHandleStatus() {
		/*socialHandleSharedPreferences = getSharedPreferences(KEY_SOCIAL, MODE_PRIVATE);
		if(socialHandleSharedPreferences.getString("ResponseTwitter","Pending").equals("Pending") && (socialHandleSharedPreferences.getString("ResponseFacebook","Pending").equals("Pending"))) {
			Thread t = new Thread(new Runnable() {
				public void run() {
					apiManager.GetUserDetails(BMHome.this);
				}
			});
			t.start();
		}*/
	}

	/*public void callBeacon() {
		handleBle(BMHome.this, 0);
	}*/

	public boolean hasPermission(String permission) {
		try {
			PackageInfo info = getPackageManager().getPackageInfo(
					this.getPackageName(), PackageManager.GET_PERMISSIONS);
			if (info.requestedPermissions != null) {
				for (String p : info.requestedPermissions) {
					if (p.equals(permission)) {
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}




	public static Boolean getTag() {
		return tag;
	}
	public static void setTag(Boolean tag) {
		BMHome.tag = tag;
	}
	/*public void handleBle(Context con, int state) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
			switch (state) {
				case 0:
					ble = new BleWrapper(con);
					ble.startBLEService();
					break;

				case 1:
					if (null != ble) {
						ble.StartService();
					}
					break;

				case 2:
					if (null != ble) {
						ble.StopService();
					}
					break;
			}
		}
	}*/

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		super.onActivityResult(requestCode, resultCode, intent);
		if (requestCode == 0) {
			if (resultCode == RESULT_OK) {

				String contents = intent.getStringExtra("SCAN_RESULT");
				String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
				// Handle successful scan
				showAlert("Content: " + contents);
				CLog.i("Scan", contents);
			} else if (resultCode == RESULT_CANCELED) {
				// Handle cancel
				showAlert("Scan unsuccessful");
				CLog.i("App", "Scan unsuccessful");
			}
		}
		if (requestCode == 1) {
			if (resultCode == RESULT_OK) {
			}else if (resultCode == RESULT_CANCELED) {
				SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(BMHome.this);
				Editor edit = sp.edit();
				edit.putBoolean("CHECKBOX6", false);
				edit.commit();
			}
		}
	}

	private void initLayout() {
	//	addToWallet = (RelativeLayout) findViewById(R.id.addtowallet_layout);
		unReadCount = (TextView)findViewById(R.id.count);
		notification = (ImageView)findViewById(R.id.buttonNotification);
		back = (ImageView)findViewById(R.id.header_back_button);
		back.setVisibility(View.GONE);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
			//	Logout();

			//	CloseWin();
			}

		});
		//home = (ImageView)findViewById(R.id.buttonhome);
		userbalance_layout = (LinearLayout) findViewById(R.id.userbalance_layout);

		balance = (TextView)findViewById(R.id.txt_userbalanceamnt);
		splitbill = (ImageView) findViewById(R.id.imageView2);

/*		loadmoney = (RelativeLayout) findViewById(R.id.loadmoney_layout);
		sendmoney = (RelativeLayout) findViewById(R.id.sendmoney_layout);
		paybill = (RelativeLayout) findViewById(R.id.paybill_layout);
		tranfertobank = (RelativeLayout) findViewById(R.id.tranfertobank_layout);
		requestmoney = (RelativeLayout) findViewById(R.id.requestmoney_layout);
		qrscan = (RelativeLayout) findViewById(R.id.qrscan_layout);*/
/*
		favorite=(ImageView)findViewById(R.id.imageView4);
		loadmoney.setOnClickListener(this);
		sendmoney.setOnClickListener(this);
		paybill.setOnClickListener(this);
		tranfertobank.setOnClickListener(this);
		requestmoney.setOnClickListener(this);
		qrscan.setOnClickListener(this);
		favorite.setOnClickListener(this);
		splitbill.setOnClickListener(this);
*/

		socialHandleSharedPreferences =  getSharedPreferences(KEY_SOCIAL,MODE_PRIVATE);
		unReadNotification = new UnreadNotification();
		//unReadNotification.execute();
	}

	@Override
	public void onClick(View v) {
		/*switch (v.getId()) {
			case R.id.addtowallet_layout:
				Intent addIntent = new Intent(getApplicationContext(), TabView.class);
				addIntent.putExtra("Activity", "TabView");
				startActivity(addIntent);
				break;

			case R.id.market_layout:
				Intent marketIntent = new Intent(getApplicationContext(), Market.class);
				marketIntent.putExtra("Activity", "TabView");
				startActivity(marketIntent);
				break;

			case R.id.banking_layout:
				Toast.makeText(BMHome.this, "Development in progress", Toast.LENGTH_SHORT).show();
				break;

			case R.id.wallet_layout:
				Intent walletIntent = new Intent(getApplicationContext(), WalletTabView.class);
				startActivity(walletIntent);
				break;

			case R.id.utility_layout:
				Intent utilityIntent = new Intent(getApplicationContext(), UtilityServices.class);
				startActivity(utilityIntent);
				break;

			case R.id.offers_layout:
				Intent offerIntent = new Intent(getApplicationContext(), Offers.class);
				startActivity(offerIntent);
				break;
			default:
				break;
		}*/

		Intent intent;
		/*switch (v.getId()) {
			case R.id.loadmoney_layout:

				break;

			case R.id.sendmoney_layout:

				break;

			case R.id.tranfertobank_layout:

				break;

			case R.id.paybill_layout:
				showAlert("Pay Bill");
				break;

			case R.id.requestmoney_layout:

				break;

			case R.id.qrscan_layout:
				intent = new Intent(getApplicationContext(), BMQRScan.class);
				startActivity(intent);
				break;


			default:
				break;
		}
*/
	}






	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	/*@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}*/

	@Override
	public void onBackPressed() {
		//ExitApplication();
	}





	public class UnreadNotification extends AsyncTask<String, String, String>{
		@Override
		protected String doInBackground(String... params) {
			//apiManager.getUnreadNotification(BMHome.this);



			return null;
		}
	}

	void CloseWin()
	{
		hideLoader();
		this.finish();
	}

	private void GetBalance() {
		balance.setText("");
		if (!verifyInternetStatus()) {
			return;
		}
		showLoader();

		try {
			System.out.println("IN HOME MSISDN::::: "+appData.getMsisdn());
			final UUID resultId = apiManager.BMMerchantBalance(appData.getMsisdn(),appData.getPinIntentString());

			final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

				@Override
				public void handleMessage(APIManager.SOAPOperationData data) {
					try {
						if("RBERESP".equalsIgnoreCase(data.getResponseDataObj().getString("TYPE"))&&"200".equalsIgnoreCase(data.getResponseDataObj().getString("TXNSTATUS"))) {
							System.out.println("DATA:::::: " + data.getResponseDataObj().toString());
							apiManager.removeListener(this);
							hideLoader();
							balance.setText(getResources().getString(R.string.ro) + " " + data.getResponseDataObj().getString("BALANCE"));
							appData.setSvnBalance(((int) Float.parseFloat(data.getResponseDataObj().getString("BALANCE"))));
						}else{
							showAlert("Validation Error");
							apiManager.removeListener(this);
							hideLoader();
						}

					} catch (Exception e) {
						e.printStackTrace();
						apiManager.removeListener(this);
						hideLoader();
						new AlertDialog.Builder(BMHome.this)
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
										Logout();
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



	private void UserQuestions(final String msisdn) {
		if (!verifyInternetStatus()) {
			return;
		}
		showLoader();
		DbManager dbManager = DbManager.createAuto(BMHome.this);

		dbManager.setMsisdn(msisdn);

		Wallet.Data.syncRead(getApplicationContext());

		try {
			final UUID resultId = apiManager.BMUserQuestions(msisdn);

			final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

				@Override
				public void handleMessage(APIManager.SOAPOperationData data) {
					try {
						if(("200".equalsIgnoreCase((String)data.getResponseDataObj().get("TXNSTATUS"))))
						{
							String questions = (String) data.getResponseDataObj().get("DATA");
							org.json.JSONObject qobject = new org.json.JSONObject(questions);
							org.json.JSONArray qarray = (org.json.JSONArray) qobject.get("QUESTION");
							System.out.println("SIZE f questions:: "+qarray.length());
							org.json.JSONObject firstq = (org.json.JSONObject) qarray.get(0);
							System.out.println("FIRST QUESTION:: " + firstq.get("QUESTION"));
							System.out.println("FIRST ANSWER:: " + firstq.get("ANSWER"));
						}else
						{
							showAlert((String)data.getResponseDataObj().get("MESSAGE"));
						}


					} catch (Exception e) {
						e.printStackTrace();
						hideLoader();
						new AlertDialog.Builder(BMHome.this)
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
	public void ShowBalance() {


	}

	public void OnDialogClose(String message) {
		if(message.equalsIgnoreCase("Validation Error"))
		Logout();
		else{

		}
	}
}

