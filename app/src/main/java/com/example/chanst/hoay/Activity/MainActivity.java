package com.example.chanst.hoay.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.chanst.hoay.R;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.io.InputStream;

import static javax.xml.transform.OutputKeys.ENCODING;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private MapView mMapView;
    private GoogleApiClient client;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main);
        initView();
        initLocation();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }
    /*
    百度地图，初始化位置
     */

    private void initLocation() {
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
        toMyLoc = (ImageButton) findViewById(R.id.toMyLoc);
        toMyLoc.setOnClickListener(this);
        chat = (ImageButton) findViewById(R.id.chat);
        chat.setOnClickListener(this);
        gallery = (ImageButton) findViewById(R.id.gallery);
        gallery.setOnClickListener(this);
        map = (ImageButton) findViewById(R.id.map);
        map.setOnClickListener(this);
        loveness = (ImageButton) findViewById(R.id.loveness);
        loveness.setOnClickListener(this);
        my = (ImageButton) findViewById(R.id.my);
        my.setOnClickListener(this);
        smallLetter = (ImageButton) findViewById(R.id.smallLetter);
        smallLetter.setOnClickListener(this);
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
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
                        mCurrentMode, true, mCurrentMarker));
        LatLng latLng = new LatLng(mLatitude, mLongtitude);
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
        MapStatusUpdate msun = MapStatusUpdateFactory.zoomTo(15.0f);
        mBaiduMap.setMapStatus(msun);
        mBaiduMap.animateMapStatus(msu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //转到个人位置
            case R.id.toMyLoc:
                centerToMyLocation();
                break;
            //跳到图鉴activity
            case R.id.gallery:
                Intent intent = new Intent();
                intent.setClass(this, GalleryAcivity.class);
                startActivity(intent);
                break;
            //跳到小纸条activity
            case R.id.smallLetter:
                Intent intentToSmallLetter = new Intent();
                intentToSmallLetter.setClass(this, SmallLetterActivity.class);
                startActivityForResult(intentToSmallLetter,SMALLLETTER);
                break;
            //跳到我的信息activity
            case R.id.my:
                Intent intentToMy = new Intent();
                intentToMy.setClass(this, LoginActivity.class);
                startActivityForResult(intentToMy,MY);
                break;
        }
    }
    //子activity返回至此
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SMALLLETTER:
                if (resultCode == RESULT_OK) {
                    LatLng point = new LatLng(mLatitude - 0.005,
                            mLongtitude - 0.005);
                    //构建Marker图标
                    BitmapDescriptor bitmap = BitmapDescriptorFactory
                            .fromResource(R.drawable.map_env);
                    //Marker 信息
                    Bundle bundle = data.getBundleExtra("info");

                    //构建MarkerOption，用于在地图上添加Marker
                    OverlayOptions option = new MarkerOptions()
                            .position(point)
                            .icon(bitmap).extraInfo(bundle);
                    mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                        @Override
                        public boolean onMarkerClick(Marker marker) {
                            showPopUpView(marker.getExtraInfo());
                            return false;
                        }
                    });
                    //在地图上添加Marker，并显示
                    mBaiduMap.addOverlay(option);
                } else {
                    Log.i("mainactivity", "hello");
                }
                break;
            case MY:
                if (resultCode == RESULT_OK) {
                    //注册成功
                }
                break;

        }
    }
    /*
    点击地图小纸条，弹出popupviwe
     */
    private void showPopUpView(Bundle bundle) {
        // 一个自定义的布局，作为显示的内容
        View contentView = LayoutInflater.from(getApplicationContext()).inflate(
                R.layout.popwindow_mainactivity, null);
//        final PopupWindow popupWindow = new PopupWindow(contentView,
//                AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT, true);
        final PopupWindow popupWindow = new PopupWindow(contentView,
                800, 850, true);

        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Log.i("mengdd", "onTouch : ");

                return false;
                // 这里如果返回true的话，touch事件将被拦截
                // 拦截后 PopupWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        //popWindow内容显示
        TextView content = (TextView) contentView.findViewById(R.id.content_popView);
        content.setText((CharSequence) bundle.get("content"));
        //popwindow 回复
        TextView tv = (TextView) contentView.findViewById(R.id.pop_reply);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //popwindow取消
        TextView cancel = (TextView) contentView.findViewById(R.id.pop_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.showAtLocation(mMapView, Gravity.CENTER, 0, 0);
    }
    /*
    个人位置监听接口
     */
    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // TODO Auto-generated method stub
            if (location == null || mMapView == null) {
                return;
            }
            MyLocationData data = new MyLocationData.Builder()
                    .accuracy(location.getRadius())//
                    .latitude(location.getLatitude())//
                    .longitude(location.getLongitude())//
                    .build();
            mBaiduMap.setMyLocationData(data);

            mLatitude = location.getLatitude();
            mLongtitude = location.getLongitude();

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
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
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
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
