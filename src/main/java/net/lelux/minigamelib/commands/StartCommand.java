package net.lelux.minigamelib.commands;

import net.lelux.minigamelib.Minigame;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StartCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) {
        if (sender.hasPermission("minigamelib.start")) {
            if (Minigame.getGameConfig().getStartCountdown().isRunning() &&
                    Minigame.getGameConfig().getStartCountdown().getRunCount() == 1) {
                Minigame.getGameConfig().getStartCountdown().setCountdown(Minigame.getGameConfig().getSkippedStartCountdown());
                return true;
            }
        }
        return false;
    }
}
