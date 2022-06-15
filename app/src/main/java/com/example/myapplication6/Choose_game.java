package com.example.myapplication6;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

public class Choose_game extends AppCompatActivity {


    DrawView drawView;
    Boolean flag= true;
    FrameLayout game;

    Runnable loop;
    Game gameLogic;
    lastDialog builder;
    Handler mHandler2;
    int width;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.getSupportActionBar().hide();
        game = new FrameLayout(this);
        gameLogic = new Game();


        DisplayMetrics metrics = this.getResources().getDisplayMetrics();

        int resourceId = this.getResources().getIdentifier("navigation_bar_height", "dimen", "android");


        int k=metrics.heightPixels;
//        if (resourceId > 0) {
//           k-= this.getResources().getDimensionPixelSize(resourceId);
//        }
        width =metrics.widthPixels ;
        drawView = new DrawView(this, gameLogic,width,k);







        game.addView(drawView);

        setContentView(game);













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
    public void onBackPressed() {


        finish();
        return;

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            JSONObject mesage = new JSONObject();
            if (x < width / 3) {
                try {
                    mesage.put("method", "gameTetrisCommand");
                    mesage.put("id", MainActivity.myId);
                    mesage.put("command", "turnLeft");
                    mesage.put("gameID", MainActivity.gameID);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
                MainActivity.ws.send(mesage.toString());

            } else if (x < width / 3 * 2) {
                try {
                    mesage.put("method", "gameTetrisCommand");
                    mesage.put("id", MainActivity.myId);
                    mesage.put("command", "rotate");
                    mesage.put("gameID", MainActivity.gameID);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
                MainActivity.ws.send(mesage.toString());
            } else {

                try {
                    mesage.put("method", "gameTetrisCommand");
                    mesage.put("id", MainActivity.myId);
                    mesage.put("command", "turnRight");
                    mesage.put("gameID", MainActivity.gameID);
                } catch (JSONException e)
                {
                    e.printStackTrace();
                }
                MainActivity.ws.send(mesage.toString());
            }


        }
        return false;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessegeEvent event) throws JSONException
    {

        if(event.message.equals("Eror"))
        {

            Message message = mHandler2.obtainMessage();
            message.sendToTarget();


        }
        String s;
        JSONObject userJson = new JSONObject(event.message);

        if(userJson.getString("method").equals("gameTetrisInfo"))
        {


            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();


              s =userJson.getJSONObject("currentBrick").getString("coordinates");


            gameLogic.currentBrick.coordinates=gson.fromJson(s,Coordinate[].class);

            s=userJson.getJSONObject("currentBrick").getString("color");

            gameLogic.currentBrick.color= s;

            s =userJson.getString("nextBrick");

            gameLogic.nextBrick=gson.fromJson(s,Brick.class);
            s =userJson.getString("blocks");
            gameLogic.blocks=gson.fromJson(s,Boolean[][].class);

            s =userJson.getString("blocksColor");
            gameLogic.blocksColor=gson.fromJson(s,String[][].class);
            gameLogic.score=userJson.getInt("score0");
            gameLogic.score1=userJson.getInt("score1");
            drawView.invalidate();
        }
        else if(userJson.getString("method").equals("endgame3"))
        {
            flag=false;

            if(userJson.getBoolean("win1"))
            {

                s= ("Игра оконченна вы выйграли ваш счет = "+gameLogic.score);
            }
            else {
                s= ("Игра оконченна вы програли ваш счет = "+gameLogic.score);
            }
            Choose_game.winDialog builder = new Choose_game.winDialog(this,s);
            AlertDialog alert = builder.create();

            //Setting the title manually

            alert.setTitle("Сообщение от сервера");
            alert.show();




        }
        else if(userJson.getString("method").equals("surender3"))
        {

            flag=false;


            s= ("Игра оконченна ваш опонет сдался ваш счет = "+gameLogic.score );


            Choose_game.winDialog builder = new Choose_game.winDialog(this,s);
            AlertDialog alert = builder.create();

            //Setting the title manually

            alert.setTitle("Сообщение от сервера");
            alert.show();

        }

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
                mesage.put("method", "surender3");
                mesage.put("id", MainActivity.myId);


                mesage.put("gameID", MainActivity.gameID);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            MainActivity.ws.send(mesage.toString());

        }

        super.onDestroy();
        EventBus.getDefault().unregister(this);

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
                            Choose_game.super.onBackPressed();
                            Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }
}


