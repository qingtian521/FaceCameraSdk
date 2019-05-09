package sdk.facecamera.demo;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;
import java.util.List;

import sdk.facecamera.demo.adapter.ViewPagerAdapter;
import sdk.facecamera.demo.crash.BaseActivity;
import sdk.facecamera.demo.fragment.BaseFragment;
import sdk.facecamera.demo.fragment.FragmentFactory;
import sdk.facecamera.demo.fragment.HomeFragment;
import sdk.facecamera.demo.util.LogUtils;
import sdk.facecamera.demo.util.UiUtil;
import sdk.facecamera.sdk.FaceSdk;

public class MainActivity extends BaseActivity {

    private BottomNavigationBar bnBar;
    private FragmentManager mFragmentManager;
    private ViewPager viewPager;
    private List<Fragment> mFragments = new ArrayList<>();
    private ViewPagerAdapter mPagerAdapter;
    private String ip;
    private String port;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        ip = intent.getStringExtra("ip");
        port = intent.getStringExtra("port");
        username = "admin";
        password = "admin";

        //添加fragment
        addFragment();
        mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),mFragments);
        viewPager.setAdapter(mPagerAdapter);
        viewPager.setOffscreenPageLimit(4);
        boolean ret = FaceSdk.getInstance().Initialize(MainActivity.this,ip);
        if (ret){
//                    Toast.makeText(getApplicationContext(),"初始化成功",Toast.LENGTH_SHORT).show();
        }else {
//                    Toast.makeText(getApplicationContext(),"初始化失败",Toast.LENGTH_SHORT).show();
        }
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }).start();

        FaceSdk.getInstance().setConnectCallBack(new FaceSdk.ConnectCallBack() {
            @Override
            public void onConnected(String ip, short port, int usrParam) {
                LogUtils.d("连接成功");
            }

            @Override
            public void onDisConnected(String ip, short port, int usrParam) {
                LogUtils.d("与服务端未建立有效连接");
            }
        });
    }

    private void initView() {
        bnBar = findViewById(R.id.bottom_bar);
        viewPager = findViewById(R.id.vp_main);
        bnBar.setMode(BottomNavigationBar.MODE_FIXED);
        bnBar.setBarBackgroundColor(R.color.white);//设置bar背景颜色
        bnBar.setActiveColor(R.color.colorPrimaryDark);//设置被选中时的颜色
        bnBar.setInActiveColor(R.color.black);//设置未被选中时的颜色

//        bnBar.setVisibility(View.GONE);
        //将item添加到bnBar中
        bnBar.addItem(new BottomNavigationItem(R.drawable.ic_home,"首页"))
//                .addItem(new BottomNavigationItem(R.drawable.ic_compare,"人脸对比参数"))
//                .addItem(new BottomNavigationItem(R.drawable.ic_camera,"相机参数"))
                .addItem(new BottomNavigationItem(R.drawable.ic_people,"人员管理"))
                .initialise();//确认布局

        bnBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                /*BaseFragment fragment;
                mFragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = mFragmentManager.beginTransaction();
                fragment = FragmentFactory.createFragment(position);*/
                viewPager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(int position) {
                /*//未选中的要隐藏
                mFragmentManager
                        .beginTransaction()
                        .hide(FragmentFactory.createFragment(position))
                        .commit();*/
            }

            @Override
            public void onTabReselected(int position) {

            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bnBar.selectTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addFragment(){
        BaseFragment homeFragment =
                FragmentFactory.createFragment(0);
        Bundle bundle = new Bundle();
        bundle.putString("ip",ip);
        bundle.putString("port",port);
        bundle.putString("username",username);
        bundle.putString("password",password);
        homeFragment.setArguments(bundle);
        mFragments.add(homeFragment);

//        BaseFragment compareFragment =
//                FragmentFactory.createFragment(1);
//        mFragments.add(compareFragment);
//
//        BaseFragment infoFragment =
//                FragmentFactory.createFragment(2);
//        mFragments.add(infoFragment);

        BaseFragment peopleFragment =
                FragmentFactory.createFragment(3);
        mFragments.add(peopleFragment);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FaceSdk.getInstance().UnInitialize();
    }
}
