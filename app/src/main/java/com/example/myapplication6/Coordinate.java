package com.example.myapplication6;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Coordinate {
    int y, x;
    public Coordinate(int y, int x)
    {
        this.y = y;
        this.x = x;

    }

    static Coordinate add(Coordinate A, Coordinate B) {
        return new Coordinate(A.y + B.y, A.x + B.x);
    }

    static Coordinate sub(Coordinate A, Coordinate B) {
        return new Coordinate(A.y - B.y, A.x - B.x);
    }

    static Coordinate rotateAntiClock(Coordinate X) {

        return new Coordinate(-X.x, X.y);
    }

    static boolean isEqual(Coordinate A, Coordinate B) {

        return A.y == B.y && A.x == B.x;
    }

    @Override
    public String toString() {
        return "x: '" +this.x+ " | y: "+this.y;
    }
}
