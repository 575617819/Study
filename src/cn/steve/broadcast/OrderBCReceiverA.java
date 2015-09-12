
package cn.steve.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import cn.steve.Utils.SteveLog;

public class OrderBCReceiverA extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String s = intent.getStringExtra("flag");
        SteveLog.log("OrderBCReceiverA������Ӧ-->" + s);
        SteveLog.log("A���յ�����Ϣ" + getResultData());
        setResultData("A��������Ϣ");
    }

}
