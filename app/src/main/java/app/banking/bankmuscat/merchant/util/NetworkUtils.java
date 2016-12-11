package app.banking.bankmuscat.merchant.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class NetworkUtils {

	/**
	 * check Internet connection status
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isConnectedtoInternet(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		//
		return null != activeNetwork && activeNetwork.isConnectedOrConnecting();
	}
}
