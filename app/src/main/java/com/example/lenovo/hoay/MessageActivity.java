
package com.example.lenovo.hoay;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MessageActivity extends AppCompatActivity {

    private LinearLayout iv_time;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message2);
        iv_time = (LinearLayout) findViewById(R.id.box1);
        iv_time.setOnClickListener(new ivTimeListener());
    }
      class ivTimeListener implements OnClickListener {
            @Override
            public void onClick(View v) {
                Intent intent_ivtime = new Intent();
                intent_ivtime.setClass(MessageActivity.this, liaotianActivity1.class);
                MessageActivity.this.startActivity(intent_ivtime);

            }
        }
}

