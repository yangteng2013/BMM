package app.banking.bankmuscat.merchant.base;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

import app.banking.bankmuscat.merchant.api.APIManager;
import app.banking.bankmuscat.merchant.common.Wallet;
import app.banking.bankmuscat.merchant.interfaces.IAlerts;
import app.banking.bankmuscat.merchant.interfaces.ILoader;
import app.banking.bankmuscat.merchant.logger.CLog;
import app.banking.bankmuscat.merchant.util.NetworkUtils;
import app.banking.bankmuscat.merchant.util.constant.ErrorAndPopupCodes;

public abstract class TabActivity extends AppCompatActivity implements ILoader, IAlerts {
	//
	protected APIManager apiManager;
	protected Wallet.Data appData = Wallet.Data.getInstance();
	private ProgressDialog progress;
	private Builder alertDialog;
	Activity mParent;
	private PopupWindow popwin;
	private String cs_cardNum;
	private String cs_cardType;

	public static final int NO_TYPE = -1;
	public static final int REGISTER_USER = 0;
	public static final int FORGOT_PIN = 1;
	public static final int CHANGE_NUMBER = 2;
	public static final int CHANGE_EMAIL = 3;
	public static final int LOAD_SVA = 4;
	public static final int UNLOAD_SVA = 5;
	public static final int DELETE_CARD = 6;
	public static final int SEND_MONEY = 7;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		//
		initProgress();
		//
		initAlerts();

	//	registerReceiver(paymentGcmReceiver, paymentGcmIntentFilter);

