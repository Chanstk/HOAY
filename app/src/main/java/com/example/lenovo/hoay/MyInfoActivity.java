package com.example.lenovo.hoay;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.GetDataCallback;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.example.lenovo.hoay.Tools.*;

import cn.leancloud.chatkit.LCChatKit;
public class MyInfoActivity extends AppCompatActivity implements View.OnClickListener{
    Button btn_edit = null;
    Button btn_fanhui = null;
    private ImageView Userimage;
    private TextView Username;
    private TextView quit;
    private int birth;
    private Handler handler;
    private String nickName;
    private byte [] pic;
    private Bitmap mBitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        Username.setText(nickName);
                        break;
                    case 2:
                        mBitmap = BitmapFactory.decodeByteArray(pic, 0, pic.length);
                        Userimage.setImageBitmap(mBitmap);
                        break;

                }
            }
        };
        initView();
    }
    GetDataCallback dataCallback = new GetDataCallback() {
        @Override
        public void done(byte[] bytes, AVException e) {
            pic = bytes;
            Message message = new Message();//发送一个消息，该消息用于在handleMessage中区分是谁发过来的消息；
            message.what = 2;
            handler.sendMessage(message);
        }
    };
    private void initView() {
        Userimage = (ImageView) findViewById(R.id.userphoto);
        Username = (TextView) findViewById(R.id.username);
        quit = (TextView) findViewById(R.id.quit);
        quit.setOnClickListener(this);
        btn_edit = (Button)findViewById(R.id.editbutton);
        btn_edit.setOnClickListener(this);
        btn_fanhui = (Button)findViewById(R.id.fanhui);
        btn_fanhui.setOnClickListener(this);
        AVQuery<AVObject> avQuery = new AVQuery<>("_User");
        avQuery.getInBackground(AVUser.getCurrentUser().getObjectId(), new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                nickName = avObject.getString("nickName").toString();
                AVFile avFile = avObject.getAVFile("pic");
                if(avFile!=null)
                    avFile.getDataInBackground(dataCallback);
                Message message = new Message();//发送一个消息，该消息用于在handleMessage中区分是谁发过来的消息；
                message.what = 1;
                handler.sendMessage(message);
            }
        });
    }
    public void onClick(View v){
        int i = v.getId();
        switch (i){
            case R.id.editbutton:
                Intent it1 = new Intent();
                it1.setClass(MyInfoActivity.this, EditInfoActivity.class);
                startActivity(it1);
                break;
            case R.id.fanhui:
                finish();
                break;
            case R.id.quit:
                AVUser.logOut();
                Toast.makeText(getApplicationContext(),"退出成功！",Toast.LENGTH_LONG);
                finish();
            default:
                break;
        }
    }
}
