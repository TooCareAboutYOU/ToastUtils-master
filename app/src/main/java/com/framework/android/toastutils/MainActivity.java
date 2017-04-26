package com.framework.android.toastutils;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import iammert.com.library.ConnectionStatusView;
import iammert.com.library.Status;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ConnectionStatusView mStatusView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStatusView= (ConnectionStatusView) findViewById(R.id.sv_status);

        findViewById(R.id.btn_ShowTop).setOnClickListener(this);
        findViewById(R.id.btn_ShowBottom).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_ShowTop:
                mStatusView.setVisibility(View.VISIBLE);
                mStatusView.setStatus(Status.COMPLETE);

                break;
            case R.id.btn_ShowBottom:
                Snackbar.make(v,"你哈",1000).setAction("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).setDuration(Snackbar.LENGTH_LONG).show();
                break;
        }
    }
}
