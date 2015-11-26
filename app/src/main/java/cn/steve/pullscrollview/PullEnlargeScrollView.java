package cn.steve.pullscrollview;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * 自定义ScrollView,实现下拉放大,对之前的那个进行改造
 *
 * @author yantinggeng
 * @date 2015-11-26
 */
public class PullEnlargeScrollView extends ScrollView {

    /**
     * 默认阻尼系数
     */
    public static final float DEFAULT_SCROLL_RATIO = 0.5f;
    /**
     * 默认最大高度系数，决定了图片被方法的倍数，直接影响了图片的效果和可以下拉的最大高度mHeaderMaxHeight
     */
    public static final float DEFAULT_MAX_HEIGHT_RADIO = 1.5f;
    private static final String TAG = "PullScaleScrollView";
    /**
     * 滑动至翻转的距离.
     */
    private static final int TURN_DISTANCE = 100;

    /**
     * 回调的动画时间。
     */
    private static final int ANIM_DURATION = 400;

    /**
     * 回弹效果变化率
     */
    private static final Interpolator INTERPOLATOR = new DecelerateInterpolator(1f);
    /**
     * 阻尼系数,越小阻力就越大.
     */
    private float mScrollRatio = DEFAULT_SCROLL_RATIO;
    /**
     * 头部view.
     */
    private View mHeader;

    private float mMaxHeightRatio = DEFAULT_MAX_HEIGHT_RADIO;
    /**
     * 头部view的最大高度.
     */
    private int mHeaderMaxHeight;
    /**
     * 当前头部的高度
     */
    private int mCurrentHeight;

    /**
     * 头部view显示高度.
     */
    private int mHeaderHeight;
    /**
     * 头部view显示的宽度
     */
    private int mHeaderWidth;

    /**
     * ScrollView的content view.
     */
    private View mContentView;

    /**
     * ScrollView的content view矩形.
     */
    private Rect mContentRect = new Rect();

    /**
     * 首次点击的Y坐标.
     */
    private float mTouchDownY;

    /**
     * 是否关闭ScrollView的滑动.
     */
    private boolean mEnableTouch = false;

    /**
     * 是否开始移动.
     */
    private boolean isMoving = false;

    /**
     * 是否移动到顶部位置.
     */
    private boolean isTop = false;

    /**
     * 状态变化时的监听器.
     */
    private OnTurnListener mOnTurnListener;
    /**
     * 状态.
     */
    private State mState = State.NORMAL;
    private PointF mStartPoint = new PointF();

    public PullEnlargeScrollView(Context context) {
        super(context);
        init(context, null);
    }

