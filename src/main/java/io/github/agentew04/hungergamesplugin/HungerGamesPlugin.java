package io.github.agentew04.hungergamesplugin;

import com.onarandombox.MultiverseCore.MultiverseCore;
import io.github.agentew04.hungergamesplugin.commands.*;
import io.github.agentew04.hungergamesplugin.events.*;
import io.github.agentew04.hungergamesplugin.kitevents.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

public final class HungerGamesPlugin extends JavaPlugin {

    //endregion

    public GameUtils game;
    private final File usersFile = new File(getDataFolder(),"users.yml");
    private final FileConfiguration usersConfig = YamlConfiguration.loadConfiguration(usersFile);
    @Override
    public void onEnable() {
        //create users.yml
        if(!usersFile.exists()){
            saveResource("users.yml",false);
        }

        //commands
        Objects.requireNonNull(this.getCommand("kit")).setExecutor(new KitCommand(this));
        Objects.requireNonNull(this.getCommand("start")).setExecutor(new StartCommand(this));
        Objects.requireNonNull(this.getCommand("ping")).setExecutor(new PingCommand());
        Objects.requireNonNull(this.getCommand("ready")).setExecutor(new ReadyCommand(this));
        Objects.requireNonNull(this.getCommand("ore")).setExecutor(new OreCommand());

        //listeners
        this.getServer().getPluginManager().registerEvents(new ResoupListener(), this);
        this.getServer().getPluginManager().registerEvents(new GoldenAppleListener(), this);
        this.getServer().getPluginManager().registerEvents(new JoinLeaveListener(this),this);
        this.getServer().getPluginManager().registerEvents(new DeathListener(this),this);

        //game
        this.game = new GameUtils();

        //recipes
        AddCustomSoup();
        AddCustomGapple();
        AddCustomTnt();
        AddCustomStonePickaxe();
        AddCustomIronPickaxe();
        AddCustomDiamondPickaxe();

        //kits listeners
        this.getServer().getPluginManager().registerEvents(new FishingListener(this), this);
        this.getServer().getPluginManager().registerEvents(new GraplerListener(this), this);
        this.getServer().getPluginManager().registerEvents(new KangaroListener(this), this);
        this.getServer().getPluginManager().registerEvents(new ViperListener(this), this);
        this.getServer().getPluginManager().registerEvents(new GladiatorListener(this),this);
        this.getServer().getPluginManager().registerEvents(new WormListener(this),this);
        this.getServer().getPluginManager().registerEvents(new StomperListener(this),this);

        getLogger().info("Inicializacao completa");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Estou sendo desligado!!!");
    }

    //region File Handling

    public FileConfiguration getUsersConfig(){
        return usersConfig;
    }
    public void saveUsersConfig(){
        try{
            usersConfig.save(usersFile);
        }catch (Exception ex){
            getLogger().severe(ex.getMessage());
        }
    }
    //endregion

    //region Custom Recipes

    //util
    public void RemoveVanillaRecipe(Material m){
        Iterator<Recipe> recipes;
        recipes = getServer().recipeIterator();
        Recipe curRecipe;
        while (recipes.hasNext()) {
            curRecipe = recipes.next();
            if (curRecipe != null && curRecipe.getResult().getType() == m) {
                recipes.remove();
                getLogger().info("Sucessfully removed "+m.name()+" recipe");
            }
        }
    }

    //soup
    public void AddCustomSoup(){
        Material[] comidas = new Material[]{Material.COOKED_BEEF,Material.COOKED_CHICKEN,Material.COOKED_COD,
        Material.COOKED_MUTTON,Material.COOKED_PORKCHOP,Material.COOKED_SALMON,
        Material.COOKED_RABBIT};
        ItemStack sopa = new ItemStack(Material.MUSHROOM_STEW);
        ItemMeta meta = sopa.hasItemMeta() ? sopa.getItemMeta() : Bukkit.getItemFactory().getItemMeta(Material.MUSHROOM_STEW);

        assert meta != null;
        meta.setDisplayName("§aSopa de macaco");
        sopa.setItemMeta(meta);
        sopa.addUnsafeEnchantment(Enchantment.ARROW_INFINITE,1);

        for (Material comida:comidas) {
            NamespacedKey key = new NamespacedKey(this,"heal_soup_"+comida.name());
            ShapelessRecipe recipe = new ShapelessRecipe(key,sopa);
            recipe.addIngredient(Material.BOWL);
            recipe.addIngredient(comida);
            getServer().addRecipe(recipe);
            getLogger().info("Sucessfully created "+comida.name()+" soup");
        }
        NamespacedKey key = new NamespacedKey(this,"heal_soup_rotten_flesh");
        ShapelessRecipe recipe = new ShapelessRecipe(key,sopa);
        recipe.addIngredient(Material.BOWL);
        recipe.addIngredient(8,Material.ROTTEN_FLESH);
        getServer().addRecipe(recipe);
        getLogger().info("Sucessfully created "+Material.ROTTEN_FLESH.name()+" soup");
    }

