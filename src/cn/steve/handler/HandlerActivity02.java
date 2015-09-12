
package cn.steve.handler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import cn.steve.study.R;

public class HandlerActivity02 extends Activity {

    private Handler mHandler = new Handler(new Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            System.out.println("�ض���Ϣ");
            return true;
        }
    }) {
        @Override
        public void handleMessage(Message msg) {
            System.out.println("�ٴ�����Ϣ");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler);
        mHandler.sendEmptyMessage(1);
    }
}
