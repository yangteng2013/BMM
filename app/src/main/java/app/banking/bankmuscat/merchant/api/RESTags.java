package app.banking.bankmuscat.merchant.api;

public enum RESTags {

	REST_REQUEST_HEADER("requestHeader"), BEARER("bearer"), PHASECODE(
			"phaseCode"), SERVICECODE("serviceCode"), SERVICEID("serviceId"), SUBTRANSACTIONCODE(
			"subTransactionCode"), INTERACTIONCODE("interactionCode"), REST_RESPONSE_HEADER(
			"responseHeader"), REGISTER_USER_INPUT("registerUserInput"), REGISTER_USER_OUTPUT(
			"registerUserOutput"), CHANGE_CARD_STATUS_INPUT(
			"changeCardStatusInput"), CHANGE_CARD_STATUS_OUTPUT(
			"changeCardStatusOutput"), ADD_HCE_CARD_INPUT("addHceCardInput"), ADD_HCE_CARD_OUTPUT(
			"addHceCardOutput"), DELETE_HCE_CARD_INPUT("deleteHceCardInput"), DELETE_HCE_CARD_OUTPUT(
			"deleteHceCardOutput"), SUSPEND_HCE_CARD_INPUT(
			"suspendHceCardInput"), SUSPEND_HCE_CARD_OUTPUT(
			"suspendHceCardOutput"), RESUME_HCE_CARD_INPUT("resumeHceCardInput"), RESUME_HCE_CARD_OUTPUT(
			"resumeHceCardOutput"), GET_TOKENS_INPUT("getTokensInput"), GET_TOKENS_OUTPUT(
			"getTokensOutput"), RESET_PIN_INPUT("resetPinInput"), RESET_PIN_OUTPUT(
			"resetPinOutput"), RESET_MPA_INPUT("resetMPAInput"), RESET_MPA_OUTPUT(
			"resetMPAOutput"), UPDATE_ALL_CARDS_STATUS_INPUT(
			"updateAllCardStatusInput"), UPDATE_ALL_CARDS_STATUS_OUTPUT(
			"updateAllCardStatusOutput"), RESEND_OTP_INPUT("resendOtpInput"), RESEND_OTP_OUTPUT(
			"resendOtpOutput"), BLOCK_ACCOUNT_INPUT("blockAccountInput"), BLOCK_ACCOUNT_OUTPUT(
			"blockAccountOutput"), UNBLOCK_ACCOUNT_INPUT("unblockAccountInput"), UNBLOCK_ACCOUNT_OUTPUT(
			"unblockAccountOutput"), BLOCK_CARD_INPUT("blockCardInput"), BLOCK_CARD_OUTPUT(
			"blockCardOutput"), UNBLOCK_CARD_INPUT("unblockCardInput"), UNBLOCK_CARD_OUTPUT(
			"unblockCardOutput"), RESUME_ACCOUNT_INPUT("resumeAccountInput"), RESUME_ACCOUNT_OUTPUT(
			"resumeAccountOutput"), TXN_STATUS("txnStatus"), TXN_MSG(
			"txnMessage"), USER_ID("userId"), MOBILE_PIN("mobilePIN"), ACTIVATION_ID(
			"activationId"), PAYMENT_INSTRUMENT_ID("paymentInstrumentId"), STATUS(
			"status"), UNIQUE_ID("uniqueId"), WALLET_CARD_REF("walletCardRef"), NAME_ON_CARD(
			"cardHolderName"), CARD_NUMBER("cardNumber"), EXPIRY_MONTH(
			"expiryMonth"), EXPIRY_YEAR("expiryYear"), CVV("cvv2"), PIN("pin"), DEVICE_PAN(
			"devicePan"), ISSUER_ID("issuerId"), REMARKS("remarks"), SET_HCE_PIN(
			"SETHCEPIN"), REGISTER_USER("REGUSER"), SUSPEND_HCE_CARD(
			"SUSHCECARD"), INITIALIZE_MPA("INITMPA"), RESUME_HCE_CARD(
			"RESHCECARD"), ADD_HCE_CARD("ADDHCECARD"), GET_TOKEN("GETTOK"), SECURE_SESSION(
			"SECSESSION"), ACTIVATION_PROOF("ACTPROOF"), DELETE_HCE_CARD(
			"DELHCECARD"), RESET_PIN("RESETPIN"), MAKE_PAYMENT("MAKEPYMT"), NOTIFY_INITIALIZATION_COMPLETE(
			"NOTIFYINIT"), RESEND_OTP(""), RESET_MPA(""), BANK_NAME("bankName"), NICK_NAME(
			"nickName"), BILLING_ADDRESS_1("billingAddress1"), BILLING_ADDRESS_2(
			"billingAddress2"), BILLING_ADDRESS_3("billingAddress3"), CITY(
			"city"), INSTRUMENT_TYPE("instrumentType"), ADD_CARD_REQUEST(
			"addCardRequest"), HCE_ENABLED("hceEnabled"), PAYMENTINSTRUMENT_ID(
			"paymentInstrumentId"),PAYER_CARD_NUMBER("payerCardNumber"),

