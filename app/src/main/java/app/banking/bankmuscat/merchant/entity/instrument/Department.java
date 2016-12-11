package app.banking.bankmuscat.merchant.entity.instrument;

public class Department {
	private String deptType="";
	private Integer mainIcons;
	

public Department(String deptType,Integer mainIcons)
{
	this.setDeptType(deptType);
	this.setMainIcons(mainIcons);
}


public String getDeptType() {
	return deptType;
}


public void setDeptType(String deptType) {
	this.deptType = deptType;
}


public Integer getMainIcons() {
	return mainIcons;
}


public void setMainIcons(Integer mainIcons) {
	this.mainIcons = mainIcons;
}




}
