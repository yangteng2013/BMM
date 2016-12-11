/**
 * \ * @author U36838
 * <p>
 * APIManager is manager class handle all the api call from this
 * application
 * Singleton class
 */
package app.banking.bankmuscat.merchant.api;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import org.apache.http.HttpConnection;
import org.json.JSONException;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import app.banking.bankmuscat.merchant.common.ErrorMessageCodes;
import app.banking.bankmuscat.merchant.common.Wallet;
import app.banking.bankmuscat.merchant.db.DbManager;
import app.banking.bankmuscat.merchant.entity.instrument.Bank;
import app.banking.bankmuscat.merchant.entity.instrument.Card;
import app.banking.bankmuscat.merchant.entity.instrument.Customer;

import app.banking.bankmuscat.merchant.entity.instrument.Notification;

import app.banking.bankmuscat.merchant.entity.instrument.SecurityQuestions;

import app.banking.bankmuscat.merchant.entity.instrument.Transaction;
import app.banking.bankmuscat.merchant.entity.instrument.TransactionHistory;
import app.banking.bankmuscat.merchant.entity.instrument.TxnData;
import app.banking.bankmuscat.merchant.logger.CLog;
import app.banking.bankmuscat.merchant.util.constant.UrlManager;

/*import app.banking.bankmuscat.wallet.entity.instrument.Customer;*/


//import org.kxml2.kdom.Element;

public final class APIManager {
    //
    private static final String TAG = "APIManager";
    //
    private static final String API_VERSION = "1.0";
    //public static final ResponseData responseData = null;

    // Holds the instance of APIManager
    private static APIManager instance;

    // Handler for processing the messages from SOAPOperationsTask
    private final Handler handler;

    Context context;

 String auth =  "Bearer 9Q_gNu3hoqkxqNHB8Gk8UQvjxnAa";
/*
String auth =  "Bearer e3ef25355a25d2acb5bbca356545fd2";
*/



    private DbManager dbManager;
    // Context context;
    // This table tracks the task which are currently running
    private final Hashtable<UUID, SOAPOperationsTask> taskTable = new Hashtable<UUID, SOAPOperationsTask>();

    // Holds the listeners
    private final List<APIMessageListener> listeners = new ArrayList<APIMessageListener>();

    private UrlManager urlManager;

    /**
     * Creates the Singleton instance of APIManager
     *
     * @return Instance of APIManager
     */

    HttpConnection httpconnection = null;
    HashMap<String, Object> deviceDetails = null;

    public static APIManager createInstance(Context context) {

        if (instance == null) {
            instance = new APIManager(context);
            CLog.i(TAG, "single instance created");
        }
        return instance;
    }

    // constructor
    private APIManager(Context context) {
        urlManager = UrlManager.getInstance(context);
        this.context = context;

        TelephonyManager m_telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        deviceDetails = new HashMap<String, Object>();
        AddItem(deviceDetails, "deviceOS", "ANDROID");
        AddItem(deviceDetails, "deviceOSVersion", android.os.Build.VERSION.RELEASE);
        AddItem(deviceDetails, "deviceManufacturer", android.os.Build.MANUFACTURER);
        AddItem(deviceDetails, "deviceModel", android.os.Build.MODEL);
        AddItem(deviceDetails, "imei", m_telephonyManager.getDeviceId());


        this.handler = new Handler() {

            /**
             * Handles message from SOAPOperationsTask
             */
            @Override
            public void handleMessage(Message message) {

                // Gets the bundle data from message
                Bundle bundle = message.getData();

                Serializable data = bundle.getSerializable("data");
                if (data != null) {

                    SOAPOperationData operationsData = (SOAPOperationData) data;
                    taskTable.remove(operationsData.id);
                    for (int i = 0; i < listeners.size(); i++)
                    {
                        APIMessageListener listener = listeners.get(i);
                        listener.handleMessage(operationsData);
                    }
                }
            }
        };

    }


    /**
     * Adds listener for handling the messages from API manager
     *
     * @param listener
     */
    public void addListener(APIMessageListener listener) {
        this.listeners.add(listener);
        //
        CLog.i(TAG, "addListener");
    }

    /**
     * Removes the listener
     *
     * @param listener
     */
    public void removeListener(APIMessageListener listener) {
        if (this.listeners.contains(listener)) {
            this.listeners.remove(listener);
            //
            CLog.i(TAG, "removeListener");
        }
    }

    /**
     * Cancels a request made
     *
     * @param requestId
     */
    public void cancelRequest(UUID requestId) {

        if (this.taskTable.containsKey(requestId)) {
            this.taskTable.get(requestId).cancel(true);
            this.taskTable.remove(requestId);
            //
            CLog.i(TAG, "cancelRequest");
        }
    }

    /**
     * Asynchronous task for handling HTTP operations (like GET, POST)
     */


    private class SOAPOperationsTask extends
            AsyncTask<SOAPOperationData, String, SOAPOperationData> {

        private static final String TAG = "SOAPOperationsTask";
        // operation handler
        private final Handler handler;

        protected SOAPOperationsTask(Handler handler) {
            this.handler = handler;
        }

        public String OpenHttpConnection(String url, String data)
                throws IOException, JSONException {
            try {
                System.out.println("OpenHttpConnection ...");

                HttpURLConnection conn;
                URL obj = new URL(url);
                conn = (HttpURLConnection) obj.openConnection();
                conn.setConnectTimeout(120000);
                System.out.println("openConnection ..." + conn);


                // Acts like a browser
                conn.setAllowUserInteraction(false);
                conn.setInstanceFollowRedirects(true);
                conn.setRequestMethod("POST");

                conn.setRequestProperty("Content-Type",
                        "application/json");

                conn.setRequestProperty("Authorization", auth);

                // Send post request
                DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
                // new

                if(!data.contains("loadSVAWithBMCardInput"))
                    data = data.replace("\\", "");
                wr.writeBytes(data);
                wr.flush();
                wr.close();

                int responseCode = conn.getResponseCode();
                String jsonString = conn.getResponseMessage();

                System.out.println("responseCode ..." + responseCode);
                StringWriter sw = null;
                sw = new StringWriter();

                //create buffered writer

                BufferedWriter out = new BufferedWriter(sw);

                out.append(data);
                out.newLine();

                out.append(Integer.toString(responseCode));
                out.newLine();

                out.append(jsonString);
                out.newLine();


                BufferedReader in = new BufferedReader(new InputStreamReader(
                        conn.getInputStream()));

                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //Log.d("ScotiaBank", response.toString());
                //out.append(response.toString());
                // out.newLine();;



			/*Object obj1 = JSONValue.parse(response.toString());
			JSONObject jsonObject = (org.json.simple.JSONObject) obj1;*/


                return response.toString();

            } catch (IOException e) {
                // CLog.e(TAG, e.toString());

            } catch (Exception e) {
                //  CLog.e(TAG, e.toString());

            } finally {
            }
            return "";


        }


        @Override
        protected SOAPOperationData doInBackground(SOAPOperationData... args) {
            // operation object
            SOAPOperationData operationData = args[0];
            try {

                String ResponseValue = "";

                try {
                    ResponseValue = OpenHttpConnection(operationData.url, operationData.GetInputData());

                } catch (IOException e) {
                    CLog.e(TAG, e.toString());
                    operationData.setHasError(true);
                    operationData
                            .setError(SOAPOperationData.ERROR_REQUEST_ERROR);
                }



                if (ResponseValue != null) {

                    /*System.out.println("BMREQRESP" + "...........................................................");
                    System.out.println("BMREQRESP" + "                                                            ");
                    System.out.println("BMREQRESP" + operationData.GetInputData());
                    System.out.println("BMREQRESP" + "                                                            ");
                    System.out.println("BMREQRESP" + "                                                            ");
                    System.out.println("BMREQRESP" + ResponseValue);
                    System.out.println("BMREQRESP" + "                                                            ");
                    System.out.println("BMREQRESP" + "...........................................................");*/



                    System.out.println(".........." + operationData.GetInputData());

                    System.out.println(".........." + ResponseValue);

                    System.out.println("...................................................................");

                    ResponseHeader responseHeader = null;
                    ResponseData respData = null;
                    CLog.i(TAG, "soap response success");

                    org.json.JSONObject result = GetResult(ResponseValue);

                    if (result != null) {

                        Iterator<String> keys = result.keys();
                        String key = keys.next();

                        if(key.equalsIgnoreCase(RESTags.REST_RESPONSE_HEADER.getTag()))
                        {
                            responseHeader = new ResponseHeader();
                            responseHeader = GetGson().fromJson(result.getJSONObject(key).toString(), ResponseHeader.class);

                        }

                        else
                        {
                            respData = new ResponseData();
                            respData.transactionList =  new ArrayList<TransactionHistory>();
                            respData = GetGson().fromJson(result.getJSONObject(key).toString(), ResponseData.class);
                            operationData.setResponseDataObj(result.getJSONObject(key));
                        }

                        //  responseHeader = new ResponseHeader();
                        //  responseHeader = GetGson().fromJson(result.getJSONObject(key).toString(), ResponseHeader.class);

                        key = keys.next();

                        if(key.equalsIgnoreCase(RESTags.REST_RESPONSE_HEADER.getTag()))
                        {
                            responseHeader = new ResponseHeader();
                            responseHeader = GetGson().fromJson(result.getJSONObject(key).toString(), ResponseHeader.class);

                        }

                        else
                        {
                            respData = new ResponseData();
                            respData = GetGson().fromJson(result.getJSONObject(key).toString(), ResponseData.class);
                            operationData.setResponseDataObj(result.getJSONObject(key));
                        }

                        //  respData = new ResponseData();
                        //  respData = GetGson().fromJson(result.getJSONObject(key).toString(), ResponseData.class);
                        //  operationData.setResponseDataObj(result.getJSONObject(key));

                    } else {

                    }

                    operationData.setResponseData(respData);

                    //???	operationData.setResponse(response);

                    /*try {
                        SoapObject resp = new SoapObject();

                        Iterator<String> iter = operationData.getResponseDataObj().keys();

                        while (iter.hasNext()) {
                            String key = iter.next();
                            try {
                                Object value = operationData.getResponseDataObj().get(key);
                                resp.addProperty(key, value);
                            } catch (JSONException e) {
                                String s = e.getMessage();
                            }
                        }

						*//*Object value = operationData.getResponseDataObj().get("responseHeader");
						resp.addProperty("responseHeader", value);

						 value = operationData.getResponseDataObj().get(operationData.GetOutnamespace());
						resp.addProperty(operationData.GetOutnamespace(), value);*//*


                        operationData.setResponse(resp);
                    } catch (Exception e) {
                        String s = e.getMessage();
                    }*/


                    //	CLog.i("ResponseFinal:", response + "");
                    try {
                        appData.setServiceId(responseHeader.serviceId);  //abc for setting srvcid
                        operationData.setServiceId(responseHeader.serviceId);
                        operationData.setOverallStatus(responseHeader.overallStatus);
                        operationData.setUniqueId(respData.uniqueId);///new for uniid
                        operationData.setTransactionId(respData.transactionId);///new for uniid
                    } catch (Exception e) {
                        // do noting
                        CLog.w(TAG,
                                "response header parse exception : "
                                        + e.getMessage());
                    }


                    //  MerchantCoupon.content = respData.txnMessage;
                    // check txtstatus
                    if (!"200".equalsIgnoreCase(respData.txnStatus)) {
                        // log warning
                        CLog.w(TAG, "200 != txnStatus");
                        operationData.setHasError(true);
                        operationData.setError(respData.txnStatus);
                        if (respData.txnMessage == null) {
                            respData.txnMessage = "No Response";
                            operationData.setErrorMessage("No Response");
                        }
                    } else if (!"Success".equalsIgnoreCase(respData.txnMessage)) {

                        ErrorMessageCodes instance = ErrorMessageCodes
                                .createInstance();
                        String errorMessage = instance.getErrorMessage(Integer
                                .parseInt(operationData.getError()));
                        // log warning
                        CLog.w(TAG, "Success != txnMessage");
                        operationData.setHasError(true);
                        // operationData.setError(SOAPOperationData.ERROR_RESPONSE_MESSAGE);
                        operationData.setErrorMessage(errorMessage);
                    }


                } else {
                    CLog.i(TAG, "soap response empty body");
                    // error details
                    operationData.setHasError(true);
                    operationData.setError(SOAPOperationData.ERROR_NOBODYIN);
                }

            } catch (Exception e) {
                CLog.e(TAG, e.toString());
                // has error case
                operationData.setHasError(true);
                operationData
                        .setError(SOAPOperationData.ERROR_GENERAL_EXCEPTION);
                // error details
            }
            //
            if (Wallet.Settings.DISABLE_API_ERRORS)
                operationData.setHasError(false);

            // return result
            return operationData;
        }

        @Override
        protected void onPostExecute(SOAPOperationData result) {
            Message msg = handler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", result);
            msg.setData(bundle);
            handler.sendMessage(msg);
        }

        @Override
        protected void onPreExecute() {
            // do nothing for now
        }

        @Override
        protected void onProgressUpdate(String... text) {
        }

    }

    public class SOAPOperationData implements Serializable {
        //
        public static final String ERROR_NOBODYIN = "1000";
        public static final String ERROR_REQUEST_ERROR = "1003";
        public static final String ERROR_GENERAL_EXCEPTION = "1004";
        // unique id for this operation
        private final UUID id;
        // soap namespace
        private String namespace = "";
        // soap method to call
        private String method = "";
        // soap request url
        private String url = "";
        // name method action prefix

        private String outnamespace = "";


        public String GetOutnamespace() {
            return outnamespace;
        }

        public String SetOutnamespace(String name) {
            return outnamespace = name;
        }

        private static final String ACTION_PREFIX = "/";
        // error string
        private String error;
        private String errorMessage;
        // true if error exist
        private boolean hasError = false;
        // soap request
        private SoapObject request;
        // response
        private SoapObject response;


        private ResponseData responseData;
        //abc
        private org.json.JSONObject responseDataObj;
        //abc

        private Vector<SoapObject> responseVector;
        // request property
        private RootPropertyInfoAdaptor currRootPropertyObj;// = new
        // RootPropertyInfoAdaptor();
        private String currRootProperty;
        // request header
        //abc private Element rootHeaderElement;
        //
        private String serviceId;
        private String uniqueId;

