/**
 * 
 */
package app.banking.bankmuscat.merchant.common;

import android.content.Context;
import android.util.Log;

import java.math.BigDecimal;
import java.util.ArrayList;

import app.banking.bankmuscat.R;
import app.banking.bankmuscat.merchant.api.APIManager;
import app.banking.bankmuscat.merchant.db.DbManager;
import app.banking.bankmuscat.merchant.entity.instrument.Address;
import app.banking.bankmuscat.merchant.entity.instrument.Bank;
import app.banking.bankmuscat.merchant.entity.instrument.Card;

import app.banking.bankmuscat.merchant.entity.instrument.Merchant;

import app.banking.bankmuscat.merchant.entity.instrument.Product;

import app.banking.bankmuscat.merchant.entity.instrument.Transaction;
import app.banking.bankmuscat.merchant.entity.instrument.User;

/**
 * @author U36838
 * 
 */
public interface Wallet {

	public static final class App {
		// application context
		private static Context appContext;

		//
		public static Context getContext() {
			return appContext;
		}

		// on application initialize App.initialize
		public static final void initialize(Context context) {
			//
			appContext = context;
			//
			Data.syncRead(context);
		}

		// application init code
		public static final void onBegin(Context context) {
			// initialize db and get one time values
			// check for unique in db
			Data.syncRead(context);
		}

		//
	}

	public static final class Settings {
		public static final boolean DEBUG_ENABLED = true;
		public static final int DEBUG_LOG_LEVEL = Log.VERBOSE;
		public static final boolean DISABLE_API_ERRORS = false;
		//
	}

	/**
	 * Application cycle states
	 */
	public static final class Status {
		public static final int INSTALLED = 0;
		public static final int OTP_SUCCESS = 1;
		public static final int PROFILE_SUCCESS = 2;
		public static final int PIN_CREATED = 3;
		public static final int REGISTRAION_COMPLETED = 4;
		public static final int NFC_INIT = 5;
		public static int CHECKNFC = 0;

	}

	public static final class Mode {
		public static final int NORMAL = 0;
		public static final int CHECKOUT = 1;
		public static int Amount = 0;
		public static int Alert = 0;
		public static int Backflag = 0;
		public static int position = 0;
		public static int unlockflag = 0;
		public static int flag = 8;
		public static String loyalty = null;
		
		public static Boolean isNFCWallet=true;

	}

	public static class Data {

		public static void syncRead(Context context) {
			/*DbManager dbManager = DbManager.createAuto(context);
			Data me = Wallet.Data.getInstance();
			//
			// uniqueId
			String stringValue = dbManager.getUniqueId();
			if (null != stringValue && !stringValue.trim().isEmpty())
				me.setUniqueId(stringValue.trim());
			// MSISDN
			stringValue = dbManager.getMsisdn();
			if (null != stringValue && !stringValue.trim().isEmpty())
				me.setMsisdn(stringValue.trim());
			// IMEI
			stringValue = dbManager.getImei();
			if (null != stringValue && !stringValue.trim().isEmpty())
				me.setImei(stringValue.trim());
			// APPKEY
			stringValue = dbManager.getAppKey();
			if (null != stringValue && !stringValue.trim().isEmpty())
				me.setAppKey(stringValue.trim());
			// ICCID
			stringValue = dbManager.getIccid();
			if (null != stringValue && !stringValue.trim().isEmpty())
				me.setIccId(stringValue.trim());
			// DEVID
			stringValue = dbManager.getDevid();
			if (null != stringValue && !stringValue.trim().isEmpty())
				me.setDevId(stringValue.trim());
			// PASSWORD
			stringValue = dbManager.getPassword();
			if (null != stringValue && !stringValue.trim().isEmpty())
				me.setPwd(stringValue.trim());*/
		}

