package app.banking.bankmuscat.merchant.util.constant;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Manages IP address and ports number along with complete url
 * Created by amit.randhawa on 8/22/2016.
 */
public class UrlManager {
    public static final String URL_DB = "ServerIPPort";
    public static final String KEY_IP = "ServerIP";
    public static final String KEY_PORT = "ServerPort";
    public static final String CONTEXT_PATH = "RootPath";
    public static final String DEFAULT_HCE="HCEPort";


    /** Default IP Address */
    public static final String DEFAULT_IP = "54.77.154.84";
    /** Default Port */
    public static final String DEFAULT_PORT = "9090";
    /** Default Context*/
    public static final String DEFAULT_CONTEXT = "mwallet";
    /**Default HCE Port*/
    public static final String DEFAULT_HCE_PORT = "9000";
    public  String URL_SUFFIX_MAKE_DEFAULT = "rest/paymentInstrumentManagement/paymentinstrumentmanagement/AddCard";
    public  String URL_SUFFIX_ADD_CARD = "rest/paymentInstrumentManagement/paymentinstrumentmanagement/AddCard";
    public  String URL_SUFFIX_ENABLE_HCE_CARD = "rest/paymentInstrumentManagement/paymentinstrumentmanagement/EnableHCEService";
    public  String URL_SUFFIX_CREATE_CHIPIN_GROUP = "rest/payments2/Payments2/CreateChipInGroup";
    public  String URL_SUFFIX_GET_CHIPIN_GROUP = "rest/payments2/Payments2/GetChipInGroups";
    public  String URL_SUFFIX_ADD_TXN_DETAILS = "rest/payments2/Payments2/AddTransactionDetails";
    public  String URL_SUFFIX_LOAD_SVA = "rest/payments2/Payments2/LoadSVA";
    public  String URL_SUFFIX_UPDATE_TXN_DETAILS = "rest/payments2/Payments2/UpdateTransactionDetails";
    public  String URL_SUFFIX_REQUEST_MONEY = "rest/payments2/Payments2/RequestMoney";
    public  String URL_SUFFIX_P2P_SEND_MONEY = "rest/payments2/Payments2/P2PSendMoney";
    public  String URL_SUFFIX_ADD_SOCIAL_CONNECT = "rest/profile/Profile/AddSocialConnect";
    public  String URL_SUFFIX_DELETE_SOCIAL_CONNECT = "rest/profile/Profile/DeleteSocialConnect";
    public  String URL_SUFFIX_PENDING_REQUEST = "rest/payments2/Payments2/PendingRequest";
    public  String URL_SUFFIX_VIEW_PAYEE_REQUEST_MONEY = "rest/payments2/Payments2/ViewPayeeRequestedMoney";
    public  String URL_SUFFIX_REJECT_REQUEST_MONEY = "rest/payments2/Payments2/RejectRequestMoney";
    public  String URL_SUFFIX_INITIATE_CHIPIN = "rest/payments2/Payments2/ChipinInitiation";
    public  String URL_SUFFIX_GET_TXN_LIST = "rest/transactions/Transactions/GetTxnList";
    public  String URL_SUFFIX_GET_ALL_CHIPIN_LIST = "rest/payments2/Payments2/GetAllChipInList";
    public  String URL_SUFFIX_PAYER_PENDING_REQUEST = "rest/payments2/Payments2/PayerPendingRequest";
    public  String URL_SUFFIX_RECHARGE = "rest/payments2/Payments2/Recharge";
    public  String URL_SUFFIX_REJECT_CHIPIN = "rest/payments2/Payments2/RejectChipin";
    public  String URL_SUFFIX_ACCEPT_CHIPIN = "rest/payments2/Payments2/AcceptChipin";
    public  String URL_SUFFIX_ADD_OTHER_BANK_ACCOUNT = "rest/walletManagement/WalletManagement/AddOtherBankAccount";
    public  String URL_SUFFIX_LINKDELINK_ACCOUNT = "rest/walletManagement/WalletManagement/LinkOrDeLinkAccount";
    public  String URL_SUFFIx_TRANSFER_TO_BANK_ACCOUNT = "rest/payments2/Payments2/TransferToBankAccountLinked";
    public  String URL_SUFFIX_TRANSFER_TO_OTHER_BANK_ACCOUNT = "rest/payments2/Payments2/TransferToOtherBankAccount";
    public  String URL_SUFFIX_DELETE_OTHER_BANK_ACCOUNT = "rest/walletManagement/WalletManagement/DeleteOtherBankAccount";
    public  String URL_SUFFIX_GET_UNREADNOTIFICATION_COUNT = "rest/profile/Profile/GetUnreadNotificationCount";
    public  String URL_SUFFIX_GET_LIST_OF_NOTIFICATION = "rest/profile/Profile/GetListOfNotifications";
    public  String URL_SUFFIX_MARK_NOTIFICATION_AS_READ = "rest/profile/Profile/MarkNotificationAsRead";
    public  String URL_SUFFIX_GET_CARD_LIST = "rest/paymentInstrumentManagement/paymentinstrumentmanagement/GetCardList";
    public  String URL_SUFFIX_GET_OTHER_BANK_ACCOUNT = "rest/walletManagement/WalletManagement/GetOtherBankAccounts";
    public  String URL_SUFFIX_GETLINKED_ACCOUNTS = "rest/walletManagement/WalletManagement/GetLinkedAccounts";
    public  String URL_SUFFIX_CREATE_SVA = "rest/registration/Registration/CreateSva";
    public  String URL_SUFFIX_GET_BALANCE = "rest/walletManagement/WalletManagement/GetBalance";
    public  String URL_SUFFIX_DELETE_HCE_CARD = "rest/paymentInstrumentManagement/paymentinstrumentmanagement/DeleteHCECard";
    public  String URL_SUFFIX_DELETE_CARD = "rest/walletManagement/WalletManagement/DeleteCard";
    public  String URL_SUFFIX_GET_PROFILE_DETAILS = "rest/profile/Profile/GetProfileDetails";
    public  String URL_SUFFIX_ACCEPT_REQUEST_MONEY = "rest/payments2/Payments2/AcceptRequestMoney";
    public String URL_SUFFIX_CMS ="CMS/consumerEnrollment/rest/ConsumerEnrollment/";
    public String getURL_SUFFIX_GET_CARD_LIST() {
        return prepareUrl(URL_SUFFIX_GET_CARD_LIST);
    }
    public String getHCEURL_SUFFIX_GET_CARD_LIST() {
        return prepareHCEUrl(URL_SUFFIX_CMS);
    }
    public String getURL_SUFFIX_GET_OTHER_BANK_ACCOUNT() {return prepareUrl(URL_SUFFIX_GET_OTHER_BANK_ACCOUNT);}
    public String getURL_SUFFIX_GETLINKED_ACCOUNTS() {return  prepareUrl(URL_SUFFIX_GETLINKED_ACCOUNTS);}
    public String getURL_SUFFIX_CREATE_SVA() {
        return  prepareUrl(URL_SUFFIX_CREATE_SVA);
    }
    public String getURL_SUFFIX_GET_BALANCE() {
        return  prepareUrl(URL_SUFFIX_GET_BALANCE);
    }
    public String getURL_SUFFIX_DELETE_HCE_CARD() {return  prepareUrl(URL_SUFFIX_DELETE_HCE_CARD);}
    public String getURL_SUFFIX_DELETE_CARD() {
        return  prepareUrl(URL_SUFFIX_DELETE_CARD);
    }
    public String getURL_SUFFIX_GET_PROFILE_DETAILS() {return  prepareUrl(URL_SUFFIX_GET_PROFILE_DETAILS);}
    public String getURL_SUFFIX_CREATE_CHIPIN_GROUP() {return  prepareUrl(URL_SUFFIX_CREATE_CHIPIN_GROUP);}
    public String getURL_SUFFIX_GET_CHIPIN_GROUP() {return  prepareUrl(URL_SUFFIX_GET_CHIPIN_GROUP);}

