package app.banking.bankmuscat.merchant.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;

import app.banking.bankmuscat.R;

public class TransactionHistoryAdapterNew extends BaseAdapter {

	private Context context;
	private ArrayList<HashMap<String, String>> dataList;

	public TransactionHistoryAdapterNew(Context con,
			ArrayList<HashMap<String, String>> itemList) {
		this.context = con;
		this.dataList = itemList;
	}

	@Override
	public int getCount() {
		return dataList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public int getItemViewType(int position) {
		return IGNORE_ITEM_VIEW_TYPE;
	}

	public static class ViewHolder {
		protected TextView name;
		protected TextView amount;
		protected TextView comment;
		protected TextView trans_Id;
		protected TextView date;
		protected TextView req_status;
		protected ImageView image;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View item = convertView;
		ViewHolder viewHolder = new ViewHolder();

		if (item == null) {
			item = LayoutInflater.from(context).inflate(
					R.layout.transaction_history_layout, parent, false);
			viewHolder.comment = (TextView) item.findViewById(R.id.textView3);
			viewHolder.name = (TextView) item.findViewById(R.id.textView2);
			viewHolder.req_status = (TextView) item
					.findViewById(R.id.textView5);
			viewHolder.trans_Id = (TextView) item.findViewById(R.id.textView6);
			viewHolder.amount = (TextView) item.findViewById(R.id.textView4);
			viewHolder.date = (TextView) item.findViewById(R.id.textView1);
			viewHolder.image=(ImageView)item.findViewById(R.id.imageView1);
		}

		setDateTime(viewHolder, position);

		viewHolder.trans_Id.setText("Wallet Txn Id: "
				+ dataList.get(position).get("transactionId"));
		viewHolder.comment.setText(dataList.get(position).get("remarks"));

		String credeb = dataList.get(position).get("creditOrDebit");
		String trans_status = dataList.get(position).get("transactionStatus");
		String trans_type = dataList.get(position).get("transactionType");

		if (trans_status.equals("SUCCESS")) {
			viewHolder.req_status.setText(trans_status);
			viewHolder.req_status.setTextColor(Color.parseColor("#00b200"));
		} else if (trans_status.equals("FAIL")) {
			viewHolder.req_status.setText(trans_status);
			viewHolder.req_status.setTextColor(Color.parseColor("#d81d37"));
		}else
		{
			viewHolder.req_status.setText(trans_status);
			viewHolder.req_status.setTextColor(Color.parseColor("#ffc966"));
		}

		if (credeb.equals("CR")) {
			viewHolder.amount.setText("+" + " $ "
					+ dataList.get(position).get("amount"));
			viewHolder.name.setText(dataList.get(position).get("payerName"));

		} else if (credeb.equals("DR")) {
			viewHolder.amount.setText("-" + " $ "
					+ dataList.get(position).get("amount"));
			viewHolder.name.setText(dataList.get(position).get("payeeName"));
		}

		String txnType = dataList.get(position).get("transactionType");

		if (txnType.equals("DP")) {
			//viewHolder.txttype.setText( "Load SVA");
			viewHolder.image.setBackgroundResource(R.drawable.cashintlisticon);
		} else if (txnType.equals("SM")) {
			//viewHolder.txttype.setText( "Send Money");
			viewHolder.image.setBackgroundResource(R.drawable.sendmoneytlisticon);
		} else if (txnType.equals("RM")) {
			viewHolder.image.setBackgroundResource(R.drawable.requestmoneytlisticon);
			//viewHolder.txttype.setText( "Request Money");
		} else if (txnType.equals("SB")) {
			viewHolder.image.setBackgroundResource(R.drawable.transfertobanktlisticon);
			//viewHolder.txttype.setText( "Transfer To Bank");
		}  else if (txnType.equals("CW")) {
			viewHolder.image.setBackgroundResource(R.drawable.cashouttlisticon);
			//viewHolder.txttype.setText( "Load SVA");
		} else if(txnType.equals("RC")) {
			//viewHolder.txttype.setText( "Request Chipin");
			viewHolder.image.setBackgroundResource(R.drawable.chipintlisticon);
		}
		else if(txnType.equals("aaaa")) {
			//viewHolder.txttype.setText( "Request Chipin");
			viewHolder.image.setBackgroundResource(R.drawable.collectepaymentlisticon);
		}
		
        /*if (trans_type.equals("LOADSVA")) {
			
			viewHolder.image.setBackgroundResource(R.drawable.loadsvanew);
		} 
		
		if (trans_type.equals("DP")) {
			
			viewHolder.image.setBackgroundResource(R.drawable.loadsvanew);
		} else if (trans_type.equals("SM")) {
			
			viewHolder.image.setBackgroundResource(R.drawable.sendmoneynewicon);

		} else if (trans_type.equals("RM")) {
			viewHolder.image.setBackgroundResource(R.drawable.requestmoneynew);

		} else if (trans_type.equals("SB")) {
			viewHolder.image.setBackgroundResource(R.drawable.chipinnewicon);

		}  else if (trans_type.equals("CW")) {
			viewHolder.image.setBackgroundResource(R.drawable.loadsvanew);

		} else if(trans_type.equals("RC"))
			viewHolder.image.setBackgroundResource(R.drawable.loadsvanew);*/

		return item;
	}

	private void setDateTime(ViewHolder viewHolder, int pos) {
		try {
			String[] arr = dataList.get(pos).get("transactionDateTime")
					.split(" ");
			String[] unOrgDate = arr[0].split("-");
			String year = unOrgDate[0];
			String month = new DateFormatSymbols().getMonths()[Integer
					.parseInt(unOrgDate[1]) - 1];
			String date = unOrgDate[2];
			String[] unOrgTime = arr[1].split("\\.");

			String time = unOrgTime[0];
			String displayTime = "";

			String[] array = time.split(":");

			if (Integer.parseInt(array[0]) < 12) {
				displayTime = array[0] + array[1] + ":" + " am";
			} else if (Integer.parseInt(array[0]) >= 12) {

				displayTime = (Integer.parseInt(array[0]) - 12) + ":"
						+ array[1] + " pm";
			}

			viewHolder.date.setText(date + " " + month + " " + year + "  "
					+ displayTime);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
