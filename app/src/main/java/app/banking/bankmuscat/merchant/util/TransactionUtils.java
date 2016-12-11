package app.banking.bankmuscat.merchant.util;


import app.banking.bankmuscat.merchant.common.Wallet;

/**
 * @author u34608 Util used for Data Transcations using NFC and QR
 */

public final class TransactionUtils {

	/**
	 * Data Delimiter
	 */
	public static final String qrDataDelimiter = "#";

	/* Generates share data for QR code */
	public static String createShareQRCodeData(String amount, String from) {

		Wallet.Data appData = Wallet.Data.getInstance();
		String shareData = QRData.TRANSFER + qrDataDelimiter + from
				+ qrDataDelimiter + amount + qrDataDelimiter
				+ appData.getUniqueId();

		return shareData;
	}

	/* extracts share data */
	public static QRData extractShareQRData(String shareData) {
		QRData qrData = new QRData();
		if (null != shareData && shareData.contains(qrDataDelimiter)) {
			String data[] = shareData.split(qrDataDelimiter);

			if (4 == data.length) {
				// CLog.i("Test", "in");
				qrData.type = Integer.parseInt(data[0]);
				qrData.transferType = data[1];
				qrData.amount = data[2];
				qrData.walletID = data[3];
			}else if(9 == data.length){
				qrData.setMerchantID(data[1]);
				qrData.setMerchantName(data[2]);
				qrData.setMcc(data[3]);
				qrData.setCityName(data[4]);
				qrData.setCountryCode(data[5]);
				qrData.setTxnCurrCode(data[6]);
				qrData.setTxnAmount(data[7]);
				qrData.setPrimaryID(data[8]);
				qrData.setSecondaryID(data[9]);
				qrData.type= 3;
			} else {
				qrData.transferType = null;
			}

		}

		return qrData;
	}

	/* Generates share data for NFC Transfer */
	public static String createShareNFCData(String amount, String from) {

		Wallet.Data appData = Wallet.Data.getInstance();
		String shareData = NFCData.TRANSFER + qrDataDelimiter + from
				+ qrDataDelimiter + amount + qrDataDelimiter
				+ appData.getUniqueId();
		//CLog.i("Create", shareData);
		return shareData;
	}

	/* Extracts share data */
	public static NFCData extractShareNFC(String shareData) {
		NFCData nfcData = new NFCData();
		if (null != shareData && shareData.contains(qrDataDelimiter)) {
			String data[] = shareData.split(qrDataDelimiter);
			//CLog.i("Extract", shareData);
			if (4 == data.length) {
			//	CLog.i("EXT", data[1]);
				nfcData.type = Integer.parseInt(data[0]);
				nfcData.transferType = data[1];
				nfcData.amount = data[2];
				nfcData.walletID = data[3];
			} else {
				nfcData.transferType = null;
			}
		}

		return nfcData;
	}

	/**
	 * @author u34608 Entity to store data used for QR Code Transfer
	 */
	public static final class QRData {
		// type is not yet defined
		public static int UNDEFINED = 0;
		// type for sharing data in P2P using QR
		public static int SHARE = 1;
		// type for transfer data using QR
		public static int TRANSFER = 2;

		// type for Merchant Pay using QR
		public static int MERCHANTPAY = 3;

		// type for pay money using QR
		public static String PAYMONEY = "PayMoney";
		// type for request money using QR
		public static String REQUESTMONEY = "RequestMoney";


		// Data - type of transfer # amount # ID
		public int type = QRData.UNDEFINED;
		public String amount = null;
		public String walletID = null;
		public String transferType = null;


		public  static  String merchantID ="";
		public static String merchantName ="";
		public static String mcc ="";
		public static String cityName ="";
		public static String countryCode ="";
		public static String txnCurrCode ="";
		public static String txnAmount ="";
		public static String primaryID ="";
		public static String secondaryID ="";

		public static String getEmailID() {
			return emailID;
		}

		public static void setEmailID(String emailID) {
			QRData.emailID = emailID;
		}

		public static String emailID ="";

		public static String getMerchantID() {
			return merchantID;
		}

		public static void setMerchantID(String merchantID) {
			QRData.merchantID = merchantID;
		}

		public static  String getMerchantName() {
			return merchantName;
		}

		public static void setMerchantName(String merchantName) {
			QRData.merchantName = merchantName;
		}

		public static String getMcc() {
			return mcc;
		}

		public static void setMcc(String mcc) {
			QRData.mcc = mcc;
		}

		public static String getCityName() {
			return cityName;
		}

		public static void setCityName(String cityName) {
			QRData.cityName = cityName;
		}

		public static String getCountryCode() {
			return countryCode;
		}

		public static void setCountryCode(String countryCode) {
			QRData.countryCode = countryCode;
		}

		public static String getTxnCurrCode() {
			return txnCurrCode;
		}

		public static void setTxnCurrCode(String txnCurrCode) {
			QRData.txnCurrCode = txnCurrCode;
		}

		public static String getTxnAmount() {
			return txnAmount;
		}

		public static void setTxnAmount(String txnAmount) {
			QRData.txnAmount = txnAmount;
		}

		public static String getPrimaryID() {
			return primaryID;
		}

		public static void setPrimaryID(String primaryID) {
			QRData.primaryID = primaryID;
		}

		public static String getSecondaryID() {
			return secondaryID;
		}

		public static void setSecondaryID(String secondaryID) {
			QRData.secondaryID = secondaryID;
		}
	}

	/**
	 * @author u34608 Entity to store data used for NFC Transfer
	 */
	public static final class NFCData {
		// type is not yet defined
		public static int UNDEFINED = 0;
		// type for sharing data in P2P using NFC
		public static int SHARE = 1;
		// type for transfer data using NFC
		public static int TRANSFER = 2;
		// type for pay money using NFC
		public static String PAYMONEY = "PayMoney";
		// type for request money using NFC
		public static String REQUESTMONEY = "RequestMoney";

		// Data - type of transfer # amount # ID
		public int type = NFCData.UNDEFINED;
		public String amount = null;
		public String walletID = null;
		public String transferType = null;
	}
}
