package com.example.chanst.hoay.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.chanst.hoay.R;

public class SmallLetterActivity extends AppCompatActivity implements View.OnClickListener {
    private Button confirm;
    private Button cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_small_letter);
        InitView();
    }
    public void InitView(){
        Intent intent = getIntent();
        confirm = (Button) findViewById(R.id.confirm);
        cancel = (Button) findViewById(R.id.cancel);
        confirm.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.confirm:
                setResult(RESULT_OK);
                finish();
                break;
            case R.id.cancel:
                setResult(RESULT_CANCELED);
                finish();
                break;
        }
    }
}
