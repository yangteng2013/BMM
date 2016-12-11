package app.banking.bankmuscat.merchant.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.banking.bankmuscat.R;
import app.banking.bankmuscat.merchant.entity.instrument.Notification;


public class BMAccountsAdapter extends RecyclerView.Adapter  {

	private Context context;
	private List<String> notificationlist;

	public BMAccountsAdapter(Context con, List<String> notificationlist) {
		this.context = con;
		this.notificationlist = notificationlist;

	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

		Myholder1 viewHolder = (Myholder1)holder;
/*		viewHolder.date.setText("gdsgfys");
		viewHolder.txtname.setText("shvdhss");
		viewHolder.amount.setText("RO 200");
		viewHolder.txnId.setText("Wallet Txn ID: HGHGAS8789787");
		viewHolder.txttype.setText( "Load SVA");
		viewHolder.image.setBackgroundResource(R.drawable.cashintlisticon);*/

		/*setFadeAnimation(viewHolder.itemView);*/

	}


	@Override
	public long getItemId(int position) {
		return 0;
	}

	public static class holder1 {
		protected TextView txt_title, txt_subtitle;

	}

	public class Myholder1 extends RecyclerView.ViewHolder {
/*		public TextView date, txtname, txttype, amount,txnId;
		ImageView image;*/

		public Myholder1(View view) {
			super(view);
/*			date = (TextView) view.findViewById(R.id.txtdate);
			txtname = (TextView) view.findViewById(R.id.txtname);
			txttype = (TextView) view.findViewById(R.id.txttype);
			amount = (TextView) view.findViewById(R.id.txtamount);
			txnId = (TextView) view.findViewById(R.id.txtid);
			image= (ImageView) view.findViewById(R.id.imgicon);*/
		}
	}
	holder1 prevView = null;

	@Override
	public Myholder1 onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.bm_merchant_accont_list_item, parent, false);

		return new Myholder1(itemView);
	}

	@Override
	public int getItemCount() {
		return this.notificationlist.size();
	}
	private void setFadeAnimation(View view) {
		AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
		anim.setDuration(1000);
		view.startAnimation(anim);
	}

}
