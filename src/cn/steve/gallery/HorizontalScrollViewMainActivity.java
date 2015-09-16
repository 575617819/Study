
package cn.steve.gallery;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.steve.Utils.CalendarUntil;
import cn.steve.study.R;

/**
 * ����ǰ�Ǵӵ�������ʼ��ת��
 * 
 * @author Steve
 */
public class HorizontalScrollViewMainActivity extends Activity {

    // ˮƽ�����Ŀؼ�
    private HorizontalScrollView horizontalScrollView;
    private LinearLayout linearLayout;
    // �������Ŀ��
    private int hsv_width;
    // �ܹ��ж��ٸ�view
    private int child_count;
    // ÿһ��view�Ŀ��
    private int child_width;
    // Ԥ����ʾ����Ļ�ϵ�view�ĸ���
    private int child_show_count;
    // һ��ʼ����ѡ�е�view
    private int child_start;

    private int currentYear = 2015;
    private int currenMonth = 1;
    private int currenDate = 1;
    private int currenDaysOfMonth = 30;
    // ������¼��������ת�������������Ϣ
    private int desYear = 2016;
    private int desMonth = 2;
    private int desDate = 1;
    private int desDaysOfMonth = 30;
    // ��ʶ��Ҫ�л���ǰ�·ݣ��꣬���ڵ�λ��
    private int desIndexToChangeDateYearMonth = 100;
    private int desIndexToChangeIndexPreNext = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontalscrollview);
        init();
    }

    /**
     * ��ʼ���ؼ�������
     */
    private void init() {
        horizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
        linearLayout = (LinearLayout) findViewById(R.id.linearLayout);
        child_count = CalendarUntil.getCurrenMonthDays();
        child_show_count = 7;
        currentYear = CalendarUntil.getCurrentYear();
        currenMonth = CalendarUntil.getCurrentMonth();
        currenDate = CalendarUntil.getCurrentDate();
        currenDaysOfMonth = CalendarUntil.getCurrenMonthDays();

        child_start = currenDate;

    }

    /**
     * �������ؼ����view��ֻ���ظ������б����ʵ��ѭ������
     */
    private void initData() {
        for (int i = 0; i < child_count; i++) {
            TextView textView = new TextView(this);
            textView.setLayoutParams(new ViewGroup.LayoutParams(child_width,
                    ViewGroup.LayoutParams.MATCH_PARENT));

            // TODO ���ڵ��߼�
            textView.setText(currenMonth + "��" + (i + 1));
            textView.setGravity(Gravity.CENTER);
            linearLayout.addView(textView);
        }

        for (int i = 0; i < child_count; i++) {

            TextView textView = new TextView(this);

            textView.setLayoutParams(new ViewGroup.LayoutParams(child_width,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            textView.setText(currenMonth + "��" + (i + 1));
            textView.setGravity(Gravity.CENTER);

            linearLayout.addView(textView);
        }
    }

    /**
     * ʵ�ֹ�����ѭ��������ֹͣ����ʱ�Ĵ���
     */
    private void initHsvTouch() {
        horizontalScrollView.setOnTouchListener(new View.OnTouchListener() {

            private int pre_item;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                boolean flag = false;
                int x = horizontalScrollView.getScrollX();

                int current_item = (x + hsv_width / 2) / child_width + 1;

                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:

                        if (current_item == desIndexToChangeDateYearMonth) {
                            currenDate = desDate;
                            currenMonth = desMonth;
                            currentYear = desYear;
                            currenDaysOfMonth = desDaysOfMonth;
                        }

                        flag = false;

                        // ���󻬶������м�λ�õ�ʱ����ת��ѭ��ҳ�����Ӧλ�� 5-��A5
                        if (x <= child_width) {
                            // �����ϸ���
                            int preMonthDays = CalendarUntil.getMonthDaysByOffset(-1);
                            if (preMonthDays < currenDaysOfMonth) {
                                // �ں���ɾ��������d��textview
                                int d = currenDaysOfMonth - preMonthDays;

                                for (int i = 0; i < d; i++) {
                                    linearLayout.removeViewAt(child_count - 1 - i);
                                }

                                for (int i = 0; i < d; i++) {
                                    linearLayout.removeViewAt(2 * child_count - 2 - i);
                                }

                                child_count -= d;
                            }

                            if (preMonthDays > currenDaysOfMonth) {
                                int d = preMonthDays - currenDaysOfMonth;
                                for (int i = 0; i < d; i++) {
                                    TextView textView = new TextView(
                                            HorizontalScrollViewMainActivity.this);
                                    // TODO ��������ı����߼�
                                    textView.setText("Hello");
                                    textView.setLayoutParams(new ViewGroup.LayoutParams(
                                            child_width, ViewGroup.LayoutParams.MATCH_PARENT));
                                    textView.setGravity(Gravity.CENTER);
                                    linearLayout.addView(textView, child_count + i);
                                }

                                for (int i = 0; i < d; i++) {
                                    TextView textView = new TextView(
                                            HorizontalScrollViewMainActivity.this);
                                    textView.setText("Hello");
                                    textView.setLayoutParams(new ViewGroup.LayoutParams(
                                            child_width, ViewGroup.LayoutParams.MATCH_PARENT));
                                    textView.setGravity(Gravity.CENTER);

                                    linearLayout.addView(textView, 2 * child_count + i);
                                }

                                child_count += d;
                            }

                            horizontalScrollView.scrollBy(child_width * child_count, 0);
                            current_item += child_count;

                            // TODO �ӵ�ǰλ����ǰ�����ϸ����λ��-3,ͬʱҪע��-3λ�õĺ�����
                            int delta = current_item % child_count - child_start;
                            desIndexToChangeDateYearMonth = current_item - 3;
                            desDate = CalendarUntil.getLastDayOfMonth();
                            desMonth = CalendarUntil.getCurrentMonth();
                            desYear = CalendarUntil.getCurrentYear();
                            desDaysOfMonth = preMonthDays;
                            modifyLeftData(current_item);
                        }

                        // ���һ�����ʱ�򵽴�ѭ���ߵ��м��ʱ����ת��ǰ�����Ӧλ�� A27->27
                        else if (x >= (child_width * child_count * 2 - hsv_width - child_width)) {
                            // ���������¸���
                            int nextMonthDays = CalendarUntil.getMonthDaysByOffset(1);
                            if (nextMonthDays < currenDaysOfMonth) {
                                // �ں���ɾ��������d��textview
                                int d = currenDaysOfMonth - nextMonthDays;

                                for (int i = 0; i < d; i++) {
                                    linearLayout.removeViewAt(child_count - 1 - i);
                                }

                                for (int i = 0; i < d; i++) {
                                    linearLayout.removeViewAt(2 * child_count - 2 - i);
                                }

                                child_count -= d;
                            }

                            if (nextMonthDays > currenDaysOfMonth) {
                                int d = nextMonthDays - currenDaysOfMonth;
                                for (int i = 0; i < d; i++) {
                                    TextView textView = new TextView(
                                            HorizontalScrollViewMainActivity.this);
                                    // TODO ��������ı����߼�
                                    textView.setText("Hello");
                                    textView.setLayoutParams(new ViewGroup.LayoutParams(
                                            child_width, ViewGroup.LayoutParams.MATCH_PARENT));
                                    textView.setGravity(Gravity.CENTER);
                                    linearLayout.addView(textView, child_count + i);
                                }

                                for (int i = 0; i < d; i++) {
                                    TextView textView = new TextView(
                                            HorizontalScrollViewMainActivity.this);
                                    textView.setText("Hello");
                                    textView.setLayoutParams(new ViewGroup.LayoutParams(
                                            child_width, ViewGroup.LayoutParams.MATCH_PARENT));
                                    textView.setGravity(Gravity.CENTER);

                                    linearLayout.addView(textView, 2 * child_count + i);
                                }

                                child_count += d;
                            }

                            horizontalScrollView.scrollBy(-child_width * child_count, 0);
                            current_item -= child_count;
                            // TODO ��ǰλ�õ��´����λ��+3��ͬʱע������ǰ����
                            int delta = current_item % child_count - child_start;

                            desIndexToChangeDateYearMonth = current_item % child_count + 3;
                            desDate = 1;
                            desMonth = CalendarUntil.getCurrentMonth();
                            desYear = CalendarUntil.getCurrentYear();
                            desDaysOfMonth = nextMonthDays;
                            // modifyRightData(current_item);

                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        flag = true;
                        horizontalScrollView.smoothScrollTo(child_width * current_item
                                - child_width / 2
                                - hsv_width / 2, horizontalScrollView.getScrollY());
                        // ȷ����ѡ��

                        break;
                }

                if (pre_item == 0) {
                    isChecked(current_item, true);
                } else if (pre_item != current_item) {
                    isChecked(pre_item, false);
                    isChecked(current_item, true);
                }
                pre_item = current_item;
                return flag;
            }
        });
    }

    // �޸���ߵ���������
    private void modifyLeftData(int currentIndex) {
        System.out.println("currentIndex:" + currentIndex);
        System.out.println("currenMonth:" + currenMonth);
        TextView textView;

        // Ԥ����ת���ϸ���,Ӧ��λ�ñ���������count���ӵ�ǰλ�õ�ǰ������ǰ��ǰ�Ŷ�Ӧ��ǰλ�ô�����Ϊ�ϸ��µ�����
        for (int i = currentIndex - 5, j = 0; i > currentIndex % child_count; i--, j++) {
            textView = (TextView) linearLayout.getChildAt(i - 1);
            textView.setText(desMonth + "��" + (desDaysOfMonth - j));
        }
        // TODO ��ǰλ�õĺ��沿��ҲҪ�޸�

        // ͬʱ���б�������λ�õ�ǰ��������Ȼ����ִ�������(TODO �������)
        int correntIndex = currentIndex % child_count;
        for (int i = correntIndex; i > 0; i--) {
            textView = (TextView) linearLayout.getChildAt(i - 1);
            textView.setText(desMonth + "��" + (i));
        }

    }

    // �޸��ұߵ���������
    private void modifyRightData(int currentIndex) {
        TextView textView;
        // ׼���¸��µ�����
        for (int i = currentIndex + 5, j = 0; i < currentIndex + child_count; i++, j++) {
            textView = (TextView) linearLayout.getChildAt(i - 1);
            textView.setText(desMonth + "��" + (desDaysOfMonth - j));
        }
    }

    /**
     * ����ָ��λ�õ�״̬
     * 
     * @param item
     * @param isChecked
     */
    private void isChecked(int item, boolean isChecked) {
        TextView textView = (TextView) linearLayout.getChildAt(item - 1);
        if (isChecked) {
            textView.setTextColor(Color.RED);
        } else {
            textView.setTextColor(Color.BLACK);
        }
    }

    /**
     * �տ�ʼ�������ʱ�ĳ�ʼѡ����Ĵ���
     */
    private void initStart() {
        final ViewTreeObserver observer = horizontalScrollView.getViewTreeObserver();
        observer.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {

            @Override
            public boolean onPreDraw() {
                observer.removeOnPreDrawListener(this);
                int child_start_item = child_start;
                if ((child_start * child_width - child_width / 2 - hsv_width / 2) <= child_width) {
                    child_start_item += child_count;
                }
                horizontalScrollView.scrollTo(child_width * child_start_item - child_width / 2
                        - hsv_width
                        / 2, horizontalScrollView.getScrollY());
                isChecked(child_start_item, true);
                return false;
            }
        });
    }

    /**
     * ֻ�е�������������ܻ�ȡ�ؼ��ĳߴ�
     */
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);
        hsv_width = horizontalScrollView.getWidth();
        int child_width_temp = hsv_width / child_show_count;
        if (child_width_temp % 2 != 0) {
            child_width_temp++;
        }
        child_width = child_width_temp;
        initData();
        initHsvTouch();
        initStart();
    }

}
