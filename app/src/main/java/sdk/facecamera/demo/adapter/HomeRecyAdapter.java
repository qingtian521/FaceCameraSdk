package sdk.facecamera.demo.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import sdk.facecamera.demo.R;
import sdk.facecamera.demo.model.PersonModel;
import sdk.facecamera.demo.util.UiUtil;

/**
 * Created by 云中双月 on 2018/3/30.
 */

public class HomeRecyAdapter extends RecyclerView.Adapter{
    private List<PersonModel> modelList;

    public HomeRecyAdapter(List<PersonModel> models){
        modelList = models;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_home,null);

        HomeHolder holder = new HomeHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //是否对比出结果了
        if (modelList.get(position).isResult()){
            ((HomeHolder) holder).name.setText("姓名："+modelList.get(position).getName());
            ((HomeHolder) holder).age.setText("年龄："+modelList.get(position).getAge());

            if (modelList.get(position).getSex() == 1){
                ((HomeHolder) holder).sex.setText("性别：男");
            }else {
                ((HomeHolder) holder).sex.setText("性别：女");
            }

            ((HomeHolder) holder).result.setText("对比成功!");
        }else {
            ((HomeHolder) holder).result.setText("对比失败!");
            ((HomeHolder) holder).sex.setText("性别：");
            ((HomeHolder) holder).name.setText("姓名：");
            ((HomeHolder) holder).age.setText("年龄：");

        }
        if (modelList.get(position).getImg() != null){
            Bitmap bitmap = UiUtil.Bytes2Bimap(modelList.get(position).getImg());
            ((HomeHolder) holder).head.setImageBitmap(bitmap);
        }

    }

    @Override
    public int getItemCount() {
        return modelList == null?0:modelList.size();
    }

    private class HomeHolder extends RecyclerView.ViewHolder{
        TextView name,sex,age,result;
        ImageView head;
        public HomeHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.tv_username);
            sex = itemView.findViewById(R.id.tv_sex);
            age = itemView.findViewById(R.id.tv_age);
            result = itemView.findViewById(R.id.tv_result);
            head = itemView.findViewById(R.id.iv_head);;
        }

    }


}
