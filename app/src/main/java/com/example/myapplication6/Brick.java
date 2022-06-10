package com.example.myapplication6;

import android.graphics.Color;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class Brick
{

    Coordinate[] coordinates;

    BrickType type;
    String color;

    Brick(Coordinate[] c,String cc,String type)
    {
        this.coordinates=c;
        this.type = BrickType.valueOf(type);

       this.color=cc;


    }
    Brick()
    {
        this.coordinates= new Coordinate[]{};
        this.type = BrickType.SQUARE;




    }

//    Brick(JSONObject brickData) throws JSONException {
//         brickData.getJSONArray("coord");
//        this.type =BrickType.valueOf( brickData.getString("coord"));
//
//        this.color=Color.parseColor(brickData.getString("color"));
//
//
//    }





}
