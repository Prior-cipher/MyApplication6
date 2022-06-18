package com.example.myapplication6;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Snake  extends AppCompatActivity
{

    SnakeView drawView;
    Boolean flag= true;
    FrameLayout game;
    int gameID;
    Runnable loop;
    SnakeLogic gameLogic;
    TextView score;
    float width;
    lastDialog builder;
    Handler mHandler2;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        game = new FrameLayout(this);
        gameLogic = new SnakeLogic();
        gameID=MainActivity.gameID;
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        width = metrics.widthPixels;



        drawView = new SnakeView(this, gameLogic,metrics.widthPixels,metrics.heightPixels);





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
        score.setText("0");

        builder= new lastDialog(this);


        mHandler2 = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message)
            {
                AlertDialog alert=builder.create();
                alert.show();
            }
        };



    }




    @Override
    public void onBackPressed()
    {


        finish();
        return;

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        int x = (int) event.getX();
        int y = (int) event.getY();


        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            JSONObject mesage = new JSONObject();
            if (x < width / 2)
            {


                try {
                    mesage.put("method", "gameSnakeInfo");
                    mesage.put("id", MainActivity.myId);
                    mesage.put("turn", false);
                    mesage.put("gameID", gameID);
                } catch (JSONException e) {
                    e.printStackTrace();
                }





            }
            else {
                try {
                    mesage.put("method", "gameSnakeInfo");
                    mesage.put("id", MainActivity.myId);
                    mesage.put("turn", true);
                    mesage.put("gameID", gameID);
                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }

            MainActivity.ws.send(mesage.toString());
        }

        return false;
    }
    @Override
    protected void onStart(){
        super.onStart();

        EventBus.getDefault().register(this);
    }
    @Override
    protected void onDestroy()
    {
        if(flag) {

            JSONObject mesage = new JSONObject();
            try {
                mesage.put("method", "surender2");
                mesage.put("id", MainActivity.myId);


                mesage.put("gameID", gameID);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            MainActivity.ws.send(mesage.toString());

        }

        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }




    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessegeEvent event) throws JSONException
    {
        if(event.message.equals("Eror"))
        {

            Message message = mHandler2.obtainMessage();
            message.sendToTarget();


        }
        JSONObject userJson = new JSONObject(event.message);
        String s= "";

        if(userJson.getString("method").equals("gameSnakeStat"))
        {

            ArrayList<int[]> list = new ArrayList<>();



            for(int i=0; i< userJson.getJSONArray("zmey").length(); i++)
            {
                list.add(new int[]{userJson.getJSONArray("zmey").getJSONArray(i).getInt(0),userJson.getJSONArray("zmey").getJSONArray(i).getInt(1)});

            }
            gameLogic.zmey=list;

             list = new ArrayList<>();

            ArrayList<int[]> list1 = new ArrayList<>();

            for(int i=0; i< userJson.getJSONArray("zmey1").length(); i++)
            {
                list1.add(new int[]{userJson.getJSONArray("zmey1").getJSONArray(i).getInt(0),userJson.getJSONArray("zmey1").getJSONArray(i).getInt(1)});

            }
            gameLogic.zmey1=list1;
            score.setText(userJson.getString("score1"));
            gameLogic.h[0] = userJson.getJSONArray("h").getInt(0);
            gameLogic.h[1] = userJson.getJSONArray("h").getInt(1);
            drawView.invalidate();
        }
        else if(userJson.getString("method").equals("endgame2"))
        {
            flag=false;

            if(userJson.getBoolean("win1"))
            {

                s= "Игра оконченна вы выиграли ваш счет = "+ Integer.parseInt(score.getText().toString()) *1000;
            }
            else {
                s= "Игра оконченна вы проиграли ваш счет = "+ Integer.parseInt(score.getText().toString()) *1000;
            }
            Snake.winDialog builder = new Snake.winDialog(this,s);
            AlertDialog alert = builder.create();
            alert.onBackPressed();
            //Setting the title manually

            alert.setTitle("Сообщение от сервера");
            alert.show();
        }
        else if(userJson.getString("method").equals("surender2"))
        {

            flag=false;


            s= "Игра оконченна ваш оппонент сдался ваш счет = "+ Integer.parseInt(score.getText().toString()) *1000;


            Snake.winDialog builder = new Snake.winDialog(this,s);
            AlertDialog alert = builder.create();
            alert.onBackPressed();
            //Setting the title manually

            alert.setTitle("Сообщение от сервера");
            alert.show();

        }

    }
    class winDialog extends AlertDialog.Builder
    {

        public winDialog(Context context, String text)
        {
            super(context);
            EventBus.getDefault().unregister(this);
            this.setMessage(text)
                    .setNegativeButton("Вернутся в меню", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            dialog.cancel();
                            Snake.super.onBackPressed();
                            Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }

}


