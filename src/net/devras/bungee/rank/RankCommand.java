package net.devras.bungee.rank;

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

			if (id >= 0 && id <= 10) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						String base = "insert into ranks(uuid, id)"
								+ " values('%s', '%s')"
								+ " on duplicate key update id=values(id);";
						String sql = String.format(base, player.getUniqueId().toString(), id);
						MySQL.update(sql);

						Rank rank = Rank.getRank(id);
						if (!rank.equals(Rank.NONE)) {
							String disp = "§6[" + rank.getPrefix() + "§r§6] §r" + player.getName();
							Nick.send("Nick", "setnickname", player.getUniqueId().toString(), disp);
						}
					}
				}).start();

				ComponentBuilder comp = new ComponentBuilder("");
				comp.append("Success Query").color(ChatColor.GREEN).bold(true);
				sender.sendMessage(comp.create());
				return;
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
	}
}
