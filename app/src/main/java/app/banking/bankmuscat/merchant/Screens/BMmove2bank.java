package app.banking.bankmuscat.merchant.Screens;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;

import app.banking.bankmuscat.R;
import app.banking.bankmuscat.merchant.adapters.BMAccountsAdapter;
import app.banking.bankmuscat.merchant.adapters.BMNotificationAdapter;
import app.banking.bankmuscat.merchant.base.ActivityBase;
import app.banking.bankmuscat.merchant.components.widgets.RobotoButton;
import app.banking.bankmuscat.merchant.entity.instrument.BankAccount;

public class BMmove2bank extends ActivityBase {

    private RecyclerView accounts;
    private ArrayList<BankAccount> accts = new ArrayList<BankAccount>();
    private BMAccountsAdapter listAdapter;
    private RobotoButton next,cancel;
    @Override
    public int GetScreenLayout() {
        return R.layout.bm_bank;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cancel = (RobotoButton) findViewById(R.id.mob_buttoncancel);
        cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                CloseWin();
            }
        });
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        accts.add(new BankAccount("dsjd"));
        accts.add(new BankAccount("dsjd"));
        accts.add(new BankAccount("dsjd"));
        accts.add(new BankAccount("dsjd"));
        accts.add(new BankAccount("dsjd"));
        accts.add(new BankAccount("dsjd"));
        next= (RobotoButton) findViewById(R.id.mob_buttonnext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getBaseContext(),BMAccountsDetail.class);
                startActivity(i);
            }
        });
        accounts=(RecyclerView) findViewById(R.id.accounts);
        accounts.setLayoutManager(new LinearLayoutManager(this));
        listAdapter=new BMAccountsAdapter(this,accts);
        accounts.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
        Animation slide_down = AnimationUtils.loadAnimation(this,
                R.anim.slide);
        accounts.startAnimation(slide_down);

    }

    void CloseWin()
    {
        this.finish();
    }
}
