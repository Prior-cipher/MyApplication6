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
    int k;
    int height;
    int width;
    public DrawView(Context context,Game game,int width,int height)
    {
        super(context);
        p = new Paint();
        this.game=game;
      if((width-80)*2<height-200)
      {
         this.k=(height-200)/20;
      }
      else {
          this.k=(width -80)/10;
      }

        this.height=height;
      this.width=width;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        p.setColor(Color.GRAY);
        p.setStrokeWidth(5f);
        //boundary(canvas);
        grid(canvas);


        //drawBriks(canvas);
        drawBrick(canvas);


    }


    private void grid(Canvas canvas)
    {
        p.setStrokeWidth(2f);



            for (int i = 1; i < 11; i +=1)
            {
                canvas.drawLine(40+k*i,  yOffset ,40+k*i , yOffset +k*20, p);
            }

        for (int  n = 0; n < 20; n+=1)
        {
            canvas.drawLine((width-k*10) /2, yOffset + n*k, (width-k*10) /2, yOffset + n*k, p);
        }


    }

    private void boundary(Canvas canvas)
    {
        p.setColor(Color.GRAY);
        p.setStrokeWidth(5f);

            canvas.drawLine(40, yOffset, 40, yOffset + k, p);
            canvas.drawLine(40, yOffset, 1040, yOffset, p);
            canvas.drawLine(k-40, yOffset, k-40, yOffset + k, p);
            canvas.drawLine(k-40, yOffset + k, 40, yOffset + k, p);


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
                    canvas.drawRect(41 + j * (k-40)/10, yOffset + i * (k-40)/10 + 2, 88*k + j * 50, yOffset + (i + 1) * 50 - 2, p);
                }
            }
        }

        
    }
}
