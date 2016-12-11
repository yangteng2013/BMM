package app.banking.bankmuscat.merchant.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import app.banking.bankmuscat.R;

public class NavDrawerListAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<String> navDrawerItems;

	public NavDrawerListAdapter(Context context,
								ArrayList<String> navDrawerItemsD) {
		this.context = context;
		navDrawerItems = navDrawerItemsD;
	}

	@Override
	public int getCount() {
		return navDrawerItems.size();
	}

	@Override
	public Object getItem(int position) {
		return navDrawerItems.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.drawer_list_item, null);
		}

		TextView txtTitle = (TextView) convertView.findViewById(R.id.txtLabel);

		txtTitle.setText(navDrawerItems.get(position));
		
		ImageView image=(ImageView) convertView.findViewById(R.id.sl_imageView);
		/*if (position == 0) {
			image.setBackgroundResource(R.drawable.pending
);
		}
		if (position == 1) {
			image.setBackgroundResource(R.drawable.transhistory);
		}
		if (position == 2) {
			image.setBackgroundResource(R.drawable.profile);
		}
		if (position == 3) {
			image.setBackgroundResource(R.drawable.download);
		}
		if (position == 4) {
			image.setBackgroundResource(R.drawable.download);
		}
		if (position == 5) {
			image.setBackgroundResource(R.drawable.language);
		}

		if (position == 6) {
			image.setBackgroundResource(R.drawable.logout);
		}
		if (position == 7) {
			image.setBackgroundResource(R.drawable.refer);
		}
		if (position == 8) {
			image.setBackgroundResource(R.drawable.faq);
		}
		if (position == 9) {
			image.setBackgroundResource(R.drawable.infoico);
		}

		if (position == 10) {
			image.setBackgroundResource(R.drawable.terms);
		}*/


		if (position == 0) {
			image.setBackgroundResource(R.drawable.profile
			);
		}
		if (position == 1) {
			image.setBackgroundResource(R.drawable.pinchange);
		}
		if (position == 2) {
			image.setBackgroundResource(R.drawable.transhistory);
		}
		if (position == 3) {
			image.setBackgroundResource(R.drawable.refer);
		}
		if (position == 4) {
			image.setBackgroundResource(R.drawable.language);
		}
		if (position == 5) {
			image.setBackgroundResource(R.drawable.logout);
		}

		if (position == 6) {
			image.setBackgroundResource(R.drawable.faq);
		}
		if (position == 7) {
			image.setBackgroundResource(R.drawable.infoico);
		}
		if (position == 8) {
			image.setBackgroundResource(R.drawable.terms);
		}
		if (position == 9) {
			image.setBackgroundResource(R.drawable.logout);
		}




		return convertView;
	}
	
}
