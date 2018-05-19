package net.lelux.minigamelib.utils;

import org.bukkit.Bukkit;

import java.util.logging.Level;

public class Log {

    public static final String PREFIX = "[MinigameLib] ";

    public static void info(String msg, boolean prefix) {
        log(Level.INFO, msg, prefix);
    }

    public static void warn(String msg, boolean prefix) {
        log(Level.WARNING, msg, prefix);
    }

    public static void err(String msg, boolean prefix) {
        log(Level.SEVERE, msg, prefix);
    }

    private static void log(Level level, String msg, boolean prefix) {
        Bukkit.getLogger().log(level, prefix ? PREFIX + msg : msg);
    }
}
