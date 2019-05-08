package sdk.facecamera.demo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import sdk.facecamera.demo.crash.BaseActivity;

public class SplashActivity extends BaseActivity {

    private EditText etIp;
    private Button btConfirm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        etIp = findViewById(R.id.et_ip);
        btConfirm = findViewById(R.id.bt_confirm);


        btConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ipStr = etIp.getText().toString().trim();
                if (ipStr.isEmpty()){
                    return;
                }
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("ip",ipStr);
                startActivity(intent);
                finish();
            }
        });
    }
}
