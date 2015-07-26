package cn.steve.fragment;

import cn.steve.study.R;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class ContentFragment extends Fragment {
  private Button btn;


  // ����activity����һ��fragment��ʱ����ϵͳ�Զ�����
  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
  }



  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);


  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    System.out.println("onActivityCreated");

    System.out.println("ArticleFragmentCreate");

    this.btn = (Button) getActivity().findViewById(R.id.button);
    this.btn.setOnClickListener(new OnClickListener() {

      @Override
      public void onClick(View arg0) {
        Toast.makeText(getActivity(), "f2", Toast.LENGTH_SHORT).show();
      }
    });

  }

  // savedInstanceState������һ��Bundle����activity��onCreate()��Bundle��࣬����״̬�ָ���
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    System.out.println("onCreateView");
    return inflater.inflate(R.layout.article_fra2, container, false);

  }

}
