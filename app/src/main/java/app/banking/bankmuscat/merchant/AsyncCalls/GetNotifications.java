package app.banking.bankmuscat.merchant.AsyncCalls;

import android.content.Context;
import android.os.AsyncTask;

import app.banking.bankmuscat.merchant.api.APIManager;


/**
 * Created by amit.randhawa on 6/28/2016.
 */
public class GetNotifications extends AsyncTask<String, String, String> {


    String notificationId ="";
    Context context;
    protected final APIManager apiManager = APIManager.createInstance(null);

    public GetNotifications(String id, Context con)
    {
        notificationId = id;
        context = con;
    }

    @Override
    protected String doInBackground(String... params) {

        //apiManager.markNotificationAsRead(context,notificationId);
        return null;

    }




}