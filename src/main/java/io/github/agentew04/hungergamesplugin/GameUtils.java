package io.github.agentew04.hungergamesplugin;

import com.google.common.util.concurrent.AbstractScheduledService;
import com.onarandombox.MultiverseCore.MultiverseCore;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Biome;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class GameUtils {
    private final MultiverseCore core;
    private final HungerGamesPlugin main;
    public GameUtils(HungerGamesPlugin main){
        this.main =main;
        core=main.getMV();
    }
    //region GameVars
    private final List<UUID> PlayersReady = new ArrayList<>();
    private final Map<UUID,Kits> PlayerKit = new HashMap<>();
    private final List<UUID> Players = new ArrayList<>();
    private final List<UUID> Spectators = new ArrayList<>();
    private boolean IsGameStarted = false;

    private Date GameStartTime = null;
    private Date Stage2StartTime = null;
    private Date Stage3StartTime = null;
    private Date Stage4StartTime = null;
    private Date Stage5StartTime = null;
    private Date Stage6StartTime = null;
    private Date Stage7StartTime = null;
    private Runnable BarrierRunnable=null;
    private int BarrierRunnableId=0;
    private BukkitTask BarrierTask = null;

    private UUID Gladiator =null;
    private UUID Gladiated =null;
    private Location GladiatorLastPos=null;
    private Location GladiatedLastPos=null;
    private boolean IsGladiatorStarted=false;

    private int BorderState = 0;
    //endregion

    //region methods
    public void addReadyPlayer(Player player){
        UUID id = player.getUniqueId();
        if(!PlayersReady.contains(id)){
            PlayersReady.add(id);
        }
    }
    public void removeReadyPlayer(Player player){
        UUID id = player.getUniqueId();
        PlayersReady.remove(id);
    }
    public List<Player> getReadyPlayers(){List<Player> players = new ArrayList<>();
        for (UUID id:PlayersReady) {
            players.add(Bukkit.getPlayer(id));
        }
        return players;
    }
    public boolean playersReadyHasPlayer(Player player){
        return PlayersReady.contains(player.getUniqueId());
    }
    public boolean checkReadyPlayer(Player player){
        return PlayersReady.contains(player.getUniqueId());
    }
    public void setPlayerKit(Player player, Kits kit){
        UUID id = player.getUniqueId();
        if(!PlayerKit.containsKey(id)){
            PlayerKit.put(id,kit);
        }else{
            PlayerKit.replace(id, kit);
        }

    }
    public Kits getPlayerKit(Player player){
        UUID id = player.getUniqueId();
        return PlayerKit.getOrDefault(id, Kits.None);
    }
    public void addAlivePlayer(Player player){
        UUID id = player.getUniqueId();
        if(!Players.contains(id)){
            Players.add(id);
        }
    }
    public void removeAlivePlayer(Player player){
        UUID id = player.getUniqueId();
        Players.remove(id);
    }
    public List<Player> getPlayers(){
        List<Player> players = new ArrayList<>();
        for (UUID id: Players) {
            players.add(Bukkit.getPlayer(id));
        }
        return players;
    }
    public void addSpectator(Player player){
        UUID id = player.getUniqueId();
        if(!Spectators.contains(id)){
            Spectators.add(id);
        }
        player.setGameMode(GameMode.SPECTATOR);
        player.teleport(new Location(Bukkit.getWorld("arena"),0,62,0));
    }
    public void removeSpectator(Player player){
        UUID id = player.getUniqueId();
        Spectators.remove(id);
    }
    public List<Player> getSpectators(){
        List<Player> players = new ArrayList<>();
        for (UUID id: Spectators) {
            players.add(Bukkit.getPlayer(id));
        }
        return players;
    }
    public int getRemainingPlayers(){
        return Players.size();
    }
    public boolean getStartStatus(){
        return  IsGameStarted;
    }
    public void setStartStatus(boolean started){
        IsGameStarted=started;
    }
    public void setGladiator(Player player){
        if(player==null){
            Gladiator=null;
        }else{
            Gladiator =player.getUniqueId();
        }
    }
    public Player getGladiator(){
        return Bukkit.getPlayer(Gladiator);
    }
    public void setGladiatorLastPos(Location pos){
        GladiatorLastPos =pos;
    }
    public Location getGladiatorLastPos(){
        return GladiatorLastPos;
    }
    public void setGladiatedLastPos(Location pos){
        GladiatedLastPos = pos;
    }
    public Location getGladiatedLastPos(){
        return GladiatedLastPos;
    }
    public void setGladiated(Player player){
        Gladiated =player.getUniqueId();
    }
    public Player getGladiated(){
        return Bukkit.getPlayer(Gladiated);
    }
    public boolean getGladiatorStatus(){
        return IsGladiatorStarted;
    }
    public void setGladiatorStatus(boolean gladiatorStatus){
        IsGladiatorStarted=gladiatorStatus;
    }
    public int getBorderState(){
        return BorderState;
    }
    public void setBorderState(int borderState){
        BorderState=borderState;
    }

    //endregion

    public void setupBorder(){
        this.GameStartTime=new Date();
        Stage2StartTime=addSeconds(GameStartTime,600);
        Stage3StartTime=addSeconds(Stage2StartTime,300);
        Stage4StartTime=addSeconds(Stage3StartTime,300);
        Stage5StartTime=addSeconds(Stage4StartTime,150);
        Stage6StartTime=addSeconds(Stage5StartTime,300);
        Stage7StartTime=addSeconds(Stage6StartTime,150);

        BarrierRunnable= () -> {
            WorldBorder wb=Bukkit.getWorld("arena").getWorldBorder();
            Date now = new Date();

            if(now.after(Stage7StartTime)){
                if(wb.getSize()==10){
                    return;
                }else{
                    wb.setSize(10);
                }
            }else if(now.after(Stage6StartTime)) {
                wb.setSize(10,150);
            }else if(now.after(Stage5StartTime)){
                if(wb.getSize()==250){
                    return;
                }else{
                    wb.setSize(250);
                }
            }else if(now.after(Stage4StartTime)){
                wb.setSize(250,150);
            }else if(now.after(Stage3StartTime)){
                if(wb.getSize()==500){
                    return;
                }else{
                    wb.setSize(500);
                }
            }else if(now.after(Stage2StartTime)){
                wb.setSize(500,300);
            }
        };
        BukkitScheduler s = Bukkit.getScheduler();
        BarrierTask=s.runTaskTimer(main,BarrierRunnable,0L,600L);
        BarrierRunnableId=BarrierTask.getTaskId();
    }

    private Location getRandomSpawn(){
        World targetWorld = Bukkit.getWorld("arena");
        assert targetWorld != null;
        int maxrange=500;
        int minrange=-500;
        double x = ThreadLocalRandom.current().nextInt(minrange, maxrange + 1);
        double z = ThreadLocalRandom.current().nextInt(minrange, maxrange + 1);
        double y=targetWorld.getHighestBlockYAt((int)x,(int)z);
        Biome spawnBiome = targetWorld.getBiome((int)x,(int)y,(int)z);
        while (isOcean(spawnBiome)){
            x=ThreadLocalRandom.current().nextInt(minrange, maxrange + 1);
            z = ThreadLocalRandom.current().nextInt(minrange, maxrange + 1);
            y = targetWorld.getHighestBlockYAt((int)x,(int)z);
            spawnBiome = targetWorld.getBiome((int)x,(int)y,(int)z);
        }
        return new Location(targetWorld,x,y,z);
    }
    private boolean isOcean(Biome biome){
        return biome == Biome.OCEAN || biome == Biome.COLD_OCEAN || biome == Biome.DEEP_COLD_OCEAN ||
                biome == Biome.DEEP_FROZEN_OCEAN || biome == Biome.DEEP_OCEAN || biome == Biome.DEEP_LUKEWARM_OCEAN ||
                biome == Biome.DEEP_WARM_OCEAN || biome == Biome.FROZEN_OCEAN || biome == Biome.LUKEWARM_OCEAN ||
                biome == Biome.WARM_OCEAN;
    }
    private void preparePlayer(Player player){
        player.getInventory().clear();
        player.setHealth(player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue());
        player.setFoodLevel(20);
        player.setSaturation(20.0f);
        player.setExp(0.0f);
        player.setLevel(0);
        player.setGameMode(GameMode.SURVIVAL);
        player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION,6000,0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,6000,4));
        player.getInventory().addItem(new ItemStack(Material.COMPASS));
    }
    private void giveKitItem(Player player, Kits kit){
        ItemStack item;
        ItemMeta meta;
        switch (kit){
            case LumberJack:
                item=new ItemStack(Material.WOODEN_AXE);
                meta = item.hasItemMeta()?item.getItemMeta():Bukkit.getItemFactory().getItemMeta(item.getType());
                meta.addEnchant(Enchantment.DIG_SPEED,7,true);
                meta.displayName(Component.text("Machado do lenhador",NamedTextColor.DARK_PURPLE));
                meta.addEnchant(Enchantment.DURABILITY,1,false);
                item.setItemMeta(meta);
                break;
            case Gladiator:
                item = new ItemStack(Material.NETHERITE_HOE);
                meta = item.hasItemMeta()?item.getItemMeta():Bukkit.getItemFactory().getItemMeta(item.getType());
                meta.addEnchant(Enchantment.ARROW_INFINITE,1,true);
                meta.setUnbreakable(true);
                meta.displayName(Component.text("A foice do x1",NamedTextColor.DARK_PURPLE));
                break;
            case Fisherman:
                item = new ItemStack(Material.FISHING_ROD);
                meta = item.hasItemMeta()?item.getItemMeta():Bukkit.getItemFactory().getItemMeta(item.getType());
                meta.addEnchant(Enchantment.LURE,1,true);
                meta.setUnbreakable(true);
                meta.displayName(Component.text("Vara do pescador",NamedTextColor.DARK_PURPLE));
                item.setItemMeta(meta);
                break;
            case Kangaro:
                item = new ItemStack(Material.FIREWORK_ROCKET);
                meta = item.hasItemMeta()?item.getItemMeta():Bukkit.getItemFactory().getItemMeta(item.getType());
                meta.displayName(Component.text("Pulo do Canguru",NamedTextColor.DARK_PURPLE));
                item.setItemMeta(meta);
                break;
            case Grapler:
                item = new ItemStack(Material.TRIPWIRE_HOOK);
                meta = item.hasItemMeta()?item.getItemMeta():Bukkit.getItemFactory().getItemMeta(item.getType());
                meta.displayName(Component.text("Grapling Hook",NamedTextColor.DARK_PURPLE));
                item.setItemMeta(meta);
                break;
            case WolfTamer:
                item = new ItemStack(Material.WOLF_SPAWN_EGG,10);
                meta = item.hasItemMeta()?item.getItemMeta():Bukkit.getItemFactory().getItemMeta(item.getType());
                meta.displayName(Component.text("Lobos da floresta",NamedTextColor.DARK_PURPLE));
                item.setItemMeta(meta);
                ItemStack item2 = new ItemStack(Material.BONE,64);
                meta = item2.hasItemMeta()?item2.getItemMeta():Bukkit.getItemFactory().getItemMeta(item2.getType());
                meta.displayName(Component.text("Biscoito Scooby Doo",NamedTextColor.DARK_PURPLE));

                item2.setItemMeta(meta);
                player.getInventory().addItem(item,item2);
                return;
            case Archer:
                item = new ItemStack(Material.BOW);
                meta = item.hasItemMeta()?item.getItemMeta():Bukkit.getItemFactory().getItemMeta(item.getType());
                meta.displayName(Component.text("Arco-Escudo Imortal",NamedTextColor.DARK_PURPLE));
                meta.setUnbreakable(true);
                meta.addEnchant(Enchantment.ARROW_INFINITE,1,true);
                item.setItemMeta(meta);
                break;
            default:
                item = new ItemStack(Material.AIR);
                break;
        }
        player.getInventory().addItem(item);
    }
    private Date addSeconds(Date d, int seconds){
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.SECOND,seconds);
        return cal.getTime();
    }
    public void StartMatch(){

        setupBorder();

        //region scoreboard
        ScoreboardManager manager= Bukkit.getServer().getScoreboardManager();
        Scoreboard scoreBoard = manager.getNewScoreboard();
        Component title = Component.text("☠", NamedTextColor.DARK_RED)
                .append(Component.text("KILL COUNTER",NamedTextColor.RED, TextDecoration.UNDERLINED))
                .append(Component.text("☠",NamedTextColor.DARK_RED));
        Objective objective = scoreBoard.registerNewObjective("killcount","playerKillCount",title);
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        //endregion

        setStartStatus(true);

        for (Player player : getReadyPlayers()) {

            //manage player
            player.teleport(getRandomSpawn(), PlayerTeleportEvent.TeleportCause.PLUGIN);
            preparePlayer(player);
            giveKitItem(player,getPlayerKit(player));
            player.setScoreboard(scoreBoard);

            addAlivePlayer(player);
        }
        World w = Bukkit.getWorld("arena");
        w.getWorldBorder().setCenter(new Location(w,0,0,0));
        Component msg=Component.text("A barreira está em ",NamedTextColor.GREEN)
                .append(Component.text(1000,NamedTextColor.GREEN ,TextDecoration.BOLD))
                .append(Component.text(" blocos de diâmetro",NamedTextColor.GREEN));
        Bukkit.broadcast(msg);
        w.getWorldBorder().setSize(1000);
    }
}
