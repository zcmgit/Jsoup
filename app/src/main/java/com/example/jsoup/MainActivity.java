package com.example.jsoup;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List<FoodBean> foodBeans = new ArrayList<>();

    private RecyclerView recyclerView;
    private FoodAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        adapter = new FoodAdapter(foodBeans);
        //在SearchInfoFragment中刷新按钮状态
        recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 2));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new FoodAdapter.onClickItem() {
            @Override
            public void onClickItem(int position) {
                Intent intent = new Intent(MainActivity.this,WebActivity.class);
                intent.putExtra("url",foodBeans.get(position).getUrl());
                startActivity(intent);
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                foodBeans = getDatas();
                for (int i = 0; i < foodBeans.size(); i++) {
                    FoodBean foodBean = foodBeans.get(i);
                    Log.d("jsoup==", i + "--" + foodBean.getName() + " " + foodBean.getPic_url()
                            + "  " + foodBean.getUrl() + "  " + foodBean.getMiaoshu());
                }
                Message message = new Message();
                message.what = 0;
                message.obj = foodBeans;
                showHandler.sendMessage(message);
            }
        }).start();
    }


    Handler showHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    List<FoodBean> foodBeans = new ArrayList<>();
                    foodBeans = (List<FoodBean>) msg.obj;
                    adapter.setDatas(foodBeans);
                    break;
            }
        }
    };

    private List<FoodBean> getDatas() {
        List<FoodBean> foodBeans = new ArrayList<>();
        try {
            Document doc = Jsoup.connect("https://home.meishichina.com/show-top-type-recipe-order-star.html").get();

            Elements picDoc = doc.select("div.pic");

            Elements detail = doc.select("div.detail").select("h2").select("a");

            Elements subcontent = doc.select("p.subcontent");

            for (int i = 0; i < picDoc.size(); i++) {
                FoodBean bean = new FoodBean();
                //attr:获取属性
                String picUrl = picDoc.get(i).select("a").select("img").attr("data-src");
                bean.setPic_url(picUrl);
                //text:获取文本
                bean.setName(detail.get(i).text());
                bean.setUrl(detail.get(i).attr("href"));
                bean.setMiaoshu(subcontent.get(i).text());
                foodBeans.add(bean);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return foodBeans;
    }


}
