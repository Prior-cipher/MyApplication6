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
    int k;
    int height;
    int width;
    public SnakeView(Context context, SnakeLogic logich, int width, int height)
    {
        super(context);
        p = new Paint();
        this.logic=logich;
        if((width-80)<height-200)
        {
            this.k=(width -80)/20;
        }
        else {
            this.k=(height -200)/20;
        }

        this.height=height;
        this.width=width;

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

//    private void grid(Canvas canvas)
//    {
//        p.setStrokeWidth(2f);
//
//        if(k<1){
//
//            for (int i = (int) (90*k); i < 1040; i = (int) (i + 50*k))
//            {
//                canvas.drawLine(i,  yOffset*k , i, yOffset*k + 895, p);
//            }
//            for (int j = (int) (50*k); j < 900; j = (int) (j + 50*k)) {
//                canvas.drawLine(40*k, yOffset*k + j, 1040*k, yOffset*k + j, p);
//            }
//
//        }
//        else {
//            for (int i =  (90); i < 1200; i =  (i + 50))
//            {
//                canvas.drawLine(i,  yOffset  , i, yOffset + 1200, p);
//            }
//            for (int j = (50); j < 1200; j = (j + 50)) {
//                canvas.drawLine(40, yOffset*k + j , 1040, yOffset + j, p);
//            }
//        }
//    }
//
//    private void boundary(Canvas canvas) {
//        p.setColor(Color.GRAY);
//        p.setStrokeWidth(5f);
//        if(k<1)
//        {
//            canvas.drawLine(40*k, yOffset*k, 40*k, yOffset*k + 1200*k, p);
//            canvas.drawLine(40*k, yOffset*k, 1040*k, yOffset*k, p);
//            canvas.drawLine(1040*k, yOffset*k, 1040*k, yOffset*k + 1200*k, p);
//            canvas.drawLine(1040*k, yOffset*k + 1200*k, 40*k, yOffset*k + 1200*k, p);
//        }
//        else {
//            canvas.drawLine(40, yOffset, 40, yOffset + 1200, p);
//            canvas.drawLine(40, yOffset, 1040, yOffset, p);
//            canvas.drawLine(1040, yOffset, 1040, yOffset + 1200, p);
//            canvas.drawLine(1040, yOffset + 1200, 40, yOffset + 1200, p);
//        }
//    }


    private void grid(Canvas canvas)
    {
        p.setStrokeWidth(2f);



        for (int i = 0; i < 20; i +=1)
        {
            canvas.drawLine((width-20*k)/2+k*i,  yOffset ,(width-20*k)/2+k*i , yOffset +k*20, p);
        }

        for (int  n = 0; n < 20; n+=1)
        {
            canvas.drawLine((width-20*k)/2, yOffset + n*k, width-(width-20*k)/2, yOffset + n*k, p);
        }


    }

    private void boundary(Canvas canvas)
    {
        p.setColor(Color.GRAY);
        p.setStrokeWidth(5f);

        canvas.drawLine((width-20*k)/2, yOffset, (width-20*k)/2, yOffset +k*20, p);
        canvas.drawLine((width-20*k)/2, yOffset, width-(width-20*k)/2, yOffset, p);
        canvas.drawLine(width-(width-20*k)/2, yOffset, width-(width-20*k)/2, yOffset +k*20, p);
        canvas.drawLine((width-20*k)/2, yOffset + 20*k, width-(width-20*k)/2, yOffset+ 20*k, p);


    }
    public void drawFood( Canvas canvas)
    {

        p.setColor(Color.GREEN);


        canvas.drawRect((width-20*k)/2 + logic.h[1] * k, yOffset + logic.h[0] * k + 2, (width-20*k)/2 + (logic.h[1]+1) * k -2, yOffset + (logic.h[0] + 1) * k - 2, p);


    }



    @SuppressLint("ResourceAsColor")
    private  void drawSnake(Canvas canvas)
    {

        for (int[] place: logic.zmey)
        {
            p.setColor(Color.parseColor("RED"));

            canvas.drawRect((width-20*k)/2 + place[1] * k, yOffset + place[0] * k + 2, (width-20*k)/2 + (place[1]+1) * k -2, yOffset + (place[0] + 1) * k - 2, p);
        }





    }
    @SuppressLint("ResourceAsColor")
    private  void drawSnake2(Canvas canvas)
    {

        for (int[] place: logic.zmey1)
        {
            p.setColor(Color.parseColor("BLUE"));
            canvas.drawRect((width-20*k)/2 + place[1] * k, yOffset + place[0] * k + 2, (width-20*k)/2 + (place[1]+1) * k -2, yOffset + (place[0] + 1) * k - 2, p);

        }





    }
}
