package com.kaka.customviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

public class DashBoard extends View {
    private Paint mPaint;
    private static final float RADIUS=Utils.px2dp(150);
    private static final float SWEEPANGLE=240;
    private Path dash;
    private PathMeasure pathMeasure;
    private static final float INDICATOR=Utils.px2dp(100);
    private float currentAngle=210f;
    private RectF rectF;

    public DashBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();//初始化Paint
    }

    private void initPaint(){
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);//抗锯齿
        mPaint.setStyle(Paint.Style.STROKE);//画线模式
        mPaint.setStrokeWidth(Utils.px2dp(2));//线宽度
        mPaint.setColor(Color.BLACK);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画弧
        drawArc(canvas);

        drawDegree2(canvas);

        drawIndicator(canvas);
    }


    private void drawArc(Canvas canvas){
        rectF = new RectF(getWidth() / 2 - RADIUS, getHeight() / 2 - RADIUS,
                getWidth() / 2 + RADIUS, getHeight() / 2 + RADIUS);
        canvas.drawArc(rectF,90+(360-SWEEPANGLE)/2,SWEEPANGLE,false,mPaint);
    }

    private void drawDegree(Canvas canvas){
        canvas.translate(getWidth()/2,getHeight()/2);
        canvas.rotate(30);
        for (int i=0;i<20;i++){
            //纵坐标下正上负，向上提高，加负值，即-mPaint.getStrokeWidth()/2
            canvas.drawLine(RADIUS-Utils.px2dp(10),-mPaint.getStrokeWidth()/2,RADIUS,-mPaint.getStrokeWidth()/2,mPaint);
            canvas.rotate(-SWEEPANGLE/20);
        }
        //最后一个点，因坐标系已经旋转了240度，向上提高，加整值，即mPaint.getStrokeWidth()/2
        canvas.drawLine(RADIUS-Utils.px2dp(10),mPaint.getStrokeWidth()/2,RADIUS,mPaint.getStrokeWidth()/2,mPaint);
        canvas.rotate(240-30);//旋转回去的角度
        canvas.translate(-getWidth()/2,-getHeight()/2);
    }

    private void drawDegree2(Canvas canvas){
        //刻度的路径
        dash=new Path();
        //Path.Direction.CW顺时针方向 同时顺时针切线方向为X轴正向
        dash.addRect(0,0,Utils.px2dp(2),Utils.px2dp(10), Path.Direction.CW);
        //弧线长度的路径
        Path length=new Path();
        length.addArc(rectF,90+(360-SWEEPANGLE)/2,SWEEPANGLE);
        //测量弧线长度
        pathMeasure=new PathMeasure(length,false);
        //这里(pathMeasure.getLength()-mPaint.getStrokeWidth())/20 弧线长度之所以减去Paint的宽度跟我第一种方式去掉宽度是一个意思
        mPaint.setPathEffect(new PathDashPathEffect(dash,
                (pathMeasure.getLength()-mPaint.getStrokeWidth())/20,0, PathDashPathEffect.Style.ROTATE));
        canvas.drawArc(rectF,90+(360-SWEEPANGLE)/2,SWEEPANGLE,false,mPaint);
        mPaint.setPathEffect(null);
    }

    private void drawIndicator(Canvas canvas){
        canvas.translate(getWidth()/2,getHeight()/2);
        canvas.drawLine(0,0,
                (float) Math.cos(Math.toRadians(currentAngle))*INDICATOR,
                (float)Math.sin(Math.toRadians(currentAngle))*INDICATOR,
                mPaint);
        canvas.translate(getWidth()/2,getHeight()/2);
    }
}
