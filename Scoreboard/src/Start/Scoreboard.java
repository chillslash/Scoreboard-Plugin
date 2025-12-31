package Start;

import java.text.SimpleDateFormat;
import java.util.*;

import Database.YMLLoader;
import Listener.PlayerListener;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;

import java.io.IOException;

public class Scoreboard extends JavaPlugin{

    private static PluginManager pm = Bukkit.getPluginManager();
    private static FileConfiguration kills, parkour;

    //TODO add onDisable to disable database

    public void onEnable(){
        pm.registerEvents(new PlayerListener(), this);

        kills = new YMLLoader("plugins/Scoreboard", "kills.yml").getFileConfiguration();
        parkour = new YMLLoader("plugins/Scoreboard", "parkour.yml").getFileConfiguration();
        saveKills();
        saveParkour();

    }
    private static void saveKills() {
        try {
            kills.save("plugins/Scoreboard/kills.yml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void saveParkour() {
        try {
            parkour.save("plugins/Scoreboard/parkour.yml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addParkour(Player player){
        Map<String, Object> dict = parkour.getValues(true);
        for (String key : dict.keySet()){
            if (key.equals(player.getUniqueId().toString())){
                return;
            }
        }
        parkour.set(player.getUniqueId().toString(), player.getName()+ " : " +new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z").format(new Date(System.currentTimeMillis())));
        saveParkour();
        for (Player user : Bukkit.getServer().getOnlinePlayers()){
            user.sendMessage("§b§l>> §d" + player.getName() + " §6has completed the Parkour!");
        }
    }

    public static void updateKills(String uuid){
        Map<String, Object> dict = kills.getValues(true);
        loop: {
            for (String key : dict.keySet()) {
                if (key.equals(uuid)) {
                    dict.replace(uuid, dict.get(uuid), ((int) dict.get(uuid)) + 1);
                    break loop;
                }
            } dict.put(uuid, 1);
        }
        for (String key : dict.keySet())
            kills.set(key, dict.get(key));
        saveKills();
    }

    public static Map<String, Object> returnKills(){
        kills = new YMLLoader("plugins/Scoreboard", "kills.yml").getFileConfiguration();
        return kills.getValues(true);
    }

    public static void create(Player player){
        Map<String, Object> dict = kills.getValues(true);
        if (!dict.containsKey(player.getUniqueId().toString()))
            kills.set(player.getUniqueId().toString(), 0);
            saveKills();
    }
}
