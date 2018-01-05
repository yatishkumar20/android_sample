package yatish.com.volleyex.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yatish on 30/11/17.
 */

public class SimpleDrawingView extends View {

    private final int paintColor = Color.BLACK;

    private Paint drawPaint;

    //private List<Point> circlePoints;   //writing on screen with dots

    private Path path = new Path();

    public SimpleDrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setupPaint();
        //circlePoints = new ArrayList<Point>();    //writing on screen with dots
    }

    private void setupPaint(){
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(5);
        drawPaint.setStyle(Paint.Style.STROKE);
        //drawPaint.setStyle(Paint.Style.FILL);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        /*drawPaint.setColor(Color.BLACK);              //Drawing circles
        canvas.drawCircle(50,50,20,drawPaint);
        drawPaint.setColor(Color.GREEN);
        canvas.drawCircle(50,150,20,drawPaint);
        drawPaint.setColor(Color.BLUE);
        canvas.drawCircle(50,250,20,drawPaint);*/
        /*for (Point p : circlePoints) {
            canvas.drawCircle(p.x, p.y, 5, drawPaint);  //writing on screen with dots
        }*/

        canvas.drawPath(path,drawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
       // circlePoints.add(new Point(Math.round(touchX), Math.round(touchY)));      //writing on screen with dots
        // indicate view should be redrawn

        switch (event.getAction()){

            case MotionEvent.ACTION_DOWN:
                // Starts a new line in the path
                path.moveTo(touchX,touchY);
                break;

            case MotionEvent.ACTION_MOVE:
                // Draws line between last point and this point
                path.lineTo(touchX,touchY);
                break;

            default:
                return false;

        }

        postInvalidate();
        return true;
    }

}
