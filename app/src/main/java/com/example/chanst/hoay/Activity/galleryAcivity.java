package com.example.chanst.hoay.Activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.chanst.hoay.Bean.galleryBean;
import com.example.chanst.hoay.R;

import java.util.ArrayList;
import java.util.List;

public class GalleryAcivity extends AppCompatActivity {
    private GridView gridView;
    private Drawable p1, p2, p3;
    private listAdapter adapter;
    private List<galleryBean> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        initView();
    }

    public void initView(){
        gridView = (GridView) findViewById(R.id.gridview);
        initData();
        adapter = new listAdapter(data);
        gridView.setAdapter(adapter);
    }

    public void initData(){
        p1 = getResources().getDrawable(R.drawable.p1);
        p2 = getResources().getDrawable(R.drawable.p2);
        p3 = getResources().getDrawable(R.drawable.p3);
        data = new ArrayList<galleryBean>();
        data.add(new galleryBean(p3));
        data.add(new galleryBean(p3));
        data.add(new galleryBean(p3));
        data.add(new galleryBean(p3));
        data.add(new galleryBean(p3));
        data.add(new galleryBean(p3));
        data.add(new galleryBean(p3));
        data.add(new galleryBean(p3));
        data.add(new galleryBean(p3));
        data.add(new galleryBean(p3));
        data.add(new galleryBean(p3));
        data.add(new galleryBean(p3));
    }
    protected class listAdapter extends BaseAdapter {
        private List<galleryBean> list;
        public listAdapter(List<galleryBean> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            ViewHolder holder;
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.grid_view_item, null);
                holder = new ViewHolder();
                holder.photo = (ImageView) view.findViewById(R.id.photoSeq);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            galleryBean bean = list.get(position);
            if (bean != null) {
                holder.photo.setImageDrawable(bean.photo);
            }
            return view;
        }
    }
    static class ViewHolder {
        ImageView photo;
    }
}
