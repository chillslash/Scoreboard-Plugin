package Scoreboard;

import Start.Scoreboard;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.*;

public class Board {

    public static void start(Player player){
        Map<String, Integer> uuidList = rank(player);

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        org.bukkit.scoreboard.Scoreboard board = manager.getNewScoreboard();

        Objective objective = board.registerNewObjective("Kills", "");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName("§6§lTop Kills");

        Score score = objective.getScore("§6zyanmc.net");
        score.setScore(1);
        score = objective.getScore("------------");
        score.setScore(2);

        score = objective.getScore("Your Kills: §a" + uuidList.get(player.getUniqueId().toString()) + " §f[§a#" + uuidList.get("Rank") + "§f]");
        score.setScore(3);
        score = objective.getScore("");
        score.setScore(4);

        int index = 5;
        for (String key : uuidList.keySet()){
            score = objective.getScore(Bukkit.getOfflinePlayer(UUID.fromString(key)).getName() + ": §a" + uuidList.get(key));
            score.setScore(index);
            index += 1;
            if (index == 10)
                break;}

        player.setScoreboard(board);
    }

    public static void disableBoard(Player player){
        player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
    }

    public static void updateBoard(World world){
        for (Player player : world.getPlayers()) {
            player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
            start(player);
        }
    }

    private static Map<String, Integer> rank(Player player){
        Map<String, Object> dict = Scoreboard.returnKills();
        Map<String, Integer> list = new HashMap<>();
        for (String key : dict.keySet())
            list.put(key, (int) dict.get(key));

        List<Integer> listB = new ArrayList<>();
        for (Object value : dict.values())
            listB.add((int) value);

        Collections.sort(listB);
        List<String> listC = new ArrayList<>();

        LinkedHashMap<String, Integer> uuidList = new LinkedHashMap<>();
        for (Integer value : listB) {
            for (Map.Entry<String, Integer> entry : list.entrySet()) {
                if (entry.getValue().equals(value) && !listC.contains(entry.getKey())) {
                    listC.add(entry.getKey());
                    break; } } }

        for (int index = (listB.size()-5) ; index < listB.size() ; index++){
            uuidList.put(listC.get(index), listB.get(index));
        }
        uuidList.put(player.getUniqueId().toString(), list.get(player.getUniqueId().toString()));
        Collections.reverse(listC);
        uuidList.put("Rank", listC.indexOf(player.getUniqueId().toString())+1);
        return uuidList;
    }

}