        public String getTransactionId() {
            return transactionId;
        }

        public void setTransactionId(String transactionId) {
            this.transactionId = transactionId;
        }

        private String transactionId;
        private String overallStatus;
        private String inputData;

        /**
         * @param data       SOAP Data

         */
        public SOAPOperationData(SOAPDescription data) {
            this.id = UUID.randomUUID();
            this.url = data.getUrl();
            this.namespace = data.getNamespace();
            this.method = data.getMethodName();
            // init default
            init();
            // create request
            createRequest();
        }

        /**
         * @param url       SOAP url
         * @param namespace SOAP namespace
         * @param method    SOAP method
         */
        public SOAPOperationData(String url, String namespace, String method) {
            this.id = UUID.randomUUID();
            this.url = url;
            this.namespace = namespace;
            this.method = method;
            // init default
            init();
            // create request
            createRequest();
        }

        private void init() {
            //abc
			/*rootHeaderElement = new Element();
			rootHeaderElement.setNamespace(namespace);
			rootHeaderElement.setName("RequestHeader");*/
        }

        public String GetInputData() {
            return inputData;
        }

        public String SetInputData(String input) {
            return inputData = input;
        }

        private void createRequest() {
            request = new SoapObject(namespace, method);
        }

        /**
         * set root body element name
         *
         * @param parentProperty
         */
        public void setRootProperty(String parentProperty) {
            this.currRootPropertyObj = new RootPropertyInfoAdaptor();
            this.currRootProperty = parentProperty;
            //
            request.addProperty(this.currRootProperty, currRootPropertyObj);
        }

        /**
         * add body element
         *
         * @param name  element name
         * @param value element value
         */
        public void addProperty(String name, Object value) {
            addProperty(name, value, String.class);
        }

        private void addProperty(String name, Object value, Object type) {
            setElementPropertyValue(currRootPropertyObj, name, value, type);
        }

        private void addNode(String name, String value) {
            //request1 = new SoapObject(SOAPDescription.BMCustomerRegister.getNamespace(), "BMCustomerRegister");
			/*PropertyInfo requestProperty = new PropertyInfo();
			requestProperty.setName("AdData");
			requestProperty.setValue("fAdData");
			currRootPropertyObj.addProperty(requestProperty);*/
            //currRootPropertyObj.addProperty(name, value,String.class);
            request.addProperty(name, value);

        }


        // check for nested element and create its value
        private void setElementPropertyValue(RootPropertyInfoAdaptor element,
                                             String name, Object value, Object type) {
            if (null != element) {
                if (value instanceof Map<?, ?>)
                    element.addProperty(name,
                            createMapProperty((Map<?, ?>) value),
                            PropertyInfo.VECTOR_CLASS);
                else
                    // if (value instance of String)
                    element.addProperty(name, value, type);
            }
        }

        // create name value for map object
        private RootPropertyInfoAdaptor createMapProperty(Map<?, ?> map) {
            RootPropertyInfoAdaptor elem = new RootPropertyInfoAdaptor();
            for (Map.Entry<?, ?> entry : map.entrySet())
                if (entry.getKey() instanceof String)
                    setElementPropertyValue(elem, entry.getKey().toString(),
                            entry.getValue().toString(),
                            PropertyInfo.STRING_CLASS);
            return elem;
        }

        /**
         * get response obeject
         *
         * @return soap object
         */
        public SoapObject getResponse() {
            return response;
        }

        // set response object
        protected void setResponse(SoapObject response) {
            this.response = response;
        }

        public ResponseData getResponseData() {
            return responseData;
        }

        protected void setResponseData(ResponseData data) {

            this.responseData = data;
        }

        public org.json.JSONObject getResponseDataObj() {
            return responseDataObj;
        }

        protected void setResponseDataObj(org.json.JSONObject data) {

            this.responseDataObj = data;
        }

        /**
         * get error string
         *
         * @return null if no error exist
         */
        public String getError() {
            return error;
        }

        // set error
        protected void setError(String error) {
            this.error = error;
        }

        protected void setErrorMessage(String message) {
            errorMessage = message;
        }

        /**
         * return error reason
         */
        public String getErrorMessage() {
            return null != errorMessage ? errorMessage : appData
                    .getErrorMessage(getError());
        }

        /**
         * return true if there is a error
         *
         * @return
         */
        public boolean hasError() {
            return hasError;
        }

        // set has any error
        protected void setHasError(boolean hasError) {
            this.hasError = hasError;
        }

        /**
         * get unique id for this request
         *
         * @return
         */
        public UUID getId() {
            return id;
        }

        /**
         * get SOAP neamespace
         *
         * @return
         */
        public String getNamespace() {
            return namespace;
        }

        /**
         * get SOAP method
         *
         * @return
         */
        public String getMethod() {
            return method;
        }

        /**
         * get SOAP url
         *
         * @return
         */
        public String getUrl() {
            return url;
        }

        // get request object
        protected SoapObject getRequest() {
            return request;
        }

        // get header object
	/*	protected Element getHeaders() {
			return rootHeaderElement;
		}*/

        /**
         * add header element
         *
         * @param name  element name
         * @param value element value
         */
		/*public void addHeader(String name, Object value) {
			addHeader(name, value, Node.TEXT);
		}*/

        //
		/*private void addHeader(String name, Object value, int nodeType) {
			Element e = new Element().createElement(null, name);
			e.addChild(nodeType, value);
			rootHeaderElement.addChild(Node.ELEMENT, e);
		}*/

        /**
         * get resulting service id
         *
         * @return
         */
        public String getServiceId() {
            return null == serviceId ? "" : serviceId;
        }

        /**
         * @param serviceId
         */
        protected void setServiceId(String serviceId) {
            this.serviceId = serviceId;
        }

        /**
         * get resulting service id
         *
         * @return
         */
        public String getUniqueId() {
            return null == uniqueId ? "" : uniqueId;
        }

        /**
         * @param uniqueId
         */
        protected void setUniqueId(String uniqueId) {
            this.uniqueId = uniqueId;
        }

        /**
         * get overall status of response
         *
         * @return
         */
        public String getOverallStatus() {
            return overallStatus;
        }

        protected void setOverallStatus(String overallStatus) {
            this.overallStatus = overallStatus;
        }

        public Vector<SoapObject> getResponseVector() {
            return responseVector;
        }

        public void setResponseVector(
                Vector<SoapObject> responseVector) {
            this.responseVector = responseVector;
        }

    }

    // listener class for APIManager
    public interface APIMessageListener extends EventListener {
        // handle on message
        void handleMessage(SOAPOperationData data);
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ Covering method for each API calls
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //
    private final Wallet.Data appData = Wallet.Data.getInstance();
    public Card cardData;
    private String saveBenFlag = "N";
    private String transferMode = "NEFT";
    private String notificationStatus = "N";
    private String no_Of_notifications = "20";
    private String pageNum = "1";


    //////ABC

    public UUID TestCall() {
        CLog.i(TAG, "BMCustomerRegister");
        // create a soap operation data
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMCustomerRegister.getUrl(),
                SOAPDescription.BMCustomerRegister.getNamespace(),
                "BMCustomerRegister");
        // handle to invoke task
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, "serviceId", appData.getServiceId());
        AddItem(requestHeader, "serviceCode", "CUSTREG");
        AddItem(requestHeader, "phaseCode", "CUSTREG");
        AddItem(requestHeader, "subTransactionCode", "CUSTREG");
        AddItem(requestHeader, "interactionCode", "REGUSR");
        AddItem(requestHeader, "bearer", "MOBILE");

        HashMap<String, Object> registerUserInput = new HashMap<String, Object>();
        AddItem(registerUserInput, "firstName", "frsdtnm");
        AddItem(registerUserInput, "lastName", "lstdnm");
        AddItem(registerUserInput, "emailId", "teem1p@gmail.com");
        AddItem(registerUserInput, "msisdn", "91446644");
        AddItem(registerUserInput, "dateOfBirth", "06-10-1985");
        AddItem(registerUserInput, "kycStatus", "N");
        AddItem(registerUserInput, "nationalId", "5454875458");
        AddItem(registerUserInput, "idNumber", "444745");
        AddItem(registerUserInput, "gender", "male");
        AddItem(registerUserInput, "country", "India");
        AddItem(registerUserInput, "couponRefNum", "bleh576");
        Gson gson = new Gson();
        String userInput = gson.toJson(registerUserInput);
        String HeaderInput = gson.toJson(requestHeader);
        //String RetValue = "{\"requestHeader\":" + HeaderInput + ", \"registerUserInput\":" + userInput + "}";
        //String RetValue1 = FormatInputData(HeaderInput,userInput, "registerUserInput" );
        data.SetInputData(FormatInputData(HeaderInput, userInput, "registerUserInput"));