	HCE_ADD_CARD_BEARER("MOBILE"), HCE_ADD_CARD_PHASE_CODE("PYMTINSTRU"), HCE_ADD_CARD_SERVICE_CODE(
			"PYMTINSTRU"), HCE_ADD_CARD_SUB_TRANSACTION_CODE("PYMTINSTRU"), HCE_ADD_CARD_INTERACTION_CODE(
			"ADDCARD"), HCE_ENABLE_CARD_BEARER("MOBILE"), HCE_ENABLE_CARD_PHASE_CODE(
			"PYMTINSTRU"), HCE_ENABLE_CARD_SERVICE_CODE("PYMTINSTRU"), HCE_ENABLE_CARD_SUB_TRANSACTION_CODE(
			"PYMTINSTRU"), HCE_ENABLE_CARD_INTERACTION_CODE("ADDCARDHCE"), ENABLE_HCE(
			"enableHCEServiceInput"), HCE_GET_CARD_BEARER("MOBILE"), HCE_GET_CARD_PHASE_CODE(
			"PYMTINSTRU"), HCE_GET_CARD_SERVICE_CODE("PYMTINSTRU"), HCE_GET_CARD_SUB_TRANSACTION_CODE(
			"PYMTINSTRU"), HCE_GET_CARD_INTERACTION_CODE("GETCARDLIST"), GET_CARD_LIST(
			"getCardListInput"), DELETE_CARD_BEARER("MOBILE"), DELETE_CARD_PHASE_CODE(
			"PYMTINSTRU"), DELETE_CARD_SERVICE_CODE("PYMTINSTRU"), DELETE_CARD_SUB_TRANSACTION_CODE(
			"PYMTINSTRU"), DELETE_CARD_INTERACTION_CODE("DELETEHCECARD"), DELETE_CARD_LIST(
			"getCardListInput"), DELETE_HCE("deleteHCECardInput"), DELETE_CARD_NORMAL_INTERACTION_CODE(
			"DELETECARD"), DELETE_Normal("deleteCardInput"),

	//Add Social Media

	CONNECT_SOCIAL_BEARER("MOBILE"), CONNECT_SOCIAL_PHASE_CODE("MYPROF"), CONNECT_SOCIAL_SERVICE_CODE(
			"MYPROF"),CONNECT_SOCIAL_TRANSACTION_CODE("MYPROF"), CONNECT_SOCIAL_INTERACTION_CODE(
			"SOCIALCONCT"),CONNECT_SOCIAL("socialConnectInput"),SOCIAL_LIST("socialMediaList"),SOCIAL_TYPE("type"),
	         SOCIAL_ID("socialMediaId"),

			/* DELETE SOCIAL Handle TO ACCOUNT */

	       DELETE_SOCIAL_HANDLE_BEARER("MOBILE"), DELETE_SOCIAL_HANDLE_PHASE_CODE(
			"MYPROF"), DELETE_SOCIAL_HANDLE_SERVICE_CODE("MYPROF"), DELETE_SOCIAL_HANDLE_SUB_TRANSACTION_CODE(
			"MYPROF"), DELETE_SOCIAL_HANDLE_INTERACTION_CODE("DELSOCIALCONCT"), DELETE_SOCIAL_HANDLE(
			"deleteSocialConnectInput"),

	/* Get User Profile */

