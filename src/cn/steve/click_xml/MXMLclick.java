
package cn.steve.click_xml;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import cn.steve.study.R;

/**
 * ��XML�ļ���������Ӧ�ķ���
 * 
 * @author Steve
 */
public class MXMLclick extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_click);

    }

    // ��Ӧ�˰�Ť���ļ��ж�Ӧ�ĺ�������
    public void onClick(View v) {
        // ������Ӧ�Ŀؼ���ID������Ӧ
        if (v.getId() == R.id.textView) {
            Toast.makeText(getApplicationContext(), "click", Toast.LENGTH_SHORT).show();
        }
    }

}