        task.execute(data);
        return data.getId();
    }

   /* public String FormatInputData(HashMap<String, Object> HeaderInput, HashMap<String, Object> BodyData, String BodyName) {
        Gson gson = new Gson();
        return "{\"requestHeader\":" + gson.toJson(HeaderInput) + ", \"" + gson.toJson(BodyData) + "\":" + BodyData + "}";
    }

    public String FormatInputData(String HeaderInput, String BodyData, String BodyName) {
        return "{\"requestHeader\":" + HeaderInput + ", \"" + BodyName + "\":" + BodyData + "}";
    }*/

    public String FormatInputDatadlt(HashMap<String, Object> HeaderInput, HashMap<String, Object> BodyData, String BodyName) {
        Gson gson = new Gson();
        return "{\"" + RESTags.REST_REQUEST_HEADER.getTag()+ "\":" +  gson.toJson(HeaderInput) + ", \"" + gson.toJson(BodyData) + "\":" + BodyData + "}";
    }

    public String FormatInputData(HashMap<String, Object> HeaderInput, HashMap<String, Object> BodyData, String BodyName) {
        Gson gson = new Gson();
        return "{\"" + RESTags.REST_REQUEST_HEADER.getTag() + "\":" + gson.toJson(HeaderInput) + ", \"" + BodyName + "\":" + gson.toJson(BodyData) + "}";
    }

    public String FormatInputData(String HeaderInput, String BodyData, String BodyName) {
        return "{\"" + RESTags.REST_REQUEST_HEADER.getTag() + "\":" + HeaderInput + ", \"" + BodyName + "\":" + BodyData + "}";
    }

    public String CreateCommand(HashMap<String, Object> BodyData) {
        Gson gson = new Gson();
        return "{\"" + "COMMAND" + "\":" + gson.toJson(BodyData) + "}";
    }

    public static String covertToXML(HashMap<String, Object> map, String root) {
        StringBuilder sb = new StringBuilder("<");
        sb.append(root);
        sb.append(">");

        for (HashMap.Entry<String, Object> e : map.entrySet()) {
            sb.append("<");
            sb.append(e.getKey());
            sb.append(">");

            sb.append((String)e.getValue());
            sb.append("</");
            sb.append(e.getKey());
            sb.append(">");
        }

        sb.append("</");
        sb.append(root);
        sb.append(">");

        return sb.toString();
    }




    public String FormatInputData(String HeaderInput, String BodyData, RESTags BodyName) {
        return FormatInputData(HeaderInput, BodyData, BodyName.getTag());
    }

    public UUID BMPerformDedupe(String emailId, String msisdn) {
        CLog.i(TAG, "BMPerformDedupe");
        // create a soap operation data
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMPerformDedupe.getUrl(),
                SOAPDescription.BMPerformDedupe.getNamespace(),
                "BMPerformDedupe");

        data.SetOutnamespace("performDedupeOutput");
        // handle to invoke task
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, "serviceId", "");
        AddItem(requestHeader, "serviceCode", "CUSTREG");
        AddItem(requestHeader, "phaseCode", "CUSTREG");
        AddItem(requestHeader, "subTransactionCode", "CUSTREG");
        AddItem(requestHeader, "interactionCode", "PDD");
        AddItem(requestHeader, "bearer", "MOBILE");
        AddItem(requestHeader, "deviceDetails", deviceDetails);

        HashMap<String, Object> registerUserInput = new HashMap<String, Object>();

        AddItem(registerUserInput, "emailId", emailId);
        AddItem(registerUserInput, "msisdn", msisdn);

        Gson gson = new Gson();
        String userInput = gson.toJson(registerUserInput);
        String HeaderInput = gson.toJson(requestHeader);

        data.SetInputData(FormatInputData(HeaderInput, userInput, "performDedupeInput"));

        task.execute(data);
        return data.getId();
    }


    public UUID BMCustomerRegister(String firstName, String lastName, String emailId, String msisdn, String dateOfBirth, String kycStatus, String nationalId, String idNumber, String gender, String country, String couponRefNum) {
        CLog.i(TAG, "BMCustomerRegister");
        // create a soap operation data
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMCustomerRegister.getUrl(),
                SOAPDescription.BMCustomerRegister.getNamespace(),
                "BMCustomerRegister");
        // handle to invoke task
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, "serviceId", "");
        AddItem(requestHeader, "serviceCode", "CUSTREG");
        AddItem(requestHeader, "phaseCode", "CUSTREG");
        AddItem(requestHeader, "subTransactionCode", "CUSTREG");
        AddItem(requestHeader, "interactionCode", "REGUSR");
        AddItem(requestHeader, "bearer", "MOBILE");

	/*	HashMap<String, Object> deviceHeader = new HashMap<String, Object>();
		AddItem(deviceHeader, "deviceOS", "ANDROID");
		AddItem(deviceHeader, "deviceOSVersion", "6.0.1");
		AddItem(deviceHeader, "deviceManufacturer", "OnePlus");
		AddItem(deviceHeader, "deviceModel", "OnePlus3");
		AddItem(deviceHeader, "imei", "2333000000000007");
		AddItem(requestHeader, "deviceDetails", deviceHeader);*/

        AddItem(requestHeader, "deviceDetails", deviceDetails);


        HashMap<String, Object> registerUserInput = new HashMap<String, Object>();
        AddItem(registerUserInput, "firstName", firstName);
        AddItem(registerUserInput, "lastName", lastName);
        AddItem(registerUserInput, "emailId", emailId);
        AddItem(registerUserInput, "msisdn", msisdn);
        AddItem(registerUserInput, "dateOfBirth", dateOfBirth);
        AddItem(registerUserInput, "kycStatus", kycStatus);
        AddItem(registerUserInput, "nationalId", nationalId);
        AddItem(registerUserInput, "idNumber", idNumber);
        AddItem(registerUserInput, "gender", gender);
        AddItem(registerUserInput, "country", country);
        AddItem(registerUserInput, "couponRefNum", couponRefNum);
        Gson gson = new Gson();
        String userInput = gson.toJson(registerUserInput);
        String HeaderInput = gson.toJson(requestHeader);

        data.SetInputData(FormatInputData(HeaderInput, userInput, "registerUserInput"));

        task.execute(data);
        return data.getId();
    }

    public UUID BMGenerateOtp(String msisdn, String emailId, String uniqueId) {
        CLog.i(TAG, "BMGenerateOtp");


        // create a soap operation data
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMGenerateOTP.getUrl(),
                SOAPDescription.BMGenerateOTP.getNamespace(),
                "BMGenerateOtp");
        // handle to invoke task
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, "serviceId", "");//abc Clearing service id     appData.getServiceId());
        AddItem(requestHeader, "serviceCode", "CUSTREG");
        AddItem(requestHeader, "phaseCode", "CUSTREG");
        AddItem(requestHeader, "subTransactionCode", "CUSTREG");
        AddItem(requestHeader, "interactionCode", "GENOTP");
        AddItem(requestHeader, "bearer", "MOBILE");
        AddItem(requestHeader, "deviceDetails", deviceDetails);

        HashMap<String, Object> registerUserInput = new HashMap<String, Object>();
        //  AddItem(registerUserInput, "emailId", emailId);
        AddItem(registerUserInput, "msisdn", msisdn);
        //  AddItem(registerUserInput, "uniqueId", uniqueId);

        Gson gson = new Gson();
        String userInput = gson.toJson(registerUserInput);
        String HeaderInput = gson.toJson(requestHeader);
        data.SetInputData(FormatInputData(HeaderInput, userInput, "generateOtpInput"));


        task.execute(data);
        return data.getId();
    }

    public UUID BMVerifyOTP(String msisdn, String emailId, String otp) {
        CLog.i(TAG, "BMVerifyOTP");


        // create a soap operation data
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMVerifyOTP.getUrl(),
                SOAPDescription.BMVerifyOTP.getNamespace(),
                "BMVerifyOTP");
        // handle to invoke task
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, "serviceId", appData.getServiceId());
        AddItem(requestHeader, "serviceCode", "CUSTREG");
        AddItem(requestHeader, "phaseCode", "CUSTREG");
        AddItem(requestHeader, "subTransactionCode", "CUSTREG");
        AddItem(requestHeader, "interactionCode", "VEROTP");
        AddItem(requestHeader, "bearer", "MOBILE");
        AddItem(requestHeader, "deviceDetails", deviceDetails);

        HashMap<String, Object> registerUserInput = new HashMap<String, Object>();
        // AddItem(registerUserInput, "emailId", emailId);
        AddItem(registerUserInput, "msisdn", msisdn);
        AddItem(registerUserInput, "otp", otp);

        Gson gson = new Gson();
        String userInput = gson.toJson(registerUserInput);
        String HeaderInput = gson.toJson(requestHeader);
        data.SetInputData(FormatInputData(HeaderInput, userInput, "verifyOtpInput"));


        task.execute(data);
        return data.getId();
    }


    public UUID BMSetPin(String uniqueId, String pin) {
        CLog.i(TAG, "BMSetPin");

        // create a soap operation data
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMSetPin.getUrl(),
                SOAPDescription.BMSetPin.getNamespace(),
                "BMSetPin");
        // handle to invoke task
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes


        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, "serviceId", appData.getServiceId());
        AddItem(requestHeader, "serviceCode", "CUSTREG");
        AddItem(requestHeader, "phaseCode", "CUSTREG");
        AddItem(requestHeader, "subTransactionCode", "CUSTREG");
        AddItem(requestHeader, "interactionCode", "SETPIN");
        AddItem(requestHeader, "bearer", "MOBILE");
        AddItem(requestHeader, "deviceDetails", deviceDetails);

        HashMap<String, Object> registerUserInput = new HashMap<String, Object>();
        AddItem(registerUserInput, "uniqueId", uniqueId);
        AddItem(registerUserInput, "pin", pin);

        Gson gson = new Gson();
        String userInput = gson.toJson(registerUserInput);
        String HeaderInput = gson.toJson(requestHeader);
        data.SetInputData(FormatInputData(HeaderInput, userInput, "setPinInput"));


        task.execute(data);
        return data.getId();
    }

    /*public UUID BMForgotPinInit(String msisdn, String securityQuestion, String securityAnswer) {
        CLog.i(TAG, "BMForgotPinInit");

        // create a soap operation data
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMForgotPinInit.getUrl(),
                SOAPDescription.BMForgotPinInit.getNamespace(),
                "BMForgotPinInit");
        // handle to invoke task
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
       // AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.LOGIN);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.LOGIN);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.LOGIN);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "AUTH");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, "msisdn", msisdn);
        AddItem(requestBody, "securityQuestion", securityQuestion);
        AddItem(requestBody, "securityAnswer", securityAnswer);

        data.SetInputData(FormatInputData(requestHeader, requestBody, "forgotPINInitInput"));

        task.execute(data);
        return data.getId();
    }*/

    public UUID BMForgotPinInit(String msisdn,String imei, String nid) {
        CLog.i(TAG, "BMForgotPinInit");

        // create a soap operation data
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMForgotPinInit.getUrl(),
                SOAPDescription.BMForgotPinInit.getNamespace(),
                "BMForgotPinInit");
        // handle to invoke task
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        // AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.LOGIN);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.LOGIN);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.LOGIN);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "AUTH");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, "msisdn", msisdn);
        AddItem(requestBody, "imei", imei);
        AddItem(requestBody, "nationalId", nid);


        data.SetInputData(FormatInputData(requestHeader, requestBody, "forgotPINInitInput"));

        task.execute(data);
        return data.getId();
    }


    /*public UUID BMForgotPIN`(String uniqueId, String msisdn, String pin){//}, String otp) {
        CLog.i(TAG, "BMForgotPINReset");

        // create a soap operation data
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMForgotPINReset.getUrl(),
                SOAPDescription.BMForgotPINReset.getNamespace(),
                "BMForgotPINReset");
        // handle to invoke task
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.CUSTREG);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.CUSTREG);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.CUSTREG);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "SETPIN");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);
        AddItem(requestBody, "msisdn", msisdn);
        AddItem(requestBody, "pin", pin);
       // AddItem(requestBody, "otp", otp);
        data.SetInputData(FormatInputData(requestHeader, requestBody, "forgotPINResetInput"));

        task.execute(data);
        return data.getId();
    }*/


    public UUID BM_ForgotPINReset(String uniqueId, String msisdn, String pin) {
        CLog.i(TAG, "BM_ForgotPINReset");

        // create a soap operation data
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMForgotPINReset.getUrl(),
                SOAPDescription.BMForgotPINReset.getNamespace(),
                "BM_ForgotPINReset");
        // handle to invoke task
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.CUSTREG);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.CUSTREG);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.CUSTREG);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "SETPIN");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);
        AddItem(requestBody, RESTags.MSISDN, msisdn);
        AddItem(requestBody, RESTags.PIN, pin);
        // AddItem(requestBody, "otp", otp);
        data.SetInputData(FormatInputData(requestHeader, requestBody, "forgotPINResetInput"));

        task.execute(data);
        return data.getId();
    }


    public UUID BMGenericValidateOtp(String msisdn, String otp) {
        CLog.i(TAG, "BMGenericValidateOtp");

        // create a soap operation data
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMGenericValidateOtp.getUrl(),
                SOAPDescription.BMGenericValidateOtp.getNamespace(),
                "BMGenericValidateOtp");
        // handle to invoke task
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "UPDUSERPROF");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, "msisdn", msisdn);
        AddItem(requestBody, "otp", otp);
        data.SetInputData(FormatInputData(requestHeader, requestBody, "verifyOtpInput"));

        task.execute(data);
        return data.getId();
    }

    public UUID BMChangePin(String uniqueId, String oldPin, String pin) {
        CLog.i(TAG, "BMChangePin");

        // create a soap operation data
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMChangePin.getUrl(),
                SOAPDescription.BMChangePin.getNamespace(),
                "BMChangePin");
        // handle to invoke task
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes


        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, "serviceId", appData.getServiceId());
        AddItem(requestHeader, "serviceCode", "MYPROF");
        AddItem(requestHeader, "phaseCode", "MYPROF");
        AddItem(requestHeader, "subTransactionCode", "MYPROF");
        AddItem(requestHeader, "interactionCode", "CHPIN");
        AddItem(requestHeader, "bearer", "MOBILE");
        AddItem(requestHeader, "deviceDetails", deviceDetails);

        HashMap<String, Object> registerUserInput = new HashMap<String, Object>();
        AddItem(registerUserInput, "uniqueId", uniqueId);
        AddItem(registerUserInput, "oldPin", oldPin);
        AddItem(registerUserInput, "newPin", pin);

        Gson gson = new Gson();
        String userInput = gson.toJson(registerUserInput);
        String HeaderInput = gson.toJson(requestHeader);
        data.SetInputData(FormatInputData(HeaderInput, userInput, "changePinInput"));


        task.execute(data);
        return data.getId();
    }

    public UUID BMLogin(String msisdn, String pin, String imei, String rnsId) {
        CLog.i(TAG, "BMLogin");

        // create a soap operation data
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMLogin.getUrl(),
                SOAPDescription.BMLogin.getNamespace(),
                "BMLogin");
        // handle to invoke task
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes


        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, "serviceId", "");//appData.getServiceId());
        AddItem(requestHeader, "serviceCode", "LOGIN");
        AddItem(requestHeader, "phaseCode", "LOGIN");
        AddItem(requestHeader, "subTransactionCode", "LOGIN");
        AddItem(requestHeader, "interactionCode", "AUTH");
        AddItem(requestHeader, "bearer", "MOBILE");
        AddItem(requestHeader, "deviceDetails", deviceDetails);

        HashMap<String, Object> registerUserInput = new HashMap<String, Object>();
        AddItem(registerUserInput, "msisdn", msisdn);
        AddItem(registerUserInput, "pin", pin);
        AddItem(registerUserInput, "imei", imei);
        AddItem(registerUserInput, "rnsId", rnsId);

        Gson gson = new Gson();
        String userInput = gson.toJson(registerUserInput);
        String HeaderInput = gson.toJson(requestHeader);
        data.SetInputData(FormatInputData(HeaderInput, userInput, "loginInput"));


        task.execute(data);
        return data.getId();
    }


    public UUID BMChangeProfilePic(String uniqueId, String image) {
        CLog.i(TAG, "BMChangeProfilePic");

        // create a soap operation data
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMChangeProfilePic.getUrl(),
                SOAPDescription.BMChangeProfilePic.getNamespace(),
                "BMChangeProfilePic");
        // handle to invoke task
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes


        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, "serviceId", appData.getServiceId());
        AddItem(requestHeader, "serviceCode", "MYPROF");
        AddItem(requestHeader, "phaseCode", "MYPROF");
        AddItem(requestHeader, "subTransactionCode", "MYPROF");
        AddItem(requestHeader, "interactionCode", "UPIMG");
        AddItem(requestHeader, "bearer", "MOBILE");
        AddItem(requestHeader, "deviceDetails", deviceDetails);

        HashMap<String, Object> registerUserInput = new HashMap<String, Object>();
        AddItem(registerUserInput, "uniqueId", uniqueId);
        AddItem(registerUserInput, "image", image);


        Gson gson = new Gson();
        String userInput = gson.toJson(registerUserInput);
        String HeaderInput = gson.toJson(requestHeader);
        data.SetInputData(FormatInputData(HeaderInput, userInput, "uploadProfilePictureInput"));


        task.execute(data);
        return data.getId();
    }

    public UUID BMDeleteProfilePic(String uniqueId) {
        CLog.i(TAG, "BMDeleteProfilePic");

        // create a soap operation data
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMDeleteProfilePic.getUrl(),
                SOAPDescription.BMDeleteProfilePic.getNamespace(),
                "BMDeleteProfilePic");
        // handle to invoke task
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes


        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, "serviceId", appData.getServiceId());
        AddItem(requestHeader, "serviceCode", "MYPROF");
        AddItem(requestHeader, "phaseCode", "MYPROF");
        AddItem(requestHeader, "subTransactionCode", "MYPROF");
        AddItem(requestHeader, "interactionCode", "DELIMG");
        AddItem(requestHeader, "bearer", "MOBILE");
        AddItem(requestHeader, "deviceDetails", deviceDetails);

        HashMap<String, Object> registerUserInput = new HashMap<String, Object>();
        AddItem(registerUserInput, "uniqueId", uniqueId);


        Gson gson = new Gson();
        String userInput = gson.toJson(registerUserInput);
        String HeaderInput = gson.toJson(requestHeader);
        data.SetInputData(FormatInputData(HeaderInput, userInput, "deleteProfilePictureInput"));


        task.execute(data);
        return data.getId();
    }


    public UUID BMChangeProfileDetails(String image, String uniqueId, String emailId, String mobileNumber, String dateOfBirth, String firstName, String lastName) {
        CLog.i(TAG, "BMChangeProfileDetails");

        // create a soap operation data
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMChangeProfileDetails.getUrl(),
                SOAPDescription.BMChangeProfileDetails.getNamespace(),
                "BMChangeProfileDetails");
        // handle to invoke task
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, "serviceId", appData.getServiceId());
        AddItem(requestHeader, "serviceCode", "MYPROF");
        AddItem(requestHeader, "phaseCode", "MYPROF");
        AddItem(requestHeader, "subTransactionCode", "MYPROF");
        AddItem(requestHeader, "interactionCode", "UPDUSERPROF");
        AddItem(requestHeader, "bearer", "MOBILE");
        AddItem(requestHeader, "deviceDetails", deviceDetails);

        HashMap<String, Object> registerUserInput = new HashMap<String, Object>();
        AddItem(registerUserInput, "image", image);
        AddItem(registerUserInput, "uniqueId", uniqueId);
        AddItem(registerUserInput, "emailId", emailId);
        AddItem(registerUserInput, "mobileNumber", mobileNumber);
        AddItem(registerUserInput, "dateOfBirth", dateOfBirth);
        AddItem(registerUserInput, "firstName", firstName);
        AddItem(registerUserInput, "lastName", lastName);


        Gson gson = new Gson();
        String userInput = gson.toJson(registerUserInput);
        String HeaderInput = gson.toJson(requestHeader);
        data.SetInputData(FormatInputData(HeaderInput, userInput, "updateCompleteProfileInput"));


        task.execute(data);
        return data.getId();
    }