	GET_PROF_BEARER("MOBILE"), GET_PROF_PHASE_CODE("MYPROF"), GET_PROF_SERVICE_CODE(
			"MYPROF"), GET_PROF_SUB_TRANSACTION_CODE("MYPROF"), GET_PROF_INTERACTION_CODE(
			"GETUSERPROF"), GET_PROF("getProfileDetailsInput"), OLD_PIN(
			"oldPin"), NEW_PIN("newPin"),

	/* Change Pin */

	CHANGE_PIN_BEARER("MOBILE"), CHANGE_PIN_PHASE_CODE("MYPROF"), CHANGE_PIN_SERVICE_CODE(
			"MYPROF"), CHANGE_PIN_SUB_TRANSACTION_CODE("MYPROF"), CHANGE_PIN_INTERACTION_CODE(
			"CHPIN"), CHANGE_PIN("changePinInput"),

	/* Get Unread Notifications */

	GET_UNREAD_NOTIFICATION_BEARER("MOBILE"), GET_UNREAD_NOTIFICATION_PHASE_CODE(
			"MYPROF"), GET_UNREAD_NOTIFICATION_SERVICE_CODE("MYPROF"), GET_UNREAD_NOTIFICATION_SUB_TRANSACTION_CODE(
			"MYPROF"), GET_UNREAD_NOTIFICATION_INTERACTION_CODE("GETUNREAD"), GET_UNREAD_NOTIFICATION(
			"getUnreadNotificationCountInput"),




	/* Get All Notifications */

	GET_All_NOTIFICATION_BEARER("MOBILE"), GET_All_NOTIFICATION_PHASE_CODE(
			"MYPROF"), GET_All_NOTIFICATION_SERVICE_CODE("MYPROF"), GET_All_NOTIFICATION_SUB_TRANSACTION_CODE(
			"MYPROF"), GET_All_NOTIFICATION_INTERACTION_CODE("GETNOTIF"), GET_All_NOTIFICATION(
			"getListOfNotificationsInput"), NOTIFICATION_STATUS(
			"notificationStatus"), NO_OF_NOTIFICATIONS("noOfNotifications"),

	/* Mark Notifications */

	MARK_NOTIFICATION_BEARER("MOBILE"), MARK_NOTIFICATION_PHASE_CODE("MYPROF"), MARK_NOTIFICATION_SERVICE_CODE(
			"MYPROF"), MARK_NOTIFICATION_SUB_TRANSACTION_CODE("MYPROF"), MARK_NOTIFICATION_INTERACTION_CODE(
			"MARKREAD"), MARK_NOTIFICATION("markNotificationAsReadInput"),NOTIFICATION_ID(
					"notificationId"),
	/* getBalanceHeader */

	BALANCE_BEARER("MOBILE"), BALANCE_PHASE_CODE("WALTMGMT"), BALANCE_SERVICE_CODE(
			"WALTMGMT"), BALANCE_SUB_TRANSACTION_CODE("WALTMGMT"), BALANCE_INTERACTION_CODE(
			"GETBAL"), BAL_ENQ("getBalanceInput"), BALANCE_SERVICE_ID(""), DEVICE_DETAILS(
			"deviceDetails"), IMEI("imei"), FETCH_CARD_BEARER("MOBILE"), FETCH_CARD_PHASE_CODE(
			"WALTMGMT"), FETCH_CARD_SERVICE_CODE("WALTMGMT"), FETCH_CARD_SUB_TRANSACTION_CODE(
			"WALTMGMT"), FETCH_CARD_INTERACTION_CODE("GETCARDS"), FETCH_CARD(
			"getStoredCardsInput"),

	/* CREATESVAERROR */

	CREATE_BEARER("MOBILE"), CREATE_PHASE_CODE("CUSTREG"), CREATE_SERVICE_CODE(
			"CUSTREG"), CREATE_SUB_TRANSACTION_CODE("CUSTREG"), CREATE_INTERACTION_CODE(
			"CREATESVA"), CREATE_ENQ("createSvaInput"), CREATE_SERVICE_ID(""),

	/* CREATEGROUP */

