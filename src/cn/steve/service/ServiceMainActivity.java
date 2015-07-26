package cn.steve.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import cn.steve.study.R;

public class ServiceMainActivity extends Activity {


  private MsgService msgService;
  private ProgressBar mProgressBar;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_service);
    // ��Service
    Intent intent = new Intent(this, MsgService.class);
    bindService(intent, conn, Context.BIND_AUTO_CREATE);
    mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
    Button mButton = (Button) findViewById(R.id.button);
    mButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        // ��ʼ����
        msgService.startDownLoad();
      }
    });
  }

  ServiceConnection conn = new ServiceConnection() {
    @Override
    public void onServiceDisconnected(ComponentName name) {
      System.out.println("onServiceDisconnected");
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
      System.out.println("onServiceConnected" + name);
      // ����һ��MsgService����
      msgService = ((MsgService.MsgBinder) service).getService();
      // ע��ص��ӿ����������ؽ��ȵı仯
      msgService.setOnProgressListener(new OnProgressListener() {
        @Override
        public void onProgress(int progress) {
          mProgressBar.setProgress(progress);
        }
      });
    }
  };

  @Override
  protected void onDestroy() {
    unbindService(conn);
    super.onDestroy();
  }
}
