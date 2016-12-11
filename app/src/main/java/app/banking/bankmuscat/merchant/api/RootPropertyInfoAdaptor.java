package app.banking.bankmuscat.merchant.api;

import org.json.JSONObject;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

public class RootPropertyInfoAdaptor extends JSONObject implements KvmSerializable {
	PropertyInfo property;

	protected void addProperty(String name, Object value, Object type) {
		property = new PropertyInfo();
		property.setName(name);
		property.setValue(value);
		property.setType(type);
		//
		try {
			this.put(name, value);//(property);
		}
		catch (Exception e)
		{
			String m = e.getMessage();
		}
	}

	@Override
	public Object getProperty(int arg0) {
		return property;
	}

	@Override
	public int getPropertyCount() {
		return this.getPropertyCount();
	}

	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		arg2.name = property.name;
		arg2.type = property.type;
	}

	@Override
	public void setProperty(int arg0, Object arg1) {
	}


}
