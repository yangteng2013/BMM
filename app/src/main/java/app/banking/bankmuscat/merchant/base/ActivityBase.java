package app.banking.bankmuscat.merchant.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import app.banking.bankmuscat.R;
import app.banking.bankmuscat.merchant.Screens.BMChangePin;
import app.banking.bankmuscat.merchant.Screens.BMEnterPin;
import app.banking.bankmuscat.merchant.Screens.BMError;
import app.banking.bankmuscat.merchant.Screens.BMHome;
import app.banking.bankmuscat.merchant.Screens.BMLanguage;
import app.banking.bankmuscat.merchant.Screens.BMLogin;

import app.banking.bankmuscat.merchant.Screens.BMMerchantM2M;
import app.banking.bankmuscat.merchant.Screens.BMProfile;


import app.banking.bankmuscat.merchant.Screens.BMTransactionHistory;
import app.banking.bankmuscat.merchant.adapters.NavDrawerListAdapter;
import app.banking.bankmuscat.merchant.api.APIManager;
import app.banking.bankmuscat.merchant.common.Wallet;
import app.banking.bankmuscat.merchant.interfaces.IAlerts;
import app.banking.bankmuscat.merchant.interfaces.ILoader;
import app.banking.bankmuscat.merchant.logger.CLog;
import app.banking.bankmuscat.merchant.util.NetworkUtils;
import app.banking.bankmuscat.merchant.util.constant.ErrorAndPopupCodes;

import static app.banking.bankmuscat.R.id.buttonNotification;
import static app.banking.bankmuscat.R.id.header_home;

public abstract class ActivityBase extends AppCompatActivity implements ILoader, IAlerts {
    //
    protected APIManager apiManager;
    protected Wallet.Data appData = Wallet.Data.getInstance();
    private ProgressDialog progress;
    private Builder alertDialog;
    private Builder optionDialog;
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
    public static final int DELETE_ACCOUNT = 8;
    public static final int USER_INACTIVITY = 9;
    public static final int CHANGE_LANGUAGE = 10;
    public static final int CHANGE_EMAILORNUMBER = 11;
    public static final int USER_RESTART = 12;
    public static final int REQUEST_MONEY = 13;
    public static final int CHANGE_DEVICE = 14;
    public static final int CHANGE_DEVICE_FORGOTPIN = 15;
    public static final int CHANGE_DEVICE_FORGOTPINFINAL = 16;
    public static final int CHIP_IN = 17;
    public static final int GET_CONTACT = 18;
    public static final int REQUEST_ACCEPT = 19;
    public static final int REREQUEST_MONEY = 20;
    public static final int SENDMONEY_FAV = 21;
    public static final int SEND_BANK = 22;
    public static final int  COLLECT_PAYMENT = 23;


    public static final String BM_MOBILE = "UserMobile";
    public static final String BM_LANGUAGE = "bmlanguage";
    public static final String BM_DATA = "bmdata";
    public static final String LANGUAGE_NONE = "None";
    public static final String LANGUAGE_ENGLISH = "en";
    public static final String LANGUAGE_ARABIC = "ar";

    public String originalAmount;

	/*public static final int LANGUAGE_NONE = 100;
    public static final int LANGUAGE_ENGLISH = 101;
	public static final int LANGUAGE_ARABIC = 102;*/

	/*public boolean isHeaderNeeded() {
        return isHeaderNeeded;
	}

	public void setHeaderNeeded(boolean headerNeeded) {
		isHeaderNeeded = headerNeeded;
	}

	private boolean isHeaderNeeded = true;*/

