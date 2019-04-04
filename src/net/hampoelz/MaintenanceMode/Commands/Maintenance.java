package net.hampoelz.MaintenanceMode.Commands;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import net.hampoelz.MaintenanceMode.Main.Config;

public class Maintenance implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
		String Usage = Config.getMessagesUsage();
		String Output = Config.getMessagesOutput();
		
		boolean Maintenance = Config.getMaintenanceSettingsStatus();

		if (Maintenance == true)
			Output = Output.replace("%status%", "&a&lEnabled");
		else if (Maintenance == false)
			Output = Output.replace("%status%", "&c&lDisabled");
		
		if (sender instanceof ConsoleCommandSender)
		{
			if (args.length == 0)
			{
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Output));
			}
			else if (args.length == 1)
			{
				if (args[0].equalsIgnoreCase("reload"))
				{
					try
					{
						Config.load();

						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aThe config was successfully reloaded"));
					}
					catch (IOException | InvalidConfigurationException e)
					{
						e.printStackTrace();

						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cAn error occurred while reloading the configuration"));
					}
				}
				else
				{
					sender.sendMessage("§cThis command can only be executed as a player");
				}
			}
			else
			{
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', Usage));
			}
		}

		if (sender instanceof Player)
		{
			Player p = (Player) sender;

			String NoPermissions = Config.getMessagesNoPermissions();

			String Enabled = Config.getMessagesEnabled();
			String AEnabled = Config.getMessagesAlreadyEnabled();
			String Disabled = Config.getMessagesDisabled();
			String ADisabled = Config.getMessagesAlreadyDisabled();

			String Kick = Config.getMaintenanceMessagesKick();
			
			if (args.length == 0)
			{
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', Output));
			}
			else if (args.length == 1)
			{
				if (args[0].equalsIgnoreCase("on"))
				{
					if (p.hasPermission("maintenancemode.toggle"))
					{
						if (Maintenance == true)
						{
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', AEnabled));
						}
						else
						{
							try
							{
								Config.setMaintenanceSettingsStatus(true);
								p.sendMessage(ChatColor.translateAlternateColorCodes('&', Enabled));
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
							
					        for (Player player : Bukkit.getServer().getOnlinePlayers())
					        {
					            if (!player.isOp() && !player.hasPermission("maintenancemode.join"))
					            {
					                player.kickPlayer(ChatColor.translateAlternateColorCodes('&', Kick));
					            }
					        }
							
						}
					}
					else
					{
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', NoPermissions));
					}
				}
				else if (args[0].equalsIgnoreCase("off"))
				{
					if (p.hasPermission("maintenancemode.toggle"))
					{
						if (Maintenance == false)
						{
							p.sendMessage(ChatColor.translateAlternateColorCodes('&', ADisabled));
						}
						else
						{
							try
							{
								Config.setMaintenanceSettingsStatus(false);
								p.sendMessage(ChatColor.translateAlternateColorCodes('&', Disabled));
							}
							catch (IOException e)
							{
								e.printStackTrace();
							}
						}
					}
					else
					{
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', NoPermissions));
					}
				}
				else if (args[0].equalsIgnoreCase("reload"))
				{
					if (p.hasPermission("maintenancemode.reload"))
					{
						try
						{
							Config.load();

							p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6MaintenanceMode&8] &aThe config was successfully reloaded"));
						}
						catch (IOException | InvalidConfigurationException e)
						{
							e.printStackTrace();

							p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8[&6MaintenanceMode&8] &cAn error occurred while reloading the configuration"));
						}
					}
					else
					{
						p.sendMessage(ChatColor.translateAlternateColorCodes('&', NoPermissions));
					}
				}
				else
				{
					p.sendMessage(ChatColor.translateAlternateColorCodes('&', Usage));
				}
			}
			else
			{
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', Usage));
			}
		}

		return true;
	}
}
