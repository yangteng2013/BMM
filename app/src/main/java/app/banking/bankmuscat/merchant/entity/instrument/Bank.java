package app.banking.bankmuscat.merchant.entity.instrument;


import com.google.gson.annotations.SerializedName;

import app.banking.bankmuscat.merchant.entity.BaseData;

public class Bank extends BaseData {

	@SerializedName("accountHolderName")
	public String accountHolderName = "";
	@SerializedName("bankName")
	public String bankName = "";
	@SerializedName("accountNumber")
	public String accountNumber = "";
	@SerializedName("ifsc")
	public String ifsc;
	@SerializedName("nickName")
	public String nickName = "";
	@SerializedName("paymentInstrumentId")
	public String paymentInstrumentId = "";
	@SerializedName("mmid")
	public String mmid = "";
	@SerializedName("uuid")
	public String uuid = "";
	@SerializedName("accountType")
	public String accountType ="";
	@SerializedName("branchName")
	public String branchName = "";
	@SerializedName("branchCode")
	public String branchCode = "";
	@SerializedName("branchCity")
	public String branchCity ="";
	@SerializedName("isDefault")
	public String isDefault = "";
	@SerializedName("payeeMobileNumber")
	public String payeeMobileNumber = "";

	@SerializedName("uniqueAccId")
	public String uniqueAccId = "";


}



/*
package app.banking.bankmuscat.wallet.entity.instrument;


import org.json.simple.JSONObject;
import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;

import app.banking.bankmuscat.wallet.entity.BaseData;
import app.banking.bankmuscat.wallet.interfaces.ICard;

public class Bank extends BaseData implements ICard,Serializable {

	private String userName = "";
	private String accountNumber = "";
	private String bankName = "";
	private boolean isDefault = false;
	private String accountType = "";
	private String uniqueAccountId = "";
	private String ifsc = "";
	private String nickName = "";
	private String typeOfBank ="";

	///abc
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

	public Bank() {
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

	public static Bank create(SoapObject bankObj) {
		Bank bank = null;
		if (bankObj.hasProperty("uniqueAccId")) {
			bank = new Bank();
			bank.setUniqueAccountId(bankObj.getPropertyAsString("uniqueAccId"));

			if (bankObj.hasProperty("accountHolderName"))
				bank.setUserName(bankObj.getPropertyAsString("accountHolderName"));

			if (bankObj.hasProperty("accountNumber"))
				bank.setAccountNumber(bankObj.getPropertyAsString("accountNumber"));

			if (bankObj.hasProperty("bankName"))
				bank.setBankName(bankObj.getPropertyAsString("bankName"));

			if (bankObj.hasProperty("ifsc"))
				bank.setIfsc(bankObj.getPropertyAsString("ifsc"));

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

	@Override
	public Card getCardDetails() {
		return null;
	}

	public static Bank JsonNcreate(JSONObject jsonObject) {
		Bank bank = new Bank();

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
*/
