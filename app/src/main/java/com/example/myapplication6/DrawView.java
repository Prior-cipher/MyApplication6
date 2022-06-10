package com.example.myapplication6;

import static android.graphics.Color.parseColor;
import static android.graphics.Color.valueOf;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.view.View;

import androidx.annotation.RequiresApi;

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
          this.k=(width -80)/10;
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

        //drabPred(canvas);
        drawBriks(canvas);
        drawBrick(canvas);
        drawScore(canvas);


    }


    private void grid(Canvas canvas)
    {
        p.setStrokeWidth(2f);



            for (int i = 0; i < 11; i +=1)
            {
                canvas.drawLine((width-10*k)/2+k*i,  yOffset ,(width-10*k)/2+k*i , yOffset +k*20, p);
            }

        for (int  n = 0; n < 20; n+=1)
        {
            canvas.drawLine((width-10*k)/2, yOffset + n*k, width-(width-10*k)/2, yOffset + n*k, p);
        }


    }

    private void boundary(Canvas canvas)
    {
        p.setColor(Color.GRAY);
        p.setStrokeWidth(5f);

            canvas.drawLine((width-10*k)/2, yOffset, (width-10*k)/2, yOffset +k*20, p);
            canvas.drawLine((width-10*k)/2, yOffset, width-(width-10*k)/2, yOffset, p);
            canvas.drawLine(width-(width-10*k)/2, yOffset, width-(width-10*k)/2, yOffset +k*20, p);
            canvas.drawLine((width-10*k)/2, yOffset + 20*k, width-(width-10*k)/2, yOffset+ 20*k, p);


    }



    public void drawBrick(Canvas canvas)
    {
        if(game.currentBrick.color!=null)
        {

            //p.setColor( game.currentBrick.color);
            p.setColor(Color.parseColor(game.currentBrick.color));

            for (Coordinate ccoord : game.currentBrick.coordinates)
            {
                canvas.drawRect((width - 10 * k) / 2 + ccoord.x * k, yOffset + ccoord.y * k + 2, (width - 10 * k) / 2 + (ccoord.x + 1) * k - 2, yOffset + (ccoord.y + 1) * k - 2, p);
                //canvas.drawRect(42*k + ccoord.x * 50*k, yOffset*k + ccoord.y * 50*k + 2, 88*k + ccoord.x * 50*k, yOffset*k + (ccoord.y + 1) * 50*k - 2, p);
            }

        }

    }

    private  void drabPred(Canvas canvas)
    {
        int yOffset =1;

        int left=(width/2-2*k);
        if(game.nextBrick!=null) {
            Brick brick1 = game.nextBrick;
            switch (brick1.type) {
                case SQUARE:
                    brick1.coordinates = new Coordinate[]
                            {
                                    new Coordinate(0, 10),
                                    new Coordinate(1, 10),
                                    new Coordinate(1, 11),
                                    new Coordinate(0, 11)
                            };

                    break;


                case L_TYPE:
                    brick1.coordinates = new Coordinate[]{
                            new Coordinate(0, 10),
                            new Coordinate(0, 11),
                            new Coordinate(0, 12),
                            new Coordinate(1, 12)
                    };

                    break;
                case T_TYPE:
                    brick1.coordinates = new Coordinate[]{
                            new Coordinate(1, 10),
                            new Coordinate(0, 11),
                            new Coordinate(1, 11),
                            new Coordinate(1, 12)
                    };

                    break;
                case Z_TYPE:
                    brick1.coordinates = new Coordinate[]{
                            new Coordinate(1, 10),
                            new Coordinate(1, 11),
                            new Coordinate(0, 11),
                            new Coordinate(0, 12)
                    };


                    break;

                case LINE:
                    brick1.coordinates = new Coordinate[]{
                            new Coordinate(0, 10),
                            new Coordinate(0, 11),
                            new Coordinate(0, 12),
                            new Coordinate(0, 13)
                    };

                    break;
            }

            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 4; j++) {

                    for (Coordinate c : brick1.coordinates) {
                        if (c.x == (j + 10) && c.y == i) {
                            int color = Color.RED;


                            Paint p = new Paint();
                            p.setColor(color);

                            canvas.drawRect(left + j * k, yOffset + i * k + 2, left + (j + 1) * k - 2, yOffset + (i + 1) * k - 2, p);
                            break;
                        }

                    }


                }
            }
        }
    }



    private  void drawBriks(Canvas canvas)
    {
        for (int i = 0; i < game.blocks.length; i++)
        {
            for (int j = 0; j <game.blocks[i].length ; j++)
            {
                if(game.blocks[i][j])
                {

                    p.setColor(Color.parseColor(game.blocksColor[i][j]));
                    canvas.drawRect((width-10*k)/2 + j * k, yOffset + i * k + 2, (width-10*k)/2 + (j+1) * k -2, yOffset + (i + 1) * k - 2, p);
                }
            }
        }

        
    }
    private  void  drawScore(Canvas canvas)
    {


                    p.setColor(Color.GRAY);
                    p.setTextSize(72);
                    canvas.drawText("" + game.score, width-(width-10*k)/2-36, 190, p);
                    canvas.drawText("" + game.score1, (width-10*k)/2, 190, p);


    }



}
