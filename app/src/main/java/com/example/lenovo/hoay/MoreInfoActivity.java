package com.example.lenovo.hoay;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.*;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.okhttp.internal.framed.FrameReader;
import com.example.lenovo.hoay.bean.DestinyBean;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;

public class MoreInfoActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn1 = null;
    String id;
    private Handler handler ;
    private ImageView imageView;
    private TextView nickName, gender, birthday, area, statement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moreinfo);
        id = getIntent().getStringExtra("id");
        Log.i("hello", id);
        imageView = (ImageView) findViewById(R.id.userphoto1);
        nickName = (TextView) findViewById(R.id.nana);
        gender = (TextView) findViewById(R.id.info3);
        birthday = (TextView) findViewById(R.id.info5);
        area = (TextView) findViewById(R.id.info6);
        statement = (TextView) findViewById(R.id.info8);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        Bundle bundle = msg.getData();
                        if(bundle.getString("pic") == null){

                        }
                        else
                        Picasso.with(getBaseContext())
                                .load(bundle.getString("pic"))
                                .into(imageView);
                        nickName.setText(bundle.getString("nickName"));
                        gender.setText(bundle.getString("gender"));
                        birthday.setText(bundle.getString("birthday"));
                        area.setText(bundle.getString("area"));
                        statement.setText(bundle.getString("statement"));
                        break;
                    case 2:
                        break;

                }
            }
        };
        GetData();
        btn1 = (Button)findViewById(R.id.fanhui2) ;
        btn1.setOnClickListener(this);
    }
    void GetData(){
        AVQuery<AVObject> query = new AVQuery<>("_User");
        query.whereEqualTo("username", id);
        query.selectKeys(Arrays.asList("nickName","id","pic","gender","birthday","area","statement"));
        query.limit(1);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                Message msg = new Message();
                msg.what = 1;
                Bundle bundle = new Bundle();
                for(AVObject bean:list){
                    AVFile avFile = bean.getAVFile("pic");
                    String url = null;
                    if(avFile != null) {
                        url = avFile.getUrl();
                    }
                    bundle.putString("pic",url);
                    bundle.putString("nickName", bean.get("nickName").toString());
                    bundle.putString("birthday", bean.get("birthday").toString());
                    bundle.putString("gender", bean.get("gender").toString());
                    bundle.putString("area", bean.get("area").toString());
                    bundle.putString("statement", bean.get("statement").toString());
                }
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        });
    }
    public void onClick(View v){
        int id = v.getId();
        switch(id){
            case R.id.fanhui2:
                finish();
                break;
            default:
                break;
        }
    }
}
