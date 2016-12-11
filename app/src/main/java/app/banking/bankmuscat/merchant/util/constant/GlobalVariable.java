package app.banking.bankmuscat.merchant.util.constant;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.preference.PreferenceManager;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

public class GlobalVariable {
	public static Resources resources;

	private static Context mContext;
	private static Activity mActivity;
	private static Activity prevActivity;
	private static boolean isLoggedIn = false;
	private static boolean isLatLong = false;
	public static boolean isFirsttime = false;
	public static boolean requestForCity = false;
	public static final String DATE_FORMAT = "dd-MM-yyyy";
	public static SimpleDateFormat SYSTEM_DATE_FORMAT;
	private static String userID;

	public static boolean debug = true; // false when moving to production

	/**
	 * Method to get the String for the given id from values
	 * 
	 * @param id
	 * @return the equivalent string for the given id
	 */
	public static String getMessage(int id) {
		String msg = "";
		if (resources != null) {
			msg = resources.getString(id);
		} else {
			if (mActivity != null) {
				resources = mActivity.getResources();
				try {
					msg = resources.getString(id);
				} catch (NotFoundException e) {
					if (GlobalVariable.debug) {
						e.printStackTrace();
					}
				}
			}
		}
		return msg;
	}

	/**
	 * Method to get the Integer for the given id from values
	 * 
	 * @param id
	 * @return the equivalent Integer for the given id
	 */
	public static int getInteger(int id) {
		int value = 0;
		if (resources != null) {
			value = resources.getInteger(id);
		} else {
			if (null != mActivity) {
				resources = mActivity.getResources();
				try {
					value = resources.getInteger(id);
				} catch (NotFoundException e) {
					if (GlobalVariable.debug) {
						e.printStackTrace();
					}
				}
			}
		}
		return value;
	}

	// Set the current Activity
	public static Context getContext() {
		return mContext;
	}

	public static void setContext(Activity context) {
		mContext = context;
		setActivity(context);
	}

	public static Activity getActivity() {
		return mActivity;
	}

	public static void setActivity(Activity mActivity) {
		GlobalVariable.mActivity = mActivity;
	}

	// For Logged In Variable
	public static boolean isLoggedIn() {
		return isLoggedIn;
	}

	public static void setLoggedIn(boolean isLoggedIn) {
		GlobalVariable.isLoggedIn = isLoggedIn;
	}

	// For Latitude and Longitude boolean variable saving in
	// Shared Preference
	public static boolean isLatLong() {
		return isLatLong;
	}

	public static void setLatLong(boolean isLatLong) {
		GlobalVariable.isLatLong = isLatLong;
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(mActivity);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putBoolean("LatLong", isLatLong);
		editor.commit();
	}

	public static Activity getPrevActivity() {
		return prevActivity;
	}

	public static void setPrevActivity(Activity prevActivity) {
		GlobalVariable.prevActivity = prevActivity;
	}

	public static void saveChanlIdFromPref(String channelId, Context context) {
		SharedPreferences preferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = preferences.edit();
		editor.putString("channelId", channelId);
		editor.commit();

	}

	public static String getChanlIdFromPref(Application app) {
		String id = null;
		SharedPreferences preferences = null;
		if (mActivity == null) {
			preferences = PreferenceManager.getDefaultSharedPreferences(app);
		} else if (mActivity != null) {
			preferences = PreferenceManager
					.getDefaultSharedPreferences(mActivity);
			id = preferences.getString("channelId", null);
			return id;
		}
		return id;
	}

	public static String getUniqueId(Context context) {
		String id = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			// get current date time with Date()
			// Date date = new Date();
			// get current date time with Calendar()
			Calendar cal = Calendar.getInstance();
			id = dateFormat.format(cal.getTime());

		} catch (Exception e) {
			id = randInt(5, 15) + "";
		}
		saveChanlIdFromPref(id, context); // to be saved
		return id;
	}

	public static int randInt(int min, int max) {
		Random rand = new Random();
		int randomNum = rand.nextInt((max - min) + 1) + min;
		return randomNum;
	}

	public static String getUserID() {
		if (null == userID || "".equals(userID)) {
			SharedPreferences preferences = PreferenceManager
					.getDefaultSharedPreferences(mActivity);
			userID = preferences.getString("userid", "");
		}
		return userID;
	}
}