///sp2

    public UUID BM_Bal_Enq(String uniqueId) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BM_Bal_Enq);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.WALT_MGMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.WALT_MGMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.WALT_MGMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "GETBAL");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> registerUserInput = new HashMap<String, Object>();
        AddItem(registerUserInput, RESTags.UNIQUE_ID, uniqueId);

        Gson gson = new Gson();
        String userInput = gson.toJson(registerUserInput);
        String HeaderInput = gson.toJson(requestHeader);
        data.SetInputData(FormatInputData(HeaderInput, userInput, RESTags.BAL_ENQ));

        task.execute(data);
        return data.getId();
    }


    public UUID BMNotification(String notificationStatus, String uniqueId, String onlyNewNotifications, String noOfNotifications, String pageNumber) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BMNotification);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "GETNOTIF");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> registerUserInput = new HashMap<String, Object>();
        AddItem(registerUserInput, RESTags.NOTIFICATION_STATUS, notificationStatus);
        AddItem(registerUserInput, RESTags.UNIQUE_ID, uniqueId);
        AddItem(registerUserInput, "onlyNewNotifications", onlyNewNotifications);
        AddItem(registerUserInput, "noOfNotifications", noOfNotifications);
        AddItem(registerUserInput, RESTags.PAGE_NUMBER, pageNumber);

        Gson gson = new Gson();
        String userInput = gson.toJson(registerUserInput);
        String HeaderInput = gson.toJson(requestHeader);
        data.SetInputData(FormatInputData(HeaderInput, userInput, RESTags.GET_All_NOTIFICATION));

        task.execute(data);
        return data.getId();
    }

    public UUID BMDeleteNotification(String uniqueId, String notificationId) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BMDeleteNotification);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "DELNOTIF");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> registerUserInput = new HashMap<String, Object>();

        AddItem(registerUserInput, RESTags.UNIQUE_ID, uniqueId);
        AddItem(registerUserInput, "notificationId", notificationId);

        Gson gson = new Gson();
        String userInput = gson.toJson(registerUserInput);
        String HeaderInput = gson.toJson(requestHeader);
        data.SetInputData(FormatInputData(HeaderInput, userInput, "deleteNotificationInput"));

        task.execute(data);
        return data.getId();
    }

    public UUID BMMarkNotificationAsRead(String uniqueId, String notificationId) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BMMarkNotificationAsRead);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "DELNOTIF");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> registerUserInput = new HashMap<String, Object>();

        AddItem(registerUserInput, RESTags.UNIQUE_ID, uniqueId);
        AddItem(registerUserInput, "notificationId", notificationId);

        Gson gson = new Gson();
        String userInput = gson.toJson(registerUserInput);
        String HeaderInput = gson.toJson(requestHeader);
        data.SetInputData(FormatInputData(HeaderInput, userInput, "deleteNotificationInput"));

        task.execute(data);
        return data.getId();
    }


   /* public UUID BM_Load_SVA(String cardNumber, String amount) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BM_Load_SVA);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.SVA_CARD_MGMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.SVA_CARD_MGMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.SVA_CARD_MGMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "LOADSVALINKEDCARD");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> registerUserInput = new HashMap<String, Object>();

        AddItem(registerUserInput, RESTags.CARD_NUMBER, cardNumber);
        AddItem(registerUserInput, RESTags.AMOUNT, amount);

        Gson gson = new Gson();
        String userInput = gson.toJson(registerUserInput);
        String HeaderInput = gson.toJson(requestHeader);
        data.SetInputData(FormatInputData(HeaderInput, userInput, "loadSVALinkedCardInput"));

        task.execute(data);
        return data.getId();
    }*/

    /*public UUID BM_LOAD_MONEY_INIT(String uniqueId, String cardNumber, String amount) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BM_LOAD_MONEY_INIT);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, "");//appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "LOADSVA");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> registerUserInput = new HashMap<String, Object>();


        AddItem(registerUserInput, RESTags.CARD_NUMBER, cardNumber);
        AddItem(registerUserInput, RESTags.AMOUNT, amount);
        AddItem(registerUserInput, RESTags.UNIQUE_ID, uniqueId);

        Gson gson = new Gson();
        String userInput = gson.toJson(registerUserInput);
        String HeaderInput = gson.toJson(requestHeader);
        data.SetInputData(FormatInputData(HeaderInput, userInput, "walletCardPaymentInput"));

        task.execute(data);
        return data.getId();
    }

    public UUID BM_LOAD_MONEY_CONFIRM(String msisdn, String amount, String otp) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BM_LOAD_MONEY_CONFIRM);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "CONFLOADSVA");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> registerUserInput = new HashMap<String, Object>();


        AddItem(registerUserInput, RESTags.MSISDN, msisdn);
        AddItem(registerUserInput, RESTags.AMOUNT, amount);
        AddItem(registerUserInput, RESTags.OTP, otp);

        Gson gson = new Gson();
        String userInput = gson.toJson(registerUserInput);
        String HeaderInput = gson.toJson(requestHeader);
        data.SetInputData(FormatInputData(HeaderInput, userInput, "loadSVAInput"));

        task.execute(data);
        return data.getId();
    }*/


    public UUID BM_LOAD_MONEY_INIT(String uniqueId, String cardNumber, String amount, String msisdn, String remarks) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BM_LOAD_MONEY_INIT);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, "");//appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "LOADSVA");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> registerUserInput = new HashMap<String, Object>();


        AddItem(registerUserInput, RESTags.PAYER_CARD_NUMBER, cardNumber);
        AddItem(registerUserInput, RESTags.AMOUNT, amount);
        AddItem(registerUserInput, RESTags.UNIQUE_ID, uniqueId);
        AddItem(registerUserInput, RESTags.TRANSFER_MODE, "DEBITCARD");
        AddItem(registerUserInput, RESTags.REMARKS, remarks);
        AddItem(registerUserInput, RESTags.PAYEE_ID, uniqueId);

      //  String transactionDataInput = ",\"transactionData\":\"{\\\"instrumentType\\\":\\\"DEBITCARD\\\",\\\"paymentInstrumentType\\\":\\\"CARD\\\"}\"}";


        String a = ",\"transactionData\":\"{\\\"instrumentType\\\":\\\"DEBITCARD\\\",\\\"paymentInstrumentType\\\":\\\"CARD\\\",\\\"amount\\\":\\\"100\\\",\\\"cardNumber\\\":\\\"6565656565655655\\\",\\\"bank\\\":\\\"Muscat\\\"}\"}";

        String b = ",\"transactionData\":\"{\\\"instrumentType\\\":\\\"DEBITCARD\\\",\\\"paymentInstrumentType\\\":\\\"CARD\\\",\\\"amount\\\":\\\"100\\\",\\\"cardNumber\\\":\\\"6565656565655655\\\",\\\"bank\\\":\\\"Muscat\\\"}\"}";


        String transactionDataInput = ",\"transactionData\":\"{\\\"instrumentType\\\":\\\"DEBITCARD\\\",\\\"paymentInstrumentType\\\":\\\"CARD\\\",\\\"amount\\\":\\\"" + amount +"\\\",\\\"cardNumber\\\":\\\"" + cardNumber + "\\\",\\\"bank\\\":\\\"Muscat\\\"}\"}";


        //---- \"CARD\",\"amount\":\"100\",\"cardNumber\":\"6565656565655655\",\"bank\":\"Muscat\"}"

        // AddItem(registerUserInput, "transactionData", "{\"instrumentType\":\"DEBITCARD\",\"paymentInstrumentType\":\"CARD\"}");

        // AddItem(registerUserInput, "transactionData", "{\\\"instrumentType\\\":\\\"DEBITCARD\\\",\\\"paymentInstrumentType\\\":\\\"CARD\\\"}");

        AddItem(registerUserInput, RESTags.PAYER_ID, uniqueId);
        AddItem(registerUserInput, RESTags.TRANS_TYPE, "DP");
        AddItem(registerUserInput, RESTags.INSTRUMENT_TYPE, "DEBIT");
        AddItem(registerUserInput, RESTags.MSISDN, msisdn);

        Gson gson = new Gson();
        String userInput = gson.toJson(registerUserInput);

        userInput =userInput.substring(0,userInput.length()-1);

        userInput =userInput + transactionDataInput;

        String HeaderInput = gson.toJson(requestHeader);
        data.SetInputData(FormatInputData(HeaderInput, userInput, "loadSVAWithBMCardInput"));

        task.execute(data);
        return data.getId();
    }

    public UUID BM_LOAD_MONEY_INIT1(String uniqueId, String paymentInstrumentId, String amount, String msisdn, String remarks) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BM_LOAD_MONEY_INIT);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, "");//appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "LOADSVA");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> registerUserInput = new HashMap<String, Object>();



        AddItem(registerUserInput, RESTags.UNIQUE_ID, uniqueId);
        AddItem(registerUserInput, RESTags.PAYMENT_INSTRUMENT_ID, paymentInstrumentId);

        AddItem(registerUserInput, RESTags.AMOUNT, amount);
        AddItem(registerUserInput, RESTags.TRANSFER_MODE, "DEBITCARD");
        AddItem(registerUserInput, RESTags.REMARKS, remarks);
        AddItem(registerUserInput, RESTags.PAYEE_ID, uniqueId);
       // String transactionDataInput = ",\"transactionData\":\"{\\\"instrumentType\\\":\\\"DEBITCARD\\\",\\\"paymentInstrumentType\\\":\\\"CARD\\\"}\"}";

        // AddItem(registerUserInput, "transactionData", "{\"instrumentType\":\"DEBITCARD\",\"paymentInstrumentType\":\"CARD\"}");

        // AddItem(registerUserInput, "transactionData", "{\\\"instrumentType\\\":\\\"DEBITCARD\\\",\\\"paymentInstrumentType\\\":\\\"CARD\\\"}");

        String transactionDataInput = ",\"transactionData\":\"{\\\"instrumentType\\\":\\\"DEBITCARD\\\",\\\"paymentInstrumentType\\\":\\\"CARD\\\",\\\"amount\\\":\\\"" + amount +"\\\",\\\"paymentInstrumentId\\\":\\\"" + paymentInstrumentId + "\\\",\\\"bank\\\":\\\"Muscat\\\"}\"}";


        AddItem(registerUserInput, RESTags.PAYER_ID, uniqueId);
        AddItem(registerUserInput, RESTags.TRANS_TYPE, "DP");
        AddItem(registerUserInput, RESTags.INSTRUMENT_TYPE, "DEBIT");
        AddItem(registerUserInput, RESTags.MSISDN, msisdn);

        Gson gson = new Gson();
        String userInput = gson.toJson(registerUserInput);

        userInput =userInput.substring(0,userInput.length()-1);

        userInput =userInput + transactionDataInput;

        String HeaderInput = gson.toJson(requestHeader);
        data.SetInputData(FormatInputData(HeaderInput, userInput, "loadSVAWithBMCardInput"));

        task.execute(data);
        return data.getId();
    }

    public UUID BM_LOAD_MONEY_CONFIRM(String transactionId, String msisdn, String amount, String otp) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BM_LOAD_MONEY_CONFIRM);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "CONFLOADSVA");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> registerUserInput = new HashMap<String, Object>();

        AddItem(registerUserInput, RESTags.TRANSACTION_ID, transactionId);
        AddItem(registerUserInput, RESTags.MSISDN, msisdn);
        AddItem(registerUserInput, RESTags.AMOUNT, amount);
        AddItem(registerUserInput, RESTags.OTP, otp);

        Gson gson = new Gson();
        String userInput = gson.toJson(registerUserInput);
        String HeaderInput = gson.toJson(requestHeader);
        data.SetInputData(FormatInputData(HeaderInput, userInput, "loadSVAInput"));

        task.execute(data);
        return data.getId();
    }

    public UUID BM_Add_Card(String uniqueId, String cardHolderName, String cardNumber, String expiryMonth, String expiryYear, String cardType, String instrumentType, String bankName) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BM_Add_Card);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.WALT_MGMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.WALT_MGMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.WALT_MGMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "ADDCARD");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> registerUserInput = new HashMap<String, Object>();

        AddItem(registerUserInput, RESTags.UNIQUE_ID, uniqueId);
        AddItem(registerUserInput, RESTags.NAME_ON_CARD, cardHolderName);
        AddItem(registerUserInput, RESTags.CARD_NUMBER, cardNumber);
        AddItem(registerUserInput, RESTags.EXPIRY_MONTH, expiryMonth);
        AddItem(registerUserInput, RESTags.EXPIRY_YEAR, expiryYear);
        // AddItem(registerUserInput, RESTags.CARD_TYPE, cardType);
        //  AddItem(registerUserInput, RESTags.INSTRUMENT_TYPE, instrumentType);
        // AddItem(registerUserInput, RESTags.BANK_NAME, bankName);


        // addCardInput":{"expiryYear":"2020","cardHolderName":"tempuserc","expiryMonth":"12","cardNumber":"4539406368521515","uniqueId":"PT161023.1614.000333"}}

        Gson gson = new Gson();
        String userInput = gson.toJson(registerUserInput);
        String HeaderInput = gson.toJson(requestHeader);
        data.SetInputData(FormatInputData(HeaderInput, userInput, "addCardInput"));

        task.execute(data);
        return data.getId();
    }


    public UUID BMGetStoredCards(String uniqueId) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BMGetStoredCards);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.WALT_MGMT);
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.WALT_MGMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.WALT_MGMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.WALT_MGMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "GETCARDS");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> registerUserInput = new HashMap<String, Object>();

        AddItem(registerUserInput, RESTags.UNIQUE_ID, uniqueId);


        Gson gson = new Gson();
        String userInput = gson.toJson(registerUserInput);
        String HeaderInput = gson.toJson(requestHeader);
        data.SetInputData(FormatInputData(HeaderInput, userInput, "getStoredCardsInput"));

        task.execute(data);
        return data.getId();
    }

    public UUID BMDeleteCard(String uniqueId, String paymentInstrumentId, String pin) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BMDeleteCard);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.WALT_MGMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.WALT_MGMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.WALT_MGMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "DELCARD");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);
        AddItem(requestBody, RESTags.PAYMENT_INSTRUMENT_ID, paymentInstrumentId);
        AddItem(requestBody, RESTags.PIN, pin);

        data.SetInputData(FormatInputData(requestHeader, requestBody, "deleteCardInput"));

        task.execute(data);
        return data.getId();
    }

    public UUID BMSendReminder(String uniqueId, String requestType, String requestId, String message) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BMSendReminder);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "SENDREM");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);
        AddItem(requestBody, RESTags.REQUEST_TYPE, requestType);
        AddItem(requestBody, RESTags.REQUEST_id, requestId);
        AddItem(requestBody, RESTags.MESSAGE, message);

        data.SetInputData(FormatInputData(requestHeader, requestBody, "sendReminderInput"));

        task.execute(data);
        return data.getId();
    }


    public UUID BMGetFavouriteTxn(String uniqueId, String transactionType) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BMGetFavouriteTxn);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "GETFAV");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);
        AddItem(requestBody, RESTags.TRANSACTION_TYPE, transactionType);

        data.SetInputData(FormatInputData(requestHeader, requestBody, "getFavouriteTxnInput"));

        task.execute(data);
        return data.getId();
    }


    public UUID BMAddFavouriteTxn(String uniqueId, String transactionId) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BMAddFavouriteTxn);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "ADDFAVTXN");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);
        AddItem(requestBody, RESTags.TRANSACTION_ID, transactionId);

        data.SetInputData(FormatInputData(requestHeader, requestBody, "addFavouriteTxnInput"));

        task.execute(data);
        return data.getId();
    }

    public UUID BMDeleteFavouriteTxn(String uniqueId, String favTransactionId) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BMDeleteFavouriteTxn);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "DELFAVTXN");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);
        AddItem(requestBody, "favTransactionId", favTransactionId);

        data.SetInputData(FormatInputData(requestHeader, requestBody, "deleteFavouriteTxnInput"));

        task.execute(data);
        return data.getId();
    }

    public UUID BMGetTxnList(String uniqueId, String[] transactionType, String sortBy, String[] transactionStatusList, String numberOfTransactions, String pageNumber, String checkAs, String startDate, String endDate) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BMGetTxnList);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.TXN_HIS);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.TXN_HIS);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.TXN_HIS);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "GETTXNLIST");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);
        AddItem(requestBody, RESTags.TRANSACTION_TYPE, transactionType);
        AddItem(requestBody, RESTags.SORT, sortBy);
        AddItem(requestBody, RESTags.TRANS_LIST, transactionStatusList);
        AddItem(requestBody, RESTags.NUM_OF_TRANSACTION, numberOfTransactions);
        AddItem(requestBody, RESTags.PAGE_NUM, pageNumber);
        AddItem(requestBody, RESTags.CHECH_AS, checkAs);

        AddItem(requestBody, RESTags.START_DATE, startDate);
        AddItem(requestBody, RESTags.END_DATE, endDate);

        data.SetInputData(FormatInputData(requestHeader, requestBody, "getTransactionListInput"));

        task.execute(data);
        return data.getId();
    }

    public UUID BMRequestMoney(String uniqueId, String payerName, String payerMobileNumber, String amount, String remarks, String pin) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BMRequestMoney);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "REQMON");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);
        AddItem(requestBody, RESTags.PAYER_NAME, payerName);
        AddItem(requestBody, RESTags.PAYER_MOBILE, payerMobileNumber);
        AddItem(requestBody, RESTags.AMOUNT, amount);
        AddItem(requestBody, RESTags.REMARKS, remarks);
       /* AddItem(requestBody, RESTags.PIN, pin);*/
        System.out.println("Request: "+requestBody.toString());
        data.SetInputData(FormatInputData(requestHeader, requestBody, "requestMoneyInput"));

        task.execute(data);
        return data.getId();
    }

    public UUID BMAcceptRequestMoney(String uniqueId, String requestMoneyId, String amount, String remarks, String pin) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BMAcceptRequestMoney);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "ACPTREQMON");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);
        AddItem(requestBody, RESTags.REQUEST_MONEY_id, requestMoneyId);
        AddItem(requestBody, RESTags.AMOUNT, amount);
        AddItem(requestBody, RESTags.REMARKS, remarks);
        AddItem(requestBody, RESTags.PIN, pin);

        data.SetInputData(FormatInputData(requestHeader, requestBody, "acceptRequestMoneyInput"));

        task.execute(data);
        return data.getId();
    }


    public UUID BMRejectRequestMoney(String uniqueId, String requestMoneyId, String remarks) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BMRejectRequestMoney);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "REJREQMON");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);
        AddItem(requestBody, RESTags.REQUEST_MONEY_id, requestMoneyId);
        AddItem(requestBody, RESTags.REMARKS, remarks);


        data.SetInputData(FormatInputData(requestHeader, requestBody, "rejectRequestMoneyInput"));

        task.execute(data);
        return data.getId();
    }

    public UUID BM_DeleteRequestMoney(String uniqueId, String requestMoneyId) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BM_DeleteRequestMoney);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "DELREJMON");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);
        AddItem(requestBody, RESTags.REQUEST_MONEY_id, requestMoneyId);

        data.SetInputData(FormatInputData(requestHeader, requestBody, "deleteRequestMoneyInput"));

        task.execute(data);
        return data.getId();
    }

    ///pending txn calls

    public UUID BMGetAllChipInList(String uniqueId, String transactionStatusList) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BMGetAllChipInList);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        // AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "GETALLCHIP");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);
        AddItem(requestBody, RESTags.PAGE_NUM, "1");
        AddItem(requestBody, RESTags.NUM_OF_TRANSACTION, "10");
        AddItem(requestBody, RESTags.SORT, "DESC");
        AddItem(requestBody, "requiredStatusList", new String[]{"PENDING"});

        data.SetInputData(FormatInputData(requestHeader, requestBody, "getAllChipInListInput"));

        task.execute(data);
        return data.getId();
    }

    public UUID BMPendingRequest(String uniqueId) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BMPendingRequest);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        // AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "PENDINGREQ");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);

        data.SetInputData(FormatInputData(requestHeader, requestBody, "pendingRequestInput"));

        task.execute(data);
        return data.getId();
    }

    public UUID BMViewPayeeRequestedMoney(String uniqueId, String transactionStatusList) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BMViewPayeeRequestedMoney);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        // AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "VIEWPAYERREQMON");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);
        AddItem(requestBody, RESTags.PAGE_NUM, "1");
        AddItem(requestBody, RESTags.SORT, "DESC");
        //AddItem(requestBody, RESTags.SORT, "ASC");
        AddItem(requestBody, RESTags.NUM_OF_TRANSACTION, "10");
        AddItem(requestBody, "requiredStatusList", new String[]{"PENDING"});

        data.SetInputData(FormatInputData(requestHeader, requestBody, "viewPayeeRequestedMoneyInput"));

        task.execute(data);
        return data.getId();
    }

    ///pending txn calls

   /* public UUID BM_Unload_SVA(String uniqueId, String amount, String remarks, String transferMode, String customSmsMobileNumber, String smsText, String saveBeneficiaryFlag, Bank beneficiaryDetails) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BM_Unload_SVA);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "ADDTXNOTR");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);
        AddItem(requestBody, RESTags.AMOUNT, amount);
        AddItem(requestBody, RESTags.REMARKS, remarks);
        AddItem(requestBody, RESTags.TRANSFER_MODE, transferMode);
        AddItem(requestBody, "customSmsMobileNumber", customSmsMobileNumber);
        AddItem(requestBody, "smsText", smsText);
        AddItem(requestBody, RESTags.SAVE_BENEFICIARY_FLAG, saveBeneficiaryFlag);

        HashMap<String, Object> beneficiaryData = new HashMap<String, Object>();
        AddItem(beneficiaryData, "beneficiaryId", beneficiaryDetails.getUniqueAccountId());
        AddItem(beneficiaryData, "bankName", beneficiaryDetails.getBankName());
        AddItem(beneficiaryData, "ifsc", beneficiaryDetails.getIfsc());
        AddItem(beneficiaryData, "accountHolderName", beneficiaryDetails.getUserName());
        AddItem(beneficiaryData, "accountNumber", beneficiaryDetails.getAccountNumber());
        AddItem(beneficiaryData, "nickName", beneficiaryDetails.getNickName());
        AddItem(beneficiaryData, "branchCode", beneficiaryDetails.branchCode);////////
        AddItem(beneficiaryData, "mmid", beneficiaryDetails.mmid);////////
        AddItem(beneficiaryData, "uid", beneficiaryDetails.uid);////////
        AddItem(beneficiaryData, "accountType", beneficiaryDetails.getAccountType());
        AddItem(beneficiaryData, "payeeMobileNumber", beneficiaryDetails.payeeMobileNumber);///////

        AddItem(requestBody, "beneficiaryDetails", beneficiaryData);

        //please check out data

        data.SetInputData(FormatInputData(requestHeader, requestBody, "transferToOtherBankAccountInput"));

        task.execute(data);
        return data.getId();
    }*/



    /*public UUID BM_Unload_SVA(String uniqueId, Transaction transaction) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BM_Unload_SVA);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "ADDTXNOTR");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);
        AddItem(requestBody, RESTags.AMOUNT, transaction.amount);
        AddItem(requestBody, RESTags.REMARKS, transaction.description);
        AddItem(requestBody, RESTags.TRANSFER_MODE, transferMode);
        AddItem(requestBody, "customSmsMobileNumber", transaction.getPayeeMobileNumber());
        AddItem(requestBody, "smsText", "sms message");
        AddItem(requestBody, RESTags.SAVE_BENEFICIARY_FLAG, "Y");

        HashMap<String, Object> beneficiaryData = new HashMap<String, Object>();
        AddItem(beneficiaryData, "beneficiaryId", transaction.getUniqueAccountId());
        AddItem(beneficiaryData, "bankName", transaction.getBankName());
        AddItem(beneficiaryData, "ifsc", transaction.getIfsc());
        AddItem(beneficiaryData, "accountHolderName", transaction.getUserName());
        AddItem(beneficiaryData, "accountNumber", transaction.getAccountNumber());
        AddItem(beneficiaryData, "nickName", transaction.getNickName());
        AddItem(beneficiaryData, "branchCode", transaction.branchCode);////////
        AddItem(beneficiaryData, "mmid", transaction.mmid);////////
        AddItem(beneficiaryData, "uid", transaction.uid);////////
        AddItem(beneficiaryData, "accountType", transaction.getAccountType());
        AddItem(beneficiaryData, "payeeMobileNumber", transaction.payeeMobileNumber);///////

        AddItem(requestBody, "beneficiaryDetails", beneficiaryData);

        //please check out data

        data.SetInputData(FormatInputData(requestHeader, requestBody, "transferToOtherBankAccountInput"));

        task.execute(data);
        return data.getId();
    }*/


	/*public UUID BM_Unload_SVA(String uniqueId, String amount,String remarks, String transferMode,String customSmsMobileNumber, String smsText,String saveBeneficiaryFlag, BeneficiaryDetails beneficiaryDetails) {

		SOAPOperationData data = new SOAPOperationData(SOAPDescription.BM_Unload_SVA);
		SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
		this.taskTable.put(data.getId(), task);
		// set default header nodes

		HashMap<String, Object> requestHeader = new HashMap<String, Object>();
		AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
		AddItem(requestHeader, RESTags.SERVICECODE, RESTags.PYMT);
		AddItem(requestHeader, RESTags.PHASECODE, RESTags.PYMT);
		AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.PYMT);
		AddItem(requestHeader, RESTags.INTERACTIONCODE, "ADDTXNOTR");
		AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
		AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

		HashMap<String, Object> requestBody = new HashMap<String, Object>();

		AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);
		AddItem(requestBody, RESTags.AMOUNT, amount);
		AddItem(requestBody, RESTags.REMARKS, remarks);
		AddItem(requestBody, RESTags.TRANSFER_MODE, transferMode);
		AddItem(requestBody, "customSmsMobileNumber", customSmsMobileNumber);
		AddItem(requestBody, "smsText", smsText);
		AddItem(requestBody, RESTags.SAVE_BENEFICIARY_FLAG, saveBeneficiaryFlag);

		HashMap<String, Object> beneficiaryData = new HashMap<String, Object>();
		AddItem(beneficiaryData, "beneficiaryId", beneficiaryDetails.beneficiaryId);
		AddItem(beneficiaryData, "bankName", beneficiaryDetails.bankName);
		AddItem(beneficiaryData, "ifsc", beneficiaryDetails.ifsc);
		AddItem(beneficiaryData, "accountHolderName", beneficiaryDetails.accountHolderName);
		AddItem(beneficiaryData, "accountNumber", beneficiaryDetails.accountNumber);
		AddItem(beneficiaryData, "nickName", beneficiaryDetails.nickName);
		AddItem(beneficiaryData, "branchCode", beneficiaryDetails.branchCode);
		AddItem(beneficiaryData, "mmid", beneficiaryDetails.mmid);
		AddItem(beneficiaryData, "uid", beneficiaryDetails.uid);
		AddItem(beneficiaryData, "accountType", beneficiaryDetails.accountType);
		AddItem(beneficiaryData, "payeeMobileNumber", beneficiaryDetails.payeeMobileNumber);

		AddItem(requestBody, "beneficiaryDetails", beneficiaryData);

		//please check out data

		data.SetInputData(FormatInputData(requestHeader,requestBody, "transferToOtherBankAccountInput" ));

		task.execute(data);
		return data.getId();
	}*/



    public UUID BM_Unload_SVA(String uniqueId, Transaction transaction) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BM_Unload_SVA);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        //  AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "ADDTXNOTR");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);
        AddItem(requestBody, RESTags.AMOUNT, transaction.amount);
        AddItem(requestBody, RESTags.REMARKS, transaction.description);
        AddItem(requestBody, RESTags.TRANSFER_MODE, transferMode);
        AddItem(requestBody, "customSmsMobileNumber", transaction.getPayeeMobileNumber());
        AddItem(requestBody, "smsText", "sms message");
        AddItem(requestBody, RESTags.SAVE_BENEFICIARY_FLAG, "Y");

        HashMap<String, Object> beneficiaryData = new HashMap<String, Object>();
        AddItem(beneficiaryData, "beneficiaryId", transaction.getBeneficiaryId());
        AddItem(beneficiaryData, "bankName", transaction.getBankName());
        AddItem(beneficiaryData, "ifsc", transaction.getIfsc());
        AddItem(beneficiaryData, "accountHolderName", transaction.getUserName());
        AddItem(beneficiaryData, "accountNumber", transaction.getAccountNumber());
        AddItem(beneficiaryData, "nickName", transaction.getNickName());
        AddItem(beneficiaryData, "branchCode", transaction.branchCode);////////
        AddItem(beneficiaryData, "mmid", transaction.mmid);////////
        AddItem(beneficiaryData, "uid", transaction.uid);////////
        AddItem(beneficiaryData, "accountType", transaction.getAccountType());
        AddItem(beneficiaryData, "payeeMobileNumber", transaction.payeeMobileNumber);///////

        AddItem(requestBody, "beneficiaryDetails", beneficiaryData);

        //please check out data

        data.SetInputData(FormatInputData(requestHeader, requestBody, "transferToOtherBankAccountInput"));

        task.execute(data);
        return data.getId();
    }

    public UUID BM_Unload_SVA_Saved(String uniqueId, Transaction transaction) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BM_Unload_SVA);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        //  AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "ADDTXNOTR");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);
        AddItem(requestBody, RESTags.AMOUNT, transaction.amount);
        AddItem(requestBody, RESTags.REMARKS, transaction.description);
        AddItem(requestBody, "uniqueAccId", transaction.getUniqueAccountId());
        AddItem(requestBody, RESTags.TRANSFER_MODE, transferMode);


        data.SetInputData(FormatInputData(requestHeader, requestBody, "transferToOtherBankAccountInput"));

        task.execute(data);
        return data.getId();
    }


    private class BeneficiarxyDetails {
        String beneficiaryId;
        String bankName;
        String ifsc;
        String accountHolderName;
        String accountNumber;
        String nickName;
        String branchName;
        String branchCode;
        String branchCity;
        String mmid;
        String uid;
        String accountType;
        String payeeMobileNumber;

    }


   /* public UUID BM_SendMoney_Registered(String uniqueId, String firstName, String lastName, String msisdn, String emailId, String amount, String pin, String remarks, String remarks1, String isChargesApplicable) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BM_SendMoney_Registered);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "P2P");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);
        AddItem(requestBody, "firstName", firstName);
        AddItem(requestBody, "lastName", lastName);
        AddItem(requestBody, RESTags.MSISDN, msisdn);
        AddItem(requestBody, RESTags.EMAIL, emailId);
        AddItem(requestBody, RESTags.AMOUNT, amount);
        AddItem(requestBody, RESTags.PIN, pin);
        AddItem(requestBody, RESTags.REMARKS, remarks);
        AddItem(requestBody, "remarks1", remarks1);
        AddItem(requestBody, RESTags.CHARGES, isChargesApplicable);

        data.SetInputData(FormatInputData(requestHeader, requestBody, "p2PSendMoneyInput"));

        task.execute(data);
        return data.getId();
    }*/


    public UUID BM_SendMoney_Registered(String uniqueId, Transaction transaction) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BM_SendMoney_Registered);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "P2P");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);
        AddItem(requestBody, "firstName", transaction.getUserName());
        AddItem(requestBody, "lastName", transaction.getUserName());
        AddItem(requestBody, RESTags.MSISDN, transaction.getPayeeMobileNumber());
        AddItem(requestBody, RESTags.EMAIL, transaction.getEmail());
        AddItem(requestBody, RESTags.AMOUNT, transaction.getAmount());
        AddItem(requestBody, RESTags.PIN, transaction.getPin());
        AddItem(requestBody, RESTags.REMARKS, transaction.getDescription());
        AddItem(requestBody, "remarks1", transaction.getDescription());
        AddItem(requestBody, RESTags.CHARGES, transaction.getIsChargeApplicable());

        data.SetInputData(FormatInputData(requestHeader, requestBody, "p2PSendMoneyInput"));

        task.execute(data);
        return data.getId();
    }

    public UUID BM_SendMoney_Registered1(String uniqueId, Transaction transaction) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BM_SendMoney_Registered);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "P2P");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);
        AddItem(requestBody, "firstName", transaction.getUserName());
        AddItem(requestBody, "lastName", transaction.getUserName());
        AddItem(requestBody, RESTags.MSISDN, transaction.getPayeeMobileNumber());
        //AddItem(requestBody, RESTags.EMAIL, transaction.getEmail());
        AddItem(requestBody, RESTags.AMOUNT, transaction.getAmount());
        AddItem(requestBody, RESTags.PIN, transaction.getPin());
        AddItem(requestBody, RESTags.REMARKS, transaction.getDescription());
        AddItem(requestBody, "remarks1", transaction.getDescription());
        AddItem(requestBody, RESTags.CHARGES, transaction.getIsChargeApplicable());

        data.SetInputData(FormatInputData(requestHeader, requestBody, "p2PSendMoneyInput"));

        task.execute(data);
        return data.getId();
    }


    public UUID BM_SendMoney_Registered2(String uniqueId, Transaction transaction) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BM_SendMoney_Registered);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "P2P");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);
        AddItem(requestBody, "firstName", transaction.getUserName());
        AddItem(requestBody, "lastName", transaction.getUserName());
        // AddItem(requestBody, RESTags.MSISDN, transaction.getPayeeMobileNumber());
        AddItem(requestBody, RESTags.EMAIL, transaction.getEmail());
        AddItem(requestBody, RESTags.AMOUNT, transaction.getAmount());
        AddItem(requestBody, RESTags.PIN, transaction.getPin());
        AddItem(requestBody, RESTags.REMARKS, transaction.getDescription());
        AddItem(requestBody, "remarks1", transaction.getDescription());
        AddItem(requestBody, RESTags.CHARGES, transaction.getIsChargeApplicable());

        data.SetInputData(FormatInputData(requestHeader, requestBody, "p2PSendMoneyInput"));

        task.execute(data);
        return data.getId();
    }




	/*public UUID BM_Add_Bank_Account(String uniqueId, BeneficiaryDetails beneficiaryDetails, String isDefault) {

		SOAPOperationData data = new SOAPOperationData(SOAPDescription.BM_Add_Bank_Account);
		SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
		this.taskTable.put(data.getId(), task);
		// set default header nodes

		HashMap<String, Object> requestHeader = new HashMap<String, Object>();
		AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
		AddItem(requestHeader, RESTags.SERVICECODE, RESTags.WALT_MGMT);
		AddItem(requestHeader, RESTags.PHASECODE, RESTags.WALT_MGMT);
		AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.WALT_MGMT);
		AddItem(requestHeader, RESTags.INTERACTIONCODE, "ADDBNKACC");
		AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
		AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

		HashMap<String, Object> requestBody = new HashMap<String, Object>();

		AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);
		AddItem(requestBody, RESTags.ACCOUNT_HOLDER_NAME, beneficiaryDetails.accountHolderName);
		AddItem(requestBody,RESTags.BANK_NAME, beneficiaryDetails.bankName);
		AddItem(requestBody, RESTags.ACCOUNT_NUMBER, beneficiaryDetails.accountNumber);
		AddItem(requestBody, RESTags.IFSC, beneficiaryDetails.ifsc);
		AddItem(requestBody, "nickName", beneficiaryDetails.nickName);
		AddItem(requestBody, "mmid", beneficiaryDetails.mmid);
		AddItem(requestBody, "uid", beneficiaryDetails.uid);
		AddItem(requestBody, "accountType", beneficiaryDetails.accountType);
		AddItem(requestBody, "branchName", beneficiaryDetails.branchName);
		AddItem(requestBody, "branchCode", beneficiaryDetails.branchCode);
		AddItem(requestBody, "branchCity", beneficiaryDetails.branchCity);
		AddItem(requestBody, "payeeMobileNumber", beneficiaryDetails.payeeMobileNumber);
		AddItem(requestBody, "isDefault", isDefault);

		data.SetInputData(FormatInputData(requestHeader,requestBody, "addBankAccountInput" ));

		task.execute(data);
		return data.getId();
	}*/


    public UUID BM_Add_Bank_Account(String uniqueId, Transaction transactionData) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BM_Add_Bank_Account);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        // AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.WALT_MGMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.WALT_MGMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.WALT_MGMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "ADDBNKACC");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);
        AddItem(requestBody, RESTags.ACCOUNT_HOLDER_NAME, transactionData.getUserName());
        AddItem(requestBody, RESTags.BANK_NAME, transactionData.getBankName());
        AddItem(requestBody, RESTags.ACCOUNT_NUMBER, transactionData.getAccountNumber());
        AddItem(requestBody, RESTags.IFSC, transactionData.getIfsc());
        AddItem(requestBody, "nickName", transactionData.getNickName());
        AddItem(requestBody, "mmid", transactionData.mmid);
        AddItem(requestBody, "uid", transactionData.uid);
        AddItem(requestBody, "accountType", transactionData.getAccountType());
        AddItem(requestBody, "branchName", transactionData.branchName);
        AddItem(requestBody, "branchCode", transactionData.branchCode);
        AddItem(requestBody, "branchCity", transactionData.branchCity);
        AddItem(requestBody, "payeeMobileNumber", transactionData.payeeMobileNumber);
        AddItem(requestBody, "isDefault", transactionData.isDefault()?"Y":"N");

        data.SetInputData(FormatInputData(requestHeader, requestBody, "addBankAccountInput"));

        task.execute(data);
        return data.getId();
    }

    public UUID BMGetBankAccount(String uniqueId) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.GetBankAccount);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.WALT_MGMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.WALT_MGMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.WALT_MGMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "GETBNKACCS");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);


        data.SetInputData(FormatInputData(requestHeader, requestBody, "getBankAccountsInput"));

        task.execute(data);
        return data.getId();
    }

    public UUID BM_Delete_Bank_accounts(String uniqueId, String uniqueAccId, String pin) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BM_Delete_Bank_accounts);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.WALT_MGMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.WALT_MGMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.WALT_MGMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "DELBNKACC");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);
        AddItem(requestBody, "uniqueAccId", uniqueAccId);
        AddItem(requestBody, RESTags.PIN, pin);

        data.SetInputData(FormatInputData(requestHeader, requestBody, "deleteBankAccountInput"));

        task.execute(data);
        return data.getId();
    }


    public UUID BMGetAllSecurityQuestions() {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BMGetAllSecurityQuestions);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        // AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "GETALLSECURITYQUESTION");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        //AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);


        data.SetInputData(FormatInputData(requestHeader, requestBody, "getAllSecurityQuestionsInput"));

        task.execute(data);
        return data.getId();
    }

    public UUID BMAddSecurityAnswer(String uniqueId,String questionID,String answer) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BMAddSecurityAnswer);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "ADDSECURITYANSWER");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);
        AddItem(requestBody, "securityQuestionId", questionID);
        AddItem(requestBody, "securityAnswer", answer);

        data.SetInputData(FormatInputData(requestHeader, requestBody, "addSecurityAnswerInput"));

        task.execute(data);
        return data.getId();
    }





    public UUID BMValidateSecurityAnswer(String uniqueId,String questionID,String answer) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.BMValidateSecurityAnswer);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "VALSECURITYANS");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);
        AddItem(requestBody, "securityQuestionId", questionID);
        AddItem(requestBody, "securityAnswer", answer);

        data.SetInputData(FormatInputData(requestHeader, requestBody, "validateSecurityAnswerInput"));

        task.execute(data);
        return data.getId();
    }


    public UUID BMChangeDevice(String uniqueId,String msisdn) {

        SOAPOperationData data = new SOAPOperationData(SOAPDescription.ChangeDevice);
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.LOGIN);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.LOGIN);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.LOGIN);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "CHANGEDEVICE");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);
        AddItem(requestBody, "msisdn", msisdn);
        AddItem(requestBody, "imei", deviceDetails.get("imei"));

        data.SetInputData(FormatInputData(requestHeader, requestBody, "switchDeviceInput"));

        task.execute(data);
        return data.getId();
    }


    public UUID BMVerifyEmail(String uniqueId) {
        CLog.i(TAG, "BMVerifyEmail");

        // create a soap operation data
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMVerifyEmail.getUrl(),
                SOAPDescription.BMVerifyEmail.getNamespace(),
                "BMVerifyEmail");
        // handle to invoke task
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        // AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "VEREMAIL");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);

        data.SetInputData(FormatInputData(requestHeader, requestBody, "verifyEmailInput"));

        task.execute(data);
        return data.getId();
    }

    public UUID BMUpdateProfileDedupe(String uniqueId, String msisdn, String pin, String emailid) {
        CLog.i(TAG, "BMUpdateProfileDedupe");

        // create a soap operation data
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMUpdateProfileDedupe.getUrl(),
                SOAPDescription.BMUpdateProfileDedupe.getNamespace(),
                "BMUpdateProfileDedupe");
        // handle to invoke task
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        // AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "UPDUSERPROF");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);
        AddItem(requestBody, "emailId", emailid);
        AddItem(requestBody, "msisdn", msisdn);
        AddItem(requestBody, "pin", pin);


        data.SetInputData(FormatInputData(requestHeader, requestBody, "updateProfileDedupeInput"));

        task.execute(data);
        return data.getId();
    }


    public UUID BMChangeMSISDN(String uniqueId, String msisdn, String otp, String mobileNumber ) {
        CLog.i(TAG, "BMChangeMSISDN");

        // create a soap operation data
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMChangeMSISDN.getUrl(),
                SOAPDescription.BMChangeMSISDN.getNamespace(),
                "BMChangeMSISDN");
        // handle to invoke task
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "UPDUSERPROF");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);
        AddItem(requestBody, "msisdn", msisdn);
        AddItem(requestBody, "otp", otp);
        AddItem(requestBody, "mobileNumber", mobileNumber);


        data.SetInputData(FormatInputData(requestHeader, requestBody, "changeMsisdnInput"));

        task.execute(data);
        return data.getId();
    }


    public UUID BMChangeEmail(String uniqueId, String msisdn, String otp, String emailId ) {
        CLog.i(TAG, "BMChangeEmail");

        // create a soap operation data
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMChangeEmail.getUrl(),
                SOAPDescription.BMChangeEmail.getNamespace(),
                "BMChangeEmail");
        // handle to invoke task
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "UPDUSERPROF");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);
        AddItem(requestBody, "emailId", emailId);
        AddItem(requestBody, "otp", otp);
        AddItem(requestBody, "msisdn", msisdn);


        data.SetInputData(FormatInputData(requestHeader, requestBody, "updateEmailInput"));

        task.execute(data);
        return data.getId();
    }

    public UUID WalletLimitCheck(String uniqueId) {
        CLog.i(TAG, "WalletLimitCheck");

        // create a soap operation data
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.WalletLimitCheck.getUrl(),
                SOAPDescription.WalletLimitCheck.getNamespace(),
                "WalletLimitCheck");
        // handle to invoke task
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "WLTLIMCHK");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);



        data.SetInputData(FormatInputData(requestHeader, requestBody, "walletLimitCheckInput"));

        task.execute(data);
        return data.getId();
    }








    public UUID BMDeleteUserEvent(String uniqueId, String eventId) {
        CLog.i(TAG, "BMDeleteUserEvent");

        // create a soap operation data
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMDeleteUserEvent.getUrl(),
                SOAPDescription.BMDeleteUserEvent.getNamespace(),
                "BMDeleteUserEvent");
        // handle to invoke task
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        //AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "DELETEUSEREVENT");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);
        AddItem(requestBody, RESTags.EVENT_ID, eventId);


        data.SetInputData(FormatInputData(requestHeader, requestBody, "deleteUserEventInput"));

        task.execute(data);
        return data.getId();
    }


    public UUID BMMerchantBalance(String msisdn,String pin) {
        CLog.i(TAG, "BMMerchantBalance");

        // create a soap operation data
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMMerchantBalance.getUrl(),
                SOAPDescription.BMMerchantBalance.getNamespace(),
                "BMMerchantBalance");
        // handle to invoke task
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        //AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
/*        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "CHIPACPT");*/
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        /*AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);*/

        HashMap<String, Object> requestBody = new HashMap<String, Object>();
        AddItem(requestBody, RESTags.TYPE, "RBEREQ");
        AddItem(requestBody, RESTags.MOBILE, msisdn);
        AddItem(requestBody, RESTags.PROVIDER, "101");
        AddItem(requestBody, RESTags.PAYID, "12");
        AddItem(requestBody, RESTags.MPIN, pin);
        AddItem(requestBody, RESTags.LANGUAGE, "1");
        AddItem(requestBody, RESTags.BLOCKSMS, "BOTH");
        AddItem(requestBody, RESTags.CELLID, "Cellid1234");
        AddItem(requestBody, RESTags.FTXNID, "FTxnId345");
        System.out.println("COMMAND STRING:::: "+ CreateCommand(requestBody));

        data.SetInputData(CreateCommand(requestBody));

        task.execute(data);
        return data.getId();
    }

    public UUID UpdatePin(String msisdn,String oldpin, String newpin) {
        CLog.i(TAG, "UpdatePin");

        // create a soap operation data
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMMerchantBalance.getUrl(),
                SOAPDescription.BMMerchantBalance.getNamespace(),
                "UpdatePin");
        // handle to invoke task
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        //AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
/*        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "CHIPACPT");*/
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        /*AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);*/

        HashMap<String, Object> requestBody = new HashMap<String, Object>();
        AddItem(requestBody, RESTags.TYPE, "RCMPNREQ");
        AddItem(requestBody, RESTags.MOBILE, msisdn);
        AddItem(requestBody, RESTags.MPIN, oldpin);
        AddItem(requestBody, RESTags.NEWMPIN, newpin);
        AddItem(requestBody, RESTags.CONFIRMMPIN, newpin);
        AddItem(requestBody, RESTags.LANGUAGE, "1");
        AddItem(requestBody, RESTags.CELLID, "Cellid1234");
        AddItem(requestBody, RESTags.FTXNID, "FTxnId345");

        System.out.println("COMMAND STRING:::: "+ CreateCommand(requestBody));

        data.SetInputData(CreateCommand(requestBody));

        task.execute(data);
        return data.getId();
    }


    public UUID BMSetSecurityQuestions(String msisdn,String question) {
        CLog.i(TAG, "BMSetSecurityQuestions");

        // create a soap operation data
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMSetSecurityQuestion.getUrl(),
                SOAPDescription.BMSetSecurityQuestion.getNamespace(),
                "BMUserQuestions");
        // handle to invoke task
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();
        AddItem(requestBody, RESTags.TYPE, "SETANSREQ");
        AddItem(requestBody, RESTags.MOBILE, msisdn);
        AddItem(requestBody, RESTags.DATA, question);
        AddItem(requestBody, RESTags.PROVIDER, "101");
        AddItem(requestBody, RESTags.LANGUAGE, "1");
        AddItem(requestBody, RESTags.USERTYPE, "CHANNEL");

        System.out.println("COMMAND STRING:::: "+ covertToXML(requestBody,"COMMAND"));

        data.SetInputData(covertToXML(requestBody,"COMMAND"));

        task.execute(data);
        return data.getId();
    }

    public UUID BMGetSecurityQuestions(String msisdn) {
        CLog.i(TAG, "BMUserQuestions");

        // create a soap operation data
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMMerchantBalance.getUrl(),
                SOAPDescription.BMMerchantBalance.getNamespace(),
                "BMGetSecurityQuestions");
        // handle to invoke task
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();
        AddItem(requestBody, RESTags.TYPE, "FETQNSREQ");
        AddItem(requestBody, RESTags.MOBILE, msisdn);
        AddItem(requestBody, RESTags.PROVIDER, "101");
        AddItem(requestBody, RESTags.LANGUAGE, "1");
        AddItem(requestBody, RESTags.BLOCKSMS, "BOTH");
        AddItem(requestBody, RESTags.USERTYPE, "CHANNEL");
        AddItem(requestBody, RESTags.CELLID, "Cellid1234");
        AddItem(requestBody, RESTags.FTXNID, "FTxnId345");

        System.out.println("COMMAND STRING:::: "+ CreateCommand(requestBody));

        data.SetInputData(CreateCommand(requestBody));

        task.execute(data);
        return data.getId();
    }

    public UUID ValidateAnswer(String msisdn,String qid,String answer) {
        CLog.i(TAG, "BMUserQuestions");
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMMerchantBalance.getUrl(),
                SOAPDescription.BMMerchantBalance.getNamespace(),
                "BMUserQuestions");
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        HashMap<String, Object> requestBody = new HashMap<String, Object>();
        AddItem(requestBody, RESTags.TYPE, "VALANSREQ");
        AddItem(requestBody, RESTags.MOBILE, msisdn);
        AddItem(requestBody, RESTags.PROVIDER, "101");
        AddItem(requestBody, RESTags.QNSCODE, qid);
        AddItem(requestBody, RESTags.ANSWER, answer);
        AddItem(requestBody, RESTags.LANGUAGE, "1");
        AddItem(requestBody, RESTags.USERTYPE, "CHANNEL");
        AddItem(requestBody, RESTags.CELLID, "Cellid1234");
        AddItem(requestBody, RESTags.FTXNID, "FTxnId345");
        System.out.println("COMMAND STRING:::: "+ CreateCommand(requestBody));
        data.SetInputData(CreateCommand(requestBody));
        task.execute(data);
        return data.getId();
    }

    public UUID GetUserInfo(String msisdn) {
        CLog.i(TAG, "GetUserInfo");
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMMerchantBalance.getUrl(),
                SOAPDescription.BMMerchantBalance.getNamespace(),
                "GetUserInfo");
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        HashMap<String, Object> requestBody = new HashMap<String, Object>();
        AddItem(requestBody, RESTags.TYPE, "RADREQ");
        AddItem(requestBody, RESTags.MOBILE, msisdn);
        AddItem(requestBody, RESTags.PROVIDER, "101");
        AddItem(requestBody, RESTags.CELLID, "Cellid1234");
        AddItem(requestBody, RESTags.FTXNID, "FTxnId345");
        System.out.println("COMMAND STRING:::: "+ CreateCommand(requestBody));
        data.SetInputData(CreateCommand(requestBody));
        task.execute(data);
        return data.getId();
    }




    public UUID BMUserQuestions(String msisdn) {
        CLog.i(TAG, "BMUserQuestions");

        // create a soap operation data
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMMerchantBalance.getUrl(),
                SOAPDescription.BMMerchantBalance.getNamespace(),
                "BMUserQuestions");
        // handle to invoke task
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        //AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
/*        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "CHIPACPT");*/
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        /*AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);*/

        HashMap<String, Object> requestBody = new HashMap<String, Object>();
        AddItem(requestBody, RESTags.TYPE, "UANSQNSREQ");
        AddItem(requestBody, RESTags.MOBILE, msisdn);
        AddItem(requestBody, RESTags.PROVIDER, "101");
        AddItem(requestBody, RESTags.LANGUAGE, "1");
        AddItem(requestBody, RESTags.USERTYPE, "CHANNEL");
        AddItem(requestBody, RESTags.CELLID, "Cellid1234");
        AddItem(requestBody, RESTags.FTXNID, "FTxnId345");

        System.out.println("COMMAND STRING:::: "+ CreateCommand(requestBody));

        data.SetInputData(CreateCommand(requestBody));

        task.execute(data);
        return data.getId();
    }


    public UUID UserRequest(String msisdn,String pin) {
        CLog.i(TAG, "BMMerchantBalance");

        // create a soap operation data
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMMerchantBalance.getUrl(),
                SOAPDescription.BMMerchantBalance.getNamespace(),
                "UserRequest");
        // handle to invoke task
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        //AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
/*        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "CHIPACPT");*/
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        /*AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);*/

        HashMap<String, Object> requestBody = new HashMap<String, Object>();
        AddItem(requestBody, RESTags.TYPE, "USERENQREQ");
        AddItem(requestBody, RESTags.MOBILE, msisdn);
        AddItem(requestBody, RESTags.PROVIDER, "101");
        AddItem(requestBody, RESTags.PAYID, "12");
        AddItem(requestBody, RESTags.MPIN, pin);
        AddItem(requestBody, RESTags.LANGUAGE, "1");
        AddItem(requestBody, RESTags.USERTYPE, "CHANNEL");
        AddItem(requestBody, RESTags.CELLID, "Cellid1234");
        AddItem(requestBody, RESTags.FTXNID, "FTxnId345");
        System.out.println("COMMAND STRING:::: "+ CreateCommand(requestBody));

        data.SetInputData(CreateCommand(requestBody));

        task.execute(data);
        return data.getId();
    }



    public UUID BMCollectPayment(String amount) {
        CLog.i(TAG, "BMCollectPayment");

        // create a soap operation data
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMMerchantBalance.getUrl(),
                SOAPDescription.BMMerchantBalance.getNamespace(),
                "BMCollectPayment");
        // handle to invoke task
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        //AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
/*        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "CHIPACPT");*/
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        /*AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);*/

        HashMap<String, Object> requestBody = new HashMap<String, Object>();
        AddItem(requestBody, RESTags.TYPE, "CMPREQ");
        AddItem(requestBody, RESTags.MOBILE, appData.getMsisdn());
        AddItem(requestBody, RESTags.AMOUNT, amount);
        AddItem(requestBody, RESTags.PROVIDER, "101");
        AddItem(requestBody, RESTags.PROVIDER2, "101");
        AddItem(requestBody, RESTags.PAYID, "12");
        AddItem(requestBody, RESTags.PAYID2, "12");
        AddItem(requestBody, RESTags.MPIN, appData.getPinIntentString());
        AddItem(requestBody, RESTags.MERCODE, "0000000109");
        AddItem(requestBody, RESTags.LANGUAGE, "1");
        AddItem(requestBody, RESTags.CELLID, "Cellid1234");
        AddItem(requestBody, RESTags.FTXNID, "FTxnId345");

        System.out.println("COMMAND STRING:::: "+ CreateCommand(requestBody));

        data.SetInputData(CreateCommand(requestBody));

        task.execute(data);
        return data.getId();
    }

    public UUID BMUnansweredQn(String msisdn) {
        CLog.i(TAG, "BMC2C");

        // create a soap operation data
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMMerchantBalance.getUrl(),
                SOAPDescription.BMMerchantBalance.getNamespace(),
                "BMC2C");
        // handle to invoke task
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestBody = new HashMap<String, Object>();
        AddItem(requestBody, RESTags.TYPE, "UANSQNSREQ");
        AddItem(requestBody, RESTags.MOBILE, msisdn);
        AddItem(requestBody, RESTags.PROVIDER, "101");
        AddItem(requestBody, RESTags.PAYID, "12");
        AddItem(requestBody, RESTags.LANGUAGE, "1");
        AddItem(requestBody, RESTags.CELLID, "Cellid1234");
        AddItem(requestBody, RESTags.FTXNID, "FTxnId345");

        System.out.println("COMMAND STRING:::: "+ CreateCommand(requestBody));

        data.SetInputData(CreateCommand(requestBody));

        task.execute(data);
        return data.getId();
    }


    public UUID ResetPin(String msisdn,String pin,String confirm) {
        CLog.i(TAG, "ResetPin");
        System.out.println("MSISDN::::: "+msisdn);

        // create a soap operation data
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMMerchantBalance.getUrl(),
                SOAPDescription.BMMerchantBalance.getNamespace(),
                "ResetPin");
        // handle to invoke task
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestBody = new HashMap<String, Object>();
        AddItem(requestBody, RESTags.TYPE, "RRPNREQ");
        AddItem(requestBody, RESTags.MOBILE, msisdn);
        AddItem(requestBody, RESTags.PROVIDER, "101");
        AddItem(requestBody, RESTags.USERTYPE, "CHANNEL");
        AddItem(requestBody, RESTags.PINTYPE, "MPIN");
        AddItem(requestBody, RESTags.NEWPIN, pin);
        AddItem(requestBody, RESTags.CONFIRMPIN, confirm);
        AddItem(requestBody, RESTags.BLOCKSMS, "BOTH");
        AddItem(requestBody, RESTags.LANGUAGE, "1");
        AddItem(requestBody, RESTags.CELLID, "Cellid1234");
        AddItem(requestBody, RESTags.FTXNID, "FTxnId345");

        System.out.println("COMMAND STRING:::: "+ CreateCommand(requestBody));

        data.SetInputData(CreateCommand(requestBody));

        task.execute(data);
        return data.getId();
    }


    public UUID BankSVA(String amount,String mobile1,String mobile2,String pin,String accountno,String bankid) {
        CLog.i(TAG, "BankSVA");

        // create a soap operation data
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMMerchantBalance.getUrl(),
                SOAPDescription.BMMerchantBalance.getNamespace(),
                "BankSVA");
        // handle to invoke task
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        System.out.println("IN ENTER PIN MSISDN::::: "+appData.getMsisdn());

        HashMap<String, Object> requestBody = new HashMap<String, Object>();
        AddItem(requestBody, RESTags.TYPE, "CWBREQ");
        AddItem(requestBody, RESTags.MOBILE, mobile1);
        AddItem(requestBody, RESTags.MOBILE2, mobile2);
        AddItem(requestBody, RESTags.AMOUNT, amount);
        AddItem(requestBody, RESTags.PROVIDER, "101");
        AddItem(requestBody, RESTags.PROVIDER2, "101");
        AddItem(requestBody, RESTags.ACCNO2,accountno);
        AddItem(requestBody, RESTags.BANKID,bankid);
        AddItem(requestBody, RESTags.PAYID, "12");
        AddItem(requestBody, RESTags.PAYID2, "12");
        AddItem(requestBody, RESTags.MPIN, pin);
        AddItem(requestBody, RESTags.LANGUAGE, "1");
        AddItem(requestBody, RESTags.CELLID, "Cellid1234");
        AddItem(requestBody, RESTags.FTXNID, "FTxnId345");

        System.out.println("COMMAND STRING:::: "+ CreateCommand(requestBody));

        data.SetInputData(CreateCommand(requestBody));

        task.execute(data);
        return data.getId();
    }

    public UUID BMC2C(String amount,String mobile1,String mobile2,String pin) {
        CLog.i(TAG, "BMC2C");

        // create a soap operation data
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMMerchantBalance.getUrl(),
                SOAPDescription.BMMerchantBalance.getNamespace(),
                "BMC2C");
        // handle to invoke task
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        System.out.println("IN ENTER PIN MSISDN::::: "+appData.getMsisdn());

        HashMap<String, Object> requestBody = new HashMap<String, Object>();
        AddItem(requestBody, RESTags.TYPE, "RTMREQ");
        AddItem(requestBody, RESTags.MOBILE, mobile1);
        AddItem(requestBody, RESTags.MOBILE2, mobile2);
        AddItem(requestBody, RESTags.AMOUNT, amount);
        AddItem(requestBody, RESTags.PROVIDER, "101");
        AddItem(requestBody, RESTags.PROVIDER2, "101");
        AddItem(requestBody, RESTags.PAYID, "12");
        AddItem(requestBody, RESTags.PAYID2, "12");
        AddItem(requestBody, RESTags.MPIN, pin);
        AddItem(requestBody, RESTags.LANGUAGE, "1");
        AddItem(requestBody, RESTags.CELLID, "Cellid1234");
        AddItem(requestBody, RESTags.FTXNID, "FTxnId345");

        System.out.println("COMMAND STRING:::: "+ CreateCommand(requestBody));

        data.SetInputData(CreateCommand(requestBody));

        task.execute(data);
        return data.getId();
    }

    public UUID BMGetTxns(String msisdn,String pin) {

                SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMMerchantBalance.getUrl(),
                SOAPDescription.BMMerchantBalance.getNamespace(),
                "BMGetTxns");

        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        HashMap<String, Object> requestBody = new HashMap<String, Object>();
        AddItem(requestBody, RESTags.TYPE, "RLTREQ");
        AddItem(requestBody, RESTags.MOBILE, msisdn);
        AddItem(requestBody, RESTags.PROVIDER, "101");
        AddItem(requestBody, RESTags.PAYID, "12");
        AddItem(requestBody, RESTags.MPIN, pin);
        AddItem(requestBody, RESTags.BLOCKSMS, "BOTH");
        AddItem(requestBody, RESTags.SERVICE, "ALL");
        AddItem(requestBody, RESTags.LANGUAGE, "1");
        AddItem(requestBody, RESTags.CELLID, "Cellid1234");
        AddItem(requestBody, RESTags.FTXNID, "FTxnId345");
        System.out.println("COMMAND STRING:::: "+ CreateCommand(requestBody));
        data.SetInputData(CreateCommand(requestBody));
        task.execute(data);
        return data.getId();
    }

    public UUID SetLanguage(String msisdn,String lang) {

        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMMerchantBalance.getUrl(),
                SOAPDescription.BMMerchantBalance.getNamespace(),
                "SetLanguage");

        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        HashMap<String, Object> requestBody = new HashMap<String, Object>();
        AddItem(requestBody, RESTags.TYPE, "RSETLANGREQ");
        AddItem(requestBody, RESTags.MOBILE, msisdn);
        AddItem(requestBody, RESTags.BLOCKSMS, "BOTH");
        AddItem(requestBody, RESTags.LANG, lang);
        AddItem(requestBody, RESTags.CELLID, "Cellid1234");
        AddItem(requestBody, RESTags.FTXNID, "FTxnId345");
        System.out.println("COMMAND STRING:::: "+ CreateCommand(requestBody));
        data.SetInputData(CreateCommand(requestBody));
        task.execute(data);
        return data.getId();
    }




    public UUID BMRejectChipin(String chipinRequestId, String remarks) {
        CLog.i(TAG, "BMRejectChipin");

        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BMRejectChipin.getUrl(),
                SOAPDescription.BMRejectChipin.getNamespace(),
                "BMRejectChipin");
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.PYMT);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "CHIPRJCT");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.CHIPIN_REQ_ID, chipinRequestId);
        AddItem(requestBody, RESTags.REMARKS, remarks);


        data.SetInputData(FormatInputData(requestHeader, requestBody, "rejectChipinInput"));

        task.execute(data);
        return data.getId();
    }





    public UUID BM_VerifySecurityAnswer(String msisdn, String pin, String imei, String uniqueId, String securityQuestionId, String securityAnswer) {
        CLog.i(TAG, "BM_VerifySecurityAnswer");

        // create a soap operation data
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BM_VerifySecurityAnswer.getUrl(),
                SOAPDescription.BM_VerifySecurityAnswer.getNamespace(),
                "BM_VerifySecurityAnswer");
        // handle to invoke task
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
       //bypa AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICEID, "");
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "VALSECURITYANS");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.MSISDN, msisdn);
        AddItem(requestBody, RESTags.PIN, pin);
        AddItem(requestBody, RESTags.IMEI, imei);
        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);
        AddItem(requestBody, "securityQuestionId", securityQuestionId);
        AddItem(requestBody, "securityAnswer", securityAnswer);


        data.SetInputData(FormatInputData(requestHeader, requestBody, "validateSecurityAnswerChangeDeviceInput"));

        task.execute(data);
        return data.getId();
    }


    public UUID BM_VerifySecurityAnswerForgotPIN(String msisdn, String nationalId, String imei, String uniqueId, String securityQuestionId, String securityAnswer) {
        CLog.i(TAG, "BM_VerifySecurityAnswerForgotPIN");

        // create a soap operation data
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BM_VerifySecurityAnswerForgotPIN.getUrl(),
                SOAPDescription.BM_VerifySecurityAnswerForgotPIN.getNamespace(),
                "BM_VerifySecurityAnswerForgotPIN");
        // handle to invoke task
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
       //bypass AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICEID, "");
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "VALSECURITYANS");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.MSISDN, msisdn);
        AddItem(requestBody, "nationalId", nationalId);
        // AddItem(requestBody, RESTags.PIN, pin);
        AddItem(requestBody, RESTags.IMEI, imei);
        AddItem(requestBody, RESTags.UNIQUE_ID, uniqueId);
        AddItem(requestBody, "securityQuestionId", securityQuestionId);
        AddItem(requestBody, "securityAnswer", securityAnswer);


        data.SetInputData(FormatInputData(requestHeader, requestBody, "validateSecurityAnswerForgotPINInput"));

        task.execute(data);
        return data.getId();
    }


    public UUID BM_ValidateOTPChangeDevice(String msisdn, String otp) {
        CLog.i(TAG, "BM_ValidateOTPChangeDevice");

        // create a soap operation data
        SOAPOperationData data = new SOAPOperationData(
                SOAPDescription.BM_ValidateOTPChangeDevice.getUrl(),
                SOAPDescription.BM_ValidateOTPChangeDevice.getNamespace(),
                "BM_ValidateOTPChangeDevice");
        // handle to invoke task
        SOAPOperationsTask task = new SOAPOperationsTask(this.handler);
        this.taskTable.put(data.getId(), task);
        // set default header nodes

        HashMap<String, Object> requestHeader = new HashMap<String, Object>();
        AddItem(requestHeader, RESTags.SERVICEID, appData.getServiceId());
        AddItem(requestHeader, RESTags.SERVICECODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.PHASECODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.SUBTRANSACTIONCODE, RESTags.MY_PROF);
        AddItem(requestHeader, RESTags.INTERACTIONCODE, "VEROTP");
        AddItem(requestHeader, RESTags.BEARER, RESTags.BEARER_MOBILE);
        AddItem(requestHeader, RESTags.DEVICE_DETAILS, deviceDetails);

        HashMap<String, Object> requestBody = new HashMap<String, Object>();

        AddItem(requestBody, RESTags.MSISDN, msisdn);
        AddItem(requestBody, RESTags.OTP, otp);


        data.SetInputData(FormatInputData(requestHeader, requestBody, "validateOTPChangeDeviceInput"));

        task.execute(data);
        return data.getId();
    }


