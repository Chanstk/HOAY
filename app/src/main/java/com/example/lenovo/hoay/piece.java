package com.example.lenovo.hoay;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class piece extends AppCompatActivity {
    private ImageButton choose;
    private ImageView close;
    private boolean isVisible = true;
    private LinearLayout layout_1;
    private Button bu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piece);
        layout_1 = (LinearLayout) findViewById(R.id.layout);
        layout_1.setVisibility(View.GONE);
        bu=(Button)findViewById(R.id.button) ;
        close=(ImageView) findViewById(R.id.imageView5);
        choose=(ImageButton) findViewById(R.id.imageButton5);
        choose.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isVisible) {
                    isVisible = false;
                    layout_1.setVisibility(View.VISIBLE);//这一句显示布局LinearLayout区域
                } else {
                    layout_1.setVisibility(View.GONE);//这一句即隐藏布局LinearLayout区域
                    isVisible = true;
                }
            }
        });
        bu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(piece.this, album.class);
                startActivity(intent);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(piece.this, album.class);
                startActivity(intent);
            }
        });
    }


}

