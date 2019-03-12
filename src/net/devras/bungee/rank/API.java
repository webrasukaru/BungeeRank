package net.devras.bungee.rank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import net.devras.nick.Nick;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;

public class API extends Plugin implements Listener{
	public static API Instance;
	public static boolean isBungeeNick = false;

	@Override
	public void onDisable() {
		super.onDisable();
	}

	@Override
	public void onEnable() {
		super.onEnable();
		Instance = this;

		CustomConfig Config = new CustomConfig(getDataFolder(), "config.yml");
		Configuration config = Config.getConfig();

		String host = config.getString("mysql.host"),
				port = config.getString("mysql.port"),
				name = config.getString("mysql.name"),
				user = config.getString("mysql.user"),
				pass = config.getString("mysql.pass");

		MySQL.Config(host, port, name, user, pass);
		MySQL.Connect();

		if (MySQL.isConnected()) {
			if (!MySQL.tableExists("ranks")) {
				MySQL.createTable("ranks", "uuid varchar(36) not null primary key, id int not null default 0");
			}
		}

		for (Plugin plugin : getProxy().getPluginManager().getPlugins()) {
			if (plugin.getDescription().getName().equalsIgnoreCase("BungeeNick")){
				isBungeeNick = true;
			}
		}

		getProxy().getPluginManager().registerCommand(this, new RankCommand());
		getProxy().getPluginManager().registerListener(this, this);
	}

	@EventHandler
	public void onJoin(PostLoginEvent event) {
		ProxiedPlayer p = event.getPlayer();
		UUID uuid = p.getUniqueId();

		if (MySQL.isConnected()) {
			ResultSet res = MySQL.query(String.format("select * from ranks where uuid='%s';", uuid.toString()));
			if (res != null) {
				try {
					while (res.next()) {
						int id = res.getInt("id");
						Rank rank = Rank.getRank(id);
						if (!rank.equals(Rank.NONE)) {
							String disp = "§6[" + rank.getPrefix() + "§r§6] §r" + p.getName();
							Nick.send("Nick", "setnickname", uuid.toString(), disp);
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
