package app.banking.bankmuscat.merchant.entity.instrument;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import app.banking.bankmuscat.merchant.entity.BaseData;

public class Notification extends BaseData implements Serializable {
	@SerializedName("amount")
	private String amount;
	@SerializedName("message")
	private String message;
	@SerializedName("transactionType")
	private String transactionType;
	@SerializedName("isNewNotification")
	private String isNewNotification;
	@SerializedName("notificationId")
	private String notificationId;
	@SerializedName("isReminder")
	private String isReminder;
	@SerializedName("notificationStatus")
	private String notificationStatus;
	@SerializedName("transactionId")
	private String transactionId;
	@SerializedName("notificationDateTime")
	private String notificationDateTime;
	@SerializedName("notificationType")
	private String notificationType;
	@SerializedName("eventName")
	private String eventName;
	@SerializedName("mobileNumber")
	private String mobileNumber;
	@SerializedName("firstName")
	private String firstName;

	public String getAmount ()
	{
		return amount;
	}

	public void setAmount (String amount)
	{
		this.amount = amount;
	}

	public String getMessage ()
	{
		return message;
	}

	public void setMessage (String message)
	{
		this.message = message;
	}

	public String getTransactionType ()
	{
		return transactionType;
	}

	public void setTransactionType (String transactionType)
	{
		this.transactionType = transactionType;
	}

	public String getIsNewNotification ()
	{
		return isNewNotification;
	}

	public void setIsNewNotification (String isNewNotification)
	{
		this.isNewNotification = isNewNotification;
	}

	public String getNotificationId ()
	{
		return notificationId;
	}

	public void setNotificationId (String notificationId)
	{
		this.notificationId = notificationId;
	}

	public String getIsReminder ()
	{
		return isReminder;
	}

	public void setIsReminder (String isReminder)
	{
		this.isReminder = isReminder;
	}

	public String getNotificationStatus ()
	{
		return notificationStatus;
	}

	public void setNotificationStatus (String notificationStatus)
	{
		this.notificationStatus = notificationStatus;
	}

	public String getTransactionId ()
	{
		return transactionId;
	}

	public void setTransactionId (String transactionId)
	{
		this.transactionId = transactionId;
	}

	public String getNotificationDateTime ()
	{
		return notificationDateTime;
	}

	public void setNotificationDateTime (String notificationDateTime)
	{
		this.notificationDateTime = notificationDateTime;
	}

	public String getNotificationType ()
	{
		return notificationType;
	}

	public void setNotificationType (String notificationType)
	{
		this.notificationType = notificationType;
	}

	public String getEventName ()
	{
		return eventName;
	}

	public void setEventName (String eventName)
	{
		this.eventName = eventName;
	}

	public String getMobileNumber ()
	{
		return mobileNumber;
	}

	public void setMobileNumber (String mobileNumber)
	{
		this.mobileNumber = mobileNumber;
	}

	public String getFirstName ()
	{
		return firstName;
	}

	public void setFirstName (String firstName)
	{
		this.firstName = firstName;
	}

	@Override
	public String toString()
	{
		return "Notification [amount = "+amount+", message = "+message+", transactionType = "+transactionType+", isNewNotification = "+isNewNotification+", notificationId = "+notificationId+", isReminder = "+isReminder+", notificationStatus = "+notificationStatus+", transactionId = "+transactionId+", notificationDateTime = "+notificationDateTime+", notificationType = "+notificationType+", eventName = "+eventName+", mobileNumber = "+mobileNumber+", firstName = "+firstName+"]";
	}
	}



