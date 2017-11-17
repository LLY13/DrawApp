package lil115.drawapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/8/28.
 */

public class RandomDraw extends View implements View.OnTouchListener{
    //array to store points and color
    static ArrayList<Point> points = new ArrayList<Point>();
    static ArrayList<Point> curPoints;
    static Stack<ArrayList> unDoStack = new Stack<>();
    static Stack<ArrayList> reDoStack = new Stack<>();

    final Paint paint = new Paint();
    final Random random = new Random();
    int color = random.nextInt();
    Timer timer;


    public RandomDraw(Context context) {
        super(context);
        setOnTouchListener(this);
    }

    public RandomDraw(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
    }

    public RandomDraw(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, final MotionEvent event) {
        int num = event.getPointerCount();
        //chosen color
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                curPoints = new ArrayList<>();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        color = random.nextInt();
                    }
                }, 100, 500);

                break;
            case MotionEvent.ACTION_MOVE:
                timer.cancel();
                if(MainActivity.isRandom){
                    color = random.nextInt();
                }
                for(int i=0;i<num;i++) {
                    Point point = new Point(event.getX(i), event.getY(i), color, MainActivity.getDrawSize());
                    curPoints.add(point);
                    points.add(point);
                }

                break;
            case MotionEvent.ACTION_UP:
                if(num == 1) {
                    unDoStack.push(curPoints);
                }
                break;
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(Point pt: points)
        {
            paint.setColor(pt.color);
            canvas.drawCircle(pt.x,pt.y,pt.size,paint);
        }
    }


}
