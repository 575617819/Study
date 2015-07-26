package cn.steve.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MsgService extends Service {
  /**
   * �����������ֵ
   */
  public static final int MAX_PROGRESS = 100;
  /**
   * �������Ľ���ֵ
   */
  private int progress = 0;

  /**
   * ���½��ȵĻص��ӿ�
   */
  private OnProgressListener onProgressListener;


  /**
   * ע��ص��ӿڵķ��������ⲿ����
   * 
   * @param onProgressListener
   */
  public void setOnProgressListener(OnProgressListener onProgressListener) {
    this.onProgressListener = onProgressListener;
  }

  /**
   * ����get()��������Activity����
   * 
   * @return ���ؽ���
   */
  public int getProgress() {
    return progress;
  }

  /**
   * ģ����������ÿ���Ӹ���һ��
   */
  public void startDownLoad() {
    new Thread(new Runnable() {
      @Override
      public void run() {
        while (progress < MAX_PROGRESS) {
          progress += 5;
          // ���ȷ����仯֪ͨ���÷�
          if (onProgressListener != null) {
            System.out.println(progress);
            onProgressListener.onProgress(progress);
          }
          try {
            Thread.sleep(1000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
        }
      }
    }).start();
  }

  /**
   * ����һ��Binder����
   */
  @Override
  public IBinder onBind(Intent intent) {
    System.out.println("onBind");
    return new MsgBinder();
  }

  public class MsgBinder extends Binder {
    /**
     * ��ȡ��ǰService��ʵ��
     * 
     * @return
     */
    public MsgService getService() {
      return MsgService.this;
    }
  }

  @Override
  public void onCreate() {
    System.out.println("MSGservice--->onCreate");
    super.onCreate();
  }

}
