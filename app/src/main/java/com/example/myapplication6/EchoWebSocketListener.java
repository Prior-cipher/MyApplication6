package com.example.myapplication6;

import android.util.Log;

import org.greenrobot.eventbus.EventBus;

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








}