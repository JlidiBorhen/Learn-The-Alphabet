package com.bor3i.schreiber;
 
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
 
public class MainDrawingView extends View {
    private Paint paint = new Paint();
    private Path path = new Path();
 
    public MainDrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
 
        paint.setAntiAlias(true);
        paint.setStrokeWidth(20f);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
    }
 
    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, paint);
       
    }
   
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
 
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(eventX, eventY);
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(eventX, eventY);
                break;
            case MotionEvent.ACTION_UP:
                // nothing to do
            	 performClick();
                break;
            default:
                return false;
        }
 
        // Schedules a repaint.
        invalidate();
        return true;
    }
    @Override
    public boolean performClick() {
     // Calls the super implementation, which generates an AccessibilityEvent
           // and calls the onClick() listener on the view, if any
           super.performClick();

           // Handle the action for the custom click here

           return true;
    }
}