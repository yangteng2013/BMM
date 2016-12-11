package app.banking.bankmuscat.merchant.api;

import org.json.simple.JSONObject;

public class RESTRequest {
	private  String bearer = "";
	private  String phasecode = "";
	private  String serviceCode = "";
	private  String subTransactionCode = "";
	private String interactionCode = "";
	private String serviceId = "";
	
	
	
	public RESTRequest(RESTags bearer,RESTags phasecode,RESTags serviceCode,RESTags subTransactionCode,RESTags interactionCode) {
		
		
		this.bearer = bearer.getTag();
		this.phasecode = phasecode.getTag();
		this.serviceCode = serviceCode.getTag();
		this.subTransactionCode = subTransactionCode.getTag();		
		this.interactionCode = interactionCode.getTag();
	}
	
public RESTRequest(RESTags bearer,RESTags phasecode,RESTags serviceCode,RESTags subTransactionCode,RESTags interactionCode,RESTags serviceId) {
		
		
		this.bearer = bearer.getTag();
		this.phasecode = phasecode.getTag();
		this.serviceCode = serviceCode.getTag();
		this.subTransactionCode = subTransactionCode.getTag();		
		this.interactionCode = interactionCode.getTag();
		this.serviceId = serviceId.getTag();
	}
	public JSONObject getRestHeader(){
		JSONObject header = new JSONObject();
		header.put(RESTags.SERVICECODE.getTag(), serviceCode);
		header.put(RESTags.PHASECODE.getTag(), phasecode);
		header.put(RESTags.SUBTRANSACTIONCODE.getTag(), subTransactionCode);
		header.put(RESTags.INTERACTIONCODE.getTag(), interactionCode);
		header.put(RESTags.BEARER.getTag(), bearer);

		return header;
		
	}

}