    public PullEnlargeScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public PullEnlargeScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        // set scroll mode
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            setOverScrollMode(OVER_SCROLL_NEVER);
        }
    }

    /**
     * 设置Header
     */
    public void setHeader(View view) {
        mHeader = view;
    }

    /**
     * @return 返回header最大高度相对于初始高度的倍率
     */
    public float getMaxHeightRatio() {
        return mMaxHeightRatio;
    }

    /**
     * 设置下拉最大高度的系数
     *
     * @param maxHeightRatio 设置下拉最大高度的系数
     */
    public void setMaxHeightRatio(float maxHeightRatio) {
        mMaxHeightRatio = maxHeightRatio;
    }

    /**
     * @return 返回当前的阻尼系数
     */
    public float getScrollRatio() {
        return mScrollRatio;
    }

    /**
     * 设置阻尼系数,越小阻力就越大.
     *
     * @param scrollRatio 阻尼系数
     */
    public void setScrollRatio(float scrollRatio) {
        mScrollRatio = scrollRatio;
    }

    /**
     * 设置状态改变时的监听器
     */
    public void setOnTurnListener(OnTurnListener turnListener) {
        mOnTurnListener = turnListener;
    }

    @Override
    protected void onFinishInflate() {
        if (getChildCount() > 0) {
            mContentView = getChildAt(0);
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (getScrollY() == 0) {
            isTop = true;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return onTouchEvent(ev) && super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mContentView != null) {
            int action = ev.getAction();

            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    mTouchDownY = ev.getY();
                    mStartPoint.set(ev.getX(), ev.getY());
                    if (mHeaderWidth == 0) {
                        mHeaderHeight = mHeader.getMeasuredHeight();
                        mHeaderMaxHeight = (int) (mHeaderHeight * mMaxHeightRatio);
                        mHeaderWidth = mHeader.getMeasuredWidth();
                    }
                    return super.onTouchEvent(ev);

                case MotionEvent.ACTION_MOVE:

                    float deltaY = Math.abs(ev.getY() - mStartPoint.y);
                    if (deltaY < 10 || deltaY < Math.abs(ev.getX() - mStartPoint.x)) {
                        break;
                    }
                    //滑动的过程中就清除所有的动画
                    mHeader.clearAnimation();
                    mContentView.clearAnimation();

                    doActionMove(ev);

                    break;

                case MotionEvent.ACTION_UP:
                    // 回滚动画
                    if (isNeedAnimation()) {
                        rollBackAnimation();
                    }

                    if (getScrollY() == 0) {
                        mState = State.NORMAL;
                    }

                    isMoving = false;
                    mEnableTouch = false;
                    break;

                default:
                    break;
            }
        }

        // 禁止控件本身的滑动，接管了滑动时机的判断.
        boolean isHandle = mEnableTouch;
        if (!mEnableTouch) {
            try {
                isHandle = super.onTouchEvent(ev);
            } catch (Exception e) {
                Log.w(TAG, e);
            }
        }
        return isHandle;
    }

    /**
     * 执行移动动画
     */
    private void doActionMove(MotionEvent event) {

        // 当滚动到顶部时，将状态设置为正常，避免先向上拖动再向下拖动到顶端后首次触摸不响应的问题
        if (getScrollY() == 0) {
            mState = State.NORMAL;
            // 滑动经过顶部初始位置时，修正Touch down的坐标为当前Touch点的坐标
            if (isTop) {
                isTop = false;
                mTouchDownY = event.getY();
            }
        }

        float deltaY = event.getY() - mTouchDownY;

        // 对于首次Touch操作要判断方位：UP OR DOWN
        if (deltaY < 0 && mState == State.NORMAL) {
            mState = State.UP;
        } else if (deltaY > 0 && mState == State.NORMAL) {
            mState = State.DOWN;
        }

        if (mState == State.UP) {
            deltaY = deltaY < 0 ? deltaY : 0;

            isMoving = false;
            mEnableTouch = false;

        } else if (mState == State.DOWN) {
            //
            if (getScrollY() <= deltaY) {
                mEnableTouch = true;
                isMoving = true;
            }
            deltaY = deltaY < 0 ? 0 : (deltaY > mHeaderMaxHeight ? mHeaderMaxHeight : deltaY);
        }

        if (isMoving) {
            // 初始化content view矩形
            if (mContentRect.isEmpty()) {
                // 保存正常的布局位置
                mContentRect.set(mContentView.getLeft(), mContentView.getTop(),
                                 mContentView.getRight(), mContentView.getBottom());
            }
            // 计算移动距离(手势移动的距离*阻尼系数)
            float moveHeight = deltaY * mScrollRatio;
            //重新计算当前的imageview的高度
            mCurrentHeight = (int) (mHeaderHeight + moveHeight);

            // 修正content移动的距离，避免超过header的底边缘
            //改变content的坐标，下移
            int top = (int) (mContentRect.top + moveHeight);
            int bottom = (int) (mContentRect.bottom + moveHeight);

            if (mCurrentHeight < mHeaderMaxHeight) {
                // 移动content view
                mContentView.layout(mContentRect.left, top, mContentRect.right, bottom);

                // 移动header view
                int width = mCurrentHeight * mHeaderWidth / mHeaderHeight;
                mHeader.layout((mHeaderWidth - width) / 2, 0, (mHeaderWidth + width) / 2,
                               mCurrentHeight);
            }
        }
    }

    private void rollBackAnimation() {
        float currentWidth = mHeader.getRight() - mHeader.getLeft();
        AnimationSet animSet = new AnimationSet(true);
        animSet.setInterpolator(INTERPOLATOR);
        ScaleAnimation scaleAnim = new ScaleAnimation(
            currentWidth / mHeaderWidth, 1,
            mCurrentHeight * 1f / mHeaderHeight, 1);
        scaleAnim.setDuration(ANIM_DURATION);
        TranslateAnimation tranAnim = new TranslateAnimation(mHeader.getLeft(), 0, 0, 0);
        tranAnim.setDuration(ANIM_DURATION);
        animSet.addAnimation(scaleAnim);
        animSet.addAnimation(tranAnim);
        mHeader.startAnimation(animSet);

        mHeader.layout(0, 0, mHeaderWidth, mHeaderHeight);

        // 开启移动动画
        TranslateAnimation
            innerAnim =
            new TranslateAnimation(0, 0, mContentView.getTop(), mContentRect.top);
        innerAnim.setDuration(ANIM_DURATION);
        innerAnim.setInterpolator(INTERPOLATOR);
        mContentView.startAnimation(innerAnim);
        mContentView.layout(mContentRect.left, mContentRect.top, mContentRect.right,
                            mContentRect.bottom);

        mContentRect.setEmpty();

        // 回调监听器
        if (mCurrentHeight > mHeaderHeight + TURN_DISTANCE && mOnTurnListener != null) {
            mOnTurnListener.onTurn();
        }
    }

    /**
     * 是否需要开启动画
     */
    private boolean isNeedAnimation() {
        return !mContentRect.isEmpty() && isMoving;
    }

    private enum State {
        /**
         * 顶部
         */
        UP,
        /**
         * 底部
         */
        DOWN,
        /**
         * 正常
         */
        NORMAL
    }

    /**
     * 翻转事件监听器
     *
     * @author markmjw
     */
    public interface OnTurnListener {

        /**
         * 翻转回调方法
         */
        void onTurn();
    }
}
