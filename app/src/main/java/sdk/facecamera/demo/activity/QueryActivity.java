package sdk.facecamera.demo.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import sdk.facecamera.demo.R;
import sdk.facecamera.demo.util.UiUtil;
import sdk.facecamera.sdk.FaceSdk;
import sdk.facecamera.sdk.pojos.QueryFaceModel;

public class QueryActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "QueryActivity";
    /**
     * 请输入id
     */
    private EditText mEditId;
    /**
     * 查询
     */
    private Button mButtonQuery;
    /**
     * 姓名：
     */
    private TextView mTextViewName;
    /**
     * 角色：
     */
    private TextView mTextViewRole;
    /**
     * 韦根卡号：
     */
    private TextView mTextViewWgNo;
    /**
     * 有效时间：
     */
    private TextView mTextViewTime;
    /**
     * 修改
     */
    private Button mButtonChange;
    private String mId;
    private ProgressDialog mProgDia;
    private ImageView imageHead;

    //模拟的几个人员id
    //private String[] ids = {"xiaohe","ctSVCzSJvVI9DYJ2weD","3UntEMobptFfiV77q7p"};
    private int count = 0;
    private QueryFaceModel mQueryModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);
        initView();

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < ids.length; i++) {
                    FaceSdk.getInstance().queryFaceById(ids[i]);
                }
            }
        }).start();*/

        FaceSdk.getInstance().setQueryCallBack(new FaceSdk.QueryCallBack() {
            @Override
            public void onQueryById(boolean success, QueryFaceModel model) throws NullPointerException{
                cancleDialog();
                if (success){
                    /*model.setName("修改"+count);
                    Log.e(TAG, "onQueryById: "+model.getId() +"   名字："+model.getName()+" 是否运行在主线程: "+UiUtil.isRunOnUIThread());
                    boolean ret = FaceSdk.getInstance().modifyFaceInfoById(model);
                    count++;
                    if (ret){
                        Log.e(TAG, "修改人员信息成功");
                    }*/
                    mQueryModel = model;
                    mTextViewName.setText("姓名："+model.getName());
                    if (model.getRole() == 1){
                        mTextViewRole.setText("角色：白名单");
                    }else if(model.getRole() == 2){
                        mTextViewRole.setText("角色：黑名单");
                    }

                    mTextViewWgNo.setText("韦根卡号："+model.getWiegandNo());

                    byte[][] imageData = model.getImageData();
                    if (imageData[0] != null){
                        imageHead.setImageBitmap(byte2Bitmap(imageData[0]));
                    }
                    if (model.getEffectTime() == -1){
                        mTextViewTime.setText("有效时间：永久有效");
                    }else {
                        mTextViewTime.setText("有效时间："+model.getEffectTime());
                    }

                }else {
                    Toast.makeText(getApplicationContext(),"查询人员失败",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private Bitmap byte2Bitmap(byte[] bytes){
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        Log.d("queryactivity", "byte2Bitmap: " + bitmap.getWidth() + "," + bitmap.getHeight());
        return bitmap;
    }
    private void initView() {
        mEditId = (EditText) findViewById(R.id.editId);
        mButtonQuery = (Button) findViewById(R.id.button_query);
        mButtonQuery.setOnClickListener(this);
        mTextViewName = (TextView) findViewById(R.id.textViewName);
        mTextViewRole = (TextView) findViewById(R.id.textViewRole);
        mTextViewWgNo = (TextView) findViewById(R.id.textViewWgNo);
        mTextViewTime = (TextView) findViewById(R.id.textViewTime);
        mButtonChange = (Button) findViewById(R.id.button_change);
        mButtonChange.setOnClickListener(this);
        imageHead = findViewById(R.id.image_head);
        imageHead.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.button_query:
                mId = mEditId.getText().toString().trim();
                if (mId.isEmpty()){
                    Toast.makeText(getApplicationContext(),"id没填",Toast.LENGTH_SHORT).show();
                    return;
                }
                showProgressDia();
                FaceSdk.getInstance().queryFaceById(mId);
                break;
            case R.id.button_change:
                AlertDialog.Builder builder = new AlertDialog.Builder(QueryActivity.this);
                View view = View.inflate(getApplicationContext(),R.layout.dialog_face,null);
                final AlertDialog alertDialog = builder.create();
                alertDialog.setTitle("修改");
                alertDialog.setView(view,0,0,0,0);
                alertDialog.show();
                //final EditText etId = view.findViewById(R.id.et_id);
                final EditText etName = view.findViewById(R.id.et_name);
                final EditText etRole = view.findViewById(R.id.et_role);
                final EditText etNo = view.findViewById(R.id.et_no);
                Button btnConfirm = view.findViewById(R.id.bt_confirm);
                Button btnCancel = view.findViewById(R.id.bt_cancel);
                btnConfirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        QueryFaceModel model = mQueryModel;
                        if (model == null){
                            Toast.makeText(getApplicationContext(),"还没有查询",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        model.setName(etName.getText().toString());
                        model.setRole(Integer.valueOf(etRole.getText().toString()));
                        model.setWiegandNo(Integer.valueOf(etNo.getText().toString()));
                        Log.e(TAG, "onQueryById: "+model.getId() +"   名字："+model.getName()+" 是否运行在主线程: "+UiUtil.isRunOnUIThread());
                        boolean ret = FaceSdk.getInstance().modifyFaceInfoById(model);
                        if (ret){
                            Log.e(TAG, "修改人员信息成功");
                        }
                        /*FaceSdk.getInstance().modifyFaceInfoById(mId,
                                etName.getText().toString(),Integer.valueOf(etRole.getText().toString()),
                                Integer.valueOf(etNo.getText().toString()));*/
                        alertDialog.dismiss();
                        FaceSdk.getInstance().queryFaceById(mId);
                    }
                });
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                break;
        }
    }

    private void showProgressDia(){
        mProgDia = new ProgressDialog(this);
        mProgDia.setMessage("查询中...");
        mProgDia.show();
    }

    private void  cancleDialog(){
        if (mProgDia != null){
            mProgDia.dismiss();
        }
    }
}
