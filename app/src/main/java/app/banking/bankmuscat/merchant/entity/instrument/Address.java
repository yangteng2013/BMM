package app.banking.bankmuscat.merchant.entity.instrument;


import org.ksoap2.serialization.SoapObject;

import java.util.Map;

import app.banking.bankmuscat.merchant.entity.BaseData;
import app.banking.bankmuscat.merchant.interfaces.ICard;


public class Address extends BaseData implements ICard {

	

	private String userName = "";
	private String emailId = "";
	private String msisdn = "";
	private String addressId = "";
	private String address1 = "";
	private String address2 = "";
	private String city = "";
	private String state = "";
	private String zipCode = "";
	private String country = "";
	private String contactNumber = "";
	private String addressType = "";
	private String status  = "";
	private boolean isDefault = false;
	
	private  Map <String,String> addressMap ;
	
	
	
	public Address() {
		super();
	}

	
	public static Address create(SoapObject addressObj) {
		Address addr = null;
		if (addressObj.hasProperty("addressId")) {
			addr = new Address();
		
			if (addressObj.hasProperty("addressId"))
				addr.setAddressId(addressObj.getPropertyAsString("addressId"));

			if (addressObj.hasProperty("address1"))
				addr.setAddress1(addressObj.getPropertyAsString("address1"));

			if (addressObj.hasProperty("address2"))
				addr.setAddress2(addressObj.getPropertyAsString("address2"));

			if (addressObj.hasProperty("city"))
				addr.setCity(addressObj.getPropertyAsString("city"));
			
			if (addressObj.hasProperty("state"))
				addr.setState(addressObj.getPropertyAsString("state"));

			if (addressObj.hasProperty("zipCode"))
				addr.setZipCode(addressObj.getPropertyAsString("zipCode"));
			
			if (addressObj.hasProperty("country"))
				addr.setCountry(addressObj.getPropertyAsString("country"));
			
			if (addressObj.hasProperty("contactNumber"))
				addr.setContactNumber(addressObj.getPropertyAsString("contactNumber"));
			
			if (addressObj.hasProperty("isDefault")){
				if(addressObj.getPropertyAsString("isDefault").equalsIgnoreCase("Y")){
					addr.setDefault(true);
				}
				else{
					addr.setDefault(false);
				}
			}
		
			if (addressObj.hasProperty("addressType"))
				addr.setAddressType(addressObj.getPropertyAsString("addressType"));
			
			if (addressObj.hasProperty("status"))
				addr.setStatus(addressObj.getPropertyAsString("status"));
		}

		return addr;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getAddressType() {
		return addressType;
	}
	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isDefault() {
		return isDefault;
	}
	public void setDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	public Map <String,String> getAddressMap() {
		return addressMap;
	}
	public void setAddressMap(Map <String,String> addressMap) {
		this.addressMap = addressMap;
	}

	@Override
	public Card getCardDetails() {
		return null;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}



	
	
}
