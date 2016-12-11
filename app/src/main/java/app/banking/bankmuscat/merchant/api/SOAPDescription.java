/**
 * 
 */
package app.banking.bankmuscat.merchant.api;

/**
 * @author U36838
 * 
 */


//172.16.2.116-:9650
//202.56.229.146-:6253


enum  SOAPDescription {



	BMPerformDedupe("http://202.56.229.146:6253/BMPerformDedupe/1.0.0",
			"http://service.cxf/"),
	BMCustomerRegister("http://202.56.229.146:6253/BMCustomerRegister/1.0.0",
			"http://service.cxf/"),
	BMGenerateOTP("http://202.56.229.146:6253/BMGenerateOTP/1.0.0",
			"http://service.cxf/"),
	BMVerifyOTP("http://202.56.229.146:6253/BMVerifyOTP/1.0.0",
			"http://service.cxf/"),
	BMSetPin("http://202.56.229.146:6253/BMSetPin/1.0.0",
			"http://service.cxf/"),
	BMForgotPinInit("http://202.56.229.146:6253/BM_ForgotPinInit/1.0.0",
			"http://service.cxf/"),
	BMForgotPINReset("http://202.56.229.146:6253/BM_ForgotPINReset/1.0.0",
			"http://service.cxf/"),
	BM_ValidateOTPChangeDevice("http://202.56.229.146:6253/BM_ValidateOTPChangeDevice/1.0.0",
			"http://service.cxf/"),
	BM_VerifySecurityAnswerForgotPIN("http://202.56.229.146:6253/BM_VerifySecurityAnswerForgotPIN/1.0.0",
			"http://service.cxf/"),
	BM_VerifySecurityAnswer("http://202.56.229.146:6253/BM_VerifySecurityAnswer/1.0.0",
			"http://service.cxf/"),
	BMChangePin("http://202.56.229.146:6253/BMChangePin/1.0.0",
			"http://service.cxf/"),
	BMLogin("http://202.56.229.146:6253/BMLogin/1.0.0",
			"http://service.cxf/"),
	BMChangeProfilePic("http://202.56.229.146:6253/BMChangeProfilePic/1.0.0",
			"http://service.cxf/"),
	BMDeleteProfilePic("http://202.56.229.146:6253/BMDeleteProfilePic/1.0.0",
			"http://service.cxf/"),
	BMChangeProfileDetails("http://202.56.229.146:6253/BMChangeProfileDetails/1.0.0",
			"http://service.cxf/"),
	BM_Bal_Enq("BMNotification","http://202.56.229.146:6253/BM_Bal_Enq/1.0.0",
			"http://service.cxf/"),
	BMNotification("BMNotification","http://202.56.229.146:6253/BMNotification/1.0.0",
			"http://service.cxf/"),
	BMDeleteNotification("BMNotification","http://202.56.229.146:6253/BMDeleteNotification/1.0.0",
			"http://service.cxf/"),
	BMMarkNotificationAsRead("http://202.56.229.146:6253/BMMarkNotificationAsRead/1.0.0",
			"http://service.cxf/"),
	BM_Load_SVA("http://202.56.229.146:6253/BM_Load_SVA/1.0.0",
			"http://service.cxf/"),
	BM_LOAD_MONEY_INIT("http://202.56.229.146:6253/BM_LOAD_MONEY_INIT/1.0.0",
			"http://service.cxf/"),
	BM_LOAD_MONEY_CONFIRM("http://202.56.229.146:6253/BM_LOAD_MONEY_CONFIRM/1.0.0",
			"http://service.cxf/"),
	BM_Add_Card("http://202.56.229.146:6253/BM_Add_Card/1.0.0",
			"http://service.cxf/"),
	BMGetStoredCards("http://202.56.229.146:6253/BMGetStoredCards/1.0.0",
			"http://service.cxf/"),
	BMDeleteCard("http://202.56.229.146:6253/BMDeleteCard/1.0.0",
			"http://service.cxf/"),
	BMSendReminder("http://202.56.229.146:6253/BMSendReminder/1.0.0",
			"http://service.cxf/"),
	BMGetFavouriteTxn("http://202.56.229.146:6253/BMGetFavouriteTxn/1.0.0",
			"http://service.cxf/"),
	BMAddFavouriteTxn("http://202.56.229.146:6253/BMAddFavouriteTxn/1.0.0",
			"http://service.cxf/"),
	BMDeleteFavouriteTxn("http://202.56.229.146:6253/BMDeleteFavouriteTxn/1.0.0",
			"http://service.cxf/"),
	BMGetTxnList("http://202.56.229.146:6253/BMGetTxnList/1.0.0",
			"http://service.cxf/"),
	BMRequestMoney("http://202.56.229.146:6253/BMRequestMoney/1.0.0",
			"http://service.cxf/"),
	BMAcceptRequestMoney("http://202.56.229.146:6253/BMAcceptRequestMoney/1.0.0",
			"http://service.cxf/"),
	BM_DeleteRequestMoney("http://202.56.229.146:6253/BM_DeleteRequestMoney/1.0.0",
			"http://service.cxf/"),
	BMRejectRequestMoney("http://202.56.229.146:6253/BMRejectRequestMoney/1.0.0",
			"http://service.cxf/"),
	BM_Unload_SVA("http://202.56.229.146:6253/BM_Unload_SVA/1.0.0",
			"http://service.cxf/"),
	BM_SendMoney_Registered("http://202.56.229.146:6253/BM_SendMoney_Registered/1.0.0",
			"http://service.cxf/"),
	BM_Add_Bank_Account("http://202.56.229.146:6253/BM_Add_Bank_Account/1.0.0",
			"http://service.cxf/"),
	GetBankAccount("http://202.56.229.146:6253/GetBankAccount/1.0.0",
			"http://service.cxf/"),
	BM_Delete_Bank_accounts("http://202.56.229.146:6253/BM_Delete_Bank_accounts/1.0.0",
			"http://service.cxf/"),
	BMGetAllSecurityQuestions("http://202.56.229.146:6253/BMGetAllSecurityQuestions/1.0.0",
			"http://service.cxf/"),
	BMAddSecurityAnswer("http://202.56.229.146:6253/BMAddSecurityAnswer/1.0.0",
			"http://service.cxf/"),
	BMGetSecurityQuestion("http://202.56.229.146:6253/BMGetSecurityQuestion/1.0.0",
			"http://service.cxf/"),
	BMValidateSecurityAnswer("http://202.56.229.146:6253/BMValidateSecurityAnswer/1.0.0",
			"http://service.cxf/"),
	ChangeDevice("http://202.56.229.146:6253/ChangeDevice/1.0.0",
			"http://service.cxf/"),
	BMGenericValidateOtp("http://202.56.229.146:6253/BMGenericValidateOtp/1.0.0",
			"http://service.cxf/"),
	BMVerifyEmail("http://202.56.229.146:6253/BMVerifyEmail/1.0.0",
			"http://service.cxf/"),
	BMUpdateProfileDedupe("http://202.56.229.146:6253/BMUpdateProfileDedupe/1.0.0",
			"http://service.cxf/"),
	BMChangeMSISDN("http://202.56.229.146:6253/BMChangeMSISDN/1.0.0",
			"http://service.cxf/"),
	BMChangeEmail("http://202.56.229.146:6253/BMChangeEmail/1.0.0",
			"http://service.cxf/"),
	WalletLimitCheck("http://202.56.229.146:6253/WalletLimitCheck/1.0.0",
			"http://service.cxf/"),
	BMGetAllChipInList("http://202.56.229.146:6253/BMGetAllChipInList/1.0.0",
			"http://service.cxf/"),
	BMPendingRequest("http://202.56.229.146:6253/BMPendingRequest/1.0.0",
			"http://service.cxf/"),
	BMViewPayeeRequestedMoney("http://202.56.229.146:6253/BMViewPayeeRequestedMoney/1.0.0",
			"http://service.cxf/"),
	BMChipinInitiation("http://202.56.229.146:6253/BMChipinInitiation/1.0.0",
			"http://service.cxf/"),
	BMGetChipinStatus("http://202.56.229.146:6253/BMGetChipinStatus/1.0.0",
			"http://service.cxf/"),
	BMGetPayerChipins("http://202.56.229.146:6253/BMGetPayerChipins/1.0.0",
			"http://service.cxf/"),
	BMDeleteChipInRequests("http://202.56.229.146:6253/BMDeleteChipInRequests/1.0.0",
			"http://service.cxf/"),
	BMDeleteUserEvent("http://202.56.229.146:6253/BMDeleteUserEvent/1.0.0",
			"http://service.cxf/"),
	BMAcceptChipin("http://202.56.229.146:6253/BMAcceptChipin/1.0.0",
			"http://service.cxf/"),
	BMRejectChipin("http://202.56.229.146:6253/BMRejectChipin/1.0.0",
			"http://service.cxf/"),
	BMCreateChipInGroup("http://202.56.229.146:6253/BMCreateChipInGroup/1.0.0",
			"http://service.cxf/"),
	BMEditChipInGroup("http://202.56.229.146:6253/BMEditChipInGroup/1.0.0",
			"http://service.cxf/"),
	BMDeleteChipInGroup("http://202.56.229.146:6253/BMDeleteChipInGroup/1.0.0",
			"http://service.cxf/"),
	BMGetChipInGroups("http://202.56.229.146:6253/BMGetChipInGroups/1.0.0",
			"http://service.cxf/"),
	BMMerchantBalance("http://202.56.229.146:6253/MerchantAPP/1.0.0",
			"http://service.cxf/"),
/*	BMMerchantBalance("http://172.16.2.116:9650/MerchantAPP/1.0.0",
			"http://service.cxf/"),
	BMSetSecurityQuestion("http://172.16.2.116:9650/BM_MER_APP_SET_SEC_Q_ANS/1.0.0","http://service.cxf/"),*/
	BMSetSecurityQuestion("http://202.56.229.146:6253/BM_MER_APP_SET_SEC_Q_ANS/1.0.0","http://service.cxf/"),

