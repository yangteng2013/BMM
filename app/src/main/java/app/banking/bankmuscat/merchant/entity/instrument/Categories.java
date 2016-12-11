package app.banking.bankmuscat.merchant.entity.instrument;




public class Categories {
private String categoryType="";
private int categoryMainIconsId;
private Boolean isCategorySelected=false;
public Categories()
{
	
}

public Categories(String categoryType,Integer categoryMainIconsId,Boolean isCategorySelected)
{
	super();
	this.categoryType=categoryType;
	this.setCategoryMainIconsId(categoryMainIconsId);
	this.isCategorySelected=isCategorySelected;
}



public String getCategoryType() {
	return categoryType;
}

public void setCategoryType(String categoryType) {
	this.categoryType = categoryType;
}

public Boolean getIsCategorySelected() {
	return isCategorySelected;
}

public void setIsCategorySelected(Boolean isCategorySelected) {
	this.isCategorySelected = isCategorySelected;
}

public int getCategoryMainIconsId() {
	return categoryMainIconsId;
}

public void setCategoryMainIconsId(int categoryMainIconsId) {
	this.categoryMainIconsId = categoryMainIconsId;
}

}
