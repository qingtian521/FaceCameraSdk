package sdk.facecamera.demo.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import sdk.facecamera.demo.R;
import sdk.facecamera.demo.activity.AddPersionActivity;
import sdk.facecamera.demo.adapter.BaseRecycleAdapter;
import sdk.facecamera.demo.adapter.PeopleRecyAdapter;
import sdk.facecamera.demo.util.UiUtil;
import sdk.facecamera.sdk.FaceSdk;
import sdk.facecamera.sdk.pojos.QueryFaceModel;


/**
 * Created by 云中双月 on 2018/3/30.
 * 人员管理
 */

public class PeopleManagerFragment extends BaseFragment {

    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private PeopleRecyAdapter mAdapter;
    private int mTotalNum;
    private int mPageNo = 1;
    private int mRole = -1;
    private int mPageSize = 15;
    private int checkedItem = 0; //默认选中所有人
    private List<QueryFaceModel> dataLists = new ArrayList<>();
    @Override
    public View initView() {
        View view = View.inflate(getActivity(),
                R.layout.fragment_people_manager, null);
        refreshLayout = view.findViewById(R.id.swip_people);
        recyclerView = view.findViewById(R.id.recy_people);
        refreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimaryDark);
        refreshLayout.setRefreshing(true);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(1, mRole, mPageSize);
            }
        });
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void initData() {
        mAdapter = new PeopleRecyAdapter(dataLists,0,0);
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(mAdapter);
//        mCriteria = new ListFaceCriteria();
        //mResult = new ListFaceResult();
        getData(mPageNo, mRole, mPageSize);
        FaceSdk.getInstance().setQueryPageCallBack(new FaceSdk.QueryPageCallBack() {
            @Override
            public void onQueryPageCallBack(boolean success, List<QueryFaceModel> modelList) {
                dataLists.clear();
                Log.i("aaaaaaaaa","1111111111111111111111");
                dataLists.addAll(modelList);
                mAdapter.notifyDataSetChanged();
            }
        });
        mAdapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View View, final int position) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity())
                        .setTitle("确定删除 "+dataLists.get(position).getName() + " ？？")
                        .setNegativeButton("删除", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                int ret = FaceSdk.getInstance().delPersonById(dataLists.get(position).getId());
                                if(ret == 0){ //删除成功
                                    dataLists.remove(position);
                                    mAdapter.notifyDataSetChanged();
                                }
                            }
                        })
                        .setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {

                            }
                        });
                dialog.create().show();
            }
        });
//        FaceSdk.getInstance().setQueryCallBack(new FaceSdk.QueryCallBack() {
//            @Override
//            public void onQueryCallBack(boolean success, QueryFaceModel model) {
//                dataLists.add(model);
//            }
//        });

//        mAdapter.setRefreshListener(new BaseRecycleAdapter.onLoadmoreListener() {
//            @Override
//            public void onLoadMore() {
//
//            }
//        });
    }

    /**
     * @param pageNo   从第几页开始加载
     * @param role     加载类型  -1 所有人 0：普通人员。 1：白名单人员。 2：黑名单人员
     * @param pageSize 每次加载多少个
     */
    private void getData(int pageNo, int role, int pageSize) {
        FaceSdk.getInstance().getPersonList(pageNo, role, pageSize);
        //mAdapter.notifyDataSetChanged();
        refreshLayout.setRefreshing(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.people_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.single_add:
                Intent intent = new Intent(getActivity(),
                        AddPersionActivity.class);
                startActivity(intent);
                break;
            case R.id.batch_add:
                break;
            case R.id.people_screen:
                selectScreen();
                break;
            case R.id.people_manager:
                break;
            case R.id.remove_all:
                new AlertDialog.Builder(getActivity()).setTitle("确定删除所有人员？？").setNegativeButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int ret = FaceSdk.getInstance().delAllPerson();
                        if(ret == 0){
                            //清除成功
                            dataLists.clear();
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }).create().show();
                break;
        }
        return true;
    }

    private void selectScreen() {
        String[] items = {"所有人员", "普通人员", "白名单人员", "黑名单人员"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                checkedItem = i;
                if (checkedItem == 0) {
                    mRole = -1;
                } else if (checkedItem == 1) {
                    mRole = 0;
                } else if (checkedItem == 2) {
                    mRole = 1;
                } else if (checkedItem == 3) {
                    mRole = 2;
                }
                dialogInterface.dismiss();

                refreshLayout.setRefreshing(true);
                getData(1, mRole, mPageSize);
            }
        });
        builder.show();
    }
}
