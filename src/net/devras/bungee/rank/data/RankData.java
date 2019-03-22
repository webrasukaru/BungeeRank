package net.devras.bungee.rank.data;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import net.devras.bungee.rank.API;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class RankData {
	// Data Store
	private static HashMap<Integer, RankData> Enums = new HashMap<>();

	public static RankData getRank(int i) {
		if (Enums.containsKey(i)) {
			return Enums.get(i);
		}
		return null;
	}

	private static ConfigurationProvider provider;
	private static File file;
	private static Configuration config;
	public static void load(File file) {
		RankData.file = file;
		provider = ConfigurationProvider.getProvider(YamlConfiguration.class);

		try {
			if (!RankData.file.exists()) {
				RankData.file.createNewFile();
			}

			RankData.config = provider.load(file);

			if (config.contains("ranks")) {
				Configuration section = config.getSection("ranks");
				for (String key : section.getKeys()) {
					int id = section.getInt(key + ".id", 0);
					String prefix = section.getString(key + ".prefix", "");
					String suffix = section.getString(key + ".suffix", "");
					RankData data = new RankData(id, prefix, suffix);
					Enums.put(id, data);
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void save() {
		if (config == null) {
			load(new File(API.Instance.getDataFolder(), "ranks.yml"));
		}

		try {
			provider.save(config, file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Var
	private int id;
	private String prefix;
	private String suffix;
	private boolean isNone = false;

	public RankData(int id, String prefix) {
		this.id = id;
		this.prefix = ChatColor.translateAlternateColorCodes('&', prefix);
		this.suffix = "";
		Enums.put(id, this);

		if (id <= 0) {
			this.isNone = true;
		}
		
		if (prefix == "" && suffix == "") {
			this.isNone = true;
		}
		
	}
	public RankData(int id, String prefix, String suffix) {
		this.id = id;
		this.prefix = ChatColor.translateAlternateColorCodes('&', prefix);
		this.suffix = ChatColor.translateAlternateColorCodes('&', suffix);
		Enums.put(id, this);
		
		if (id <= 0) {
			this.isNone = true;
		}
		
		if (prefix == "" && suffix == "") {
			this.isNone = true;
		}
	}

	public int getId() {
		return this.id;
	}
	public String getPrefix() {
		return this.prefix;
	}
	public String getSuffix() {
		return this.suffix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public boolean isNone() {
		return isNone;
	}
	public void setNone(boolean isNone) {
		this.isNone = isNone;
	}
}
