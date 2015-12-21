package com.Geekpower14.quake.stuff;

import com.Geekpower14.quake.Quake;
import com.Geekpower14.quake.arena.APlayer;
import com.Geekpower14.quake.utils.Utils.ItemSlot;
import com.Geekpower14.quake.utils.GlowEffect;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public abstract class TItem implements Cloneable{
	
	public Quake plugin;
	
	public String name = "Unknown";
	
	public String alias = "";
	
	public String givePerm = "quake.admin";
	
	public long reloadTime;
	
	public int nb = 1;
	
	public TItem(String name, String display, int nb, long l)
	{
		this.name = name;
		this.alias = display;
		this.reloadTime = l;
		
		this.nb = nb;
		
		plugin = Quake.getPlugin();
	}
	
	public static ItemStack addGlow(ItemStack item){
		return GlowEffect.addGlow(item);
	}
	
	public static ItemStack setItemNameAndLore(ItemStack item, String name, String[] lore, boolean glow)
	{
		ItemMeta im = item.getItemMeta();
		if (im == null)
			return item;
		if (!name.isEmpty())
			im.setDisplayName(name);
		if (lore != null)
			im.setLore(Arrays.asList(lore));
		item.setItemMeta(im);
		try{
			if(glow)
				item = addGlow(item);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return item;
	}
	
    public static long secondToTick(double second)
    {
        return (long) (second * 20);
    }
	
	public String getName()
	{
		return name;
	}
	
	public String getDisplayName()
	{
		return alias;
	}
	
	public String getGivePerm()
	{
		return this.givePerm;
	}
	
	public Boolean istheSame(ItemStack it)
	{
		ItemStack item = this.getItem();

		ItemMeta meta = item.getItemMeta();
		ItemMeta met = it.getItemMeta();

		if(meta == null && met == null)
		{
			return true;
		}

		if(meta == null || met == null)
		{
			return false;
		}

		if (!meta.getDisplayName().equalsIgnoreCase(met.getDisplayName()))
		{
			return false;
		}

		if (!meta.getLore().equals(met.getLore()))
		{
			return false;
		}

		return true;
	}
	
	public Object clone() {
		Object o = null;
		try {
			// On récupère l'instance à renvoyer par l'appel de la
			// méthode super.clone()
			o = super.clone();
		} catch(CloneNotSupportedException cnse) {
			// Ne devrait jamais arriver car nous implémentons
			// l'interface Cloneable
			cnse.printStackTrace(System.err);
		}
		// on renvoie le clone
		return o;
	}
	
	public int getNB()
	{
		return nb;
	}

	public void setNB(int nb)
	{
		this.nb = nb;
	}
	
	public abstract ItemStack getItem();
	
	public abstract void rightAction(APlayer ap, ItemSlot slot);

	public abstract void leftAction(APlayer p, ItemSlot slot);
	
	//public abstract void onItemTouchGround(arena arena, Item item);

}
