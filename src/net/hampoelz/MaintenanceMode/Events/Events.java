package net.hampoelz.MaintenanceMode.Events;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.server.ServerListPingEvent;

import net.hampoelz.MaintenanceMode.Main.Config;
import net.md_5.bungee.api.ChatColor;

public class Events implements Listener
{
	@EventHandler
	public void onLogin(PlayerLoginEvent event)
	{
		String Kick = Config.getMaintenanceMessagesKick();

		boolean Maintenance = Config.getMaintenanceSettingsStatus();

		if (Maintenance == true)
		{
			if (!event.getPlayer().hasPermission("maintenancemode.join"))
			{
				event.disallow(null, ChatColor.translateAlternateColorCodes('&', Kick));
			}
		}
	}

	@EventHandler
	public void onPing(ServerListPingEvent event)
	{
		String Motd = Config.getNormalMODT();
		String MM_Motd = Config.getMaintenanceMODT();

		int MaxPlayers = Config.getNormalSettingsMaxPlayersInt();
		int MM_MaxPlayers = Config.getMaintenanceSettingsMaxPlayersInt();

		boolean Maintenance = Config.getMaintenanceSettingsStatus();
		boolean AutoMaxPlayers = Config.getNormalSettingsAutoMaxPlayersStatus();

		Motd = Motd.replaceAll("&", "\u00A7");
		MM_Motd = MM_Motd.replaceAll("&", "\u00A7");

		if (Maintenance == true)
		{
			event.setMotd(MM_Motd);
			event.setMaxPlayers(MM_MaxPlayers);
		}
		else
		{
			event.setMotd(Motd);

			if (AutoMaxPlayers == true)
			{
			  int PlayersOnline = Bukkit.getServer().getOnlinePlayers().size() + Config.getNormalSettingsAutoMaxPlayersInt();
				event.setMaxPlayers(PlayersOnline);
			}
			else
			{
				event.setMaxPlayers(MaxPlayers);
			}
		}
	}
}
