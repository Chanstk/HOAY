package com.example.chanst.hoay.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chanst.hoay.Tools.*;
import com.example.chanst.hoay.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText userName, passWord;
    private TextView login, signUp;
    private static final int  SIGN_UP = 111;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getIntent();
        initView();
    }

    private void initView() {
        userName = (EditText) findViewById(R.id.userName_login_activity);
        passWord = (EditText) findViewById(R.id.passWord_login_activity);
        login = (TextView) findViewById(R.id.login);
        login.setOnClickListener(this);
        signUp = (TextView) findViewById(R.id.to_signUp);
        signUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                String userName_str = userName.getText().toString();
                String userPassWord_str = passWord.getText().toString();
                Login(userName_str, userPassWord_str);
                break;
            case R.id.to_signUp:
                Intent intent = new Intent();
                intent.setClass(this, SignUpAcitivity.class);
                startActivityForResult(intent,SIGN_UP);
                break;
        }
    }
    private void Login(String userName_str, String userPassWord_str){
        //检查用户名密码是否有效
        if (tools.CheckUserNamePassword(userName_str, userPassWord_str) != true){
            Toast.makeText(this,"请输入有效的用户名密码！", Toast.LENGTH_LONG);
            return;
        }
        //检查用户名密码是否正确
        if(tools.tools_Login(userName_str, userPassWord_str)!= true) {
            Toast.makeText(this,"用户名密码无效！", Toast.LENGTH_LONG);
        }else{
            //登录成功，返回主界面
            Intent intent = new Intent();
            this.setResult(RESULT_OK, intent);
            this.finish();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case SIGN_UP:
                //获取注册返回信息
                if (resultCode == RESULT_OK) {
                    this.setResult(RESULT_OK, data);
                    this.finish();
                    break;
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