		try {
			apiManager = APIManager.createInstance(null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		/*ActionBar actionBar = getSupportActionBar();
		actionBar.hide();*/
	}
	/*IntentFilter paymentGcmIntentFilter = new IntentFilter("PaymentGCM");
	BroadcastReceiver paymentGcmReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			String data = arg1.getStringExtra("chargeslip_data");
			Log.d("chargeslip_data", data);
			chargeSlipPopUp(GCMIntentService.parseChargeSlipData(data));
		}

	};*/



	/*private void chargeSlipPopUp(HashMap<String, String> cs_data) {

		try {

			Log.i("chargeSlipPopUpMethod", "Inside chargeSlipPopUp method");
			LayoutInflater layout = (LayoutInflater) ActivityBase.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = layout.inflate(R.layout.popup_window,
					(ViewGroup) findViewById(R.id.popup_element));

			DisplayMetrics displaymetrics = new DisplayMetrics();
			getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
			int height = displaymetrics.heightPixels;
			int width = displaymetrics.widthPixels;

		*//*	if (height > 1200) {
				System.out.println("true");


			} else {
				System.out.println("false");
				popwin = new PopupWindow(view, 630, 1030, true);
			}*//*

			popwin = new PopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT,  WindowManager.LayoutParams.WRAP_CONTENT, true);
			popwin.showAtLocation(view, Gravity.CENTER, 0, 0);
			popwin.setBackgroundDrawable(null);
			TextView popup_title = (TextView) view.findViewById(R.id.popup_header_title);
			popup_title.setText("Charge Slip");
			TextView popup_date_time = (TextView) view.findViewById(R.id.popup_Date_Time);


			Spannable word = new SpannableString("Date/Time : ");
			word.setSpan(new ForegroundColorSpan(R.color.app_color), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			popup_date_time.setText(word);

			if(!cs_data.get("cs_txnTime").equals("")) {
				Spannable word1 = new SpannableString(cs_data.get("cs_txnTime"));
				word1.setSpan(new ForegroundColorSpan(R.color.black), 0, word1.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				popup_date_time.append(word1);
			}

			TextView popup_MID = (TextView) view.findViewById(R.id.popup_MID);
			Spannable word3 = new SpannableString("MID:");
			word3.setSpan(new ForegroundColorSpan(R.color.app_color), 0, word3.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			popup_MID.setText(word3);

			if(!cs_data.get("cs_mid").equals("")) {
				Spannable word4 = new SpannableString(cs_data.get("cs_mid"));
				word4.setSpan(new ForegroundColorSpan(R.color.black), 0, word4.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				popup_MID.append(word4);
			}

			TextView popup_TID = (TextView) view.findViewById(R.id.popup_TID);
			Spannable word5 = new SpannableString("TID : ");
			word5.setSpan(new ForegroundColorSpan(R.color.app_color), 0, word5.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			popup_TID.setText(word5);

			if(!cs_data.get("cs_tid").equals("")) {
				Spannable word6 = new SpannableString(cs_data.get("cs_tid"));
				word6.setSpan(new ForegroundColorSpan(R.color.black), 0, word6.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				popup_TID.append(word6);
			}

			TextView popup_SALE = (TextView) view.findViewById(R.id.popup_SALE);
			popup_SALE.setText("Sale");
			TextView popup_card_num = (TextView) view.findViewById(R.id.popup_card_num);


			Spannable word7 = new SpannableString("CARD NUM  : ");
			word7.setSpan(new ForegroundColorSpan(R.color.app_color), 0, word7.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			popup_card_num.setText(word7);

			if(!cs_data.get("cs_cardNum").equals("")) {
				Spannable word8 = new SpannableString(cs_data.get("cs_cardNum"));
				word8.setSpan(new ForegroundColorSpan(R.color.black), 0, word8.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				popup_card_num.append(word8);
			}

			TextView popup_card_type = (TextView) view.findViewById(R.id.popup_card_type);
			Spannable word9 = new SpannableString("CARD TYPE : ");
			word9.setSpan(new ForegroundColorSpan(R.color.app_color), 0, word9.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			popup_card_type.setText(word9);

			if(!cs_data.get("cs_cardType").equals("")) {
				Spannable word10 = new SpannableString(cs_data.get("cs_cardType"));
				word10.setSpan(new ForegroundColorSpan(R.color.black), 0, word10.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				popup_card_type.append(word10);
			}

			TextView popup_rrn = (TextView) view.findViewById(R.id.popup_rrn);
			Spannable word11 = new SpannableString("RRN     : ");
			word11.setSpan(new ForegroundColorSpan(R.color.app_color), 0, word11.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			popup_rrn.setText(word11);
			if(!cs_data.get("cs_rrn").equals("")) {
				Spannable word12 = new SpannableString(cs_data.get("cs_rrn"));
				word12.setSpan(new ForegroundColorSpan(R.color.black), 0, word12.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				popup_rrn.append(word12);
			}

			TextView popup_transactionId = (TextView) view.findViewById(R.id.popup_transactionId);
			Spannable word13 = new SpannableString("TXN ID : ");
			word13.setSpan(new ForegroundColorSpan(R.color.app_color), 0, word13.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			popup_transactionId.setText(word13);

			if(!cs_data.get("cs_transactionId").equals("")) {
				Spannable word14 = new SpannableString(cs_data.get("cs_transactionId"));
				word14.setSpan(new ForegroundColorSpan(R.color.black), 0, word14.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
				popup_transactionId.append(word14);
			}
			TextView popup_amount_tag = (TextView) view.findViewById(R.id.popup_amount_tag);
			popup_amount_tag.setText("AMOUNT : ");
			if(!cs_data.get("cs_amount").equals("")) {
				int amt_int = Integer.parseInt(cs_data.get("cs_amount"));
				float amt_float = (float) amt_int;
				String amt_str = String.valueOf((amt_float / 100));

				TextView popup_amount = (TextView) view.findViewById(R.id.popup_amount);
				popup_amount.setText("$" + amt_str);
			}

			Button close_poup = (Button) view.findViewById(R.id.popup_button);
			close_poup.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Log.e("Onclick", "Onclick called");
					Log.e("Onclick popup", "" + popwin);
					Log.e("Onclick popup eheight", "" + popwin.isTouchable());
					popwin.dismiss();
				}
			});
		} catch (Exception e) {
			Log.e("chargeSlipPopUpMethodError",
					"Error in chargeSlipPopUp method");
			e.printStackTrace();

			System.out.println("Exception Occured");

		}

	}*/

	/**
	 *
	 */
	private void initProgress() {
		// do nothing
	}

	/**
	 * init alert box
	 */
	private void initAlerts() {
		alertDialog = new Builder(this);
	}

	/**
	 * implement ILoader
	 */
	public void showLoader() {
		showLoader("Loading..");
	}

	public void showLoader(String message) {
		hideLoader();
		progress = ProgressDialog.show(this, "", message, true, false);

	}

	/**
	 * implement ILoader
	 */
	public void hideLoader() {
		if (null != progress) {
			progress.hide();
			progress.dismiss();
		}
		progress = null;
	}

	@Override
	protected void onPause() {
		super.onPause();
		//this.unregisterReceiver(paymentGcmReceiver);
	}

	@Override
	protected void onResume() {
		super.onResume();
	//	registerReceiver(paymentGcmReceiver, paymentGcmIntentFilter);
	}

	public void showAlert(String message) {
		showAlert("", message);
	}

	public void showAlert(String title, String message) {
		alertDialog.setTitle(title);
		alertDialog.setMessage(message);
		alertDialog.setCancelable(true);
		//
		alertDialog.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// here you can add functions
						OnDialogClose();
						dialog.dismiss();
					}
				});

		alertDialog.show();
	}


	public void showAlert(String title, String message, final boolean doAfter) {
		alertDialog.setTitle(title);
		alertDialog.setMessage(message);
		alertDialog.setCancelable(true);
		boolean doAfter1 = doAfter;
		//
		alertDialog.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {

						dialog.dismiss();
						if(doAfter)
						OnDialogClose();

					}
				});

		alertDialog.show();
	}

	public void OnDialogClose() {

	}

	public void ExitApplication() {

		/*finish();
		Intent i = new Intent(getApplicationContext(), Exit.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(i);*/

	}

	/**
	 * check internet status and show alert if not connected
	 *
	 * @return
	 */
	protected boolean verifyInternetStatus() {
		boolean status = NetworkUtils.isConnectedtoInternet(this);
		if (!status) {
			showAlert("", ErrorAndPopupCodes.Network_Error.getTag());
		}
		return status;
	}

	public boolean onQueryTextChange(String newText) {
		return false;
	}

	public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {

	}

	public boolean onPrepareOptionsMenu1(Menu menu) {
		if (mParent != null) {
			return mParent.onPrepareOptionsMenu(menu);
		}
		return true;
	}

	public boolean onOptionsItemSelected1(MenuItem item) {
		if (mParent != null) {
			return mParent.onOptionsItemSelected(item);
		}
		return false;
	}

	public void setDeleteFocus(final EditText eText, final EditText toEText) {
		eText.setOnKeyListener(new View.OnKeyListener() {
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				if (keyCode == KeyEvent.KEYCODE_DEL
						&& event.getAction() == KeyEvent.ACTION_UP) {
					CLog.i("KEY_Event", "del");
					eText.setText("");
					toEText.requestFocus();
				}
				return false;
			}
		});
	}

	public void setTextFocus(final EditText eText, final EditText toEText) {
		eText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
									  int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
										  int after) {
				/* do nothing */

			}

			@Override
			public void afterTextChanged(Editable s) {
				if (eText.getText().toString().length() == 1) {
					toEText.requestFocus();

				}

			}

		});

	}




	public void ErrorAction()
	{

	}

	public class ViewPagerAdapter extends FragmentStatePagerAdapter {
		private final List<Fragment> mFragmentList = new ArrayList<>();
		private final List<String> mFragmentTitleList = new ArrayList<>();

		public ViewPagerAdapter(android.support.v4.app.FragmentManager manager) {
			super(manager);
		}

		@Override
		public Fragment getItem(int position) {
			return mFragmentList.get(position);
		}

		@Override
		public int getCount() {
			return mFragmentList.size();
		}

		public void addFragment(Fragment fragment, String title) {
			mFragmentList.add(fragment);
			mFragmentTitleList.add(title);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return mFragmentTitleList.get(position);
		}
	}


	public boolean IsSequentialOrRepetitive(String one, String two, String three, String four) {

		int w = Integer.parseInt(one);
		int x = Integer.parseInt(two);
		int y = Integer.parseInt(three);
		int z = Integer.parseInt(four);

		if (w == x && x == y && y == z)
			return true;

		else if (w + 1 == x && x + 1 == y && y + 1 == z)
			return true;

		else if (w - 1 == x && x - 1 == y && y - 1 == z)
			return true;

		return false;
	}

}