	/////////////////////

	/*BMPerformDedupe("http://172.16.2.116:9650/BMPerformDedupe/1.0.0",
			"http://service.cxf/"),
	BMCustomerRegister("http://172.16.2.116:9650/BMCustomerRegister/1.0.0",
			"http://service.cxf/"),
	BMGenerateOTP("http://172.16.2.116:9650/BMGenerateOTP/1.0.0",
			"http://service.cxf/"),
	BMVerifyOTP("http://172.16.2.116:9650/BMVerifyOTP/1.0.0",
			"http://service.cxf/"),
	BMSetPin("http://172.16.2.116:9650/BMSetPin/1.0.0",
			"http://service.cxf/"),
	BMForgotPinInit("http://172.16.2.116:9650/BM_ForgotPinInit/1.0.0",
			"http://service.cxf/"),
	BMForgotPINReset("http://172.16.2.116:9650/BM_ForgotPINReset/1.0.0",
			"http://service.cxf/"),
	BM_ValidateOTPChangeDevice("http://172.16.2.116:9650/BM_ValidateOTPChangeDevice/1.0.0",
			"http://service.cxf/"),
	BM_VerifySecurityAnswerForgotPIN("http://172.16.2.116:9650/BM_VerifySecurityAnswerForgotPIN/1.0.0",
			"http://service.cxf/"),
	BM_VerifySecurityAnswer("http://172.16.2.116:9650/BM_VerifySecurityAnswer/1.0.0",
			"http://service.cxf/"),
	BMChangePin("http://172.16.2.116:9650/BMChangePin/1.0.0",
			"http://service.cxf/"),
	BMLogin("http://172.16.2.116:9650/BMLogin/1.0.0",
			"http://service.cxf/"),
	BMChangeProfilePic("http://172.16.2.116:9650/BMChangeProfilePic/1.0.0",
			"http://service.cxf/"),
	BMDeleteProfilePic("http://172.16.2.116:9650/BMDeleteProfilePic/1.0.0",
			"http://service.cxf/"),
	BMChangeProfileDetails("http://172.16.2.116:9650/BMChangeProfileDetails/1.0.0",
			"http://service.cxf/"),
	BM_Bal_Enq("BMNotification","http://172.16.2.116:9650/BM_Bal_Enq/1.0.0",
			"http://service.cxf/"),
	BMNotification("BMNotification","http://172.16.2.116:9650/BMNotification/1.0.0",
			"http://service.cxf/"),
	BMDeleteNotification("BMNotification","http://172.16.2.116:9650/BMDeleteNotification/1.0.0",
			"http://service.cxf/"),
	BMMarkNotificationAsRead("http://172.16.2.116:9650/BMMarkNotificationAsRead/1.0.0",
			"http://service.cxf/"),
	BM_Load_SVA("http://172.16.2.116:9650/BM_Load_SVA/1.0.0",
			"http://service.cxf/"),
	BM_LOAD_MONEY_INIT("http://172.16.2.116:9650/BM_LOAD_MONEY_INIT/1.0.0",
			"http://service.cxf/"),
	BM_LOAD_MONEY_CONFIRM("http://172.16.2.116:9650/BM_LOAD_MONEY_CONFIRM/1.0.0",
			"http://service.cxf/"),
	BM_Add_Card("http://172.16.2.116:9650/BM_Add_Card/1.0.0",
			"http://service.cxf/"),
	BMGetStoredCards("http://172.16.2.116:9650/BMGetStoredCards/1.0.0",
			"http://service.cxf/"),
	BMDeleteCard("http://172.16.2.116:9650/BMDeleteCard/1.0.0",
			"http://service.cxf/"),
	BMSendReminder("http://172.16.2.116:9650/BMSendReminder/1.0.0",
			"http://service.cxf/"),
	BMGetFavouriteTxn("http://172.16.2.116:9650/BMGetFavouriteTxn/1.0.0",
			"http://service.cxf/"),
	BMAddFavouriteTxn("http://172.16.2.116:9650/BMAddFavouriteTxn/1.0.0",
			"http://service.cxf/"),
	BMDeleteFavouriteTxn("http://172.16.2.116:9650/BMDeleteFavouriteTxn/1.0.0",
			"http://service.cxf/"),
	BMGetTxnList("http://172.16.2.116:9650/BMGetTxnList/1.0.0",
			"http://service.cxf/"),
	BMRequestMoney("http://172.16.2.116:9650/BMRequestMoney/1.0.0",
			"http://service.cxf/"),
	BMAcceptRequestMoney("http://172.16.2.116:9650/BMAcceptRequestMoney/1.0.0",
			"http://service.cxf/"),
	BM_DeleteRequestMoney("http://172.16.2.116:9650/BM_DeleteRequestMoney/1.0.0",
			"http://service.cxf/"),
	BMRejectRequestMoney("http://172.16.2.116:9650/BMRejectRequestMoney/1.0.0",
			"http://service.cxf/"),
	BM_Unload_SVA("http://172.16.2.116:9650/BM_Unload_SVA/1.0.0",
			"http://service.cxf/"),
	BM_SendMoney_Registered("http://172.16.2.116:9650/BM_SendMoney_Registered/1.0.0",
			"http://service.cxf/"),
	BM_Add_Bank_Account("http://172.16.2.116:9650/BM_Add_Bank_Account/1.0.0",
			"http://service.cxf/"),
	GetBankAccount("http://172.16.2.116:9650/GetBankAccount/1.0.0",
			"http://service.cxf/"),
	BM_Delete_Bank_accounts("http://172.16.2.116:9650/BM_Delete_Bank_accounts/1.0.0",
			"http://service.cxf/"),
	BMGetAllSecurityQuestions("http://172.16.2.116:9650/BMGetAllSecurityQuestions/1.0.0",
			"http://service.cxf/"),
	BMAddSecurityAnswer("http://172.16.2.116:9650/BMAddSecurityAnswer/1.0.0",
			"http://service.cxf/"),
	BMGetSecurityQuestion("http://172.16.2.116:9650/BMGetSecurityQuestion/1.0.0",
			"http://service.cxf/"),
	BMValidateSecurityAnswer("http://172.16.2.116:9650/BMValidateSecurityAnswer/1.0.0",
			"http://service.cxf/"),
	ChangeDevice("http://172.16.2.116:9650/ChangeDevice/1.0.0",
			"http://service.cxf/"),
	BMGenericValidateOtp("http://172.16.2.116:9650/BMGenericValidateOtp/1.0.0",
			"http://service.cxf/"),
	BMVerifyEmail("http://172.16.2.116:9650/BMVerifyEmail/1.0.0",
			"http://service.cxf/"),
	BMUpdateProfileDedupe("http://172.16.2.116:9650/BMUpdateProfileDedupe/1.0.0",
			"http://service.cxf/"),
	BMChangeMSISDN("http://172.16.2.116:9650/BMChangeMSISDN/1.0.0",
			"http://service.cxf/"),
	BMChangeEmail("http://172.16.2.116:9650/BMChangeEmail/1.0.0",
			"http://service.cxf/"),
	WalletLimitCheck("http://172.16.2.116:9650/WalletLimitCheck/1.0.0",
			"http://service.cxf/"),
	BMGetAllChipInList("http://172.16.2.116:9650/BMGetAllChipInList/1.0.0",
			"http://service.cxf/"),
	BMPendingRequest("http://172.16.2.116:9650/BMPendingRequest/1.0.0",
			"http://service.cxf/"),
	BMViewPayeeRequestedMoney("http://172.16.2.116:9650/BMViewPayeeRequestedMoney/1.0.0",
			"http://service.cxf/"),
	BMChipinInitiation("http://172.16.2.116:9650/BMChipinInitiation/1.0.0",
			"http://service.cxf/"),
	BMGetChipinStatus("http://172.16.2.116:9650/BMGetChipinStatus/1.0.0",
			"http://service.cxf/"),
	BMGetPayerChipins("http://172.16.2.116:9650/BMGetPayerChipins/1.0.0",
			"http://service.cxf/"),
	BMDeleteChipInRequests("http://172.16.2.116:9650/BMDeleteChipInRequests/1.0.0",
			"http://service.cxf/"),
	BMDeleteUserEvent("http://172.16.2.116:9650/BMDeleteUserEvent/1.0.0",
			"http://service.cxf/"),
	BMAcceptChipin("http://172.16.2.116:9650/BMAcceptChipin/1.0.0",
			"http://service.cxf/"),
	BMRejectChipin("http://172.16.2.116:9650/BMRejectChipin/1.0.0",
			"http://service.cxf/"),
	BMCreateChipInGroup("http://172.16.2.116:9650/BMCreateChipInGroup/1.0.0",
			"http://service.cxf/"),
	BMEditChipInGroup("http://172.16.2.116:9650/BMEditChipInGroup/1.0.0",
			"http://service.cxf/"),
	BMDeleteChipInGroup("http://172.16.2.116:9650/BMDeleteChipInGroup/1.0.0",
			"http://service.cxf/"),
	BMGetChipInGroups("http://172.16.2.116:9650/BMGetChipInGroups/1.0.0",
			"http://service.cxf/"),*/




