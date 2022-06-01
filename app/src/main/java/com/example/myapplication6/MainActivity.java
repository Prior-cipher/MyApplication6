package com.example.myapplication6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;


public class MainActivity extends AppCompatActivity
{

    public static WebSocket ws;
    private Button start;
    private Button close;
    private Button MEssage;
    private TextView output;
    Handler mHandler = new Handler();
    myDialog builder;
    public  static  int myId=-1;
    public  static  int gameID=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        start = (Button) findViewById(R.id.start);
        close = (Button) findViewById(R.id.closeb);
        builder = new myDialog(this);
        output = (TextView) findViewById(R.id.output);


        start.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                start();
            }
        });

        close.setOnClickListener(new View.OnClickListener()
        {


            @Override
            public void onClick(View view) {
                mHandler.removeCallbacksAndMessages(null);

                ws.close(1000, "Goodbye !");
            }
        });



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

                AlertDialog alert = builder.create();
                alert.onBackPressed();
                //Setting the title manually

                alert.setTitle("AlertDialogExample");
                alert.show();


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
            gameID=mesage.getInt("gameID");

            Intent intent = new Intent(MainActivity.this, PingPong.class);


              startActivity(intent);
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


    public class MyThread extends Thread
    {
        public void run() {
            Log.d("", "Mой поток запущен...");
            Request request ;

            OkHttpClient client = new OkHttpClient();
            request = new Request.Builder().url("ws://192.168.1.46:7960").build();

            ws = client.newWebSocket(request, new EchoWebSocketListener());
        }
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


