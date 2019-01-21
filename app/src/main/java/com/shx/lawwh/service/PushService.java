package com.shx.lawwh.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.igexin.sdk.GTIntentService;
import com.igexin.sdk.message.GTCmdMessage;
import com.igexin.sdk.message.GTNotificationMessage;
import com.igexin.sdk.message.GTTransmitMessage;
import com.shx.lawwh.R;
import com.shx.lawwh.activity.NewsActivity;
import com.shx.lawwh.common.CommonValues;
import com.shx.lawwh.entity.response.ResponseMessage;
import com.shx.lawwh.entity.response.ResponseUserInfo;
import com.shx.lawwh.libs.http.HttpCallBack;
import com.shx.lawwh.libs.http.HttpTrowable;
import com.shx.lawwh.libs.http.MyJSON;
import com.shx.lawwh.libs.http.RequestCenter;
import com.shx.lawwh.libs.http.ZCResponse;
import com.shx.lawwh.utils.LogGloble;
import com.shx.lawwh.utils.SharedPreferencesUtil;

import java.util.Map;


/**
 * 继承 GTIntentService 接收来自个推的消息, 所有消息在线程中回调, 如果注册了该服务, 则务必要在 AndroidManifest中声明, 否则无法接受消息<br>
 * onReceiveMessageData 处理透传消息<br>
 * onReceiveClientId 接收 cid <br>
 * onReceiveOnlineState cid 离线上线通知 <br>
 * onReceiveCommandResult 各种事件处理回执 <br>
 */
public class PushService extends GTIntentService implements HttpCallBack {

    private static final int NOTIFICATION_ID_1 = 1;
    @Override
    public void onReceiveServicePid(Context context, int i) {

    }

    @Override
    public void onReceiveClientId(Context context, String clientid) {
        LogGloble.d("zzy","onReceiveClientId -> " + "clientid = " + clientid);
        SharedPreferencesUtil.saveValue(this, CommonValues.CID,clientid);
        ResponseUserInfo userInfo= (ResponseUserInfo) SharedPreferencesUtil.readObject(this,CommonValues.USERINFO);
        if(userInfo!=null){
            RequestCenter.uploadAppid(userInfo.getId(),clientid,this);
        }

    }

    @Override
    public void onReceiveMessageData(Context context, GTTransmitMessage gtTransmitMessage) {
        byte[] payload = gtTransmitMessage.getPayload();
//        String taskId = gtTransmitMessage.getTaskId();
//        String messageId = gtTransmitMessage.getMessageId();
//        String payloadId = gtTransmitMessage.getPayloadId();
        SharedPreferencesUtil.saveValue(this,CommonValues.NEWMESSAGE,true);
        Boolean isMessageOpen=SharedPreferencesUtil.getBooleanValue(this,CommonValues.ISMESSAGEOPEN,true);
        if(isMessageOpen) {
            if (payload != null) {
                String data = new String(payload);
                ResponseMessage message = MyJSON.parseObject(data, ResponseMessage.class);
                showNotification(message.getTitle(), message.getContent());
                //LogGloble.d("zzy","payload:"+data+" taskId:"+taskId+" messageId:"+messageId+" payloadId:"+payloadId);
            }
        }
    }

    /**
     * 显示系统自带的默认通知栏
     * */
    private void showNotification(String title,String content){
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
        Intent intent=new Intent(this, NewsActivity.class);
        PendingIntent pendingIntent= PendingIntent.getActivity(this, 1,intent, PendingIntent.FLAG_CANCEL_CURRENT);
        mBuilder.setContentTitle(title)//设置通知栏标题
                .setContentText(content) //设置通知栏显示内容</span>
                .setContentIntent(pendingIntent) //设置通知栏点击意图
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间
                .setSmallIcon(R.drawable.push_small);//设置通知小ICON
        Notification notification = mBuilder.build();
        //点击通知跳转以后，通知自动消失
        notification.flags=Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(NOTIFICATION_ID_1,notification);
    }

    @Override
    public void onReceiveOnlineState(Context context, boolean b) {

    }

    @Override
    public void onReceiveCommandResult(Context context, GTCmdMessage gtCmdMessage) {

    }

    @Override
    public void onNotificationMessageArrived(Context context, GTNotificationMessage gtNotificationMessage) {

    }

    @Override
    public void onNotificationMessageClicked(Context context, GTNotificationMessage gtNotificationMessage) {

    }

    @Override
    public boolean doSuccess(ZCResponse respose, String requestUrl) {
        return false;
    }

    @Override
    public boolean doFaild(HttpTrowable error, String url) {
        return false;
    }

    @Override
    public boolean httpCallBackPreFilter(String result, String url) {
        return false;
    }
}
