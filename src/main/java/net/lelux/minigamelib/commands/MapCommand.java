package net.lelux.minigamelib.commands;

import net.lelux.minigamelib.Minigame;
import net.lelux.minigamelib.config.GameMap;
import net.lelux.minigamelib.utils.Languages;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MapCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender.hasPermission("minigamelib.map")) {
            GameMap map = Minigame.getGameConfig().getMap();
            sender.sendMessage(Languages.getString("map_command",
                    map.getTeamCount() + "x" + map.getTeamSize(), map.getName()));
            return true;
        }
        return false;
    }
}