		static void syncWrite(Context context) {
			DbManager dbManager = DbManager.createAuto(context);
			Data me = Wallet.Data.getInstance();
			//
			// uniqueId
			String stringValue = me.getUniqueId();
			if (null != stringValue && !stringValue.trim().isEmpty())
				dbManager.setUniqueId(stringValue.trim());
			// MSISDN
			stringValue = me.getMsisdn();
			if (null != stringValue && !stringValue.trim().isEmpty())
				dbManager.setMsisdn(stringValue.trim());
			// IMEI
			stringValue = me.getImei();
			if (null != stringValue && !stringValue.trim().isEmpty())
				dbManager.setImei(stringValue.trim());
			// APPKEY
			stringValue = me.getAppKey();
			if (null != stringValue && !stringValue.trim().isEmpty())
				dbManager.setAppKey(stringValue.trim());
			// ICCID
			stringValue = me.getIccId();
			if (null != stringValue && !stringValue.trim().isEmpty())
				dbManager.setIccid(stringValue.trim());
			// DEVID
			stringValue = me.getDevId();
			if (null != stringValue && !stringValue.trim().isEmpty())
				dbManager.setDevid(stringValue.trim());
			// PASSWORD
			stringValue = me.getPwd();
			if (null != stringValue && !stringValue.trim().isEmpty())
				dbManager.setPassword(stringValue.trim());
		}

		// Splash screen timer
		public static final int SPLASH_SCREEN_TIME_OUT = 2000;
		//
		//
		// Holds the instance of APIManager
		private static Data instance;

		// get instance
		public static Data getInstance() {
			return createInstance();
		}

		private static Data createInstance() {
			if (instance == null)
				instance = new Data();
			return instance;
		}

		public static Data ClearData() {
			instance = null;
			return instance;
		}

		private ErrorMessageCodes errorCode = ErrorMessageCodes
				.createInstance();

		private String appKey = "";
		private String iccId = "";
		private String devId = "";
		private String pwd = "";
		private String uniqueId = "";
		private String msisdn = "";
		private String imei = "";
		private String userName = "";

		public User getUser() {
			return user;
		}

		public void setUser(User user) {
			this.user = user;
		}

		private User user;


		///abc

		private APIManager.ResponseData userData = null;

		public int getSvnBalance() {
			return svnBalance;
		}

		public void setSvnBalance(int svnBalance) {
			this.svnBalance = svnBalance;
		}

		private int svnBalance;

		//
		//
		private Boolean ValidateMsisdn = false;
		private String serviceId = "";
		private String email = "";
		private String instrumentId = "";
		private String instrumentType = "";
		private String uniqueAcctId = "";
		private String addressId = "";
		private String nfcTransferData = "";
		private Boolean AdHocPayment = false;




		private int goldSeatCount;

		private int silverSeatCount;

		private int appMode = Mode.NORMAL;

		public boolean isWebAllowed = false;
		public boolean isSocialAccountsON = false;
		private Card defaultCard;
		private Address defaultAddress;
		private Address selectedAddressCheckout;
		private Card selectedCardCheckout;

		public Transaction getCurrentTransaction() {
			return currentTransaction;
		}

		public void setCurrentTransaction(Transaction currentTransaction) {
			this.currentTransaction = currentTransaction;
		}

		//abc
		private Transaction currentTransaction;

		private ArrayList<Card> checkoutCardList = null;
		private ArrayList<Address> checkoutAddressList = null;







		private ArrayList<Product> cartValues;

		private ArrayList<Merchant> filteredMerchantList;
		private BigDecimal total;
		private BigDecimal staticTotal;
		private BigDecimal staticTotalDelItem;
		private String merchantIntent = "";
		private String intentConfirm = "";
		private String shopFrmIntent = "";
		private ArrayList<Card> walletCardList= new ArrayList<Card>();
		private ArrayList<Card> wallethceCardList= new ArrayList<Card>();
		private ArrayList<Bank> walletBankList;
		private ArrayList<Card> walletDefaultCardList;

		private String myMoneyLoadAmount = "0";
		private String pinIntentString = "";
		private int selectedMovieImage;



		//abc
		public void setUserData(APIManager.ResponseData data) {
			 userData = data;
		}
		public APIManager.ResponseData getUserData() {
			return userData;
		}



		public String getPwd() {
			return pwd;
		}

		public void setPwd(String pwd) {
			this.pwd = pwd;
		}

		public String getDevId() {
			return devId;
		}

		public void setDevId(String devId) {
			this.devId = devId;
		}

