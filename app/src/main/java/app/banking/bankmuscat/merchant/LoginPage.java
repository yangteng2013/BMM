package app.banking.bankmuscat.merchant;/*
package com.comviva.wallet;

import java.util.ArrayList;
import java.util.List;

import com.comviva.hce.httpmanager.ConnectionDetector;
import com.comviva.wallet.util.CustomeProgressBar;
import com.comviva.wallet.util.constant.GlobalVariable;



import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.BadTokenException;
import android.webkit.WebView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;



public class LoginPage extends Activity implements OnClickListener {

	// flag for Internet connection status
	Boolean isInternetPresent = false;
	// Connection detector class
	ConnectionDetector cd;

	String[] member_names;
	TypedArray profile_pics;
	String[] statues;
	String[] contactType;
	List<RowItem> rowItems;
	ListView mylistview;
	RelativeLayout skip1;
	RelativeLayout facebookLayout;
	RelativeLayout googleLayout;
	RelativeLayout SignInLayout;
	RelativeLayout SignUpLayout;
	GoogleLoginActivity gpLogin; 
	SharedPreferences preferences;
	ProgressDialog pdialog;
	private static Context mContext;
	private static Activity mActivity;

	WebView web;
	Dialog auth_dialog;
	SharedPreferences pref;
	FacebookLoginOption fblogin;
	CustomeProgressBar cust;

	
	CallbackManager callbackManager;
	public static String activityToOpen;

	
	 * public void slideToLeft(View view) { TranslateAnimation animate = new
	 * TranslateAnimation(view.getWidth(), 0, 0, 0); animate.setDuration(500);
	 * animate.setFillAfter(true); view.startAnimation(animate); }
	 

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login_page);
		GlobalVariable.setContext(LoginPage.this);
		Intent intent = getIntent();
		if (null != intent) {
			activityToOpen = intent.getAction();
		}
		 gpLogin = GoogleLoginActivity.getInstance(); 
		
		 Thread.setDefaultUncaughtExceptionHandler(new UnCaughtException(
		 LoginPage.this));
		 
		cust = new CustomeProgressBar();
		
		 * gab = new GoogleAnalyticsBean(this);
		 * gab.GASendScreenView(getString(R.string.login_screen)); final
		 * LinearLayout layout = (LinearLayout)
		 * findViewById(R.id.linearlayout0); // layout.setVisibility(View.GONE);
		 * layout.post(new Runnable() {
		 * 
		 * @Override public void run() { slideToLeft(layout);
		 * 
		 * AnimationDrawable animationDrawable =
		 * (AnimationDrawable)layout.getDrawable(); animationDrawable.start();
		 * 
		 * } }); // slideToLeft(layout);
		 
		pref = getSharedPreferences("AppPref", MODE_PRIVATE);
		rowItems = new ArrayList<RowItem>();
		cd = new ConnectionDetector(getApplicationContext());

		profile_pics = getResources().obtainTypedArray(R.array.pics);
		preferences = PreferenceManager.getDefaultSharedPreferences(this);

		facebookLayout = (RelativeLayout) findViewById(R.id.facebookContainer);
		facebookLayout.setOnClickListener(this);
		googleLayout = (RelativeLayout) findViewById(R.id.googleContainer);
		googleLayout.setOnClickListener(this);
		SignInLayout = (RelativeLayout) findViewById(R.id.signInCOntainer);
		SignInLayout.setOnClickListener(this);
		SignUpLayout = (RelativeLayout) findViewById(R.id.signUpContainer);
		SignUpLayout.setOnClickListener(this);
	}

	
	 * @Override public void onStart() { super.onStart();
	 * Session.getActiveSession().addCallback(statusCallback); }
	 

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public void onBackPressed() {
		finish();
		Intent i = new Intent(getApplicationContext(), Exit.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(i);
	}

	public ProgressDialog createProgressDialog1() {
		pdialog = new ProgressDialog(LoginPage.this);
		try {
			// pdialog.setMessage("Processing");
			pdialog.show();
			pdialog.setContentView(R.layout.custom_progressbar1);
			Window window = pdialog.getWindow();
			window.setLayout(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
		} catch (BadTokenException e) {
		}
		pdialog.setCancelable(false);
		// pdialog.setContentView(R.layout.progressdialog);
		return pdialog;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == GoogleLoginActivity.RC_SIGN_IN) {
			if (resultCode == RESULT_OK) {
				// If the error resolution was successful we should continue
				// processing errors.
				GoogleLoginActivity.mSignInProgress = GoogleLoginActivity.STATE_SIGN_IN;
			} else {
				// If the error resolution was not successful or the user
				// canceled,
				// we should stop processing errors.

				GoogleLoginActivity.mSignInProgress = GoogleLoginActivity.STATE_DEFAULT;
			}

			if (!GoogleLoginActivity.mGoogleApiClient.isConnecting()) {
				// If Google Play services resolved the issue with a dialog then
				// onStart is not called so we need to re-attempt connection
				// here.
				GoogleLoginActivity.mGoogleApiClient.connect();
			}
		} else if (requestCode == FacebookLoginOption.FACEBOOK_REQCODE) {

			FacebookLoginOption.callbackManager.onActivityResult(requestCode,
					resultCode, data);
		}
		if (requestCode == 2) {
			preferences = PreferenceManager.getDefaultSharedPreferences(this);
			String FlagValue = preferences.getString("Flag Value", "");
			if (FlagValue.equals("True")) {
				finish();
			}
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

	}
	
	
	public static void showToast(String msg, int duration) {
			Toast.makeText(GlobalVariable.getActivity(), msg, duration).show();
		}
	
	@Override
	public void onClick(View v) {
		isInternetPresent = cd.isConnectingToInternet();
		cust = new CustomeProgressBar();
		if (isInternetPresent) {
			// Internet Connection is Present
			// make HTTP requests

			switch (v.getId()) {
			case R.id.facebookContainer:
				// createProgressDialog1();
				if (FacebookLoginOption.enableFB) {
					FacebookLoginOption.createInstance();
				}
				break;
			case R.id.googleContainer:
				
				  cust.createProgressDialog(LoginPage.this);
				  CustomeProgressBar.getInstance().createProgressDialog(this);
				  gpLogin.signInWithGplus();
				 
				break;
			case R.id.signInCOntainer:
				
				 * Intent intent_login = new Intent(LoginPage.this,
				 * Zerch_login_page.class); startActivityForResult(intent_login,
				 * 2);
				 
				break;
			case R.id.signUpContainer:
				
				 * Intent intent_register = new Intent(LoginPage.this,
				 * Registration.class); startActivityForResult(intent_register,
				 * 2);
				 
				break;
			}
		} else {
			switch (v.getId()) {
			case R.id.facebookContainer:
				showToast(GlobalVariable.getMessage(R.string.no_internet),
						Toast.LENGTH_SHORT);
				break;
			case R.id.googleContainer:
				showToast(GlobalVariable.getMessage(R.string.no_internet),
						Toast.LENGTH_SHORT);
				break;
			case R.id.signInCOntainer:
				
				 * Intent intent_login = new Intent(LoginPage.this,
				 * Zerch_login_page.class); startActivity(intent_login);
				 
				break;
			case R.id.signUpContainer:
				
				 * Intent intent_register = new Intent(LoginPage.this,
				 * Registration.class); startActivity(intent_register);
				 
				break;
			}
		}
	}

}
*/
