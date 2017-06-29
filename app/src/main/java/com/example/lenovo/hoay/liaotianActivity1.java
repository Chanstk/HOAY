package com.example.lenovo.hoay;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.*;


public class liaotianActivity1 extends AppCompatActivity implements View.OnClickListener{
    ImageView iv1 = null;//对象信息
    ImageView iv2 = null;//隐藏的头像
    ImageView iv3 = null;//发送按钮
    TextView  tv = null;//隐藏的对话
    EditText  et = null;//输入框内容
    Button    btn1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liaotian2);
        btn1 = (Button)findViewById(R.id.返回) ;
        iv1 = (ImageView)findViewById(R.id.对象信息);
        iv2 = (ImageView)findViewById(R.id.我头像3);
        iv3 = (ImageView)findViewById(R.id.fasong);
        tv = (TextView)findViewById(R.id.对话5);
        et = (EditText)findViewById(R.id.输入栏);
        btn1.setOnClickListener(this);
        et.setOnClickListener(this);
        iv1.setOnClickListener(this);
        //iv2.setOnClickListener(this);
        iv3.setOnClickListener(this);
        //tv.setOnClickListener(this);
    }

    public void onClick(View v){
        int id = v.getId();
        switch (id){
            case R.id.对象信息:
                Intent it1 = new Intent(liaotianActivity1.this,infoActivity.class);
                startActivity(it1);
                break;
            case R.id.fasong:
                String s = "";
                s = et.getText().toString();
                tv.setText(s);
                iv2.setVisibility(View.VISIBLE);tv.setVisibility(View.VISIBLE);
                break;
            case R.id.返回:
                finish();
                break;
            default:
                break;


        }

    }
}
