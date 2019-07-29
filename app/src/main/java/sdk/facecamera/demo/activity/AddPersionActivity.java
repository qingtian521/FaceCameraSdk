package sdk.facecamera.demo.activity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.TimeZone;

import sdk.facecamera.demo.R;
import sdk.facecamera.demo.crash.BaseActivity;
import sdk.facecamera.demo.util.LogUtils;
import sdk.facecamera.demo.util.UiUtil;
import sdk.facecamera.sdk.FaceSdk;

public class AddPersionActivity extends BaseActivity {
    private int defaultTime = -1;
    private EditText id, name, type, number, time;
    private Button save, cancel;
    private ImageView imageView;
    private int RESULT_LOAD_IMAGE = 200;
    private String idStr, nameStr, typeStr, wieganStr;
    private int typeNum, wiegandNo;
    private Object[] mObjects;
    private ProgressDialog dialog;
    private Bitmap mFaceBmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_persion);
        intiView();


    }

    private void intiView() {
        id = findViewById(R.id.et_add_id);
        name = findViewById(R.id.et_add_name);
        type = findViewById(R.id.et_add_type);
        number = findViewById(R.id.et_add_num);
        time = findViewById(R.id.et_add_time);
        save = findViewById(R.id.button_save);
        cancel = findViewById(R.id.button_cancel);
        imageView = findViewById(R.id.image_add);

        //设置类型和门禁卡默认值
        type.setText(0 + "");
        number.setText(0 + "");

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                idStr = id.getText().toString().trim();
                nameStr = name.getText().toString().trim();
                typeStr = type.getText().toString().trim();
                wieganStr = number.getText().toString().trim();
                String timeStr = time.getText().toString().trim();
                if (!timeStr.isEmpty()) {
                    defaultTime = Integer.valueOf(timeStr);
                }
                if (idStr.isEmpty() || nameStr.isEmpty() || typeStr.isEmpty()
                        || wieganStr.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "信息未填写完毕", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (mFaceBmp == null) {
                    Toast.makeText(getApplicationContext(), "还没有选择人脸图片", Toast.LENGTH_SHORT).show();
                    return;
                }
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                mFaceBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] thumb = baos.toByteArray();
                typeNum = Integer.valueOf(typeStr);
                wiegandNo = Integer.valueOf(wieganStr);
                int ret = FaceSdk.getInstance().addPerson(idStr, nameStr, typeNum, wiegandNo, thumb, defaultTime);
                if (ret != 0) {
                    Toast.makeText(getApplicationContext(), "注册错误，错误码：" + ret, Toast.LENGTH_SHORT).show();
                    LogUtils.e("注册错误，错误码：" + ret);
                } else {
                    Toast.makeText(getApplicationContext(), "注册成功" + ret, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && null != data) {
            Uri selectedImage = data.getData();
            mFaceBmp = null;
            if (selectedImage != null) {
                mFaceBmp = getBitmapFormUri(getContentResolver(), selectedImage);
                //upload(mFaceBmp);
                //imageView.setImageBitmap(mFaceBmp);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                mFaceBmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] thumb = baos.toByteArray();

                //另外一种注册方式，先检测图片再注册
                FaceSdk.getInstance().haveFace(thumb,
                        new FaceSdk.HaveFaceCallBack() {
                            @Override
                            public void onFaceSuccess(byte[] faceImg, byte[] twistImg) {
                                LogUtils.d("有人脸");
                                imageView.setImageBitmap(BitmapFactory.decodeByteArray(faceImg, 0, faceImg.length));
                                //注册
                                Calendar oldCal = Calendar.getInstance(TimeZone.getTimeZone("UTC-8:00"));
                                oldCal.set(1970, 0, 1, 0, 0, 0);
//                            int ret = FaceSdk.getInstance().addPersonPacket("11","hello",1,0,faceImg,twistImg);
                            }

                            @Override
                            public void onFaceFaild(int errorcode) {
                                LogUtils.d("没有人脸");
                                Toast.makeText(getApplicationContext(),
                                        "检测没有人脸,错误码：" + errorcode, Toast.LENGTH_SHORT).show();
                            }
                        });


            }

                /*Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                final String picturePath = cursor.getString(columnIndex);
                upload(picturePath);
                cursor.close();*/
        }
    }

    private Bitmap getBitmapFormUri(ContentResolver contentResolver, Uri uri) {
        try {
            InputStream input = contentResolver.openInputStream(uri);
            BitmapFactory.Options onlyBoundsOptions = new BitmapFactory.Options();
            onlyBoundsOptions.inJustDecodeBounds = true;
            onlyBoundsOptions.inDither = true;//optional
            onlyBoundsOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
            BitmapFactory.decodeStream(input, null, onlyBoundsOptions);
            input.close();
            int originalWidth = onlyBoundsOptions.outWidth;
            int originalHeight = onlyBoundsOptions.outHeight;
            if ((originalWidth == -1) || (originalHeight == -1))
                return null;
            //图片分辨率以480x800为标准
            float hh = 800f;//这里设置高度为800f
            float ww = 480f;//这里设置宽度为480f
            //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
            int be = 1;//be=1表示不缩放
            if (originalWidth > originalHeight && originalWidth > ww) {//如果宽度大的话根据宽度固定大小缩放
                be = (int) (originalWidth / ww);
            } else if (originalWidth < originalHeight && originalHeight > hh) {//如果高度高的话根据宽度固定大小缩放
                be = (int) (originalHeight / hh);
            }
            if (be <= 0)
                be = 1;
            //比例压缩
            BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
            bitmapOptions.inSampleSize = be;//设置缩放比例
            bitmapOptions.inDither = true;//optional
            bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;//optional
            input = contentResolver.openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(input, null, bitmapOptions);
            input.close();

            return compressImage(bitmap);//再进行质量压缩
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 1000) {  //循环判断如果压缩后图片是否大于1000kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            //第一个参数 ：图片格式 ，第二个参数： 图片质量，100为最高，0为最差  ，第三个参数：保存压缩后的数据的流
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        return BitmapFactory.decodeStream(isBm, null, null);
    }


    private void showDialog() {
        dialog = new ProgressDialog(AddPersionActivity.this);
        dialog.setMessage("上传中...");
        dialog.show();
    }
}
