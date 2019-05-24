package sdk.facecamera.demo.fragment;

import android.content.Intent;
import android.media.MediaCodec;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import sdk.facecamera.demo.R;
import sdk.facecamera.demo.activity.AddPersionActivity;
import sdk.facecamera.demo.activity.QueryActivity;
import sdk.facecamera.demo.adapter.HomeRecyAdapter;
import sdk.facecamera.demo.model.PersonModel;
import sdk.facecamera.demo.util.LibVLCManager;
import sdk.facecamera.demo.util.LogUtils;
import sdk.facecamera.demo.util.UiUtil;
import sdk.facecamera.sdk.FaceSdk;
import sdk.facecamera.sdk.pojos.FaceInfo;

/**
 * Created by 云中双月 on 2018/3/30.
 */

public class HomeFragment extends BaseFragment{


    private SurfaceView mSurfaceView;
    private RecyclerView mRecyclerView;
    private String ip;
    private String port;
    private String username;
    private String password;
    private List<PersonModel> personList = new ArrayList<>();
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            recyAdapter.notifyDataSetChanged();
            mRecyclerView.smoothScrollToPosition(personList.size()-1);
        }
    };
    private HomeRecyAdapter recyAdapter;
    private MediaCodec mCodec;
    private boolean isStart; //标志位，判断是否在播放
    private SurfaceHolder holder;
    private MyCallback callback;
    private LibVLCManager mVlcManager;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        if(isVisibleToUser && holder != null) {
//            FaceSdk.getInstance().startVideoPlay(holder);
//        }else {
//            FaceSdk.getInstance().stopVideoPlay();
//        }
    }

    @Override
    public View initView() {
        View view = View.inflate(getActivity(), R.layout.fragment_home,null);
        mSurfaceView = view.findViewById(R.id.sueface_view);
        mRecyclerView = view.findViewById(R.id.recy_view);
        setHasOptionsMenu(true);
        //vlc在部分手机上无法播放
        //mVlcManager = new LibVLCManager();
        //mVlcManager.initPlayer("192.168.0.216", mSurfaceView);
        return view;
    }

    @Override
    public void initData() {
        Bundle bundle = getArguments();
        ip = bundle.getString("ip");
        port = bundle.getString("port");
        username = bundle.getString("username");
        password = bundle.getString("password");

        holder = mSurfaceView.getHolder();
        callback = new MyCallback();
        holder.addCallback(callback);
        recyAdapter = new HomeRecyAdapter(personList);
        final LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(recyAdapter);

        FaceSdk.getInstance().setFaceInfoCallBack(new FaceSdk.FaceInfoCallBack() {
            @Override
            public void onFaceInfoResult(boolean inside, FaceInfo info, String errMsg) {
                PersonModel model = new PersonModel();
                if (inside){
                    model.setResult(true);
                    //如果匹配得到设备库中的人员
                    model.setName(info.getName());
                    model.setAge(info.getAge());
                    model.setImg(info.getCaptureImageData());
                    model.setSex(info.getSex());
                    model.setFaceAngle(info.getFaceAngle());
                    model.setFaceAngleFlat(info.getFaceAngleFlat());
                }else {
                    //匹配不到  但是数据可能不为空
                    model.setResult(false);
                    if (info.getName() != null){
                        model.setName(info.getName());
                    }
                    model.setAge(info.getAge());
                    model.setSex(info.getSex());
                    model.setImg(info.getCaptureImageData());
                    model.setFaceAngle(info.getFaceAngle());
                    model.setFaceAngleFlat(info.getFaceAngleFlat());
                }
                personList.add(model);
                //通知更新
                mHandler.sendEmptyMessage(0);
            }
        });



    }

    /*@Override
    public void onResume() {
        super.onResume();
        mVlcManager.startPlay();
    }

    @Override
    public void onPause() {
        super.onPause();
        mVlcManager.pausePlay();
    }*/

    /**
     * 由于surface可能被销毁，它只在SurfaceHolder.Callback.surfaceCreated()
     * 和 SurfaceHolder.Callback.surfaceDestroyed()之间有效，
     * 所以要确保渲染线程访问的是合法有效的surface。
     */
    private class MyCallback implements SurfaceHolder.Callback {
        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            FaceSdk.getInstance().startVideoPlay(surfaceHolder);
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int width, int height) {
            /*ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(width,height);
            float scale = (float) frameHeight / frameWidth;
            layoutParams.width = width;
            float scaleHeight = width * 1.0f / scale;
            layoutParams.height = (int) scaleHeight;
            setLayoutParams(layoutParams);*/
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            FaceSdk.getInstance().stopVideoPlay();

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (callback != null){
            holder.removeCallback(callback);
        }
        //mVlcManager.releasePlayer();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.people_menu,menu);


    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem item = menu.findItem(R.id.match_switch);
        if (FaceSdk.getInstance().getMatchEnable()){
            item.setTitle("关闭检测");
        }else {
            item.setTitle("开启检测");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.single_add:
                Intent intent = new Intent(getActivity(),
                        AddPersionActivity.class);
                startActivity(intent);
                break;
            case R.id.match_switch:
                //开关检测
                boolean matchSwitch = false;
                if (!FaceSdk.getInstance().getMatchEnable()){
                    //如果是关闭的状态 需要开启
                    matchSwitch = true;
                }
                FaceSdk.getInstance().setMatchEnable(matchSwitch);
                break;
            case R.id.query_by_id:
                Intent intentQuery = new Intent(getActivity(),
                        QueryActivity.class);
                startActivity(intentQuery);
                break;
            case R.id.batch_add:
                break;
            case R.id.people_screen:
                break;
            case R.id.people_manager:
                break;
        }
        return true;
    }
}
