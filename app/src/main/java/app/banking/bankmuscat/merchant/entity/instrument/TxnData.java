package app.banking.bankmuscat.merchant.entity.instrument;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import app.banking.bankmuscat.merchant.entity.BaseData;

/**
 * Created by ADMIN on 11/30/2016.
 */
public class TxnData extends BaseData implements Serializable {

    @SerializedName("TXNDT")
    public String[] txnDates;

    @SerializedName("TXNTYPE")
    public String[] txnTypes;


    @SerializedName("TXNDT")
    public String[] txnIds;

    @SerializedName("PAYID")
    public String[] payIds;

    @SerializedName("SERVICE")
    public String[] service;

    @SerializedName("FROM")
    public String[] from;

    @SerializedName("TXNAMT")
    public String[] amounts;

    @SerializedName("TXNSTATUS")
    public String[] status;



}
