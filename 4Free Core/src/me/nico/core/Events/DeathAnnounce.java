package me.nico.core.Events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.perms.Role;

import me.nico.core.Main;
import net.md_5.bungee.api.ChatColor;

public class DeathAnnounce implements Listener {
	private Main main;

	public DeathAnnounce(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent event) {
		
		if(main.getConfig().getString("DeathAnnouncer.enabled") == "false") {
			return;
		} else {
		
		Player player = event.getEntity();
		Player pKiller = null;
		try {
			pKiller = player.getKiller(); 
			if(pKiller == null) {
				return;
			}
		} catch (Exception e) {
			System.out.print(e);
		}
//		Bukkit.broadcastMessage(pKiller.toString()); 
		
		
		FPlayer fPlayer = FPlayers.getInstance().getByPlayer(Bukkit.getServer().getPlayer(player.getName()));
		Faction fac = fPlayer.getFaction();
		List<FPlayer> owners = fac.getFPlayersWhereRole(Role.ADMIN);

		
		String staffMsg = main.getConfig().getString("DeathAnnouncer.Messages.staff-messages.l1") + "\n"
				+ main.getConfig().getString("DeathAnnouncer.Messages.staff-messages.l2");
		String partnerMsg = main.getConfig().getString("DeathAnnouncer.Messages.partner-messages.l1") + "\n"
				+ main.getConfig().getString("DeathAnnouncer.Messages.partner-messages.l2");
		String leaderMsg = main.getConfig().getString("DeathAnnouncer.Messages.leader-messages.l1") + "\n"
				+ main.getConfig().getString("DeathAnnouncer.Messages.leader-messages.l2");
		String normalMsh = main.getConfig().getString("DeathAnnouncer.Messages.normal-messages.l1") + "\n"
				+ main.getConfig().getString("DeathAnnouncer.Messages.normal-messages.l2");
		String partnerPermission = main.getConfig().getString("DeathAnnouncer.config.Partner_permission");
		String staffPermission = main.getConfig().getString("DeathAnnouncer.config.Staff_permission");
		String full = normalMsh;
		

		
		
		for (FPlayer owner : owners) {
			if (fPlayer == owner) {
				full = leaderMsg;
			}
		}
		if (player.hasPermission(partnerPermission)) {
			full = partnerMsg;
		}
		if (player.hasPermission(staffPermission)) {
			full = staffMsg;
		}
		
		Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', full.replace("{PLAYER}", player.getName()).replace("{KILLER}", pKiller.getName())));
		if (main.getConfig().getString("DeathAnnouncer.soundOn") == "true") {
			for (Player all : Bukkit.getServer().getOnlinePlayers()) {
				all.playSound(all.getLocation(), Sound.ORB_PICKUP, 1, 1);
			}
		}
	}
}
}