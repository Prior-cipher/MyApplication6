package com.example.myapplication6;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class Snake  extends AppCompatActivity
{

    SnakeView drawView;

    FrameLayout game;

    Runnable loop;
    SnakeLogic gameLogic;
    TextView score;
    int width;
    Handler mHandler;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        game = new FrameLayout(this);
        gameLogic = new SnakeLogic();
        gameLogic.Game();

        drawView = new SnakeView(this, gameLogic);
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();

        width = metrics.widthPixels;


        score = new TextView(this);
        score.setText(R.string.score);
        score.setId(R.id.score1);
        score.setTextSize(30);


        RelativeLayout.LayoutParams scoretext = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);


        scoretext.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
        scoretext.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);


        score.setLayoutParams(scoretext);





        game.addView(drawView);
        game.addView(score);
        setContentView(game);
        score.setText("1");




       mHandler = new Handler();


        mUpdate.start();
    }
    public Thread mUpdate = new Thread()
    {
        public void run()
        {
            while (!this.interrupted())
            {



                Runnable uiUpdate = new Runnable()
                {
                    public void run() {


                        if(gameLogic.status)
                        {
                            gameLogic.Game();


                            score.setText(String.valueOf(gameLogic.zmey.size()));
                            drawView.invalidate();


                        }
                        else
                            {
                                score.setText("you loose");
                            }
                    }
                };
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                mHandler.post(uiUpdate);
            }
        }

        public void interrupt()
        {
            super.interrupt();


        }
    };



    @Override
    public void onBackPressed() {


        finish();
        return;

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();


        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            if (x < width / 2) {

                gameLogic.turnLeft();
                drawView.invalidate();
            }
            else {

                gameLogic.turnRight();
                drawView.invalidate();
            }


        }
        return false;
    }


}


