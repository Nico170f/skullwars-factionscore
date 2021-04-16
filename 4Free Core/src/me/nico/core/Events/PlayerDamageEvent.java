package me.nico.core.Events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.earth2me.essentials.api.NoLoanPermittedException;
import com.earth2me.essentials.api.UserDoesNotExistException;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;

import me.nico.core.Main;
import net.ess3.api.Economy;
import net.md_5.bungee.api.ChatColor;

public class PlayerDamageEvent implements Listener {

	private Main main;

	public PlayerDamageEvent(Main main) {
		this.main = main;
	}

	@EventHandler
	public void entityDagageEvent(EntityDamageByEntityEvent e) {
		
		if(!main.getConfig().getBoolean("StrengthMod.enabled")) {
			return;
		}
		
        if (!(e.getDamager() instanceof Player) || !(e.getEntity() instanceof Player)) {
            return;
        }
        
        e.getDamager().sendMessage("Took damage:" + e.getEntity());
        
        final Player d = (Player) e.getDamager();
        final Player v = (Player) e.getEntity();
        if (d == null || v == null) 
            return;
        
        if (d.hasPotionEffect(PotionEffectType.INCREASE_DAMAGE)) {
            for (final PotionEffect eff : d.getActivePotionEffects()) {
                if (eff.getType().equals((Object) PotionEffectType.INCREASE_DAMAGE)) {
                    final double div = (eff.getAmplifier() + 1) * 1.3 + 2.0;
                    int dmg;
                    if (e.getDamage() / div <= 1.0) {
                    	int mod = main.getConfig().getInt("StrengthMod.modifier");
                        dmg = (eff.getAmplifier() + 1) * mod + 1;
                    } else {
                        final double flatdmg = 2.0;
                        dmg = (int) (e.getDamage() / div) + (int) ((eff.getAmplifier() + 1) * flatdmg);
                    }
                    e.setDamage((double) dmg);
                    break;
                }
            }
        }

	}
}
