package net.lelux.minigamelib.connections;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQL extends Connection<java.sql.Connection> {

    private final String username;
    private final String password;
    private final String host;
    private final int port;
    private final String database;

    public MySQL(String username, String password, String database, String host, int port) {
        super("MySQL");
        this.username = username;
        this.password = password;
        this.database = database;
        this.host = host;
        this.port = port;
    }

    public MySQL(String username, String password, String database, String host) {
        this(username, password, database, host, 3306);
    }

    public MySQL(String username, String password, String database) {
        this(username, password, database, "localhost");
    }

    public boolean forceConnect() throws SQLException {
        con = DriverManager.getConnection("jdbc:mysql://" + host + ":"
                + port + "/" + database, username, password);
        return true;
    }

    public boolean forceDisconnect() throws SQLException {
        con.close();
        return true;
    }

    public ResultSet getResult(String cmd) {
        if (isConnected()) {
            try {
                return con.createStatement().executeQuery(cmd);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void update(String cmd) {
        if (isConnected()) {
            try {
                con.createStatement().executeUpdate(cmd);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}