////////////////


    public void AddItem(HashMap<String, Object> Data, RESTags Key, RESTags Value) {
        AddItem(Data, Key.getTag(), Value.getTag());
    }

    public void AddItem(HashMap<String, Object> Data, RESTags Key, String Value) {
        AddItem(Data, Key.getTag(), Value);
    }

    public void AddItem(HashMap<String, Object> Data, RESTags Key, Object Value) {
        AddItem(Data, Key.getTag(), Value);
    }

    public void AddItem(HashMap<String, Object> Data, String Key, String Value) {
        if (Data == null)
            Data = new HashMap<String, Object>();

        if (!Data.containsKey(Key)) {
            Data.put(Key, Value);
        }
    }

    public void AddItem(HashMap<String, Object> Data, String Key, Object Value) {
        if (Data == null)
            Data = new HashMap<String, Object>();

        if (!Data.containsKey(Key)) {
            Data.put(Key, Value);
        }
    }


    private org.json.JSONObject GetResult(String response) {
        if (response.length() == 0)
            return null;
        org.json.JSONObject reader = null;
        try {
            reader = new org.json.JSONObject(response);

        } catch (Exception e) {
            String m = e.getMessage();
        }
        return reader;

    }

    public class ResponseHeader {

        @SerializedName("serviceId")
        public String serviceId;

        @SerializedName("overallStatus")
        public String overallStatus;
    }


    public class ResponseData implements Serializable {
        @SerializedName("TXNSTATUS")
        public String txnStatus;

        @SerializedName("MESSAGE")
        public String txnMessage;

        @SerializedName("uniqueId")
        public String uniqueId;

        @SerializedName("emailId")
        public String emailId;

        @SerializedName("mobileNumber")
        public String mobileNumber;

        @SerializedName("firstName")
        public String firstName;

        @SerializedName("dateOfBirth")
        public String dateOfBirth;

        @SerializedName("IVR-RESPONSE")
        public String ivrResponse;

        @SerializedName("aaa")
        public String aaa;

        @SerializedName("image")
        public String image;

        @SerializedName("BALANCE")
        public String balance;

        @SerializedName("FICBALANCE")
        public String ficbalance;

        @SerializedName("frbalance")
        public String frbalance;

        @SerializedName("cardList")
        public ArrayList<Card> cardList;

        @SerializedName("transactionList")
        public ArrayList<TransactionHistory> transactionList;

/*        @SerializedName("DATA")
        public TxnData Data;*/


        @SerializedName("favouriteTxnsList")
        public ArrayList<TransactionHistory> favouriteTxnsList;

        @SerializedName("notificationList")
        public ArrayList<Notification> notificationList;

        @SerializedName("TXNID")
        public String transactionId;

        @SerializedName("securityQuestionId")
        public String securityQuestionId;

        @SerializedName("securityQuestion")
        public String securityQuestion;

        @SerializedName("securityQuestionDetailList")
        public ArrayList<SecurityQuestions> securityQuestionDetailList;

        @SerializedName("accountDetailsList")
        public ArrayList<Bank> bankList;

        @SerializedName("isEmailVerified")
        public String isEmailVerified;

        @SerializedName("isMobileVerified")
        public String isMobileVerified;

        @SerializedName("deviceStatus")
        public String deviceStatus;

        @SerializedName("requestMoneyId")
        public String requestMoneyId;

        @SerializedName("favTransactionId")
        public String favTransactionId;

        @SerializedName("userDataList")
        public ArrayList<Customer> userDataList;


    }

    public static Boolean getCheckingPinStatus() {
        return checkingPinStatus;
    }

    public static void setCheckingPinStatus(Boolean Status) {
        checkingPinStatus = Status;
    }

    private static Boolean checkingPinStatus = false;


    Gson _gson;

    public Gson GetGson() {
        if (null == _gson) {
            GsonBuilder builder = new GsonBuilder();
            _gson = new Gson();
            //	_gson = new GsonBuilder().registerTypeAdapter(Date.class, new Object()).create();
        }
        return _gson;
    }


    public String GetValueFromData(org.json.JSONObject result, String header, String name) {
        try {

            return result.getJSONObject("responseHeader").getString(name);

        } catch (Exception e) {
            String msg = e.getMessage();
            return "";
        }
    }

    public void ClearAppData()
    {
        Object appDcata = Wallet.Data.ClearData();
        appData.setUserData(new ResponseData());
        appData.setServiceId("");
        appData.setUniqueId("");


    }

}