	CREATE_GROUP_BEARER("MOBILE"), CREATE_GROUP_PHASE_CODE("PYMT"), CREATE_GROUP_SERVICE_CODE(
			"PYMT"), CREATE_GROUP_SUB_TRANSACTION_CODE("PYMT"), CREATE_GROUP_INTERACTION_CODE(
			"CGRP"), CREATE_GROUP("createGroupInput"), CREATE_GROUP_SERVICE_ID(
			""), GROUP_NAME("groupName"), GROUP_MEMBER_LIST("groupMemberList"),GROUP_ID(
			"groupId"),

	/* CREATEGROUP */

	GET_GROUPS_BEARER("MOBILE"), GET_GROUPS_PHASE_CODE("PYMT"), GET_GROUPS__SERVICE_CODE(
			"PYMT"), GET_GROUPS_SUB_TRANSACTION_CODE("PYMT"), GET_GROUPS_INTERACTION_CODE(
			"CGRP"), GET_GROUPS("getGroupsInput"), GET_GROUPS_SERVICE_ID(""),

	/* LOADSVA */

	ADD_TRANS_PHASE_CODE("PYMT"), ADD_TRANS__SERVICE_CODE("PYMT"), ADD_TRANS_SUB_TRANSACTION_CODE(
			"PYMT"), ADD_TRANS_INTERACTION_CODE("ADDTXN"), ADD_TRANS(
			"addTransactionDetailsInput"), ADD_TRANS_SERVICE_ID(""), ADD_TRANS__BEARER(
			"WEB"),

	LOAD_SVA_PHASE_CODE("PYMT"), LOAD_SVA__SERVICE_CODE("PYMT"), LOAD_SVA_SUB_TRANSACTION_CODE(
			"PYMT"), LOAD_SVA_INTERACTION_CODE("LOADSVA"), LOAD_SVA(
			"loadSVAInput"), LOAD_SVA_SERVICE_ID(""), LOAD_SVA__BEARER("WEB"), TRANS_ID(
			"transactionId"),

	UPDATE_TRANS_PHASE_CODE("PYMT"), UPDATE_TRANS__SERVICE_CODE("PYMT"), UPDATE_TRANS_SUB_TRANSACTION_CODE(
			"UPDATETXN"), UPDATE_TRANS_INTERACTION_CODE("LOADSVA"), UPDATE_TRANS(
			"updateTransactionDetailsInput"), UPDATE_TRANS_SERVICE_ID(""), UPDATE_TRANS__BEARER(
			"WEB"),

	/*
	 * REQMONEY
	 */

	REQ_MONEY_PHASE_CODE("PYMT"), REQ_MONEY__SERVICE_CODE("PYMT"), REQ_MONEY_SUB_TRANSACTION_CODE(
			"PYMT"), REQ_MONEY_INTERACTION_CODE("REQMON"), REQ_MONEY(
			"requestMoneyInput"), REQ_MONEY_SERVICE_ID(""), REQ_MONEY__BEARER(
			"MOBILE"), CHANNEL("channelCode"), CARD_NO("payerCardNumber"), PAYER_ID(
			"payerId"), PAYER_INSTRU_TYPE("payerInstrumentType"), TRANS_STAGE(
			"transactionStage"), TRANS_TYPE("transactionType"), ON_FLY(
			"onTheFly"), OTHER_ATTR("otherAttributes"),

	/* SENDMONEY */

	SEN_MONEY_PHASE_CODE("PYMT"), SEN_MONEY__SERVICE_CODE("PYMT"), SEN_MONEY_SUB_TRANSACTION_CODE(
			"PYMT"), SEN_MONEY_INTERACTION_CODE("P2P"), SEN_MONEY(
			"p2PSendMoneyInput"), SEN_MONEY_SERVICE_ID(""), SEN_MONEY_BEARER(
			"MOBILE"), PAYER_NAME("payerName"), PAYER_MOBILE(
			"payerMobileNumber"), PAYER_EMAIL("payerEmailId"), AMOUNT("AMOUNT"), CHARGES(
			"isChargesApplicable"), EMAIL("emailId"), MSISDN("msisdn"), REFER_ID(
			"referenceId1"), PAYEE_ID("payeeId"),OTP("otp"),

	//Send Money using Facebook ID

	Social_Media("socialMedia"),Type("type"),Social_Media_Id("socialMediaId"),


	/* SENDPENDINGREQUEST */

