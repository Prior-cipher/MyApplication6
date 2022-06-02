package com.example.myapplication6;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

class Prediction extends View {
    Game game;
    public Prediction(Context context, Game game) {
        super(context);
        this.game=game;
    }

    @Override
    protected void onDraw(Canvas canvas)
    {

        int yOffset =1;

        int left=480;
        Brick brick1=new Brick(game.nextBrick);
        switch (brick1.type)
        {
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
            for (int j = 0; j < 4; j++)
            {

                for(Coordinate c:brick1.coordinates)
                {
                    if(c.x==(j+10) && c.y==i)
                    {
                        int color = Color.RED;


                        Paint p = new Paint();
                        p.setColor(color);
                        canvas.drawRect(left + j * 50, yOffset + i * 50 + 2, left+44 + j * 50, yOffset + (i + 1) * 50 - 2, p);
                        break;
                    }

                }


            }
        }

    }
}
