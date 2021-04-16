package me.nico.core.Events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.perms.Role;

import me.nico.core.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class DeathAnnounce implements Listener {
	private Main main;

	public DeathAnnounce(Main main) {
		this.main = main;
	}

	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent event) {

		if (main.getConfig().getString("DeathAnnouncer.enabled") == "false") {
			return;
		} else {

			Player player = event.getEntity();
			Player pKiller = null;
			try {
				pKiller = player.getKiller();
				if (pKiller == null) {
					return;
				}
			} catch (Exception e) {
				System.out.print(e);
			}

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

			ItemStack item = pKiller.getItemInHand();
			String name;

			ArrayList<String> loreArray = new ArrayList<String>();
			List<String> enchantmentlist = new ArrayList<String>();

			if (pKiller.getItemInHand() == null) {
				name = "null";
				// lore = "null";
			} else if (pKiller.getItemInHand().getItemMeta() == null) {
				name = pKiller.getItemInHand().getType().toString();
				if (item.getType() == null || item.getType() == Material.AIR) {
					name = "FIST";
				}

			} else if (pKiller.getItemInHand().getItemMeta().getDisplayName() == null) {
				name = pKiller.getItemInHand().getType().toString();
				if(item.getType() == Material.DIAMOND_HOE) {
					name = "hoe";
				}

			} else {
				name = pKiller.getItemInHand().getItemMeta().getDisplayName();

				if(item.getType() != Material.BOW) {
				List<String> lores = pKiller.getItemInHand().getItemMeta().getLore();
				for (String s : lores) {
					loreArray.add(s);
				}
				}


			}

			
			if(item.getType() == Material.DIAMOND_SWORD || item.getType() == Material.BOW || item.getType() == Material.DIAMOND_AXE) {
					if (item.containsEnchantment(Enchantment.DAMAGE_ALL)) {
					int value = item.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
					enchantmentlist.add("Sharpness " + value);
					}
					if (item.containsEnchantment(Enchantment.DAMAGE_UNDEAD)) {
					int value = item.getEnchantmentLevel(Enchantment.DAMAGE_UNDEAD);
					enchantmentlist.add("Smite " + value);
					}
					if (item.containsEnchantment(Enchantment.DAMAGE_ARTHROPODS)) {
					int value = item.getEnchantmentLevel(Enchantment.DAMAGE_ARTHROPODS);
					enchantmentlist.add("Bane Of Arthropods " + value);
					}
					if (item.containsEnchantment(Enchantment.KNOCKBACK)) {
					int value = item.getEnchantmentLevel(Enchantment.KNOCKBACK);
					enchantmentlist.add("Knockback " + value);
					}
					if (item.containsEnchantment(Enchantment.FIRE_ASPECT)) {
					int value = item.getEnchantmentLevel(Enchantment.FIRE_ASPECT);
					enchantmentlist.add("Fire Aspect " + value);
					}
					if (item.containsEnchantment(Enchantment.LOOT_BONUS_MOBS)) {
					int value = item.getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
					enchantmentlist.add("Looting " + value);
					}
					if (item.containsEnchantment(Enchantment.ARROW_DAMAGE)) {
					int value = item.getEnchantmentLevel(Enchantment.ARROW_DAMAGE);
					enchantmentlist.add("Power " + value);
					}
					if (item.containsEnchantment(Enchantment.ARROW_KNOCKBACK)) {
					int value = item.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK);
					enchantmentlist.add("Punch " + value);
					}
					if (item.containsEnchantment(Enchantment.ARROW_FIRE)) {
					int value = item.getEnchantmentLevel(Enchantment.ARROW_FIRE);
					enchantmentlist.add("Flame " + value);
					}
					if (item.containsEnchantment(Enchantment.ARROW_INFINITE)) {
					int value = item.getEnchantmentLevel(Enchantment.ARROW_INFINITE);
					enchantmentlist.add("Infinity " + value);
					}
					if (item.containsEnchantment(Enchantment.DURABILITY)) {
					int value = item.getEnchantmentLevel(Enchantment.DURABILITY);
					enchantmentlist.add("Unbreaking " + value);
					}

			}



			
			
			
			Bukkit.broadcastMessage(pKiller.getName());
			Bukkit.broadcastMessage(player.getName());
			Bukkit.broadcastMessage(name);

			// Bukkit.broadcastMessage(pKiller.getItemInHand().toString());
			// Bukkit.broadcastMessage(pKiller.getItemInHand().getType().toString());
			// Bukkit.broadcastMessage(pKiller.getItemInHand().getItemMeta().getDisplayName());

			TextComponent tc = new TextComponent();
			tc.setText(ChatColor.translateAlternateColorCodes('&', full.replace("{PLAYER}", player.getName())
					.replace("{KILLER}", pKiller.getName()).replace("{ITEM}", name)));
			
			if(enchantmentlist.size() == 0 && loreArray.size() == 0) {
				tc.setHoverEvent(
						// new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(" " +
						// item.getType()).create()));
						new HoverEvent(HoverEvent.Action.SHOW_TEXT,
								new ComponentBuilder("" + name).create()));
			}
			if(enchantmentlist.size() != 0 && loreArray.size() == 0) {
				tc.setHoverEvent(
						// new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(" " +
						// item.getType()).create()));
						new HoverEvent(HoverEvent.Action.SHOW_TEXT,
								new ComponentBuilder("" + name + "\n"
										+ enchantmentlist.toString().replace(", ", "\n").replace("[", "").replace("]", "")).create()));	
			}
			
			
			if(enchantmentlist.size() == 0 && loreArray.size() != 0) {
				tc.setHoverEvent(
						// new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(" " +
						// item.getType()).create()));
						new HoverEvent(HoverEvent.Action.SHOW_TEXT,
								new ComponentBuilder("" + name + "\n"
										+ loreArray.toString().replace(", ", "\n").replace("[", "").replace("]", ""))
												.create()));
			}
			
			
			if(enchantmentlist.size() != 0 && loreArray.size() != 0) {
					tc.setHoverEvent(
					// new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(" " +
					// item.getType()).create()));
					new HoverEvent(HoverEvent.Action.SHOW_TEXT,
							new ComponentBuilder("" + name + "\n"
									+ enchantmentlist.toString().replace(", ", "\n").replace("[", "").replace("]", "")
									+ "\n" + loreArray.toString().replace(", ", "\n").replace("[", "").replace("]", ""))
											.create()));
			}
			


			// pKiller.spigot().sendMessage(tc);
			// player.spigot().sendMessage(tc);

			for (Player p : Bukkit.getOnlinePlayers()) {
				p.spigot().sendMessage(tc);
			}

			// player.sendMessage(tc);
			// pKiller.spigot.sendMessage(tc);

			// Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&',
			// full.replace("{PLAYER}", player.getName()).replace("{KILLER}",
			// pKiller.getName()) + "item "));
			if (main.getConfig().getString("DeathAnnouncer.soundOn") == "true") {
				for (Player all : Bukkit.getServer().getOnlinePlayers()) {
					all.playSound(all.getLocation(), Sound.ORB_PICKUP, 1, 1);
				}
			}
		}
	}
}