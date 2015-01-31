package com.Geekpower14.quake.stuff.hoe;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class AmazingHoe extends HoeBasic{

	public AmazingHoe()
	{
		super("amazinghoe",
				ChatColor.BLUE + "Amazing PortalGun",
				secondToTick(1.3),
				FireworkEffect.builder().withColor(Color.FUCHSIA).with(FireworkEffect.Type.CREEPER).build());
        //this.aim = 1.2;
	}

	public ItemStack getItem() {
		ItemStack coucou = setItemNameAndLore(new ItemStack(Material.DIAMOND_HOE), ChatColor.BLUE + "Amazing PortalGun", new String[]{
			ChatColor.DARK_GRAY + "Recharge en " + ChatColor.GOLD +"1.3" + ChatColor.DARK_GRAY + " secondes."
		}, true);

		return coucou;
	}
}