	PENDING_REQ_BEARER("MOBILE"), PENDING_REQ_PHASE_CODE("PYMT"), PENDING_REQ__SERVICE_CODE(
			"PYMT"), PENDING_REQ_SUB_TRANSACTION_CODE("PYMT"), PENDING_REQ_INTERACTION_CODE(
			"PENDINGREQ"), PEN_REQ("pendingRequestInput"),

	/* ACCEPTREQUESTMONEY */

	ACCEPT_REQ_MON_BEARER("MOBILE"), ACCEPT_REQ_MON_PHASE_CODE("PYMT"), ACCEPT_REQ_MON__SERVICE_CODE(
			"PYMT"), ACCEPT_REQ_MON_SUB_TRANSACTION_CODE("PYMT"), ACCEPT_REQ_MON_INTERACTION_CODE(
			"ACPTREQMON"), ACCEPT_REQ_MON("acceptRequestMoneyInput"), REQ_MON_ID(
			"requestMoneyId"),

	/* ACCEPTREQUESTMONEY */

	REJECT_REQ_MON_BEARER("MOBILE"), REJECT_REQ_MON_PHASE_CODE("PYMT"), REJECT_REQ_MON__SERVICE_CODE(
			"PYMT"), REJECT_REQ_MON_SUB_TRANSACTION_CODE("PYMT"), REJECT_REQ_MON_INTERACTION_CODE(
			"REJREQMON"), REJECT_REQ_MON("rejectRequestMoneyInput"),

	/* SENDPENDINGREQUEST */

	REQ_PENDING_REQ_BEARER("MOBILE"), REQ_PENDING_REQ_PHASE_CODE("PYMT"), REQ_PENDING_REQ__SERVICE_CODE(
			"PYMT"), REQ_PENDING_REQ_SUB_TRANSACTION_CODE("PYMT"), REQ_PENDING_REQ_INTERACTION_CODE(
			"VIEWPAYERREQMON"), REQ_PENDING_REQ("viewPayeeRequestedMoneyInput"),

	/* INITIATECHIPIN */

	INI_CHIPIN_REQ_BEARER("MOBILE"), INI_CHIPIN_REQ_PHASE_CODE("PYMT"), INI_CHIPIN_REQ__SERVICE_CODE(
			"PYMT"), INI_CHIPIN_REQ_SUB_TRANSACTION_CODE("PYMT"), INI_CHIPIN_REQ_INTERACTION_CODE(
			"CHIPININIT"), INI_CHIPIN_REQ("chipinInitiationInput"), TOTAL_AMOUNT(
			"totalAmount"), EVENT_ID("eventId"), EVENT_NAME("chipinEventName"), MEMBER_DETAILS(
			"chipinMemberDetailsList"),

	/* Transaction History Tags */

	TRANS_HIS_REQ_BEARER("MOBILE"), TRANS_HIS_REQ_PHASE_CODE("TXNHIS"), TRANS_HIS_REQ_SERVICE_CODE(
			"TXNHIS"), TRANS_HIS_REQ_SUB_TRANSACTION_CODE("TXNHIS"), TRANS_HIS_REQ_INTERACTION_CODE(
			"GETTXNLIST"), TRANS_HIS_REQ("getTransactionListInput"), SORT(
			"sortBy"), TRANS_LIST("transactionStatusList"), NUM_OF_TRANSACTION(
			"numberOfTransactions"), PAGE_NUM("pageNumber"), CHECH_AS("checkAs"),

	/* Recharge Tags */

	RECHARGE_REQ_BEARER("MOBILE"), RECHARGE_REQ_PHASE_CODE("PYMT"), RECHARGE_REQ_SERVICE_CODE(
			"PYMT"), RECHARGE_REQ_SUB_TRANSACTION_CODE("PYMT"), RECHARGE_REQ_INTERACTION_CODE(
			"RECHARGE"), RECHARGE("rechargeInput"), RECHARGE_TYPE(
			"rechargeType"), OPERATOR("operator"), MOBILE_NO("mobileNo"), PLAN(
			"plan"), ACCOUNT_NUMBER("accountNumber"),

	/* Chipin */

