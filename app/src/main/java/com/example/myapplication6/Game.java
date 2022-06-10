package com.example.myapplication6;

import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Arrays;

public class Game
{

    Boolean[][] blocks;
    String blocksColor[][];
    Brick currentBrick;
    Brick nextBrick;
    int score = 0;
    int score1 = 0;
    Game()
    {

        this.blocksColor= new String[][]{};
        this.currentBrick= new Brick();
        this.nextBrick= new Brick();
        this.blocks=new Boolean[][]{};
    }




}
