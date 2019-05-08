package sdk.facecamera.demo.crash;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import sdk.facecamera.demo.R;
import sdk.facecamera.demo.util.PrefUtils;

public class CrashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crash);
        TextView tvError = findViewById(R.id.tv_error);
        String errorInfo = PrefUtils.getString(this, ContentValue.ERRORSTR,"");
        tvError.setText(errorInfo);
    }
}
