/*
 * custom logger class for comviva wallet.
 * 
 * can handle formatting of message and tag
 * can block logger for final build
 * can set minimum level of log 
 */
package app.banking.bankmuscat.merchant.logger;

import android.util.Log;

import app.banking.bankmuscat.merchant.common.Wallet;


/**
 * @author U36838
 * 
 */
public final class CLog {
	
	private static final String TAG_PREFIX = "Comviva : Wallet :: ";

	// Determine the minimum leve to be logged
	// set the value from Log.VERBOSE to Log.ASSERT
	private static final int MINIMUM_LEVEL = Wallet.Settings.DEBUG_LOG_LEVEL;

	// Determine if this is a debugging build or not
	// set to false for release builds
	private static final boolean DEBBUGGING_BUILD = Wallet.Settings.DEBUG_ENABLED;

	// format tag
	private static String formatTag(String tag) {
		return TAG_PREFIX + tag;
	}

	// format msg
	private static String formatMsg(String msg) {
		return msg;
	}

	/*
	 * Send a {@link #VERBOSE} log message.
	 * 
	 * @param tag Used to identify the source of a log message. It usually
	 * identifies the class or activity where the log call occurs.
	 * 
	 * @param msg The message you would like logged.
	 */
	public static int v(String tag, String msg) {
		if (!DEBBUGGING_BUILD || Log.VERBOSE < MINIMUM_LEVEL)
			return 0;
		return Log.v(formatTag(tag), formatMsg(msg));
	}

	/**
	 * Send a {@link #VERBOSE} log message and log the exception.
	 * 
	 * @param tag Used to identify the source of a log message. It usually
	 *        identifies the class or activity where the log call occurs.
	 * @param msg The message you would like logged.
	 * @param tr An exception to log
	 */
	public static int v(String tag, String msg, Throwable tr) {
		if (!DEBBUGGING_BUILD || Log.VERBOSE < MINIMUM_LEVEL)
			return 0;
		return Log.v(formatTag(tag), formatMsg(msg), tr);
	}

	/**
	 * Send a {@link #DEBUG} log message.
	 * 
	 * @param tag Used to identify the source of a log message. It usually
	 *        identifies the class or activity where the log call occurs.
	 * @param msg The message you would like logged.
	 */
	public static int d(String tag, String msg) {
		if (!DEBBUGGING_BUILD || Log.DEBUG < MINIMUM_LEVEL)
			return 0;
		return Log.d(formatTag(tag), formatMsg(msg));
	}

	/**
	 * Send a {@link #DEBUG} log message and log the exception.
	 * 
	 * @param tag Used to identify the source of a log message. It usually
	 *        identifies the class or activity where the log call occurs.
	 * @param msg The message you would like logged.
	 * @param tr An exception to log
	 */
	public static int d(String tag, String msg, Throwable tr) {
		if (!DEBBUGGING_BUILD || Log.DEBUG < MINIMUM_LEVEL)
			return 0;
		return Log.d(formatTag(tag), formatMsg(msg), tr);
	}

	/**
	 * Send an {@link #INFO} log message.
	 * 
	 * @param tag Used to identify the source of a log message. It usually
	 *        identifies the class or activity where the log call occurs.
	 * @param msg The message you would like logged.
	 */
	public static int i(String tag, String msg) {
		if (!DEBBUGGING_BUILD || Log.INFO < MINIMUM_LEVEL)
			return 0;


		return Log.i(formatTag(tag), formatMsg(msg));
	}

	/**
	 * Send a {@link #INFO} log message and log the exception.
	 * 
	 * @param tag Used to identify the source of a log message. It usually
	 *        identifies the class or activity where the log call occurs.
	 * @param msg The message you would like logged.
	 * @param tr An exception to log
	 */
	public static int i(String tag, String msg, Throwable tr) {
		if (!DEBBUGGING_BUILD || Log.INFO < MINIMUM_LEVEL)
			return 0;
		return Log.i(formatTag(tag), formatMsg(msg), tr);
	}

