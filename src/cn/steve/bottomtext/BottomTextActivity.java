
package cn.steve.bottomtext;

import java.util.ArrayList;
import java.util.List;

import cn.steve.study.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;

/**
 * �޸ĵ�UC�˵�ʽ�Ľ��� ���˵�������һ����textview textview���Խ����������¹���
 * 
 * @author steve.yan
 */
public class BottomTextActivity extends Activity {

    private List<String> titles;

    private BottomPopupMenu myPopupMenu;
    MypopMenuTitleListner listner = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_text);
        initMenu();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /**
         * ϵͳ�˵�����Ҫ��һ��������Ч��
         */
        menu.add("");
        myPopupMenu.setDefaultContent();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (myPopupMenu.isShowing()) {
            myPopupMenu.dismiss();
        } else {
            /**
             * ���������ʹ�˵�����Ի���һ��������Ч��
             * myPopupMenu.setAnimationStyle(android.R.style.Animation_Dialog);
             */

            // ���ò˵�����ʾλ��
            myPopupMenu.showAtLocation(findViewById(R.id.layout), Gravity.BOTTOM, 0, 0);
            myPopupMenu.isShowing();
        }
        return false;
    }

    private void initMenu() {

        listner = new MypopMenuTitleListner();
        /**
         * �˵����������
         */
        titles = new ArrayList<String>();
        titles = array2list(new String[] {
                "�ݳ�", "����", "����"
        });

        myPopupMenu = new BottomPopupMenu(this, titles);

        /**
         * ���ò˵�����������Ч�� res/anim�е�xml�ļ���styles.xml�е�style���ʹ��
         */
        myPopupMenu.setAnimationStyle(R.style.PopupAnimation);
        myPopupMenu.setListner(listner);

    }

    /**
     * ת��ΪList<String> ���ڲ˵����еĲ˵���ͼ�긳ֵ
     * 
     * @param values
     * @return
     */
    private List<String> array2list(String[] values) {

        List<String> list = new ArrayList<String>();
        for (String var : values) {
            list.add(var);
        }

        return list;
    }

    class MypopMenuTitleListner implements BottomPopupMenu.TitleChangeListener {
        @Override
        public String getContent(int i) {
            switch (i) {
                case 0:
                    // return titles.get(0);
                    return "aaaaaaaaaaaaaaaxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx������  ������������������������������xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa���ǽ�β";
                case 1:

                    // return titles.get(1);
                    return "bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb";

                case 2:
                    // return titles.get(2);
                    return "cccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuccccccccccc";
                default:
                    return titles.get(0);
            }

        }

    }

}
