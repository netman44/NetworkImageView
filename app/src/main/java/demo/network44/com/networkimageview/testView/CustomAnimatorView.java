package demo.network44.com.networkimageview.testView;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * 在此写用途
 *
 * @author: zhiwei
 * @date: 2016-12-11 20:40
 * @version: 9.1.0
 */
public class CustomAnimatorView extends View{
    private static final String TAG = "CustomView";
    private Paint mPait;
    private static final int DEFAULT_RADIU = 100;
    private int mRadiu;

    public CustomAnimatorView(Context context) {
        super(context);
        init();
    }

    public CustomAnimatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomAnimatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomAnimatorView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        /**
         * 抗锯齿
         */
        mPait = new Paint(Paint.FAKE_BOLD_TEXT_FLAG);

        /*
         * 设置画笔样式为描边，圆环嘛……当然不能填充不然就么意思了
         *
         * 画笔样式分三种：
         * 1.Paint.Style.STROKE：描边
         * 2.Paint.Style.FILL_AND_STROKE：描边并填充
         * 3.Paint.Style.FILL：填充
         */
        mPait.setStyle(Paint.Style.STROKE);
        mPait.setColor(Color.argb(255,255, 128, 103));
        mPait.setStrokeWidth(10);

        mRadiu = DEFAULT_RADIU;
    }

    ObjectAnimator animator = null;
    public void restart() {
        if (animator != null && animator.isRunning()) {
            //停止原来的动画,开始新动画
            animator.cancel();
        }
        animator = ObjectAnimator.ofInt(this, "radiu", DEFAULT_RADIU, 400);
        animator.setDuration(3000);
        animator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int x = getWidth() / 2;
        int y = getHeight() /2;
        canvas.drawCircle(x , y, mRadiu, mPait);
    }

    public void setRadiu(int radiu) {
        mRadiu = radiu;
        //更新自己
        invalidate();
    }
}
