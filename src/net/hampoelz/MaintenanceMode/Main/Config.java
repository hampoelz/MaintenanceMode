package net.hampoelz.MaintenanceMode.Main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config
{

	public static File ConfigFile = new File("plugins/MaintenanceMode", "config.yml");
	public static FileConfiguration Config = YamlConfiguration.loadConfiguration(ConfigFile);

	//----------------------------------------------------------------------------------------------------------------------\\

	public static void save() throws IOException
	{
		Config.save(ConfigFile);
	}

	public static void load() throws FileNotFoundException, IOException, InvalidConfigurationException
	{
		Config.load(ConfigFile);
	}

	private static void setConfigContent(int ConfigVersion) throws IOException
	{
		Config.set("config.ConfigVersion", ConfigVersion);

		Config.set("config.Messages.No Permissions", "&8[&6MaintenanceMode&8] &cYou not have permission to perform this command.");
		Config.set("config.Messages.Usage", "&8[&6MaintenanceMode&8] &7Usage: &c/maintenance [on/off]");
		Config.set("config.Messages.Enabled", "&8[&6MaintenanceMode&8] &7The Maintenance Mode is now &a&lEnabled&7.");
		Config.set("config.Messages.Disabled", "&8[&6MaintenanceMode&8] &7The Maintenance Mode is now &c&lDisabled&7.");
		Config.set("config.Messages.Output", "&8[&6MaintenanceMode&8] &7Maintenance status: %status%");
		Config.set("config.Messages.Already Enabled", "&8[&6MaintenanceMode&8] &7The Maintenance Mode is already &a&lEnabled&7.");
		Config.set("config.Messages.Already Disabled", "&8[&6MaintenanceMode&8] &7The Maintenance Mode is already &c&lDisabled&7.");

		Config.set("config.Normal.Motd", "&a&m--&2&m--&8&m[-&r &a&lServer &f&lNetwork &8&m-]&2&m--&a&m--\n&7[&61.10&7] &8| &2Minigames &8| &9CityBuild &8| &2Survival");
		Config.set("config.Normal.Settings.Auto Max Players.Status", false);
		Config.set("config.Normal.Settings.Auto Max Players.Int", 2);
		Config.set("config.Normal.Settings.Max Players.Int", 200);

		Config.set("config.Maintenance.Motd", "&a&m--&2&m--&8&m[-&r &a&lServer &f&lNetwork &8&m-]&2&m--&a&m--\n&cThe server is currently in Maintenance Mode!");
		Config.set("config.Maintenance.Messages.Kick", "&4&lWe are Sorry!\n\n&cThe server is currently in Maintenance Mode!\n&cCheck back soon!\n\n&2Your Server Team!");
		Config.set("config.Maintenance.Settings.Status", false);
		Config.set("config.Maintenance.Settings.Max Players.Int", 0);

		save();
	}
	
	public static void setConfig() throws IOException
	{
		int ConfigVersion = 2;

		int Version = Config.getInt("config.ConfigVersion");

		if(!ConfigFile.exists())
		{
			setConfigContent(ConfigVersion);
		}
		else if (Version != ConfigVersion)
		{
			File ConfigFileBackup = new File("plugins/MaintenanceMode", "old config [v" + Version + "].yml");

			FileUtils.copyFile(ConfigFile, ConfigFileBackup);

			for (String key : Config.getKeys(false))
			{
				Config.set(key, null);
			}
			
			save();
			
			setConfigContent(ConfigVersion);
		}
	}

	//----------------------------------------------------------------------------------------------------------------------\\

	public static String getMessagesNoPermissions()
	{
		return Config.getString("config.Messages.No Permissions");
	}

	public static String getMessagesUsage()
	{
		return Config.getString("config.Messages.Usage");
	}

	public static String getMessagesEnabled()
	{
		return Config.getString("config.Messages.Enabled");
	}

	public static String getMessagesDisabled()
	{
		return Config.getString("config.Messages.Disabled");
	}

	public static String getMessagesOutput()
	{
		return Config.getString("config.Messages.Output");
	}

	public static String getMessagesAlreadyEnabled()
	{
		return Config.getString("config.Messages.Already Enabled");
	}

	public static String getMessagesAlreadyDisabled()
	{
		return Config.getString("config.Messages.Already Disabled");
	}

	//----------------------------------------------------------------------------------------------------------------------\\

	public static String getNormalMODT()
	{
		return Config.getString("config.Normal.Motd");
	}

	public static Boolean getNormalSettingsAutoMaxPlayersStatus()
	{
		return Config.getBoolean("config.Normal.Settings.Auto Max Players.Status");
	}

	public static int getNormalSettingsAutoMaxPlayersInt()
	{
		return Config.getInt("config.Normal.Settings.Auto Max Players.Int");
	}

	public static int getNormalSettingsMaxPlayersInt()
	{
		return Config.getInt("config.Normal.Settings.Max Players.Int");
	}

	//----------------------------------------------------------------------------------------------------------------------\\

	public static String getMaintenanceMODT()
	{
		return Config.getString("config.Maintenance.Motd");
	}

	public static String getMaintenanceMessagesKick()
	{
		return Config.getString("config.Maintenance.Messages.Kick");
	}

	public static Boolean getMaintenanceSettingsStatus()
	{
		return Config.getBoolean("config.Maintenance.Settings.Status");
	}

	public static int getMaintenanceSettingsMaxPlayersInt()
	{
		return Config.getInt("config.Maintenance.Settings.Max Players.Int");
	}

	//----------------------------------------------------------------------------------------------------------------------\\

	public static void setMaintenanceSettingsStatus(Boolean Maintenance) throws IOException
	{
		Config.set("config.Maintenance.Settings.Status", Maintenance);
		save();
	}
}
