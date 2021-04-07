package me.nico.core.Events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.massivecraft.factions.Faction;
import com.massivecraft.factions.event.FactionPlayerEvent;
import com.massivecraft.factions.perms.Role;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;

import me.nico.core.Main;
import net.md_5.bungee.api.ChatColor;

public class DeathAnnounce implements Listener {
	private Main main;

	public DeathAnnounce(Main main) {
		this.main = main;
	}
	
	@EventHandler
	public void PlayerChatEvent(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		
        FPlayer fPlayer = FPlayers.getInstance().getByPlayer(Bukkit.getServer().getPlayer(player.getName()));
        Faction fac = fPlayer.getFaction();
        List<FPlayer> owners = fac.getFPlayersWhereRole(Role.ADMIN);
        for(FPlayer owner : owners) {
            if(fPlayer == owner) {
                Bukkit.broadcastMessage(ChatColor.GREEN + "User is owner" + ChatColor.AQUA + " : Users faction: " + fac.getTag());
            } else {
                Bukkit.broadcastMessage(ChatColor.RED + "User is not owner" + ChatColor.AQUA + " : Users faction: " + fac.getTag());
            }
        }
		
		
		
		

	}
	
	
}
