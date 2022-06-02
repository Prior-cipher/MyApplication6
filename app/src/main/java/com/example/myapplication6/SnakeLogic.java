package com.example.myapplication6;

import java.util.ArrayList;
import java.util.Arrays;

public class SnakeLogic
{
    int[] h = new int[2];
    ArrayList<int[]>  zmey = new ArrayList<int[]>(Arrays.asList(new int[]{5, 0}, new int[]{6, 0}));
    String direction = "top";
    boolean stat = false;
    boolean status=true;
    boolean turnRight=true;
    boolean turnLeft= true;

    SnakeLogic()
    {




    }

    int getRndInteger(int min, int max)
    {
        return (int) (Math.floor(Math.random() * (max - min + 1)) + min);
    }





    int[] setFood()
    {

        int x = getRndInteger(0, 19);
        int y = getRndInteger(0, 19);
        while (!chekIncludes(x, y))
        {
            x = getRndInteger(0, 19);
            y = getRndInteger(0, 19);
        }
        int[] ar = {x, y};
        return ar;
    }


    boolean chekIncludes(int x, int y)
    {
        boolean flag = false;
        for (int i = 0; i < zmey.size(); i++)
        {
            if (zmey.get(i)[0] == y && zmey.get(i)[1] == x)
            {

                return true;
            }
        }

        return false;
    }


    boolean CheckZmey()
    {
        for (int i = 0; i < zmey.size() - 1; i++)
        {
            for (int j = i + 1; j < zmey.size(); j++)
            {

                if (zmey.get(i)[0] == zmey.get(j)[0] && zmey.get(i)[1] == zmey.get(j)[1]) {

                    return false;
                }

            }

        }

        return true;
    }




    void Game()
    {

        turnLeft=true;
        turnRight=true;

        if (!stat)
        {
            h = setFood();

            stat = true;

        }


        int[] pred = new int[]{zmey.get(0)[0], zmey.get(0)[1]};
        switch (direction)
        {
            case "top":
                pred[0] = pred[0] - 1;
                break;
            case "down":
                pred[0] = pred[0]+1;
                break;
            case "left":
                pred[1] = pred[1] - 1;
                break;
            case "right":
                pred[1] = pred[1] + 1;
                break;
            default:
                break;
        }

        if (pred[0] ==  h[0] &&pred[1] ==  h[1])
        {


            zmey.add(0,h);

            h = setFood();

        } else

            {
                if (pred[0] < 0 || pred[0] > 20 || pred[1] < 0 || pred[1] > 20 )
                {
                    status=false;
                    return;
                }


            for (int index = zmey.size() - 1; index > 0; index--)
            {

                zmey.set(index, zmey.get(index - 1));

            }
            switch (direction)
            {
                case "top":
                    zmey.set(0, new int[]{zmey.get(0)[0] - 1, zmey.get(0)[1]});
                    break;
                case "down":
                    zmey.set(0, new int[]{zmey.get(0)[0] + 1, zmey.get(0)[1]});
                    break;
                case "left":
                    zmey.set(0, new int[]{zmey.get(0)[0], zmey.get(0)[1] - 1});
                    break;
                case "right":
                    zmey.set(0, new int[]{zmey.get(0)[0], zmey.get(0)[1] + 1});
                    break;
                default:
                    break;
            }
        }


        if (zmey.get(0)[1] < 0 || zmey.get(0)[1] > 20 || zmey.get(0)[0] < 0 || zmey.get(0)[0] > 20 || !CheckZmey())
        {
        status=false;
        }


    }

    public void turnRight()
    {
        if(turnRight){
            switch (direction) {
                case "top":
                    direction="right";
                    break;
                case "down":
                    direction="left";
                    break;
                case "left":
                    direction="top";
                    break;
                case "right":
                    direction="down";
                    break;
                default:
                    break;

            }
            turnLeft=true;
            turnRight=false;
    }
    }

    public void turnLeft()
    {
        if(turnLeft)
        {
            switch (direction)
            {
                case "top":
                    direction="left";
                    break;
                case "down":
                    direction="right";
                    break;
                case "left":
                    direction="down";
                    break;
                case "right":
                    direction="top";
                    break;
                default:
                    break;
            }
            turnLeft=false;
            turnRight=true;
        }

    }
}