    //golden apple
    public void AddCustomGapple(){
        RemoveVanillaRecipe(Material.GOLDEN_APPLE);

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this,"golden_apple"),new ItemStack(Material.GOLDEN_APPLE,1));
        recipe.shape(" G ","GAG"," G ");
        recipe.setIngredient('G',Material.GOLD_INGOT);
        recipe.setIngredient('A',Material.APPLE);
        getServer().addRecipe(recipe);
        getLogger().info("Sucessfully created "+Material.GOLDEN_APPLE.name()+" recipe");
    }

    //tnt
    public void AddCustomTnt(){
        RemoveVanillaRecipe(Material.TNT);

        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this,"tnt"),new ItemStack(Material.TNT,1));
        recipe.shape("SSS","GGG","SSS");
        recipe.setIngredient('S',Material.SAND);
        recipe.setIngredient('G',Material.GUNPOWDER);
        getServer().addRecipe(recipe);
        getLogger().info("Sucessfully created "+Material.TNT.name()+" recipe");
    }

    //stone pickaxe
    public void AddCustomStonePickaxe(){
        RemoveVanillaRecipe(Material.STONE_PICKAXE);

        //result item
        ItemStack pickaxe = new ItemStack(Material.STONE_PICKAXE);
        ItemMeta meta = pickaxe.hasItemMeta() ? pickaxe.getItemMeta() : Bukkit.getItemFactory().getItemMeta(Material.STONE_PICKAXE);
        assert meta != null;
        meta.addEnchant(Enchantment.DIG_SPEED,2,false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        pickaxe.setItemMeta(meta);

        //default cobblestone
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this,"stone_pickaxe_cobblestone"),pickaxe);
        recipe.shape("CCC"," S "," S ");
        recipe.setIngredient('C',Material.COBBLESTONE);
        recipe.setIngredient('S',Material.STICK);
        getServer().addRecipe(recipe);
        getLogger().info("Sucessfully created "+"stone_pickaxe_cobblestone"+" recipe");

        //cobbled deepslate
        ShapedRecipe recipe1 = new ShapedRecipe(new NamespacedKey(this,"stone_pickaxe_cobbled_deepslate"),pickaxe);
        recipe1.shape("CCC"," S "," S ");
        recipe1.setIngredient('C',Material.COBBLED_DEEPSLATE);
        recipe1.setIngredient('S',Material.STICK);
        getServer().addRecipe(recipe1);
        getLogger().info("Sucessfully created "+"stone_pickaxe_cobbled_deepslate"+" recipe");
    }

    //iron pickaxe
    public void AddCustomIronPickaxe(){
        RemoveVanillaRecipe(Material.IRON_PICKAXE);

        //result item
        ItemStack pickaxe = new ItemStack(Material.IRON_PICKAXE);
        ItemMeta meta = pickaxe.hasItemMeta() ? pickaxe.getItemMeta() : Bukkit.getItemFactory().getItemMeta(Material.IRON_PICKAXE);
        assert meta != null;
        meta.addEnchant(Enchantment.DIG_SPEED,3,false);
        meta.addEnchant(Enchantment.DURABILITY,1,false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        pickaxe.setItemMeta(meta);

        //craft
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this,"iron_pickaxe"),pickaxe);
        recipe.shape("III"," S "," S ");
        recipe.setIngredient('I',Material.IRON_INGOT);
        recipe.setIngredient('S',Material.STICK);
        getServer().addRecipe(recipe);
        getLogger().info("Sucessfully created "+Material.IRON_PICKAXE.name()+" recipe");
    }

    //diamond pickaxe
    public void AddCustomDiamondPickaxe(){
        RemoveVanillaRecipe(Material.DIAMOND_PICKAXE);

        //result item
        ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta meta = pickaxe.hasItemMeta() ? pickaxe.getItemMeta() : Bukkit.getItemFactory().getItemMeta(Material.DIAMOND_PICKAXE);
        assert meta != null;
        meta.addEnchant(Enchantment.DIG_SPEED,5,false);
        meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS,1,false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        pickaxe.setItemMeta(meta);

        //craft
        ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(this,"diamond_pickaxe"),pickaxe);
        recipe.shape("DDD"," S "," S ");
        recipe.setIngredient('D',Material.DIAMOND);
        recipe.setIngredient('S',Material.STICK);
        getServer().addRecipe(recipe);
        getLogger().info("Sucessfully created "+Material.DIAMOND_PICKAXE.name()+" recipe");
    }

    //endregion

    //get multiverse
    public MultiverseCore getMV(){
        if(getServer().getPluginManager().getPlugin("Multiverse-Core") instanceof MultiverseCore){
            return (MultiverseCore) getServer().getPluginManager().getPlugin("Multiverse-Core");
        }else{
            return null;
        }
    }

}
