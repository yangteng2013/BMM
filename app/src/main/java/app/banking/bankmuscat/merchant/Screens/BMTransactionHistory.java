package app.banking.bankmuscat.merchant.Screens;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.UUID;

import app.banking.bankmuscat.R;
import app.banking.bankmuscat.merchant.adapters.BMNotificationAdapter;
import app.banking.bankmuscat.merchant.api.APIManager;
import app.banking.bankmuscat.merchant.base.ActivityBase;
import app.banking.bankmuscat.merchant.common.Wallet;
import app.banking.bankmuscat.merchant.entity.instrument.Notification;
import app.banking.bankmuscat.merchant.logger.CLog;
import app.banking.bankmuscat.merchant.util.constant.ErrorAndPopupCodes;

public class BMTransactionHistory extends ActivityBase {
    private SwipeRefreshLayout swipeContainer;
    TextView headingTextView;
    private RecyclerView transactions;
    private BMNotificationAdapter listAdapter;
    private final APIManager apiManager = APIManager.createInstance(null);
    ArrayList<Notification> notificationList = new ArrayList<Notification>();
    @Override
    public int GetScreenLayout() {
        return R.layout.bm_txn_history;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        headingTextView = (TextView) findViewById(R.id.title_header);
        headingTextView.setText(getResources().getString(R.string.recenttransactions));
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                GetTxns();


            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        GetTxns();


    }

    void LoadList(){
        transactions=(RecyclerView) findViewById(R.id.transactions);
        transactions.setLayoutManager(new LinearLayoutManager(this));
        listAdapter = new BMNotificationAdapter(this, notificationList);
        transactions.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
        Animation slide_down = AnimationUtils.loadAnimation(this,
                R.anim.slide);
        swipeContainer.setRefreshing(false);
        transactions.startAnimation(slide_down);
    }

    private void GetTxns() {
        if (!verifyInternetStatus()) {
            return;
        }
        showLoader();
        Wallet.Data.syncRead(getApplicationContext());
        try {
            final UUID resultId = apiManager.BMGetTxns(appData.getMsisdn(),appData.getPinIntentString());

            final APIManager.APIMessageListener l = new APIManager.APIMessageListener() {

                @Override
                public void handleMessage(APIManager.SOAPOperationData data) {
                    try {
                        org.json.JSONObject txnData = (org.json.JSONObject) data.getResponseDataObj().get("DATA");
                        int count = Integer.parseInt((String)data.getResponseDataObj().get("NOOFTXN"));
                        org.json.JSONArray amounts=(org.json.JSONArray)txnData.get("TXNAMT");
                        org.json.JSONArray status=(org.json.JSONArray)txnData.get("TXNSTATUS");
                        org.json.JSONArray types=(org.json.JSONArray)txnData.get("TXNTYPE");
                        org.json.JSONArray txids=(org.json.JSONArray)txnData.get("TXNID");
                        org.json.JSONArray txdates=(org.json.JSONArray)txnData.get("TXNDT");
                        org.json.JSONArray from=(org.json.JSONArray)txnData.get("FROM");
                        if (data.getId() == resultId)
                        {
                            if("200".equalsIgnoreCase((String)data.getResponseDataObj().get("TXNSTATUS")))
                            {
                                for(int i=0;i<count;i++) {
                                    Notification item = new Notification();
                                    item.setAmount((String) amounts.get(i));
                                    item.setNotificationStatus((String) status.get(i));
                                    item.setTransactionId((String) txids.get(i));
                                    item.setTransactionType((String) types.get(i));
                                    item.setNotificationDateTime((String) txdates.get(i));
                                    item.setMobileNumber((String) from.get(i));
                                    notificationList.add(item);
                                }
                                apiManager.removeListener(this);
                                LoadList();
                                hideLoader();
                            }else
                            {
                                apiManager.removeListener(this);
                                hideLoader();
                                showAlert((String)data.getResponseDataObj().get("MESSAGE"));
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        hideLoader();
                       showAlert(getResources().getString(R.string.no_txns));

                    }
                }
            };
            apiManager.addListener(l);
        } catch (Exception e) {
            e.printStackTrace();
            hideLoader();
        }
    }

}
