package app.banking.bankmuscat.merchant.entity.instrument;

import com.google.gson.annotations.SerializedName;

import app.banking.bankmuscat.merchant.entity.BaseData;

public class Card extends BaseData {//implements ICard,Parcelable {

	public boolean isSaved() {
		return isSaved;
	}

	public void setSaved(boolean saved) {
		isSaved = saved;
	}

	private boolean isSaved = false;


	@SerializedName("type")
	public String type;
	@SerializedName("cardHolderName")
	public String cardHolderName;
	@SerializedName("cardNumber")
	public String cardNumber;
	@SerializedName("bankName")
	public String bankName;
	@SerializedName("expiryYear")
	public String expiryYear;
	@SerializedName("expiryMonth")
	public String expiryMonth;
	@SerializedName("cvv")
	public String cvv;
	@SerializedName("paymentInstrumentId")
	public String paymentInstrumentId;
	@SerializedName("instrumentType")
	public String instrumentType;

	@SerializedName("cardCategory")
	public String cardCategory;

	@SerializedName("cardType")
	public String cardType;

	/*private CardType type;
	private String cardHolderName = "";
	private String cardNumber = "";
	private String bankName = "";
	private String expiryYear = "";
	private String expiryMonth = "";
	private boolean isDefault = false;
	private String billingAddress1 = "";
	private String billingAddress2 = "";
	private String city = "";
	private String country = "";
	private String nickName = "";
	private String stateRegion = "";
	private String zipCode = "";
	private String instrumentType = "";
	private String cvv = "";
	private String cardCategory="NHCE";
	private String pay_id="";
	private String HceId="";

	private String instrumentId="";



	public String getHceId() {
		return HceId;
	}

	public void setHceId(String hceId) {
		HceId = hceId;
	}

	public static enum CardType {
		VISA, MASTER, AMEX, DISCOVER, INVALID;
	}

	public String getPay_id() {
		return pay_id;
	}

	public void setPay_id(String pay_id) {
		this.pay_id = pay_id;
	}

	public String getInstrumentType() {
		return instrumentType;
	}

	public void setInstrumentType(String instrumentType) {
		this.instrumentType = instrumentType;
		if ("VISA".equalsIgnoreCase(this.instrumentType))
			setType(CardType.VISA);
		else if ("American Express".equalsIgnoreCase(this.instrumentType))
			setType(CardType.AMEX);
		else if ("Discover".equalsIgnoreCase(this.instrumentType))
			setType(CardType.DISCOVER);
		else if ("Master Card".equalsIgnoreCase(this.instrumentType))
			setType(CardType.MASTER);
		else
			setType(CardType.INVALID);

	}

	public Card(String cardHolderName, String cardNumber, String bankName,
				String expiryYear, String expiryMonth, boolean isDefault, String instrumentId) {
		super();
		this.cardHolderName = cardHolderName;
		this.cardNumber = cardNumber;
		this.bankName = bankName;
		this.expiryYear = expiryYear;
		this.expiryMonth = expiryMonth;
		this.isDefault = isDefault;
		this.instrumentId = instrumentId;
	}
	public Card(Parcel in)
	{
		cardHolderName =in.readString();
		cardNumber =in.readString();
		bankName =in.readString();
		expiryYear =in.readString();
		expiryMonth =in.readString();
		billingAddress1 =in.readString();
		billingAddress2 =in.readString();
		city = in.readString();
		country =in.readString();
		nickName = in.readString();
		stateRegion =in.readString();
		zipCode =in.readString();
		instrumentType =in.readString();
		cvv =in.readString();
		cardCategory=in.readString();
		pay_id=in.readString();
		instrumentId=in.readString();
	}

	public String getInstrumentId() {
		return instrumentId;
	}

	public void setInstrumentId(String instrumentId) {
		this.instrumentId = instrumentId;
	}

	public CardType getType() {
		return type;
	}

	protected void setType(CardType type) {
		this.type = type;
	}

	public String getUserName() {
		return cardHolderName;
	}

	public void setUserName(String userName) {
		this.cardHolderName = userName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getExpiryYear() {
		return expiryYear;
	}

	public void setExpiryYear(String expiryYear) {
		this.expiryYear = expiryYear;
	}

	public String getExpiryMonth() {
		return expiryMonth;
	}


	public void setExpiryMonth(String expiryMonth) {
		this.expiryMonth = expiryMonth;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getBillingAddress1() {
		return billingAddress1;
	}

	public void setBillingAddress1(String billingAddress1) {
		this.billingAddress1 = billingAddress1;
	}

	public String getBillingAddress2() {
		return billingAddress2;
	}

	public void setBillingAddress2(String billingAddress2) {
		this.billingAddress2 = billingAddress2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getStateRegion() {
		return stateRegion;
	}

	public void setStateRegion(String stateRegion) {
		this.stateRegion = stateRegion;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Card() {
		super();
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	@Override
	public Card getCardDetails() {
		return this;
	}

	public static Card create(SoapObject cardObj) {
		Card card = null;
		if (cardObj.hasProperty("paymentInstrumentId")) {
			card = new Card();
			card.setInstrumentId(cardObj.getPropertyAsString("paymentInstrumentId"));

			if (cardObj.hasProperty("cardHolderName"))
				card.setUserName(cardObj.getPropertyAsString("cardHolderName"));

			if (cardObj.hasProperty("cardNumber"))
				card.setCardNumber(cardObj.getPropertyAsString("cardNumber"));

			if (cardObj.hasProperty("bankName"))
				card.setBankName(cardObj.getPropertyAsString("bankName"));

			if (cardObj.hasProperty("instrumentType"))
				card.setInstrumentType(cardObj
						.getPropertyAsString("instrumentType"));

			if (cardObj.hasProperty("expiryYear"))
				card.setExpiryYear(cardObj.getPropertyAsString("expiryYear"));

			if (cardObj.hasProperty("expiryMonth"))
				card.setExpiryMonth(cardObj.getPropertyAsString("expiryMonth"));

			if (cardObj.hasProperty("isDefault"))
				card.setDefault(!"N".equalsIgnoreCase(cardObj
						.getPropertyAsString("isDefault")));

			if (cardObj.hasProperty("billingAddress1"))
				card.setBillingAddress1(cardObj
						.getPropertyAsString("billingAddress1"));

			if (cardObj.hasProperty("billingAddress2"))
				card.setBillingAddress2(cardObj
						.getPropertyAsString("billingAddress2"));

			if (cardObj.hasProperty("city"))
				card.setCity(cardObj.getPropertyAsString("city"));

			if (cardObj.hasProperty("country"))
				card.setCountry(cardObj.getPropertyAsString("country"));

			if (cardObj.hasProperty("nickName"))
				card.setNickName(cardObj.getPropertyAsString("nickName"));

			if (cardObj.hasProperty("stateRegion"))
				card.setStateRegion(cardObj.getPropertyAsString("stateRegion"));

			if (cardObj.hasProperty("zipCode"))
				card.setZipCode(cardObj.getPropertyAsString("zipCode"));
		}

		return card;
	}


	public static Card Jsoncreate(JSONObject getcardListObj1) {
		Card card = new Card();
		JSONArray cardsermat = (JSONArray) getcardListObj1
				.get("cardServiceMatrixList");
		JSONObject cardCate=(JSONObject) cardsermat.get(0);
		if ((null != getcardListObj1.get("uniqueId").toString()))
		{

			card.setCardNumber(getcardListObj1.get("cardNumber").toString());
			card.setInstrumentId(getcardListObj1.get("paymentInstrumentId").toString());
			card.setInstrumentType(getcardListObj1.get("cardType").toString());
			card.setDefault(getcardListObj1.get("isDefault" ).toString().equals("Y"));
			card.setUserName(getcardListObj1.get("userName").toString());
			card.setNickName(getcardListObj1.get("nickName").toString());
			card.setExpiryYear(getcardListObj1.get("expiryYear").toString());
			card.setExpiryMonth(getcardListObj1.get("expiryMonth").toString());
			if(cardCate!=null){
				card.setCardCategory(cardCate.get("serviceType").toString());
				card.setPay_id(getcardListObj1.get("paymentInstrumentId").toString());
				card.setHceId(cardCate.get("idExternalSystem").toString());
			}

			return card;
		} else {
			card = null;
			return card;
		}
	}

	public static Card JsonNcreate(JSONObject getcardListObj1) {
		Card card = new Card();

		if ((null != getcardListObj1.get("uniqueId").toString()))
		{

			card.setCardNumber(getcardListObj1.get("cardNumber").toString());
			card.setInstrumentId(getcardListObj1.get("paymentInstrumentId").toString());
			card.setInstrumentType(getcardListObj1.get("cardType").toString());
			card.setDefault(getcardListObj1.get("isDefault" ).toString().equals("Y"));
			card.setUserName(getcardListObj1.get("userName").toString());
			card.setNickName(getcardListObj1.get("nickName").toString());
			card.setExpiryYear(getcardListObj1.get("expiryYear").toString());
			card.setExpiryMonth(getcardListObj1.get("expiryMonth").toString());
			if(null!= (JSONArray) getcardListObj1.get("cardServiceMatrixList")){
				JSONArray cardsermat = (JSONArray) getcardListObj1.get("cardServiceMatrixList");
				if(cardsermat.size() !=0)
				{
					JSONObject cardser = (JSONObject) cardsermat.get(0);
					card.setCardCategory(cardser.get("serviceType").toString());
					card.setHceId(cardser.get("idExternalSystem").toString());
				}
			}else{
				card.setCardCategory("NOTHCE");
			}
			card.setPay_id(getcardListObj1.get("paymentInstrumentId").toString());


			return card;
		} else {
			card = null;
			return card;
		}


	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public String getCardCategory() {
		return cardCategory;
	}

	public void setCardCategory(String cardCategory) {
		this.cardCategory = cardCategory;
	}

	public static final Creator<Card> CREATOR = new Creator<Card>() {
		@Override
		public Card createFromParcel(Parcel in) {
			return new Card(in);
		}

		@Override
		public Card[] newArray(int size) {
			return new Card[size];
		}
	};


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(cardHolderName);
		dest.writeString(cardNumber);
		dest.writeString(bankName);
		dest.writeString(expiryYear);
		dest.writeString(expiryMonth);
		dest.writeString(billingAddress1);
		dest.writeString(billingAddress2);
		dest.writeString(city);
		dest.writeString(country);
		dest.writeString(nickName);
		dest.writeString(stateRegion);
		dest.writeString(zipCode);
		dest.writeString(instrumentType);
		dest.writeString(cardCategory);
		dest.writeString(pay_id);
		dest.writeString(instrumentId);

	}*/

}




