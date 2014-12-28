package cn.steve.webview;

import android.content.Context;
import android.widget.Toast;

/**
 * ������ʱ��JS���޷����������������Ҫ��proguard-project���ò�������
 * @author Steve
 *
 */
public class WebHost {
  public Context mContext;

  public WebHost(Context context) {
    this.mContext = context;
  }

  public void callJS() {
    Toast.makeText(mContext, "call from", Toast.LENGTH_LONG).show();
  }
}
