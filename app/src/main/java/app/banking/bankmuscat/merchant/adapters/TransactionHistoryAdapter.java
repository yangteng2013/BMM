package app.banking.bankmuscat.merchant.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import app.banking.bankmuscat.R;
import app.banking.bankmuscat.merchant.entity.instrument.TransactionHistoryItems;

public class TransactionHistoryAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<TransactionHistoryItems> txnlist;
	public int ClickedPosition;
	
	public TransactionHistoryAdapter(Context context,ArrayList<TransactionHistoryItems> txnlist) {
		super();
		this.context = context;
		this.txnlist = txnlist;
		ClickedPosition = -1;
		
	}
	
	public int getCount() {
		return txnlist.size();
	}

	@Override
	public boolean areAllItemsEnabled() {
		return true;
	}

	@Override
	public boolean isEnabled(int position) {
		return true;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	public static class ViewHolder {
		protected TextView merchantName;
		protected TextView txnAmount;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		View item = convertView;
		 ViewHolder viewHolder = new ViewHolder();
		 if (item == null){
		item= LayoutInflater.from(context).inflate(R.layout.transaction_history_list_items,parent,false);
		viewHolder.merchantName = (TextView) item.findViewById(R.id.t_textView1);
		viewHolder.txnAmount =(TextView)item.findViewById(R.id.imageView1_t);
		item.setTag(viewHolder);
		 }
		 else{
		        viewHolder = (ViewHolder) item.getTag();
		    }
		final TransactionHistoryItems addr = txnlist.get(position);
		viewHolder.merchantName.setText(addr.getMerchantName());
		viewHolder.txnAmount.setText(addr.getTxnAmount());
		if (position % 2 == 1) {
			item.setBackgroundResource(R.color.list_grey);
			viewHolder.txnAmount.setBackgroundResource(R.drawable.alternate_text);
		}
		item.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});
				return item;
	}
}
