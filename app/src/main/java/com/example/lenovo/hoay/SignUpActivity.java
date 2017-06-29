package com.example.lenovo.hoay;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SignUpCallback;
import com.example.lenovo.hoay.Tools.tools;


public class SignUpActivity extends AppCompatActivity {

    private EditText userName, passWord1,passWord2, veryfiedCode;
    private LinearLayout linLay;
    private TextView signUp, sendVerifiedCode;
    private AVUser user ;
    private Handler handler ;
    private Thread th;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_acitivity);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        Toast.makeText(getApplicationContext(), "已发送验证码", Toast.LENGTH_SHORT).show();
                        signUp.setVisibility(View.VISIBLE);
                        linLay.setVisibility(View.VISIBLE);
                        sendVerifiedCode.setVisibility(View.GONE);
                        break;
                    case 2:
                        Toast.makeText(SignUpActivity.this, "用户名已被占用", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        getIntent();
        InitView();
    }
    /*
    初始化界面
     */
    private void InitView() {
        linLay = (LinearLayout) findViewById(R.id.verifiedCode_lin);
        user = new AVUser();
        userName = (EditText) findViewById(R.id.userName_signUp_activity);
        passWord1 = (EditText) findViewById(R.id.passWord_signUp_activity_1);
        passWord2 = (EditText) findViewById(R.id.passWord_signUp_activity_2);
        signUp = (TextView) findViewById(R.id.signUp);
        sendVerifiedCode = (TextView) findViewById(R.id.sendVerfiedCode);
        veryfiedCode = (EditText) findViewById(R.id.verifiedCode);
        //注册并发送验证码
        sendVerifiedCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userName.getText().toString().matches("[0-9]{11}") == true){
                    if(passWord1.getText().toString().equals(passWord2.getText().toString())){
                        user.setUsername(userName.getText().toString());
                        user.setPassword(passWord1.getText().toString());
                        user.put("mobilePhoneNumber", userName.getText().toString());
                        user.signUpInBackground(new SignUpCallback() {
                            public void done(AVException e) {
                                if (e == null) {
                                    Message message = new Message();//发送一个消息，该消息用于在handleMessage中区分是谁发过来的消息；
                                    message.what = 1;
                                    handler.sendMessage(message);
                                } else {
                                    Message message = new Message();//发送一个消息，该消息用于在handleMessage中区分是谁发过来的消息；
                                    message.what = 2;
                                    handler.sendMessage(message);
                                }
                            }
                        });
                    }else
                        Toast.makeText(SignUpActivity.this, "密码两次输入不一致！", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(SignUpActivity.this, "手机号输入错误！！", Toast.LENGTH_SHORT).show();

            }
        });
        //检查验证码是否有效
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = veryfiedCode.getText().toString();
                if(code.matches("[0-9]+") == true) {
                    AVUser.verifyMobilePhoneInBackground(code, new AVMobilePhoneVerifyCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                Toast.makeText(getApplicationContext(), "注册成功！", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent();
                                setResult(RESULT_OK, intent);
                                finish();
                            } else {
                                Toast.makeText(SignUpActivity.this, "验证码有误", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else
                    Toast.makeText(SignUpActivity.this, "验证码格式有误！", Toast.LENGTH_SHORT);
            }
        });
    }
    /*
    注册
     */
    private void SignUp(String userName, String userPassWord1,String userPassWord2,String veryfiedCode_str){
        //检查用户名密码是否有效
        if (tools.CheckUserNamePassword(userName, userPassWord1) != true){
            Toast.makeText(this,"请输入有效的用户名密码！", Toast.LENGTH_LONG);
            return;
        }
        if(tools.tools_SignUp(userName, userPassWord1)!=true){
            Toast.makeText(this,"注册失败！", Toast.LENGTH_LONG);
        }else{
            Toast.makeText(this,"注册成功！", Toast.LENGTH_LONG);
        }
        user.setUsername(userName);// 设置用户名
        user.setPassword(userPassWord1);// 设置密码
        user.put("mobilePhoneNumber", userName);
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    // 注册成功，把用户对象赋值给当前用户 AVUser.getCurrentUser()
                    //返回数据
                    Toast.makeText(getApplicationContext(), "注册成功！", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    setResult(RESULT_OK, intent);
                    finish();
                } else {
                    // 失败的原因可能有多种，常见的是用户名已经存在。
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
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
