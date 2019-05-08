package sdk.facecamera.demo.fragment;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

/**
 * Created by 云中双月 on 2018/3/30.
 */

public class CameraInfoFragment extends BaseFragment{
    @Override
    public View initView() {
        TextView textView = new TextView(getActivity());
        textView.setText("相机参数");
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(30);
        return textView;
    }

    @Override
    public void initData() {

    }
}
