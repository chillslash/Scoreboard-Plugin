package Listener;

import Start.Scoreboard;
import Scoreboard.Board;

import com.mojang.authlib.yggdrasil.response.User;
import com.onarandombox.MultiversePortals.event.MVPortalEvent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.Score;


public class PlayerListener implements Listener {

    private String worldName = "Sumo";

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerPortalEvent(MVPortalEvent event){
        if (event.getSendingPortal().getName().equals("Fly2")){
            Player player = event.getTeleportee();

            if(player.getKiller() != null && player.getKiller() != player) {
                Player killer = player.getKiller();
                player.setHealth(20.0);
                Scoreboard.updateKills(killer.getUniqueId().toString());
                Board.updateBoard(event.getFrom().getWorld());
                killer.playSound(killer.getLocation(), Sound.ORB_PICKUP, 2, 1);
            }
        }
    }

    @EventHandler
    public void finish(PlayerInteractEvent event){
        try {
            if (event.getClickedBlock().toString().equals("CraftBlock{chunk=CraftChunk{x=-3z=25},x=-36,y=53,z=414,type=STONE_PLATE,data=0}")) {
                Scoreboard.addParkour(event.getPlayer());
            }
        } catch (Exception e) { }
    }

    @EventHandler
    public void join(PlayerChangedWorldEvent event){
        if (event.getPlayer().getWorld().getName().equals(worldName)) {
            Scoreboard.create(event.getPlayer());
            Board.start(event.getPlayer());
        } else if (event.getFrom().getName().equals(worldName) && !event.getPlayer().getWorld().getName().equals(worldName))
            Board.disableBoard(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent event) {
        if (event.getEntity().getWorld().getName().equals("Parkour")){
            event.getEntity().spigot().respawn();
            event.getEntity().setFireTicks(0);
            event.getEntity().addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100, 0));
        }

    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        if (event.getEntity() instanceof Player) {
            if (event.getEntity().getWorld().getName().equals("Parkour") && event.getCause().equals(EntityDamageEvent.DamageCause.VOID)) {
                ((Player) event.getEntity()).setHealth(0);
            }
        }
    }
}



