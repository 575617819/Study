package cn.steve.broadcast;

import cn.steve.Utils.SteveLog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * �����Ĺ㲥������
 * 
 * 1.�㲥�����ߣ�������������onReceiveִ����֮�󣬾ͽ����ˡ� 2.�첽�㲥�����ߵĽ���˳�����첽�ģ���֪���ĸ�����գ����ǿ����������ȼ� 3.�����Ҫ��˳��Ľ��գ�����Ҫ��������㲥
 * 
 * @author Steve
 *
 */

public class NormalBCReceiverA extends BroadcastReceiver {

  @Override
  public void onReceive(Context context, Intent intent) {
    String flag_str = intent.getStringExtra("flag");

    SteveLog.log("NormalBCReceiverA" + flag_str);
  }

}
