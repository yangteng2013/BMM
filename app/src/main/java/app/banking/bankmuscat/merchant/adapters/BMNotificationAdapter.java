package app.banking.bankmuscat.merchant.adapters;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import app.banking.bankmuscat.R;
import app.banking.bankmuscat.merchant.entity.instrument.Notification;



public class BMNotificationAdapter extends RecyclerView.Adapter  {

	private Context context;
	private List<Notification> notificationlist;

	public BMNotificationAdapter(Context con, List<Notification> notificationlist) {
		this.context = con;
		this.notificationlist = notificationlist;

	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

		Notification item = notificationlist.get(position);
		Myholder1 viewHolder = (Myholder1)holder;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		try {
			Date date = dateFormat.parse(item.getNotificationDateTime());
			SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy  hh:mm aa", Locale.getDefault());
			String currentData = sdf.format(date);
			viewHolder.date.setText(currentData);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		viewHolder.txtname.setText(item.getMobileNumber());
		viewHolder.amount.setText(context.getResources().getString(R.string.ro)+" "+item.getAmount());
		viewHolder.txnId.setText(context.getResources().getString(R.string.invoice_number)+" "+item.getTransactionId());
		if(item.getTransactionType().equalsIgnoreCase("CR"))
			viewHolder.txttype.setText(context.getResources().getString(R.string.collect_payment));
		else
		viewHolder.txttype.setText( item.getTransactionType());
		viewHolder.image.setBackgroundResource(R.drawable.collectepaymentlisticon);

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
		public TextView date, txtname, txttype, amount,txnId;
		ImageView image;

		public Myholder1(View view) {
			super(view);
			date = (TextView) view.findViewById(R.id.txtdate);
			txtname = (TextView) view.findViewById(R.id.txtname);
			txttype = (TextView) view.findViewById(R.id.txttype);
			amount = (TextView) view.findViewById(R.id.txtamount);
			txnId = (TextView) view.findViewById(R.id.txtid);
			image= (ImageView) view.findViewById(R.id.imgicon);
		}
	}
	holder1 prevView = null;

	@Override
	public Myholder1 onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext())
				.inflate(R.layout.recyclerview_item_row, parent, false);

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
