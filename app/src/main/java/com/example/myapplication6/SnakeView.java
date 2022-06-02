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

    public SnakeView(Context context, SnakeLogic logic)
    {
        super(context);
        p = new Paint();
        this.logic=logic;

    }




    @Override
    protected void onDraw(Canvas canvas) {
        p.setColor(Color.GRAY);
        p.setStrokeWidth(5f);
        boundary(canvas);
        grid(canvas);

        drawFood(canvas);
        drawSnake(canvas);


    }

    private void grid(Canvas canvas)
    {
        p.setStrokeWidth(2f);
        for (int i = 90; i < 1040; i = i + 50)
        {
            canvas.drawLine(i, yOffset, i, yOffset + 1000, p);
        }
        for (int j = 50; j < 1040; j = j + 50) {
            canvas.drawLine(40, yOffset + j, 1040, yOffset + j, p);
        }
    }

    private void boundary(Canvas canvas)
    {
        p.setColor(Color.GRAY);
        p.setStrokeWidth(5f);
        canvas.drawLine(39, yOffset, 39, yOffset + 1001, p);
        canvas.drawLine(40, yOffset-1, 1040, yOffset-1, p);
        canvas.drawLine(1040, yOffset, 1040, yOffset + 1001, p);
        canvas.drawLine(1040, yOffset + 1001, 40, yOffset + 1001, p);
    }


    public void drawFood( Canvas canvas)
    {

        p.setColor(Color.GREEN);
            canvas.drawRect(42 + logic.h[1] * 50, yOffset + logic.h[0] * 50 + 2, 88 + logic.h[1] * 50, yOffset + (logic.h[0] + 1) * 50 - 2, p);




    }



    @SuppressLint("ResourceAsColor")
    private  void drawSnake(Canvas canvas)
    {

        for (int[] place: logic.zmey)
        {
            p.setColor(Color.parseColor("PURPLE"));
            canvas.drawRect(42 + place[1] * 50, yOffset + place[0] * 50 + 2, 88 + place[1] * 50, yOffset + (place[0] + 1) * 50 - 2, p);

        }





    }
}
