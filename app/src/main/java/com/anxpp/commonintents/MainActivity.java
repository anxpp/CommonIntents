package com.anxpp.commonintents;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.anxpp.commonintents.utils.IntentUtils;
import com.anxpp.commonintents.utils.SdkException;

public class MainActivity extends AppCompatActivity {

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
        toolbar.setLogo(R.mipmap.ic_launcher);
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
                        toast("设置闹钟");
                        IntentUtils.createAlarm(MainActivity.this, "测试", 7, 27);
                        break;
                    case 1://Timer
                        try {
                            IntentUtils.startTimer(MainActivity.this,"测试",20);
                            toast("开始计时器");
                        } catch (SdkException e) {
                            toast("SDK版本太低");
                        }
                        break;
                    default:break;
                }
            }
        });
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
    public static String items[] = {
            "Alarm","Timer","Camera",
            "Contacts","Email","Storage",
            "Maps","Music","Video",
            "Phone","Setting","Message",
            "Web","Other"};
    public MyAdapter(Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return items.length;
    }
    @Override
    public Object getItem(int position) {
        return items[position];
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
        viewHolder.textView.setText(items[position]);
        return convertView;
    }

    class ViewHolder{
        TextView textView;
    }
}