package cn.steve.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import cn.steve.Utils.SteveLog;



public class OrderBCReceiverB extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
    String s = intent.getStringExtra("flag");
    SteveLog.log("B���յ�����Ϣ:"+getResultData());
    setResultData("B���͵���Ϣ");
  }
  
}
