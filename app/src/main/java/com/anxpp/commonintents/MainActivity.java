package com.anxpp.commonintents;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anxpp.commonintents.utils.IntentUtils;
import com.anxpp.commonintents.utils.SdkException;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private final int CAMERA_THUMBNAIL_RESULT = 0;
    public static String items[] = {
            "Alarm","Timer","showAlarms",
            "addCalenderEvent","CapturePicture","Storage",
            "Maps","Music","Video",
            "Phone","Setting","Message",
            "Web","Other"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void snackbar(String msg){
        Snackbar.make(findViewById(R.id.fab), msg, Snackbar.LENGTH_SHORT).setAction("Action", null).show();
    }
    private  void toast(Object msg){
        Toast.makeText(MainActivity.this,""+msg, Toast.LENGTH_SHORT).show();
    }
    private void init(){
        //设置标题栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setLogo(R.mipmap.ic_launcher);
//        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.colorAccent));
        setSupportActionBar(toolbar);
        //设置fab按钮
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snackbar("anxpp.com");
            }
        });
        //
        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(new MyAdapter(this));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0://Alarm
                        toast(items[position]);
                        IntentUtils.createAlarm(MainActivity.this, "测试", 7, 27);
                        break;
                    case 1://Timer
                        try {
                            IntentUtils.startTimer(MainActivity.this,"测试",20,true);
                            toast(items[position]);
                        } catch (SdkException e) {
                            toast(e.getMessage());
                        }
                        break;
                    case 2://ShowAllAlarm
                        try {
                            IntentUtils.showAllAlarm(MainActivity.this);
                            toast(items[position]);
                        } catch (SdkException e) {
                            toast(e.getMessage());
                        }
                        break;
                    case 3://addCalenderEvent
                        Calendar startCalendar = Calendar.getInstance();
                        startCalendar.set(2016,3,8,8,30);
                        Calendar endCalendar = Calendar.getInstance();
                        endCalendar.set(2016,3,8,8,30);
                        IntentUtils.addCalenderEvent(MainActivity.this,"title","location",startCalendar,endCalendar);
                        toast(items[position]);
                        break;
                    case 4://CapturePicture
                        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), CAMERA_THUMBNAIL_RESULT);
                        toast(items[position]);
                        break;
                    default:break;
                }
            }
        });
    }
    //取回数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("onActivityResult", "");
        Log.e("requestCode", requestCode + "");
        Log.e("resultCode", resultCode + "");
        if(resultCode == RESULT_OK){
            switch (requestCode) {
                case CAMERA_THUMBNAIL_RESULT:
                    Bundle bundle = data.getExtras();
                    Bitmap bitmap = (Bitmap) bundle.get("data");
                    onCreateDialog(bitmap).show();
                    break;
                default:break;
            }
        }
    }
    private Dialog onCreateDialog(Bitmap bitmap) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_img, null);
        ((ImageView)view.findViewById(R.id.img_dialog)).setImageBitmap(bitmap);
        builder.setView(view);
        final Dialog dialog = builder.create();
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        return dialog;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == R.id.action_settings)
//            return true;
        return super.onOptionsItemSelected(item);
    }
}
class MyAdapter extends BaseAdapter{

    private LayoutInflater inflater;

    Context context;
    public MyAdapter(Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return MainActivity.items.length;
    }
    @Override
    public Object getItem(int position) {
        return MainActivity.items[position];
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(R.layout.activity_main_item,null);
            viewHolder = new ViewHolder();
            viewHolder.textView = ((TextView)convertView.findViewById(R.id.text));
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.textView.setText(MainActivity.items[position]);
        return convertView;
    }

    class ViewHolder{
        TextView textView;
    }
}