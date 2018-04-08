package net.lelux.minigamelib.utils;

public class ChestUtils {

    public static int calcSize(int val) {
        return (int) Math.ceil(val / 9D) * 9;
    }
}
