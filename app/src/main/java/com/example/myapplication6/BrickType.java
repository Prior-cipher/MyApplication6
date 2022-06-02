package com.example.myapplication6;

import java.util.Random;

enum BrickType {
    SQUARE,L_TYPE,LINE,T_TYPE,Z_TYPE;

   Coordinate[][] state;

    private static final Random RANDOM = new Random();

    public static BrickType getRandomBrick() {
        return BrickType.values()[RANDOM.nextInt(5)];
    }
}
