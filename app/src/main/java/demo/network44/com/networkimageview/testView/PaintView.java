package demo.network44.com.networkimageview.testView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * 在此写用途
 *
 * @author: zhiwei
 * @date: 2016-12-12 21:22
 * @version: 9.1.0
 */
public class PaintView extends View {
    private static final String TAG = "PaintView";

    public PaintView(Context context) {
        super(context);
    }

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PaintView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    ValueAnimator animator;
    public void startAnimator() {
        if (isAnimatorRunning()) {
            animator.cancel();
        }
        animator = ValueAnimator.ofFloat(0, 230);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 3.为目标对象的属性设置计算好的属性值
                float animatorValue = (float) animation.getAnimatedValue();
                startAnimatorPhase(animatorValue);

            }
        });
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(1000);
        animator.start();
    }

    public boolean isAnimatorRunning() {
        return animator != null && animator.isRunning();
    }

    public void stopAnimator() {
        if (animator != null && animator.isRunning()) {
            animator.cancel();
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int x = 100;
        int y = 50;

        drawStyle(canvas, x, y);
        y += 120;
        drawShadowLayer(canvas, x, y);
        y += 50;
        drawCap(canvas, x, y);
        y += 3 * 50 + 10;
        drawJoin(canvas, x, y);
        y += 150;
        x -= 90;
        drawCornerPathEffect(canvas, x, y);
        drawDashPathEffect(canvas, x + 300, y);

        y += 200;
        x = 20;
        drawDiscretePathEffect(canvas, x, y);
    }

    private void drawDiscretePathEffect(Canvas canvas, int x, int y) {
        Paint paint = getPaint();
        paint.setStrokeWidth(2);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);

        Path path = getPath(y);
        canvas.drawPath(path, paint);
        path.offset(0, 100);
        paint.setPathEffect(new DiscretePathEffect(2, 5));
        canvas.drawPath(path, paint);
        path.offset(0, 100);
        paint.setPathEffect(new DiscretePathEffect(6, 5));
        canvas.drawPath(path, paint);
        path.offset(0, 100);
        paint.setPathEffect(new DiscretePathEffect(6, 15));
        canvas.drawPath(path, paint);
    }

    private void drawCornerPathEffect(Canvas canvas, int x, int y) {
        Paint paint = getPaint();
        paint.setStrokeWidth(5);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);

        Path path = new Path();
        path.moveTo(x, y + 200);
        path.lineTo(x + 100,y);
        path.lineTo(x + 200, y + 200);

        canvas.drawPath(path,paint);

        paint.setColor(Color.RED);
        paint.setPathEffect(new CornerPathEffect(100));
        canvas.drawPath(path,paint);

        paint.setColor(Color.GREEN);
        paint.setPathEffect(new CornerPathEffect(200));
        canvas.drawPath(path,paint);
    }

    private void drawDashPathEffect(Canvas canvas, int x, int y) {
        Paint paint = getPaint();
        paint.setStrokeWidth(5);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);

        Path path = new Path();
        path.moveTo(x, y + 200);
        path.lineTo(x + 100,y);
        path.lineTo(x + 200, y + 200);

        canvas.drawPath(path,paint);
        paint.setColor(Color.RED);
        DashPathEffect effect = new DashPathEffect(new float[]{20,10, 50, 100},0);
        //使用DashPathEffect画线段
        paint.setPathEffect(effect);
        canvas.translate(0,50);
        canvas.drawPath(path,paint);


        //画同一条线段,偏移值为15
        effect = new DashPathEffect(new float[]{20,10, 50, 100},phase);
        paint.setPathEffect(effect);
        paint.setColor(Color.GREEN);
        canvas.translate(0,50);
        canvas.drawPath(path,paint);
    }

    float phase;
    int maxPhase = 230;

    public void startAnimatorPhase(float phase) {
        this.phase = phase;
        invalidate();
    }

    private void drawJoin(Canvas canvas, int x, int y) {
        Paint rectPaint = getPaint();
        rectPaint.setColor(Color.BLUE);
        rectPaint.setStyle(Paint.Style.STROKE);

        Paint paint = getPaint();
        int gap = 40;
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(gap);
        Path path = new Path();
        path.moveTo(x, y);
        path.lineTo(x + 80, y);
        path.lineTo(x, y + 80);
        path.close();

        paint.setStrokeJoin(Paint.Join.BEVEL);
        canvas.drawPath(path, paint);
        //画当前三角形的矩形区域
        canvas.drawRect(x, y, x + 80, y + 80, rectPaint);
        canvas.drawRect(x - gap / 2, y - gap/2, x + 80 + gap, y + 80 + gap, rectPaint);

        path.offset(200, 0);
        paint.setStrokeJoin(Paint.Join.MITER);
        canvas.drawPath(path, paint);
        x += 200;
        canvas.drawRect(x, y, x + 80, y + 80, rectPaint);
        canvas.drawRect(x - gap / 2, y - gap/2, x + 80 + gap + 1, y + 80 + gap + 3, rectPaint);


        path.offset(200, 0);
        paint.setStrokeJoin(Paint.Join.ROUND);
        canvas.drawPath(path, paint);
        x += 200;
        canvas.drawRect(x, y, x + 80, y + 80, rectPaint);
        canvas.drawRect(x - gap / 2, y - gap/2, x + 80 + gap, y + 80 + gap, rectPaint);
    }

    private void drawCap(Canvas canvas, int x, int y) {
        //线冒
        int oldY = y;

        Paint paint = getPaint();
        int gap = 40;
        paint.setStrokeWidth(gap);
        paint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawLine(x, y, x + 100, y, paint);
        y += gap + 10;
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(x, y, x + 100, y, paint);
        y += gap + 10;
        paint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawLine(x, y, x + 100, y, paint);

        paint.reset();
        paint.setColor(Color.DKGRAY);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        canvas.drawRect(x - 25, oldY - gap / 2, x, y + gap / 2, paint);
    }


    private void drawShadowLayer(Canvas canvas, int x, int y) {
        Paint paint = getPaint();
        paint.setTextSize(50);
        paint.setShadowLayer(10, 15, 15, Color.BLACK);
        canvas.drawText("这是文字阴影测试", x, y, paint);
    }

    private void drawStyle(Canvas canvas, int x, int y) {
        Paint paint = getPaint();
        int radius = 50;
        canvas.drawCircle(x, y, radius, paint);

        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawCircle(x + 150, y, radius, paint);

        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(x + 250, y, radius, paint);
    }

    private Path getPath(int y){
        Path path = new Path();
        // 定义路径的起点
        path.moveTo(0, y);

        // 定义路径的各个点
        for (int i = 0; i <= 40; i++) {
            path.lineTo(i*35, y + (float) (Math.random() * 150));
        }
        return path;
    }

    private Paint getPaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }
}
