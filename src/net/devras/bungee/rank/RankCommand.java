package net.devras.bungee.rank;

<<<<<<< HEAD
import net.devras.bungee.rank.data.RankData;
=======
>>>>>>> bungeerank/master
import net.devras.nick.Nick;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class RankCommand extends Command {
	public RankCommand() {
		super("rank", "net.devras.rank");
	}

	@Override
	public void execute(final CommandSender sender, String[] args) {
		if (args.length > 1) {
			final String name = args[0];
			final ProxiedPlayer player = API.Instance.getProxy().getPlayer(name);
			final int id = Integer.parseInt(args[1]);

			if (player == null || player.getUniqueId() == null) {
				ComponentBuilder comp = new ComponentBuilder("");
				comp.append("Sorry, can not found the that player!").color(ChatColor.RED).bold(true);
				sender.sendMessage(comp.create());
				return;
			}

<<<<<<< HEAD
			if (id >= 0) {
=======
			if (id >= 0 && id <= 10) {
>>>>>>> bungeerank/master
				new Thread(new Runnable() {
					@Override
					public void run() {
						String base = "insert into ranks(uuid, id)"
								+ " values('%s', '%s')"
								+ " on duplicate key update id=values(id);";
						String sql = String.format(base, player.getUniqueId().toString(), id);
						MySQL.update(sql);

<<<<<<< HEAD
						/*
=======
>>>>>>> bungeerank/master
						Rank rank = Rank.getRank(id);
						if (!rank.equals(Rank.NONE)) {
							String disp = "§6[" + rank.getPrefix() + "§r§6] §r" + player.getName();
							Nick.send("Nick", "setnickname", player.getUniqueId().toString(), disp);
						}
<<<<<<< HEAD
						*/

						RankData data = RankData.getRank(id);
						if (data != null && !data.isNone()) {
							String disp = "§6[" + data.getPrefix() + "§r§6] §r" + player.getName() + " §r" + data.getSuffix();
							Nick.send("Nick", "setnickname", player.getUniqueId().toString(), disp);
						}else {
							Nick.send("Nick", "setnickname", player.getUniqueId().toString(), player.getName());
						}
=======
>>>>>>> bungeerank/master
					}
				}).start();

				ComponentBuilder comp = new ComponentBuilder("");
				comp.append("Success Query").color(ChatColor.GREEN).bold(true);
				sender.sendMessage(comp.create());
				return;
<<<<<<< HEAD
			}

		}

		ComponentBuilder comp = new ComponentBuilder("");
		comp.append("/rank <name> <id>").color(ChatColor.RED).bold(true);
		sender.sendMessage(comp.create());
=======
			}else {
				ComponentBuilder comp = new ComponentBuilder("");
				comp.append("Id Allow range: 0 - 10").color(ChatColor.RED).bold(true);
				sender.sendMessage(comp.create());
				return;
			}

		}else {
			ComponentBuilder comp = new ComponentBuilder("");
			comp.append("/rank <name> <id>").color(ChatColor.RED).bold(true);
			sender.sendMessage(comp.create());
		}
>>>>>>> bungeerank/master
	}
}
