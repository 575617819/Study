
package cn.steve.simpleWelcome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import cn.steve.study.R;

/**
 * ��ӭ���棬2��֮���Զ���ʧ����ת������½����
 * 
 * @author steve.yan
 */
public class WelcomeActivity extends Activity {
    protected static final String TAG = "WelcomeActivity";

    private Context mContext;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_first);
        mContext = this;
        findView();
        init();

    }

    private void findView() {
        mImageView = (ImageView) findViewById(R.id.iv_welcome);
    }

    private void init() {
        // ����������֮���Զ���ʧ
        mImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                // ��ת����½����
                Intent intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                // ������ǰ�Ľ���
                finish();
            }
        }, 2000);

    }
}
