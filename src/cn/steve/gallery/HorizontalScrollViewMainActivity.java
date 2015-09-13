package cn.steve.gallery;

import android.app.Activity;
import android.graphics.Color;
import android.hardware.camera2.TotalCaptureResult;
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
    child_count = 31;
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

      textView.setText("" + (i + 1));
      textView.setGravity(Gravity.CENTER);
      linearLayout.addView(textView);
    }


    for (int i = 0; i < child_count; i++) {

      TextView textView = new TextView(this);

      textView.setLayoutParams(new ViewGroup.LayoutParams(child_width,
          ViewGroup.LayoutParams.MATCH_PARENT));
      textView.setText("" + (i + 1));
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
            flag = false;

            if (x <= child_width) {
              horizontalScrollView.scrollBy(child_width * child_count, 0);
              current_item += child_count;

            } else if (x >= (child_width * child_count * 2 - hsv_width - child_width)) {
              horizontalScrollView.scrollBy(-child_width * child_count, 0);
              current_item -= child_count;
            }
            break;
          case MotionEvent.ACTION_UP:
            flag = true;
            horizontalScrollView.smoothScrollTo(child_width * current_item - child_width / 2
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



  private void modifyData(int currentIndex) {


    boolean isSwitch = false;
    // ���һ������������¸���
    if ((currentIndex % child_count) + 3 == currenDaysOfMonth) {
      // ��ʾ�Ѿ���ѭ��������
      isSwitch = currentIndex / child_count == 1;
      int i = 0;
      if (isSwitch) {
        i = 0;
        for (; i < child_count; i++) {
          
        }
      } else {
        i = child_count;
        for (; i < 2 * child_count; i++) {
        }
      }


    }



    TextView textView;

    // ��ߵĲ���
    for (int i = 0; i < currentIndex; i++) {

      textView = (TextView) linearLayout.getChildAt(currentIndex - 1);

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
        horizontalScrollView.scrollTo(child_width * child_start_item - child_width / 2 - hsv_width
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
