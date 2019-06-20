package com.recolectarq.campo.recolectarq;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

public class Lienzo extends View {

    private Path drawPath;
    private Paint drawPaint, canvasPaint;
    private int paintColor = 0xFF000000;
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;

    Boolean borrador=false;


    public Lienzo(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }

    private void setupDrawing() {
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(5);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        canvasPaint = new Paint(Paint.DITHER_FLAG);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float touchx=event.getX();
        float touchy=event.getY();

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchx,touchy);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchx,touchy);
                break;
            case MotionEvent.ACTION_UP:
                drawPath.moveTo(touchx,touchy);
                drawCanvas.drawPath(drawPath,drawPaint);
                drawPath.reset();
                break;
                default:
                    return false;
        }

        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
     canvas.drawBitmap(canvasBitmap,0,0,canvasPaint);
     canvas.drawPath(drawPath,drawPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        canvasBitmap=Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888);
        drawCanvas=new Canvas(canvasBitmap);
    }

    public void configurarColor(String nuevoColor)
    {
        invalidate();
        System.out.println("EL COLOR ES: "+ nuevoColor);
        paintColor= Color.parseColor(nuevoColor);
        drawPaint.setColor(paintColor);

    }

    public void configurarTamanoPincel(float tamanoPincel)
    {
        float pixel= TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                tamanoPincel,getResources().getDisplayMetrics());
        drawPaint.setStrokeWidth(pixel);

    }

    public void configurarBorrador(boolean estadoBorrador)
    {
        borrador=estadoBorrador;
        if(borrador){
            drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        }else
        {
            drawPaint.setXfermode(null);
        }

    }

    public void nuevoDibujo()
    {
        drawCanvas.drawColor(0,PorterDuff.Mode.CLEAR);
        invalidate();
    }
}
