package com.example.lenovo.hoay;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.GetDataCallback;

import java.util.Calendar;

public class EditInfoActivity extends AppCompatActivity implements  View.OnClickListener{
    ImageView myphoto = null;
    Button btn_Edit_back = null;
    Button btn_Edit_submit = null;
    private byte[] pic;
    EditText nickname;
    private RadioGroup radioSex;
    private TextView Area;
    private TextView Hobby;
    private TextView Statement;
    private TextView date;
    private Button datePicker;
    private String gender;
    private Bitmap mBitmap;
    // 用来保存年月日：
    private int mYear;
    private int mMonth;
    private int mDay;
    String nickName_str ;
    String Area_str;
    String Statement_str ;
    String date_str;
    // 声明一个独一无二的标识，来作为要显示DatePicker的Dialog的ID：
    static final int DATE_DIALOG_ID = 0;
    private Handler handler;
    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edit_info);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        if(gender.equals("男"))
                            radioSex.check(R.id.男);
                        else
                            radioSex.check(R.id.女);
                        nickname.setText(nickName_str);
                        Area.setText(Area_str);
                        Statement.setText(Statement_str);
                        break;
                    case 2:
                        mBitmap = BitmapFactory.decodeByteArray(pic, 0, pic.length);
                        myphoto.setImageBitmap(mBitmap);
                        break;

                }
            }
        };
        btn_Edit_back = (Button)findViewById(R.id.fanhui2);
        btn_Edit_back.setOnClickListener(this);
        btn_Edit_submit = (Button)findViewById(R.id.wancheng );
        btn_Edit_submit.setOnClickListener(this);
        myphoto = (ImageView)findViewById(R.id.myphoto);
        myphoto.setOnClickListener(this);
        nickname = (EditText)findViewById(R.id.nickname);
        radioSex = (RadioGroup)findViewById(R.id.Sex);
        setListener();
        Area = (TextView) findViewById(R.id.area);
        Hobby = (TextView) findViewById(R.id.hobby);
        Statement = (TextView) findViewById(R.id.statement);

        date = (TextView) findViewById(R.id.date);
        datePicker = (Button) findViewById(R.id.datepicker);
        datePicker.setOnClickListener(new btnDow_OnClickListener());

        // 获得当前的日期：
        final Calendar currentDate = Calendar.getInstance();

        mYear = currentDate.get(Calendar.YEAR);
        mMonth = currentDate.get(Calendar.MONTH);
        mDay = currentDate.get(Calendar.DAY_OF_MONTH);
        // 设置文本的内容：
        date.setText(new StringBuilder().append(mYear).append("年")
                .append(mMonth + 1).append("月")// 得到的月份+1，因为从0开始
                .append(mDay).append("日"));

        AVQuery<AVObject> avQuery = new AVQuery<>("_User");
        avQuery.getInBackground(AVUser.getCurrentUser().getObjectId(), new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                // object 就是 id 为 558e20cbe4b060308e3eb36c 的 Todo 对象实例
                nickName_str = avObject.getString("nickName");
                Area_str = avObject.getString("area");
                Statement_str = avObject.getString("statement");
                gender = avObject.getString("gender");
                AVFile avFile = avObject.getAVFile("pic");
                if(avFile != null) {
                    avFile.getDataInBackground(dataCallback);
                    Message message = new Message();//发送一个消息，该消息用于在handleMessage中区分是谁发过来的消息；
                    message.what = 1;
                    handler.sendMessage(message);
                }
            }
        });
    }
    GetDataCallback dataCallback = new GetDataCallback() {
        @Override
        public void done(byte[] bytes, AVException e) {
            pic = bytes;
            Message message = new Message();//发送一个消息，该消息用于在handleMessage中区分是谁发过来的消息；
            message.what = 2;
            handler.sendMessage(message);
        }
    };
    public void onClick(View v)
    {
        int i = v.getId();
        switch (i){
            case R.id.fanhui2:
                finish();
                break;
            case R.id.wancheng:
                nickName_str = nickname.getText().toString();
                Area_str = Area.getText().toString();
                Statement_str = Statement.getText().toString();
                date_str = date.getText().toString();
                //sex
                RecordString(nickName_str,Statement_str,Statement_str,date_str);
                break;
            case R.id.myphoto:
               Intent it1 = new Intent(EditInfoActivity.this, Upload_iconActivity.class);
               startActivityForResult(it1,1);
                break;
            default:
                break;
        }
    }
    public class btnDow_OnClickListener implements  View.OnClickListener{
        @Override
        public void onClick(View v) {
// 调用Activity类的方法来显示Dialog:调用这个方法会允许Activity管理该Dialog的生命周期，
// 并回调用 onCreateDialog(int)回调函数来请求一个Dialog
            showDialog(DATE_DIALOG_ID);

        }
    }
    // 需要定义弹出的DatePicker对话框的事件监听器：
    private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            String str1 = new String(new StringBuilder().append(mYear)
                    .append("年").append(mMonth + 1).append("月")// 得到的月份+1，因为从0开始
                    .append(mDay).append("日"));


// 设置文本的内容：
            date.setText(str1);
        }
    };
    private void setListener() {
        // TODO Auto-generated method stub
        //设置所有Radiogroup的状态改变监听器
        radioSex.setOnCheckedChangeListener(mylistener);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode){
            case 1:
                if(resultCode == RESULT_OK) {
                    Log.i("hell","ok result");
                    Bundle bundle = data.getExtras();
                    pic = bundle.getByteArray("pic");
                    mBitmap = BitmapFactory.decodeByteArray(pic, 0, pic.length);
                    myphoto.setImageBitmap(mBitmap);
                }
                break;
        }
    }

    RadioGroup.OnCheckedChangeListener mylistener = new RadioGroup.OnCheckedChangeListener()
    {
        @Override
        public void onCheckedChanged(RadioGroup Group, int Checkid) {
            // TODO Auto-generated method stub
            //设置TextView的内容显示CheckBox的选择结果

            RadioButton radioButton = (RadioButton)findViewById(radioSex.getCheckedRadioButtonId());
            int id = radioSex.getCheckedRadioButtonId();
            switch(id){
                case R.id.男:
                    gender = "男";
                    break;
                case R.id.女:
                    gender = "女";
                    break;

            }
        }
    };
    private void RecordString( String nickName_str,String Area_str,String Statement_str,String date_str){
        AVObject avb = AVObject.createWithoutData("_User",AVUser.getCurrentUser().getObjectId());

        AVFile avFile = new AVFile("pic", pic);
        avb.put("nickName", nickName_str);
        avb.put("area",Area_str);
        avb.put("statement",Statement_str);
        avb.put("birthday",date_str);
        avb.put("gender", gender);
        if(pic != null) {
            avb.put("pic", avFile);
        }
        avb.saveInBackground();
        Toast.makeText(getApplicationContext(),"保存成功！",Toast.LENGTH_LONG).show();
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
                        mDay);
        }
        return null;
    }
    };

