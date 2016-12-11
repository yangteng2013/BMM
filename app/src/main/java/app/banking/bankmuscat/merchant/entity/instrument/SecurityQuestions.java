package app.banking.bankmuscat.merchant.entity.instrument;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import app.banking.bankmuscat.merchant.entity.BaseData;

public class SecurityQuestions extends BaseData implements Serializable {



	@SerializedName("securityQuestionId")
	public String securityQuestionId;
	@SerializedName("securityQuestion")
	public String securityQuestion;
	@SerializedName("securityQuestionStatus")
	public String securityQuestionStatus;
	@SerializedName("answer")
	public String answer;



}

