package app.banking.bankmuscat.merchant.entity;

import java.util.UUID;

public class BaseData {
	
	protected String id;
	
	private Object data = "";
	
	/**
	 * get data
	 * @return
	 */
	public Object getData() {
		return data;
	}

	/**
	 * set data
	 * @param data
	 */
	public void setData(Object data) {
		this.data = data;
	}
	/**
	 * get unique id of this obj
	 * @return
	 */
	public String getId() {
		return id;
	}
	/**
	 * clone object
	 */
	public BaseData clone()
	{
		return this;
	}

	public BaseData()
	{
		this.id = UUID.randomUUID().toString();
	}
	
	

}
