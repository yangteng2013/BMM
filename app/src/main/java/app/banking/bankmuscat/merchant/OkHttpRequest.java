package app.banking.bankmuscat.merchant;/*package com.comviva.wallet;

import java.io.IOException;

import jsObject.JSObject;

import android.os.StrictMode;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class OkHttpRequest {
	

	public static final MediaType JSON=MediaType.parse("application/json; charset=utf-8");
	private static OkHttpClient client=new OkHttpClient();
	private static String url="http://54.77.154.84:8789/mwallet/rest/paymentInstrumentManagement/paymentinstrumentmanagement/GetCardList";
	static Logger LOGGER = Logger.getLogger(OkHttpRequest.class.getName());
	
	
	public static JSObject getResponse(JSObject jsRequest){
			
		LOGGER.info("JSONRequest :-   "+jsRequest);
		
		String response = null;
		
		try {
			response = postRequest(jsRequest.getString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JSObject jsResponse = new JSObject(response);
		
		LOGGER.info("JSONResponse :-   "+jsResponse);

		return jsResponse;
	}
	
	
	public static String postRequest(final String json) throws IOException {
		  RequestBody body = RequestBody.create(JSON, json);
		  Request request = new Request.Builder()
		      .url(url)
		      .post(body)
		      .build();
		  
		  StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		  StrictMode.setThreadPolicy(policy);
		  
		  System.out.println("Request Body............" + request);
		  
		  Response response = client.newCall(request).execute();
		  return response.body().string();
		  
		} 
	

	
	
	
	

}
*/