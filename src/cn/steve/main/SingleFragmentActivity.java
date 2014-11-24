package cn.steve.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import cn.steve.study.R;





/**
 * ������һ�������
 * 
 * @author steve.yan
 * 
 */

public abstract class SingleFragmentActivity extends FragmentActivity {

	public abstract Fragment createFragment();

	@Override
	public void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setContentView(R.layout.activity_fragment);
		// ��ȡ������
		FragmentManager fm = getSupportFragmentManager();
		// ��ȡ��Ӧ��fragment
		Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

		// �ж�fragment�Ƿ����
		if (fragment == null) {
			fragment = createFragment();
			FragmentTransaction fragmentTS = fm.beginTransaction();
			fragmentTS.add(R.id.fragmentContainer, fragment).commit();
		}

	}

}
