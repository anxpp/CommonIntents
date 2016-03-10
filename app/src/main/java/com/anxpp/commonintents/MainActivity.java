package com.anxpp.commonintents;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
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

    static final String TAG = "MainActivity";

    private final int CAMERA_THUMBNAIL_RESULT = 0;
    private final int CAMERA_IMAGE_MODE = 5;
    private final int CAMERA_VIDEO_MODE = 6;
    private final int REQUEST_SELECT_CONTACT = 7;
    private final int REQUEST_SELECT_SPECIFIC_CONTACT = 8;
    public static String items[] = {
            "setAnAlarm","startTimer","showAllAlarms",
            "addCalenderEvent","CapturePicture","imageModeCamera",
            "videoModeCamera","selectContact","selectSpecificContactData",
            "viewContact","editAnExistingContact","insertContact",
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
        Toast.makeText(MainActivity.this, "" + msg, Toast.LENGTH_SHORT).show();
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
                switch (position) {
                    case 0://Alarm
                        toast(items[position]);
                        IntentUtils.createAlarm(MainActivity.this, "测试", 7, 27);
                        break;
                    case 1://Timer
                        try {
                            IntentUtils.startTimer(MainActivity.this, "测试", 20, true);
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
                        startCalendar.set(2016, 3, 8, 8, 30);
                        Calendar endCalendar = Calendar.getInstance();
                        endCalendar.set(2016, 3, 8, 8, 30);
                        IntentUtils.addCalenderEvent(MainActivity.this, "title", "location", startCalendar, endCalendar);
                        toast(items[position]);
                        break;
                    case 4://CapturePicture
                        startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), CAMERA_THUMBNAIL_RESULT);
                        toast(items[position]);
                        break;
                    case 5://imageModeCamera
                        startActivityForResult(new Intent(MediaStore.INTENT_ACTION_STILL_IMAGE_CAMERA), CAMERA_IMAGE_MODE);
                        toast(items[position]);
                        break;
                    case 6://videoModeCamera
                        startActivityForResult(new Intent(MediaStore.INTENT_ACTION_VIDEO_CAMERA), CAMERA_VIDEO_MODE);
                        toast(items[position]);
                        break;
                    case 7://selectContact
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(intent, REQUEST_SELECT_CONTACT);
                        }
                        break;
                    case 8://selectSpecificContactData
                        intent = new Intent(Intent.ACTION_PICK);
                        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(intent, REQUEST_SELECT_SPECIFIC_CONTACT);
                        }
                        break;
                    case 9://viewContact
//                        intent = new Intent(Intent.ACTION_VIEW, contactUri);
//                        if (intent.resolveActivity(getPackageManager()) != null) {
//                            startActivity(intent);
//                        }
                        break;
                    case 10://EditAnExistingContact
                        //editContact
                        break;
                    case 11://insertContact
                        intent = new Intent(Intent.ACTION_INSERT);
                        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                        intent.putExtra(ContactsContract.Intents.Insert.NAME, "name");
                        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, "email");
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivity(intent);
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }
    public void editContact(Uri contactUri, String email) {
        Intent intent = new Intent(Intent.ACTION_EDIT);
        intent.setData(contactUri);
        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, email);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    //取回数据
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG+"requestCode", requestCode + "");
        Log.e(TAG+"resultCode", resultCode + "");
        if(resultCode == RESULT_OK){
            switch (requestCode) {
                case CAMERA_THUMBNAIL_RESULT:
                    Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                    onCreateDialog(bitmap).show();
                    break;
                case CAMERA_IMAGE_MODE:
                    break;
                case CAMERA_VIDEO_MODE:
                    break;
                case REQUEST_SELECT_CONTACT:
                    Uri contactUri = data.getData();
                    toast(contactUri.getUserInfo());
                    // Do something with the selected contact at contactUri
                    break;
                case REQUEST_SELECT_SPECIFIC_CONTACT:
                    // Get the URI and query the content provider for the phone number
                    contactUri = data.getData();
                    String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
                    Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
                    // If the cursor returned is valid, get the phone number
                    if (cursor != null && cursor.moveToFirst()) {
                        int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                        String number = cursor.getString(numberIndex);
                        // Do something with the phone number
                        toast(number);
                    }
                    break;
                default:break;
            }
        }
    }
    //创建显示图片的窗口
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