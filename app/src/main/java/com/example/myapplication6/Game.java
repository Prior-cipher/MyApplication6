package com.example.myapplication6;

import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Arrays;

public class Game {

    Boolean[][] blocks;
    Color blocksColor[][];
    Brick currentBrick;
    BrickType nextBrick;
    int score = 0;

    Game() {

        blocks = new Boolean[24][20];
        blocksColor = new Color[24][20];

        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 20; j++) {
                blocks[i][j] = new Boolean(false);
            }


        }

        if (nextBrick == null) {

            nextBrick = BrickType.getRandomBrick();
            currentBrick = new Brick(nextBrick);
            nextBrick = BrickType.getRandomBrick();
        }


    }

    public void takt() {
        placing();
        currentBrick.Down();
    }


    private void downingBlock(int line) {


        score += 100;

        for (int i = line; i > 0; i--) {
            for (int j = 0; j < 20; j++) {
                blocks[i][j] = new Boolean(blocks[i - 1][j]);
            }
        }
        for (int j = 0; j < 10; j++) {
            blocks[0][j] = new Boolean(false);
        }

    }

    private void placing() {
        for (Coordinate c : currentBrick.coordinates) {
            if ((c.y + 1) > 23 || blocks[c.y + 1][c.x] == true) {
                for (Coordinate cInner : currentBrick.coordinates) {
                    blocks[cInner.y][cInner.x] = true;
                }
                checkLine();
                nextBrick();

                break;
            }
        }
    }

    private void nextBrick() {
        this.currentBrick = new Brick(nextBrick);
        this.nextBrick = BrickType.getRandomBrick();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)

    public void checkMoveLeft()
    {
        boolean check = true;
        for (Coordinate c : currentBrick.coordinates) {
            if (c.x - 1 < 0 || blocks[c.y][c.x - 1]) {
                check = false;
                break;
            }
        }

        if (check) {
            currentBrick.MoveLeft();
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void checkMoveRight()
    {

        boolean check = true;
        for (Coordinate c : currentBrick.coordinates) {
            if ((c.x + 1) >= 20 || blocks[c.y][c.x + 1]) {
                check = false;
                break;
            }
        }

        if (check) {
            currentBrick.MoveRight();

        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void checkRotation() {


        Brick testBrick = new Brick(currentBrick.type);
        for (int i = 0; i < 4; i++) {
            testBrick.coordinates[i].y = currentBrick.coordinates[i].y;
            testBrick.coordinates[i].x = currentBrick.coordinates[i].x;
        }
        testBrick.Rotate();

        boolean check = true;
        for (Coordinate c : testBrick.coordinates) {
            if ((c.x) >= 20 || c.x < 0 || blocks[c.y][c.x]) {
                check = false;
                break;
            }
        }
        if (check) {
            currentBrick.Rotate();
        }
    }

    public void checkLine() {

        for (int i = 0; i < 24; i++) {
            boolean flag = true;
            for (int j = 0; j < 20; j++) {
                if (!blocks[i][j]) {
                    flag = false;
                    break;
                }
            }

            if (flag) {
                downingBlock(i);
            }
        }

    }


}
