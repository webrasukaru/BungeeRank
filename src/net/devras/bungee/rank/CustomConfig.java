package net.devras.bungee.rank;

import java.io.File;
import java.io.IOException;

import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class CustomConfig {
	private ConfigurationProvider provider;
	private File dir, info;
	private String file;
	private Configuration config;

	public CustomConfig(File dir, String file) {
		this.provider = ConfigurationProvider.getProvider(YamlConfiguration.class);
		this.setDir(dir);
		this.setFile(file);

		if (!dir.exists()) {
			dir.mkdirs();
		}

		info = new File(dir, file);
		try {
			if (!info.exists()) {
				info.createNewFile();
				getConfig().set("mysql.host", "localhost");
				getConfig().set("mysql.name", "mc_ranks");
				getConfig().set("mysql.user", "root");
				getConfig().set("mysql.pass", "1234");
				getConfig().set("mysql.port", "3306");
				saveConfig();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		reloadConfig();
	}

	public Configuration getConfig() {
		if (config == null) {
			reloadConfig();
		}
		return config;
	}

	public void saveConfig() {
		try {
			this.provider.save(config, info);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void reloadConfig() {
		try {
			config = this.provider.load(info);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public File getDir() {
		return dir;
	}

	public void setDir(File dir) {
		this.dir = dir;
	}
}
