package app.banking.bankmuscat.merchant.entity.instrument;


import org.json.simple.JSONObject;
import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;

import app.banking.bankmuscat.merchant.entity.BaseData;

public class Transaction extends BaseData implements Serializable {

	public boolean isSaved() {
		return isSaved;
	}

	public void setSaved(boolean saved) {
		isSaved = saved;
	}

	private boolean isSaved = false;
	private String userName = "";
	private String accountNumber = "";
	private String bankName = "";
	private boolean isDefault = false;
	private String accountType = "";
	private String uniqueAccountId = "";
	private String ifsc = "";

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	private int count;

	public int getFinalcount() {
		return finalcount;
	}

	public void setFinalcount(int finalcount) {
		this.finalcount = finalcount;
	}

	private int finalcount;

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	private String mode = "";

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	private String transactionId = "";

	public String getRequestMoneyId() {
		return requestMoneyId;
	}

	public void setRequestMoneyId(String requestMoneyId) {
		this.requestMoneyId = requestMoneyId;
	}

	private String requestMoneyId = "";

	public String getFavTransactionId() {
		return favTransactionId;
	}

	public void setFavTransactionId(String favTransactionId) {
		this.favTransactionId = favTransactionId;
	}

	private String favTransactionId = "";

	public String getChipinId() {
		return chipinId;
	}

	public void setChipinId(String chipinId) {
		this.chipinId = chipinId;
	}

	private String nickName = "";
	private String typeOfBank ="";
	private String chipinId="";

	public String getPin() {
		return pin;
	}

	public String getEmail() {
		return email;
	}

	public String getIsChargeApplicable() {
		return isChargeApplicable;
	}

	private String pin = "";

	public void setPin(String pin) {
		this.pin = pin;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setIsChargeApplicable(String isChargeApplicable) {
		this.isChargeApplicable = isChargeApplicable;
	}

	private String email = "";
	private String isChargeApplicable ="N";

	public String getAmount() {
		return amount;
	}

	public String getDescription() {
		return description;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public String getBranchCity() {
		return branchCity;
	}

	public String getMmid() {
		return mmid;
	}

	public String getUid() {
		return uid;
	}

	public String getPayeeMobileNumber() {
		return payeeMobileNumber;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public void setBranchCity(String branchCity) {
		this.branchCity = branchCity;
	}

	public void setMmid(String mmid) {
		this.mmid = mmid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public void setPayeeMobileNumber(String payeeMobileNumber) {
		this.payeeMobileNumber = payeeMobileNumber;
	}

	///abc

	public String getBeneficiaryId() {
		return beneficiaryId;
	}

	public void setBeneficiaryId(String beneficiaryId) {
		this.beneficiaryId = beneficiaryId;
	}

	public String getCustomSmsMobileNumber() {
		return customSmsMobileNumber;
	}

	public void setCustomSmsMobileNumber(String customSmsMobileNumber) {
		this.customSmsMobileNumber = customSmsMobileNumber;
	}

	public String customSmsMobileNumber = "";

	public String beneficiaryId = "";
	public String amount = "";
	public String description = "";
	public String branchCode = "";
	public String branchName = "";
	public String branchCity = "";
	public String mmid = "";
	public String uid = "";
	public String payeeMobileNumber ="";

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getIfsc() {
		return ifsc;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public Transaction() {
		super();
		// this.cardNumber = cardNumber;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String cardNumber) {
		this.accountNumber = cardNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public static Transaction create(SoapObject bankObj) {
		Transaction bank = null;
		if (bankObj.hasProperty("uniqueAccId")) {
			bank = new Transaction();
			bank.setUniqueAccountId(bankObj.getPropertyAsString("uniqueAccId"));

			if (bankObj.hasProperty("userName"))
				bank.setUserName(bankObj.getPropertyAsString("userName"));

			if (bankObj.hasProperty("accountNo"))
				bank.setAccountNumber(bankObj.getPropertyAsString("accountNo"));

			if (bankObj.hasProperty("bankName"))
				bank.setBankName(bankObj.getPropertyAsString("bankName"));

			if (bankObj.hasProperty("accountType"))
				bank.setAccountType(bankObj.getPropertyAsString("accountType"));

			if (bankObj.hasProperty("isDefault"))
				if (bankObj.hasProperty("isDefault"))
					bank.setDefault(!"N".equalsIgnoreCase(bankObj
							.getPropertyAsString("isDefault")));
		}

		return bank;
	}

	public String getUniqueAccountId() {
		return uniqueAccountId;
	}

	public void setUniqueAccountId(String uniqueAccountId) {
		this.uniqueAccountId = uniqueAccountId;
	}



	public static Transaction JsonNcreate(JSONObject jsonObject) {
		Transaction bank = new Transaction();

		if ((null != jsonObject.get("uniqueAccId").toString())) {

			bank.setUniqueAccountId(jsonObject.get("uniqueAccId").toString());
			bank.setAccountNumber(jsonObject.get("accountNumber").toString());
			bank.setUserName(jsonObject.get("accountHolderName").toString());
			bank.setBankName(jsonObject.get("bankName").toString());
			if(jsonObject.containsKey("ifsc"))
			{
				
			bank.setIfsc(jsonObject.get("ifsc").toString());
			bank.setTypeOfBank("Other");
			}else
			{
				bank.setTypeOfBank("Linked");
				bank.setIfsc("");
			}
			//bank.setNickName(jsonObject.get("nickName").toString());
			//bank.setAccountNumber(jsonObject.get("uniqueAccId").toString());
			//bank.setBankName(jsonObject.get("accountType").toString());

			return bank;
		} else {
			bank = null;
			return bank;
		}

	}

	public String getTypeOfBank() {
		return typeOfBank;
	}

	public void setTypeOfBank(String typeOfBank) {
		this.typeOfBank = typeOfBank;
	}

}
