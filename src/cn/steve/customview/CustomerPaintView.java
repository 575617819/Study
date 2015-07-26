package cn.steve.customview;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import cn.steve.study.R;

/**
 * ����ֻ�����α��������ߣ������ϲ�����
 * 
 * @author steve.yan
 *
 */
public class CustomerPaintView extends View {
  private Resources myResources;

  // ���ʣ������������
  private Paint myPaint;
  private Paint mBitmapPaint;

  // ����·��
  private Path myPath;

  // ��������ײ�λͼ
  private Bitmap myBitmap;
  private Canvas myCanvas;

  private float mX, mY;
  private static final float TOUCH_TOLERANCE = 4;

  // ��¼��Ⱥ͸߶�
  private int mWidth;
  private int mHeight;

  public CustomerPaintView(Context context) {
    super(context);
    initialize();
  }

  public CustomerPaintView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    initialize();
  }

  public CustomerPaintView(Context context, AttributeSet attrs) {
    super(context, attrs);
    initialize();
  }

  /**
   * ��ʼ������
   */
  private void initialize() {
    // Get a reference to our resource table.
    myResources = getResources();
    // �������������õĻ���
    myPaint = new Paint();
    myPaint.setAntiAlias(true);
    myPaint.setDither(true);
    // ���û��ʵ���ɫ
    myPaint.setColor(myResources.getColor(R.color.purple_dark));
    myPaint.setStyle(Paint.Style.STROKE);
    myPaint.setStrokeJoin(Paint.Join.ROUND);
    myPaint.setStrokeCap(Paint.Cap.ROUND);
    myPaint.setStrokeWidth(12);
    // ���û��ʵ�����
    // myPaint.setMaskFilter(new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL));
    // myPaint.setMaskFilter(new EmbossMaskFilter(new float[] { 1, 1, 1 },0.4f, 6,3.5f));
    // myPaint.setAlpha(50);
    myPath = new Path();
    mBitmapPaint = new Paint(Paint.DITHER_FLAG);
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    mWidth = w;
    mHeight = h;
    myBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
    myCanvas = new Canvas(myBitmap);
  }

  // ������canvas��ΪͼƬ���б���
  public Bitmap save_img() {
    myCanvas.save(Canvas.ALL_SAVE_FLAG);
    myCanvas.restore();
    return myBitmap;
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    float x = event.getX();
    float y = event.getY();
    switch (event.getAction()) {
      case MotionEvent.ACTION_DOWN:
        touch_start(x, y);
        invalidate();
        break;
      case MotionEvent.ACTION_MOVE:
        touch_move(x, y);
        invalidate();
        break;
      case MotionEvent.ACTION_UP:
        touch_up();
        invalidate();
        break;
    }
    return true;
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    // ������ɫ
    canvas.drawColor(getResources().getColor(R.color.blue_dark));
    // ���������������������ƽ����󻭲������
    canvas.drawBitmap(myBitmap, 0, 0, mBitmapPaint);
    // ����·��
    canvas.drawPath(myPath, myPaint);
  }

  private void touch_start(float x, float y) {
    myPath.reset();
    myPath.moveTo(x, y);
    mX = x;
    mY = y;
  }

  private void touch_move(float x, float y) {
    float dx = Math.abs(x - mX);
    float dy = Math.abs(y - mY);
    if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {

      /**
       * ��һ�����������Ƶ��x����
       * 
       * �ڶ������������Ƶ��y����
       * 
       * �������������������x����
       * 
       * ���ĸ��������������y����
       * 
       * ���α���������
       * 
       */

      myPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);

      /**
       * 
       * @param x1 ��һ�����Ƶ��X����
       * @param y1 ��һ�����Ƶ��Y����
       * @param x2 �ڶ������Ƶ��X����
       * @param y2 �ڶ������Ƶ��Y����
       * @param x3 �������X����
       * @param y3 �������Y����
       * 
       */
      // myPath.cubicTo(mX, mY, (x + dx) , (y + dy), (x+mX)/2, (y+mY)/2);

      mX = x;
      mY = y;

    }
  }

  private void touch_up() {
    myPath.lineTo(mX, mY);
    // commit the path to our offscreen
    // ���������һ�䣬�ʴ�̧��ʱmyPath���ã���ô���Ƶ��߽���ʧ
    myCanvas.drawPath(myPath, myPaint);
    // kill this so we don't double draw
    myPath.reset();
  }

  /**
   * �������ͼ��
   */
  public void clear() {
    // �������1����������λͼ
    // myBitmap = Bitmap.createBitmap(mWidth, mHeight,
    // Bitmap.Config.ARGB_8888);
    // myCanvas = new Canvas(myBitmap);
    // �������2����λͼ���Ϊ��ɫ
    myBitmap.eraseColor(myResources.getColor(R.color.white));
    // �������������������Ϻ�����������
    // ·������
    myPath.reset();
    // ˢ�»���
    invalidate();
  }
}
