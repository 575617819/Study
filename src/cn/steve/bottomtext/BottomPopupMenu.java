
package cn.steve.bottomtext;

import java.util.List;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
@SuppressLint("ViewConstructor")
public class BottomPopupMenu extends PopupWindow {
    public final int TITLE_ID = 123456;

    private TitleChangeListener listner;

    /**
     * �˵��������岼��LinearLayout
     */
    private LinearLayout linearLayout;

    /**
     * �˵���������Ⲽ��GridView
     */
    private GridView gv_title;

    private ScrollView scrollview;

    /**
     * �˵�������ͼ��������GridView
     */
    private TextView gv_body;
    /**
     * �˵����������GridView������
     */
    private BottomTitleAdapter titleAdapter;

    private Context context;

    /**
     * ��ǰѡ�еķ������
     */
    private int currentIndex = 0;

    /**
     * ��һ��ѡ�еķ������ ����ѡ��������ʱ�������ƶ��������ж�Ӧ�������ƶ�
     */
    private int preIndex = 0;

    /**
     * �����빦�ܲ����м�ķֽ��� RelativeLayout + TextView
     */
    private RelativeLayout divisionLayout;

    /**
     * ��Ļ���
     */
    private int screenWidth = 0;

    @SuppressWarnings("deprecation")
    public BottomPopupMenu(Context context, List<String> titles) {
        super(context);
        this.context = context;

        /**
         * �˵��������岼��LinearLayout��ʼ��
         */
        linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        // linearLayout.setLayoutParams(new
        // LayoutParams(LayoutParams.MATCH_PARENT, 200));
        linearLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));

        /**
         * ��ȡ��Ļ���
         */
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        screenWidth = wm.getDefaultDisplay().getWidth();

        /**
         * �ֽ��߲��ֳ�ʼ��
         */
        divisionLayout = new RelativeLayout(context);
        divisionLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 1));
        divisionLayout.setBackgroundColor(Color.YELLOW);

        /**
         * ���Ⲽ�ֳ�ʼ��
         */
        gv_title = new GridView(context);

        /**
         * �������³�ʼ��adapter
         */
        final List<String> l = titles;
        final Context c = context;

        titleAdapter = new BottomTitleAdapter(titles, context, 0);

        /**
         * ���ñ�ѡ�к󣬱�����ɫ������ϵͳԭ�еĻ�ɫ����ΪTRANSPARENT
         */
        gv_title.setSelector(new ColorDrawable(Color.WHITE));
        gv_title.setAdapter(titleAdapter);

        /**
         * ����GridView����
         */
        gv_title.setNumColumns(titleAdapter.getCount());

        gv_title.setBackgroundColor(Color.WHITE);

        /**
         * ѡ��������ʱ����Ӧ�¼� ʵ�ֻ����л��˵�
         */
        gv_title.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                /**
                 * ���³�ʼ��adapter��Ϊ�˸ı����ѡ����ɫ
                 */
                titleAdapter = new BottomTitleAdapter(l, c, position);

                preIndex = currentIndex;
                currentIndex = position;

                gv_title.setAdapter(titleAdapter);

                /**
                 * �ֽ��߲����е�textView����ѡ�б����ƶ�λ�õģ�����Ϊ����Ч��
                 */
                divisionTran(position);

                /**
                 * ���ڹ���ͼ��GridView����Ч�� TranslateAnimation�����еĲ���������ʱ��̫��ȷ
                 * �ƺ���������������ڿؼ������λ�� ��һ�������ǿ�ʼλ�ã��ڶ����ǽ���λ�� ��ʱ���Ū���
                 */
                Animation translateBody;
                if (preIndex < currentIndex) {

                    translateBody = new TranslateAnimation(screenWidth, 0, 0, 0);
                    translateBody.setDuration(500);
                    gv_body.startAnimation(translateBody);

                } else if (preIndex > currentIndex) {

                    translateBody = new TranslateAnimation(-screenWidth, 0, 0, 0);
                    translateBody.setDuration(500);
                    gv_body.startAnimation(translateBody);

                }
                gv_body.setText(listner.getContent(currentIndex));
            }

        });

        scrollview = new ScrollView(context);
        scrollview.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        // scrollview.setVerticalScrollBarEnabled(true);
        // scrollview.setFillViewport(true);

        gv_body = new TextView(context);
        gv_body.setBackgroundColor(Color.WHITE);
        gv_body.setTextSize(20);
        // gv_body.setPadding(0, 10, 0, 10);

        gv_body.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));

        /**
         * �������ʱ����Ӧ�¼�
         */
        gv_body.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(c, gv_body.getText(), Toast.LENGTH_SHORT).show();

            }
        });

        /**
         * ��ʼ��textViewλ��
         */
        divisionTran(0);

        /**
         * �������Ӳ��ּ��뵽���岼����ȥ
         */
        linearLayout.addView(gv_title);
        linearLayout.addView(divisionLayout);
        scrollview.addView(gv_body);
        linearLayout.addView(scrollview);

        this.setWidth(LayoutParams.MATCH_PARENT);
        this.setHeight(200);
        this.setContentView(linearLayout);

        /**
         * ���´�����Ϊ�˽�����˵������ֺ󣬲�����Ӧ�ٴΰ�menu����ʹ�˵�����ʧ������
         * �������ַ�ҵ��Ĵ�http://blog.csdn.net/admin_/article/details/7278402 �����Լ�ȥ��
         */

        this.setFocusable(true);
        linearLayout.setFocusableInTouchMode(true);
        linearLayout.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((keyCode == KeyEvent.KEYCODE_MENU) && (BottomPopupMenu.this.isShowing())) {
                    BottomPopupMenu.this.dismiss();
                    titleAdapter = new BottomTitleAdapter(l, c, 0);
                    gv_title.setAdapter(titleAdapter);
                    return true;
                }
                return false;
            }
        });

    }

    /**
     * �ֽ��߲����е�textView����ѡ�б����ƶ�λ�õģ�����Ϊ����Ч��
     */
    public void divisionTran(int position) {

        /**
         * ���Ƴ���RelativeLayout��ԭ�е�textView
         */
        divisionLayout.removeAllViews();

        /**
         * ��������textView�������� ��̬�ı�ؼ�λ�� ��һ��
         */
        RelativeLayout.LayoutParams lp =
                new RelativeLayout.LayoutParams(screenWidth / 7, LayoutParams.MATCH_PARENT);

        /**
         * ���ö���Ч��
         */
        Animation translateTextView;
        translateTextView =
                new TranslateAnimation((preIndex - currentIndex) * screenWidth / 3, 0, 0, 0);

        /**
         * ����ѡ�еı���ȷ������ ��̬�ı�ؼ�λ�� �ڶ���
         */
        if (position == 0) {
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        } else if (position == 1) {
            lp.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        } else {
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        }

        /**
         * ��̬�ı�ؼ�λ�� ������
         */
        TextView line = new TextView(context);
        line.setBackgroundColor(Color.WHITE);
        divisionLayout.addView(line, lp);

        /**
         * ���ö���ִ��ʱ��
         */
        translateTextView.setDuration(200);

        /**
         * ��������
         */
        line.startAnimation(translateTextView);
    }

    // /����Ĭ�ϵĲ���
    public void setDefaultContent() {

        gv_body.setText(listner.getContent(0));

    }

    // define a interface for the main thread to refresh the UI
    interface TitleChangeListener {
        public String getContent(int i);
    }

    public void setListner(TitleChangeListener listner) {
        this.listner = listner;
    }

}
