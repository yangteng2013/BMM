/**
 * 
 */
package app.banking.bankmuscat.merchant.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import app.banking.bankmuscat.merchant.common.Wallet;
import app.banking.bankmuscat.merchant.logger.CLog;


/**
 * @author U36838
 * 
 */
public class DbManager extends SQLiteOpenHelper {
	//
	private static final String TAG = "DbManager";
	// Database Version
	private static final int DB_VERSION = 1;
	// Database Name
	private static final String DB_NAME = "ConsumerDB";
	// table name
	private static final String DB_DIC_TABLE = "DICTIONARY";
	// table dictionary columns
	private static final String DB_DIC_COL_KEY = "KEY";
	private static final String DB_DIC_COL_VALUE = "VALUE";

	/**
	 * create DbManager instance automatically.
	 * 
	 * @param context
	 * @return
	 */
	public static DbManager createAuto(Context context) {
		return new DbManager(context);
	}

	//
	private DbManager(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		CLog.i(TAG, "create instance");
	}

	/* (non-Javadoc)
	 * 
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite .SQLiteDatabase) */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// SQL statement to create book table
		final String createTable = "CREATE TABLE " + DB_DIC_TABLE + " ( " +
		// "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
				DB_DIC_COL_KEY + " TEXT PRIMARY KEY, " + DB_DIC_COL_VALUE + " TEXT )";
		CLog.i(TAG, "onCreate : " + createTable);
		// create books table
		db.execSQL(createTable);
		//
		// insert default value
		final String insertDefault = "INSERT INTO " + DB_DIC_TABLE + " VALUES ('STATUS', '" + Wallet.Status.INSTALLED + "');";

		CLog.i(TAG, "insert default : " + insertDefault);
		// create books table
		db.execSQL(insertDefault);
	}

	/* (non-Javadoc)
	 * 
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite .SQLiteDatabase, int, int) */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		CLog.i(TAG, "onUpgrade");
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + DB_DIC_TABLE);
		// Create tables again
		onCreate(db);

	}

	private void setKeyVal(String key, String value) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValue = new ContentValues();
		contentValue.put(DB_DIC_COL_KEY, key);
		contentValue.put(DB_DIC_COL_VALUE, value);
		//
		CLog.i(TAG, "set key : " + key + " <> value : " + value);
		// Inserting Row
		int rowsEffected = db.update(DB_DIC_TABLE, contentValue, DB_DIC_COL_KEY + " = ?", new String[] { key });
		if (0 == rowsEffected)
			db.insert(DB_DIC_TABLE, null, contentValue);
		// Closing database connection
		db.close();
	}

	private String getKeyVal(String key) {
		String result = "";
		String countQuery = "SELECT  " + DB_DIC_COL_VALUE + " FROM " + DB_DIC_TABLE + " WHERE " + DB_DIC_COL_KEY + " = ?";
		SQLiteDatabase db = this.getReadableDatabase();
		final String[] uniqueKey = { key };
		Cursor cursor = db.rawQuery(countQuery, uniqueKey);
		if (null != cursor && cursor.getCount() > 0) {
			cursor.moveToFirst();
			result = cursor.getString(0);
		}
		// Closing database connection
		db.close();
		//
		CLog.i(TAG, "getUniqueId : query : " + countQuery);
		CLog.i(TAG, "getUniqueId : result : " + result);
		//
		return result;
	}

	/**
	 * set unique id to db. If there exist one will replace else creates new one
	 * 
	 * @param
	 */
	public void setUniqueId(String uniqueId) {
		setKeyVal("UNIQUE_ID", uniqueId);
	}

	/**
	 * get unique id if exist else return blank string.
	 * 
	 * @return
	 */
	public String getUniqueId() {
		return getKeyVal("UNIQUE_ID");
	}

	/**
	 * set app status value
	 * @param status
	 */
	public void setAppStatus(int status) {
		setKeyVal("STATUS", status + "");

	}

	/**
	 * get app current status value
	 * @return
	 */
	public int getAppStatus() {
		return Integer.parseInt(getKeyVal("STATUS"));
	}
	
	
	public String getMsisdn() {
		return getKeyVal("MSISDN");
	}

	public void setMsisdn(String msisdn) {
		setKeyVal("MSISDN", msisdn);
	}
	

	/**
	 * set Imei number
	 * @param imei
	 */
	public void setImei(String imei){
		setKeyVal("IMEI", imei);
	}
	
	public String getImei() {
		String imei = getKeyVal("IMEI");
		return null!= imei && 0 < imei.length() ? imei : getMsisdn();
	}
	
	public String getAppKey() {
		return getKeyVal("APPKEY");
	}

	public void setAppKey(String appKey) {
		setKeyVal("APPKEY", appKey);
	}

	public String getIccid() {
		return getKeyVal("ICCID");
	}

	public void setIccid(String iccid) {
		setKeyVal("ICCID", iccid);
	}

	public String getDevid() {
		return getKeyVal("DEVID");
	}

	public void setDevid(String devid) {
		setKeyVal("DEVID", devid);
	}

	public String getPassword() {
		return getKeyVal("PASSWORD");
	}

	public void setPassword(String password) {
		setKeyVal("PASSWORD", password);
	}

	
	
}
