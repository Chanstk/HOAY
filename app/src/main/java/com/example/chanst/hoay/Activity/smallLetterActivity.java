package com.example.chanst.hoay.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chanst.hoay.R;

public class SmallLetterActivity extends AppCompatActivity implements View.OnClickListener {
    private Button confirm;
    private Button cancel;
    private EditText content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_small_letter);
        InitView();
    }
    public void InitView(){
        Intent intent = getIntent();
        content = (EditText) findViewById(R.id.content_SimallLetterAct);
        confirm = (Button) findViewById(R.id.confirm);
        cancel = (Button) findViewById(R.id.cancel);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.confirm:
                String content_str = content.getText().toString();
                if (content_str.equals("")){
                    Toast.makeText(this,"请输入内容！", Toast.LENGTH_SHORT);
                    return;
                }
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putString("content", content_str);
                intent.putExtra("info", bundle);
                setResult(RESULT_OK, intent);
                finish();
                break;
            case R.id.cancel:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
    }
}