/*
package app.banking.bankmuscat.wallet.entity.instrument;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.ksoap2.serialization.SoapObject;

import app.banking.bankmuscat.wallet.entity.BaseData;
import app.banking.bankmuscat.wallet.interfaces.ICard;

public class Card extends BaseData implements ICard,Parcelable {

	public String getHceId() {
		return HceId;
	}

	public void setHceId(String hceId) {
		HceId = hceId;
	}

	public static enum CardType {
		VISA, MASTER, AMEX, DISCOVER, INVALID;
	}

	private CardType type;
	private String cardHolderName = "";
	private String cardNumber = "";
	private String bankName = "";
	private String expiryYear = "";
	private String expiryMonth = "";
	private boolean isDefault = false;
	private String billingAddress1 = "";
	private String billingAddress2 = "";
	private String city = "";
	private String country = "";
	private String nickName = "";
	private String stateRegion = "";
	private String zipCode = "";
	private String instrumentType = "";
	private String cvv = "";
	private String cardCategory="NHCE";
	private String pay_id="";
	private String HceId="";



	
	public String getPay_id() {
		return pay_id;
	}

	public void setPay_id(String pay_id) {
		this.pay_id = pay_id;
	}

	public String getInstrumentType() {
		return instrumentType;
	}

	public void setInstrumentType(String instrumentType) {
		this.instrumentType = instrumentType;
		if ("VISA".equalsIgnoreCase(this.instrumentType))
			setType(CardType.VISA);
		else if ("American Express".equalsIgnoreCase(this.instrumentType))
			setType(CardType.AMEX);
		else if ("Discover".equalsIgnoreCase(this.instrumentType))
			setType(CardType.DISCOVER);
		else if ("Master Card".equalsIgnoreCase(this.instrumentType))
			setType(CardType.MASTER);
		else
			setType(CardType.INVALID);

	}

	public Card(String cardHolderName, String cardNumber, String bankName,
			String expiryYear, String expiryMonth, boolean isDefault) {
		super();
		this.cardHolderName = cardHolderName;
		this.cardNumber = cardNumber;
		this.bankName = bankName;
		this.expiryYear = expiryYear;
		this.expiryMonth = expiryMonth;
		this.isDefault = isDefault;
	}
	public Card(Parcel in)
	{
		cardHolderName =in.readString();
		 cardNumber =in.readString();
		 bankName =in.readString();
		 expiryYear =in.readString();
		 expiryMonth =in.readString();
		 billingAddress1 =in.readString();
		 billingAddress2 =in.readString();
		 city = in.readString();
		 country =in.readString();
		 nickName = in.readString();
		 stateRegion =in.readString();
		 zipCode =in.readString();
		 instrumentType =in.readString();
		 cvv =in.readString();
		 cardCategory=in.readString();
	     pay_id=in.readString();
	}

	public String getInstrumentId() {
		return id;
	}

	public void setInstrumentId(String instrumentId) {
		this.id = instrumentId;
	}

	public CardType getType() {
		return type;
	}

	protected void setType(CardType type) {
		this.type = type;
	}

	public String getUserName() {
		return cardHolderName;
	}

	public void setUserName(String userName) {
		this.cardHolderName = userName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getExpiryYear() {
		return expiryYear;
	}

	public void setExpiryYear(String expiryYear) {
		this.expiryYear = expiryYear;
	}

	public String getExpiryMonth() {
		return expiryMonth;
	}


	public void setExpiryMonth(String expiryMonth) {
		this.expiryMonth = expiryMonth;
	}

	public boolean isDefault() {
		return isDefault;
	}

	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}

	public String getBillingAddress1() {
		return billingAddress1;
	}

	public void setBillingAddress1(String billingAddress1) {
		this.billingAddress1 = billingAddress1;
	}

	public String getBillingAddress2() {
		return billingAddress2;
	}

	public void setBillingAddress2(String billingAddress2) {
		this.billingAddress2 = billingAddress2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getStateRegion() {
		return stateRegion;
	}

	public void setStateRegion(String stateRegion) {
		this.stateRegion = stateRegion;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Card() {
		super();
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	@Override
	public Card getCardDetails() {
		return this;
	}

	public static Card create(SoapObject cardObj) {
		Card card = null;
		if (cardObj.hasProperty("paymentInstrumentId")) {
			card = new Card();
			card.setInstrumentId(cardObj.getPropertyAsString("paymentInstrumentId"));

			if (cardObj.hasProperty("cardHolderName"))
				card.setUserName(cardObj.getPropertyAsString("cardHolderName"));

			if (cardObj.hasProperty("cardNumber"))
				card.setCardNumber(cardObj.getPropertyAsString("cardNumber"));

			if (cardObj.hasProperty("bankName"))
				card.setBankName(cardObj.getPropertyAsString("bankName"));

			if (cardObj.hasProperty("instrumentType"))
				card.setInstrumentType(cardObj
						.getPropertyAsString("instrumentType"));

			if (cardObj.hasProperty("expiryYear"))
				card.setExpiryYear(cardObj.getPropertyAsString("expiryYear"));

			if (cardObj.hasProperty("expiryMonth"))
				card.setExpiryMonth(cardObj.getPropertyAsString("expiryMonth"));

			if (cardObj.hasProperty("isDefault"))
				card.setDefault(!"N".equalsIgnoreCase(cardObj
						.getPropertyAsString("isDefault")));

			if (cardObj.hasProperty("billingAddress1"))
				card.setBillingAddress1(cardObj
						.getPropertyAsString("billingAddress1"));

			if (cardObj.hasProperty("billingAddress2"))
				card.setBillingAddress2(cardObj
						.getPropertyAsString("billingAddress2"));

			if (cardObj.hasProperty("city"))
				card.setCity(cardObj.getPropertyAsString("city"));

			if (cardObj.hasProperty("country"))
				card.setCountry(cardObj.getPropertyAsString("country"));

			if (cardObj.hasProperty("nickName"))
				card.setNickName(cardObj.getPropertyAsString("nickName"));

			if (cardObj.hasProperty("stateRegion"))
				card.setStateRegion(cardObj.getPropertyAsString("stateRegion"));

			if (cardObj.hasProperty("zipCode"))
				card.setZipCode(cardObj.getPropertyAsString("zipCode"));
		}

		return card;
	}
	
	
	public static Card Jsoncreate(JSONObject getcardListObj1) {
		Card card = new Card();
		JSONArray cardsermat = (JSONArray) getcardListObj1
				.get("cardServiceMatrixList");
		JSONObject cardCate=(JSONObject) cardsermat.get(0);
		if ((null != getcardListObj1.get("uniqueId").toString()))
			 {

			card.setCardNumber(getcardListObj1.get("cardNumber").toString());
			card.setInstrumentType(getcardListObj1.get("cardType").toString());
			card.setDefault(getcardListObj1.get("isDefault" ).toString().equals("Y"));
			card.setUserName(getcardListObj1.get("userName").toString());
			card.setNickName(getcardListObj1.get("nickName").toString());
			card.setExpiryYear(getcardListObj1.get("expiryYear").toString());
			card.setExpiryMonth(getcardListObj1.get("expiryMonth").toString());
			if(cardCate!=null){
			card.setCardCategory(cardCate.get("serviceType").toString());
			card.setPay_id(getcardListObj1.get("paymentInstrumentId").toString());
				card.setHceId(cardCate.get("idExternalSystem").toString());
			}
			
			return card;
		} else {
			card = null;
			return card;
		}
	}
		
		public static Card JsonNcreate(JSONObject getcardListObj1) {
			Card card = new Card();
		
			if ((null != getcardListObj1.get("uniqueId").toString()))
				 {

				card.setCardNumber(getcardListObj1.get("cardNumber").toString());
				card.setInstrumentType(getcardListObj1.get("cardType").toString());
				card.setDefault(getcardListObj1.get("isDefault" ).toString().equals("Y"));
				card.setUserName(getcardListObj1.get("userName").toString());
				card.setNickName(getcardListObj1.get("nickName").toString());
				card.setExpiryYear(getcardListObj1.get("expiryYear").toString());
				card.setExpiryMonth(getcardListObj1.get("expiryMonth").toString());
				if(null!= (JSONArray) getcardListObj1.get("cardServiceMatrixList")){
					JSONArray cardsermat = (JSONArray) getcardListObj1.get("cardServiceMatrixList");
					if(cardsermat.size() !=0)
					{
						JSONObject cardser = (JSONObject) cardsermat.get(0);
						card.setCardCategory(cardser.get("serviceType").toString());
						card.setHceId(cardser.get("idExternalSystem").toString());
					}
				}else{
					card.setCardCategory("NOTHCE");
				}
				card.setPay_id(getcardListObj1.get("paymentInstrumentId").toString());
				
				
				return card;
			} else {
				card = null;
				return card;
			}

		
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public String getCardCategory() {
		return cardCategory;
	}

	public void setCardCategory(String cardCategory) {
		this.cardCategory = cardCategory;
	}
	
	public static final Creator<Card> CREATOR = new Creator<Card>() {
		@Override
		public Card createFromParcel(Parcel in) {
			return new Card(in);
		}

		@Override
		public Card[] newArray(int size) {
			return new Card[size];
		}
	};


	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(cardHolderName);
		dest.writeString(cardNumber);
		dest.writeString(bankName);
		dest.writeString(expiryYear);
		dest.writeString(expiryMonth);
		dest.writeString(billingAddress1);
		dest.writeString(billingAddress2);
		dest.writeString(city);
		dest.writeString(country);
		dest.writeString(nickName);
		dest.writeString(stateRegion);
		dest.writeString(zipCode);
		dest.writeString(instrumentType);
		dest.writeString(cardCategory);
		dest.writeString(pay_id);
		
	}

}
*/
