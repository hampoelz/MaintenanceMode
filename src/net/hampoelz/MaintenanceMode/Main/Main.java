package net.hampoelz.MaintenanceMode.Main;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import net.hampoelz.MaintenanceMode.Commands.Maintenance;
import net.hampoelz.MaintenanceMode.Events.Events;

public class Main extends JavaPlugin
{
	
	@Override
	public void onEnable()
	{			   
		Bukkit.getPluginManager().registerEvents(new Events(), this);
		
		getCommands();
		
		try
		{
			Config.setConfig();
			Config.save();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		System.out.println("[MaintenanceMode] v" + getDescription().getVersion() + " enabled!");
	}

	@Override
	public void onDisable()
	{
		System.out.println("[MaintenanceMode] v" + getDescription().getVersion() + " disabled!");
	}	
	
	private void getCommands()
	{
		getCommand("maintenance").setExecutor(new Maintenance());
		getCommand("mm").setExecutor(new Maintenance());
	}
}