	CHIP_LIST_REQ_BEARER("MOBILE"), CHIP_LIST_REQ_PHASE_CODE("PYMT"), CHIP_LIST_SERVICE_CODE(
			"PYMT"), CHIP_LIST_SUB_TRANSACTION_CODE("PYMT"), CHIP_LIST_INTERACTION_CODE(
			"GETALLCHIP"), CHIP_LIST("getAllChipInListInput"), STATUS_LIST(
			"requiredStatusList"), START_DATE("startDate"), END_DATE("endDate"),CHIP_EVENT_ID("chipinEventId")
	,CHIP_EVENT_ID_LIST("chipinRequestIdList"),
	/* Reject Chipin */

	REJ_CHIP_REQ_BEARER("MOBILE"), REJ_CHIP_REQ_PHASE_CODE("PYMT"), REJ_CHIP_SERVICE_CODE(
			"PYMT"), REJ_CHIP_SUB_TRANSACTION_CODE("PYMT"), REJ_CHIP_INTERACTION_CODE(
			"CHIPRJCT"), REJ_CHIP("rejectChipinInput"), CHIPIN_REQ_ID(
			"chipinRequestId"),

	/* Accept Chipin */

	ACCEPT_CHIP_REQ_BEARER("MOBILE"), ACCEPT_CHIP_REQ_PHASE_CODE("PYMT"), ACCEPT_CHIP_SERVICE_CODE(
			"PYMT"), ACCEPT_CHIP_SUB_TRANSACTION_CODE("PYMT"), ACCEPT_CHIP_INTERACTION_CODE(
			"CHIPACPT"), ACCEPT_CHIP("acceptChipinInput"),

	/* Add Bank Account */

	ADD_BANK_REQ_BEARER("MOBILE"), ADD_BANK_REQ_PHASE_CODE("WALTMGMT"), ADD_BANK_SERVICE_CODE(
			"WALTMGMT"), ADD_BANK_SUB_TRANSACTION_CODE("WALTMGMT"), ADD_BANK_INTERACTION_CODE(
			"ADDBNKACC"), ADD_BANK("addBankAccountInput"), ACCOUNT_HOLDER_NAME(
			"accountHolderName"), IFSC("ifsc"),

	/* GET Bank Account */

	GET_ACC_REQ_BEARER("MOBILE"), GET_ACC_REQ_PHASE_CODE("WALTMGMT"), GET_ACC_SERVICE_CODE(
			"WALTMGMT"), GET_ACC_SUB_TRANSACTION_CODE("WALTMGMT"), GET_ACC_INTERACTION_CODE(
			"GETBNKACCS"), GET_ACC("getBankAccountsInput"),

	/* Delete Bank Account */

	DELETE_BANK_REQ_BEARER("MOBILE"), DELETE_BANK_REQ_PHASE_CODE("WALTMGMT"), DELETE_BANK_SERVICE_CODE(
			"WALTMGMT"), DELETE_BANK_SUB_TRANSACTION_CODE("WALTMGMT"), DELETE_BANK_INTERACTION_CODE(
			"DELBNKACC"), DELETE_BANK("deleteBankAccountInput"),

	/* GET Link Bank Account */

	GET_LINK_ACC_REQ_BEARER("MOBILE"), GET_LINK_ACC_REQ_PHASE_CODE("WALTMGMT"), GET_LINK_ACC_SERVICE_CODE(
			"WALTMGMT"), GET_LINK_ACC_SUB_TRANSACTION_CODE("WALTMGMT"), GET_LINK_ACC_INTERACTION_CODE(
			"GETLNKACC"), GET_LINK_ACC("getLinkedAccountsInput"),

	/* Load SVA with Bank Account */

	LOAD_SVA_BANK_REQ_BEARER("MOBILE"), LOAD_SVA_BANK_REQ_PHASE_CODE("PYMT"), LOAD_SVA_BANK_SERVICE_CODE(
			"PYMT"), LOAD_SVA_BANK_SUB_TRANSACTION_CODE("PYMT"), LOAD_SVA_BANK_INTERACTION_CODE(
			"LOADSVA"), LOAD_SVA_BANK("loadSvaWithLinkedAccountInput"), ACCOUNT_ACC_ID(
			"uniqueAccId"),

	/* Link De-link Account */