    public String getURL_SUFFIX_ADD_TXN_DETAILS() {
        return  prepareUrl(URL_SUFFIX_ADD_TXN_DETAILS);
    }

    public String getURL_SUFFIX_LOAD_SVA() {
        return  prepareUrl(URL_SUFFIX_LOAD_SVA);
    }

    public String getURL_SUFFIX_UPDATE_TXN_DETAILS() {
        return  prepareUrl(URL_SUFFIX_UPDATE_TXN_DETAILS);
    }

    public String getURL_SUFFIX_REQUEST_MONEY() {
        return  prepareUrl(URL_SUFFIX_REQUEST_MONEY);
    }

    public String getURL_SUFFIX_P2P_SEND_MONEY() {
        return  prepareUrl(URL_SUFFIX_P2P_SEND_MONEY);
    }

    public String getURL_SUFFIX_ADD_SOCIAL_CONNECT() {
        return  prepareUrl(URL_SUFFIX_ADD_SOCIAL_CONNECT);
    }

    public String getURL_SUFFIX_DELETE_SOCIAL_CONNECT() {
        return  prepareUrl(URL_SUFFIX_DELETE_SOCIAL_CONNECT);
    }

    public String getURL_SUFFIX_PENDING_REQUEST() {
        return  prepareUrl(URL_SUFFIX_PENDING_REQUEST);
    }

    public String getURL_SUFFIX_VIEW_PAYEE_REQUEST_MONEY() {
        return  prepareUrl(URL_SUFFIX_VIEW_PAYEE_REQUEST_MONEY);
    }

    public String getURL_SUFFIX_REJECT_REQUEST_MONEY() {
        return  prepareUrl(URL_SUFFIX_REJECT_REQUEST_MONEY);
    }

    public String getURL_SUFFIX_INITIATE_CHIPIN() {
        return  prepareUrl(URL_SUFFIX_INITIATE_CHIPIN);
    }

    public String getURL_SUFFIX_GET_TXN_LIST() {
        return  prepareUrl(URL_SUFFIX_GET_TXN_LIST);
    }

