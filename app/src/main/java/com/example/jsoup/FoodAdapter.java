package com.example.jsoup;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zcm
 * @create 2018/10/31
 * @Describe
 */
public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.MyHolder>{

    private Context context;
    private onClickItem onItemClickListener;
    List<FoodBean> foodBeans = new ArrayList<>();
    public FoodAdapter(List<FoodBean> foodBeans){
        this.foodBeans = foodBeans;
    }

    public void setDatas(List<FoodBean> foodBeans){
        this.foodBeans = foodBeans;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        MyHolder holder = new MyHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item,viewGroup,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder myHolder, final int i) {
        myHolder.miaoshu.setText(foodBeans.get(i).getMiaoshu());
        myHolder.name.setText(foodBeans.get(i).getName());
        Glide.with( this.context ).load( foodBeans.get(i).getPic_url()).into( myHolder.pic );
        myHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClickItem(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodBeans.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView pic;
        private TextView miaoshu;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            pic = itemView.findViewById(R.id.pic);
            miaoshu = itemView.findViewById(R.id.miaoshu);
        }
    }

    public interface onClickItem{
        void onClickItem(int position);
    }
    public void setOnItemClickListener(onClickItem onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
}
