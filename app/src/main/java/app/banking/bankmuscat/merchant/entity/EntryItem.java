package app.banking.bankmuscat.merchant.entity;



public class EntryItem implements Item{

	public final String title;
	public final String subtitle;

	public EntryItem(String subtitle, String title) {
		
		this.subtitle = subtitle;
		this.title = title;
	}
	
	@Override
	public boolean isSection() {
		return false;
	}

}
