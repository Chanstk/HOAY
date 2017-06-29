package com.example.lenovo.hoay;

import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVGeoPoint;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.inner.GeoPoint;
import com.example.lenovo.hoay.Chat.talkList;

public class Main2Activity extends AppCompatActivity {
    private MapView mMapView;
    private double mLatitude;
    private double mLongtitude;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private MyLocationListener mLocationListener;
    private ImageButton toMyLoc, smallLetter;
    private boolean isFirstIn = true;
    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker = null;
    private ImageButton chat, gallery, map, loveness, my;
    private static final int  SMALLLETTER = 173;
    private static final int  MY = 171;

    private ImageButton btn1;
    private ImageButton btn2;
    private Button btn3;
    private ImageButton btn4;
    private Button btn5;
    private Button btn6;
    private ImageButton btn7;
    private ImageButton btn8;
    private Button btn9;
    private ImageView btn10;
    private ImageView btn15;
    private ImageButton btn11;
    private LinearLayout letterbox;
    private LinearLayout message;
    private LinearLayout landmine;
    private LinearLayout bong;
    private ImageButton btn12;
    private ImageButton ChatList;
    private BaiduMap baiduMap;
    private MarkerOptions markerOptions;
    private Marker marker;
    private MapView mapview;
    private String content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main2);
        initView();
        initLocation();
        btn1 = (ImageButton)findViewById(R.id.photo);
        btn2 = (ImageButton)findViewById(R.id.smallLetter);
        btn3 = (Button) findViewById(R.id.quedin);
        btn4 = (ImageButton) findViewById(R.id.bang);
        btn8 = (ImageButton) findViewById(R.id.land);
        btn10 = (ImageView) findViewById(R.id.chacha);
        btn15 = (ImageView) findViewById(R.id.chacha2);
        message = (LinearLayout) findViewById(R.id.message);
        btn6 = (Button) findViewById(R.id.mquedin);
        letterbox = (LinearLayout)findViewById(R.id.send) ;
        landmine = (LinearLayout)findViewById(R.id.landmine) ;
        bong = (LinearLayout)findViewById(R.id.boom) ;
        btn7 = (ImageButton) findViewById(R.id.letter);
        btn11 = (ImageButton) findViewById(R.id.bomb);
        btn9 = (Button) findViewById(R.id.lquedin);
        btn12 = (ImageButton)findViewById(R.id.my);
        mapview = (MapView) findViewById(R.id.bmapView);
        ChatList = (ImageButton) findViewById(R.id.msg);
        ChatList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main2Activity.this , talkList.class);
                startActivity(i);
            }
        });
        btn1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(Main2Activity.this , album.class);
                startActivity(i);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                letterbox.setVisibility(View.VISIBLE);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                letterbox.setVisibility(View.GONE);
                EditText Etext = (EditText)findViewById(R.id.textView3);;
                content = Etext.getText().toString();

                baiduMap = mapview.getMap();
                LatLng latLng = new LatLng(mLatitude,mLongtitude);
                //准备 marker 的图片
                BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.env);
                //准备 marker option 添加 marker 使用
                markerOptions = new MarkerOptions().icon(bitmap).position(latLng);
                marker = (Marker) baiduMap.addOverlay(markerOptions);
                   /*
     * 设置marker点击事件
     */
                BaiduMap.OnMarkerClickListener mMarkerlis=new BaiduMap.OnMarkerClickListener(){

                    @Override
                    public boolean onMarkerClick(Marker marker) {


                        TextView MessageContent = (TextView) findViewById(R.id.textView);

                        MessageContent.setText(content);
                        //将marker所在的经纬度的信息转化成屏幕上的坐标
                        message.setVisibility(View.VISIBLE);
                        TextView MessageName = (TextView) findViewById(R.id.textView2);
                        message.setVisibility(View.VISIBLE);
                        MessageName.setVisibility(View.GONE);
                        return true;
                    }

                };
                baiduMap.setOnMarkerClickListener(mMarkerlis);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(Main2Activity.this , MainActivity.class);
                startActivity(i);
            }
        });

        btn12.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(AVUser.getCurrentUser() == null) {
                    Intent i = new Intent(Main2Activity.this, LoginActivity.class);
                    startActivity(i);
                }else{
                    Intent i = new Intent(Main2Activity.this, MyInfoActivity.class);
                    startActivity(i);
                }

            }
        });
        btn6.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                message.setVisibility(View.GONE);
                TextView MessageContent = (TextView) findViewById(R.id.textView);
                MessageContent.setText("在下工商叶良辰，请多指教。");
                TextView MessageName = (TextView) findViewById(R.id.textView2);
                MessageName.setVisibility(View.VISIBLE);
            }
        });
        btn7.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                message.setVisibility(View.VISIBLE);
            }
        });
        btn8.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                landmine.setVisibility(View.VISIBLE);
            }
        });
        btn9.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                landmine.setVisibility(View.GONE);
            }
        });
        btn15.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                letterbox.setVisibility(View.GONE);
            }
        });
        btn11.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                bong.setVisibility(View.VISIBLE);
            }
        });
        btn10.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                bong.setVisibility(View.GONE);
            }
        });
    }

    private void initLocation() {
        centerToMyLocation();
        mLocationListener = new MyLocationListener();
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(mLocationListener);    //注册监听函数
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");
        option.setIsNeedAddress(true);
        option.setOpenGps(true);
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }
    /*
    初始化界面
     */
    private void initView() {
//        String FL_NAME = "custom_config_0323.txt";
//        String PACKAGE_NAME = "com.example.chanst.hoay";
//        String FL_PATH = "/data" + Environment.getDataDirectory().getAbsolutePath() +"/" + PACKAGE_NAME;
//        String FL = FL_PATH + "/" + FL_NAME;
//        mMapView.setCustomMapStylePath(FL);
        mMapView = (MapView) findViewById(R.id.bmapView);
        mMapView.showZoomControls(false);
        mBaiduMap = mMapView.getMap();
        // 缩放比例
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msu);
//        toMyLoc = (ImageButton) findViewById(R.id.toMyLoc);
//        toMyLoc.setOnClickListener(this);
//        chat = (ImageButton) findViewById(R.id.chat);
//        chat.setOnClickListener(this);
//        gallery = (ImageButton) findViewById(R.id.gallery);
//        gallery.setOnClickListener(this);
//        map = (ImageButton) findViewById(R.id.map);
//        map.setOnClickListener(this);
//        loveness = (ImageButton) findViewById(R.id.loveness);
//        loveness.setOnClickListener(this);
//        my = (ImageButton) findViewById(R.id.my);
//        my.setOnClickListener(this);
//        smallLetter = (ImageButton) findViewById(R.id.smallLetter);
//        smallLetter.setOnClickListener(this);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    /*
    将地图聚焦到用户位置
     */
    private void centerToMyLocation() {
        Log.i("hello", mLatitude + " " + mLongtitude);
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                mCurrentMode, true, mCurrentMarker));
        LatLng latLng = new LatLng(mLatitude, mLongtitude);
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        MapStatusUpdate msun = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msun);
        mBaiduMap.animateMapStatus(msu);
    }
    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (location == null || mMapView == null) {
                Log.i("hello","fail to locate");
                return;
            }

            MyLocationData data = new MyLocationData.Builder()
                    .accuracy(location.getRadius())//
                    .latitude(location.getLatitude())//
                    .longitude(location.getLongitude())//
                    .build();
            mBaiduMap.setMyLocationData(data);
            Log.i("hello","location confirm");
            mLatitude = location.getLatitude();
            mLongtitude = location.getLongitude();
            //记录位置
            if(AVUser.getCurrentUser()!=null) {
                AVObject avb = new AVObject("location");
                AVGeoPoint point = new AVGeoPoint(mLatitude, mLongtitude);
                avb.put("username",AVUser.getCurrentUser().getUsername());
                avb.put("loc",point);
                avb.saveInBackground();
            }
            if (isFirstIn) {
                LatLng latLng = new LatLng(location.getLatitude(),
                        location.getLongitude());
                Log.i("hello", mLatitude + " " + mLongtitude);
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(latLng).zoom(15.0f);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                isFirstIn = false;
            }
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        mBaiduMap.setMyLocationEnabled(true);
        if (!mLocationClient.isStarted()) {
            mLocationClient.start();
        }
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        client.connect();
//        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
//        AppIndex.AppIndexApi.end(client, getIndexApiAction());
//        client.disconnect();
    }
}
