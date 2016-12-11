package app.banking.bankmuscat.merchant.entity.instrument;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class NotificationItems implements Serializable {


	private String notificationId = "";
	private String notificationType = "";
	private String notificationStatus = "";
	private String isNewNotification = "";
	private String transactionType = "";
	private String notificationDateTime = "";
	private String message = "";


	private String firstName = "";
	private String amount = "";
	private String mobileNumber = "";
	private String emailId = "";
	private String transactionId = "";
	private String eventName = "";
	
	private static ArrayList<NotificationItems> sendlist;
    private static ArrayList<NotificationItems> requestlist;
	private static ArrayList<NotificationItems> chipinlist;
    private static ArrayList<NotificationItems> allItems;
	

public static ArrayList<NotificationItems> getSendlist() {
		return sendlist;
	}

	public static void setSendlist(ArrayList<NotificationItems> sendlist) {
		NotificationItems.sendlist = sendlist;
	}

	public static ArrayList<NotificationItems> getRequestlist() {
		return requestlist;
	}

	public static void setRequestlist(ArrayList<NotificationItems> requestlist) {
		NotificationItems.requestlist = requestlist;
	}

	public static ArrayList<NotificationItems> getChipinlist() {
		return chipinlist;
	}

	public static void setChipinlist(ArrayList<NotificationItems> chipinlist) {
		NotificationItems.chipinlist = chipinlist;
	}

	public static ArrayList<NotificationItems> getAllItems() {
		return allItems;
	}

	public static void setAllItems(ArrayList<NotificationItems> allItems) {
		NotificationItems.allItems = allItems;
	}

	

	public static ArrayList<NotificationItems> create(JSONObject res) {

		 sendlist =  new ArrayList<NotificationItems>();
		 requestlist =  new ArrayList<NotificationItems>();
		 chipinlist =  new ArrayList<NotificationItems>();
		 allItems = new ArrayList<NotificationItems>();

		

		JSONObject outPut = (JSONObject) res
				.get("getListOfNotificationsOutput");

		if (outPut.get("txnStatus").toString().equals("200")) {

			if (outPut.containsKey("notificationList")) {
				JSONArray notification = (JSONArray) outPut
						.get("notificationList");

				if (notification.size() != 0) {

					for (int i = 0; i < notification.size(); i++) {

						NotificationItems items = new NotificationItems();
						JSONObject data = (JSONObject) notification.get(i);
						
						if(data.containsKey("notificationId"))
						{
						items.setNotificationId(data.get("notificationId")
								.toString());
						}
						if(data.containsKey("notificationType"))
						{
						items.setNotificationType(data.get("notificationType")
								.toString());
						}
						if(data.containsKey("notificationStatus"))
						{
						items.setNotificationStatus(data.get(
								"notificationStatus").toString());
						}
						if(data.containsKey("isNewNotification"))
						{
						items.setIsNewNotification(data
								.get("isNewNotification").toString());
						}
						if(data.containsKey("transactionType"))
						{
						items.setTransactionType(data.get("transactionType")
								.toString());
						}
						if(data.containsKey("notificationDateTime"))
						{
						items.setNotificationDateTime(data.get(
								"notificationDateTime").toString());
						}
						if(data.containsKey("message"))
						{
						items.setMessage(data.get("message").toString());
						}
						if(data.containsKey("firstName"))
						{
						items.setFirstName(data.get("firstName").toString());
						}
						if(data.containsKey("mobileNumber"))
						{
						items.setMobileNumber(data.get("mobileNumber")
								.toString());
						}
						if(data.containsKey("emailId"))
						{
						items.setEmailId(data.get("emailId").toString());
						}
						if(data.containsKey("transactionId"))
						{
						items.setTransactionId(data.get("transactionId")
								.toString());
						}
						if(data.containsKey("amount"))
						{
						items.setAmount(data.get("amount").toString());
						}
						if (("SB").equals(data.get("transactionType")
								.toString())) {
							if(data.containsKey("eventName")) {
								items.setEventName(data.get("eventName").toString());
							}
							if(getChipinlist()== null)
							{
								chipinlist = new ArrayList<NotificationItems>();
							}
							chipinlist.add(items);
							
						} else if (("SM").equals(data.get("transactionType")
								.toString())) {
							if(getSendlist() == null)
							{
								sendlist = new ArrayList<NotificationItems>();
							}
							sendlist.add(items);
						} else if (("RM").equals(data.get("transactionType")
								.toString())) {
							if(getRequestlist() == null)
							{
								requestlist = new ArrayList<NotificationItems>();
							}
							requestlist.add(items);
						}
						if(getAllItems() == null)
						{
							allItems = new ArrayList<NotificationItems>();
						}
						allItems.add(items);

					}

				}
			}

		} else {

		}
		return allItems;

	}

	public String getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(String notificationId) {
		this.notificationId = notificationId;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public String getNotificationStatus() {
		return notificationStatus;
	}

	public void setNotificationStatus(String notificationStatus) {
		this.notificationStatus = notificationStatus;
	}

	public String getIsNewNotification() {
		return isNewNotification;
	}

	public void setIsNewNotification(String isNewNotification) {
		this.isNewNotification = isNewNotification;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getNotificationDateTime() {
		return notificationDateTime;
	}

	public void setNotificationDateTime(String notificationDateTime) {
		this.notificationDateTime = notificationDateTime;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}


}
