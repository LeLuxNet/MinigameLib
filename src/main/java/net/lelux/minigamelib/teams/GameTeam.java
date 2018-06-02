package net.lelux.minigamelib.teams;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Location;

@Getter
@AllArgsConstructor
public class GameTeam {

    private final String name;
    private final ChatColor textColor;
    private final DyeColor blockColor;
    private final Location spawn;
}
