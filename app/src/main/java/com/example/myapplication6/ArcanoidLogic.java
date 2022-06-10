package com.example.myapplication6;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ArcanoidLogic
{
    public int ballX;
    public int ballY;
    public int curentPosition;
    public int enemyPosition;
    public List<BrikBreakeer> listofBriks;


    int scoreO=0;
    int score1=0;
    public ArcanoidLogic(int ballX, int ballY, int curentPosition, int enemyPosition)
    {
       this.ballX=ballX;
         this.ballY=ballY;
        this.curentPosition=curentPosition;
         this.enemyPosition=enemyPosition;
         this.listofBriks=new ArrayList<BrikBreakeer>() ;

         }

}


