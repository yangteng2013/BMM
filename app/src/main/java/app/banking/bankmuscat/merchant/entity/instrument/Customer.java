package app.banking.bankmuscat.merchant.entity.instrument;


import com.google.gson.annotations.SerializedName;

public class Customer {


	@SerializedName("customerId")
	public String customerId;
	@SerializedName("firstName")
	public String firstName;
	@SerializedName("middleName")
	public String middleName;
	@SerializedName("lastName")
	public String lastName;



	
}
