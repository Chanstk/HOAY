package com.example.lenovo.hoay;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;


import android.widget.ImageButton;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class album extends AppCompatActivity {
    private GridView gview;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter sim_adapter;
    private int[] icon = { R.drawable.touxiang1, R.drawable.touxiang2,
            R.drawable.touxiang3, R.drawable.touxiang4 ,R.drawable.touxiang1, R.drawable.touxiang2,
            R.drawable.touxiang3, R.drawable.touxiang4  };
    private String[] iconName = { "包子", "豆沙", "娜娜", "凹凸曼","包子", "豆沙", "娜娜", "凹凸曼" };
    private ImageButton im;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        gview = (GridView) findViewById(R.id.gridview);
        //新建List
        data_list = new ArrayList<Map<String, Object>>();
        //获取数据
        getData();
        //新建适配器
        String [] from ={"image","text"};
        int [] to = {R.id.image,R.id.button5};
        sim_adapter = new SimpleAdapter(this, data_list, R.layout.activity_item, from, to);
        //配置适配器
        gview.setAdapter(sim_adapter);
        im=(ImageButton) findViewById(R.id.bang);
        im.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(album.this, piece.class);
                startActivity(intent);
            }
        });


    }


    public List<Map<String, Object>> getData(){
        //cion和iconName的长度是相同的，这里任选其一都可以
        for(int i=0;i<icon.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", icon[i]);
            map.put("text", iconName[i]);
            data_list.add(map);
        }

        return data_list;
    }

}
