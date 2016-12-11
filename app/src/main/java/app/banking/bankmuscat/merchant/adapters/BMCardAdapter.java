package app.banking.bankmuscat.merchant.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import app.banking.bankmuscat.R;
import app.banking.bankmuscat.merchant.entity.instrument.Card;
import app.banking.bankmuscat.merchant.interfaces.IBMCardAdapter;

public class BMCardAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<Card> dataList;
	private IBMCardAdapter client;

	public BMCardAdapter(Context con,
						 ArrayList<Card> itemList, IBMCardAdapter client) {
		this.context = con;
		this.dataList = itemList;
		this.client = client;
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
		protected TextView cardnumber;
		protected ImageView cardType;
		protected ImageView deleteCard;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		View item = convertView;
		ViewHolder viewHolder = new ViewHolder();

		Card card = dataList.get(position);

		if (item == null) {
			item = LayoutInflater.from(context).inflate(
					R.layout.bm_card_list_row, parent, false);

			viewHolder.name = (TextView) item.findViewById(R.id.txtname);
			viewHolder.cardnumber = (TextView) item.findViewById(R.id.txtcard);
			viewHolder.name.setText(card.cardHolderName);
			/*if(card.getUserName().length() == 0)
				viewHolder.name.setText(card.getNickName());
			if(card.getNickName().length() == 0)
				viewHolder.name.setText(card.getBankName());*/

			viewHolder.deleteCard = (ImageView) item.findViewById(R.id.removecard);
			viewHolder.cardType = (ImageView) item.findViewById(R.id.cardtype);


			viewHolder.deleteCard.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					client.OnCardDelete(position);
				}
			});
		}

		Card data = dataList.get(position);

		viewHolder.name.setText(data.cardHolderName );
		viewHolder.cardnumber.setText(data.cardNumber );
		switch (data.cardType.toUpperCase())
		{
			case "VISA":
			viewHolder.cardType.setImageDrawable(context.getResources().getDrawable(R.drawable.visa));
				break;
			case "MASTER":
				viewHolder.cardType.setImageDrawable(context.getResources().getDrawable(R.drawable.master));
				break;
			default:
				viewHolder.cardType.setImageDrawable(context.getResources().getDrawable(R.drawable.master));
				break;
		}


		return item;
	}





}
