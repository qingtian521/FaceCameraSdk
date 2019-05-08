package sdk.facecamera.demo.adapter;

import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import sdk.facecamera.demo.R;
import sdk.facecamera.demo.util.LogUtils;
import sdk.facecamera.demo.util.UiUtil;
import sdk.facecamera.sdk.pojos.QueryFaceModel;

/**
 * Created by 云中双月 on 2018/4/2.
 */

public class PeopleRecyAdapter extends BaseRecycleAdapter {
    private TextView id,name,type,num,time;
    private ImageView headView;

    public PeopleRecyAdapter(List mList, int headCount, int footCount) {
        super(mList, headCount, footCount);
    }

    @Override
    public void bindData(BaseViewHolder holder, int position, List mList) {
        List<QueryFaceModel> faceList = (List<QueryFaceModel>) mList;
        headView = (ImageView) holder.getView(R.id.iv_people_head);
        headView.setImageBitmap(UiUtil.Bytes2Bimap(faceList.get(position).getImageData()[0]));
        id = (TextView) holder.getView(R.id.tv_people_id);
        name = (TextView) holder.getView(R.id.tv_people_name);
        type = (TextView) holder.getView(R.id.tv_people_type);
        num = (TextView) holder.getView(R.id.tv_people_num);
        time = (TextView) holder.getView(R.id.tv_people_time);
        //时间戳*1000   不然是1970
//        LogUtils.i(faceList.get(position).getExpireDate()+"");
        if(faceList.size()<=0) return;
        Date date = new Date(faceList.get(position).getExpireDate()*1000);
        String timeStr = UiUtil.dateToString(date,"yyyy年MM月dd日 HH时mm分ss秒");
        String typeStr;
        if (faceList.get(position).getRole() == 0){
            typeStr = "普通人员";
        }else if (faceList.get(position).getRole() == 1){
            typeStr = "白名单人员";
        }else {
            typeStr = "黑名单人员";
        }
        id.setText("ID："+faceList.get(position).getId());
        name.setText("姓名："+faceList.get(position).getName());
        type.setText("类型"+typeStr);
        num.setText("门禁卡号："+faceList.get(position).getWiegandNo());
        time.setText("到期时间："+timeStr);
        //
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_people;
    }

    @Override
    public int getHeadLayoutId() {
        return 0;
    }
}

