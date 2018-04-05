package net.lelux.minigamelib.connections;

import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL extends Connection<java.sql.Connection> {

    private final String username;
    private final String password;
    private final String host;
    private final int port;
    private final String database;
    private final String table;

    public MySQL(String username, String password, String host, int port, String database, String table) {
        super("MySQL");
        this.username = username;
        this.password = password;
        this.host = host;
        this.port = port;
        this.database = database;
        this.table = table;
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
}