	///////////////

















	//////////////////


	ConsumerOnboarding("http://54.77.154.84:9090/mwallet/consumerOnboarding",
			"http://service.cxf/"), Authenticate(
			"http://54.77.154.84:9090/mwallet/webConsumerOnboarding",
			"http://service.cxf/"), PaymentInstruments(
			"http://54.77.154.84:9090/mwallet/payments", "http://service.cxf/"), ProximityPayQR(
			"http://54.77.154.84:9094/proximityPayQR", "http://service.cxf/"), MovieTicketBooking(
			"http://54.77.154.84:9094/ticketBooking", "http://service.cxf/"), BillPayment(
			"http://54.77.154.84:9094/billPayment", "http://service.cxf/"), MWalletMPService(
			"http://54.77.154.84:9094/mWalletMPService", "http://service.cxf/"), GetMerchantCoupons(
			"http://203.197.173.241:9096/couponPreferences", "http://www.mahindracomviva.com/CouponPreferences/"), MyProfile(
			"http://54.77.154.84:9094/myProfile", "http://service.cxf/");
	//
	//
	private String methodName;
	private String url;
	private String namespace;

	/**
	 * get methodName
	 *
	 * @return
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * get url
	 * 
	 * @return
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * get namespace
	 * 
	 * @return
	 */
	public String getNamespace() {
		return namespace;
	}

	private SOAPDescription(final String url, final String namespace) {
		this.url = url;
		this.namespace = namespace;
	};

	private SOAPDescription(final String methodName, final String url, final String namespace) {
		this.methodName = methodName;
		this.url = url;
		this.namespace = namespace;
	};
}
