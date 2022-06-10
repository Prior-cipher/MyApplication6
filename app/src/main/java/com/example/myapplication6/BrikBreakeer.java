package com.example.myapplication6;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BrikBreakeer
{

    private Bitmap brick;
    private int x;
    private int y;
    private int c;
    public BrikBreakeer( int x, int y,int c)
    {

        this.x = x;
        this.y = y;
        this.c=c;

    }





    public int getX() {
        return x;
    }


    public void setX(int x) {
        this.x = x;
    }


    public int getY() {
        return y;
    }


    public void setY(int y) {
        this.y = y;
    }

    public int getBrick() {
        return c;
    }
}
