package net.lelux.minigamelib.timer;

public enum GameState {

    LOBBY,
    INGAME,
    END;

    private static GameState state;

    public static void set(GameState state) {
        GameState.state = state;
    }

    public static GameState get() {
        return state;
    }

    public static boolean is(GameState state) {
        return GameState.state == state;
    }
}
