package cn.steve.signature;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import cn.steve.study.R;

/***
 *  ������HandWritingView 
 *  ���ܣ���д���� 
 *  ����ʱ�䣺2013-12-12 
 *  �����ˣ�LXH
 */
public class HandWritingView extends View {
	//definite the pen style
	public static final int PLAIN_PEN = 1;
	public static final int ERASER = 2;
	public static final int BLUR = 3;
	public static final int EMBOSS = 4;
	public static final int TS_PEN = 5;

	private Paint paint = null;
	private Canvas canvas = null;
	private static Bitmap originalBitmap = null;
	public  static Bitmap new1Bitmap = null;
	private static Bitmap new2Bitmap = null;
	private static Bitmap tempBitmap;
	public  static Bitmap saveImage = null;
	private float clickX = 0, clickY = 0;
	private float startX = 0, startY = 0;
	private boolean isClear = false;
	private int color = Color.BLACK; // ���û��ʵ���ɫ
	private float strokeWidth = 5.0f;
	private Path path;

	public HandWritingView(Context context, AttributeSet attrs) {
		super(context, attrs);
		path = new Path();
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inSampleSize = 2;

		tempBitmap = BitmapFactory.decodeResource(getResources(),R.drawable.qianming, null);
		
		originalBitmap = tempBitmap.copy(Bitmap.Config.ARGB_8888, true);
		new1Bitmap = Bitmap.createBitmap(originalBitmap);
	}

	public void clear() {
		isClear = true;
		// recyclingResources.recycleBitmap(new1Bitmap);
		new2Bitmap = Bitmap.createBitmap(originalBitmap);
		path.rewind();
		invalidate();
	}

	public Bitmap saveImage() {
		if (saveImage == null) {
			return null;
		}
		return saveImage;
	}

	public void setImge() {
		saveImage = null;
	}

	public void setstyle(float strokeWidth) {
		this.strokeWidth = strokeWidth;
	}

	/**
	 * ���ܣ���ɻ������ز���,���Ƴ�ͼ�γ���
	 * 
	 */
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		canvas.clipRect(0, 0, 400, 900);                // ���ƻ������������(x,y,x+width,y+high);
		canvas.drawColor(Color.argb(150, 120, 120, 0)); // ���ƻ���ı�����ɫ
		canvas.drawBitmap(HandWriting(new1Bitmap), 0, 0, null);
		canvas.drawPath(path, paint);
	}

	/**
	 * ���ܣ���ɻ��ʵĲ�����������bitmap����
	 * 
	 * @param originalBitmap
	 * @return
	 */
	@SuppressLint("HandlerLeak")
	public Bitmap HandWriting(Bitmap originalBitmap) {
		if (isClear) {
			canvas = new Canvas(new2Bitmap);
		} else {
			canvas = new Canvas(originalBitmap);
		}
		paint = new Paint();
		paint.setColor(Color.argb(255, 0, 0, 0)); // ���û��ʵ���ɫ
		paint.setStrokeWidth(5);// ���û��ʵĴ�ϸ
		getMaskFilter(1);
		paint.setStyle(Style.STROKE);
		// paint.setStrokeWidth(strokeWidth);//���û��ʵĴ�ϸ
		paint.setAntiAlias(true);// �����
		paint.setDither(true);
		paint.setFilterBitmap(true);
		paint.setSubpixelText(true);
		paint.setStrokeJoin(Paint.Join.ROUND);
		paint.setStrokeCap(Paint.Cap.ROUND);
		if (isClear) {
			return new2Bitmap;
		}
		return originalBitmap;
	}

	/**
	 * ���ܣ���ɶԻ���·���Ĳ���,Ϊ�˱�֤����Ч���Ĺ⻬�ԣ����ñ������߷�
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		startX = event.getX();
		startY = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			touchDown(event);
			return true;
		case MotionEvent.ACTION_MOVE:
			touchMove(event);
			return true;
		case MotionEvent.ACTION_UP:
			touchUp(event);
			return true;
		default:
			break;
		}
		return super.onTouchEvent(event);
	}

	/**
	 * ���ܣ���ָ������Ļʱ����
	 * 
	 * @param event
	 */
	private void touchDown(MotionEvent event) {
		clickX = startX;
		clickY = startY;
		// path���ƵĻ������
		path.moveTo(startX, startY);
		invalidate();
	}

	/**
	 * ���ܣ���ָ����Ļ�ϻ���ʱ����
	 * 
	 * @param event
	 */
	private void touchMove(MotionEvent event) {
		// ���α�������ʵ��ƽ�����ߣ�clickX, clickYΪ�����㣬(clickX+startX)/2,
		// (clickY+startY)/2Ϊ�յ�
		path.quadTo(clickX, clickY, (clickX + startX) / 2,(clickY + startY) / 2);
		// �ڶ���ִ��ʱ����һ�ν������õ�����ֵ����Ϊ�ڶ��ε��õĳ�ʼ����ֵ
		clickX = startX;
		clickY = startY;
		invalidate();
	}

	/**
	 * ���ܣ���ָ�뿪��Ļʱ����
	 * 
	 * @param event
	 */
	private void touchUp(MotionEvent event) {
		// ��굯�𱣴����״̬
		canvas.drawPath(path, paint);
	}

	/**
	 * ���ܣ���������뿪�ǵ���
	 */
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
	}

	/**
	 * ���ܣ����û��ʷ��
	 * @param mPaintType
	 * @return
	 */
	private MaskFilter getMaskFilter(int mPaintType) {
		MaskFilter maskFilter = null;
		switch (mPaintType) {
		case BLUR:// Ǧ��ģ�����
			maskFilter = new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL);
			break;
		case EMBOSS:// ë�ʸ�����
			maskFilter = new EmbossMaskFilter(new float[] { 1, 1, 1 }, 0.4f, 6,3.5f);
			break;
		case TS_PEN:// ͸��ˮ�ʷ��
			maskFilter = null;
			paint.setAlpha(50);
			break;
		default:
			maskFilter = null;
			break;
		}
		paint.setMaskFilter(maskFilter);
		return maskFilter;
	}
}
