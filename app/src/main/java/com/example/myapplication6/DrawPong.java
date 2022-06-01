package com.example.myapplication6;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class DrawPong extends View
{
    Paint p;


    int height;
    double ky;
    double kx;
    pongLogic pl;
    DrawPong(Context context, int height,int wight,pongLogic pl)
    {
        super(context);

        this.p = new Paint();
        this.pl=pl;
        ky=1920;
        kx=1080;
        this.kx= Double.valueOf(wight)/kx;;
        this.ky= Double.valueOf(height)/ky;;

        this.height= height;

    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        p.setColor(Color.BLUE);
        p.setStrokeWidth(5f);

        writeOne(canvas);
        writeTwo(canvas);
        writeBall(canvas);



    }

    private void writeOne(Canvas canvas) {
        p.setColor(Color.BLUE);
        p.setStrokeWidth(5f);

       canvas.drawRect( Math.round(pl.enemyPosition*kx), Math.round(3),  Math.round(7 * 50*kx) + Math.round(pl.enemyPosition*kx), Math.round(50*ky ), p);
    }

    private void writeTwo(Canvas canvas)
    {
        p.setColor(Color.RED);
        p.setStrokeWidth(5f);
       canvas.drawRect( Math.round(pl.curentPosition*kx), height -  Math.round(50*ky) ,   Math.round(7 * 50*kx) + Math.round(pl.curentPosition*kx), Math.round(height)  - 3, p);
    }

    private void writeBall(Canvas canvas)
    {
        p.setColor(Color.GREEN);
        p.setStrokeWidth(5f);
       canvas.drawRect( Math.round(pl.ballX*kx), Math.round(pl.ballY*ky)  ,  Math.round((1 * 50 + pl.ballX)*kx), Math.round(pl.ballY*ky) +  50 , p);
    }

}
