package com.Geekpower14.quake;

import com.Geekpower14.quake.arena.ArenaManager;
import com.Geekpower14.quake.commands.CommandsManager;
import com.Geekpower14.quake.listener.PlayerListener;
import com.Geekpower14.quake.stuff.ItemManager;
import net.minecraft.server.v1_7_R4.EntityPlayer;
import net.samagames.gameapi.GameAPI;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Quake extends JavaPlugin{

	public static Quake plugin;
	private static String[] colors = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "a","b", "c", "d", "f", "g", "h", "i", "j","k","l","m","n","o","p","r","s","t","u","v","w","x","y","z"};
	private static int e = 0;
	private static int r = 0;
	public Logger log;
	public ArenaManager arenaManager;
	public CommandsManager commandsManager;
	public ItemManager itemManager;
	public int DefaultPort;
	public String BungeeName;

	public String type = "solo";

	public HashMap<Player, ArrayList<Object>> cachedPackets = new HashMap<Player, ArrayList<Object>>();
	
	public BukkitTask TabTask;

	
	/* return the next null filler */
	public static String nextNull(){
		String s = "";
		for(int a = 0; a < r; a++){
			s = " "+s;
		}
		s = s + "\u00A7" + colors[e];
		e++;
		if(e > 14){
			e = 0;
			r++;
		}
		return s;
	}

	public static Quake getPlugin() {
		return plugin;
	}

	public static Boolean hasPermission(Player p, String perm)
	{
		if(perm.equalsIgnoreCase(""))
			return true;
		if(p.isOp())
			return true;
		if(p.hasPermission("Quake.admin"))
			return true;
		if(p.hasPermission(perm))
			return true;

		return false;
	}

    public static List<Player> getOnline() {
        List<Player> list = new ArrayList<>();

        for (World world : Bukkit.getWorlds()) {
            list.addAll(world.getPlayers());
        }
        return Collections.unmodifiableList(list);
    }

    public static int getPing(Player p)
    {
        CraftPlayer cp = (CraftPlayer) p;
        EntityPlayer ep = cp.getHandle();

        return ep.ping;
    }
	
    public static int msToTick(int ms)
    {
        return (ms*20)/1000;
    }

	public void onEnable()
	{
		log = getLogger();
		plugin = this;

		File conf = new File(getDataFolder().getAbsoluteFile().getParentFile().getParentFile(), "data.yml");
		this.getLogger().info("Searching data.yml in "+conf.getAbsolutePath());
		if (!conf.exists()) {
			this.getLogger().log(Level.SEVERE, "StatsApi stopped loading : data.yml not found");
			this.getPluginLoader().disablePlugin(this);
			return;
		}
		Bukkit.getWorld("world").setAutoSave(false);

		this.saveDefaultConfig();

		DefaultPort = getConfig().getInt("port");
		BungeeName = getConfig().getString("BungeeName");
		type = getConfig().getString("Type", "solo");

		String type_ = (type.equals("team"))?"quaketeam":"quake";

		GameAPI.registerGame(type_, DefaultPort, BungeeName);

		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

		arenaManager = new ArenaManager(this);

		itemManager = new ItemManager(this);

		commandsManager = new CommandsManager(this);

		getCommand("q").setExecutor(commandsManager);

		Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);

		/*try{
			Method a = EntityTypes.class.getDeclaredMethod("a", new Class<?>[]{Class.class, String.class, int.class});
			a.setAccessible(true);
			a.invoke(null, CustomEntityFirework.class, "EntityFirework", 99999);
		}catch (Exception e){
			e.printStackTrace();
		}*/

		GameAPI.getManager().sendArenas();
		log.info("quake enabled!");
	}

	public void onDisable()
	{
		//TabTask.cancel();
		arenaManager.disable();

		log.info("quake disabled!");
	}
}