    public String getURL_SUFFIX_PAYER_PENDING_REQUEST() {
        return  prepareUrl(URL_SUFFIX_PAYER_PENDING_REQUEST);
    }

    public String getURL_SUFFIX_GET_ALL_CHIPIN_LIST() {
        return  prepareUrl(URL_SUFFIX_GET_ALL_CHIPIN_LIST);
    }

    public String getURL_SUFFIX_RECHARGE() {
        return  prepareUrl(URL_SUFFIX_RECHARGE);
    }

    public String getURL_SUFFIX_REJECT_CHIPIN() {
        return  prepareUrl(URL_SUFFIX_REJECT_CHIPIN);
    }

    public String getURL_SUFFIX_ACCEPT_CHIPIN() {
        return  prepareUrl(URL_SUFFIX_ACCEPT_CHIPIN);
    }

    public String getURL_SUFFIX_ADD_OTHER_BANK_ACCOUNT() {
        return  prepareUrl(URL_SUFFIX_ADD_OTHER_BANK_ACCOUNT);
    }

    public String getURL_SUFFIX_LINKDELINK_ACCOUNT() {
        return prepareUrl(URL_SUFFIX_LINKDELINK_ACCOUNT);
    }

    public String getURL_SUFFIX_DELETE_OTHER_BANK_ACCOUNT() {
        return prepareUrl(URL_SUFFIX_DELETE_OTHER_BANK_ACCOUNT);
    }

    public String getURL_SUFFIX_TRANSFER_TO_OTHER_BANK_ACCOUNT() {
        return  prepareUrl(URL_SUFFIX_TRANSFER_TO_OTHER_BANK_ACCOUNT);
    }

    public String getURL_SUFFIX_ACCEPT_REQUEST_MONEY() {
        return  prepareUrl(URL_SUFFIX_ACCEPT_REQUEST_MONEY);
    }

    public String getURL_SUFFIx_TRANSFER_TO_BANK_ACCOUNT() {
        return  prepareUrl(URL_SUFFIx_TRANSFER_TO_BANK_ACCOUNT);
    }

    public String getURL_SUFFIX_GET_UNREADNOTIFICATION_COUNT() {
        return  prepareUrl(URL_SUFFIX_GET_UNREADNOTIFICATION_COUNT);
    }

    public String getURL_SUFFIX_GET_LIST_OF_NOTIFICATION() {
        return  prepareUrl(URL_SUFFIX_GET_LIST_OF_NOTIFICATION);
    }

    public String getURL_SUFFIX_MARK_NOTIFICATION_AS_READ() {
        return  prepareUrl(URL_SUFFIX_MARK_NOTIFICATION_AS_READ);
    }

    public String getURL_SUFFIX_MAKE_DEFAULT() {
        return  prepareUrl(URL_SUFFIX_MAKE_DEFAULT);
    }
    public String getURL_SUFFIX_ADD_CARD() {
        return prepareUrl(URL_SUFFIX_ADD_CARD);
    }

    public String getURL_SUFFIX_ENABLE_HCE_CARD() {
        return prepareUrl(URL_SUFFIX_ENABLE_HCE_CARD);
    }

    private SharedPreferences sharedPreference;
    public static UrlManager urlManager;

    private UrlManager(Context context) {
        try {
            sharedPreference = context.getSharedPreferences(URL_DB, Context.MODE_PRIVATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String prepareUrl(String urlSuffix) {
        return "http://" + getIP() + ":" + getPort() +  "/" + getContextPath() + "/" + urlSuffix;
    }

    private String prepareHCEUrl(String urlSuffix) {
        return "https://" + getIP() + ":" + getHcePort() +  "/" + urlSuffix;
    }

    public static UrlManager getInstance(Context context) {
        if(urlManager == null) {
            urlManager = new UrlManager(context);
        }
        return urlManager;
    }

    /**
     * Set server IP;
     * @param ip IP Adrress
     */
    public void setServerIP(String ip) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(KEY_IP, ip);
        editor.apply();
    }

    /**
     * Set Server Port.
     * @param port Port Number
     */
    public void setPort(String port) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(KEY_PORT, port);
        editor.commit();
    }


    public void setHcePort(String port) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(DEFAULT_HCE_PORT, port);
        editor.commit();
    }

    public void setContextPath(String path) {
        SharedPreferences.Editor editor = sharedPreference.edit();
        editor.putString(KEY_PORT, path);
        editor.commit();
    }
    public String getContextPath(){return sharedPreference.getString(CONTEXT_PATH,DEFAULT_CONTEXT);}
    public String getIP() {
        return sharedPreference.getString(KEY_IP, DEFAULT_IP);
    }
    public String getPort() {
        return sharedPreference.getString(KEY_PORT, DEFAULT_PORT);
    }
    public String getHcePort() {
        return sharedPreference.getString(DEFAULT_HCE, DEFAULT_HCE_PORT);
    }
    public String getAddCardUrl() {
        return prepareUrl(URL_SUFFIX_ADD_CARD);
    }




}
