package com.example.myapplication6;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import androidx.appcompat.app.AppCompatActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;

public class PingPong extends AppCompatActivity implements View.OnClickListener, SensorEventListener
{
    DrawPong pongView;
    WebSocket ws;
    FrameLayout game;

    SensorManager sm;
    Boolean flag= true;
    int gameID;
    TextView scoreOne;
    TextView scoreTwo;
    int speed;
    lastDialog builder;
    private float[] rotationMatrix;     //Матрица поворота
    private float[] accelData;           //Данные с акселерометра
    private float[] magnetData;       //Данные геомагнитного датчика
    private float[] OrientationData; //Матрица положения в пространстве
    Runnable loop;

    Handler handler;
    Handler mHandler2;
    pongLogic pl;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {


        super.onCreate(savedInstanceState);
        gameID=MainActivity.gameID;

        ws=MainActivity.ws;
        this.getSupportActionBar().hide();
        pl = new pongLogic(123,13,123,123);
        game = new FrameLayout(this);
        scoreOne=new TextView( this);
        scoreTwo=new TextView( this);
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();

//        int size2=0;
//        Resources resources = this.getResources();
//        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
//        if (resourceId > 0)
//        {
//             size2=resources.getDimensionPixelSize(resourceId);
//
//        }
        RelativeLayout scors= new RelativeLayout(this);

        RelativeLayout.LayoutParams rl = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        //

        Resources resources = this.getResources();




//        float dip = 42f;
//        Resources r = getResources();
//        float px = TypedValue.applyDimension(
//                TypedValue.COMPLEX_UNIT_DIP,
//                dip,
//                r.getDisplayMetrics()
//        );
//
//        int height = metrics.heightPixels-Math.round(px);
        int height = metrics.heightPixels;

        int wight = metrics.widthPixels;

        pongView = new DrawPong(this,height,wight,pl);

        //scoreOne.setText(String.valueOf(logic.scoreO));
        scoreTwo.setText(String.valueOf("0"));
        scoreOne.setId(R.id.score1);
        scoreTwo.setId(R.id.score2);
        scoreOne.setTextSize(80);
        scoreTwo.setTextSize(80);

// Converts 14 dip into its equivalent px


        RelativeLayout.LayoutParams scoretext1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams scoretext2 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        scors.setLayoutParams(rl);
        scoretext1.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        scoretext1.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        scoretext2.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        scoretext2.addRule(RelativeLayout.BELOW,R.id.score1);
        scoreOne.setLayoutParams(scoretext1);
        scoreTwo.setLayoutParams(scoretext2);

        scors.addView(scoreOne);
        scors.addView(scoreTwo);
        game.addView(scors);
        game.addView(pongView);
        setContentView(game);



         handler = new Handler();
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);


        rotationMatrix = new float[16];
        accelData = new float[3];
        magnetData = new float[3];
        OrientationData = new float[3];

        builder= new lastDialog(this);

