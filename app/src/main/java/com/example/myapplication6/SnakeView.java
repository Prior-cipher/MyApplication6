package com.example.myapplication6;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class SnakeView extends View
{
    Paint p;
    int yOffset =200;
    SnakeLogic logic;
    float k;
    public SnakeView(Context context, SnakeLogic logich,float width)
    {
        super(context);
        p = new Paint();
        this.logic=logich;
        this.k= width;
    }




    @Override
    protected void onDraw(Canvas canvas)
    {
        p.setColor(Color.GRAY);
        p.setStrokeWidth(5f);
        boundary(canvas);
        grid(canvas);

        drawFood(canvas);
        drawSnake(canvas);
        drawSnake2(canvas);



    }

    private void grid(Canvas canvas)
    {
        p.setStrokeWidth(2f);

        if(k<1){

            for (int i = (int) (90*k); i < 1040; i = (int) (i + 50*k))
            {
                canvas.drawLine(i,  yOffset*k , i, yOffset*k + 895, p);
            }
            for (int j = (int) (50*k); j < 900; j = (int) (j + 50*k)) {
                canvas.drawLine(40*k, yOffset*k + j, 1040*k, yOffset*k + j, p);
            }

        }
        else {
            for (int i =  (90); i < 1200; i =  (i + 50))
            {
                canvas.drawLine(i,  yOffset  , i, yOffset + 1200, p);
            }
            for (int j = (50); j < 1200; j = (j + 50)) {
                canvas.drawLine(40, yOffset*k + j , 1040, yOffset + j, p);
            }
        }
    }

    private void boundary(Canvas canvas) {
        p.setColor(Color.GRAY);
        p.setStrokeWidth(5f);
        if(k<1)
        {
            canvas.drawLine(40*k, yOffset*k, 40*k, yOffset*k + 1200*k, p);
            canvas.drawLine(40*k, yOffset*k, 1040*k, yOffset*k, p);
            canvas.drawLine(1040*k, yOffset*k, 1040*k, yOffset*k + 1200*k, p);
            canvas.drawLine(1040*k, yOffset*k + 1200*k, 40*k, yOffset*k + 1200*k, p);
        }
        else {
            canvas.drawLine(40, yOffset, 40, yOffset + 1200, p);
            canvas.drawLine(40, yOffset, 1040, yOffset, p);
            canvas.drawLine(1040, yOffset, 1040, yOffset + 1200, p);
            canvas.drawLine(1040, yOffset + 1200, 40, yOffset + 1200, p);
        }
    }

    public void drawFood( Canvas canvas)
    {

        p.setColor(Color.GREEN);
        canvas.drawRect(42 *k+ logic.h[1] * 50*k, yOffset*k + logic.h[0] * 50*k + 2, 88*k + logic.h[1] * 50*k, yOffset*k + (logic.h[0] + 1) * 50*k - 2, p);




    }



    @SuppressLint("ResourceAsColor")
    private  void drawSnake(Canvas canvas)
    {

        for (int[] place: logic.zmey)
        {
            p.setColor(Color.parseColor("PURPLE"));
            canvas.drawRect(42*k + place[1] * 50*k, yOffset*k + place[0] * 50*k + 2, 88*k + place[1] * 50*k, yOffset*k + (place[0] + 1) * 50*k - 2, p);

        }





    }
    @SuppressLint("ResourceAsColor")
    private  void drawSnake2(Canvas canvas)
    {

        for (int[] place: logic.zmey1)
        {
            p.setColor(Color.parseColor("BLUE"));
            canvas.drawRect(42*k + place[1] * 50*k, yOffset*k + place[0] * 50*k + 2, 88*k + place[1] * 50*k, yOffset*k + (place[0] + 1) * 50*k - 2, p);

        }





    }
}
