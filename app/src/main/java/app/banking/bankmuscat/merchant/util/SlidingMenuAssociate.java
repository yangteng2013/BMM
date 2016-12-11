package app.banking.bankmuscat.merchant.util;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import app.banking.bankmuscat.R;
import app.banking.bankmuscat.merchant.adapters.NavDrawerListAdapter;

public class SlidingMenuAssociate {

	private static String TRANSACTION_HISTORY = "Transaction History";
	private static String PENDING_REQUESTS = "Pending Requests";
	private static String SETTINGS = "Settings";
	private static String HELP = "Help";
	private static String FAQ = "FAQ";
	private static String ABOUT = "About";

	private final String activityTransaction = "TransactionHistory";
	private final String activitySetting = "Settings";
	private final String activityHelp = "Help";
	private final String activityFaq = "FAQs";
	private final String activityAbout = "About";

	//
	private DrawerLayout drawerLayout;
	private ListView list;
	private ImageView showMenu;
	private Activity activity;
	private Context context;

	public SlidingMenuAssociate(Activity activity, DrawerLayout drawerLayout,
			ListView list, ImageView showMenu) {
		this.activity = activity;
		context = this.activity.getApplicationContext();
		this.drawerLayout = drawerLayout;
		this.list = list;
		this.showMenu = showMenu;

		init();
	}

	private void init() {
		//
		showMenu.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				toggleSliding();
			}
		});
		//
		ArrayList<String> marketDrawerItems = new ArrayList<String>();
		marketDrawerItems.add(TRANSACTION_HISTORY);
		marketDrawerItems.add(PENDING_REQUESTS);
		marketDrawerItems.add(SETTINGS);
		marketDrawerItems.add(HELP);
		marketDrawerItems.add(FAQ);
		marketDrawerItems.add(ABOUT);
		//
		NavDrawerListAdapter marketNavigationAdapter = new NavDrawerListAdapter(
				context, marketDrawerItems);

		list.setAdapter(marketNavigationAdapter);
		drawerLayout.openDrawer(Gravity.LEFT);
		drawerLayout.closeDrawer(list);
		// /
		onClickDrawerLayout();
		//
		//
		ActionBarDrawerToggle marketDrawerToggle = new ActionBarDrawerToggle(
				activity, drawerLayout, R.drawable.ic_launcher,
				R.string.drawer_open, R.string.drawer_close) {

			public void onDrawerClosed(View view) {
				activity.invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				activity.invalidateOptionsMenu();
			}
		};
		drawerLayout.setDrawerListener(marketDrawerToggle);
	}

	private void onClickDrawerLayout() {

		list.setOnItemClickListener(new DrawerItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				/*if (position == 0) {

					if (activityTransaction.equals(activity.getClass()
							.getSimpleName())) {
						drawerLayout.closeDrawer(Gravity.LEFT);
					} else {
						drawerLayout.closeDrawer(Gravity.LEFT);
						Intent intent = new Intent(context,
								TransactionHistory.class);
						activity.startActivity(intent);
					}

				} else if (position == 2) {

					if (activitySetting.equals(activity.getClass()
							.getSimpleName())) {
						drawerLayout.closeDrawer(Gravity.LEFT);
					} else {

						drawerLayout.closeDrawer(Gravity.LEFT);
						Intent intent = new Intent(context, Settings.class);
						activity.startActivity(intent);
					}

				} else if (position == 3) {
					if (activityHelp.equals(activity.getClass()
							.getSimpleName())) {
						drawerLayout.closeDrawer(Gravity.LEFT);
					} else {

						drawerLayout.closeDrawer(Gravity.LEFT);
						Intent intent = new Intent(context, Help.class);
						activity.startActivity(intent);
					}

				} else if (position == 4) {
					if (activityFaq.equals(activity.getClass()
							.getSimpleName())) {
						drawerLayout.closeDrawer(Gravity.LEFT);
					} else {

						drawerLayout.closeDrawer(Gravity.LEFT);
						Intent intent = new Intent(context, FAQs.class);
						activity.startActivity(intent);
					}

				} else if (position == 5) {

					if (activityAbout.equals(activity.getClass()
							.getSimpleName())) {
						drawerLayout.closeDrawer(Gravity.LEFT);
					} else {

						drawerLayout.closeDrawer(Gravity.LEFT);
						Intent intent = new Intent(context, About.class);
						activity.startActivity(intent);
					}

				}else if (position == 1) {

					if (activityAbout.equals(activity.getClass()
							.getSimpleName())) {
						drawerLayout.closeDrawer(Gravity.LEFT);
					} else {

						drawerLayout.closeDrawer(Gravity.LEFT);
						Intent intent = new Intent(context, PendingRequestTabView.class);
						activity.startActivity(intent);
					}

				}*/
			}
		});
	}

	public void toggleSliding() {
		if (null == drawerLayout)
			return;
		if (drawerLayout.isDrawerOpen(Gravity.LEFT)) {
			drawerLayout.closeDrawer(Gravity.LEFT);
		} else {
			drawerLayout.openDrawer(Gravity.LEFT);
		}
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
		}
	}
}
