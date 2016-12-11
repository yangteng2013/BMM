package app.banking.bankmuscat.merchant.entity.instrument;

public class Merchant {
	private String merchantType="";
	private Boolean isMerchantItemSelected=false;
	private Integer marketMainIconsId;
	public Merchant()
	{
		
	}
	public Merchant(String merchantType,Integer marketMainIconsId,Boolean isMerchantItemSelected)
	{
		this.merchantType=merchantType;
		this.marketMainIconsId=marketMainIconsId;
		this.isMerchantItemSelected=isMerchantItemSelected;
	}
	public String getMerchantType() {
		return merchantType;
	}
	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}
	public Boolean getIsMerchantItemSelected() {
		return isMerchantItemSelected;
	}
	public void setIsMerchantItemSelected(Boolean isMerchantItemSelected) {
		this.isMerchantItemSelected = isMerchantItemSelected;
	}
	public Integer getMarketMainIconsId() {
		return marketMainIconsId;
	}
	public void setMarketMainIconsId(Integer marketMainIconsId) {
		this.marketMainIconsId = marketMainIconsId;
	}
	
}