	/**
	 * Send a {@link #WARN} log message.
	 * 
	 * @param tag Used to identify the source of a log message. It usually
	 *        identifies the class or activity where the log call occurs.
	 * @param msg The message you would like logged.
	 */
	public static int w(String tag, String msg) {
		if (!DEBBUGGING_BUILD || Log.WARN < MINIMUM_LEVEL)
			return 0;
		return Log.w(formatTag(tag), formatMsg(msg));
	}

	/**
	 * Send a {@link #WARN} log message and log the exception.
	 * 
	 * @param tag Used to identify the source of a log message. It usually
	 *        identifies the class or activity where the log call occurs.
	 * @param msg The message you would like logged.
	 * @param tr An exception to log
	 */
	public static int w(String tag, String msg, Throwable tr) {
		if (!DEBBUGGING_BUILD || Log.WARN < MINIMUM_LEVEL)
			return 0;
		return Log.w(formatTag(tag), formatMsg(msg), tr);
	}

	/**
	 * Send a {@link #WARN} log message and log the exception.
	 * 
	 * @param tag Used to identify the source of a log message. It usually
	 *        identifies the class or activity where the log call occurs.
	 * 
	 * @param tr An exception to log
	 */
	public static int w(String tag, Throwable tr) {
		if (!DEBBUGGING_BUILD || Log.WARN < MINIMUM_LEVEL)
			return 0;
		return Log.w(formatTag(tag), tr);
	}

	/**
	 * Send an {@link #ERROR} log message.
	 * 
	 * @param tag Used to identify the source of a log message. It usually
	 *        identifies the class or activity where the log call occurs.
	 * @param msg The message you would like logged.
	 */
	public static int e(String tag, String msg) {
		if (!DEBBUGGING_BUILD || Log.ERROR < MINIMUM_LEVEL)
			return 0;
		return Log.e(formatTag(tag), formatMsg(msg));
	}

	/**
	 * Send a {@link #ERROR} log message and log the exception.
	 * 
	 * @param tag Used to identify the source of a log message. It usually
	 *        identifies the class or activity where the log call occurs.
	 * @param msg The message you would like logged.
	 * @param tr An exception to log
	 */
	public static int e(String tag, String msg, Throwable tr) {
		if (!DEBBUGGING_BUILD || Log.ERROR < MINIMUM_LEVEL)
			return 0;
		return Log.e(formatTag(tag), formatMsg(msg), tr);
	}

	/**
	 * What a Terrible Failure: Report a condition that should never happen. The
	 * error will always be logged at level ASSERT with the call stack.
	 * Depending on system configuration, a report may be added to the
	 * {@link android.os.DropBoxManager} and/or the process may be terminated
	 * immediately with an error dialog.
	 * 
	 * @param tag Used to identify the source of a log message.
	 * @param msg The message you would like logged.
	 */
	public static int wtf(String tag, String msg) {
		if (!DEBBUGGING_BUILD || Log.ASSERT < MINIMUM_LEVEL)
			return 0;
		return Log.wtf(formatTag(tag), formatMsg(msg));
	}

	/**
	 * What a Terrible Failure: Report an exception that should never happen.
	 * Similar to {@link #wtf(String, String)}, with an exception to log.
	 * 
	 * @param tag Used to identify the source of a log message.
	 * @param tr An exception to log.
	 */
	public static int wtf(String tag, Throwable tr) {
		if (!DEBBUGGING_BUILD || Log.ASSERT < MINIMUM_LEVEL)
			return 0;
		return Log.wtf(formatTag(tag), tr);
	}

	/**
	 * What a Terrible Failure: Report an exception that should never happen.
	 * Similar to {@link #wtf(String, Throwable)}, with a message as well.
	 * 
	 * @param tag Used to identify the source of a log message.
	 * @param msg The message you would like logged.
	 * @param tr An exception to log. May be null.
	 */
	public static int wtf(String tag, String msg, Throwable tr) {
		if (!DEBBUGGING_BUILD || Log.ASSERT < MINIMUM_LEVEL)
			return 0;
		return Log.wtf(formatTag(tag), formatMsg(msg), tr);
	}

}
