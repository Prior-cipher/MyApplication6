package com.example.myapplication6;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

public class DrawArc extends View
{
    Paint p;


    int height;
    double ky;
    double kx;
    ArcanoidLogic pl;

    private RectF r;
    DrawArc(Context context, int height, int wight, ArcanoidLogic pl)
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
        drawBriks(canvas);



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

    private  void drawBriks (Canvas canvas)
    {

        for (int i = 0; i < pl.listofBriks.size(); i++) {
            BrikBreakeer b = pl.listofBriks.get(i);
            r = new RectF((float) (b.getX()*kx), (float) (b.getY()*ky), (float) (b.getX()*kx + 100*kx), (float)( b.getY()*ky + 50*ky));
            p.setColor(switcher(b.getBrick()));
            canvas.drawRect( r, p);
        }
    }




    private int switcher(int a) {


        switch (a)
        {
            case 0:
                return Color.parseColor("#00FFFF");

            case 1:
                return Color.parseColor("#0000FF");


            case 2:
                return Color.parseColor("#008000");


            case 3:
                return Color.parseColor("#FFA500");


            case 4:
                return Color.parseColor("#FF00FF");


            case 5:
                return Color.parseColor("#800080");


            case 6:
                return Color.parseColor("#FF0000");


            case 7:
                return Color.parseColor("#FFFF00");

            default:
                return 0;
        }

    }

}