    public boolean isHeaderNeeded() {
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiManager.setCheckingPinStatus(false);


        setContentView(R.layout.bm_base);

        String lang = BMReadData(BM_LANGUAGE, LANGUAGE_ENGLISH);
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        this.getResources().updateConfiguration(config, null);

        CollectContainerLayouts();


        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //
        initProgress();
        //
        initAlerts();


        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        try {
            getWindow().setBackgroundDrawableResource(R.drawable.bg);
        } catch (Exception e) {
            String s = e.getMessage();
        }


        //	registerReceiver(paymentGcmReceiver, paymentGcmIntentFilter);

        try {
            apiManager = APIManager.createInstance(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
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
        optionDialog = new Builder(this);
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
        progress.setCanceledOnTouchOutside(false);

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


    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    //HideSoftKeyboard(ActivityBase.this);
                    HideSoftKeyboard();
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    public static void HideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null)
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        HideSoftKeyboard(this);
        return false;
    }

    public void HideSoftKeyboard() {
        TextView title_header = (TextView) findViewById(R.id.title_header);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(title_header.getWindowToken(), 0);
    }

    public void showAlert(String message) {
        showAlert("", message);
    }

    public void showAlert(final String title, final String message) {
        if (!(this).isFinishing()) {
            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.bm_dialog_alert, null);
            dialogBuilder.setView(dialogView);

            EditText editText = (EditText) dialogView.findViewById(R.id.txtmessage);
            editText.setText(message);


            Button ok = (Button) dialogView.findViewById(R.id.btnok);
            ok.setText("OK");

            final AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    OnDialogClose();
                    OnDialogClose(message);
                    alertDialog.dismiss();
                }
            });
        }
    }


    public void showAlert(String title, final String message, final boolean doAfter) {
        if (!(this).isFinishing()) {
            final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.bm_dialog_alert, null);
            dialogBuilder.setView(dialogView);

            EditText editText = (EditText) dialogView.findViewById(R.id.txtmessage);
            editText.setText(message);


            Button ok = (Button) dialogView.findViewById(R.id.btnok);
            ok.setText("OK");

            final AlertDialog alertDialog = dialogBuilder.create();
            alertDialog.show();
            alertDialog.setCanceledOnTouchOutside(false);
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (doAfter)
                        OnDialogClose();

                    OnDialogClose(message);
                    alertDialog.dismiss();
                }
            });
        }

    }

    public void showInfo(final String message) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.bm_dialog_info, null);
        dialogBuilder.setView(dialogView);

        EditText editText = (EditText) dialogView.findViewById(R.id.txtmessage);
        editText.setText(message);


        Button ok = (Button) dialogView.findViewById(R.id.btnok);
        ok.setText("OK");

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(false);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             /*   OnDialogClose();
                OnDialogClose(message);*/
                alertDialog.dismiss();
            }
        });

    }


    public void OnDialogClose() {

    }

    public void OnDialogClose(String message) {

    }


    public void showOptionDialog(final String message, final boolean doAfter) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.bm_dialog_option, null);
        dialogBuilder.setView(dialogView);

        EditText editText = (EditText) dialogView.findViewById(R.id.txtmessage);
        editText.setText(message);

        final AlertDialog optionDialog = dialogBuilder.create();
        optionDialog.show();
        optionDialog.setCanceledOnTouchOutside(false);

        Button no = (Button) dialogView.findViewById(R.id.btnno);
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                optionDialog.dismiss();
                if (doAfter) {
                    OnOptionDialogCancel(message);

                }
            }
        });


        Button yes = (Button) dialogView.findViewById(R.id.btnyes);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                optionDialog.dismiss();

                if (doAfter) {
                    OnOptionDialogClose(message);
                    OnOptionDialogClose();
                }
            }
        });


    }

    public void OnOptionDialogClose() {


    }

    public void OnOptionDialogClose(String message)
    {
        if (message.compareToIgnoreCase(getResources().getString(R.string.logoutmessage)) == 0)
        {
            appData = new Wallet.Data();
            Logout();
        }
    }

    public void OnOptionDialogCancel(String message) {


    }

    public void ExitApplication() {

        int pid = android.os.Process.myPid();
        android.os.Process.killProcess(pid);
        System.exit(0);

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
                    toEText.setSelection(toEText.getText().length());
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


    public void ErrorAction() {

    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(android.support.v4.app.FragmentManager manager) {
            super(manager);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
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

    public void ShowLogin() {

        /*Intent intent = new Intent(getApplicationContext(), BMLogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);*/

        Logout();
    }

    public void ShowHome() {

        appData.setCurrentTransaction(null);

        Intent intent = new Intent(getApplicationContext(), BMHome.class);
        intent.putExtra("Mode", false);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void ShowProfile() {

        Intent intent = new Intent(getApplicationContext(), BMProfile.class);
        intent.putExtra("Mode", false);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }



    public void ShowTransferToBankList() {


    }

    public void Logout() {

        Intent intent = new Intent(getApplicationContext(), BMLogin.class);
        appData.setUserData(null);
/*        appData.setMsisdn(null);
        appData.setPinIntentString(null);*/
        appData = Wallet.Data.ClearData();
        apiManager.ClearAppData();
        BMWriteData(BM_MOBILE, "");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        this.finish();
        startActivity(intent);
    }

    public void ShowNotifications() {

    }

    public int GetScreenLayout() {
        return 0;
    }

    public void RemoveHeaderButtons() {

        ImageView headerMenuButton, headerHome, headerNotification,home;
        headerMenuButton = (ImageView) findViewById(R.id.header_menu_button);
        home = (ImageView) findViewById(R.id.header_user);
        headerHome = (ImageView) findViewById(header_home);
        headerNotification = (ImageView) findViewById(buttonNotification);
        headerMenuButton.setVisibility(View.INVISIBLE);
        headerHome.setVisibility(View.INVISIBLE);
        headerNotification.setVisibility(View.INVISIBLE);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mDrawerList.setVisibility(View.INVISIBLE);

    }

    /* The click listner for ListView in the navigation drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        }
    }

    private void CollectContainerLayouts() {
        mheaderBar = (RelativeLayout) findViewById(R.id.header_layout);
        RelativeLayout lytContent = (RelativeLayout) findViewById(R.id.content_layout);


        LayoutInflater inflater = LayoutInflater.from(this);
        View content = inflater.inflate(GetScreenLayout(), lytContent);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        showMenu = (ImageView) findViewById(R.id.header_menu_button);

        mDrawerToggle = new ActionBarDrawerToggle(this, /* host Activity */
                mDrawerLayout, /* DrawerLayout object */
                R.drawable.ic_launcher, /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open, /* "open drawer" description for accessibility */
                R.string.drawer_close /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu(); // creates call to
                // onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        //unReadCount.setText(unReadtext);

        //GetBalance();


        if (isHeaderNeeded()) {
            mheaderBar.setVisibility(View.VISIBLE);

            showMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HideSoftKeyboard();
                    if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                    } else {
                        mDrawerLayout.openDrawer(Gravity.LEFT);
                    }
                }
            });
            // set up the drawer's list view with items and click listener
			/*navDrawerItems.add("Transaction History");
			navDrawerItems.add("Pending Requests");
			navDrawerItems.add("Profile");
			navDrawerItems.add("Download Mobile Banking App");
			navDrawerItems.add("Download Offer App");
			navDrawerItems.add("Language");
			navDrawerItems.add("Logout");
			navDrawerItems.add("Refer a friend");
			navDrawerItems.add("FAQ");
			navDrawerItems.add("Help/ Demo Screen");
			navDrawerItems.add("Terms and Conditions");*/


            navDrawerItems.add(appData.getUserName());
            navDrawerItems.add(getResources().getString(R.string.menu_change_pin));
            navDrawerItems.add(getResources().getString(R.string.menu_transactionhistory));
            navDrawerItems.add(getResources().getString(R.string.menu_m2m));
            navDrawerItems.add(getResources().getString(R.string.menu_language));
            navDrawerItems.add(getResources().getString(R.string.menu_logout));


            navigationAdapter = new NavDrawerListAdapter(getApplicationContext(),
                    navDrawerItems);
            mDrawerList.setAdapter(navigationAdapter);
            mDrawerLayout.openDrawer(Gravity.LEFT);
            mDrawerLayout.closeDrawer(mDrawerList);
            mDrawerList.setOnItemClickListener(new DrawerItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
					/*if (position == 0) {
						mDrawerLayout.closeDrawer(Gravity.LEFT);
						Intent intent = new Intent(getApplicationContext(),
								BMTransactions.class);
						//intent.putExtra("BMProfile", "2");
						startActivity(intent);
					} else if (position == 1) {
						mDrawerLayout.closeDrawer(Gravity.LEFT);
						Intent intent = new Intent(getApplicationContext(),
								app.banking.bankmuscat.wallet.Screens.BMPendingTransactions.class);
						//intent.putExtra("BMProfile", "2");
						startActivity(intent);
					} else if (position == 2) {
						mDrawerLayout.closeDrawer(Gravity.LEFT);
						Intent intent = new Intent(getApplicationContext(),
								BMProfile.class);
						//intent.putExtra("BMProfile", "2");
						startActivity(intent);
					} else if (position == 3) {
						mDrawerLayout.closeDrawer(Gravity.LEFT);
						try {
							Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "BankMuscat Mobile banking"));
							i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivity(i);
						} catch (android.content.ActivityNotFoundException anfe) {

						}
					} else if (position == 4) {
						try {
							Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "BankMuscat Mobile banking"));
							i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
							startActivity(i);
						} catch (android.content.ActivityNotFoundException anfe) {

						}
					} else if (position == 5) {
						mDrawerLayout.closeDrawer(Gravity.LEFT);
						Intent intent = new Intent(getApplicationContext(), BMProfile.class);
						intent.putExtra("BMLanguage", "2");
						startActivity(intent);
					} else if (position == 1) {
						mDrawerLayout.closeDrawer(Gravity.LEFT);
						Intent intent = new Intent(getApplicationContext(), BMProfile.class);
						intent.putExtra("Transaction_Details", "2");
						startActivity(intent);
					} else if (position == 6) {
						mDrawerLayout.closeDrawer(Gravity.LEFT);
						appData = new Wallet.Data();
						Logout();
						//CloseWin();
						//ExitApplication();
					} else if (position == 7) {
						mDrawerLayout.closeDrawer(Gravity.LEFT);
						Intent intent = new Intent(getApplicationContext(), BMProfile.class);
						intent.putExtra("BMLanguage", "2");
						startActivity(intent);
					} else if (position == 8) {
						mDrawerLayout.closeDrawer(Gravity.LEFT);
						Intent intent = new Intent(getApplicationContext(), BMProfile.class);
						startActivity(intent);
					} else if (position == 9) {
						mDrawerLayout.closeDrawer(Gravity.LEFT);
						Intent intent = new Intent(getApplicationContext(), BMTermsAndConditions.class);
						intent.putExtra("BMLanguage", "2");
						startActivity(intent);
					} else if (position == 10) {
						mDrawerLayout.closeDrawer(Gravity.LEFT);
						Intent intent = new Intent(getApplicationContext(), BMTermsAndConditions.class);
						intent.putExtra("Transaction_Details", "2");
						startActivity(intent);
					}*/

                    Intent intent = null;
                    if (position == 0) {
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                        finish();
                        intent = new Intent(getApplicationContext(),
                                BMHome.class);

                    }else if (position == 1) {
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                        intent = new Intent(getApplicationContext(),
                                BMChangePin.class);
                        //intent.putExtra("BMProfile", "2");
                        //startActivity(intent);
                    }

                    else if (position == 2) {
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                        intent = new Intent(getApplicationContext(),
                                BMTransactionHistory.class);
                        //intent.putExtra("BMProfile", "2");
                        //startActivity(intent);
                    } else if (position == 3) {
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                        try {
							intent = new Intent(getApplicationContext(),
									BMMerchantM2M.class);
							//intent.putExtra("BMProfile", "2");
							startActivity(intent);
                        } catch (android.content.ActivityNotFoundException anfe) {

                        }
                    } else if (position == 4) {
                        mDrawerLayout.closeDrawer(Gravity.LEFT);
                        intent = new Intent(getApplicationContext(),
                                BMLanguage.class);
                        intent.putExtra("Mode",10);
                        startActivity(intent);

                    } else if (position == 5                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         ) {

                        showOptionDialog(getResources().getString(R.string.logoutmessage), true);


                    }
                    //CloseWin();

                    if (intent != null) {
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    }
                }
            });

            ImageView headerBack, headerHome, headerNotification,headerUser;

            headerBack = (ImageView) findViewById(R.id.header_back_button);

            headerHome = (ImageView) findViewById(header_home);

            headerUser = (ImageView) findViewById(R.id.header_user);

            headerNotification = (ImageView) findViewById(buttonNotification);


            headerBack.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    CloseWin();
                }

            });

            headerHome.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    ShowHome();
                }

            });

            headerBack.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    CloseWin();
                }

            });

            headerUser.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {

                    ShowHome();
                }

            });

            headerNotification.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    ShowNotifications();
                }

            });

        } else {
            mheaderBar.setVisibility(View.GONE);
        }

        HideSoftKeyboard(this);
    }

    void CloseWin() {
/*hideSoftKeyboard();
                NavUtils.navigateUpTo(BMEnterEmail.this, NavUtils
						.getParentActivityIntent(BMEnterEmail.this));*/
        this.finish();
    }


    @Override
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (item != null && item.getItemId() == android.R.id.home) {
            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            } else {
                mDrawerLayout.openDrawer(Gravity.LEFT);
            }
        }
        return false;
    }

    private RelativeLayout mheaderBar;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private NavDrawerListAdapter navigationAdapter;
    private ArrayList<String> navDrawerItems = new ArrayList<String>();
    private ImageView showMenu;


    //public static final long PIN_TIMEOUT = 300000;
    public boolean isActive = true;
    public boolean checkingPin = false;

    private Handler timeoutHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
        }
    };

    private Runnable timeoutCallback = new Runnable() {
        @Override
        public void run() {
            AskPin();
        }
    };

    public void resetTimer() {
        long PIN_TIMEOUT = ((BMApplication) this.getApplication()).MAX_INACTIVITY_TIME;
        timeoutHandler.removeCallbacks(timeoutCallback);
        timeoutHandler.postDelayed(timeoutCallback, PIN_TIMEOUT);
    }

    public void showError() {
        Intent i = new Intent(this, BMError.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);  //this combination of flags would start a new instance even if the instance of same Activity exists.
        i.addFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
        startActivity(i);
    }

    public void stopTimer() {
        timeoutHandler.removeCallbacks(timeoutCallback);
    }

    @Override
    public void onUserInteraction() {
        resetTimer();
    }


	/*@Override
	public void onUserLeaveHint(){
		resetTimer();
	}*/

    public void AskPin() {
        if (appData != null) {
                if (appData.getMsisdn().length()!=0) {
                    Intent intent = new Intent(getApplicationContext(), BMHome.class);
                    startActivity(intent);
                } else {
                     Logout();
                }
        }else{
            Logout();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == USER_INACTIVITY) {

            if (resultCode == RESULT_OK) {

//isActive = true;
                //finish();
                apiManager.setCheckingPinStatus(false);
                resetTimer();

            } else {//if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                //AskPin();
                apiManager.setCheckingPinStatus(false);
                Logout();

            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();



		/*String lang = BMReadData(BM_LANGUAGE, LANGUAGE_ENGLISH);

		Locale locale = new Locale(lang);
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		this.getResources().updateConfiguration(config, null);*/

        ///app in bg

        BMApplication bmApp = (BMApplication) this.getApplication();
        if (bmApp.wasInactive) {


            AskPin();
        }

        bmApp.StopInactivityTimer();

        ///app in bg


        resetTimer();
        //	registerReceiver(paymentGcmReceiver, paymentGcmIntentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //this.unregisterReceiver(paymentGcmReceiver);

        ///app in bg

        ((BMApplication) this.getApplication()).StartInactivityTimer();

        ///app in bg
    }

    @Override
    public void onStop() {
        super.onStop();
        stopTimer();
    }

    public String BMReadData(String key, String defaultValue) {
        try {
            SharedPreferences settings = this.getSharedPreferences(BM_DATA, Context.MODE_PRIVATE);
            return settings.getString(key, defaultValue);
        } catch (Exception e) {
        }
        return defaultValue;
    }

    public boolean BMWriteData(String key, String value) {
        try {
            SharedPreferences settings = this.getSharedPreferences(BM_DATA, Context.MODE_PRIVATE);
            SharedPreferences.Editor prefEditor = settings.edit();
            prefEditor.putString(key, value);
            return prefEditor.commit();
        } catch (Exception e) {
        }
        return false;
    }

    public static class BMSpinnerAdapter extends ArrayAdapter<String> {
        // Initialise custom font, for example:
        Typeface font = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/PreciousSansMedium.ttf");

        // (In reality I used a manager which caches the Typeface objects)
        // Typeface font = FontManager.getInstance().getFont(getContext(), BLAMBOT);

        public BMSpinnerAdapter(Context context, int resource, List<String> items) {
            super(context, resource, items);
        }

        // Affects default (closed) state of the spinner
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            view.setTypeface(font);
            return view;
        }

        // Affects opened state of the spinner
        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            TextView view = (TextView) super.getDropDownView(position, convertView, parent);
            view.setTypeface(font);
            return view;
        }
    }

    public void BMPerformDedupeForEmail(final String emailId) {
         BMPerformDedupe(emailId, "00000000");
    }

    public void BMPerformDedupeForNumber(final String mobileNumber) {
         BMPerformDedupe("xxx@xxx.xxx", mobileNumber);
    }

    public void OnDedupeCompletion(String retValue, boolean isEmail) {

    }

    public void OnDedupeEmailCompletion(String retValue) {

    }

    public void OnDedupeNumberCompletion(String retValue) {

    }

    private void BMPerformDedupe(final String emailId, final String mobileNumber) {


        if (verifyInternetStatus()) {

            showLoader();

            try {


                final UUID resultId = apiManager.BMPerformDedupe(emailId, mobileNumber);

                final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

                    @Override
                    public void handleMessage(APIManager.SOAPOperationData data) {
                        try {

                            if (data.getId() == resultId) {
                                apiManager.removeListener(this);
                                hideLoader();

                                if (!data.hasError()) {

                                    OnDedupeCompletion("", (emailId.compareToIgnoreCase("xxx@xxx.com") == 0) ? false : true);

                                    OnDedupeNumberCompletion("");
                                    OnDedupeEmailCompletion("");

                                    hideLoader();
                                } else {
                                    OnDedupeCompletion(data.getResponseData().userDataList.get(0).firstName,(emailId.compareToIgnoreCase("xxx@xxx.com") == 0) ? false : true);


                                    if(emailId.compareToIgnoreCase("xxx@xxx.com") == 0)
                                    {
                                        OnDedupeNumberCompletion(data.getResponseData().userDataList.get(0).firstName);
                                    }
                                    else
                                    {
                                        OnDedupeEmailCompletion(data.getResponseData().userDataList.get(0).firstName);
                                    }
                                }

                            } else {
                                OnDedupeCompletion(null, (emailId.compareToIgnoreCase("xxx@xxx.com") == 0) ? false : true);
                                OnDedupeNumberCompletion(null);
                                OnDedupeEmailCompletion(null);

                            }
                        } catch (Exception e) {


                            new AlertDialog.Builder(ActivityBase.this)
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

        else
        {
            showInfo("No Connectivity");
        }
    }


    /*String retValue = null;


    private String BMPerformDedupe(final String emailId, final String mobileNumber) {


        if (verifyInternetStatus()) {

            showLoader();

            try {


                final UUID resultId = apiManager.BMPerformDedupe(emailId, mobileNumber);

                final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

                    @Override
                    public void handleMessage(APIManager.SOAPOperationData data) {
                        try {

                            if (data.getId() == resultId) {
                                apiManager.removeListener(this);
                                hideLoader();

                                if (!data.hasError()) {


                                    retValue = "";

                                    OnDedupeCompletion(retValue);
                                    hideLoader();
                                } else {
                                    retValue = data.getResponseData().userDataList.get(0).firstName;

                                }

                            } else {
                                retValue = null;

                            }
                        } catch (Exception e) {


                            new AlertDialog.Builder(ActivityBase.this)
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

            return retValue;
        }

        return null;
    }*/

}
