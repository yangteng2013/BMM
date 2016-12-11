package app.banking.bankmuscat.merchant.entity.instrument;



import org.ksoap2.serialization.SoapObject;

import app.banking.bankmuscat.merchant.entity.BaseData;

public class TransactionHistoryItems extends BaseData{
	
	private String txnDateTime = "";
	private String txnId = "";
	private String txnStatus = "";
	private String txnAmount = "";
	private String orderNumber="";
	private String payerId = "";
	private String payeeId = "";
	private String instrumentNumber = "";
	private String productName = "";
	private String merchantCategory="";
	private String merchantName="";
	
	public TransactionHistoryItems() {
		super();
	}
	
	public static TransactionHistoryItems create(SoapObject txnHistoryObject) {
		TransactionHistoryItems txnHistoryItem = null;
		if (txnHistoryObject.hasProperty("txnId")) {
			
			txnHistoryItem = new TransactionHistoryItems();
			txnHistoryItem.setTxnId(txnHistoryObject
					.getPropertyAsString("txnId"));
		
			if (txnHistoryObject.hasProperty("txnDateTime"))
				txnHistoryItem.setTxnDateTime(txnHistoryObject
						.getPropertyAsString("txnDateTime"));

			if (txnHistoryObject.hasProperty("txnStatus"))
				txnHistoryItem.setTxnStatus(txnHistoryObject.
						getPropertyAsString("txnStatus"));
			
			if (txnHistoryObject.hasProperty("txnAmount"))
				txnHistoryItem.setTxnAmount(txnHistoryObject
						.getPropertyAsString("txnAmount"));
			
			if (txnHistoryObject.hasProperty("orderNumber"))
				txnHistoryItem.setOrderNumber(txnHistoryObject
						.getPropertyAsString("orderNumber"));
			
			if (txnHistoryObject.hasProperty("payerId"))
				txnHistoryItem.setPayerId(txnHistoryObject.
						getPropertyAsString("payerId"));
			
			if (txnHistoryObject.hasProperty("payeeId"))
				txnHistoryItem.setPayeeId(txnHistoryObject
						.getPropertyAsString("payeeId"));
			
			if (txnHistoryObject.hasProperty("instrumentNumber"))
				txnHistoryItem.setInstrumentNumber(txnHistoryObject
						.getPropertyAsString("instrumentNumber"));
			
			if (txnHistoryObject.hasProperty("productName"))
				txnHistoryItem.setProductName(txnHistoryObject.
						getPropertyAsString("productName"));
			
			if (txnHistoryObject.hasProperty("merchantCategory"))
				txnHistoryItem.setMerchantCategory(txnHistoryObject
						.getPropertyAsString("merchantCategory"));
			
			if (txnHistoryObject.hasProperty("merchantName"))
				txnHistoryItem.setMerchantName(txnHistoryObject
						.getPropertyAsString("merchantName"));
				
		}
		return txnHistoryItem;

	}
	
	
	
	public void setTxnId(String txnId) {
		this.txnId = txnId;
	}
	
	public String getTxnId() {
		return txnId;
	}
	
	public void setTxnDateTime(String txnDateTime) {
		this.txnDateTime = txnDateTime;
	}
	
	public String getTxnDateTime() {
		return txnDateTime;
	}
	
	public void setTxnStatus(String txnStatus) {
		this.txnStatus = txnStatus;
	}
	
	public String getTxnStatus() {
		return txnStatus;
	}
	
	public void setTxnAmount(String txnAmount) {
		this.txnAmount = txnAmount;
	}
	
	public String getTxnAmount() {
		return txnAmount;
	}
	
	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}
	
	public String getOrderNumber() {
		return orderNumber;
	}
	
	public void setPayerId(String payerId) {
		this.payerId = payerId;
	}
	
	public String getPayerId() {
		return payerId;
	}
	
	public void setPayeeId(String payeeId) {
		this.payeeId = payeeId;
	}
	
	public String getPayeeId() {
		return payeeId;
	}
	
	public void setInstrumentNumber(String instrumentNumber) {
		this.instrumentNumber = instrumentNumber;
	}
	
	public String getInstrumentNumber() {
		return instrumentNumber;
	}
	
	public void setProductName(String productName) {
		this.productName = productName;
	}
	
	public String getProductName() {
		return productName;
	}
	
	public void setMerchantCategory(String merchantCategory) {
		this.merchantCategory = merchantCategory;
	}
	
	public String getMerchantCategory() {
		return merchantCategory;
	}
	
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	
	public String getMerchantName() {
		return merchantName;
	}
	
	public TransactionHistoryItems getTransactionHistory() {
		return null;
	}
	
}
