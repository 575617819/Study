package cn.steve.broadcast;

import cn.steve.Utils.SteveLog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * �����Ĺ㲥������
 * 
 * @author Steve
 *
 */

public class NormalBCReceiverB extends BroadcastReceiver {
  @Override
  public void onReceive(Context context, Intent intent) {
    String flag_str = intent.getStringExtra("flag");

    SteveLog.log("NormalBCReceiverB" + flag_str);
  }

}
