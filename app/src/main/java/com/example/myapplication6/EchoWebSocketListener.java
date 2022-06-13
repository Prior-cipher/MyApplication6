package com.example.myapplication6;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.net.UnknownHostException;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public   class EchoWebSocketListener extends WebSocketListener
{


    @Override
    public void onOpen(WebSocket webSocket, Response response)
    {


        super.onOpen(webSocket, response);

    }

    @Override
    public void onMessage(WebSocket webSocket, String text)
    {
        super.onMessage(webSocket, text);

        EventBus.getDefault().post(new MessegeEvent(text));

    }

    @Override
    public void onClosed(WebSocket webSocket, int code,String text)
    {
        super.onClosed( webSocket, code,  text);
        EventBus.getDefault().post(new MessegeEvent(text));
    }



    public void onFailure(WebSocket webSocket, Throwable t,Response response)
    {

        super.onFailure( webSocket, t, response);
        EventBus.getDefault().post(new MessegeEvent("Eror"));

    }








}