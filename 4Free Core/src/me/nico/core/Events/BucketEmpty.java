package me.nico.core.Events;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.inventory.ItemStack;

import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;

import me.nico.core.Main;
import net.ess3.api.Economy;
import net.md_5.bungee.api.ChatColor;

public class BucketEmpty implements Listener {

	private Main main;

	public BucketEmpty(Main main) {
		this.main = main;
	}

	@SuppressWarnings({ "deprecation", "static-access" })
	@EventHandler
	public void onBucketEmptyEvent(PlayerBucketEmptyEvent event)
			throws UserDoesNotExistException, NoLoanPermittedException {
//			event.setCancelled(true); //export this now
		Player player = event.getPlayer();
		ItemStack item = player.getItemInHand();
		int cost = main.getConfig().getInt("MegaBucket.config.cost");

		if (!main.getConfig().getBoolean("MegaBucket.enabled")) {
			return;
		}

		if (item.getItemMeta().getDisplayName() == null) {
			// Bukkit.broadcastMessage("item is not a megabucket");
			return;
		}

		if (!player.hasPermission(main.getConfig().getString("MegaBucket.config.bypass_permission"))) {
			if (Economy.hasLess(player.getName(), cost)) {
				String less = main.getConfig().getString("MegaBucket.messages.nofunds");
				player.sendMessage(ChatColor.translateAlternateColorCodes('&',
						less.replace("{COST}", main.getConfig().getString("MegaBucket.config.cost"))));
				event.setCancelled(true);
				return;
			}
		}

//			Block factionBlock = event.getBlockClicked();
//			FLocation flocation = new FLocation(factionBlock.getLocation());
//			Faction fac = Board.getInstance().getFactionAt(flocation); 
//			FPlayer fPlayer = FPlayers.getInstance().getByPlayer(Bukkit.getServer().getPlayer(event.getPlayer().getName()));
//			if(!fac.getOnlinePlayers().contains(fPlayer)) {
//				String nofac = main.getConfig().getString("MegaBucket.messages.notFacClaims");
//				player.sendMessage(ChatColor.translateAlternateColorCodes('&', nofac));
//				event.setCancelled(true);
//				return;
//				
//			}

		BlockFace face = event.getBlockFace();
		int loc;
		Material block = Material.WATER;
		Block placedOn = event.getBlockClicked();
		switch (face.toString().toLowerCase()) {
		case "west":
			loc = 0;
			break;
		case "north":
			loc = 1;
			break;
		case "east":
			loc = 2;
			break;
		case "south":
			loc = 3;
			break;
		case "down":
			loc = 4;
			break;
		case "up":
			loc = 5;
			break;
		default:
			return;
		}

		Material blockrel = placedOn.getRelative(main.ints.get(loc)[0], main.ints.get(loc)[1], main.ints.get(loc)[2])
				.getType();
		byte blockrel2 = placedOn.getRelative(main.ints.get(loc)[0], main.ints.get(loc)[1], main.ints.get(loc)[2])
				.getData();
		if ((blockrel.toString() != "AIR")) {
			if ((blockrel.toString() == "STATIONARY_WATER")) {
				// Bukkit.broadcastMessage("Includes WATER");
				if (blockrel2 == 0) {
					// Bukkit.broadcastMessage("ID ALSO = 0 GG");
					event.setCancelled(true);
					return;
				} else {

					try {
						Economy.subtract(player.getName(), cost);
						String done = main.getConfig().getString("MegaBucket.messages.charged");
						player.sendMessage(ChatColor.translateAlternateColorCodes('&',
								done.replace("{COST}", main.getConfig().getString("MegaBucket.config.cost"))));
						if (main.getConfig().getBoolean("MegaBucket.soundOn")) {
							player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1, 1);
						}
					} catch (Exception e) {
						player.sendMessage("ERROR");
						return;
					}

					placedOn.getRelative(main.ints.get(loc)[0], main.ints.get(loc)[1], main.ints.get(loc)[2])
							.setType(block);
					event.setCancelled(true);

				}

			} else {
				// player.sendMessage(ChatColor.RED + "Can't use Megabucket here!");
				event.setCancelled(true);
				return;
			}

		} else {

			try {
				Economy.subtract(player.getName(), cost);
				String done = main.getConfig().getString("MegaBucket.messages.charged");
				player.sendMessage(ChatColor.translateAlternateColorCodes('&',
						done.replace("{COST}", main.getConfig().getString("MegaBucket.config.cost"))));
				if (main.getConfig().getBoolean("MegaBucket.soundOn")) {
					player.playSound(player.getLocation(), Sound.ORB_PICKUP, 1, 1);
				}
			} catch (Exception e) {
				player.sendMessage("ERROR");
				return;
			}

			placedOn.getRelative(main.ints.get(loc)[0], main.ints.get(loc)[1], main.ints.get(loc)[2]).setType(block);
			event.setCancelled(true);
		}

	}
}
