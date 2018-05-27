package net.lelux.minigamelib.utils;

import java.util.Random;

public class MathUtils {

    private static Random random = new Random();

    public static int generateRandomInt(int min, int max) {
        return random.nextInt(max - min) + min;
    }

    public static int generateRandomInt(int max) {
        return random.nextInt(max);
    }

    public static int calcSize(int val) {
        return (int) Math.ceil(val / 9D) * 9;
    }
}
