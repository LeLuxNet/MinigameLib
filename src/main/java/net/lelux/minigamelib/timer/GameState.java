package net.lelux.minigamelib.timer;

import net.lelux.minigamelib.Minigame;

public enum GameState {

    LOBBY,
    INGAME,
    END;

    private static GameState state;

    public static void set(GameState state) {
        if(GameState.state != state) {
            GameState.state = state;
            Minigame.changedGameState();
        }
    }

    public static GameState get() {
        return state;
    }

    public static boolean is(GameState state) {
        return GameState.state == state;
    }
}
