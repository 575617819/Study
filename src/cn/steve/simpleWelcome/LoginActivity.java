package cn.steve.simpleWelcome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;
import cn.steve.alertDialog.AlertDialogActivity;
import cn.steve.study.R;



/**
 * ������QQһ���ĵ�½����
 * 
 * @author steve.yan
 *
 */
public class LoginActivity extends Activity {

  private Context mContext;
  private RelativeLayout rl_user;
  private Button mLogin;
  private TextURLView mTextViewURL_ForgetPSW;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_welcome_login);
    mContext = this;
    findView();
    initTvUrl();
    init();
  }

  // ʵ�����ؼ�
  private void findView() {
    rl_user = (RelativeLayout) findViewById(R.id.rl_user);
    mLogin = (Button) findViewById(R.id.login);
    mTextViewURL_ForgetPSW = (TextURLView) findViewById(R.id.tv_forget_password);
  }


  private void init() {
    Animation anim = AnimationUtils.loadAnimation(mContext, R.anim.welcome_login_anim);
    anim.setFillAfter(true);
    rl_user.startAnimation(anim);
    mLogin.setOnClickListener(loginOnClickListener);
  }

  // ���¿ؼ�������
  private void initTvUrl() {
    mTextViewURL_ForgetPSW.setText(R.string.forget_password);
  }

  // ��½�ļ�����
  private OnClickListener loginOnClickListener = new OnClickListener() {
    @Override
    public void onClick(View v) {
      Intent intent = new Intent(mContext, AlertDialogActivity.class);
      startActivity(intent);
      finish();

    }
  };

}
