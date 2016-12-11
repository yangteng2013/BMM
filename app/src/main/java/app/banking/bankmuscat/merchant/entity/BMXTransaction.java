package app.banking.bankmuscat.merchant.entity;

/**
 * Created by ADMIN on 10/17/2016.
 */

public class BMXTransaction {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    private String name;
    private double price;

    public BMXTransaction(){
        super();
    }

    public BMXTransaction(int id, String name, int price) {
        super();
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return   this.name + "   ->        RO." + this.price + "";
    }

}
