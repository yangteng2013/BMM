package app.banking.bankmuscat.merchant;/*package com.comviva.wallet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import jsObject.JSObject;

import org.apache.log4j.Logger;

import android.content.Context;
import android.content.res.AssetManager;



public class RestRequests {

	Context context;
	private static String[] setSessionService = { "getCardListInput" };
	private static JSObject jsCommands = null;
	
	private static Logger LOGGER = Logger.getLogger(RestRequests.class
			.getName());
	private final static Wallet.Data appData = Wallet.Data.getInstance();
	RestResponse response  = new RestResponse();

	public static JSObject getCommand(String commandName, Context context) {

		BufferedReader reader = null;
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			AssetManager assetManager = context.getResources().getAssets();
			InputStream inputStream = null;

			try {
				inputStream = assetManager.open("commands.txt");
				if (inputStream != null) {
					reader = new BufferedReader(new InputStreamReader(
							inputStream));
					while ((line = reader.readLine()) != null) {
						sb.append("\n" + line);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {

				}
			}
		}
		String sFile = sb.toString();

		jsCommands = new JSObject(sFile);

		// String sFile = FileUtil.getCurrentDirectory() + "\\commands.txt";
		
		 * Reader r = FileUtil.openFileForTextInput(sFile); if (r == null) {
		 * LOGGER.fatal("Unable to load commands from: " + sFile); }
		 * 
		 * if (StringUtil.isEmpty(sFile)) {
		 * LOGGER.fatal("Unable to read commands from: " + sFile);
		 * 
		 * }
		 * 
		 * String s = FileUtil.readFileText(r, (int)
		 * FileUtil.getFileSize(sFile)); if (StringUtil.isEmpty(s)) {
		 * LOGGER.fatal("Unable to read commands from: " + sFile); }
		 
		if (jsCommands == null)
			return null;

		JSObject jsCmd;
		int i = 0;
		while (true) {
			jsCmd = jsCommands.getArrayElement("commands", i);
			if (jsCmd == null)
				break;
			String s1 = jsCmd.getValAsString("request");
			if (commandName.equals(s1))
				break;
			i++;
		}
		return jsCmd;
	}

	public static JSObject getRequest(String sCommand, String[] key,
			String[] val, Context context) {

		JSObject jsCmd = getCommand(sCommand, context);
		if (jsCmd == null)
			return null;

		for (int i = 0; i < setSessionService.length; i++) {
			if (sCommand == setSessionService[i]) {
				// jsCmd.setVal("session-id", sessionId);

				break;
			}
		}

		JSObject jsParam = jsCmd.getValAsJSObject("getCardListInput");
		if (jsParam == null)
			return null;
		if (key != null && val != null) {
			for (int i = 0; i < key.length; i++) {
				jsParam.setVal(key[i], val[i]);

			}
		}
		return jsCmd;
	}

	public static void getInstrumentListHCE(Context context) {

		WalletStatus status;

		String key[] = { "uniqueId" };
		String val[] = { appData.getUniqueId() };

		JSObject jsCmd = getRequest("getCardListInput", key, val, context);
		JSObject jsResp = getResponse(jsCmd);
		RestResponse response = new RestResponse();
		response.parseResponse(jsResp);
		//JSObject jsRespParam = jsResp.getValAsJSObject("getCardListOutput");
		

	}

	// send request to the server and get response
	private static JSObject getResponse(JSObject jsCmd) {

		LOGGER.info("JSONRequest :-   " + jsCmd.getString());

		// JSObject jsResp = SimpleRequest.getResponse(jsCmd);
		JSObject jsResp = OkHttpRequest.getResponse(jsCmd);

		LOGGER.info("JSONResponse :-   " + jsResp.getString());

		return jsResp;

	}
}

*/