package app.banking.bankmuscat.merchant.entity.instrument;

public class Product {

	private String type = "";
	private String price = "";
	private String sellingPrice="";
	private Boolean isInCart = false;
	private int cartIndex = -1;
	private int cartSize=0;
    private String quantity="1";
	public Product() {
	}

	public Product(String type, String price, Boolean isInCart,String sellingPrice) {
		super();
		this.type = type;
		this.price = price;
		this.isInCart = isInCart;
		this.sellingPrice=sellingPrice;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Boolean getIsInCart() {
		return isInCart;
	}

	public void setIsInCart(Boolean isInCart) {
		this.isInCart = isInCart;
	}

	public int getCartIndex() {
		return cartIndex;
	}

	public void setCartIndex(int cartIndex) {
		this.cartIndex = cartIndex;
	}

	public int getCartSize() {
		return cartSize;
	}

	public void setCartSize(int cartSize) {
		this.cartSize = cartSize;
	}

	public String getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(String sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

}
