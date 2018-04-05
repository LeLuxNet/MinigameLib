package net.lelux.minigamelib.connections;

import net.lelux.minigamelib.utils.Languages;
import net.lelux.minigamelib.utils.Log;

public abstract class Connection<E> {

    E con;
    private String name;

    Connection(String name) {
        this.name = name;
    }

    public void connect() {

        if (!isConnected()) {
            boolean success;
            try {
                success = forceConnect();
            } catch (Exception e) {
                success = false;
            }
            if (success) {
                Log.info(Languages.getString("connect", name), true);
            } else {
                Log.err(Languages.getString("connect_err", name), true);
            }
        }
    }

    public void disconnect() {
        if (isConnected()) {
            boolean success;
            try {
                success = forceDisconnect();
            } catch (Exception e) {
                success = false;
            }
            if (success) {
                Log.info(Languages.getString("disconnect", name), true);
            } else {
                Log.err(Languages.getString("disconnect_err", name), true);
            }
        }
    }

    public boolean isConnected() {
        return con != null;
    }

    protected abstract boolean forceConnect() throws Exception;

    protected abstract boolean forceDisconnect() throws Exception;

}
