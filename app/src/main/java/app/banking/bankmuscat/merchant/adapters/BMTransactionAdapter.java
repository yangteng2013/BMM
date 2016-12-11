package app.banking.bankmuscat.merchant.adapters;

/**
 * Created by ADMIN on 10/17/2016.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import app.banking.bankmuscat.R;
import app.banking.bankmuscat.merchant.entity.instrument.TransactionHistory;

public class BMTransactionAdapter extends BaseAdapter {


    private ArrayList<TransactionHistory> listData;
    private LayoutInflater layoutInflater;

    public BMTransactionAdapter(Context aContext, ArrayList<TransactionHistory> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.transaction, null);
            holder = new ViewHolder();
            holder.imgicon = (ImageView) convertView.findViewById(R.id.imgicon);
            holder.txtdate = (TextView) convertView.findViewById(R.id.txtdate);
            holder.txttime = (TextView) convertView.findViewById(R.id.txttime);
            holder.txtname = (TextView) convertView.findViewById(R.id.txtname);
            holder.txttype = (TextView) convertView.findViewById(R.id.txttype);
            holder.txtid = (TextView) convertView.findViewById(R.id.txtid);
            holder.txtamount = (TextView) convertView.findViewById(R.id.txtamount);
            holder.txtstatus = (TextView) convertView.findViewById(R.id.txtstatus);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.txtamount.setText("RO " + listData.get(position).amount);

        String strDate = listData.get(position).transactionDateTime;// 2016-10-25 13:57:49.597
        String creditOrDebit = listData.get(position).creditOrDebit;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        try {
            Date date = dateFormat.parse(strDate);

            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy  hh:mm aa", Locale.getDefault());
            String currentData = sdf.format(date);
            holder.txtdate.setText( currentData);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }





        //holder.txttime.setText( listData.get(position).amount);
        /*String type ="";
        switch (listData.get(position).transactionType)
        {
            case "SM": type = "Send Money";
                break;
            case "RM": type = "Request Money";

                break;
            default:
                type = "";
                break;

        }*/

        String txnType = listData.get(position).transactionType;

        if (txnType.equals("DP")) {
            holder.txttype.setText( "Load Money");
            holder.txtname.setText( "Load Money");
            holder.imgicon.setBackgroundResource(R.drawable.cashintlisticon);
        } else if (txnType.equals("SM")) {
            if(creditOrDebit.equalsIgnoreCase("DR"))
            {
                System.out.println("PAYEE: "+listData.get(position).transactionData.payeeName.length()+" NUMBER: "+listData.get(position).transactionData.payeeMobileNumber);
                holder.txtname.setText( listData.get(position).transactionData.payeeName.length()==0 ? listData.get(position).transactionData.payeeMobileNumber : listData.get(position).transactionData.payeeName);
                holder.txttype.setText( "Send Money");
                holder.imgicon.setBackgroundResource(R.drawable.sendmoneytlisticon);
            }else{
                holder.txtname.setText( listData.get(position).transactionData.payerName);
                holder.txttype.setText( "Received Money");
                holder.imgicon.setBackgroundResource(R.drawable.requestmoneytlisticon);
            }
        } else if (txnType.equals("RM")) {
            holder.txtname.setText( listData.get(position).transactionData.payerName);
            holder.imgicon.setBackgroundResource(R.drawable.requestmoneytlisticon);
            holder.txttype.setText( "Request Money");
        } else if (txnType.equals("SB")) {
            holder.txtname.setText( listData.get(position).transactionData.payerName);
            holder.imgicon.setBackgroundResource(R.drawable.chipintlisticon);
            holder.txttype.setText( "Split Bill");
        }  else if (txnType.equals("CW")) {
            holder.txtname.setText( listData.get(position).transactionData.payerName);
            holder.imgicon.setBackgroundResource(R.drawable.cashouttlisticon);
            holder.txttype.setText( "Transfer to Bank");
        } else if(txnType.equals("RC")) {
            holder.txtname.setText( listData.get(position).transactionData.payerName);
            holder.txttype.setText( "Request Chipin");
            holder.imgicon.setBackgroundResource(R.drawable.chipintlisticon);
        }
        else if(txnType.equals("aaaa")) {
            holder.txttype.setText( "Request Chipin");
            holder.imgicon.setBackgroundResource(R.drawable.collectepaymentlisticon);
        }




     //   holder.txttype.setText( type);
        holder.txtstatus.setText( listData.get(position).transactionStatus);
        holder.txtid.setText(convertView.getResources().getString(R.string.wallettxnid) + listData.get(position).transactionId);

        /*holder.headlineView.setText(listData.get(position).getName());
        holder.reporterNameView.setText("By, " + listData.get(position).getId());
        holder.reportedDateView.setText("23-Dec-2016");*/
        return convertView;
    }

    static class ViewHolder {
      ImageView imgicon;
        TextView txtdate;
        TextView txttime;
        TextView txtname;
        TextView txttype;
        TextView txtid;
        TextView txtamount;
        TextView txtstatus;

    }


}
