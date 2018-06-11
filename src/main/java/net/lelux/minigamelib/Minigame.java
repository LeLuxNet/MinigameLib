package net.lelux.minigamelib;

import javafx.scene.control.cell.MapValueFactory;
import lombok.Getter;
import net.lelux.minigamelib.config.GameConfig;
import net.lelux.minigamelib.config.GameMap;
import net.lelux.minigamelib.config.MapSelection;
import net.lelux.minigamelib.listeners.DamageListener;
import net.lelux.minigamelib.listeners.DeathListener;
import net.lelux.minigamelib.listeners.InteractListener;
import net.lelux.minigamelib.listeners.JoinLeaveListener;
import net.lelux.minigamelib.player.GamePlayer;
import net.lelux.minigamelib.shop.ClickEvent;
import net.lelux.minigamelib.shop.ClickableItem;
import net.lelux.minigamelib.stats.StatsManager;
import net.lelux.minigamelib.teams.ScoreboardManager;
import net.lelux.minigamelib.timer.GameState;
import net.lelux.minigamelib.timer.LobbyState;
import net.lelux.minigamelib.utils.ItemBuilder;
import net.lelux.minigamelib.utils.Languages;
import net.lelux.minigamelib.utils.Log;
import net.lelux.minigamelib.utils.MathUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class Minigame extends JavaPlugin {

    private static GameConfig config;

    @Getter
    private static ScoreboardManager scoreboardManager;

    @Getter
    private static GameMap map;

    @Getter
    private static MapSelection mapSelection;

    @Override
    public void onEnable() {
        Log.info(Languages.getString("start", "PreInitialisation"), true);
        preInitialisation();
        Log.info(Languages.getString("stop", "PreInitialisation"), true);

        mapSelection = new MapSelection();

        Log.info(Languages.getString("start", "Initialisation"), true);
        config = initialisation();
        Log.info(Languages.getString("stop", "Initialisation"), true);

        GameState.set(GameState.LOBBY);
        LobbyState.set(LobbyState.MAP_VOTING);
        config.getMySQL().connect();
        config.getVault().connect();
        initListeners();

        Log.info(Languages.getString("start", "PostInitialisation"), true);
        postInitialisation();
        Log.info(Languages.getString("stop", "PostInitialisation"), true);
    }

    @Override
    public void onDisable() {
        onStop();
        config.getVault().disconnect();
        config.getMySQL().disconnect();
    }

    public void preInitialisation() {}

    public GameConfig initialisation() {
        return null;
    }

    public void postInitialisation() {}

    public void onStop() {}

    public static Minigame getMinigame() {
        return config.getMinigame();
    }

    public static void changedGameState() {
        if (GameState.is(GameState.LOBBY)) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                p.teleport(config.getLobbyLoc());
            }
        } else if (GameState.is(GameState.INGAME)) {
            Bukkit.getServer().getOnlinePlayers()
                    .forEach(p -> p.teleport(GamePlayer.toGamePlayer(p).getTeam().getSpawn()));
        } else if (GameState.is(GameState.END)) {
            Bukkit.getServer().getOnlinePlayers()
                    .forEach(p -> p.teleport(config.getEndLoc()));
            Bukkit.getServer().getOnlinePlayers()
                    .forEach(p -> GamePlayer.toGamePlayer(p).setVisible(true));
            config.getStopCountdown().start();
        }
    }

    public static void changedLobbyState() {
        if(LobbyState.is(LobbyState.MAP_VOTING)) {

        } else if(LobbyState.is(LobbyState.TEAM_SELECTION)) {
            int maxVotes = 0;
            for (GameMap map : config.getMaps()) {
                int votes = map.getVoteCount();
                if (votes > maxVotes) {
                    maxVotes = votes;
                }
            }
            List<GameMap> maps = new ArrayList<>();
            for (GameMap map : config.getMaps()) {
                int votes = map.getVoteCount();
                if (votes >= maxVotes) {
                    maps.add(map);
                }
            }
            map = maps.get(MathUtils.generateRandomInt(maps.size() - 1));
            scoreboardManager = new ScoreboardManager(map.getTeamList());
        }
    }

    private void initListeners() {
        initListener(new DamageListener());
        initListener(new JoinLeaveListener());
        initListener(new DeathListener());
        initListener(new InteractListener());
    }

    private void initListener(Listener listener) {
        Bukkit.getServer().getPluginManager().registerEvents(listener, Minigame.getMinigame());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("map")) {
            if (sender.hasPermission("minigamelib.map")) {
                if (map != null) {
                    sender.sendMessage(Languages.getString("map_command",
                            map.getTeamCount() + "x" + map.getTeamSize(), map.getName()));
                } else {
                    sender.sendMessage(Languages.getString("map_command_noselection"));
                }
                return true;
            }
        } else if (cmd.getName().equalsIgnoreCase("start")) {
            if (sender.hasPermission("minigamelib.start")) {
                if (Minigame.getGameConfig().getStartCountdown().isRunning() &&
                        Minigame.getGameConfig().getStartCountdown().getRunCount() == 1) {
                    Minigame.getGameConfig().getStartCountdown().
                            setCountdown(Minigame.getGameConfig().getSkippedStartCountdown());
                }
                return true;
            }
        }
        return false;
    }

    public static GameConfig getGameConfig() {
        return config;
    }
}
