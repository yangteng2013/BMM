package app.banking.bankmuscat.merchant.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.Iterator;
import java.util.List;

import app.banking.bankmuscat.R;
import app.banking.bankmuscat.merchant.entity.instrument.BankAccount;
import app.banking.bankmuscat.merchant.entity.instrument.Notification;


public class BMAccountsAdapter extends RecyclerView.Adapter  {

	private Context context;
	private List<BankAccount> accountslist;
	static int selectedposition=0;
	public BMAccountsAdapter(Context con, List<BankAccount> accountslist) {
		this.context = con;
		this.accountslist = accountslist;

	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


		Myholder1 viewHolder = (Myholder1)holder;
		viewHolder.button.setTag(new Integer(position));

		viewHolder.button.setImageResource(R.drawable.add3);
		if(accountslist.get(position).isSelected) {
			System.out.println("FOUND And Setting!!!!"+position);
			viewHolder.button.setImageResource(R.drawable.add2);
		}
		else {
			viewHolder.button.setImageResource(R.drawable.add3);
		}
		viewHolder.button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Iterator<BankAccount> acctItr =accountslist.iterator();
				while(acctItr.hasNext()) {
					BankAccount temp =acctItr.next();
					temp.isSelected=false;
				}
				BankAccount account= accountslist.get(position);
				account.isSelected=true;
				accountslist.set(position,account);
				notifyItemChanged(position);
				notifyDataSetChanged();
			}
		});
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
		ImageView button;

		public Myholder1(View view) {
			super(view);
			button = (ImageView) view.findViewById(R.id.radiobutton);
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
		return this.accountslist.size();
	}
	private void setFadeAnimation(View view) {
		AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
		anim.setDuration(1000);
		view.startAnimation(anim);
	}

}
