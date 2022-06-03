package com.example.myapplication6;

import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;


public class MainActivity extends AppCompatActivity
{

    public static WebSocket ws;
    private Button start;
    private Button close;
    private Button  snake;

    AlertDialog alert;
    Handler mHandler = new Handler();
    myDialog builder;
    myDialog2 builder2;
    public  static  int myId=-1;
    public  static  int gameID=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        builder = new myDialog(this);
        builder2 = new myDialog2(this);
       snake= (Button) findViewById(R.id.snakebutton);





        start();


        Button pong;
        pong= findViewById(R.id.pongButton);
        pong.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {




                JSONObject mesage = new JSONObject();
                try {
                    mesage.put("method", "iwantgame1");
                    mesage.put("clientId", myId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ws.send(mesage.toString());

                alert = builder.create();

                //Setting the title manually

                alert.setTitle("AlertDialogExample");
                alert.show();


            }
        });

        ImageView pongimg;
        pongimg= findViewById(R.id.pongimg);
        pongimg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {



                JSONObject mesage = new JSONObject();
                try {
                    mesage.put("method", "iwantgame1");
                    mesage.put("clientId", myId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                ws.send(mesage.toString());

                alert = builder.create();

                //Setting the title manually

                alert.setTitle("AlertDialogExample");
                alert.show();


            }
        });

        snake.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                JSONObject mesage = new JSONObject();
                try {
                    mesage.put("method", "iwantgame2");
                    mesage.put("clientId", MainActivity.myId);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                alert = builder2.create();

                //Setting the title manually

                alert.setTitle("AlertDialogExample");
                alert.show();
                ws.send(mesage.toString());




            }
        });
        ImageView snakeimg;
        snakeimg= findViewById(R.id.smakeimg);
        snakeimg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                JSONObject mesage = new JSONObject();
                try {
                    mesage.put("method", "iwantgame2");
                    mesage.put("clientId", MainActivity.myId);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                alert = builder2.create();

                //Setting the title manually

                alert.setTitle("AlertDialogExample");
                alert.show();
                ws.send(mesage.toString());




            }
        });


        Button Tetris= (Button) findViewById(R.id.button);
        Tetris.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, Choose_game.class);


                startActivity(intent);
            }
        });
        ImageView tetrisimg;
        tetrisimg= findViewById(R.id.tetrisimg);
        tetrisimg.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, Choose_game.class);


                startActivity(intent);
            }
        });



    }
    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void onEvent(MessegeEvent event) throws JSONException
    {
        JSONObject mesage = new JSONObject(event.message);


      if(mesage.getString("method").equals("start"))
      {
          myId=mesage.getInt("clientId");
      }
      if(mesage.getString("method").equals("loadgame1"))

      {
          alert.cancel();
            gameID=mesage.getInt("gameID");

            Intent intent = new Intent(MainActivity.this, PingPong.class);


              startActivity(intent);
      }

        if(mesage.getString("method").equals("loadgame2"))

        {
            alert.cancel();
            gameID=mesage.getInt("gameID");

            Intent intent = new Intent(MainActivity.this, Snake.class);


            startActivity(intent);
        }
        if(mesage.getString("method").equals("gameSnakeStat"))

        {
            ArrayList<int[]> list = new ArrayList<>();



            for(int i=0; i< mesage.getJSONArray("zmey").length(); i++)
            {
                list.add(new int[]{mesage.getJSONArray("zmey").getJSONArray(i).getInt(0),mesage.getJSONArray("zmey").getJSONArray(i).getInt(1)});

            }
        }


        Log.w("",event.message);
    }
    class myDialog extends AlertDialog.Builder
    {

        public myDialog(Context context)
        {
            super(context);
            this.setMessage("Вы хотите выйти из очереди в игру ?")


                    .setNegativeButton("Да", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            //  Action for 'NO' Button


                            JSONObject mesage = new JSONObject();
                            try {
                                mesage.put("method", "stopfind");
                                mesage.put("id", MainActivity.myId);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            ws.send(mesage.toString());

                            dialog.cancel();
                            Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }


    class myDialog2 extends AlertDialog.Builder
    {

        public myDialog2(Context context)
        {
            super(context);
            this.setMessage("Вы хотите выйти из очереди в игру ?")


                    .setNegativeButton("Да", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            //  Action for 'NO' Button


                            JSONObject mesage = new JSONObject();
                            try {
                                mesage.put("method", "stopfind2");
                                mesage.put("id", MainActivity.myId);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            ws.send(mesage.toString());

                            dialog.cancel();
                            Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }

    private void start()
    {
        Request request ;

        OkHttpClient client = new OkHttpClient();
        request = new Request.Builder().url("ws://192.168.1.46:7960").build();

        ws = client.newWebSocket(request, new EchoWebSocketListener());

//       MyThread coonection= new MyThread();
//        coonection.start();





    }





    @Override
    protected void onResume()
    {
        super.onResume();
       if(!EventBus.getDefault().isRegistered(this))
       {
           EventBus.getDefault().register(this);
       }


    }

    @Override
    protected void onStart()
    {
        super.onStart();

        EventBus.getDefault().register(this);
    }
    @Override
    protected void onPause()
    {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    private void output(final String txt)
    {

    }
}


