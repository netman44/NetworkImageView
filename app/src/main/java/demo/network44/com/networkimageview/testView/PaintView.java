package demo.network44.com.networkimageview.testView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
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
        canvas.drawRect(x - gap / 2, y - gap/2, x + 80 + gap + 1, y + 80 + gap + 3, rectPaint);

        path.offset(200, 0);
        paint.setStrokeJoin(Paint.Join.ROUND);
        canvas.drawPath(path, paint);
        x += 200;
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

    private Paint getPaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        return paint;
    }
}
