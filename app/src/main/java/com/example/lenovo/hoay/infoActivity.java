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
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.example.lenovo.hoay.bean.DestinyBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.leancloud.chatkit.activity.LCIMConversationActivity;
import cn.leancloud.chatkit.utils.LCIMConstants;


public class infoActivity extends AppCompatActivity implements View.OnClickListener{
    Button btn1 = null;
    Button btn2 = null;
    Button btn3 = null;
    private TextView username = null;
    private ImageView userPhoto;
    private String id;
    private TextView Info_name = null;
    private List<Map<String,String>> data_list;
    private SimpleAdapter sim_adapter;
    private String[] MeetTime = {"8:00","9:30"};
    private String[]  MeetThing = {"相遇在大明湖畔,,,","相遇在墨湖湖畔..."};
    private ListView MeetInfoListview = null;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");

        userPhoto = (ImageView) findViewById(R.id.userphoto);
        username = (TextView) findViewById(R.id.username);
        username.setText(bundle.getString("nickName"));
        Picasso.with(getBaseContext())
                .load(bundle.getString("photo"))
                .resize(100,100)
                .centerCrop()
                .into(userPhoto);

        MeetInfoListview = (ListView) findViewById(R.id.MeetInfoListview);
        final List<Map<String, String>> data = new ArrayList<Map<String, String>>();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        MeetInfoListview.setAdapter(new SimpleAdapter(getBaseContext(), data, R.layout.meetinfo,new String[] { "time", "thing" }, new int[] { R.id.MeetInfo_MeetTime,R.id.MeetInfo_MeetInfo }));
                        break;
                    case 2:
                        break;

                }
            }
        };

        AVQuery<AVObject> query = new AVQuery<>("encounter");
        query.whereEqualTo("user1", AVUser.getCurrentUser().get("username").toString());
        query.whereEqualTo("user2", id);
        Log.i("INFO",AVUser.getCurrentUser().get("username").toString());
        Log.i("INFO","f" + id);
        query.selectKeys(Arrays.asList("area", "time"));
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if(e ==null) {
                    Log.i("INFO","hello");
                    for (AVObject bean : list) {
                        Log.i("INFO","hello1");
                        Map<String, String> map1 = new HashMap<String, String>();
                        map1.put("time",bean.get("time").toString());
                        map1.put("thing",bean.get("area").toString());
                        data.add(map1);
                    }
                    Message me = new Message();
                    me.what = 1;
                    handler.sendMessage(me);
                }else
                    e.printStackTrace();
            }
        });

        btn1 = (Button)findViewById(R.id.morebutton);
        btn2 = (Button)findViewById(R.id.fanhui);
        btn3 = (Button)findViewById(R.id.faxiaoxi);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }
    public void onClick(View v) {
        int i =v.getId();
        switch (i)
        {
            case R.id.morebutton:
                Intent it1 = new Intent(infoActivity.this, MoreInfoActivity.class);
                it1.putExtra("id", id);
                startActivity(it1);
                break;
            case R.id.fanhui:
                infoActivity.this.finish();
                break;
            case R.id.faxiaoxi:
                Intent intent = new Intent(this, LCIMConversationActivity.class);
                intent.putExtra(LCIMConstants.PEER_ID, id);
                startActivity(intent);
                break;
            default:
                break;
        }

    }
}


