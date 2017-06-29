package com.example.lenovo.hoay;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.*;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.GetDataCallback;
import com.example.lenovo.hoay.bean.DestinyBean;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends Activity implements View.OnClickListener {
    //ActionBar actionBar;
    Button btn1 = null;
    Button btn2 = null;
    Button btn3 = null;
    Button btn4 = null;
    private List<DestinyBean> dataList ;
    private listAdapter adapter;
    private ListView DestinyList = null;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        adapter = new listAdapter(dataList);
                        DestinyList.setAdapter(adapter);
                        DestinyList.invalidate();
                        break;
                    case 2:
                        break;

                }
            }
        };

        GetInfoList();
        DestinyList = (ListView) findViewById(R.id.DestinyList);
        DestinyList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("id", dataList.get(position).id);
                bundle.putString("nickName", dataList.get(position).nickname);
                bundle.putString("photo", dataList.get(position).photo);
                intent.putExtras(bundle);
                intent.setClass(getBaseContext(),infoActivity.class);
                startActivity(intent);
            }
        });
        btn1 = (Button)findViewById(R.id.youyuantouming);
        btn2 = (Button)findViewById(R.id.nieyuantouming);
        btn3 = (Button)findViewById(R.id.guanzhutouming);
        btn4 = (Button)findViewById(R.id.haoyoutouming);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
    }
    public void onClick(View v) {
        int id=v.getId();
        switch(id){
            case R.id.youyuantouming:
                btn1.setVisibility(View.INVISIBLE);btn2.setVisibility(View.VISIBLE);
                btn3.setVisibility(View.VISIBLE);btn4.setVisibility(View.VISIBLE);
                break;
            case R.id.nieyuantouming:
                btn2.setVisibility(View.INVISIBLE);btn3.setVisibility(View.VISIBLE);
                btn4.setVisibility(View.VISIBLE);btn1.setVisibility(View.VISIBLE);
                break;
            case R.id.guanzhutouming:
                btn3.setVisibility(View.INVISIBLE);btn4.setVisibility(View.VISIBLE);
                btn1.setVisibility(View.VISIBLE);btn2.setVisibility(View.VISIBLE);
                break;
            case R.id.haoyoutouming:
                btn4.setVisibility(View.INVISIBLE);btn1.setVisibility(View.VISIBLE);
                btn2.setVisibility(View.VISIBLE);btn3.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }

    }

    void GetInfoList(){
        dataList = new ArrayList<DestinyBean>();
        AVQuery<AVObject> query = new AVQuery<>("_User");
        query.selectKeys(Arrays.asList("nickName", "username","pic"));
        query.whereNotEqualTo("username", AVUser.getCurrentUser().get("username").toString());
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if(e ==null) {
                    for (AVObject bean : list) {
                        AVFile avFile = bean.getAVFile("pic");
                        String url = null;
                        if (avFile != null)
                            url = avFile.getUrl();
                        DestinyBean Dbean = new DestinyBean(bean.get("nickName").toString(), url, bean.get("username").toString(), (int)(Math.random() * 100), (int)(Math.random() * 100));
                        dataList.add(Dbean);
                    }
                    Message me = new Message();
                    me.what = 1;
                    handler.sendMessage(me);
                }else
                    e.printStackTrace();
            }
        });
    }

    protected class listAdapter extends BaseAdapter {
        private List<DestinyBean> list;
        public listAdapter(List<DestinyBean> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            ViewHolder holder;
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.destinylist, null);
                holder = new ViewHolder();
                holder.id = (TextView) view.findViewById(R.id.DestinyInfo);
                holder.destiny = (ProgressBar) view.findViewById(R.id.youyuanvalue);
                holder.undestiny = (ProgressBar) view.findViewById(R.id.nieyuanvalue);
                holder.nickName = (TextView) view.findViewById(R.id.username);
                holder.photo = (ImageView) view.findViewById(R.id.userimage);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            DestinyBean bean = list.get(position);
            if (bean != null) {
                holder.nickName.setText(bean.nickname);
                holder.destiny.setProgress(bean.destiny);
                holder.undestiny.setProgress(bean.undestiny);
                holder.id.setText(bean.id);
                if(bean.photo == null){

                }
                else {
                    Picasso.with(view.getContext())
                            .load(bean.photo)
                           /* .resize(30, 30)
                            .centerCrop()*/
                            .into(holder.photo);
                }
            }
            return view;
        }
    }
    static class ViewHolder {
        TextView id,  nickName;
        ProgressBar destiny, undestiny;
        ImageView photo;
    }
}

