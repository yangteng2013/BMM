package app.banking.bankmuscat.merchant.entity.instrument;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import app.banking.bankmuscat.merchant.entity.BaseData;

public class TransactionHistory extends BaseData implements Serializable {



	@SerializedName("transactionId")
	public String transactionId;
	@SerializedName("favTransactionId")
	public String favTransactionId;
	@SerializedName("transactionType")
	public String transactionType;
	@SerializedName("amount")
	public String amount;
	@SerializedName("transactionStatus")
	public String transactionStatus;
	@SerializedName("transactionDateTime")
	public String transactionDateTime;
	@SerializedName("requestId")
	public String requestId;




	@SerializedName("bearer")
	public String bearer;
	@SerializedName("creditOrDebit")
	public String creditOrDebit;
	@SerializedName("creditAmount")
	public String creditAmount;
	@SerializedName("transactionData")
	public TransactionData transactionData;
	@SerializedName("transactionDate")
	public String transactionDate;




	/*"transactionId": "1610251714001206",
			"transactionType": "SM",
			"amount": "22",
			"transactionStatus": "PENDING",
			"transactionDateTime": "2016-10-25 17:14:10.336",
			"requestId": "SM161025.1714.001208",
			"bearer": "MOBILE",
			"creditOrDebit": "DR",
			"creditAmount": "22",
			"transactionData": {
		"amount": "22",
				"payerMobileNumber": "91310000",
				"remarks": "cvhh",
				"payerName": "AkashNewPayer Smith",
				"payerEmailId": "veragq1@gmail.com",
				"payeeEmailId": "fhj@gg.com",
				"payeeName": ""*/



	public class TransactionData extends BaseData implements Serializable {



		@SerializedName("amount")
		public String amount;
		@SerializedName("payerMobileNumber")
		public String payerMobileNumber;
		@SerializedName("remarks")
		public String remarks;
		@SerializedName("payerName")
		public String payerName;
		@SerializedName("payerEmailId")
		public String payerEmailId;
		@SerializedName("payeeEmailId")
		public String payeeEmailId;
		@SerializedName("payeeName")
		public String payeeName;
		@SerializedName("payeeMobileNumber")
		public String payeeMobileNumber;
		@SerializedName("accountHolderName")
		public String accountHolderName;
		@SerializedName("bankName")
		public String bankName;
		@SerializedName("ifsc")
		public String ifsc;
		@SerializedName("accountNumber")
		public String accountNumber;
		@SerializedName("uniqueAccId")
		public String uniqueAccId;




	}
}






/*
package app.banking.bankmuscat.wallet.entity.instrument;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import app.banking.bankmuscat.wallet.entity.BaseData;

public class TransactionHistory extends BaseData implements Serializable {

	public String getTransactionId() {
		return transactionId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public String getAmount() {
		return amount;
	}

	public String getTransactionStatus() {
		return transactionStatus;
	}

	public String getTransactionDateTime() {
		return transactionDateTime;
	}

	public String getRequestId() {
		return requestId;
	}

	public String getBearer() {
		return bearer;
	}

	public String getCreditOrDebit() {
		return creditOrDebit;
	}

	public String getCreditAmount() {
		return creditAmount;
	}

	public TransactionData getTransactionData() {
		return transactionData;
	}

	@SerializedName("transactionId")
	private String transactionId;
	@SerializedName("transactionType")
	private String transactionType;
	@SerializedName("amount")
	private String amount;
	@SerializedName("transactionStatus")
	private String transactionStatus;
	@SerializedName("transactionDateTime")
	private String transactionDateTime;
	@SerializedName("requestId")
	private String requestId;

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public void setTransactionStatus(String transactionStatus) {
		this.transactionStatus = transactionStatus;
	}

	public void setTransactionDateTime(String transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public void setBearer(String bearer) {
		this.bearer = bearer;
	}

	public void setCreditOrDebit(String creditOrDebit) {
		this.creditOrDebit = creditOrDebit;
	}

	public void setCreditAmount(String creditAmount) {
		this.creditAmount = creditAmount;
	}

	public void setTransactionData(TransactionData transactionData) {
		this.transactionData = transactionData;
	}

	@SerializedName("bearer")
	private String bearer;
	@SerializedName("creditOrDebit")
	private String creditOrDebit;
	@SerializedName("creditAmount")
	private String creditAmount;
	@SerializedName("transactionData")
	private TransactionData transactionData;




	*/
/*"transactionId": "1610251714001206",
			"transactionType": "SM",
			"amount": "22",
			"transactionStatus": "PENDING",
			"transactionDateTime": "2016-10-25 17:14:10.336",
			"requestId": "SM161025.1714.001208",
			"bearer": "MOBILE",
			"creditOrDebit": "DR",
			"creditAmount": "22",
			"transactionData": {
		"amount": "22",
				"payerMobileNumber": "91310000",
				"remarks": "cvhh",
				"payerName": "AkashNewPayer Smith",
				"payerEmailId": "veragq1@gmail.com",
				"payeeEmailId": "fhj@gg.com",
				"payeeName": ""*//*




	public class TransactionData extends BaseData implements Serializable {

		public String getAmount() {
			return amount;
		}

		public String getPayerMobileNumber() {
			return payerMobileNumber;
		}

		public String getRemarks() {
			return remarks;
		}

		public String getPayerName() {
			return payerName;
		}

		public String getPayerEmailId() {
			return payerEmailId;
		}

		public String getPayeeEmailId() {
			return payeeEmailId;
		}

		public String getPayeeName() {
			return payeeName;
		}

		public void setAmount(String amount) {
			this.amount = amount;
		}

		public void setPayerMobileNumber(String payerMobileNumber) {
			this.payerMobileNumber = payerMobileNumber;
		}

		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}

		public void setPayerName(String payerName) {
			this.payerName = payerName;
		}

		public void setPayerEmailId(String payerEmailId) {
			this.payerEmailId = payerEmailId;
		}

		public void setPayeeEmailId(String payeeEmailId) {
			this.payeeEmailId = payeeEmailId;
		}

		public void setPayeeName(String payeeName) {
			this.payeeName = payeeName;
		}

		@SerializedName("amount")
		private String amount;
		@SerializedName("payerMobileNumber")
		private String payerMobileNumber;
		@SerializedName("remarks")
		private String remarks;
		@SerializedName("payerName")
		private String payerName;
		@SerializedName("payerEmailId")
		private String payerEmailId;
		@SerializedName("payeeEmailId")
		private String payeeEmailId;
		@SerializedName("payeeName")
		private String payeeName;




	}
	}



*/