        ws.send("{\"method\": \"startgame\",\"id\": \"${}\"}");
        mHandler2 = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message)
            {
                AlertDialog alert=builder.create();
                alert.show();
            }
        };

         loop = new Runnable()
        {
            public void run()
            {


                JSONObject mesage = new JSONObject();
                try {
                    mesage.put("method", "gamePongInfo");
                    mesage.put("id", MainActivity.myId);
                    mesage.put("speed", speed);
                    mesage.put("gameID", gameID);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


             ws.send(mesage.toString());
                if(scoreOne.getText().toString()!=String.valueOf(pl.scoreO) ||scoreTwo.getText().toString()!=String.valueOf(pl.score1))
                {
                    scoreOne.setText(String.valueOf(pl.scoreO));
                    scoreTwo.setText(String.valueOf(pl.score1));
                }
                //pongView.invalidate();
                handler.postDelayed(this, 100);


            }
        };

        loop.run();
    }


    private void loadNewSensorData(SensorEvent event)
    {
        final int type = event.sensor.getType(); //Определяем тип датчика
        if (type == Sensor.TYPE_ACCELEROMETER) { //Если акселерометр
            accelData = event.values.clone();
        }

        if (type == Sensor.TYPE_MAGNETIC_FIELD) { //Если геомагнитный датчик
            magnetData = event.values.clone();
        }

    }
    @Override
    public void onClick(View view) {

    }

    public static Point getNavigationBarSize(Context context) {
        Point appUsableSize = getAppUsableScreenSize(context);
        Point realScreenSize = getRealScreenSize(context);

        // navigation bar on the side
        if (appUsableSize.x < realScreenSize.x) {
            return new Point(realScreenSize.x - appUsableSize.x, appUsableSize.y);
        }

        // navigation bar at the bottom
        if (appUsableSize.y < realScreenSize.y) {
            return new Point(appUsableSize.x, realScreenSize.y - appUsableSize.y);
        }

        // navigation bar is not present
        return new Point();
    }

    public static Point getAppUsableScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size;
    }

    public static Point getRealScreenSize(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();


            display.getRealSize(size);


        return size;
    }
    @Override
    public void onSensorChanged(SensorEvent event)
    {

        loadNewSensorData(event); // Получаем данные с датчика

        SensorManager.getRotationMatrix(rotationMatrix, null, accelData, magnetData); //Получаем матрицу поворота
        SensorManager.getOrientation(rotationMatrix, OrientationData); //Получаем данные ориентации устройства в пространстве

        //Выводим результат
        SensorManager.getRotationMatrixFromVector(
                rotationMatrix , event.values);

        speed=(int) Math.round(Math.toDegrees(OrientationData[2]));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i)
    {
    }
    @Override
    protected void onResume()
    {
        super.onResume();
        sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_GAME );
        sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_GAME );
        //loop.run();
    }
    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
//        try {
//            loop.wait();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
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
                mesage.put("method", "surender1");
                mesage.put("id", MainActivity.myId);

                mesage.put("gameID", gameID);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ws.send(mesage.toString());

        }
        handler.removeCallbacks(loop);
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
        if(userJson.getString("method").equals("gamePongStat"))
        {
            pl.ballX= userJson.getInt("ballX");
            pl.ballY= userJson.getInt("ballY");
            pl.curentPosition= userJson.getInt("curentPosition");
            pl.enemyPosition= userJson.getInt("enemyPosition");
            pl.scoreO=userJson.getInt("scoreO");
            pl.score1=userJson.getInt("score1");
            pongView.invalidate();
        }
        else if(userJson.getString("method").equals("endgame1"))
        {

            flag=false;
            String s="";
            if(userJson.getBoolean("win1"))
            {

                s= String.format("Игра оконченна вы вйграли ваш счет = %d", this.pl.scoreO*1000);
            }
           else {
                s= String.format("Игра оконченна вы програли ваш счет = %d", this.pl.scoreO*1000);
            }
            winDialog builder = new winDialog(this,s);
            AlertDialog alert = builder.create();
            alert.onBackPressed();
            //Setting the title manually

            alert.setTitle("Сообщение от сервера");
            alert.show();
        }
        else if(userJson.getString("method").equals("surender1"))
        {
            flag=false;
            String s="";


                s= String.format("Игра оконченна ваш опонет сдался ваш счет = %d", this.pl.scoreO*1000);


            winDialog builder = new winDialog(this,s);
            AlertDialog alert = builder.create();
            alert.onBackPressed();
            //Setting the title manually

            alert.setTitle("Сообщение от сервера");
            alert.show();

        }

    }
    class winDialog extends AlertDialog.Builder
    {

        public winDialog(Context context,String text)
        {
            super(context);
            handler.removeCallbacks(loop);


            EventBus.getDefault().unregister(this);
            this.setMessage(text)


                    .setNegativeButton("Вернутся в меню", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id)
                        {

                            //  Action for 'NO' Button

                            dialog.cancel();
                            PingPong.super.onBackPressed();
                            Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }



}