	LINK_BANK_REQ_BEARER("MOBILE"), LINK_BANK_REQ_PHASE_CODE("WALTMGMT"), LINK_BANK_SERVICE_CODE(
			"WALTMGMT"), LINK_BANK_SUB_TRANSACTION_CODE("WALTMGMT"), LINK_BANK_INTERACTION_CODE(
			"FETACC"), LINK_BANK("linkOrDeLinkAccountInput"), LINK_FLAG(
			"isLinkFlag"), ACC_DETAILS_LIST("accountDetailsList"),

	/* Transfer to Linked Bank Account */

	TRANSFER_TO_LINK_BANK_REQ_BEARER("MOBILE"), TRANSFER_TO_LINK_BANK_REQ_PHASE_CODE(
			"PYMT"), TRANSFER_TO_LINK_BANK_SERVICE_CODE("PYMT"), TRANSFER_TO_LINK_BANK_SUB_TRANSACTION_CODE(
			"PYMT"), TRANSFER_TO_LINK_BANK_INTERACTION_CODE("ADDTXNLINK"), TRANSFER_TO_LINK_BANK(
			"transferToBankAccountLinkedInput"),

	/* Transfer to Other Bank Account */

	TRANSFER_TO_OTHER_BANK_REQ_BEARER("MOBILE"), TRANSFER_TO_OTHER_BANK_REQ_PHASE_CODE(
			"PYMT"), TRANSFER_TO_OTHER_BANK_SERVICE_CODE("PYMT"), TRANSFER_TO_OTHER_BANK_SUB_TRANSACTION_CODE(
			"PYMT"), TRANSFER_TO_OTHER_BANK_INTERACTION_CODE("ADDTXNOTR"), TRANSFER_TO_OTHER_BANK(
			"transferToOtherBankAccountInput"), SAVE_BENEFICIARY_FLAG(
			"saveBeneficiaryFlag"), TRANSFER_MODE("transferMode"),


	/**Make Default*/


	MAKE_DEFAULT_BEARER(""),MAKE_DEFAULT_REQ_BEARER("MOBILE"),MAKE_DEFAULT_PHASE_CODE(
			"PYMT"), MAKE_DEFAULT_SERVICE_CODE("PYMT"),MAKE_DEFAULT_SUB_TRANSACTION_CODE(
			"PYMT"), MAKE_DEFAULT_INTERACTION_CODE("ADDTXNOTR"), MAKE_DEFAULT(
			"transferToOtherBankAccountInput"),


	LOGIN("LOGIN"), CUSTREG("CUSTREG"),
	BEARER_MOBILE("MOBILE"), MY_PROF("MYPROF"), SVA_CARD_MGMT("SVACARDMGMT"), WALT_MGMT("WALTMGMT"),
	PYMT("PYMT"), GET_TXN_LIST("GETTXNLIST"), TXN_HIS("TXNHIS"), ADD_BNK_ACC("ADDBNKACC"),


    PAGE_NUMBER("pageNumber"),CARD_TYPE("cardType"),REQUEST_TYPE("requestType"),REQUEST_id("requestId"),
	MESSAGE("message"), TRANSACTION_TYPE("transactionType"), TRANSACTION_ID("transactionId"),REQUEST_MONEY_id("requestMoneyId"),


	FICBALANCE("FICBALANCE"), TYPE("TYPE"), PROVIDER("PROVIDER"), PAYID("PAYID"), MPIN("MPIN"), LANGUAGE("LANGUAGE1"), BLOCKSMS("BLOCKSMS"), CELLID("CELLID"), FTXNID("FTXNID"), MOBILE("MSISDN"), USERTYPE("USERTYPE"), PROVIDER2("PROVIDER2"), PAYID2("PAYID2"), MERCODE("MERCODE"), SERVICE("SERVICE"), NEWMPIN("NEWMPIN"), CONFIRMMPIN("CONFIRMMPIN"), MOBILE2("MSISDN2"), DATA("DATA"), QNSCODE("QNSCODE"), ANSWER("ANSWER"), PINTYPE("PINTYPE"), NEWPIN("NEWPIN"), CONFIRMPIN("CONFIRMPIN"), ACCNO2("ACCNO2"), BANKID("BANKID"), LANG("LANG");

	private String tag;

	private RESTags(String tag) {
		this.tag = tag;
	}

	public String getTag() {
		return tag;
	}

}