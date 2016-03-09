package com.anxpp.commonintents.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.AlarmClock;
import android.provider.CalendarContract;
import android.provider.MediaStore;

import java.util.Calendar;

/**
 * 常用Intent
 * Created by anxpp on 2016/3/8.
 * anxpp.com
 */
public class IntentUtils {
    /**
     * 设置闹钟
     * @param context 这个不用说了吧
     * @param msg 闹钟标题
     * @param hour 时
     * @param min 分
     */
    public static void createAlarm(Context context, String msg, int hour, int min){
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
        intent.putExtra(AlarmClock.EXTRA_MESSAGE,msg);
        intent.putExtra(AlarmClock.EXTRA_HOUR,hour);
        intent.putExtra(AlarmClock.EXTRA_MINUTES,min);
        if(intent.resolveActivity(context.getPackageManager())!=null)
            context.startActivity(intent);
    }

    /**
     * 设置计时器
     * @param context 上下文
     * @param msg 消息
     * @param sec 时间(秒)
     * @param ui true为后台计时(通常通知栏会有提示)，false会弹出计时界面
     */
    public static void startTimer(Context context,String msg,int sec,boolean ui) throws SdkException {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            Intent intent = new Intent(AlarmClock.ACTION_SET_TIMER)
                    .putExtra(AlarmClock.EXTRA_LENGTH,sec)
                    .putExtra(AlarmClock.EXTRA_MESSAGE,msg)
                    .putExtra(AlarmClock.EXTRA_SKIP_UI,ui);
            if(intent.resolveActivity(context.getPackageManager())!=null)
                context.startActivity(intent);
        }
        else throw new SdkException("SDK版本不能低于" + android.os.Build.VERSION_CODES.KITKAT);
    }

    /**
     * 显示所有闹钟
     * @param context 上下文
     * @throws SdkException
     */
    public static void showAllAlarm(Context context) throws SdkException {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            Intent intent = new Intent(AlarmClock.ACTION_SHOW_ALARMS);
            if (intent.resolveActivity(context.getPackageManager()) != null)
                context.startActivity(intent);
            else throw new SdkException("当前版本不支持");
        }
        else throw new SdkException("SDK版本不能低于" + android.os.Build.VERSION_CODES.KITKAT);
    }

    /**
     * 添加日历事件
     * @param context 上下文
     * @param title 标题
     * @param location 位置
     * @param begin 开始
     * @param end 结束
     */
    public static void addCalenderEvent(Context context, String title, String location, Calendar begin, Calendar end) {
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.Events.TITLE, title)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, location)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin)
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end);
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        }
    }

}
