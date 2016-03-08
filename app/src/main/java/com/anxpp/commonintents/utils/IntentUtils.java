package com.anxpp.commonintents.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.AlarmClock;

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
     */
    public static void startTimer(Context context,String msg,int sec) throws SdkException {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            Intent intent = new Intent(AlarmClock.ACTION_SET_TIMER);
            intent.putExtra(AlarmClock.EXTRA_LENGTH,sec);
            intent.putExtra(AlarmClock.EXTRA_MESSAGE,msg);
            intent.putExtra(AlarmClock.EXTRA_SKIP_UI,true);
            if(intent.resolveActivity(context.getPackageManager())!=null)
                context.startActivity(intent);
        }
        else throw new SdkException("dfg");
    }

}
