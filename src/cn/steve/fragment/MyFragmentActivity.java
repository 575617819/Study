package cn.steve.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import cn.steve.study.R;

/**
 * ���fragment�������� �򵥵��˽�
 * 
 * @author steve.yan
 *
 */
public class MyFragmentActivity extends FragmentActivity {
  public FragmentTransaction fragmentTransaction;

  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.myfragment);
    System.out.println("MyFragmentActivity");
    // �ڳ����м���Fragment
    FragmentManager fragmentManager = getSupportFragmentManager();
    fragmentTransaction = fragmentManager.beginTransaction();
    // ��һ��fragment
    ArticleFragment fragment = new ArticleFragment();
    // ��ӵ�activity�Ĳ�����
    fragmentTransaction.add(R.id.fragment_container, fragment);
    // �ڶ���
    ContentFragment content = new ContentFragment();
    fragmentTransaction.add(R.id.fragment_container2, content);
    // �ύ����
    fragmentTransaction.commit();
  }
}
