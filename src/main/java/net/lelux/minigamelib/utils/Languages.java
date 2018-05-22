package net.lelux.minigamelib.utils;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class Languages {

    private static ResourceBundle bundle;

    public static void load() {
        bundle = ResourceBundle.getBundle("lang", Locale.getDefault());
    }

    public static void unload() {
        bundle = null;
    }

    public static String getString(String msg, String... val) {
        if (bundle == null) {
            load();
        }
        MessageFormat format = new MessageFormat(bundle.getString(msg), Locale.getDefault());
        return format.format(val);
    }
}