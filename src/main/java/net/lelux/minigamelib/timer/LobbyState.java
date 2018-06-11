package net.lelux.minigamelib.timer;

import net.lelux.minigamelib.Minigame;

public enum LobbyState {

    MAP_VOTING,
    TEAM_SELECTION;

    private static LobbyState state;

    public static void set(LobbyState state) {
        if(LobbyState.state != state) {
            LobbyState.state = state;
            Minigame.changedLobbyState();
        }
    }

    public static LobbyState get() {
        return state;
    }

    public static boolean is(LobbyState state) {
        return LobbyState.state == state;
    }
}
