package com.example.demor.dingdingadress;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class CompanyOrganizationActivity extends FragmentActivity {

    private CompanyOrganizationFragment fragmentCompany;
    private TextView tv_company_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        tv_company_name=findViewById(R.id.tv_company_name);

        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        fragmentCompany = new CompanyOrganizationFragment();
        FragmentTransaction trx = getSupportFragmentManager()
                .beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("fid","公司");
        bundle.putString("groupname","公司");
        fragmentCompany.setArguments(bundle);
        trx.add(R.id.rl_main, fragmentCompany);
        trx.commit();
    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onMessageEvent(String name) {
        tv_company_name.setText(name);
    }

    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
