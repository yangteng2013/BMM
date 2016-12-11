package app.banking.bankmuscat.merchant.common;

import java.util.HashMap;
import java.util.Map;

public class ErrorMessageCodes  {

	Map<Integer, String> errorDataBank = new HashMap<Integer, String>();

	private static ErrorMessageCodes instance;

	public static ErrorMessageCodes createInstance() {

		if (instance == null) {
			instance = new ErrorMessageCodes();
		}
		return instance;
	}

	private ErrorMessageCodes() {
		init();
	}

	private void init() {


	}

	protected void addError(int code, String message) {
		errorDataBank.put(code, message);
	}

	protected void addError(String message, int code) {
		addError(code, message);
	}

	public String getErrorMessage(int code) {
		return errorDataBank.get(code);
	}

	private class ErrorObj {
		ErrorObj(int code, String message) {
			this.code = code;
			this.message = message;
		}

		private int code;
		private String message;
	}
}
