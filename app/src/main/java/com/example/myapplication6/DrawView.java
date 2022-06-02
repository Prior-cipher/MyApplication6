package com.example.myapplication6;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

class DrawView extends View
{
    Paint p;
    int yOffset =200;
    Game game;
    float k;
    public DrawView(Context context,Game game,float width)
    {
        super(context);
        p = new Paint();
        this.game=game;
        this.k= width;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        p.setColor(Color.GRAY);
        p.setStrokeWidth(5f);
        boundary(canvas);
        grid(canvas);


        drawBriks(canvas);
        drawBrick(canvas);


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
            canvas.drawLine(40*k, yOffset*k + j, 1040*k, yOffset*k + j+200, p);
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


    public void drawBrick( Canvas canvas)
    {

        p.setColor(Color.GRAY);
        for (Coordinate ccoord : game.currentBrick.coordinates)
        {
            canvas.drawRect(42*k + ccoord.x * 50*k, yOffset*k + ccoord.y * 50*k + 2, 88*k + ccoord.x * 50*k, yOffset*k + (ccoord.y + 1) * 50*k - 2, p);
        }



    }

    public  void ClearBrick()
    {

    }

    private  void drawBriks(Canvas canvas)
    {
        for (int i = 0; i < game.blocks.length; i++) {
            for (int j = 0; j <game.blocks[i].length ; j++)
            {
                if(game.blocks[i][j])
                {
                    p.setColor(Color.BLUE);
                    canvas.drawRect(42*k + j * 50*k, yOffset*k + i * 50*k + 2, 88*k + j * 50*k, yOffset*k + (i + 1) * 50*k - 2, p);
                }
            }
        }

        
    }
}
