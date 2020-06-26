package com.booksaw.betterTeams.commands.teama;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.booksaw.betterTeams.Main;
import com.booksaw.betterTeams.MessageManager;
import com.booksaw.betterTeams.Team;
import com.booksaw.betterTeams.TeamPlayer;
import com.booksaw.betterTeams.commands.SubCommand;

public class TitleTeama extends SubCommand {

	private final char[] bannedChars = new char[] { ',', '�' };

	@Override
	public String onCommand(CommandSender sender, String label, String[] args) {
		Team team = Team.getTeam(Bukkit.getPlayer(args[0]));

		if (team == null) {
			return "admin.inTeam";
		}

		TeamPlayer toTitle = team.getTeamPlayer(Bukkit.getPlayer(args[0]));

		if (toTitle == null) {
			return "noPlayer";
		}

		if (args[1].length() > Main.plugin.getConfig().getInt("maxTitleLength")) {
			return "title.tooLong";
		}

		for (char bannedChar : bannedChars) {
			if (args[1].contains(bannedChar + "")) {
				return "bannedChar";
			}
		}

		args[1] = ChatColor.translateAlternateColorCodes('&', args[1]);

		team.setTitle(toTitle, args[1]);

		MessageManager.sendMessageF(toTitle.getPlayer().getPlayer(), "title.change", args[1]);

		return "admin.title.success";
	}

	@Override
	public String getCommand() {
		return "title";
	}

	@Override
	public String getNode() {
		return "admin.title";
	}

	@Override
	public String getHelp() {
		return "Change a players title to the specified title";
	}

	@Override
	public String getArguments() {
		return "<player> <title>";
	}

	@Override
	public int getMinimumArguments() {
		return 2;
	}

	@Override
	public int getMaximumArguments() {
		return 2;
	}

	@Override
	public void onTabComplete(List<String> options, CommandSender sender, String label, String[] args) {
		if (args.length == 1) {
			addPlayerStringList(options, args[0]);
		}
		if (args.length == 2) {
			options.add("<title>");
		}
	}

}