		public String getIccId() {
			return iccId;
		}

		public void setIccId(String iccId) {
			this.iccId = iccId;
		}

		public String getAppKey() {
			return appKey;
		}

		public void setAppKey(String appKey) {
			this.appKey = appKey;
		}

		public boolean isWebAllowed() {
			return isWebAllowed;
		}

		public void setWebAllowed(boolean isWebAllowed) {
			this.isWebAllowed = isWebAllowed;
		}

		public boolean isSocialAccountsON() {
			return isSocialAccountsON;
		}

		public void setSocialAccountsON(boolean isSocialAccountsON) {
			this.isSocialAccountsON = isSocialAccountsON;
		}

		public String getMsisdn() {
			return msisdn;
		}

		public void setMsisdn(String msisdn) {
			this.msisdn = msisdn;
		}

		public String getImei() {
			return imei;
		}

		public void setImei(String imei) {
			this.imei = imei;
		}

		public String getServiceId() {
			return serviceId;
		}

		public void setServiceId(String serviceId) {
			this.serviceId = serviceId;
		}

		public String getUniqueId() {
			return uniqueId;
		}

		public void setUniqueId(String uniqueId) {
			this.uniqueId = uniqueId;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getInstrumentId() {
			return instrumentId;
		}

		public void setInstrumentId(String instrumentid) {
			this.instrumentId = instrumentid;
		}

		/**
		 * get error message of respective error
		 */
		public String getErrorMessage(String error) {
			Context c = Wallet.App.getContext();
			
			try
			{

			if (APIManager.SOAPOperationData.ERROR_GENERAL_EXCEPTION
					.equalsIgnoreCase(error)) {
				return c.getString(R.string.general_exception);
			}
			else if (APIManager.SOAPOperationData.ERROR_REQUEST_ERROR
					.equalsIgnoreCase(error)) {
				return c.getString(R.string.error_requesterror);
			} else if (APIManager.SOAPOperationData.ERROR_NOBODYIN.equalsIgnoreCase(error)) {
				return c.getString(R.string.error_bodyin);
			}
			try {
				return errorCode.getErrorMessage(Integer.parseInt(error));
			} catch (NumberFormatException e) {
				return error;
			}
			}catch(Exception e)
			{
				return e.toString();
			}
		}

		public String getInstrumentType() {
			return instrumentType;
		}

		public void setInstrumentType(String instrumentType) {
			this.instrumentType = instrumentType;
		}

		public String getUniqueAcctId() {
			return uniqueAcctId;
		}

		public void setUniqueAcctId(String uniqueAcctId) {
			this.uniqueAcctId = uniqueAcctId;
		}

		public String getAddressId() {
			return addressId;
		}

		public void setAddressId(String addressId) {
			this.addressId = addressId;
		}

		public int getAppMode() {
			return appMode;
		}

		public void setAppMode(int appMode) {
			this.appMode = appMode;
		}

		public Card getDefaultCard() {
			return defaultCard;
		}

		public void setDefaultCard(Card defaultCard) {
			this.defaultCard = defaultCard;
		}

		public Address getDefaultAddress() {
			return defaultAddress;
		}

		public void setDefaultAddress(Address defaultAddress) {
			this.defaultAddress = defaultAddress;
		}

		public Address getSelectedAddressCheckout() {
			return selectedAddressCheckout;
		}

		public void setSelectedAddressCheckout(Address selectedAddressCheckout) {
			this.selectedAddressCheckout = selectedAddressCheckout;
		}

		public Card getSelectedCardCheckout() {
			return selectedCardCheckout;
		}

		public void setSelectedCardCheckout(Card selectedCardCheckout) {
			this.selectedCardCheckout = selectedCardCheckout;
		}

		public ArrayList<Product> getCartValues() {
			return cartValues;
		}

		public void setCartValues(ArrayList<Product> cartValues) {
			this.cartValues = cartValues;
		}



		public BigDecimal getTotal() {
			return total;
		}

		public void setTotal(BigDecimal total) {
			this.total = total;
		}



		public BigDecimal getStaticTotal() {
			return staticTotal;
		}

		public void setStaticTotal(BigDecimal staticTotal) {
			this.staticTotal = staticTotal;
		}

		public BigDecimal getStaticTotalDelItem() {
			return staticTotalDelItem;
		}

		public void setStaticTotalDelItem(BigDecimal staticTotalDelItem) {
			this.staticTotalDelItem = staticTotalDelItem;
		}

		public String getIntentConfirm() {
			return intentConfirm;
		}

		public void setIntentConfirm(String shopCartIntentConfirm) {
			intentConfirm = shopCartIntentConfirm;
		}

		public ArrayList<Merchant> getFilteredMerchantList() {
			return filteredMerchantList;
		}

		public void setFilteredMerchantList(
				ArrayList<Merchant> filteredMerchantList) {
			this.filteredMerchantList = filteredMerchantList;
		}

		public String getMerchantIntent() {
			return merchantIntent;
		}

		public void setMerchantIntent(String merchantIntent) {
			this.merchantIntent = merchantIntent;
		}

		public String getNfcTransferData() {
			return nfcTransferData;
		}

		public void setNfcTransferData(String nfcTransferData) {
			this.nfcTransferData = nfcTransferData;
		}

		public String getShopFrmIntent() {
			return shopFrmIntent;
		}

		public void setShopFrmIntent(String shopFrmIntent) {
			this.shopFrmIntent = shopFrmIntent;
		}

		public ArrayList<Card> getWalletCardList() {
			return walletCardList;
		}

		
		public void setWalletCardList(ArrayList<Card> walletCardList) {
			this.walletCardList=walletCardList;
		}
		public ArrayList<Bank> getWalletBankList() {
			return walletBankList;
		}
		public void setWallethceCardList(ArrayList<Card> hceCardList) {
			this.wallethceCardList=hceCardList;
		}
		public ArrayList<Card> getWallethceCardList() {
			return wallethceCardList;
		}

		public void setWalletBankList(ArrayList<Bank> walletBankList) {
			this.walletBankList = walletBankList;
		}

		public ArrayList<Card> getWalletDefaultCardList() {
			return walletDefaultCardList;
		}

		public void setWalletDefaultCardList(
				ArrayList<Card> walletDefaultCardList) {
			this.walletDefaultCardList = walletDefaultCardList;
		}



		public int getGoldSeatCount() {
			return goldSeatCount;
		}

		public void setGoldSeatCount(int goldSeatCount) {
			this.goldSeatCount = goldSeatCount;
		}

		public int getSilverSeatCount() {
			return silverSeatCount;
		}

		public void setSilverSeatCount(int silverSeatCount) {
			this.silverSeatCount = silverSeatCount;
		}

		public String getPinIntentString() {
			return pinIntentString;
		}

		public void setPinIntentString(String pinIntentString) {
			this.pinIntentString = pinIntentString;
		}

		public int getSelectedMovieImage() {
			return selectedMovieImage;
		}

		public void setSelectedMovieImage(int selectedMovieImage) {
			this.selectedMovieImage = selectedMovieImage;
		}

		public String getMyMoneyLoadAmount() {
			return myMoneyLoadAmount;
		}

		public void setMyMoneyLoadAmount(String myMoneyLoadAmount) {
			this.myMoneyLoadAmount = myMoneyLoadAmount;
		}



		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public ArrayList<Card> getCheckoutCardList() {
			return checkoutCardList;
		}

		public void setCheckoutCardList(ArrayList<Card> checkoutCardList) {
			this.checkoutCardList = checkoutCardList;
		}

		public ArrayList<Address> getCheckoutAddressList() {
			return checkoutAddressList;
		}

		public void setCheckoutAddressList(
				ArrayList<Address> checkoutAddressList) {
			this.checkoutAddressList = checkoutAddressList;
		}

		public Boolean getAdHocPayment() {
			return AdHocPayment;
		}

		public void setAdHocPayment(Boolean adHocPayment) {
			AdHocPayment = adHocPayment;
		}

		public Boolean getValidateMsisdn() {
			return ValidateMsisdn;
		}

		public void setValidateMsisdn(Boolean validateMsisdn) {
			ValidateMsisdn = validateMsisdn;
		}

	}
}
