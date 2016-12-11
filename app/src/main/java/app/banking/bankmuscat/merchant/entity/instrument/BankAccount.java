package app.banking.bankmuscat.merchant.entity.instrument;

import app.banking.bankmuscat.merchant.entity.BaseData;

/**
 * Created by ADMIN on 12/11/2016.
 */

public class BankAccount extends BaseData {

    public String name;

    public BankAccount(String name) {
        this.name = name;
        this.isSelected = false;
    }

    public Boolean isSelected;

}
