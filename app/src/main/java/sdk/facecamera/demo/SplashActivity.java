package sdk.facecamera.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import sdk.facecamera.demo.crash.BaseActivity;
import sdk.facecamera.demo.util.LogUtils;
import sdk.facecamera.sdk.FaceSdk;
import sdk.facecamera.sdk.SimpleFaceSdk;
import sdk.facecamera.sdk.pojos.DeviceModel;
import sdk.facecamera.sdk.pojos.NetInfoEx;

public class SplashActivity extends BaseActivity {

    private EditText etIp;
    private Button btConfirm;
    private Button btSearch;
    private StringBuffer stringBuffer;
    private TextView tvSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        etIp = findViewById(R.id.et_ip);
        btConfirm = findViewById(R.id.bt_confirm);
        btSearch = findViewById(R.id.bt_search);
        tvSearch = findViewById(R.id.search_result);

        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ipStr = etIp.getText().toString().trim();
                if (ipStr.isEmpty()) {
                    return;
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("ip", ipStr);
                startActivity(intent);
            }
        });

        SimpleFaceSdk.setSearchListener(new SimpleFaceSdk.OnSearchListener() {
            @Override
            public void onSearchResult(DeviceModel model) {
                LogUtils.i("setDeviceIp  = " + model.getDeviceIp());
            }
        });

        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleFaceSdk.searchDevice();
            }
        });

        SimpleFaceSdk.setConnectEvent(new SimpleFaceSdk.ConnectEvent() {
            @Override
            public void onConnect(String ip, short port, int usrParam) {
                LogUtils.i("onConnect ");
            }

            @Override
            public void onDisConnect(String ip, short port, int usrParam) {
                LogUtils.i("onDisConnect");
            }
        });

//       boolean ret =  SimpleFaceSdk.getInstance().connect("192.168.0.102");
//        LogUtils.i("initialize ret = " + ret);
//
//       new Thread(new Runnable() {
//           @Override
//           public void run() {
//               for (int i = 0;i<100;i++){
//                   String deviceid = SimpleFaceSdk.getInstance().getDeviceId();
//                   LogUtils.i("deviceid = " + deviceid);
//                   SimpleFaceSdk.getInstance().getWifiInfo();
//                   NetInfoEx netInfoEx = SimpleFaceSdk.getInstance().getNetInfo();
//                   LogUtils.i("NetInfoEx = ip = " + netInfoEx.getIp());
//                   LogUtils.i("------------------- i=" + i);
//               }
//           }
//       }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
