package com.example.chanst.hoay.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chanst.hoay.R;
import com.example.chanst.hoay.Tools.tools;

public class SignUpAcitivity extends AppCompatActivity {
    private EditText userName, passWord;
    private TextView signUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_acitivity);
        getIntent();
        InitView();
    }

    private void InitView() {
        userName = (EditText) findViewById(R.id.userName_signUp_activity);
        passWord = (EditText) findViewById(R.id.passWord_signUp_activity);
        signUp = (TextView) findViewById(R.id.signUp);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName_str = userName.getText().toString();
                String passWord_str = passWord.getText().toString();
                SignUp(userName_str, passWord_str);
            }
        });
    }
    private void SignUp(String userName, String userPassWord){
        //检查用户名密码是否有效
        if (tools.CheckUserNamePassword(userName, userPassWord) != true){
            Toast.makeText(this,"请输入有效的用户名密码！", Toast.LENGTH_LONG);
            return;
        }
        if(tools.tools_SignUp(userName, userPassWord)!=true){
            Toast.makeText(this,"注册失败！", Toast.LENGTH_LONG);
        }else{
            Toast.makeText(this,"注册成功！", Toast.LENGTH_LONG);
            //返回数据
            Intent intent = new Intent();
            this.setResult(RESULT_OK, intent);
            this.finish();
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
