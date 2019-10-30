package com.example.demor.dingdingadress;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class PersonDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);
        TextView tv_person_name=(TextView) findViewById(R.id.tv_person_name);
        Intent resultintent=getIntent();
        String name=resultintent.getStringExtra("name");
        tv_person_name.setText(name);
    }
}
